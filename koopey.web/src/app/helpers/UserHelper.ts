import { User, UserType } from "../models/user";

export class UserHelper {
  public static countBuyers(users: Array<User>): number {
    return users.filter((user) => {
      user.type === UserType.Buyer;
    }).length;
  }

  public static countSellers(users: Array<User>): number {
    return users.filter((user) => {
      user.type === UserType.Seller;
    }).length;
  }

  public static countReceivers(users: Array<User>): number {
    return users.filter((user) => {
      user.type === UserType.Receiver;
    }).length;
  }

  public static countSenders(users: Array<User>): number {
    return users.filter((user) => {
      user.type === UserType.Sender;
    }).length;
  }

  public static getBuyer(users: Array<User>): User | undefined {
    return users.find((user: User) => {
      user.type === UserType.Buyer;
    });
  }

  public static getSeller(users: Array<User>): User | undefined {
    return users.find((user: User) => {
      user.type === UserType.Seller;
    });
  }

  public static getSender(users: Array<User>): User | undefined {
    return users.find((user: User) => {
      user.type === UserType.Sender;
    });
  }

  public static getReceiver(users: Array<User>): User | undefined {
    return users.find((user: User) => {
      user.type === UserType.Receiver;
    });
  }
}
