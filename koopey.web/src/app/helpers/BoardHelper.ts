import { Game } from "../models/game";
import { Piece, PieceType } from "../models/piece";
import { PlayerType } from "../models/game";

export enum SquareType {
    Empty = 1,
    Enemy = 2,    
    Friend = 3,
    Illegal = 4,
    Obstacle = 5
}

export enum LineType {
    PositiveHorizontal = 1,
    NegativeHorizontal = 2,
    PositiveVerticle = 3,
    NegativeVerticle = 4,
    PositiveIncreaseDiagonal = 5,
    NegativeIncreaseDiagonal = 6,
    PositiveDecreaseDiagonal = 7,
    NegativeDecreaseDiagonal = 8,
    UnequalDiagonal = 9,
    ZeroLengthLine = 10
}

export class BoardHelper {

    public static getSquarePieceByObject(game: Game, endPiece: Piece): Piece {
        for (var i = 0; i < game.pieces.length; i++) {
            if ((game.pieces[i].file == endPiece.file) && (game.pieces[i].rank == endPiece.rank)) { return game.pieces[i] }
        }
    }

    public static getSquarePieceByPosition(game: Game, file: number, rank : number): Piece {
        for (var i = 0; i < game.pieces.length; i++) {
            if ((game.pieces[i].file == file) && (game.pieces[i].rank == rank)) { return game.pieces[i] }
        }
    }

    public static getSquarePieceByTypeAndColor(game: Game , pieceType: PieceType , playerType: PlayerType): Piece {
        for (var i = 0; i < game.pieces.length; i++) {
            if ((game.pieces[i].type == pieceType) && (game.pieces[i].color == playerType)) { return game.pieces[i] }
        }
    }

    public static getSquareType(game: Game, endPiece: Piece): SquareType {       
        var myPlayerType : PlayerType= Game.readMyPlayerType(game.users);
        for (var i = 0; i < game.pieces.length; i++) {
            if ((endPiece.file == game.pieces[i].file) && (endPiece.rank == game.pieces[i].rank)) {
                if (game.pieces[i].color == myPlayerType) {                 
                    return SquareType.Friend;
                } else if (game.pieces[i].color == PlayerType.Grey) {                   
                    return SquareType.Obstacle;
                } else {                   
                    return SquareType.Enemy;
                }                
            } else if (i == game.pieces.length - 1) {
                //Last item and no pieces found
                return SquareType.Empty;
            }
        }
    }

    public static getSquareCount(startPiece: Piece, endPiece: Piece): number {
        //Only need to measure the linear block count as diagonals are isosceles right triangles
        var lineType: LineType = this.getLineType(startPiece, endPiece);
        if ((lineType == LineType.PositiveHorizontal) || (lineType == LineType.NegativeHorizontal) ||
            (lineType == LineType.PositiveIncreaseDiagonal) || (lineType == LineType.NegativeIncreaseDiagonal) ||
            (lineType == LineType.PositiveDecreaseDiagonal) || (lineType == LineType.NegativeDecreaseDiagonal)) {
            return Math.abs(startPiece.file - endPiece.file);
        } else if ((lineType == LineType.PositiveVerticle) || (lineType == LineType.NegativeVerticle)) {
            return Math.abs(startPiece.rank - endPiece.rank);
        } else if (lineType == LineType.UnequalDiagonal) {
            return Math.abs(startPiece.file - endPiece.file) + Math.abs(startPiece.rank - endPiece.rank);
        } else if (lineType == LineType.ZeroLengthLine) {
            return 0;
        }
    }

    public static getLineType(startPiece: Piece, endPiece: Piece): LineType {
        //Gradients -> IncreaseDiagonal=1,DecreaseDiagonal=-1,Linear=0,PositiveVerticle=-Infinity,NegativeVerticle=Infinity, Unequal = [-0.5, 0.5,-2,2] 
        var gradient = (startPiece.rank - endPiece.rank) / (startPiece.file - endPiece.file);
        if (gradient == 1) {
            if (startPiece.file < endPiece.file) {
                return LineType.PositiveIncreaseDiagonal;
            } else {
                return LineType.NegativeIncreaseDiagonal;
            }
        } else if (gradient == -1) {
            if (startPiece.file < endPiece.file) {
                return LineType.PositiveDecreaseDiagonal;
            } else {
                return LineType.NegativeDecreaseDiagonal;
            }
        } else if (gradient == Infinity) {
            return LineType.NegativeVerticle;
        } else if (gradient == -Infinity) {
            return LineType.PositiveVerticle;
        } else if (gradient == 0) {
            return (startPiece.file < endPiece.file) ? LineType.PositiveHorizontal : LineType.NegativeHorizontal;
        } else if (gradient == -0.5 || gradient == 0.5 || gradient == -2 || gradient == 2) {
            return LineType.UnequalDiagonal;
        }
    }

    public static isRelativeBackwardMove(startPiece: Piece, endPiece: Piece): boolean {
        if ((startPiece.color == PlayerType.Blue) && (startPiece.rank > endPiece.rank)) {
            return true;
        } else if ((startPiece.color == PlayerType.Green) && (startPiece.file > endPiece.file)) {
            return true;
        } else if ((startPiece.color == PlayerType.Red) && (startPiece.rank < endPiece.rank)) {
            return true;
        } else if ((startPiece.color == PlayerType.Yellow) && (startPiece.file < endPiece.file)) {
            return true;
        } else {
            return false;
        }
    }

    public static isRelativeForwardMove(startPiece: Piece, endPiece: Piece): boolean {
        if ((startPiece.color == PlayerType.Blue) && (startPiece.rank < endPiece.rank)) {
            return true;
        } else if ((startPiece.color == PlayerType.Green) && (startPiece.file < endPiece.file)) {
            return true;
        } else if ((startPiece.color == PlayerType.Red) && (startPiece.rank > endPiece.rank)) {
            return true;
        } else if ((startPiece.color == PlayerType.Yellow) && (startPiece.file > endPiece.file)) {
            return true;
        } else {
            return false;
        }
    }

    public static isRelativeLeftMove(startPiece: Piece, endPiece: Piece): boolean {
        if ((startPiece.color) == PlayerType.Blue && (startPiece.file > endPiece.file)) {
            return true;
        } else if ((startPiece.color == PlayerType.Green) && (startPiece.rank < endPiece.rank)) {
            return true;
        } else if ((startPiece.color == PlayerType.Red) && (startPiece.file < endPiece.file)) {
            return true;
        } else if ((startPiece.color == PlayerType.Yellow) && (startPiece.rank > endPiece.rank)) {
            return true;
        } else {
            return false;
        }
    }

    public static isRelativeRightMove(startPiece: Piece, endPiece: Piece): boolean {
        if ((startPiece.color == PlayerType.Blue) && (startPiece.file < endPiece.file)) {
            return true;
        } else if ((startPiece.color == PlayerType.Green) && (startPiece.rank > endPiece.rank)) {
            return true;
        } else if ((startPiece.color == PlayerType.Red) && (startPiece.file > endPiece.file)) {
            return true;
        } else if ((startPiece.color == PlayerType.Yellow) && (startPiece.rank < endPiece.rank)) {
            return true;
        } else {
            return false;
        }
    }

    public static isObstacleMove(game: Game, startPiece: Piece, endPiece: Piece): boolean {
        var blocks = BoardHelper.getSquareCount(startPiece, endPiece);
        var lineType = BoardHelper.getLineType(startPiece, endPiece);
        var currentSquare: Piece = Piece.clone(startPiece);
        //Increment through blocks, don't check start and last block as could be capture hence "var i = 1" and "i < blocks"
        for (var i = 1; i < blocks; i++) {
            //Navigate files and ranks in the correct direction
            if (lineType == LineType.PositiveHorizontal) {
                currentSquare.file++;
            } else if (lineType == LineType.NegativeHorizontal) {
                currentSquare.file--;
            } if (lineType == LineType.PositiveVerticle) {
                currentSquare.rank++;
            } else if (lineType == LineType.NegativeVerticle) {
                currentSquare.rank--;
            } else if (lineType == LineType.PositiveIncreaseDiagonal) {
                currentSquare.file++;
                currentSquare.rank++;
            } else if (lineType == LineType.NegativeIncreaseDiagonal) {
                currentSquare.file--;
                currentSquare.rank--;
            } else if (lineType == LineType.PositiveDecreaseDiagonal) {
                currentSquare.file++;
                currentSquare.rank--;
            } else if (lineType == LineType.NegativeDecreaseDiagonal) {
                currentSquare.file--;
                currentSquare.rank++;
            }
            //Check if block is occupied
            var squareType : SquareType = BoardHelper.getSquareType(game, currentSquare)
            if (squareType == SquareType.Enemy || squareType == SquareType.Friend) {
                return true; //At least one obstacle found
            } else if (i == blocks - 1) {
                return false;  //No obstacles found       
            }
        }
    }

    public static isOutOfBounds(endPiece: Piece): boolean {
        if (endPiece.file <= 3 && endPiece.rank <= 3) {
            return true; //Bottom left corner
        } else if (endPiece.file >= 12 && endPiece.rank <= 3) {
            return true; // Bottom right corner
        } else if (endPiece.file >= 3 && endPiece.rank <= 12) {
            return true; // Top left corner
        } else if (endPiece.file >= 12 && endPiece.rank >= 12) {
            return true; // Top right corner
        } else if (endPiece.file < 1 || endPiece.file > 14) {
            return true; //Out of file bounds
        } else if (endPiece.rank < 1 || endPiece.rank > 14) {
            return true;  //Out of rank bounds       
        } else {
            return false;
        }
    }   

    public static isSquareAvailable(game: Game, endPiece: Piece): boolean {
        var squareType : SquareType = BoardHelper.getSquareType(game, endPiece);
        if (squareType == SquareType.Enemy || squareType == SquareType.Friend) {
            return false;
        } else {
            return true;
         }
    } 
    
    public static isSquareEnemy(game: Game, endPiece: Piece): boolean {
        var squareType : SquareType = BoardHelper.getSquareType(game, endPiece);
        if (squareType == SquareType.Enemy ) {
            return true;
        } else {
            return false;
         }
    } 
    
    public static isSquareFriend(game: Game, endPiece: Piece): boolean {  
        var squareType : SquareType = BoardHelper.getSquareType(game, endPiece);       
        if ( squareType == SquareType.Friend) {
            return true;
        } else {
            return false;
         }
    } 

     //TODO: Test and add check for pawn attack
    public static isSquareAttacked(game: Game, endPiece: Piece): boolean {       
        //LineType.PositiveHorizontal
        var currentSquare: Piece = Piece.clone(endPiece);
        for (var i = endPiece.file; i <= 14; i++) {
            //Increment in the correct direction
            currentSquare.file = i;
            //Check if block is occupied
            if (!BoardHelper.isOutOfBounds(currentSquare) && !BoardHelper.isSquareAvailable(game, currentSquare)) {
                //At least one obstacle found
                var blockPiece = BoardHelper.getSquarePieceByObject(game, currentSquare);
                if ((endPiece.color != blockPiece.color) && ((blockPiece.type == PieceType.Rook) || (blockPiece.type == PieceType.Queen))) {
                    return true; //At least one rook or queen found
                } else {
                    break; //No rook or queen found                    
                }
            }
        }

        //LineType.NegativeHorizontal - currentSquare.file--;
        var currentSquare: Piece = Piece.clone(endPiece);
        for (var i = endPiece.file; i >= 1; i--) {
            //Increment in the correct direction
            currentSquare.file = i;
            //Check if block is occupied
            if (!BoardHelper.isOutOfBounds(currentSquare) && !BoardHelper.isSquareAvailable(game, currentSquare)) {
                //At least one obstacle found
                var blockPiece = BoardHelper.getSquarePieceByObject(game, currentSquare);
                if ((endPiece.color != blockPiece.color) && ((blockPiece.type == PieceType.Rook) || (blockPiece.type == PieceType.Queen))) {
                    return true; //At least one rook or queen found
                } else {
                    break; //No rook or queen found                    
                }
            }
        }

        //LineType.PositiveVerticle
        var currentSquare: Piece = Piece.clone(endPiece);
        for (var i = endPiece.rank; i <= 14; i++) {
            //Increment in the correct direction
            currentSquare.rank = i;
            //Check if block is occupied
            if (!BoardHelper.isOutOfBounds(currentSquare) && !BoardHelper.isSquareAvailable(game, currentSquare)) {
                //At least one obstacle found
                var blockPiece = BoardHelper.getSquarePieceByObject(game, currentSquare);
                if ((endPiece.color != blockPiece.color) && ((blockPiece.type == PieceType.Rook) || (blockPiece.type == PieceType.Queen))) {
                    return true; //At least one rook or queen found
                } else {
                    break; //No rook or queen found                    
                }
            }
        }

        //LineType.NegativeVerticle
        var currentSquare: Piece = Piece.clone(endPiece);
        for (var i = endPiece.rank; i >= 1; i--) {
            //Increment in the correct direction
            currentSquare.rank = i;
            //Check if block is occupied
            if (!BoardHelper.isOutOfBounds(currentSquare) && !BoardHelper.isSquareAvailable(game, currentSquare)) {
                //At least one obstacle found
                var blockPiece = BoardHelper.getSquarePieceByObject(game, currentSquare);
                if ((endPiece.color != blockPiece.color) && ((blockPiece.type == PieceType.Rook) || (blockPiece.type == PieceType.Queen))) {
                    return true; //At least one rook or queen found
                } else {
                    break; //No rook or queen found                    
                }
            }
        }

        //LineType.PositiveIncreaseDiagonal
        var currentSquare: Piece = Piece.clone(endPiece);
        for (var i = endPiece.rank; i <= 14; i++) {
            //Increment in the correct direction
            currentSquare.file = i;
            currentSquare.rank = i;
            //Check if block is occupied
            if (!BoardHelper.isOutOfBounds(currentSquare) && !BoardHelper.isSquareAvailable(game, currentSquare)) {
                //At least one obstacle found
                var blockPiece = BoardHelper.getSquarePieceByObject(game, currentSquare);
                if ((endPiece.color != blockPiece.color) && ((blockPiece.type == PieceType.Rook) || (blockPiece.type == PieceType.Queen))) {
                    return true; //At least one biship or queen found
                } else {
                    break; //No rook or queen found                    
                }
            }
        }

        //LineType.NegativeIncreaseDiagonal
        var currentSquare: Piece = Piece.clone(endPiece);
        for (var i = endPiece.rank; i >= 1; i--) {
            //Increment in the correct direction
            currentSquare.file = i;
            currentSquare.rank = i;
            //Check if block is occupied
            if (!BoardHelper.isOutOfBounds(currentSquare) && !BoardHelper.isSquareAvailable(game, currentSquare)) {
                //At least one obstacle found
                var blockPiece = BoardHelper.getSquarePieceByObject(game, currentSquare);
                if ((endPiece.color != blockPiece.color) && ((blockPiece.type == PieceType.Rook) || (blockPiece.type == PieceType.Queen))) {
                    return true; //At least one biship or queen found
                } else {
                    break; //No rook or queen found                    
                }
            }
        }

        //LineType.PositiveDecreaseDiagonal
        var currentSquare: Piece = Piece.clone(endPiece);
        for (var i = endPiece.rank; i <= 14; i++) {
            //Increment in the correct direction
            currentSquare.file = i;
            currentSquare.rank = endPiece.rank - i;
            //Check if block is occupied
            if (!BoardHelper.isOutOfBounds(currentSquare) && !BoardHelper.isSquareAvailable(game, currentSquare)) {
                //At least one obstacle found
                var blockPiece = BoardHelper.getSquarePieceByObject(game, currentSquare);
                if ((endPiece.color != blockPiece.color) && ((blockPiece.type == PieceType.Rook) || (blockPiece.type == PieceType.Queen))) {
                    return true; //At least one biship or queen found
                } else {
                    break; //No biship or queen found                    
                }
            }
        }

        //LineType.NegativeDecreaseDiagonal
        var currentSquare: Piece = Piece.clone(endPiece);
        for (var i = endPiece.rank; i >= 1; i--) {
            //Increment in the correct direction
            currentSquare.file = endPiece.file - i;
            currentSquare.rank = i;
            //Check if block is occupied
            if (!BoardHelper.isOutOfBounds(currentSquare) && !BoardHelper.isSquareAvailable(game, currentSquare)) {
                //At least one obstacle found
                var blockPiece = BoardHelper.getSquarePieceByObject(game, currentSquare);
                if ((endPiece.color != blockPiece.color) && ((blockPiece.type == PieceType.Rook) || (blockPiece.type == PieceType.Queen))) {
                    return true; //At least one biship or queen found
                } else {
                    break; //No rook or queen found                    
                }
            }
        }

        //Check horse positions, 8 points
        var currentSquare: Piece = Piece.clone(endPiece);
        var knightPotentialSquares: number[][] = [[2, 1], [-2, 1], [2, -1], [-2, -1], [1, 2], [-1, 2], [1, -2], [-1, -2]];
        for (var i = 0; i < knightPotentialSquares.length; i++) {
            currentSquare.file = knightPotentialSquares[i][0];
            currentSquare.rank = knightPotentialSquares[i][1];
            if (!BoardHelper.isOutOfBounds(currentSquare) && !BoardHelper.isSquareAvailable(game, currentSquare)) {
                //At least one obstacle found
                var blockPiece = BoardHelper.getSquarePieceByObject(game, currentSquare);
                if ((endPiece.color != blockPiece.color) && (blockPiece.type == PieceType.Knight)) {
                    return true; //At least one knight found
                }
            }
        }
        //If statements never triggered therfore no checks
        return false;
    }
}