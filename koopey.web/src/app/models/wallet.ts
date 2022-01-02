import { Audit } from "./base/audit";

export class Wallet extends Audit {
  public balance: number = 0;
  public userId: string = "";
  public code: string = "";
  public identifier: string = "";
  public currency: string = "";
  public pubKey: string = "";
  public prvKey: string = "";
}
