const SHA256 = require("crypto-js/sha256");
import { Advert } from "../models/advert";
import { Config } from "../config/settings";
//import { Game, PlayerType } from "../models/game";
import { Location } from "../models/location";
import { UUID } from "angular2-uuid";
import { Review } from "../models/review";
import { Score } from "../models/score";
import { Wallet } from "../models/wallet";

export enum UserType {
  Buyer = "buyer",
  Seller = "seller",
}

export class User {
  public id: string = UUID.UUID();
  public ip: string = "";
  public device: string = "";
  public alias: string = "";
  public avatar: string = "";
  public birthday: number = 0;
  public currency: string = Config.default_currency;
  public description: string = "";
  public education: string = "";
  public career: string = "";
  public email: string = "";
  public hash: string = "";
  public language: string = Config.default_language;
  public mobile: string = "";
  public name: string = "";
  public password: string = "";
  public secret: string = "";
  public type: string = "complete";
  public createTimeStamp: number = Date.now();
  //public player: PlayerType = PlayerType.Grey;
  public score: number = 0;
  public readTimeStamp: number = 0;
  public updateTimeStamp: number = 0;
  public deleteTimeStamp: number = 0;
  public measure: string = Config.default_measure;
  public authenticated: boolean = false;
  public track: boolean = true;
  public terms: boolean = true;
  public cookies: boolean = true;
  public distance: number = 10000;
  public timeZone: string = Config.default_time_zone;
  public notify: boolean = false;
  public guid: string = UUID.UUID();
  public oldEmail: string = "";
  public oldPassword: string = "";
  public newPassword: string = "";
  public newEmail: string = "";
  public location: Location = new Location();
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
        user.terms +
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
      user.location &&
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
    if (user && user.authenticated && user.terms) {
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

  public static isSeller(user: User): boolean {
    if (user && user.type === "seller") {
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
      user.location
    ) {
      return true;
    } else {
      return false;
    }
  }

  public static clone(user: User): User {
    var u: User = new User();
    u.id = user.id;
    u.alias = user.alias;
    u.name = user.name;
    //  u.player = user.player;
    u.score = user.score;
    u.scores = user.scores;
    return u;
  }

  public static clones(users: Array<User>): Array<User> {
    var us: Array<User> = new Array<User>();
    for (var i = 0; i < users.length; i++) {
      us.push(users[i]);
    }
    return us;
  }

  public static equals(user1: User, user2: User): boolean {
    if (!user1 || !user2) {
      return false;
    } else if (user1.id.match(user2.id)) {
      return true;
    } else {
      return false;
    }
  }

  public static equalsArray(users1: Array<User>, users2: Array<User>): boolean {
    if (!users1 || !users2) {
      return false;
    } else if (users1.length != users2.length) {
      return false;
    } else if (users1.length == users2.length) {
      for (var i = 0; i < users1.length; i++) {
        if (User.contains(users1, users2[i])) {
          if (i + 1 == users1.length) {
            return true;
          }
        } else {
          return false;
        }
      }
    }
    return false;
  }

  public static exclude(users: Array<User>, user: User): Array<User> {
    if (users && users.length > 0) {
      var results: Array<User> = new Array<User>();
      for (var i = 0; i < users.length; i++) {
        if (!User.equals(users[i], user)) {
          results.push(users[i]);
        }
      }
      return results;
    }
    return new Array<User>();
  }

  public static tiny(user: User): User {
    var simpleUser: User = new User();
    simpleUser.id = user.id;
    simpleUser.alias = user.alias;
    simpleUser.authenticated = user.authenticated;
    simpleUser.avatar = user.avatar;
    simpleUser.language = user.language;
    simpleUser.name = user.name;
    simpleUser.type = user.type;
    simpleUser.terms = user.terms;
    simpleUser.email = user.email;
    return simpleUser;
  }

  public static simplify(user: User): User {
    var simpleUser: User = new User();
    simpleUser.id = user.id;
    simpleUser.alias = user.alias;
    simpleUser.authenticated = user.authenticated;
    simpleUser.avatar = user.avatar;
    simpleUser.language = user.language;
    simpleUser.measure = user.measure;
    simpleUser.name = user.name;
    simpleUser.type = user.type;
    simpleUser.terms = user.terms;
    simpleUser.location = user.location;
    simpleUser.scores = user.scores;
    simpleUser.wallets = user.wallets;
    return simpleUser;
  }

  public static contains(users: Array<User>, user: User): boolean {
    if (users && users.length > 0) {
      for (var i = 0; i <= users.length; i++) {
        if (users[i] && users[i].id == user.id) {
          //Current item is not unique
          return true;
        } else if (i == users.length - 1) {
          //Last item and unique
          return false;
        }
      }
    }
    return false;
  }

  public static countBuyers(users: Array<User>): number {
    if (users && users.length > 0) {
      var counter: number = 0;
      for (var i = 0; i <= users.length; i++) {
        if (users[i] && users[i].type && users[i].type == "buyer") {
          counter++;
        }
      }
      return counter;
    }

    return -1;
  }

  public static countSellers(users: Array<User>): number {
    if (users && users.length > 0) {
      var counter: number = 0;
      for (var i = 0; i <= users.length; i++) {
        if (users[i] && users[i].type && users[i].type == "seller") {
          counter++;
        }
      }
      return counter;
    }
    return -1;
  }

  public static create(users: Array<User>, user: User): Array<User> {
    if (users.length == 0 || !User.contains(users, user)) {
      users.push(user);
      return users;
    } else {
      return users;
    }
  }

  public static readFull(users: Array<User>, user: User): User {
    if (users && users.length > 0) {
      for (var i = 0; i < users.length; i++) {
        if (users[i] && users[i].id == user.id) {
          return users[i];
        }
      }
    }
    return new User();
  }

  public static readBuyers(users: Array<User>): Array<User> {
    if (users && users.length > 0) {
      var receivers: Array<User> = new Array<User>();
      for (var i = 0; i < users.length; i++) {
        if (users[i] && users[i].type && users[i].type == "buyer") {
          receivers.push(users[i]);
        }
      }
      return receivers;
    }
    return new Array<User>();
  }

  public static readSellers(users: Array<User>): Array<User> {
    if (users && users.length > 0) {
      var receivers: Array<User> = new Array<User>();
      for (var i = 0; i < users.length; i++) {
        if (users[i] && users[i].type && users[i].type == "seller") {
          receivers.push(users[i]);
        }
      }
      return receivers;
    }
    return new Array<User>();
  }

  public static readSender(users: Array<User>): User {
    if (users && users.length > 0) {
      for (var i = 0; i < users.length; i++) {
        if (users[i] && users[i].type && users[i].type == "sender") {
          return users[i];
        }
      }
    }
    return new User();
  }

  public static readRecievers(users: Array<User>): Array<User> {
    if (users && users.length > 0) {
      var receivers: Array<User> = new Array<User>();
      for (var i = 0; i < users.length; i++) {
        if (users[i] && users[i].type && users[i].type == "receiver") {
          receivers.push(users[i]);
        }
      }
      return receivers;
    }
    return new Array<User>();
  }

  public static update(users: Array<User>, user: User): Array<User> {
    if (users && users.length > 0) {
      for (var i = 0; i < users.length; i++) {
        if (users[i] && users[i].id == user.id) {
          users[i] = user;
          return users;
        }
      }
    }
    return new Array<User>();
  }

  public static delete(users: Array<User>, user: User): Array<User> {
    if (users && users.length > 0) {
      for (var i = 0; i < users.length; i++) {
        if (users[i] && users[i].id == user.id) {
          users.splice(i, 1);
          return users;
        }
      }
    }
    return new Array<User>();
  }
}
