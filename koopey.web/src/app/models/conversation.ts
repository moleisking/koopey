import { Audit } from "./base/audit";
import { User } from "../models/user";
import { Message } from "../models/message";

export class Conversation extends Audit {
  public users: Array<User> = new Array<User>();
  public messages: Array<Message> = new Array<Message>();
  public sent: boolean = false;
  public recieved: boolean = false;
}
