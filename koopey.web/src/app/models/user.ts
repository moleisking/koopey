import { Asset } from "./asset";
import { Base } from "./base/base";
import { Environment } from "src/environments/environment";
import { Location } from "../models/location";
import { v7 as uuidv7 } from "uuid";
import { Score } from "../models/score";
import { Wallet } from "../models/wallet";

export class User extends Base {
  public ip: string = "";
  public device: string = "";
  public alias: string = "";
  public avatar: string = "";
  public birthday: Date = new Date("1900-01-01");
  public currency: string = Environment.Default.Currency;
  public education: string = "";
  public career: string = "";
  public email: string = "";
  public language: string = Environment.Default.Language;
  public address: string = "";
  public altitude: number = 0;
  public average: number = 0;
  public latitude: number = 0;
  public longitude: number = 0;
  public positive: number = 0;
  public negative: number = 0;
  public mobile: string = "";
  public password: string = "";
  public secret: string = "";
  public score: number = 0;
  public serial: string = "";
  public measurement: string = Environment.Default.Measurement;
  public track: boolean = true;
  public gdpr: boolean = false;
  public cookie: boolean = true;
  public distance: number = 10000;
  public notify: boolean = false;
  public verify: boolean = false;
  public guid: string = uuidv7();
  public purchases: Array<Asset> = new Array<Asset>();
  public sales: Array<Asset> = new Array<Asset>();
  public collections: Array<Location> = new Array<Location>();
  public deliveries: Array<Location> = new Array<Location>();
  public contacts: Array<User> = new Array<User>();
  public scores: Array<Score> = new Array<Score>();
  public wallets: Array<Wallet> = new Array<Wallet>();

  public static isAuthenticated(user: User): boolean {
    if (!User.isEmpty(user) && user.verify) {
      return true;
    } else {
      return false;
    }
  }

  public static isEmpty(user: User): boolean {
    if (
      user.alias &&
      user.name &&
      user.alias.length >= 5 &&
      user.name.length >= 5
    ) {
      return false;
    } else {
      return true;
    }
  }

  public static isLegal(user: User): boolean {
    if (user && user.verify && user.gdpr) {
      return true;
    } else {
      return false;
    }
  }

  public static isMyUser(user: User): boolean {
    if (user && user.id == localStorage.getItem("id")) {
      return true;
    } else {
      return false;
    }
  }
}
