import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject, Subject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Game } from "../models/game";

@Injectable()
export class GameService {
  public game = new ReplaySubject<Game>();
  public games = new ReplaySubject<Array<Game>>();
  private counter = new Subject<number>();

  public httpHeader = {
    headers: new HttpHeaders({
      Authorization: "JWT " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private translate: TranslateService
  ) {}

  public getCounter(): Observable<number> {
    return this.counter.asObservable();
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

  public create(game: Game): Observable<String> {
    var url = Config.system_backend_url + "/game/create";
    return this.httpClient.put<String>(url, game, this.httpHeader);
  }

  public count(): Observable<Number> {
    var url = Config.system_backend_url + "/game/read/count/";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public readStarted(): Observable<Number> {
    var url = Config.system_backend_url + "/game/read/count/started";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public countEnded(): Observable<Number> {
    var url = Config.system_backend_url + "/game/read/count/ended";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public countPlaying(): Observable<Number> {
    var url = Config.system_backend_url + "/game/read/count/playing";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public countMyUserLosses(): Observable<Number> {
    var url = Config.system_backend_url + "/game/read/my/loss/count";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public countMyUserWins(): Observable<Number> {
    var url = Config.system_backend_url + "/game/read/my/win/count";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public readGame(game: Game): Observable<Game> {
    var url = Config.system_backend_url + "/game/read/one";
    return this.httpClient.get<Game>(url, this.httpHeader);
  }

  public readGamesEnded(): Observable<Array<Game>> {
    var url = Config.system_backend_url + "/game/read/many/ended";
    return this.httpClient.get<Array<Game>>(url, this.httpHeader);
  }

  public readGamesPlaying(): Observable<Array<Game>> {
    var url = Config.system_backend_url + "/game/read/many/playing";
    return this.httpClient.get<Array<Game>>(url, this.httpHeader);
  }

  public readGamesStarted(): Observable<Array<Game>> {
    var url = Config.system_backend_url + "/game/read/many/started";
    return this.httpClient.get<Array<Game>>(url, this.httpHeader);
  }

  public readMyGamesEnded(): Observable<Array<Game>> {
    var url = Config.system_backend_url + "/game/read/my/many/ended";
    return this.httpClient.get<Array<Game>>(url, this.httpHeader);
  }

  public readMyGamesPlaying(): Observable<Array<Game>> {
    var url = Config.system_backend_url + "/game/read/many/my/playing";
    return this.httpClient.get<Array<Game>>(url, this.httpHeader);
  }

  public updateGameStart(game: Game): Observable<String> {
    var url = Config.system_backend_url + "/game/update/start";
    return this.httpClient.post<String>(url, game, this.httpHeader);
  }

  public updateGamePlaying(game: Game): Observable<String> {
    var url = Config.system_backend_url + "/game/update/playing";
    return this.httpClient.post<String>(url, game, this.httpHeader);
  }

  public updateGameEnd(game: Game): Observable<String> {
    var url = Config.system_backend_url + "/game/update/end";
    return this.httpClient.post<String>(url, game, this.httpHeader);
  }

  public delete(game: Game): Observable<String> {
    var url = Config.system_backend_url + "/game/delete";
    return this.httpClient.post<String>(url, game, this.httpHeader);
  }
}
