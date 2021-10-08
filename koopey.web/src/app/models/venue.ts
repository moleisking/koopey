import { BaseModel } from "./baseModel";
import { Location } from "../models/location";
import { User } from "../models/user";

export class Venue extends BaseModel {
  public locations: Array<Location> = new Array<Location>();
  public users: Array<User> = new Array<User>();
}
