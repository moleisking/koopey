import { Audit } from "./base/audit";
import { MessageType } from "./type/MessageType";
import { ModelHelper } from "../helpers/ModelHelper";
import { User } from "../models/user";

export class Message extends Audit {
  public sender: User = new User();
  public senderId?: string;
  public receiver: User = new User();
  public receiverId?: string;

  public static countNotRecived(messages: Array<Message>): Number {
    return ModelHelper.count(MessageType.NotRecieved, messages);
  }

  public static countNotSent(messages: Array<Message>): Number {
    return ModelHelper.count(MessageType.NotRecieved, messages);
  }
}
