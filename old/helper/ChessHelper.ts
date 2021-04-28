import { Game, GameType } from "../models/game";
import { User } from "../../src/app/models/user";
import { Piece, PieceType } from "../models/piece";
import { PlayerType } from "../models/game";
import { BoardHelper, LineType, SquareType } from "../helpers/BoardHelper";

export enum MoveType {
  Capture = "Capture",
  Check = "Check",
  Checkmate = "Checkmate",
  EnPassant = "EnPassant",
  KingSideCastle = "KingSideCastle",
  QueenSideCastle = "QueenSideCastle",
  PawnPromotion = "PawnPromotion",
  Resign = "Resign",
  Timeout = "Timeout",
  Normal = "Normal",
}

export class ChessHelper {
  public static AlgebraicNotation(
    startPiece: Piece,
    endPiece: Piece,
    moveType: MoveType
  ): string {
    if (moveType == MoveType.KingSideCastle) {
      return "O-O";
    } else if (moveType == MoveType.QueenSideCastle) {
      return "O-O-O";
    } else if (moveType == MoveType.Timeout) {
      return "$";
    } else if (moveType == MoveType.Resign) {
      return "$$";
    } else {
      var algebraicNotation = "";
      //Start piece and position
      algebraicNotation +=
        ChessHelper.ColorToLetter(endPiece.color) +
        ChessHelper.PieceToLetter(endPiece.type) +
        ChessHelper.RankNumberToLetter(startPiece.rank) +
        startPiece.file;
      //Join of before and after position
      if (moveType == MoveType.Capture) {
        algebraicNotation += "x";
      } else {
        algebraicNotation += "-";
      }
      //End position
      algebraicNotation +=
        ChessHelper.RankNumberToLetter(endPiece.rank) + endPiece.file;
      //Special notes
      if (moveType == MoveType.Check) {
        algebraicNotation += "+";
      } else if (moveType == MoveType.Checkmate) {
        algebraicNotation += "++";
      }
      //Posible promote to queen and checkmate
      if (moveType == MoveType.PawnPromotion) {
        algebraicNotation += "=Q";
      }
      return algebraicNotation;
    }
  }

  private static ColorToLetter(playerType: PlayerType): string {
    if (playerType == PlayerType.Blue) {
      return "B";
    } else if (playerType == PlayerType.Green) {
      return "G";
    } else if (playerType == PlayerType.Red) {
      return "R";
    } else if (playerType == PlayerType.Yellow) {
      return "Y";
    }
  }

  /*public static isCaptureMove(game: Game, endPiece: Piece): boolean {
        for (var i = 0; i < game.pieces.length; i++) {
            if ((endPiece.file == game.pieces[i].file) && (endPiece.rank == game.pieces[i].rank) && (endPiece.color != game.pieces[i].color)) {
                return true;
            } else if (i == game.pieces.length - 1) {
                //Last item and no pieces found
                return false;
            }
        }
    }*/

  private static isCastleMove(game: Game, startPiece: Piece, endPiece: Piece) {
    //Common features of all castle
    if (
      startPiece.type == PieceType.King &&
      startPiece.moves == 0 &&
      !BoardHelper.isObstacleMove(game, startPiece, endPiece)
    ) {
      //Common features of blue castle (bottom)
      if (
        startPiece.color == PlayerType.Blue &&
        startPiece.file == 8 &&
        startPiece.rank == 1 &&
        endPiece.rank == 1
      ) {
        //Queen side castle
        if (endPiece.file == 4 || endPiece.file == 5 || endPiece.file == 6) {
          var castlePiece = BoardHelper.getSquarePieceByPosition(game, 4, 1);
          if (
            !Piece.isEmpty(castlePiece) &&
            castlePiece.color == PlayerType.Blue &&
            castlePiece.type == PieceType.Rook &&
            castlePiece.moves == 0
          ) {
            return true;
          }
        } else if (endPiece.file == 10 || endPiece.file == 11) {
          //King side castle
          var castlePiece = BoardHelper.getSquarePieceByPosition(game, 11, 1);
          if (
            !Piece.isEmpty(castlePiece) &&
            castlePiece.color == PlayerType.Blue &&
            castlePiece.type == PieceType.Rook &&
            castlePiece.moves == 0
          ) {
            return true;
          }
        } else {
          return false;
        }
      } else if (
        startPiece.color == PlayerType.Green &&
        startPiece.file == 1 &&
        endPiece.file == 1 &&
        startPiece.rank == 8
      ) {
        //Queen side castle
        if (endPiece.rank == 4 || endPiece.rank == 5 || endPiece.rank == 6) {
          var castlePiece = BoardHelper.getSquarePieceByPosition(game, 1, 4);
          if (
            !Piece.isEmpty(castlePiece) &&
            castlePiece.color == PlayerType.Green &&
            castlePiece.type == PieceType.Rook &&
            castlePiece.moves == 0
          ) {
            return true;
          }
        } else if (endPiece.rank == 10 || endPiece.rank == 11) {
          //King side castle
          var castlePiece = BoardHelper.getSquarePieceByPosition(game, 1, 11);
          if (
            !Piece.isEmpty(castlePiece) &&
            castlePiece.color == PlayerType.Green &&
            castlePiece.type == PieceType.Rook &&
            castlePiece.moves == 0
          ) {
            return true;
          }
        } else {
          return false;
        }
      } else if (
        startPiece.color == PlayerType.Red &&
        startPiece.file == 7 &&
        startPiece.rank == 14 &&
        endPiece.rank == 14
      ) {
        //Queen side castle
        if (endPiece.file == 9 || endPiece.file == 10 || endPiece.file == 11) {
          var castlePiece = BoardHelper.getSquarePieceByPosition(game, 11, 14);
          if (
            !Piece.isEmpty(castlePiece) &&
            castlePiece.color == PlayerType.Red &&
            castlePiece.type == PieceType.Rook &&
            castlePiece.moves == 0
          ) {
            return true;
          }
        } else if (endPiece.file == 4 || endPiece.file == 5) {
          //King side castle
          var castlePiece = BoardHelper.getSquarePieceByPosition(game, 4, 14);
          if (
            !Piece.isEmpty(castlePiece) &&
            castlePiece.color == PlayerType.Red &&
            castlePiece.type == PieceType.Rook &&
            castlePiece.moves == 0
          ) {
            return true;
          }
        } else {
          return false;
        }
      } else if (
        startPiece.color == PlayerType.Yellow &&
        startPiece.file == 14 &&
        endPiece.file == 14 &&
        startPiece.rank == 7
      ) {
        //Queen side castle
        if (endPiece.rank == 9 || endPiece.rank == 10 || endPiece.rank == 11) {
          var castlePiece = BoardHelper.getSquarePieceByPosition(game, 14, 11);
          if (
            !Piece.isEmpty(castlePiece) &&
            castlePiece.color == PlayerType.Yellow &&
            castlePiece.type == PieceType.Rook &&
            castlePiece.moves == 0
          ) {
            return true;
          }
        } else if (endPiece.rank == 4 || endPiece.rank == 5) {
          //King side castle
          var castlePiece = BoardHelper.getSquarePieceByPosition(game, 14, 4);
          if (
            !Piece.isEmpty(castlePiece) &&
            castlePiece.color == PlayerType.Yellow &&
            castlePiece.type == PieceType.Rook &&
            castlePiece.moves == 0
          ) {
            return true;
          }
        } else {
          return false;
        }
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  private static isCheck(
    game: Game,
    pieceType: PieceType,
    playerType: PlayerType
  ): boolean {
    //TODO
    //See if player is in check
    var kingPiece: Piece = BoardHelper.getSquarePieceByTypeAndColor(
      game,
      pieceType,
      playerType
    );

    return false;
  }

  private static isEnPassant(game: Game, endPiece: Piece): boolean {
    //TODO
    for (var i = 0; i < game.pieces.length; i++) {
      if (
        endPiece.file == game.pieces[i].file &&
        endPiece.rank == game.pieces[i].rank &&
        endPiece.color != game.pieces[i].color
      ) {
        return true;
      } else {
        return false;
      }
    }
  }

  public static isLegalMove(
    game: Game,
    startPiece: Piece,
    endPiece: Piece
  ): boolean {
    console.log("BoardHelper.isSquareFriend(game,endPiece)");
    console.log(BoardHelper.isSquareFriend(game, endPiece));
    console.log(startPiece.type);
    console.log(endPiece.type);
    console.log(startPiece.color);
    console.log(game.token);
    if (
      !BoardHelper.isSquareFriend(game, endPiece) &&
      startPiece.type == endPiece.type &&
      startPiece.color == game.token
    ) {
      console.log("isLegalMove:true");
      if (startPiece.type == PieceType.Pawn) {
        console.log("this.isPawnMove");
        return this.isLegalPawnMove(game, startPiece, endPiece);
      } else if (startPiece.type == PieceType.Rook) {
        console.log("this.isRookMove");
        return this.isLegalRookMove(game, startPiece, endPiece);
      } else if (startPiece.type == PieceType.Bishop) {
        console.log("this.isBishipMove");
        return this.isLegalBishipMove(game, startPiece, endPiece);
      } else if (startPiece.type == PieceType.Knight) {
        console.log("this.isKnightMove");
        return this.isLegalKnightMove(startPiece, endPiece);
      } else if (startPiece.type == PieceType.Queen) {
        console.log("this.isQueenMove");
        return this.isLegalQueenMove(game, startPiece, endPiece);
      } else if (startPiece.type == PieceType.King) {
        console.log("this.isKingMove");
        return this.isLegalKingMove(game, startPiece, endPiece);
      } else {
        return false;
      }
    } else {
      console.log("isLegalMove:false");
      console.log(startPiece);
      console.log(endPiece);
      console.log(game.token);
      return false;
    }
  }

  public static isLegalPawnMove(
    game: Game,
    startPiece: Piece,
    endPiece: Piece
  ): boolean {
    console.log(" isPawnMove");
    console.log(startPiece);
    console.log(endPiece);
    var blocks: number = BoardHelper.getSquareCount(startPiece, endPiece);
    var obsticle: boolean = BoardHelper.isObstacleMove(
      game,
      startPiece,
      endPiece
    );
    var lineType: LineType = BoardHelper.getLineType(startPiece, endPiece);
    var squareType = BoardHelper.getSquareType(game, endPiece);
    if (
      startPiece.type == PieceType.Pawn &&
      blocks >= 1 &&
      blocks <= 2 &&
      BoardHelper.isRelativeForwardMove(startPiece, endPiece)
    ) {
      if (startPiece.moves == 0 && blocks == 2 && !obsticle) {
        return true; //First move jump
      } else if (
        blocks == 1 &&
        !obsticle &&
        (lineType == LineType.PositiveHorizontal ||
          lineType == LineType.NegativeHorizontal ||
          lineType == LineType.PositiveVerticle ||
          lineType == LineType.NegativeVerticle)
      ) {
        return true; //Straight move forward
      } else if (
        (BoardHelper.isRelativeLeftMove(startPiece, endPiece) ||
          BoardHelper.isRelativeRightMove(startPiece, endPiece)) &&
        squareType == SquareType.Enemy
      ) {
        /*ChessHelper.isCaptureMove(game, endPiece)*/
        return true; //Diagonal capture
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  private static isLegalRookMove(
    game: Game,
    startPiece: Piece,
    endPiece: Piece
  ): boolean {
    if (startPiece.type == PieceType.Rook && endPiece.type == PieceType.Rook) {
      var obsticle = BoardHelper.isObstacleMove(game, startPiece, endPiece);
      var lineType = BoardHelper.getLineType(startPiece, endPiece);
      if (
        !obsticle &&
        (lineType == LineType.PositiveHorizontal ||
          lineType == LineType.PositiveVerticle ||
          lineType == LineType.NegativeHorizontal ||
          lineType == LineType.NegativeVerticle)
      ) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  private static isLegalBishipMove(
    game: Game,
    startPiece: Piece,
    endPiece: Piece
  ): boolean {
    if (
      startPiece.type == PieceType.Bishop &&
      endPiece.type == PieceType.Bishop
    ) {
      var obsticle = BoardHelper.isObstacleMove(game, startPiece, endPiece);
      var lineType = BoardHelper.getLineType(startPiece, endPiece);
      if (
        !obsticle &&
        (lineType == LineType.PositiveIncreaseDiagonal ||
          lineType == LineType.NegativeIncreaseDiagonal ||
          lineType == LineType.PositiveDecreaseDiagonal ||
          lineType == LineType.NegativeDecreaseDiagonal)
      ) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  private static isLegalKnightMove(
    startPiece: Piece,
    endPiece: Piece
  ): boolean {
    if (
      startPiece.type == PieceType.Knight &&
      endPiece.type == PieceType.Knight
    ) {
      var blocks = BoardHelper.getSquareCount(startPiece, endPiece);
      var lineType = BoardHelper.getLineType(startPiece, endPiece);
      if (blocks == 3 && lineType == LineType.UnequalDiagonal) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  private static isLegalQueenMove(
    game: Game,
    startPiece: Piece,
    endPiece: Piece
  ): boolean {
    if (
      startPiece.type == PieceType.Queen &&
      endPiece.type == PieceType.Queen
    ) {
      var obsticle = BoardHelper.isObstacleMove(game, startPiece, endPiece);
      var lineType = BoardHelper.getLineType(startPiece, endPiece);
      if (!obsticle && lineType >= 1 && lineType <= 8) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  private static isLegalKingMove(
    game: Game,
    startPiece: Piece,
    endPiece: Piece
  ): boolean {
    if (startPiece.type == PieceType.King && endPiece.type == PieceType.King) {
      var blocks = BoardHelper.getSquareCount(startPiece, endPiece);
      if (blocks == 1 && BoardHelper.isSquareAttacked(game, endPiece)) {
        return true;
      } else if (blocks > 1) {
        return this.isCastleMove(game, startPiece, endPiece) ? true : false;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public static isPawnPromotionMove(endPiece: Piece): boolean {
    if (
      endPiece.type == PieceType.Pawn &&
      endPiece.color == PlayerType.Blue &&
      endPiece.rank >= 8
    ) {
      return true;
    } else if (
      endPiece.type == PieceType.Pawn &&
      endPiece.color == PlayerType.Green &&
      endPiece.file >= 8
    ) {
      return true;
    } else if (
      endPiece.type == PieceType.Pawn &&
      endPiece.color == PlayerType.Red &&
      endPiece.rank >= 7
    ) {
      return true;
    } else if (
      endPiece.type == PieceType.Pawn &&
      endPiece.color == PlayerType.Yellow &&
      endPiece.file >= 7
    ) {
      return true;
    } else {
      return false;
    }
  }

  private static isPawnMove(endPiece: Piece): boolean {
    return endPiece.type == PieceType.Pawn ? true : false;
  }

  private static PieceToLetter(pieceType: PieceType): string {
    if (pieceType == PieceType.Bishop) {
      return "B";
    } else if (pieceType == PieceType.King) {
      return "K";
    } else if (pieceType == PieceType.Knight) {
      return "K";
    } else if (pieceType == PieceType.Pawn) {
      return "P";
    } else if (pieceType == PieceType.Queen) {
      return "Q";
    } else if (pieceType == PieceType.Rook) {
      return "R";
    }
  }

  public static RankNumberToLetter(rank: number): string {
    if (rank == 1) {
      return "a";
    } else if (rank == 2) {
      return "b";
    } else if (rank == 3) {
      return "c";
    } else if (rank == 4) {
      return "d";
    } else if (rank == 5) {
      return "e";
    } else if (rank == 6) {
      return "f";
    } else if (rank == 7) {
      return "g";
    } else if (rank == 8) {
      return "h";
    } else if (rank == 9) {
      return "i";
    } else if (rank == 10) {
      return "j";
    } else if (rank == 11) {
      return "k";
    } else if (rank == 12) {
      return "l";
    } else if (rank == 13) {
      return "m";
    } else if (rank == 14) {
      return "n";
    }
  }

  public static RankLetterToNumber(letter: string): number {
    if (letter != null) {
      if (letter == "a") {
        return 1;
      } else if (letter == "b") {
        return 2;
      } else if (letter == "c") {
        return 3;
      } else if (letter == "d") {
        return 4;
      } else if (letter == "e") {
        return 5;
      } else if (letter == "f") {
        return 6;
      } else if (letter == "g") {
        return 7;
      } else if (letter == "h") {
        return 8;
      } else if (letter == "i") {
        return 9;
      } else if (letter == "j") {
        return 10;
      } else if (letter == "k") {
        return 11;
      } else if (letter == "l") {
        return 12;
      } else if (letter == "m") {
        return 13;
      } else if (letter == "n") {
        return 14;
      }
    }
  }
}
