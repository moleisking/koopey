import { Asset } from "../models/asset";
import { Base } from "./base/base";
import { Environment } from "./../../environments/environment";
import { Location } from "../models/location";
import { User } from "../models/user";
import { Advert } from "./advert";

export class Transaction extends Base {
  public advertId?: string;
  public advert!: Advert ;
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
  public start?: Date = new Date("1900-01-01");
  public end?: Date;
}
