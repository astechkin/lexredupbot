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
public class Update {

    Long update_id; //The update‘s unique identifier. Update identifiers start from a certain positive number and increase sequentially. This ID becomes especially handy if you’re using Webhooks, since it allows you to ignore repeated updates or to restore the correct update sequence, should they get out of order.
    Message message; //Optional. New incoming message of any kind — text, photo, sticker, etc.
    Message edited_message; //Optional. New version of a message that is known to the bot and was edited
    InlineQuery inline_query; //Optional. New incoming inline query
    ChosenInlineResult chosen_inline_result; //Optional. The result of an inline query that was chosen by a user and sent to their chat partner.
    CallbackQuery callback_query; //Optional. New incoming callback query

    /**
     * Update identifiers start from a certain positive number and increase
     * sequentially. This ID becomes especially handy if you’re using Webhooks,
     * since it allows you to ignore repeated updates or to restore the correct
     * update sequence, should they get out of order.
     *
     * @return The update‘s unique identifier
     */
    public Long getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(Long update_id) {
        this.update_id = update_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getEdited_message() {
        return edited_message;
    }

    public void setEdited_message(Message edited_message) {
        this.edited_message = edited_message;
    }

    public InlineQuery getInline_query() {
        return inline_query;
    }

    public void setInline_query(InlineQuery inline_query) {
        this.inline_query = inline_query;
    }

    public ChosenInlineResult getChosen_inline_result() {
        return chosen_inline_result;
    }

    public void setChosen_inline_result(ChosenInlineResult chosen_inline_result) {
        this.chosen_inline_result = chosen_inline_result;
    }

    public CallbackQuery getCallback_query() {
        return callback_query;
    }

    public void setCallback_query(CallbackQuery callback_query) {
        this.callback_query = callback_query;
    }

}
