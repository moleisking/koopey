import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject, Subject } from "rxjs";
import { Environment } from "src/environments/environment";
import { Game } from "../models/game";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class GameService extends BaseService {
  public game = new ReplaySubject<Game>();
  public games = new ReplaySubject<Array<Game>>();
  private counter = new Subject<number>();

  constructor(
    protected httpClient: HttpClient
  ) {
    super(httpClient);
  }

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
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/create";
    return this.httpClient.put<String>(url, game, this.privateHeader());
  }

  public count(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/count/";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public readStarted(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/count/started";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public countEnded(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/count/ended";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public countPlaying(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/count/playing";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public countMyUserLosses(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/my/loss/count";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public countMyUserWins(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/my/win/count";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public readGame(game: Game): Observable<Game> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/one";
    return this.httpClient.get<Game>(url, this.privateHeader());
  }

  public readGamesEnded(): Observable<Array<Game>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/many/ended";
    return this.httpClient.get<Array<Game>>(url, this.privateHeader());
  }

  public readGamesPlaying(): Observable<Array<Game>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/many/playing";
    return this.httpClient.get<Array<Game>>(url, this.privateHeader());
  }

  public readGamesStarted(): Observable<Array<Game>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/many/started";
    return this.httpClient.get<Array<Game>>(url, this.privateHeader());
  }

  public readMyGamesEnded(): Observable<Array<Game>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/my/many/ended";
    return this.httpClient.get<Array<Game>>(url, this.privateHeader());
  }

  public readMyGamesPlaying(): Observable<Array<Game>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/read/many/my/playing";
    return this.httpClient.get<Array<Game>>(url, this.privateHeader());
  }

  public updateGameStart(game: Game): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/update/start";
    return this.httpClient.post<String>(url, game, this.privateHeader());
  }

  public updateGamePlaying(game: Game): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/update/playing";
    return this.httpClient.post<String>(url, game, this.privateHeader());
  }

  public updateGameEnd(game: Game): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/update/end";
    return this.httpClient.post<String>(url, game, this.privateHeader());
  }

  public delete(game: Game): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/game/delete";
    return this.httpClient.post<String>(url, game, this.privateHeader());
  }
}
