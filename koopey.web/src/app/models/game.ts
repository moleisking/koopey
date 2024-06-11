
import { User } from "../models/user";
import { Base } from "./base/base";
import { GameType } from "./type/GameType";
import { UserType } from "./type/UserType";

export class Game extends Base {
  public users: Array<User> = new Array<User>(); //player1, player2, player3, player4
  public counter: number = 1;
  public defeats: Array<boolean> = new Array<boolean>(
    false,
    false,
    false,
    false
  ); //B,G,R,Y
  public moves: Array<string> = new Array<string>();
  public type: string = GameType.FourWayChess;
  public token: UserType = UserType.Blue; // First move is always blue
  public startTimeStamp: number = Date.now();
  public endTimeStamp: number = Date.now();

  public static isEmpty(game: Game): boolean {
    if (
      game &&
      game.type &&
      game.createTimeStamp != 0 &&
      game.type.match("twowaychess|fourwaychess") &&
      game.users.length >= 1 &&
      game.users.length <= 4
    ) {
      return false;
    } else {
      return true;
    }
  }

  private static isBlueMove(game: Game) {
    return game && game.token && game.token == UserType.Blue ? true : false;
  }

  public static isClone(gameA: Game, gameB: Game): boolean {
    return gameA &&
      gameB &&
      gameA.id &&
      gameB.id &&
      gameA.token == gameB.token &&
      gameA.moves.length == gameB.moves.length
      ? true
      : false;
  }

  private static isGreenMove(game: Game) {
    return game && game.token && game.token == UserType.Green ? true : false;
  }

  private static isRedMove(game: Game) {
    return game && game.token && game.token == UserType.Red ? true : false;
  }

  public static isStarting(game: Game): boolean {
    if (
      game &&
      game.type &&
      game.startTimeStamp != 0 &&
      game.type.match("twowaychess|fourwaychess") &&
      game.users.length >= 1 &&
      game.users.length <= 4
    ) {
      return false;
    } else {
      return true;
    }
  }

  private static isYellowMove(game: Game) {
    return game && game.token && game.token == UserType.Yellow ? true : false;
  }

  public static isPlaying(game: Game): boolean {
    if (
      game &&
      game.type &&
      game.startTimeStamp != 0 &&
      game.endTimeStamp != 0 &&
      game.type.match("twowaychess|fourwaychess") &&
      game.users.length == 4
    ) {
      return false;
    } else {
      return true;
    }
  }

  /*   public static isEnded(game: Game): boolean {
           if (game
               && game.type           
               && game.createTimeStamp != 0
               && game.deleteTimeStamp != 0          
               && game.type.match('twowaychess|fourwaychess')
               && game.users.length == 4) {
               return false;
           } else {
               return true;
           }
       }*/

  /* public static isEnd(game: Game): boolean {
        var counter: number = 0;
        for (var i = 0; i < game.pieces.length; i++) {
            if (game.pieces[i].type == PieceType.King && game.pieces[i].color != PlayerType.Grey) {
                counter++;
            } else if (i == game.pieces.length - 1 && counter == 1) {
                return true;
            } else if (i == game.pieces.length - 1 && counter > 1) {
                return false;
            }
        }
    }

    public static contains(games: Array<Game>, id: string): boolean {
        if (games && games.length > 0) {
            for (var i = 0; i <= games.length; i++) {
                //Exclude current item
                if (games[i] &&
                    games[i].id == id) {
                    //Current item is not unique                     
                    return true;
                } else if (i == games.length - 1) {
                    //Last item and unique  
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static createFourWayChessGame(): Game {
        var game = new Game();
        game.token = PlayerType.Blue;     
        game.defeats = new Array<boolean>(false, false, false, false);//B,G,R,Y
        game.pieces = new Array<Piece>(
            new Piece(PlayerType.Blue, PieceType.Rook, 5, 4, 1), new Piece(PlayerType.Blue, PieceType.Knight, 5, 5, 1), new Piece(PlayerType.Blue, PieceType.Bishop, 3, 6, 1), new Piece(PlayerType.Blue, PieceType.Queen, 9, 7, 1),
            new Piece(PlayerType.Blue, PieceType.King, 20, 8, 1), new Piece(PlayerType.Blue, PieceType.Bishop, 3, 9, 1), new Piece(PlayerType.Blue, PieceType.Knight, 5, 10, 1), new Piece(PlayerType.Blue, PieceType.Rook, 5, 11, 1),
            new Piece(PlayerType.Blue, PieceType.Pawn, 1, 4, 2), new Piece(PlayerType.Blue, PieceType.Pawn, 1, 5, 2), new Piece(PlayerType.Blue, PieceType.Pawn, 1, 6, 2), new Piece(PlayerType.Blue, PieceType.Pawn, 1, 7, 2),
            new Piece(PlayerType.Blue, PieceType.Pawn, 1, 8, 2), new Piece(PlayerType.Blue, PieceType.Pawn, 1, 9, 2), new Piece(PlayerType.Blue, PieceType.Pawn, 1, 10, 2), new Piece(PlayerType.Blue, PieceType.Pawn, 1, 11, 2),
            new Piece(PlayerType.Green, PieceType.Rook, 5, 1, 4), new Piece(PlayerType.Green, PieceType.Knight, 5, 1, 5), new Piece(PlayerType.Green, PieceType.Bishop, 3, 1, 6), new Piece(PlayerType.Green, PieceType.Queen, 9, 1, 7),
            new Piece(PlayerType.Green, PieceType.King, 20, 1, 8), new Piece(PlayerType.Green, PieceType.Bishop, 3, 1, 9), new Piece(PlayerType.Green, PieceType.Knight, 5, 1, 10), new Piece(PlayerType.Green, PieceType.Rook, 5, 1, 11),
            new Piece(PlayerType.Green, PieceType.Pawn, 1, 2, 4), new Piece(PlayerType.Green, PieceType.Pawn, 1, 2, 5), new Piece(PlayerType.Green, PieceType.Pawn, 1, 2, 6), new Piece(PlayerType.Green, PieceType.Pawn, 1, 2, 7),
            new Piece(PlayerType.Green, PieceType.Pawn, 1, 2, 8), new Piece(PlayerType.Green, PieceType.Pawn, 1, 2, 9), new Piece(PlayerType.Green, PieceType.Pawn, 1, 2, 10), new Piece(PlayerType.Green, PieceType.Pawn, 1, 2, 11),
            new Piece(PlayerType.Red, PieceType.Rook, 5, 4, 14), new Piece(PlayerType.Red, PieceType.Knight, 5, 5, 14), new Piece(PlayerType.Red, PieceType.Bishop, 3, 6, 14), new Piece(PlayerType.Red, PieceType.King, 20, 7, 14),
            new Piece(PlayerType.Red, PieceType.Queen, 9, 8, 14), new Piece(PlayerType.Red, PieceType.Bishop, 3, 9, 14), new Piece(PlayerType.Red, PieceType.Knight, 5, 10, 14), new Piece(PlayerType.Red, PieceType.Rook, 5, 11, 14),
            new Piece(PlayerType.Red, PieceType.Pawn, 1, 4, 13), new Piece(PlayerType.Red, PieceType.Pawn, 1, 5, 13), new Piece(PlayerType.Red, PieceType.Pawn, 1, 6, 13), new Piece(PlayerType.Red, PieceType.Pawn, 1, 7, 13),
            new Piece(PlayerType.Red, PieceType.Pawn, 1, 8, 13), new Piece(PlayerType.Red, PieceType.Pawn, 1, 9, 13), new Piece(PlayerType.Red, PieceType.Pawn, 1, 10, 13), new Piece(PlayerType.Red, PieceType.Pawn, 1, 11, 13),
            new Piece(PlayerType.Yellow, PieceType.Rook, 5, 14, 4), new Piece(PlayerType.Yellow, PieceType.Knight, 5, 14, 5), new Piece(PlayerType.Yellow, PieceType.Bishop, 3, 14, 6), new Piece(PlayerType.Yellow, PieceType.King, 20, 14, 7),
            new Piece(PlayerType.Yellow, PieceType.Queen, 9, 14, 8), new Piece(PlayerType.Yellow, PieceType.Bishop, 3, 14, 9), new Piece(PlayerType.Yellow, PieceType.Knight, 5, 14, 10), new Piece(PlayerType.Yellow, PieceType.Rook, 5, 14, 11),
            new Piece(PlayerType.Yellow, PieceType.Pawn, 1, 13, 4), new Piece(PlayerType.Yellow, PieceType.Pawn, 1, 13, 5), new Piece(PlayerType.Yellow, PieceType.Pawn, 1, 13, 6), new Piece(PlayerType.Yellow, PieceType.Pawn, 1, 13, 7),
            new Piece(PlayerType.Yellow, PieceType.Pawn, 1, 13, 8), new Piece(PlayerType.Yellow, PieceType.Pawn, 1, 13, 9), new Piece(PlayerType.Yellow, PieceType.Pawn, 1, 13, 10), new Piece(PlayerType.Yellow, PieceType.Pawn, 1, 13, 11)
        );
        return game;
    }

    public static create(games: Array<Game>, game: Game): Array<Game> {
        if (games.length == 0 || !Game.contains(games, game.id)) {
            games.push(game);
            return games;
        } else {
            return games;
        }
    }

    public static read(games: Array<Game>, game: Game): Game {
        if (games && games.length > 0) {
            for (var i = 0; i <= games.length; i++) {
                if (games[i] &&
                    games[i].id == game.id) {
                    return games[i];
                }
            }
        }
    }

    public static readMyPlayerType(players: Array<User>): PlayerType {
        for (var i = 0; i < players.length; i++) {
            if (localStorage.id == players[i].id) {
                return players[i].player;
            }
        }
    }

    public static readMyPlayer(players: Array<User>): User {
        var me: User;
        for (var i = 0; i < players.length; i++) {
            if (players[i].id == localStorage.id) {
                return players[i];
            }
        }
    }

    public static readCompetitors(users: Array<User>): Array<User> {
        var competitors: Array<User>;
        for (var i = 0; i < users.length; i++) {
            if (users[i].id != localStorage.id) {
                competitors.push(users[i]);
            }
        }
        return competitors;
    }


    public static readLosser(players: Array<User>): User {
        var losser: User;
        for (var i = 0; i < players.length; i++) {
            if (players[i].score < losser.score) {
                losser = players[i];
            }
        }
        return losser;
    }

    public static readWinner(players: Array<User>): User {
        var winner: User;
        for (var i = 0; i < players.length; i++) {
            if (players[i].score > winner.score) {
                winner = players[i];
            }
        }
        return winner;
    }

    public static readType(games: Array<Game>, gameType : GameType): Game {       
        for (var i = 0; i <= games.length; i++) {
            if (games[i].type == gameType) {
               return games[i];
            }
        }        
    }

    public static update(games: Array<Game>, game: Game): Array<Game> {
        if (games && games.length > 0) {
            for (var i = 0; i <= games.length; i++) {
                if (games[i] &&
                    games[i].id == game.id) {
                    games[i] == game;
                    return games;
                }
            }
        }
    }

    public static delete(games: Array<Game>, game: Game): Array<Game> {
        if (games && games.length > 0) {
            for (var i = 0; i <= games.length; i++) {
                if (games[i] &&
                    games[i].id == game.id) {
                    games.splice(i, 1);
                    return games;
                }
            }
        }
    }

    public static deletePlayer(game: Game, playerType: PlayerType) {
        for (var i = 0; i < game.pieces.length; i++) {
            if (game.pieces[i].color == playerType) {
                game.pieces[i].color = PlayerType.Grey;
                game.pieces[i].value = 0;
                if (playerType == PlayerType.Blue) {
                    game.defeats[0] = true;
                } else if (playerType == PlayerType.Green) {
                    game.defeats[1] = true;
                } else if (playerType == PlayerType.Red) {
                    game.defeats[2] = true;
                } else if (playerType == PlayerType.Yellow) {
                    game.defeats[3] = true;
                }
            }
        }
    }*/
}
