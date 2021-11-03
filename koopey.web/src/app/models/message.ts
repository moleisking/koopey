import { BaseModel } from "./baseModel";
import { User } from "../models/user";
import { ModelHelper } from "../helpers/ModelHelper";
import { MessageType } from "./type/MessageType";

export class Message extends BaseModel {
  public users: Array<User> = new Array<User>();
  public sent: boolean = false;
  public recieved: boolean = false;

  public static countNotRecived(messages: Array<Message>): Number {
    return ModelHelper.count(MessageType.NotRecieved, messages);
  }

  public static countNotSent(messages: Array<Message>): Number {
    return ModelHelper.count(MessageType.NotRecieved, messages);
  }
}
