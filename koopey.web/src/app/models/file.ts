const SHA256 = require("crypto-js/sha256");

import { BaseModel } from "./baseModel";

export class File extends BaseModel {
  public size: number = 0;
  public type: string = "";
  public path: string = "";
  public data: string = "";
  public hash: string = "";

  public static isEmpty(file: File): boolean {
    if (file && file.type && file.name && file.size > 0) {
      return false;
    } else {
      return true;
    }
  }
}
