//Angular, Material, Libraries
import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
//Services
import { AlertService } from "../services/alert.service";
import { GameService } from "../services/game.service";
import { UserService } from "../services/user.service";
//Objects
import { Piece } from "../models/piece";

@Component({
    selector: "chess-piece-component",
    templateUrl: "../../views/chess-piece.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class ChessPieceComponent implements OnInit {

    private static LOG_HEADER: string = 'CHESS:PIECE';
    private thisPiece: Piece;
    @Input() rank = Number;
    @Input() file = Number;
    @Input() pieces = Array<Piece>();   

    constructor() { }

    ngOnInit() { }

    private setPiece(piece: Piece) {
        this.thisPiece = piece;
    }   

}