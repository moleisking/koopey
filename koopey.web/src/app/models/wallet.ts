import { Base } from "./base/base";

export class Wallet extends Base {
  public balance: number = 0;
  public userId: string = "";
  public code: string = "";
  public identifier: string = "";
  public currency: string = "";
  public pubKey: string = "";
  public prvKey: string = "";
}
