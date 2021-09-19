import { Advert } from "../models/advert";
import { Environment } from "src/environments/environment";
import { File } from "../models/file";
import { Image } from "../models/image";
import { Location } from "../models/location";
import { Review } from "../models/review";
import { Tag } from "../models/tag";
import { User } from "../models/user";
import { BaseModel } from "./baseModel";

export class Asset extends BaseModel {
  public title: string = "";
  public dimensiontUnit: string = Environment.Default.DimensionUnit;
  public weightUnit: string = Environment.Default.WeightUnit;
  public currency: string = Environment.Default.Currency;
  public available: boolean = true;
  public manufactureDate: number = 0;
  public value: number = 0;
  public quantity: number = 1;
  public width: number = 0;
  public height: number = 0;
  public length: number = 0;
  public weight: number = 0;
  public distance: number = 10000;
  public hash: string = "";
  public timeZone: string = Environment.Default.TimeZone;
  public user: User = new User();
  public advert: Advert = new Advert();
  public file: File = new File();
  public location: Location = new Location();
  public images: Array<Image> = new Array<Image>();
  public reviews: Array<Review> = new Array<Review>();
  public tags: Array<Tag> = new Array<Tag>();

  public static isCreate(asset: Asset): boolean {
    if (
      asset.currency &&
      asset.id &&
      asset.title &&
      asset.images.length > 0 &&
      asset.location &&
      asset.tags.length > 1 &&
      asset.user &&
      asset.value &&
      asset.value > 0
    ) {
      return true;
    } else {
      return false;
    }
  }

  public static isEmpty(asset: Asset): boolean {
    if (
      asset.currency &&
      asset.title &&
      asset.images.length > 0 &&
      asset.location &&
      asset.tags.length > 0 &&
      asset.value &&
      asset.value > 0
    ) {
      return false;
    } else {
      return true;
    }
  }

  public static isProduct(asset: Asset): boolean {
    if (asset && asset.type && asset.type == "product") {
      return true;
    } else {
      return false;
    }
  }

  public static isService(asset: Asset): boolean {
    if (asset && asset.type && asset.type == "service") {
      return true;
    } else {
      return false;
    }
  }
}
