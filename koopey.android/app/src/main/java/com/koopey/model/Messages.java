package com.koopey.model;

import com.koopey.model.base.BaseCollection;

public class Messages extends BaseCollection<Message> {

    public static final String MESSAGES_FILE_NAME = "messages.dat";

    public int countUnread() {
        int read = 0;
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).isRead()){
                read++;
            }
        }
        return read;
    }

    public int countUnsent() {
        int sent = 0;
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).isSent()){
                sent++;
            }
        }
        return sent;
    }



   /* public Message getLastConversationMessage(String otherUserId) {
        //NOTE: Backend Result return LIFO
        Message result = null;
        for (int i = 0; i < this.messages.size(); i++) {
            String fromId = this.messages.get(i).fromId;
            String toId = this.messages.get(i).toId;
            if (toId.equals(otherUserId) || fromId.equals(otherUserId)) {
                result = this.messages.get(i);
                break;
            }
        }
        return result;
    }*/

   /* public Message getFirstConversationMessage(String otherUserId) {
        //NOTE: Backend Result return LIFO
        Message result = null;
        for (int i = 0; i < this.messages.size(); i++) {
            String fromId = this.messages.get(i).fromId;
            String toId = this.messages.get(i).toId;
            if (toId.equals(otherUserId) || fromId.equals(otherUserId)) {
                result = this.messages.get(i);
            }
        }
        return result;
    }*/

}