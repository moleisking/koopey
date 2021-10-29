import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Environment } from "src/environments/environment";
import { Competition } from "../models/competition";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class CompetitionService extends BaseService {
  public competition = new ReplaySubject<Competition>();
  public competitions = new ReplaySubject<Array<Competition>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getCompetition(): Observable<Competition> {
    return this.competition.asObservable();
  }

  public setCompetition(cometition: Competition) {
    this.competition.next(cometition);
  }

  public getCompetitions(): Observable<Array<Competition>> {
    return this.competitions.asObservable();
  }

  public setCompetitions(competitions: Array<Competition>) {
    this.competitions.next(competitions);
  }

  public create(cometition: Competition): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/competition/create";
    return this.httpClient.put<String>(url, cometition, this.privateHeader());
  }

  public count(): Observable<Number> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/competition/read/count";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public delete(competition: Competition): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/competition/delete";
    return this.httpClient.post<String>(url, competition, this.privateHeader());
  }

  public read(competition: Competition): Observable<Array<Competition>> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/competition/read/many";
    return this.httpClient.get<Array<Competition>>(url, this.privateHeader());
  }

  public update(competition: Competition): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/competition/update";
    return this.httpClient.post<String>(url, competition, this.privateHeader());
  }
}
