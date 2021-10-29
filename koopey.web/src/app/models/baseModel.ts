const SHA256 = require("crypto-js/sha256");
import { UUID } from "angular2-uuid";
import { Environment } from "src/environments/environment";

export class BaseModel {
  public id: string = UUID.UUID();
  public name: string = "";
  public type: string = "none";
  public description: string = "";
  public publishDate: number = Date.now();
  public timeZone: string = Environment.Default.TimeZone;
  public hash?: string;
}
