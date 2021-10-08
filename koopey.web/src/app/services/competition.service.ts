import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Article } from "../models/article";
import { Asset } from "../models/asset";
import { User } from "../models/user";
import { Environment } from "src/environments/environment";
import { Cometition } from "../models/cometition";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class CometitionService extends BaseService {
  public cometition = new ReplaySubject<Cometition>();
  public cometitions = new ReplaySubject<Array<Cometition>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getCometition(): Observable<Cometition> {
    return this.cometition.asObservable();
  }

  public setCometition(cometition: Cometition) {
    this.cometition.next(cometition);
  }

  public getCometitions(): Observable<Array<Cometition>> {
    return this.cometitions.asObservable();
  }

  public setCometitions(cometitions: Array<Cometition>) {
    this.cometitions.next(cometitions);
  }

  public create(cometition: Cometition): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/cometition/create";
    return this.httpClient.put<String>(url, cometition, this.privateHttpHeader);
  }

  public count(): Observable<Number> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/cometition/read/count";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public delete(cometition: Cometition): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/cometition/delete";
    return this.httpClient.post<String>(url, cometition, this.privateHttpHeader);
  }

  public read(cometition: Cometition): Observable<Array<Cometition>> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/cometition/read/many";
    return this.httpClient.get<Array<Cometition>>(url, this.privateHttpHeader);
  }

  public update(cometition: Cometition): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/cometition/update";
    return this.httpClient.post<String>(url, cometition, this.privateHttpHeader);
  }
}
