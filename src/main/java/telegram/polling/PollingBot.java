/*
 * Copyright (C) 2016 user
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package telegram.polling;

import telegram.api.BotApi;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import telegram.api.data.Chat;
import telegram.api.data.Message;
import telegram.api.data.Update;
import telegram.api.data.response.UpdateResponse;

/**
 *
 * @author user
 */
public class PollingBot implements Runnable {

    private static final Logger LOG = Logger.getLogger(PollingBot.class.getName());

    private int pollingInterval = 2;
    private boolean longPolling = false;
    private String botToken;

    private Thread poller;
    private BotHandler handler;
    private BotApi api;
    private long offset;

    public PollingBot(String botToken) {
        this.botToken = botToken;
    }

    public PollingBot(String botToken, int pollingInterval, boolean longPolling) {
        this(botToken);
        this.pollingInterval = pollingInterval;
        this.longPolling = longPolling;
    }

    public void setHandler(BotHandler listener) {
        this.handler = listener;
    }

    public BotHandler getHandler() {
        return handler;
    }

    public void start() {
        api = BotApiFactory.getInstance(botToken);
        offset = 1;
        poller = new Thread(this, "Bot Poller");
        poller.start();
    }

    @Override
    public void run() {
        boolean interrupted = false;
        try {
            if (handler != null) {
                handler.setBotToken(botToken);
                handler.setApi(api);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Setting Token & API", e);
            interrupted = true;
        }
        long old_offset = 0;
        long max_update_id = 0;
        while (!interrupted) {
            try {
                if (offset != old_offset) {
                    System.out.println("Requesting from id: " + offset);
                } else if (max_update_id != 0) {
                    // if something goes wrong and we stuck on the same offset, go next
                    System.out.println("Skipping probably bad id: " + offset);
                    offset++;
                }
                max_update_id = 0;
                UpdateResponse updateResponse = null;
                try {
                    updateResponse = api.getUpdates(offset, 1, longPolling ? pollingInterval : 0);
                } catch (BotException ex) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex.getCause() == null ? ex : ex.getCause());
                    if (ex.getCause() == null) {
                        // skip current message as an invalid
                        max_update_id = offset;
                    }
                }
                old_offset = offset;
                if (updateResponse != null) {
                    if (updateResponse.getOk() && updateResponse.getResult() != null) {
                        for (Update update : updateResponse.getResult()) {
                            long update_id = update.getUpdate_id();
                            if (update_id > max_update_id) {
                                max_update_id = update_id;
                            }
                            try {
                                System.out.println("Received update, id: " + update_id);
                                if (handler != null) {
                                    handler.newUpdate(update);
                                    if (update.getMessage() != null) {
                                        Message message = update.getMessage();
                                        Chat chat = message.getChat();
                                        handler.onMessage(message);
                                        if (message.getText() != null) {
                                            String text = message.getText();
                                            if (text.startsWith("/")) {
                                                handler.onCommand(chat, message.getFrom(), text.substring(1));
                                            } else {
                                                handler.onText(chat, message.getFrom(), text);
                                            }
                                        }
                                        if (message.getNew_chat_member() != null) {
                                            handler.onNewChatMember(chat, message.getNew_chat_member());
                                        }
                                        if (message.getLeft_chat_member() != null) {
                                            handler.onLeftChatMember(chat, message.getLeft_chat_member());
                                        }
                                        if (message.getNew_chat_title() != null) {
                                            handler.onNewChatTitle(chat, message.getNew_chat_title());
                                        }
                                        if (message.getSticker() != null) {
                                            handler.onSticker(chat, message.getFrom(), message.getSticker());
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                                if (ex instanceof InterruptedException) {
                                    throw ex;
                                }
                                ex.printStackTrace();
                            }
                        }
                    } else {
                        System.out.println("Received null update result");
                    }
                } else {
                }
                if (max_update_id != 0) {
                    offset = max_update_id + 1;
                }

                if (!longPolling) {
                    synchronized (this) {
                        this.wait(pollingInterval * 1000);
                    }
                }

            } catch (InterruptedException e) {
                interrupted = true;
                e.printStackTrace();
            }
        }
        System.out.println("Shutting down");
    }

    public String getBotToken() {
        return botToken;
    }

    public BotApi getApi() {
        return api;
    }

}
