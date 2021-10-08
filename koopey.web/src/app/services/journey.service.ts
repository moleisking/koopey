import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Article } from "../models/article";
import { Asset } from "../models/asset";
import { User } from "../models/user";
import { Environment } from "src/environments/environment";
import { Jouney } from "../models/jouney";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class JouneyService extends BaseService {
  public jouney = new ReplaySubject<Jouney>();
  public jouneys = new ReplaySubject<Array<Jouney>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getJouney(): Observable<Jouney> {
    return this.jouney.asObservable();
  }

  public setJouney(jouney: Jouney) {
    this.jouney.next(jouney);
  }

  public getJouneys(): Observable<Array<Jouney>> {
    return this.jouneys.asObservable();
  }

  public setJouneys(jouneys: Array<Jouney>) {
    this.jouneys.next(jouneys);
  }

  public create(jouney: Jouney): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/jouney/create";
    return this.httpClient.put<String>(url, jouney, this.privateHttpHeader);
  }

  public count(): Observable<Number> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/jouney/read/count";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public delete(jouney: Jouney): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/jouney/delete";
    return this.httpClient.post<String>(url, jouney, this.privateHttpHeader);
  }

  public read(jouney: Jouney): Observable<Array<Jouney>> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/jouney/read/many";
    return this.httpClient.get<Array<Jouney>>(url, this.privateHttpHeader);
  }

  public update(jouney: Jouney): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/jouney/update";
    return this.httpClient.post<String>(url, jouney, this.privateHttpHeader);
  }
}
