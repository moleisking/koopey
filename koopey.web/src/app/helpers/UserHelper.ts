import { User, UserType } from "../models/user";

export class UserHelper {
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
