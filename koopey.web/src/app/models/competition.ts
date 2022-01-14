import { Base } from "./base/base";
import { User } from "../models/user";
import { Game } from "../models/game";

export class Competition extends Base {
  public userId!: String;
  public user: User = new User();
  public gameId!: String;
  public game: Game = new Game();
}
