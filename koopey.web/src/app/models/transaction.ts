const SHA256 = require("crypto-js/sha256");
import { Location } from "../models/location";
import { User } from "../models/user";
import { UUID } from "angular2-uuid";
import { Asset } from "../models/asset";
import { BaseModel } from "./baseModel";

export class Transaction extends BaseModel {
  public asset: Asset = new Asset();
  public buyer: User = new User();
  public seller: User = new User();
  public reference: string = UUID.UUID();
  public currency: string = "tok";
  public secret: string = "secret";
  public value: number = 1;
  public quantity: number = 1;
  public total: number = 1;
  public destination: Location = new Location();
  public source: Location = new Location();
  public start: number = Date.now();
  public end: number = Date.now();
}
