const SHA256 = require("crypto-js/sha256");
import { Config } from "../config/settings";
import { Location } from "../models/location";
import { Tag } from "../models/tag";
import { UUID } from "angular2-uuid";

export class File {
  public id: string = UUID.UUID();
  public description: string = "";
  public size: number = 0;
  public type: string = "";
  public name: string = "";
  public path: string = "";
  public data: string = "";
  public hash: string = "";
  public timeZone: string = Config.default_time_zone;
  public createTimeStamp: number = Date.now();
  public readTimeStamp: number = 0;
  public updateTimeStamp: number = 0;
  public deleteTimeStamp: number = 0;

  public static isEmpty(file: File): boolean {
    if (file && file.type && file.name && file.size > 0) {
      return false;
    } else {
      return true;
    }
  }
}
