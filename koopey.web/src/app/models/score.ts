const SHA256 = require("crypto-js/sha256");
import { UUID } from 'angular2-uuid';
import { User } from "../models/user";
import { Game, GameType } from "../models/game";
//import { WebAnimationsPlayer } from '@angular/animations/browser';

export class Score {

    public id: string = UUID.UUID();
    public userId: string;
    public type: string = GameType.FourWayChess;
    public wins: number = 0;
    public draws: number = 0;
    public losses: number = 0;
    public elo: number = 1000;
    public hash: string;
    public createTimeStamp: number = 0;
    public readTimeStamp: number = 0;
    public updateTimeStamp: number = 0;
    public deleteTimeStamp: number = 0;

    private static EloApproximation(Ra: number, Rb: number, Wa: boolean): number {
        var K: number = 32;//Max points to add or subtract
        var C: number = 400;

        // Probability of Player B Winning
        var Pb: number = 1 * 1 / (1 + 1 * (Math.pow(10, 1 * (Ra - Rb) / C))); //Score.Probability(Ra, Rb);

        // Probability of Player A Winning
        var Pa: number = 1 * 1 / (1 + 1 * (Math.pow(10, 1 * (Rb - Ra) / C))); //Score.Probability(Rb, Ra);

        if (Wa == true) {
            // Case -1 When Player A wins   
            return Math.round(Ra + K * (1 - Pa));
        } else {
            // Case -2 When Player B wins   
            return Math.round(Ra + K * (0 - Pa));
        }
    }


    public static ELO(game: Game, player: User, competitors: Array<User>): number {
        if (game && player) {
            var playerScore: Score = Score.readFourWayChess(player.scores);
            if (playerScore) {
                var totalElo: number = 0;
                for (var i = 0; i < competitors.length; i++) {
                    var competitorScore: Score = Score.readFourWayChess(competitors[i].scores);
                    if (competitors[i].score > player.score) {
                        console.log("competitors[i].score > player.score");
                        var s1: number = Score.EloApproximation(playerScore.elo, competitorScore.elo, false);
                        totalElo += s1;
                    } else if (competitors[i].score == player.score) {
                        console.log("competitors[i].score == player.score");
                        var s2: number = Score.EloApproximation(playerScore.elo, competitorScore.elo, false)
                        totalElo += s2;
                    } else {
                        console.log("else");
                        var s3: number = Score.EloApproximation(playerScore.elo, competitorScore.elo, true)
                        totalElo += s3;
                    }
                }
                return totalElo / (game.users.length - 1);
            } else {
                return 0;
            }
        }
    }


    public static compareHash(scoreA: Score, scoreB: Score): boolean {
        return (Score.toHash(scoreA) == Score.toHash(scoreB)) ? true : false;
    }

    public static toHash(score: Score): string {
        return SHA256(score.id + score.type + score.wins + score.losses + score.elo).toString();
    }

    public static containsId(scores: Array<Score>, id: string): boolean {
        if (scores && scores.length > 0) {
            for (var i = 0; i < scores.length; i++) {
                //Exclude current item
                if (scores[i] &&
                    scores[i].id == id) {
                    //Current item is not unique                     
                    return true;
                } else if (i == scores.length - 1) {
                    //Last item and unique  
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static containsType(scores: Array<Score>, gameType: GameType): boolean {
        if (scores && scores.length > 0) {
            for (var i = 0; i < scores.length; i++) {
                //Exclude current item
                if (scores[i] &&
                    scores[i].type == gameType) {
                    //Current item is not unique                     
                    return true;
                } else if (i == scores.length - 1) {
                    //Last item and unique  
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static containsFourWayChess(scores: Array<Score>): boolean {
        return Score.containsType(scores, GameType.FourWayChess);
    }

    public static containsTwoWayChess(scores: Array<Score>): boolean {
        return Score.containsType(scores, GameType.TwoWayChess);
    }

    public static create(scores: Array<Score>, score: Score): Array<Score> {
        if (scores.length == 0 || !Score.containsId(scores, score.id)) {
            scores.push(score);
            return scores;
        } else {
            return scores;
        }
    }

    public static read(scores: Array<Score>, score: Score): Score {
        if (scores && scores.length > 0) {
            for (var i = 0; i < scores.length; i++) {
                if (scores[i] &&
                    scores[i].id == score.id) {
                    return scores[i];
                }
            }
        }
    }

    public static readRank(player: User, competitors: Array<User>): number {
        var bigger: number = 1;           
        for (var i = 0; i < competitors.length; i++) {
            if (competitors[i].score > player.score) {
                bigger++; 
            }
        }
        return bigger;
    }

    public static readType(scores: Array<Score>, gameType: GameType): Score {
        for (var i = 0; i < scores.length; i++) {
            if (scores[i].type == gameType) {
                return scores[i];
            }
        }
    }

    public static readFourWayChess(scores: Array<Score>): Score {
        return Score.readType(scores, GameType.FourWayChess);
    }

    public static readTwoWayChess(scores: Array<Score>): Score {
        return Score.readType(scores, GameType.TwoWayChess);
    }

    public static update(scores: Array<Score>, score: Score): Array<Score> {
        if (scores && scores.length > 0) {
            for (var i = 0; i <= scores.length; i++) {
                if (scores[i] &&
                    scores[i].id == score.id) {
                    scores[i] == score;
                    return scores;
                }
            }
        }
    }

    public static delete(scores: Array<Score>, score: Score): Array<Score> {
        if (scores && scores.length > 0) {
            for (var i = 0; i <= scores.length; i++) {
                if (scores[i] &&
                    scores[i].id == score.id) {
                    scores.splice(i, 1);
                    return scores;
                }
            }
        }
    }
}