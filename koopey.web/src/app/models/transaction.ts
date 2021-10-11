const SHA256 = require("crypto-js/sha256");
import { Location } from "../models/location";
import { User } from "../models/user";
import { UUID } from "angular2-uuid";
import { Asset } from "../models/asset";
import { Review } from "../models/review";
import { Wallet } from "../models/wallet";
import { BaseModel } from "./baseModel";

export enum StateType {
  Quote = "quote",
  Invoice = "invoice",
  Receipt = "receipt",
}

export class Transaction extends BaseModel {
  public users: Array<User> = new Array<User>(); //seller,buyer
  public asset: Asset = new Asset();
  public reviews: Array<Review> = new Array<Review>();
  public reference: string = UUID.UUID();
  public state: string = "quote";
  public currency: string = "tok";
  public secret: string = "secret";
  public totalValue: number = 1;
  public quantity: number = 1;
  public itemValue: number = 1;
  public guid: string = UUID.UUID();
  public location: Location = new Location();
  public startTimeStamp: number = Date.now();
  public endTimeStamp: number = Date.now();

  public static isEmpty(transaction: Transaction): boolean {
    if (
      transaction &&
      transaction.currency &&
      transaction.description &&
      transaction.type &&
      transaction.state &&
      transaction.quantity > 0 &&
      transaction.totalValue != 0 &&
      transaction.startTimeStamp != 0 &&
      transaction.endTimeStamp != 0 &&
      transaction.type.match("cod|cbd") &&
      transaction.users.length >= 2 &&
      transaction.itemValue != 0 &&
      transaction.state.match("quote|invoice|receipt") &&
      transaction.currency.match("btc|eth|eur|gbp|usd|zar")
    ) {
      return false;
    } else {
      return true;
    }
  }

  public static isBitcoin(transaction: Transaction): boolean {
    if (transaction && transaction.currency === "btc") {
      return true;
    } else {
      return false;
    }
  }

  public static isEthereum(transaction: Transaction): boolean {
    if (transaction && transaction.currency === "eth") {
      return true;
    } else {
      return false;
    }
  }

  public static isFiat(transaction: Transaction): boolean {
    if (
      transaction &&
      (transaction.currency === "gbp" ||
        transaction.currency === "usd" ||
        transaction.currency === "zar")
    ) {
      return true;
    } else {
      return false;
    }
  }

  public static isQuote(transaction: Transaction): boolean {
    if (transaction && transaction.state === "quote") {
      return true;
    } else {
      return false;
    }
  }

  public static isInvoice(transaction: Transaction): boolean {
    if (transaction && transaction.state === "invoice") {
      return true;
    } else {
      return false;
    }
  }

  public static isReceipt(transaction: Transaction): boolean {
    if (transaction && transaction.state === "receipt") {
      return true;
    } else {
      return false;
    }
  }

  public static contains(
    transactions: Array<Transaction>,
    id: string
  ): boolean {
    if (transactions && transactions.length > 0) {
      for (var i = 0; i <= transactions.length; i++) {
        //Exclude current fee
        if (transactions[i] && transactions[i].id == id) {
          //Current item is not unique
          return true;
        } else if (i == transactions.length - 1) {
          //Last item and unique
          return false;
        }
      }
    }
    return false;
  }

  public static create(
    transactions: Array<Transaction>,
    transaction: Transaction
  ): Array<Transaction> {
    if (
      transactions.length == 0 ||
      !Transaction.contains(transactions, transaction.id)
    ) {
      transactions.push(transaction);
      return transactions;
    } else {
      return transactions;
    }
  }

  public static read(
    transactions: Array<Transaction>,
    transaction: Transaction
  ): Transaction {
    if (transactions && transactions.length > 0) {
      for (var i = 0; i < transactions.length; i++) {
        if (transactions[i] && transactions[i].id == transaction.id) {
          return transactions[i];
        }
      }
    }
    return new Transaction();
  }

  public static update(
    transactions: Array<Transaction>,
    transaction: Transaction
  ): Array<Transaction> {
    if (transactions && transactions.length > 0) {
      for (var i = 0; i < transactions.length; i++) {
        if (transactions[i] && transactions[i].id == transaction.id) {
          transactions[i] == transaction;
          return transactions;
        }
      }
    }
    return new Array<Transaction>();
  }

  public static delete(
    transactions: Array<Transaction>,
    transaction: Transaction
  ): Array<Transaction> {
    if (transactions && transactions.length > 0) {
      for (var i = 0; i < transactions.length; i++) {
        if (transactions[i] && transactions[i].id == transaction.id) {
          transactions.splice(i, 1);
          return transactions;
        }
      }
    }
    return new Array<Transaction>();
  }
}
