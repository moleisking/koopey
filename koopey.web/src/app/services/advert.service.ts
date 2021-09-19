import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Advert } from "../models/advert";
import { Environment } from "src/environments/environment";
import { Search } from "../models/search";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class AdvertService extends BaseService {
  public advert = new ReplaySubject<Advert>();
  public adverts = new ReplaySubject<Array<Advert>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getAdvert(): Observable<Advert> {
    return this.advert.asObservable();
  }

  public setAdvert(advert: Advert): void {
    this.advert.next(advert);
  }

  public getAdverts(): Observable<Array<Advert>> {
    return this.adverts.asObservable();
  }

  public setAdverts(adverts: Array<Advert>) {
    this.adverts.next(adverts);
  }

  public count(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/advert/count/";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public create(advert: Advert): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/advert/create";
    return this.httpClient.put<String>(url, advert, this.httpHeader);
  }

  public delete(advert: Advert): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/advert/delete";
    return this.httpClient.post<String>(url, advert, this.httpHeader);
  }

  public readAdvert(advert: Advert): Observable<Advert> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/advert/read/one/" + advert.id;
    return this.httpClient.get<Advert>(url, this.httpHeader);
  }

  public readAdverts(search: Search): Observable<Array<Advert>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/advert/read/many";
    return this.httpClient.get<Array<Advert>>(url, this.httpHeader);
  }

  public readUserAdverts(): Observable<Array<Advert>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/advert/read/many/mine";
    return this.httpClient.get<Array<Advert>>(url, this.httpHeader);
  }

  public update(advert: Advert): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/advert/update";
    return this.httpClient.post<String>(url, advert, this.httpHeader);
  }
}
