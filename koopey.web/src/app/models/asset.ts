const SHA256 = require("crypto-js/sha256");
import { Advert } from "../models/advert";
import { Config } from "../config/settings";
import { File } from "../models/file";
import { Image } from "../models/image";
import { Location } from "../models/location";
import { Review } from "../models/review";
import { Tag } from "../models/tag";
import { User } from "../models/user";
import { UUID } from "angular2-uuid";

export class Asset {
  public id: string = UUID.UUID();
  public title: string = "";
  public type: string = "product";
  public description: string = "";
  public dimensiontUnit: string = Config.default_dimension_unit;
  public weightUnit: string = Config.default_weight_unit;
  public currency: string = Config.default_currency;
  public available: boolean = true;
  public manufactureDate: number = 0;
  public createTimeStamp: number = Date.now();
  public readTimeStamp: number = 0;
  public updateTimeStamp: number = 0;
  public deleteTimeStamp: number = 0;
  public value: number = 0;
  public quantity: number = 1;
  public width: number = 0;
  public height: number = 0;
  public length: number = 0;
  public weight: number = 0;
  public distance: number = 10000;
  public hash: string = "";
  public timeZone: string = Config.default_time_zone;
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

  /* public static isUpdate(asset: Asset): boolean {
        if (asset.currency &&
            asset.id &&  
            asset.title &&   
            asset.images.length > 0 &&
            asset.location &&
            asset.tags.length > 1 &&
            asset.user &&
            asset.value &&
            asset.value > 0) {
            return true;
        } else {
            return false;
        }
    }*/

  /*public static convertDistanceToString(distance: number): string {
        if (distance <= 100) {
            return "100m";
        } else if (distance > 100 && distance < 1000) {
            return distance + "m";
        } else {
            return Math.round(distance / 1000) + "km";
        }
    }*/

  /*public static getCurrentDistanceAsString(asset: Asset): string {
        return this.convertDistanceToString(asset.currentDistance);
    }

    public static getRegisteredDistanceAsString(asset: Asset): string {
        return this.convertDistanceToString(asset.registeredDistance);
    }*/

  /*public static simplify(asset: Asset): Asset {
        var simpleAsset = asset;
        simpleAsset.advert = null;
        simpleAsset.file = null;
        simpleAsset.images = [];
        simpleAsset.location = null;
        simpleAsset.tags = [];  
        simpleAsset.user = User.simplify (asset.user);
        return simpleAsset;
    }

    public static clean(asset: Asset): Asset {
        if (asset.advert == null) {
            asset.advert = new Advert();
        }      
        if (asset.file == null) {
            asset.file = new File();
        }  
        if (asset.tags == null) {
            asset.tags = new Array<Tag>();
        }
        if (asset.user == null) {
            asset.user = new User();
        }
        return asset;
    }

    

    public static sort(assets: Array<Asset>): Array<Asset> {
        //Sort adverts
        for (var i = 0; i < assets.length; i++) {
            var asset = assets[i];
            if ((asset && asset.advert) && (asset.advert.startTimeStamp <= Date.now())
                && (Date.now() <= asset.advert.endTimeStamp)) {
                //Push to the front of queue               
                assets.splice(i, 1);
                assets.unshift(asset);
            }
        }
        return assets;
    }*/
}
