const SHA256 = require("crypto-js/sha256");
import { User } from "../models/user";
import { UUID } from 'angular2-uuid';

export class Message {
    public id: string = UUID.UUID();
    public users: Array<User> = new Array<User>();//sender,receiver
    public sent: boolean = false;
    public delivered: boolean = false;
    public subject: string;
    public text: string;
    public read: boolean = false;
    public archived: boolean;
    public language: string;
    public hash: string;
    public createTimeStamp: number = Date.now();
    public readTimeStamp: number = 0;
    public updateTimeStamp: number = 0;
    public deleteTimeStamp: number = 0;

    public static isEmpty(message: Message): boolean {
        if (message &&
            message.users &&
            message.users.length > 1 &&
            !User.isEmpty(message.users[0]) &&
            !User.isEmpty(message.users[1]) &&           
            message.text) {
            return false;
        } else {
            return true;
        }
    }
   
    public static countUnread(messages: Array<Message>): Number {
        var unread: number = 0;
        if (messages && messages.length > 0) {
            for (var i = 0; i < messages.length; i++) {
                if (messages[i] &&
                    !messages[i].read) {
                    unread++;

                }
            }
        }
        return unread;
    }

    public static countUnsent(messages: Array<Message>): Number {
        var unsent: number = 0;
        if (messages && messages.length > 0) {
            for (var i = 0; i < messages.length; i++) {
                if (messages[i] &&
                    !messages[i].sent) {
                    unsent++;

                }
            }
        }
        return unsent;
    }

    public static contains(messages: Array<Message>, id: string): boolean {
        if (messages && messages.length > 0) {
            for (var i = 0; i < messages.length; i++) {
                //Exclude current 
                if (messages[i] &&
                    messages[i].id == id) {
                    //Current item is not unique                     
                    return true;
                } else if (i == messages.length - 1) {
                    //Last item and unique  
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static create(messages: Array<Message>, message: Message): Array<Message> {
        if (messages.length == 0 || !Message.contains(messages, message.id)) {
            messages.push(message);
            return messages;
        } else {
            return messages;
        }
    }

    public static read(messages: Array<Message>, message: Message): Message {
        if (messages && messages.length > 0) {
            for (var i = 0; i < messages.length; i++) {
                if (messages[i] &&
                    messages[i].id == message.id) {
                    return messages[i];
                }
            }
        }
    }

    public static update(messages: Array<Message>, message: Message): Array<Message> {
        if (messages && messages.length > 0) {
            for (var i = 0; i < messages.length; i++) {
                if (messages[i] &&
                    messages[i].id == message.id) {
                    messages[i] = message;
                    return messages;
                }
            }
        }
    }

    public static delete(messages: Array<Message>, message: Message): Array<Message> {
        if (messages && messages.length > 0) {
            for (var i = 0; i < messages.length; i++) {
                if (messages[i] &&
                    messages[i].id == message.id) {
                    messages.splice(i, 1);
                    return messages;
                }
            }
        }
    }
}
