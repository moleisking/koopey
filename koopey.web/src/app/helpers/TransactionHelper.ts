import { Asset } from "../models/asset";
import { Environment } from "src/environments/environment";
import { Transaction } from "../models/transaction";
import { User, UserType } from "../models/user";
import { UserHelper } from "./UserHelper";

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
      return (
        transaction.totalValue /
        UserHelper.countBuyers(transaction.users).valueOf()
      );
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
        transaction.totalValue / UserHelper.countBuyers(transaction.users);
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
      return (
        transaction.totalValue /
        UserHelper.countSellers(transaction.users).valueOf()
      );
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
        transaction.totalValue / UserHelper.countSellers(transaction.users);
      return (
        sellerShareValue +
        (sellerShareValue / 100) * Environment.Transaction.Margin
      );
    }
    return -1;
  }
}
