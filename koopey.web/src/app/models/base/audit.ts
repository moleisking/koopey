const SHA256 = require("crypto-js/sha256");
import { Environment } from "src/environments/environment";
import { Base } from "./base";

export class Audit extends Base {

  public name: string = "";
  public type: string = "none";
  public description: string = "";
  public timeZone: string = Environment.Default.TimeZone;

  public isEmpty(): boolean {
    return this.name.length == 0 && this.type.length == 0 && this.description.length == 0 ? true : false;
  }

}