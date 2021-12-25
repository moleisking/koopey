import { Advert } from "../models/advert";
import { AssetType } from "./type/AssetType";
import { BaseModel } from "./baseModel";
import { Environment } from "src/environments/environment";
import { Location } from "../models/location";
import { Review } from "../models/review";
import { Tag } from "../models/tag";
import { User } from "../models/user";

export class Asset extends BaseModel {
  constructor() {
    super();
    super.type = AssetType.Product;
  }
  public firstImage: string = "";
  public secondImage: string = "";
  public thirdImage: string = "";
  public fourthImage: string = "";
  public currency: string = Environment.Default.Currency;
  public serial: string = "";
  public available: boolean = true;
  public altitude: number = 0;
  public average: number = 0;
  public height: number = 0;
  public latitude: number = 0;
  public longitude: number = 0;
  public manufactureDate: Date = new Date("1900-01-01");
  public negative: number = 0;
  public positive: number = 0;
  public quantity: number = 1;
  public value: number = 0;
  public length: number = 0;
  public weight: number = 0;
  public width: number = 0;
  public distance: number = 10000;
  public buyer: User = new User();
  public buyerId?: string;
  public seller: User = new User();
  public sellerId?: string;
  public advert!: Advert ;
  public data!: string;
  public destinations: Array<Location> = new Array<Location>();
  public sources: Array<Location> = new Array<Location>();
  public reviews: Array<Review> = new Array<Review>();
  public tags: Array<Tag> = new Array<Tag>();
}
