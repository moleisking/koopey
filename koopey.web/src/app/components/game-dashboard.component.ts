//Angular, Material, Libraries
import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../services/alert.service";
import { AuthService } from "../services/auth.service";
import { ScoreService } from "../services/score.service";
//Objects
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { GameType } from "../models/game";
import { Score } from "../models/score";
import { User } from "../models/user";

@Component({
    selector: "game-dashboard-component",
    templateUrl: "../../views/game-dashboard.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class GameDashboardComponent implements OnInit {

    //Objects 
    private authUser: User = new User();
    private scores: Array<Score> = new Array<Score>();
    //Strings  
    private LOG_HEADER: string = 'GAME:DASHBOARD:';
    //Numbers
    private columns: number = 1;
    private screenWidth: number = window.innerWidth;

    constructor(
        private alertService: AlertService,
        private router: Router,
        private sanitizer: DomSanitizer,
        private scoreService: ScoreService
    ) { }

    ngOnInit() {
        this.readScores();
    }

    private onScreenSizeChange(event: any) {
        this.screenWidth = window.innerWidth;
        if (this.screenWidth <= 512) {
            this.columns = 1;
        } else if ((this.screenWidth > 512) && (this.screenWidth <= 1024)) {
            this.columns = 2;
        } else if ((this.screenWidth > 1024) && (this.screenWidth <= 2048)) {
            this.columns = 3;
        } else if ((this.screenWidth > 2048) && (this.screenWidth <= 4096)) {
            this.columns = 4;
        }
    }


    private gotoFourWayChess() {
         if (this.scores && Score.containsFourWayChess(this.scores)) {
            //FourWayChess score exists, so save score;  
            localStorage.setItem("scores", JSON.stringify(this.scores));
            this.router.navigate(["/game/fourwaychess"]);
        } else {
            //No FourWayChess scores object so create new score object                 
            this.createFourWayChessScore();
        }       
    }

    private gotoTwoWayChess() {
        if (this.scores && Score.containsTwoWayChess(this.scores)) {
            //TwoWayChess score exists, so save score;  
            localStorage.setItem("scores", JSON.stringify(this.scores))
            this.router.navigate(["/game/twowaychess"])
        } else {
            //No TwoWayChess scores object so create new score object                 
            this.createTwoWayChessScore();
        }          
    }

    private hasGames(): boolean {
        return Config.business_model_games;
    }

    private createFourWayChessScore() {
        console.log("createFourWayChessScore()");
        //Create FourWayChess score object to push to user
        var score = new Score();
        score.type = GameType.FourWayChess;
        score.userId = localStorage.getItem("id");
        score.elo = 1000;
        score.wins = 0;
        score.losses = 0;
        score.draws = 0;
        //Save FourWayChess score object to user

        this.scoreService.createOne(score).subscribe(
            (alert: Alert) => {
                if (Alert.isSuccess(alert)) {
                    console.log(alert);
                    if (!Config.system_production) { console.log("Create FourWayChessScore success"); }
                } else {
                    console.log(alert);
                    if (!Config.system_production) { console.log("Create FourWayChessScore error"); }
                }
            },
            (error) => { if (!Config.system_production) { console.log(error); } },
            () => { 
                console.log("this.router.navigate([game-fourwaychess])");
                setTimeout(() => {
                    this.router.navigate(["/game/fourwaychess"])
                }, 600);
            });
    }

    private createTwoWayChessScore() {
        console.log("createTwoWayChessScore()");
        //Create TwoWayChess score object to push to user
        var score = new Score();
        score.type = GameType.TwoWayChess;
        score.userId = localStorage.getItem("id");
        score.elo = 1000;
        score.wins = 0;
        score.losses = 0;
        score.draws = 0;
        //Save TwoWayChess score object to user
        this.scoreService.createOne(score).subscribe(
            (alert: Alert) => {
                if (Alert.isSuccess(alert)) {

                    if (!Config.system_production) { console.log("Create TwoWayChessScore success"); }
                } else {
                    if (!Config.system_production) { console.log("Create TwoWayChessScore error"); }
                }
            },
            (error) => { if (!Config.system_production) { console.log(error); } },
            () => { 
                console.log("this.router.navigate([game-twowaychess])");
                setTimeout(() => {
                    this.router.navigate(["/game/twowaychess"])
                }, 600);  
            });
    }

    private readScores() {
        this.scoreService.readManyByUser().subscribe(
            (scores: Array<Score>) => { this.scores = scores; },
            (error) => { if (!Config.system_production) { console.log("ngOnInit:error"); console.log(error); } },
            () => { });
    } 
}
