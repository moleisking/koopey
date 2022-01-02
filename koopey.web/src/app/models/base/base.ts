const SHA256 = require("crypto-js/sha256");
import { UUID } from "angular2-uuid";
import { Environment } from "src/environments/environment";

export class Base {
  public id: string = UUID.UUID(); 
  public publishDate: number = Date.now();
  public hash?: string;
}
