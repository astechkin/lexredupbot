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
package telegram.api;

import telegram.api.data.InputFile;
import telegram.api.data.response.Response;
import telegram.api.data.response.UpdateResponse;
import telegram.api.data.User;
import telegram.api.data.response.MessageResponse;
import telegram.api.data.response.UserResponse;
import telegram.api.annotation.Param;
import telegram.polling.BotException;

/**
 * Telegram Bot Api Interface
 *
 * @author user
 */
public interface BotApi {

    /**
     * A simple method for testing your bot's auth token. Requires no
     * parameters. Returns basic information about the bot in form of a
     * {@link telegram.api.data.User} object.
     *
     * @return {@link telegram.api.data.response.UserResponse}
     */
    public UserResponse getMe();

    /**
     * Use this method to receive incoming updates using long polling
     * (<a href="http://en.wikipedia.org/wiki/Push_technology#Long_polling">Wiki</a>).
     * An Array of {@link telegram.api.data.Update} objects is returned.
     *
     * @param offest Identifier of the first update to be returned. Must be
     * greater by one than the highest among the identifiers of previously
     * received updates. By default, updates starting with the earliest
     * unconfirmed update are returned. An update is considered confirmed as
     * soon as getUpdates is called with an offset higher than its update_id.
     * The negative offset can be specified to retrieve updates starting from
     * -offset update from the end of the updates queue. All previous updates
     * will forgotten.
     * @param limit Limits the number of updates to be retrieved. Values between
     * 1—100 are accepted. Defaults to 100.
     * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e.
     * usual short polling
     * @return
     */
    public UpdateResponse getUpdates(
            @Param(name = "offset") long offest,
            @Param(name = "limit") int limit,
            @Param(name = "timeout") int timeout) throws BotException;

    /**
     * Use this method to specify a url and receive incoming updates via an
     * outgoing webhook. Whenever there is an update for the bot, we will send
     * an HTTPS POST request to the specified url, containing a JSON-serialized
     * Update. In case of an unsuccessful request, we will give up after a
     * reasonable amount of attempts.
     *
     * If you'd like to make sure that the Webhook request comes from Telegram,
     * we recommend using a secret path in the URL, e.g.
     * <a href="">https://www.example.com/&lt;token&gt;</a>. Since nobody else
     * knows your bot‘s token, you can be pretty sure it’s us.
     *
     * @param url Optional	HTTPS url to send updates to. Use an empty string to
     * remove webhook integration
     * @param certificate Optional	Upload your public key certificate so that
     * the root certificate in use can be checked. See our self-signed guide for
     * details.
     * @return
     */
    public Response setWebhook(
            @Param(name = "url") String url,
            @Param(name = "certificate") InputFile certificate
    );

    /**
     * Use this method to send text messages. Short version.<br/>On success, the
     * sent {@link telegram.api.data.Message} is returned.
     *
     * @param chat_id Unique identifier for the target chat or username of the
     * target channel (in the format @channelusername)
     * @param text Text of the message to be sent
     * @return {@link telegram.api.data.response.MessageResponse}
     */
    public MessageResponse sendMessage(
            String chat_id, //Yes
            String text //Yes
    );

    /**
     * Use this method to send text messages. Short version with long chat_id
     *
     * @param chat_id Unique identifier for the target chat
     * @param text Text of the message to be sent
     * @return {@link telegram.api.data.response.MessageResponse}
     */
    public MessageResponse sendMessage(
            @Param(name = "chat_id") long chat_id, //Yes
            @Param(name = "text") String text //Yes
    );

    /**
     * Use this method to send text messages. Full version
     *
     * @param chat_id Unique identifier for the target chat or username of the
     * target channel (in the format @channelusername)
     * @param text Text of the message to be sent
     * @param parse_mode
     * @param disable_web_page_preview
     * @param disable_notification
     * @param reply_to_message_id
     * @param reply_markup
     * @return {@link telegram.api.data.response.MessageResponse}
     */
    public MessageResponse sendMessage(
            @Param(name = "chat_id") String chat_id, //Yes
            @Param(name = "text") String text, //Yes
            @Param(name = "parse_mode") String parse_mode, //Optional
            @Param(name = "disable_web_page_preview") Boolean disable_web_page_preview, //Optional
            @Param(name = "disable_notification") Boolean disable_notification, //Optional
            @Param(name = "reply_to_message_id") Integer reply_to_message_id, //Optional
            @Param(name = "reply_markup") Object reply_markup //Optional
    );

    /**
     * Use this method to forward messages of any kind. On success, the sent
     * Message is returned.
     *
     * @param chat_id Unique identifier for the target chat or username of the
     * target channel (in the format @channelusername)
     * @param from_chat_id Unique identifier for the chat where the original
     * message was sent (or channel username in the format @channelusername)
     * @param disable_notificationSends the message silently. iOS users will not
     * receive a notification, Android users will receive a notification with no
     * sound.
     * @param message_id Unique message identifier
     * @return {@link telegram.api.data.response.MessageResponse}
     */
    public MessageResponse forwardMessage(
            @Param(name = "chat_id") String chat_id, //Yes
            @Param(name = "from_chat_id") String from_chat_id, //Yes
            @Param(name = "disable_notification") Boolean disable_notification, //Optional
            @Param(name = "message_id") int message_id //Yes
    );

    /**
     * Use this method to send .webp stickers. On success, the sent Message is
     * returned.
     *
     * @param chat_id Unique identifier for the target chat
     * @param sticker Sticker to send. You can pass a file_id as String
     * to resend a sticker that is already on the Telegram servers
     */ 
    public MessageResponse sendSticker(
            @Param(name = "chat_id") long chat_id, //Yes
            @Param(name = "sticker") String sticker //Yes
    );

    /**
     * Use this method to send .webp stickers. On success, the sent Message is
     * returned.
     *
     * @param chat_id Unique identifier for the target chat or username of the
     * target channel (in the format@channelusername)
     * @param sticker Sticker to send. You can either pass a file_id as String
     * to resend a sticker that is already on the Telegram servers, or upload a
     * new sticker using multipart/form-data.
     * @param disable_notification Sends the message silently. iOS users will
     * not receive a notification, Android users will receive a notification
     * with no sound.
     * @param reply_to_message_id If the message is a reply, ID of the original
     * message
     * @param reply_markup Additional interface options. A JSON-serialized
     * object for an inline keyboard, custom reply keyboard, instructions to
     * hide reply keyboard or to force a reply from the user.
     * @return {@link telegram.api.data.response.MessageResponse}
     */
    public MessageResponse sendSticker(
            @Param(name = "chat_id") String chat_id, //Yes
            @Param(name = "sticker") String sticker, //Yes
            @Param(name = "disable_notification") Boolean disable_notification, //Optional
            @Param(name = "reply_to_message_id") Integer reply_to_message_id, //Optional
            @Param(name = "reply_markup") Object reply_markup //Optional
    );

}
