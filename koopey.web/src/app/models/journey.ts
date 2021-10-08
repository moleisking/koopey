import { Asset } from "../models/asset";
import { BaseModel } from "./baseModel";
import { User } from "../models/user";

export class Jouney extends BaseModel {
  public users: Array<User> = new Array<User>();
  public assets: Array<Asset> = new Array<Asset>();
}
