const SHA256 = require("crypto-js/sha256");
import { BaseModel } from "./baseModel";
import { Environment } from "src/environments/environment";
import { Location } from "../models/location";
import { UUID } from "angular2-uuid";
import { Review } from "../models/review";
import { Score } from "../models/score";
import { Wallet } from "../models/wallet";

export enum UserType {
  Buyer = "buyer",
  Receiver = "receiver",
  Seller = "seller",
  Sender = "sender",
}

export class User extends BaseModel {
  public ip: string = "";
  public device: string = "";
  public alias: string = "";
  public avatar: string = "";
  public birthday: number = 0;
  public currency: string = Environment.Default.Currency;
  public education: string = "";
  public career: string = "";
  public email: string = "";
  public language: string = Environment.Default.Language;
  public mobile: string = "";
  public password: string = "";
  public secret: string = "";

  //public player: PlayerType = PlayerType.Grey;
  public score: number = 0;
  public measure: string = Environment.Default.Measure;
  public authenticated: boolean = false;
  public track: boolean = true;
  public gdpr: boolean = true;
  public cookies: boolean = true;
  public distance: number = 10000;
  public notify: boolean = false;
  public guid: string = UUID.UUID();

  public locations: Array<Location> = new Array<Location>();
  public contacts: Array<User> = new Array<User>();
  public scores: Array<Score> = new Array<Score>();
  public reviews: Array<Review> = new Array<Review>();
  public wallets: Array<Wallet> = new Array<Wallet>();

  public static compareHash(userA: User, userB: User): boolean {
    return User.toHash(userA) == User.toHash(userB) ? true : false;
  }

  public static toHash(user: User): string {
    return SHA256(
      user.id +
        user.type +
        user.alias +
        user.name +
        user.birthday +
        user.currency +
        user.description +
        user.education +
        user.career +
        user.email +
        user.language +
        user.mobile +
        user.password +
        user.track +
        user.gdpr +
        user.cookies
    ).toString();
  }

  public static isAuthenticated(user: User): boolean {
    if (!User.isEmpty(user) && user.authenticated) {
      return true;
    } else {
      return false;
    }
  }

  public static isCreate(user: User): boolean {
    if (
      user.id &&
      user.alias &&
      user.alias.length >= 5 &&
      user.birthday != 0 &&
      user.email &&
      user.email.length >= 5 &&
      user.email.match(
        "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
      ) &&
      user.locations &&
      user.name &&
      user.name.length >= 5 &&
      user.password &&
      user.password.length >= 5
    ) {
      return true;
    } else {
      return false;
    }
  }

  public static isBuyer(user: User): boolean {
    if (user && user.type === "buyer") {
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
    if (user && user.authenticated && user.gdpr) {
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

  public static isUpdate(user: User): boolean {
    if (
      user.id &&
      user.alias &&
      user.alias.length >= 5 &&
      user.name &&
      user.locations.length > 0
    ) {
      return true;
    } else {
      return false;
    }
  }
}
