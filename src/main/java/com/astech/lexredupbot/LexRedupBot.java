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
package com.astech.lexredupbot;

import telegram.api.BotApi;
import telegram.api.data.Chat;
import telegram.api.data.Message;
import telegram.api.data.Sticker;
import telegram.api.data.Update;
import telegram.api.data.User;
import telegram.polling.BotApiFactory;
import telegram.polling.BotHandler;
import telegram.polling.PollingBot;

/**
 * Lexical Reduplication LexRedupBot for the Telegram LexRedupBot API
 *
 * @author user
 */
public class LexRedupBot implements BotHandler {

    private BotApi api;
    private long botID;

    public static void main(String... args) {
        PollingBot bot = new PollingBot(BotApiFactory.getBotToken("lexredupbot", "key.bin"), 3, false);
        bot.setHandler(new LexRedupBot());
        System.out.println("Starting Bot.");
        bot.start();
    }

    @Override
    public void setBotToken(String token) {
        botID = Long.valueOf(token.split(":")[0]);
    }

    @Override
    public void setApi(BotApi api) {
        this.api = api;
    }

    @Override
    public void newUpdate(Update update) {
        //System.out.println("new Update");
    }

    @Override
    public void onMessage(Message message) {
        //System.out.println("new Message");
    }

    @Override
    public void onCommand(Chat chat, User from, String command) {
        String redup = LexRedupler.processText(command);
        api.sendMessage(chat.getId(), redup);
    }

    @Override
    public void onText(Chat chat, User from, String text) {
        String redup = LexRedupler.processText(text);
        api.sendMessage(chat.getId(), redup);
    }

    @Override
    public void onNewChatMember(Chat chat, User chatMember) {
        String answer;
        if (botID == chatMember.getId()) {
            answer = "Спасибо, что позвали!";
        } else {
            answer = "LexredupBot приветствует тебя, " + chatMember.getFirst_name() + "!";
        }
        api.sendMessage(chat.getId(), answer);
    }

    @Override
    public void onLeftChatMember(Chat chat, User chatMember) {
        String answer = "Пока, " + chatMember.getFirst_name() + "! Буду скучать!";
        api.sendMessage(chat.getId(), answer);
    }

    @Override
    public void onNewChatTitle(Chat chat, String title) {
        String redup = LexRedupler.processText(title);
        api.sendMessage(chat.getId(), redup);
    }

    @Override
    public void onSticker(Chat chat, User from, Sticker sticker) {
        api.sendSticker(chat.getId(), "BQADAgAD2QADWfR0AAEoBUkSdNeWIQI");
    }

}
