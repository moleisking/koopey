import { Base } from "./base/base";
import { MessageType } from "./type/MessageType";
import { ModelHelper } from "../helpers/ModelHelper";
import { User } from "../models/user";

export class Message extends Base {
  public sender: User = new User();
  public senderId?: string;
  public receiver: User = new User();
  public receiverId?: string;

  public static countRead(messages: Array<Message>): Number {
    return ModelHelper.count(MessageType.Read, messages);
  }

  public static countSent(messages: Array<Message>): Number {
    return ModelHelper.count(MessageType.Sent, messages);
  }

  public static countFail(messages: Array<Message>): Number {
    return ModelHelper.count(MessageType.Fail, messages);
  }
}
