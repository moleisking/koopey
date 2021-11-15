import { BaseModel } from "./baseModel";
import { Environment } from "src/environments/environment";
import { Tag } from "../models/tag";

export class Search extends BaseModel {
  public eventId: string = "";
  public userId: string = "";
  public productId: string = "";
  public transactionId: string = "";
  public period: string = Environment.Default.Period;
  public currency: string = Environment.Default.Currency;
  public alias: string = "";
  public start: Date = new Date(2020, 12, 12); // - 2419200000; //86400000 (day) // 604800000 (week) // 2419200000 (28 days)
  public end: Date = new Date(2021, 12, 1); // + 2419200000;
  public max: number = 5000;
  public min: number = 0;
  public measurement: string = Environment.Default.Measurement;
  public latitude: number = 0;
  public longitude: number = 0;
  public radius: number = Environment.Default.Radius;
  public reference: string = "";
  public tags: Array<Tag> = new Array<Tag>();

  public static isEmpty(search: Search): boolean {
    if (search.latitude && search.longitude && search.tags.length > 0) {
      return true;
    } else {
      return false;
    }
  }
}
