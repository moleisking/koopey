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
  public type: string = "once";
  public secret: string = "secret";
  public guid: string = UUID.UUID();
  public location: Location = new Location();
  public start: number = Date.now();
  public end: number = Date.now();

  public static isEmpty(event: Appointment): boolean {
    if (
      event &&
      event.description &&
      event.name &&
      event.start != 0 &&
      event.end != 0 &&
      event.users.length >= 1 &&
      event.type.match("once|hour|day|week|month|year")
    ) {
      return false;
    } else {
      return true;
    }
  }
}
