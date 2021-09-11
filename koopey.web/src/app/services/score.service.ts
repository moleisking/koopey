import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Environment } from "src/environments/environment";
import { Score } from "../models/score";
import { BaseService } from "./base.service";

@Injectable()
export class ScoreService extends BaseService {
  public score = new ReplaySubject<Score>();
  public scores = new ReplaySubject<Array<Score>>();

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
    var url = Environment.ApiUrls.KoopeyApiUrl + "/score/create/one";
    return this.httpClient.put<String>(url, score, this.httpHeader);
  }

  public createMany(scores: Array<Score>): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/score/create/many";
    return this.httpClient.put<String>(url, scores, this.httpHeader);
  }

  public delete(score: Score): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/score/delete";
    return this.httpClient.post<String>(url, score, this.httpHeader);
  }

  public readOne(score: Score): Observable<Score> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/score/read/one";
    return this.httpClient.get<Score>(url, this.httpHeader);
  }

  public readManyByUser(): Observable<Array<Score>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/score/read/many";
    return this.httpClient.get<Array<Score>>(url, this.httpHeader);
  }

  public updateOne(score: Score): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/score/update/one";
    return this.httpClient.post<String>(url, score, this.httpHeader);
  }

  public updateMany(scores: Array<Score>): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/score/update/many";
    return this.httpClient.post<String>(url, scores, this.httpHeader);
  }

  private handleError(error: any) {
    return Observable.throw({
      ScoreService: { Code: error.status, Message: error.message },
    });
  }
}
