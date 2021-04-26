import { UUID } from "angular2-uuid";
import { PlayerType } from "./game";
const SHA256 = require("crypto-js/sha256");

export enum PieceType {
  Bishop = "Bishop",
  King = "King",
  Knight = "Knight",
  Pawn = "Pawn",
  Queen = "Queen",
  Rook = "Rook",
}

export class Piece {
  public id: string = UUID.UUID();
  public type: PieceType; //K,Q,R,N,P
  public color: PlayerType; //B,G,R,Y
  public value: number = 1; //1-20
  public moves: number = 0; //1-20
  public file: number = 0; // 0-13
  public rank: number = 0; //0-13

  constructor(
    color: PlayerType,
    type: PieceType,
    value: number,
    file: number,
    rank: number
  ) {
    this.type = type;
    this.color = color;
    this.value = value;
    this.file = file;
    this.rank = rank;
  }

  public static compareHash(pieceA: Piece, pieceB: Piece): boolean {
    return Piece.toHash(pieceA) == Piece.toHash(pieceB) ? true : false;
  }

  public static toHash(piece: Piece): string {
    return SHA256(
      piece.id +
        piece.type +
        piece.color +
        piece.value +
        piece.file +
        piece.moves +
        piece.rank
    ).toString();
  }

  public static isEmpty(piece: Piece): boolean {
    return piece == null ||
      piece.type == null ||
      piece.color == null ||
      piece.file == 0 ||
      piece.rank == 0
      ? true
      : false;
  }

  public static contains(pieces: Array<Piece>, piece: Piece): boolean {
    if (pieces && pieces.length > 0) {
      for (var i = 0; i < pieces.length; i++) {
        if (pieces[i] && pieces[i].id == piece.id) {
          //Current item is not unique
          return true;
        } else if (i == pieces.length - 1) {
          //Last item and unique
          return false;
        }
      }
    } else {
      return false;
    }
  }

  public static clone(piece: Piece): Piece {
    var p: Piece = new Piece(
      piece.color,
      piece.type,
      piece.value,
      piece.file,
      piece.rank
    );
    p.id = piece.id;
    p.moves = piece.moves;
    return p;
  }

  public static create(pieces: Array<Piece>, piece: Piece): Array<Piece> {
    if (!Piece.contains(pieces, piece)) {
      pieces.push(piece);
      return pieces;
    } else {
      return pieces;
    }
  }

  public static equal(pieceA: Piece, pieceB: Piece): boolean {
    if (
      pieceA.id == pieceB.id &&
      pieceA.type == pieceB.type &&
      pieceA.color == pieceB.color &&
      pieceA.file == pieceB.file &&
      pieceA.rank == pieceB.rank
    ) {
      return true;
    } else {
      return false;
    }
  }

  public static equalPiece(pieceA: Piece, pieceB: Piece): boolean {
    if (
      pieceA.id == pieceB.id &&
      pieceA.type == pieceB.type &&
      pieceA.color == pieceB.color
    ) {
      return true;
    } else {
      return false;
    }
  }

  public static equalPosition(pieceA: Piece, pieceB: Piece): boolean {
    if (pieceA.file == pieceB.file && pieceA.rank == pieceB.rank) {
      return true;
    } else {
      return false;
    }
  }

  public static read(pieces: Array<Piece>, file: number, rank: number): Piece {
    if (pieces && pieces.length > 0) {
      for (var i = 0; i < pieces.length; i++) {
        if (pieces[i] && pieces[i].file == file && pieces[i].rank == rank) {
          return pieces[i];
        }
      }
    }
  }

  public static update(pieces: Array<Piece>, piece: Piece): Array<Piece> {
    if (pieces && pieces.length > 0) {
      for (var i = 0; i < pieces.length; i++) {
        if (pieces[i] && pieces[i].id == piece.id) {
          pieces[i] = piece;
          return pieces;
        }
      }
    }
  }

  public static delete(pieces: Array<Piece>, piece: Piece): Array<Piece> {
    if (pieces && pieces.length > 0) {
      for (var i = 0; i < pieces.length; i++) {
        if (pieces[i] && pieces[i].id == piece.id) {
          pieces.splice(i, 1);
          return pieces;
        }
      }
    }
  }
}
