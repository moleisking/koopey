//Core
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Objects
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Score } from "../models/score";

@Injectable()
export class ScoreService {

  private static LOG_HEADER: string = 'SCORE:SERVICE:';
  public score = new ReplaySubject<Score>();
  public scores = new ReplaySubject<Array<Score>>();

  constructor(private http: Http) { }

  /*********  Object *********/

  public getScore(): Observable<Score> {
    return this.score.asObservable();
  }

  public setScore(score: Score): void {
    this.score.next(score);
  }

  public getScores(): Observable<Array<Score>> {
    return this.scores.asObservable()
  }

  public setScores(scores: Array<Score>): void {
    this.scores.next(scores);
  }

  /*********  Create *********/

  public createOne(score : Score): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(score);
    var url = Config.system_backend_url + "/score/create/one";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public createMany(scores: Array<Score>): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(scores);
    var url = Config.system_backend_url + "/score/create/many";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Read *********/

  public readOne(score: Score): Observable<Score> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(score);
    var url = Config.system_backend_url + "/score/read/one";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().score }).catch(this.handleError);
  }

  public readManyByUser(): Observable<Array<Score>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/score/read/many";
    return this.http.get(url, options).map((res: Response) => { return res.json().scores }).catch(this.handleError);
  }

  /*********  Update *********/

  public updateOne(score: Score): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(score);
    var url = Config.system_backend_url + "/score/update/one";  
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateMany(scores: Array<Score>): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(scores);
    var url = Config.system_backend_url + "/score/update/many";  
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Delete *********/

  public delete(score: Score): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(score);
    var url = Config.system_backend_url + "/score/delete";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Error *********/

  private handleError(error: any) {
    return Observable.throw({ "ScoreService": { "Code": error.status, "Message": error.message } });
  }
}
