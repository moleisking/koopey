import { Asset } from "../models/asset";
import { BaseModel } from "./baseModel";
import { User } from "../models/user";
import { Location } from "../models/location";

export class Journey extends BaseModel {
  public driver: User = new User();
  public location: Location = new Location();
  public passanger: User = new User();
  public vehicle: Asset = new Asset();
}
