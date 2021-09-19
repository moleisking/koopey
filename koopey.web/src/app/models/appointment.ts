import { BaseModel } from "./baseModel";
import { Location } from "../models/location";
import { Transaction } from "../models/transaction";
import { User } from "../models/user";
import { UUID } from "angular2-uuid";

export enum AppointmentType {
  Once = "once",
  Hour = "hour",
  Day = "day",
  Week = "week",
  Month = "month",
  Year = "year",
}

export class Appointment extends BaseModel {
  public users: Array<User> = new Array<User>();
  public transactions: Array<Transaction> = new Array<Transaction>();
  public name: string = "";
  public description: string = "";
  public type: string = "once";
  public timeZone: string = "Etc/UTC";
  public secret: string = "secret";
  public hash: string = "";
  public guid: string = UUID.UUID();
  public location: Location = new Location();
  public startTimeStamp: number = Date.now();
  public endTimeStamp: number = Date.now();
  public createTimeStamp: number = Date.now();
  public readTimeStamp: number = 0;
  public updateTimeStamp: number = 0;
  public deleteTimeStamp: number = 0;

  public static isEmpty(event: Appointment): boolean {
    if (
      event &&
      event.description &&
      event.name &&
      event.startTimeStamp != 0 &&
      event.endTimeStamp != 0 &&
      event.users.length >= 1 &&
      event.type.match("once|hour|day|week|month|year")
    ) {
      return false;
    } else {
      return true;
    }
  }
}
