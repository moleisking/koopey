//Angular, Material, Libraries
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Observable, Observer } from "rxjs";
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../../../services/alert.service";
import { AuthService } from "../../../services/auth.service";
import { GameService } from "../../../services/game.service";
import { ScoreService } from "../../../services/score.service";
import { UserService } from "../../../services/user.service";
//Helpers
import { ChessHelper, MoveType } from "../../../helpers/ChessHelper";
import { BoardHelper, LineType, SquareType } from "../../../helpers/BoardHelper";

//Objects
import { Alert } from "../../../models/alert";
import { Config } from "../../../config/settings";
import { Game, PlayerType, GameType } from "../../../models/game";
import { Piece, PieceType } from "../../../models/piece";
import { Point } from "ng2-img-cropper/src/model/point";
import { User } from "../../../models/user";
import { Score } from "../../../models/score";

@Component({
    selector: "four-way-chess-board-component",
    templateUrl: "../../views/four-way-chess-board.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class FourWayChessBoardComponent implements OnInit {

    private static LOG_HEADER: string = 'CHESS:BOARD';
    private authSubscription: Subscription;
    private counterSubscription: Subscription;
    private gameSubscription: Subscription;
    private authUser: User = new User();
    private counterMax: number = 10;
    private playerMax: number = 2;
    private file: number = 0;
    private rank: number = 0;
    private currentPiece: Piece;
    private game: Game = new Game();

    constructor(
        private alertService: AlertService,
        private authenticateService: AuthService,
        private gameService: GameService,
        private userService: UserService,
        private scoreService: ScoreService,
        protected router: Router
    ) { }

    ngOnInit() {
        this.authUser = this.authenticateService.getLocalUser();
        //Read past games
        this.scoreService.readManyByUser().subscribe(
            (scores: Array<Score>) => { this.authUser.scores = scores; },
            (error) => { if (!Config.system_production) { console.log("ngOnInit:error"); console.log(error); } },
            () => { });
    }

    ngAfterContentInit() {
        //Check to see if refresh with existing playing games
        this.gameSubscription = this.gameService.readMyGamesPlaying().subscribe(
            (games: Array<Game>) => {
                if (games && games.length > 0 && games[0].deleteTimeStamp != 0 && games[0].users.length == 4) {
                    console.log("Continue playing game");
                    //Continue playing game
                    this.game = games[0];
                    this.incrementClock();
                    console.log(this.game);
                } else if (games && games.length > 0 && games[0].users.length <= 4) {
                    console.log("Continue waiting for players");
                    //Continue waiting for players
                    this.game = games[0];
                    console.log(this.game);

                    //TODO:Remove later
                    if (this.game.users.length == 2) {

                        this.incrementClock();
                    } else {
                        if (!Config.system_production) {
                            console.log("Not enough players to incrementClock");
                            console.log(this.game.users)
                        }
                    }

                } else {
                    console.log("No game found so search for a new starting game");
                    //No game found so search for a new starting game
                    this.gameSubscription = this.gameService.readGamesStarted().subscribe(
                        (games) => {
                            if (games && games.length > 0 && games[0].users.length < 4) {
                                console.log("Join a started game");
                                //Join a started game
                                this.game = games[0];
                                this.updateGame();
                                console.log(this.game);
                                if (this.game.users.length == 4) {
                                    this.incrementClock();
                                }
                            } else {
                                console.log("No started games found do start new game");
                                //No started games found do start new game
                                this.createGame();
                                console.log(this.game);
                            }
                        },
                        (error) => { if (!Config.system_production) { console.log(error); } },
                        () => { }
                    );
                }
            },
            (error) => { if (!Config.system_production) { console.log(error); } },
            () => {
                //Always start sync to update game
                if (this.game && this.game.users.length > 0) {
                    this.syncGame();
                } else {
                    if (!Config.system_production) { console.log("syncGame not statrted due to null game"); }
                }
            }
        );
    }

    ngOnDestroy() {
        if (this.authSubscription) {
            this.authSubscription.unsubscribe();
        }
        if (this.counterSubscription) {
            this.counterSubscription.unsubscribe();
        }
        if (this.gameSubscription) {
            this.gameSubscription.unsubscribe();
        }
    }



    private createGame() {
        console.log("createGame");
        console.log(this.authUser);
        //Create default values
        this.game = new Game();
        this.game.token = PlayerType.Blue;
        // this.game.scores = new Array<number>(0, 0, 0, 0);//B,G,R,Y
        this.game.defeats = new Array<boolean>(false, false, false, false);//B,G,R,Y
        this.game.pieces = new Array<Piece>(
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
        //Add new game to user object
        console.log("SET  this.game.users.push(User.simplify(this.authUser))");
        this.authUser.score = 0;
        this.game.users.push(User.simplify(this.authUser));
        console.log(this.game);
        //Choose color for player, starting at blue
        this.assignColorToPlayer()
        //Add new game to database of games
        this.gameSubscription = this.gameService.create(this.game).subscribe(
            (alert) => { if (!Config.system_production) { console.log(alert); } },
            (error) => { if (!Config.system_production) { console.log(error); } },
            () => { }
        );
    }

    private readGame() {
        this.gameSubscription = this.gameService.readGame(this.game).subscribe(
            (game: Game) => { this.game = game; },
            (error) => { if (!Config.system_production) { console.log(error); } },
            () => { }
        );
    }

    private readScores() {
        this.scoreService.readManyByUser().subscribe(
            (scores: Array<Score>) => { this.authUser.scores = scores; },
            (error) => { if (!Config.system_production) { console.log("ngOnInit:error"); console.log(error); } },
            () => { });
    }

    private updateGame() {
        this.game.users.push(this.authUser);
        this.assignColorToPlayer();
        this.gameSubscription = this.gameService.updateGameStart(this.game).subscribe(
            (alert) => { if (!Config.system_production) { console.log(alert); } },
            (error) => { if (!Config.system_production) { console.log(error); } },
            () => { }
        );
    }

    private updateGameEnd() {
        this.game.users.push(this.authUser);
        this.gameSubscription = this.gameService.updateGameEnd(this.game).subscribe(
            (alert) => { if (!Config.system_production) { console.log(alert); } },
            (error) => { if (!Config.system_production) { console.log(error); } },
            () => { }
        );
    }

    private updateGamePlaying() {
        this.gameSubscription = this.gameService.updateGamePlaying(this.game).subscribe(
            (alert) => { if (!Config.system_production) { /*console.log(alert);*/ } },
            (error) => { if (!Config.system_production) { console.log(error); } },
            () => { }
        );
    }

    private updateScore(score: Score) {
        this.scoreService.updateOne(score).subscribe(
            (alert: Alert) => { },
            (error) => { if (!Config.system_production) { console.log(error); } },
            () => { });
    }

    private setPosition(file: number, rank: number) {
        this.file = file;
        this.rank = rank;
    }

    private dragStart() {
        console.log("dragStart()");
        if (this.isMyMove()) {
            console.log("dragStart():true");
            this.currentPiece = Piece.read(this.game.pieces, this.file, this.rank);
        } else {
            console.log("dragStart():false");
        }
    }

    private dragEnd() {
        console.log("dragEnd()");
        if (this.isMyMove()) {
            setTimeout(() => {
                console.log(this.file);
                console.log(this.rank);
                if (!Piece.isEmpty(this.currentPiece)) {
                    //NOTE: Need to clone Piece to avoid Object type
                    var startPiece: Piece = Piece.clone(this.currentPiece);
                    var endPiece: Piece = Piece.clone(this.currentPiece);
                    endPiece.file = this.file;
                    endPiece.rank = this.rank;


                    //TEST
                    //console.log("isSquareAttacked:" + BoardHelper.isSquareAttacked(this.game, endPiece))


                    if (ChessHelper.isLegalMove(this.game, startPiece, endPiece)) {
                        console.log("this.isLegalMove:true")
                        var endSquareType: SquareType = BoardHelper.getSquareType(this.game, endPiece);
                        if (endSquareType == SquareType.Enemy) {
                            console.log("this.isCaptureMove:true")
                            //Add to score and remove piece
                            var currentSquarePiece = BoardHelper.getSquarePieceByObject(this.game, endPiece);
                            this.incrementPlayerScore(currentSquarePiece);
                            this.game.pieces = Piece.delete(this.game.pieces, currentSquarePiece);
                            //Add move to record
                            this.game.moves.push(ChessHelper.AlgebraicNotation(startPiece, currentSquarePiece, MoveType.Capture));
                            //Remove pieces is King capture
                            if (currentSquarePiece.type == PieceType.King && currentSquarePiece.color != PlayerType.Grey) {
                                //Remove player from game
                                Game.deletePlayer(this.game, currentSquarePiece.color);
                                if (Game.isEnd(this.game)) {
                                    console.log("Game Over");
                                    this.updateGameEnd();
                                    this.updatePlayerELOs()
                                }
                            }
                        } else if (endSquareType == SquareType.Obstacle) {
                            var currentSquarePiece = BoardHelper.getSquarePieceByObject(this.game, endPiece);
                            this.game.pieces = Piece.delete(this.game.pieces, currentSquarePiece);
                            //Add move to record
                            this.game.moves.push(ChessHelper.AlgebraicNotation(startPiece, currentSquarePiece, MoveType.Capture));
                        } else if (ChessHelper.isLegalPawnMove(this.game, startPiece, endPiece) && ChessHelper.isPawnPromotionMove(endPiece)) {
                            endPiece.type = PieceType.Queen;
                            endPiece.value = 9;
                            this.game.pieces = Piece.update(this.game.pieces, endPiece);
                        } else {
                            //Add move to record
                            this.game.moves.push(ChessHelper.AlgebraicNotation(startPiece, endPiece, MoveType.Normal));
                        }

                        //Increment move count of piece
                        endPiece.moves++;
                        //Move piece
                        this.game.pieces = Piece.update(this.game.pieces, endPiece);
                        //Move token and save game to server
                        this.nextPlayerMove();

                    } else {
                        console.log("this.isLegalMove:false")
                    }
                } else {
                    console.log("Piece.isEmpty:true")
                }


            }, 600);
        } else {
            console.log("MyMove:false");
        }
    }

    private syncGame() {
        console.log("syncGame()");
        //Unsubscribe to previous counts
        if (this.gameSubscription) {
            this.gameSubscription.unsubscribe();
        }
        //Counts every 5 seconds up to 180
        const gameObservable = Observable.interval(3000).take(36);
        console.log("syncGame() 3");
        this.gameSubscription = gameObservable.subscribe(() => {
            if (this.game) {
                //console.log("syncGame() true");
                //read latest copy of game
                this.gameService.readGame(this.game).subscribe(
                    (game) => {
                        if (!Game.isClone(this.game, game)) {
                            this.game = game;
                            console.log("syncGame() NEW game");
                        } else {
                            //   console.log("syncGame() old game");
                        }
                    },
                    (error) => { if (!Config.system_production) { console.log(error); } },
                    () => { }
                );
            }
        });
    }

    private print() {
        console.log("print()");
        console.log("MyPlayer:" + Game.readMyPlayerType(this.game.users));
        console.log("Turn:" + this.game.token);
        console.log(this.game);
        console.log(this.currentPiece);
        this.updatePlayerELOs();
    }

    private updatePlayerELOs() {
        console.log("calculateElos()")
        for (var i = 0; i < this.game.users.length; i++) {
            if (!User.isEmpty(this.game.users[i])) {
                var competitors = User.delete(User.clones(this.game.users), this.game.users[i])
                console.log(this.game.users[i].alias);
                var score: Score = Score.readFourWayChess(this.game.users[i].scores);
                var rank: number = Score.readRank(this.game.users[i], competitors)
                score.elo = Score.ELO(this.game, this.game.users[i], competitors);
                if (rank == 1) {
                    score.wins = score.wins + 1
                } else if (rank == 2 || rank == 3) {
                    score.draws = score.draws + 1
                } else if (rank == 4) {
                    score.losses = score.losses + 1
                }

                this.updateScore(score);
                /* console.log(this.game.users[i].score);
                 console.log(this.game.users[i].scores[0].elo);
                 console.log(competitors);
                 console.log(Score.ELO(this.game, this.game.users[i], competitors));*/
            }
        }
    }

    private incrementClock() {
        //Unsubscribe to previous counts
        if (this.counterSubscription) {
            this.counterSubscription.unsubscribe();
        }
        //Counts from 1 to 15 once
        const counterObservable = Observable.interval(1000).timeInterval() //..take(1).r.repeat(1);
        this.counterSubscription = counterObservable.subscribe(() => {
            if (this.game) {
                if (this.game.counter >= this.counterMax) {
                    //Add move timeout to history
                    this.game.moves.push(ChessHelper.AlgebraicNotation(null, null, MoveType.Timeout));
                    //Turn over, counter set in nextPlayerMove()  
                    this.nextPlayerMove();
                } else {
                    //Increment counter
                    this.game.counter++;
                }
            }
        });
    }

    private nextPlayerMove() {
        if (this.game) {
            if (this.game.token == PlayerType.Blue && this.game.defeats[0] == false) {
                this.game.token = PlayerType.Green;
                this.game.counter = 1;
                this.updateGamePlaying();
            } else if (this.game.token == PlayerType.Green && this.game.defeats[1] == false) {
                this.game.token = PlayerType.Blue;//"R" TODO change
                this.game.counter = 1;
                this.updateGamePlaying();
            } else if (this.game.token == PlayerType.Red && this.game.defeats[2] == false) {
                this.game.token = PlayerType.Yellow;
                this.game.counter = 1;
                this.updateGamePlaying();
            } else if (this.game.token == PlayerType.Yellow && this.game.defeats[3] == false) {
                this.game.token = PlayerType.Blue;
                this.game.counter = 1;
                this.updateGamePlaying();
            }
        }
    }

    private assignColorToPlayer() {
        if (this.game) {
            if (this.game.users.length == 1) {
                this.game.users[0].player = PlayerType.Blue;
            } else if (this.game.users.length == 2) {
                this.game.users[1].player = PlayerType.Green;
            } else if (this.game.users.length == 3) {
                this.game.users[2].player = PlayerType.Red;
            } else if (this.game.users.length == 4) {
                this.game.users[3].player = PlayerType.Yellow;
            }
        }
    }

    private incrementPlayerScore(piece: Piece) {
        //User score needed in case of game end
        if (this.game) {
            if (this.game.users.length >= 1 && this.game.token == PlayerType.Blue) {
                this.game.users[0].score += piece.value;
            } else if (this.game.users.length >= 2 && this.game.token == PlayerType.Green) {
                this.game.users[1].score += piece.value;
            } else if (this.game.users.length == 3 && this.game.token == PlayerType.Red) {
                this.game.users[2].score += piece.value;
            } else if (this.game.users.length == 4 && this.game.token == PlayerType.Yellow) {
                this.game.users[3].score += piece.value;
            }
        }
    }

    private isMyMove(): boolean {
        var myPlayerColor = Game.readMyPlayerType(this.game.users);
        if (myPlayerColor == this.game.token) {
            return true;
        } else {
            return false;
        }
    }
}