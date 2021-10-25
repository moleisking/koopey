const SHA256 = require("crypto-js/sha256");
import { Location } from "../models/location";
import { User } from "../models/user";
import { UUID } from "angular2-uuid";
import { Asset } from "../models/asset";
import { BaseModel } from "./baseModel";

export class Transaction extends BaseModel {
  public asset: Asset = new Asset();
  public customer: User = new User();
  public provider: User = new User();
  public reference: string = UUID.UUID();
  public currency: string = "tok";
  public secret: string = "secret";
  public totalValue: number = 1;
  public quantity: number = 1;
  public itemValue: number = 1;
  public destination: Location = new Location();
  public source: Location = new Location();
  public start: number = Date.now();
  public end: number = Date.now();
}
