//Core
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject, Subject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Game } from "../models/game";
import { User } from "../models/user";
import { UserControlComponent } from "../components/user/control/user-control.component";

@Injectable()
export class GameService {

    private LOG_HEADER: string = 'GAME:SERVICE:';
    public game = new ReplaySubject<Game>();
    public games = new ReplaySubject<Array<Game>>();
    private counter = new Subject<number>();

    constructor(
        private http: Http,
        private translate: TranslateService
    ) { }

    /*********  Object *********/

    public getCounter(): Observable<number> {     
        return   this.counter.asObservable();      
    }

    public setCounter(counter: number) {        
        this.counter.next(counter);
    }

    public getGame(): Observable<Game> {
        return this.game.asObservable();
    }

    public setGame(game: Game) {
        this.game.next(game);
    }

    public getGames(): Observable<Array<Game>> {
        return this.games.asObservable();
    }

    public setGames(games: Array<Game>) {
        this.games.next(games);
    }

    /*********  Create *********/

    public create(game: Game): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(game);
        var url = Config.system_backend_url + "/game/create";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Read *********/

    public readCount(): Observable<Number> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/game/read/count/";
        return this.http.get(url, options).map((res: Response) => { return res.json().games.count }).catch(this.handleError);
    }

    public readStarted(): Observable<Number> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/game/read/count/started";
        return this.http.get(url, options).map((res: Response) => { return res.json().games.count }).catch(this.handleError);
    }

    public readCountEnded(): Observable<Number> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/game/read/count/ended";
        return this.http.get(url, options).map((res: Response) => { return res.json().games.count }).catch(this.handleError);
    }

    public readCountPlaying(): Observable<Number> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/game/read/count/playing";
        return this.http.get(url, options).map((res: Response) => { return res.json().games.count }).catch(this.handleError);
    }

    public readGame(game: Game): Observable<Game> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(game);
        var url = Config.system_backend_url + "/game/read/one";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().game }).catch(this.handleError);
    }

    public readGamesEnded(): Observable<Array<Game>> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/game/read/many/ended";
        return this.http.get(url, options).map((res: Response) => { return res.json().games }).catch(this.handleError);
    }

    public readGamesPlaying(): Observable<Array<Game>> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/game/read/many/playing";
        return this.http.get(url, options).map((res: Response) => { return res.json().games }).catch(this.handleError);
    }

    public readGamesStarted(): Observable<Array<Game>> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/game/read/many/started";
        return this.http.get(url, options).map((res: Response) => { return res.json().games }).catch(this.handleError);
    }


    public readMyGamesEnded(): Observable<Array<Game>> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/game/read/my/many/ended";
        return this.http.get(url, options).map((res: Response) => { return res.json().games }).catch(this.handleError);
    }

    public readMyGamesPlaying(): Observable<Array<Game>> {
        //Can be starting or playing game
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/game/read/many/my/playing";
        return this.http.get(url, options).map((res: Response) => { return res.json().games }).catch(this.handleError);
    }

    public readMyUserLosses(): Observable<number> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/game/read/my/loss/count";
        return this.http.get(url, options).map((res: Response) => { return res.json().user.losses }).catch(this.handleError);
    }

    public readMyUserWins(): Observable<number> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/game/read/my/win/count";
        return this.http.get(url, options).map((res: Response) => { return res.json().user.wins }).catch(this.handleError);
    }

    /*********  Update *********/

    public updateGameStart(game: Game): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(game);
        var url = Config.system_backend_url + "/game/update/start";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public updateGamePlaying(game: Game): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(game);
        var url = Config.system_backend_url + "/game/update/playing";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public updateGameEnd(game: Game): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(game);
        var url = Config.system_backend_url + "/game/update/end";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Delete *********/

    public delete(game: Game): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(game);
        var url = Config.system_backend_url + "/game/delete";
        return this.http.post(url, body, options).catch(this.handleError);
    }

    /*********  Error *********/

    private handleError(error: any) {
        return Observable.throw({ "GameService": { "Code": error.status, "Message": error.message } });
    }
}