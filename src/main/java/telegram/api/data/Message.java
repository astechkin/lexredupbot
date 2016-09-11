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

import java.util.List;

/**
 *
 * @author user
 */
public class Message {
Integer	message_id;	//Unique message identifier
User from; //Optional. Sender, can be empty for messages sent to channels
Integer date; //Date the message was sent in Unix time
Chat chat; //Conversation the message belongs to
User forward_from; //Optional. For forwarded messages, sender of the original message
Chat forward_from_chat; //Optional. For messages forwarded from a channel, information about the original channel
Integer forward_date; //Optional. For forwarded messages, date the original message was sent in Unix time
Message reply_to_message; //Optional. For replies, the original message. Note that the Message object in this field will not contain further reply_to_message fields even if it itself is a reply.
Integer edit_date; //Optional. Date the message was last edited in Unix time
String text; //Optional. For text messages, the actual UTF-8 text of the message, 0-4096 characters.
List<MessageEntity> entities; //Optional. For text messages, special entities like usernames, URLs, bot commands, etc. that appear in the text
Audio audio; //Optional. Message is an audio file, information about the file
Document document; //Optional. Message is a general file, information about the file
List<PhotoSize> photo; //Optional. Message is a photo, available sizes of the photo
Sticker sticker; //Optional. Message is a sticker, information about the sticker
Video video; //Optional. Message is a video, information about the video
Voice voice; //Optional. Message is a voice message, information about the file
String caption; //Optional. Caption for the document, photo or video, 0-200 characters
Contact contact; //Optional. Message is a shared contact, information about the contact
Location location; //Optional. Message is a shared location, information about the location
Venue venue; //Optional. Message is a venue, information about the venue
User new_chat_member; //Optional. A new member was added to the group, information about them (this member may be the bot itself)
User left_chat_member; //Optional. A member was removed from the group, information about them (this member may be the bot itself)
String new_chat_title; //Optional. A chat title was changed to this value
List<PhotoSize> new_chat_photo; //Optional. A chat photo was change to this value
Boolean delete_chat_photo; //Optional. Service message: the chat photo was deleted
Boolean group_chat_created; //Optional. Service message: the group has been created
Boolean supergroup_chat_created; //Optional. Service message: the supergroup has been created. This field can‘t be received in a message coming through updates, because bot can’t be a member of a supergroup when it is created. It can only be found in reply_to_message if someone replies to a very first message in a directly created supergroup.
Boolean channel_chat_created; //Optional. Service message: the channel has been created. This field can‘t be received in a message coming through updates, because bot can’t be a member of a channel when it is created. It can only be found in reply_to_message if someone replies to a very first message in a channel.
Integer migrate_to_chat_id; //Optional. The group has been migrated to a supergroup with the specified identifier. This number may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it. But it smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
Integer migrate_from_chat_id; //Optional. The supergroup has been migrated from a group with the specified identifier. This number may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it. But it smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
Message pinned_message; //Optional. Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.

    

    public Integer getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getForward_from() {
        return forward_from;
    }

    public void setForward_from(User forward_from) {
        this.forward_from = forward_from;
    }

    public Chat getForward_from_chat() {
        return forward_from_chat;
    }

    public void setForward_from_chat(Chat forward_from_chat) {
        this.forward_from_chat = forward_from_chat;
    }

    public Integer getForward_date() {
        return forward_date;
    }

    public void setForward_date(Integer forward_date) {
        this.forward_date = forward_date;
    }

    public Message getReply_to_message() {
        return reply_to_message;
    }

    public void setReply_to_message(Message reply_to_message) {
        this.reply_to_message = reply_to_message;
    }

    public Integer getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(Integer edit_date) {
        this.edit_date = edit_date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MessageEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<MessageEntity> entities) {
        this.entities = entities;
    }


    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<PhotoSize> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotoSize> photo) {
        this.photo = photo;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public void setSticker(Sticker sticker) {
        this.sticker = sticker;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public User getNew_chat_member() {
        return new_chat_member;
    }

    public void setNew_chat_member(User new_chat_member) {
        this.new_chat_member = new_chat_member;
    }

    public User getLeft_chat_member() {
        return left_chat_member;
    }

    public void setLeft_chat_member(User left_chat_member) {
        this.left_chat_member = left_chat_member;
    }

    public String getNew_chat_title() {
        return new_chat_title;
    }

    public void setNew_chat_title(String new_chat_title) {
        this.new_chat_title = new_chat_title;
    }

    public List<PhotoSize> getNew_chat_photo() {
        return new_chat_photo;
    }

    public void setNew_chat_photo(List<PhotoSize> new_chat_photo) {
        this.new_chat_photo = new_chat_photo;
    }

    public Boolean getDelete_chat_photo() {
        return delete_chat_photo;
    }

    public void setDelete_chat_photo(Boolean delete_chat_photo) {
        this.delete_chat_photo = delete_chat_photo;
    }

    public Boolean getGroup_chat_created() {
        return group_chat_created;
    }

    public void setGroup_chat_created(Boolean group_chat_created) {
        this.group_chat_created = group_chat_created;
    }

    public Boolean getSupergroup_chat_created() {
        return supergroup_chat_created;
    }

    public void setSupergroup_chat_created(Boolean supergroup_chat_created) {
        this.supergroup_chat_created = supergroup_chat_created;
    }

    public Boolean getChannel_chat_created() {
        return channel_chat_created;
    }

    public void setChannel_chat_created(Boolean channel_chat_created) {
        this.channel_chat_created = channel_chat_created;
    }

    public Integer getMigrate_to_chat_id() {
        return migrate_to_chat_id;
    }

    public void setMigrate_to_chat_id(Integer migrate_to_chat_id) {
        this.migrate_to_chat_id = migrate_to_chat_id;
    }

    public Integer getMigrate_from_chat_id() {
        return migrate_from_chat_id;
    }

    public void setMigrate_from_chat_id(Integer migrate_from_chat_id) {
        this.migrate_from_chat_id = migrate_from_chat_id;
    }

    public Message getPinned_message() {
        return pinned_message;
    }

    public void setPinned_message(Message pinned_message) {
        this.pinned_message = pinned_message;
    }
}
