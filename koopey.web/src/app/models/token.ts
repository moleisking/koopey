const SHA256 = require("crypto-js/sha256");
import { UUID } from "angular2-uuid";

export class Token {
  public id: string = UUID.UUID();
  public value: number = 0;
  public pubKey: string = "";
  public prvKey: string = "";
  public name: string = "";
  public type: string = "";
  public hash: string = "";
  public createTimeStamp: number = Date.now();
  public readTimeStamp: number = 0;
  public updateTimeStamp: number = 0;
  public deleteTimeStamp: number = 0;

  public static isEmpty(token: Token): Boolean {
    if (token && token.id && token.prvKey && token.type) {
      return false;
    } else {
      return true;
    }
  }
}
