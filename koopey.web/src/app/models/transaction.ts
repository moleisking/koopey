import { Asset } from "../models/asset";
import { BaseModel } from "./baseModel";
import { Environment } from "src/environments/environment";
import { Location } from "../models/location";
import { User } from "../models/user";

export class Transaction extends BaseModel {
  public asset?: Asset;
  public assetId?: string;
  public buyer?: User;
  public buyerId?: string;
  public seller?: User;
  public sellerId?: string;
  public reference: string = "";
  public currency: string = Environment.Default.Currency;
  public value: number = 0;
  public quantity: number = 0;
  public total: number = 0;
  public destination?: Location;
  public destinationId?: string;
  public source?: Location;
  public sourceId?: string;
  public start: number = Date.now();
  public end: number = Date.now() + 30 * 24 * 60 * 60;
}
