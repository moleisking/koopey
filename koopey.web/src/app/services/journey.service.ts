import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Article } from "../models/article";
import { Asset } from "../models/asset";
import { User } from "../models/user";
import { Environment } from "src/environments/environment";
import { Journey } from "../models/journey";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class JourneyService extends BaseService {
  public journey = new ReplaySubject<Journey>();
  public journeys = new ReplaySubject<Array<Journey>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getJouney(): Observable<Journey> {
    return this.journey.asObservable();
  }

  public setJouney(journey: Journey) {
    this.journey.next(journey);
  }

  public getJouneys(): Observable<Array<Journey>> {
    return this.journeys.asObservable();
  }

  public setJouneys(journeys: Array<Journey>) {
    this.journeys.next(journeys);
  }

  public create(journey: Journey): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/journey/create";
    return this.httpClient.put<String>(url, journey, this.privateHttpHeader);
  }

  public count(): Observable<Number> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/journey/read/count";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public delete(journey: Journey): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/journey/delete";
    return this.httpClient.post<String>(url, journey, this.privateHttpHeader);
  }

  public read(journey: Journey): Observable<Array<Journey>> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/journey/read/many";
    return this.httpClient.get<Array<Journey>>(url, this.privateHttpHeader);
  }

  public update(journey: Journey): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/journey/update";
    return this.httpClient.post<String>(url, journey, this.privateHttpHeader);
  }
}
