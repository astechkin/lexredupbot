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
package org.telegram.api;

/**
 *
 * @author user
 */
public class Update {

    Integer update_id; //The update‘s unique identifier. Update identifiers start from a certain positive number and increase sequentially. This ID becomes especially handy if you’re using Webhooks, since it allows you to ignore repeated updates or to restore the correct update sequence, should they get out of order.
    Message message; //Optional. New incoming message of any kind — text, photo, sticker, etc.
    Message edited_message; //Optional. New version of a message that is known to the bot and was edited
    InlineQuery inline_query; //Optional. New incoming inline query
    ChosenInlineResult chosen_inline_result; //Optional. The result of an inline query that was chosen by a user and sent to their chat partner.
    CallbackQuery callback_query; //Optional. New incoming callback query

}
