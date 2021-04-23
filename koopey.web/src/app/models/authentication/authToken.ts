const SHA256 = require("crypto-js/sha256");
import { UUID } from "angular2-uuid";

export class AuthToken {
  public id: string = UUID.UUID();
  public value: number = 0;
  public token: string = "";
  public pubKey: string = "";
  public prvKey: string = "";
  public name: string = "";
  public type: string = "";
  public hash: string = "";
  public createTimeStamp: number = Date.now();
  public readTimeStamp: number = 0;
  public updateTimeStamp: number = 0;
  public deleteTimeStamp: number = 0;

  public static isEmpty(authToken: AuthToken): Boolean {
    if (authToken && authToken.id && authToken.prvKey && authToken.type) {
      return false;
    } else {
      return true;
    }
  }
}
