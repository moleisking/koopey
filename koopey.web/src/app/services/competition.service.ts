import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Environment } from "src/environments/environment";
import { Competition } from "../models/competition";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class CometitionService extends BaseService {
  public cometition = new ReplaySubject<Competition>();
  public cometitions = new ReplaySubject<Array<Competition>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getCometition(): Observable<Competition> {
    return this.cometition.asObservable();
  }

  public setCometition(cometition: Competition) {
    this.cometition.next(cometition);
  }

  public getCometitions(): Observable<Array<Competition>> {
    return this.cometitions.asObservable();
  }

  public setCometitions(cometitions: Array<Competition>) {
    this.cometitions.next(cometitions);
  }

  public create(cometition: Competition): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/cometition/create";
    return this.httpClient.put<String>(url, cometition, this.privateHttpHeader);
  }

  public count(): Observable<Number> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/cometition/read/count";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public delete(competition: Competition): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/cometition/delete";
    return this.httpClient.post<String>(
      url,
      competition,
      this.privateHttpHeader
    );
  }

  public read(competition: Competition): Observable<Array<Competition>> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/cometition/read/many";
    return this.httpClient.get<Array<Competition>>(url, this.privateHttpHeader);
  }

  public update(competition: Competition): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/cometition/update";
    return this.httpClient.post<String>(
      url,
      competition,
      this.privateHttpHeader
    );
  }
}
