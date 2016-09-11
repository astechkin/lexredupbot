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
package telegram.api.data;

/**
 *
 * @author user
 */
class CallbackQuery {

    String id; //Unique identifier for this query
    User from; //Sender
    Message message; //Optional. Message with the callback button that originated the query. Note that message content and message date will not be available if the message is too old
    String inline_message_id; //Optional. Identifier of the message sent via the bot in inline mode, that originated the query
    String data; //Data associated with the callback button. Be aware that a bad client can send arbitrary data in this field

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getInline_message_id() {
        return inline_message_id;
    }

    public void setInline_message_id(String inline_message_id) {
        this.inline_message_id = inline_message_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
