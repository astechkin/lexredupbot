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
import telegram.api.data.Chat;
import telegram.api.data.Message;
import telegram.api.data.Sticker;
import telegram.api.data.Update;
import telegram.api.data.User;

/**
 *
 * @author user
 */
public interface BotHandler {

    public void setBotToken(String token);

    public void setApi(BotApi api);

    public void newUpdate(Update update);

    public void onMessage(Message message);

    public void onCommand(Chat chat, User from, String command);

    public void onText(Chat chat, User from, String text);

    public void onNewChatMember(Chat chat, User chatMember);

    public void onLeftChatMember(Chat chat, User chatMember);

    public void onNewChatTitle(Chat chat, String title);

    public void onSticker(Chat chat, User from, Sticker sticker);

}
