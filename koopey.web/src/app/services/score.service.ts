import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Score } from "../models/score";

@Injectable()
export class ScoreService {
  public score = new ReplaySubject<Score>();
  public scores = new ReplaySubject<Array<Score>>();

  public httpHeader = {
    headers: new HttpHeaders({
      Authorization: "JWT " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
    }),
  };

  constructor(private httpClient: HttpClient) {}

  public getScore(): Observable<Score> {
    return this.score.asObservable();
  }

  public setScore(score: Score): void {
    this.score.next(score);
  }

  public getScores(): Observable<Array<Score>> {
    return this.scores.asObservable();
  }

  public setScores(scores: Array<Score>): void {
    this.scores.next(scores);
  }

  public createOne(score: Score): Observable<String> {
    var url = Config.system_backend_url + "/score/create/one";
    return this.httpClient.put<String>(url, score, this.httpHeader);
  }

  public createMany(scores: Array<Score>): Observable<String> {
    var url = Config.system_backend_url + "/score/create/many";
    return this.httpClient.put<String>(url, scores, this.httpHeader);
  }

  public delete(score: Score): Observable<String> {
    var url = Config.system_backend_url + "/score/delete";
    return this.httpClient.post<String>(url, score, this.httpHeader);
  }

  public readOne(score: Score): Observable<Score> {
    var url = Config.system_backend_url + "/score/read/one";
    return this.httpClient.get<Score>(url, this.httpHeader);
  }

  public readManyByUser(): Observable<Array<Score>> {
    var url = Config.system_backend_url + "/score/read/many";
    return this.httpClient.get<Array<Score>>(url, this.httpHeader);
  }

  public updateOne(score: Score): Observable<String> {
    var url = Config.system_backend_url + "/score/update/one";
    return this.httpClient.post<String>(url, score, this.httpHeader);
  }

  public updateMany(scores: Array<Score>): Observable<String> {
    var url = Config.system_backend_url + "/score/update/many";
    return this.httpClient.post<String>(url, scores, this.httpHeader);
  }

  private handleError(error: any) {
    return Observable.throw({
      ScoreService: { Code: error.status, Message: error.message },
    });
  }
}
