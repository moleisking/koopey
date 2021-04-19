import { UUID } from "angular2-uuid";

export class Advert {
  public id: string = UUID.UUID();
  public hash: string = "";
  public type: string = "null";
  public startTimeStamp: number = 0;
  public endTimeStamp: number = 0;
  public createTimeStamp: number = Date.now();
  public readTimeStamp: number = 0;
  public updateTimeStamp: number = 0;
  public deleteTimeStamp: number = 0;
}
