import { Base } from "./base/base";
import { User } from "../models/user";
import { Game } from "../models/game";

export class Competition extends Base {
  public users: Array<User> = new Array<User>();
  public games: Array<Game> = new Array<Game>();
}
