import { Asset } from "../models/asset";
import { Environment } from "src/environments/environment";
import { Transaction } from "../models/transaction";
import { User } from "../models/user";

export class TransactionHelper {
  public static AssetValuePlusMargin(asset: Asset): number {
    if (asset && asset.currency && asset.value && asset.value > 0) {
      if (asset.currency.match("btc|eth")) {
        return (
          asset.value + (asset.value / 100) * Environment.Transaction.Margin
        );
      } else if (asset.currency.match("eur|gbp|usd|zar")) {
        return asset.value;
      } else {
        return 0;
      }
    }
    return -1;
  }

  public static TotalTransactionValuePlusMargin(
    transaction: Transaction
  ): number {
    if (
      transaction &&
      transaction.currency &&
      transaction.totalValue &&
      transaction.totalValue > 0
    ) {
      if (transaction.currency.match("btc|eth")) {
        return (
          transaction.totalValue +
          (transaction.totalValue / 100) * Environment.Transaction.Margin
        );
      } else {
        return 0;
      }
    }
    return -1;
  }

  public static BuyerShareValue(transaction: Transaction): number {
    if (
      transaction &&
      transaction.currency &&
      transaction.users &&
      transaction.totalValue &&
      transaction.totalValue > 0
    ) {
      return transaction.totalValue / this.CountBuyers(transaction).valueOf();
    }
    return -1;
  }

  public static BuyerShareValuePlusMargin(transaction: Transaction): number {
    if (
      transaction &&
      transaction.currency &&
      transaction.users &&
      transaction.totalValue &&
      transaction.totalValue > 0
    ) {
      var buyerShareValue =
        transaction.totalValue / this.CountBuyers(transaction);
      return (
        buyerShareValue +
        (buyerShareValue / 100) * Environment.Transaction.Margin
      );
    }
    return -1;
  }

  public static isAuthBuyer(transaction: Transaction): boolean {
    if (transaction && transaction.users && transaction.users.length >= 2) {
      for (var i = 0; i < transaction.users.length; i++) {
        if (
          User.isBuyer(transaction.users[i]) &&
          transaction.users[i].id == localStorage.getItem("id")
        ) {
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  }

  public static isAuthSeller(transaction: Transaction): boolean {
    if (transaction && transaction.users && transaction.users.length >= 2) {
      for (var i = 0; i < transaction.users.length; i++) {
        if (
          User.isSeller(transaction.users[i]) &&
          transaction.users[i].id == localStorage.getItem("id")
        ) {
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  }

  public static SellerShareValue(transaction: Transaction): number {
    if (
      transaction &&
      transaction.currency &&
      transaction.users &&
      transaction.totalValue &&
      transaction.totalValue > 0
    ) {
      return transaction.totalValue / this.CountSellers(transaction).valueOf();
    }
    return -1;
  }

  public static SellerShareValuePlusMargin(transaction: Transaction): number {
    if (
      transaction &&
      transaction.currency &&
      transaction.users &&
      transaction.totalValue &&
      transaction.totalValue > 0
    ) {
      var sellerShareValue =
        transaction.totalValue / this.CountSellers(transaction);
      return (
        sellerShareValue +
        (sellerShareValue / 100) * Environment.Transaction.Margin
      );
    }
    return -1;
  }

  public static CountBuyers(transaction: Transaction): number {
    if (transaction && transaction.users && transaction.users.length > 0) {
      var counter: number = 0;
      for (var i = 0; i <= transaction.users.length; i++) {
        if (
          transaction.users[i] &&
          transaction.users[i].type &&
          transaction.users[i].type == "buyer"
        ) {
          counter++;
        }
      }
      return counter;
    }
    return -1;
  }

  public static CountSellers(transaction: Transaction): number {
    if (transaction && transaction.users && transaction.users.length > 0) {
      var counter: number = 0;
      for (var i = 0; i <= transaction.users.length; i++) {
        if (
          transaction.users[i] &&
          transaction.users[i].type &&
          transaction.users[i].type == "seller"
        ) {
          counter++;
        }
      }
      return counter;
    }
    return -1;
  }
}
