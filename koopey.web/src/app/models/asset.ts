import { Advert } from "../models/advert";
import { AssetType } from "./type/AssetType";
import { BaseModel } from "./baseModel";
import { Environment } from "src/environments/environment";
import { File } from "../models/file";
import { Image } from "../models/image";
import { Location } from "../models/location";
import { Review } from "../models/review";
import { Tag } from "../models/tag";
import { User } from "../models/user";

export class Asset extends BaseModel {
  constructor() {
    super();
    super.type = AssetType.Product;
  }
  public currency: string = Environment.Default.Currency;
  public available: boolean = true;
  public manufactureDate: Date = new Date("1900-01-01");
  public value: number = 0;
  public quantity: number = 1;
  public width: number = 0;
  public height: number = 0;
  public length: number = 0;
  public weight: number = 0;
  public distance: number = 10000;
  public buyer: User = new User();
  public seller: User = new User();
  public advert!: Advert ;
  public file!: File;
  public destinations: Array<Location> = new Array<Location>();
  public sources: Array<Location> = new Array<Location>();
  public images: Array<Image> = new Array<Image>();
  public reviews: Array<Review> = new Array<Review>();
  public tags: Array<Tag> = new Array<Tag>();
}
