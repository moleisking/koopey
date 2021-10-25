import { BaseModel } from "./baseModel";

export class Wallet extends BaseModel {
  public balance: number = 0;
  public userId: string = "";
  public code: string = "";
  public identifier: string = "";
  public currency: string = "";
  public pubKey: string = "";
  public prvKey: string = "";
}
