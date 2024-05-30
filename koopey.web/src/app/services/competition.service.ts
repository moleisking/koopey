import { BaseService } from "./base.service";
import { Competition } from "../models/competition";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class CompetitionService extends BaseService {
  public competition = new ReplaySubject<Competition>();
  public competitions = new ReplaySubject<Array<Competition>>();

  constructor(
    protected httpClient: HttpClient
  ) {
    super(httpClient);
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
    let url = this.baseUrl() + "/competition/create";
    return this.httpClient.post<String>(url, cometition, this.privateHeader());
  }

  public count(): Observable<Number> {
    let url = this.baseUrl() + "/competition/read/count";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public delete(competition: Competition): Observable<void> {
    let url = this.baseUrl() + "/competition/delete";
    return this.httpClient.post<void>(url, competition, this.privateHeader());
  }

  public read(id: string): Observable<Array<Competition>> {
    let url = this.baseUrl() + "/competition/read/" + id;
    return this.httpClient.get<Array<Competition>>(url, this.privateHeader());
  }

  public update(competition: Competition): Observable<void> {
    let url = this.baseUrl() + "/competition/update";
    return this.httpClient.post<void>(url, competition, this.privateHeader());
  }
}
