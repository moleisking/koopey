import { v7 as uuidv7 } from "uuid";
import { Environment } from "../../../environments/environment";

export class Base {
  public id: string = uuidv7(); 
  public name: string = "";
  public type: string = "none";
  public description: string = "";
  public timeZone: string = Environment.Default.TimeZone;
  public createTimeStamp: number = Date.now();
  public readimeStamp: number = 0;
  public updateTimeStamp: number = 0;
  public readTimeStamp: number = 0;

  public isEmpty(): boolean {
    return this.name.length == 0 && this.type.length == 0 && this.description.length == 0 && this.id.length == 0 ? true : false;
  }

}
