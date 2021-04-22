const SHA256 = require("crypto-js/sha256");
import { User } from "../models/user";
import { UUID } from "angular2-uuid";

export class Message {
  public id: string = UUID.UUID();
  public users: Array<User> = new Array<User>(); //sender,receiver
  public sent: boolean = false;
  public delivered: boolean = false;
  public subject: string = "";
  public text: string = "";
  public read: boolean = false;
  public archived: boolean = false;
  public language: string = "en";
  public hash: string = "";
  public createTimeStamp: number = Date.now();
  public readTimeStamp: number = 0;
  public updateTimeStamp: number = 0;
  public deleteTimeStamp: number = 0;

  public static isEmpty(message: Message): Boolean {
    if (
      message &&
      message.users &&
      message.users.length > 1 &&
      !User.isEmpty(message.users[0]) &&
      !User.isEmpty(message.users[1]) &&
      message.text
    ) {
      return false;
    } else {
      return true;
    }
  }

  public static countUnread(messages: Array<Message>): Number {
    var unread: number = 0;
    if (messages && messages.length > 0) {
      for (var i = 0; i < messages.length; i++) {
        if (messages[i] && !messages[i].read) {
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
        if (messages[i] && !messages[i].sent) {
          unsent++;
        }
      }
    }
    return unsent;
  }
}
