import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Advert } from "../models/advert";
import { Environment } from "../../environments/environment";
import { Search } from "../models/search";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class AdvertService extends BaseService {
  public advert = new ReplaySubject<Advert>();
  public adverts = new ReplaySubject<Array<Advert>>();

  constructor(
    protected httpClient: HttpClient
  ) {
    super(httpClient);
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
    let url = this.baseUrl() + "/advert/count/";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public create(advert: Advert): Observable<String> {
    let url = this.baseUrl() + "/advert/create";
    return this.httpClient.put<String>(url, advert, this.privateHeader());
  }

  public delete(advert: Advert): Observable<void> {
    let url = this.baseUrl() + "/advert/delete";
    return this.httpClient.post<void>(url, advert, this.privateHeader());
  }

  public read(advert: Advert): Observable<Advert> {
    let url = this.baseUrl() + "/advert/read/one/" + advert.id;
    return this.httpClient.get<Advert>(url, this.privateHeader());
  }

  public search(search: Search): Observable<Array<Advert>> {
    let url = this.baseUrl() + "/advert/read/many";
    return this.httpClient.get<Array<Advert>>(url, this.privateHeader());
  }

  public searchMyAdverts(): Observable<Array<Advert>> {
    let url = this.baseUrl() + "/advert/read/many/mine";
    return this.httpClient.get<Array<Advert>>(url, this.privateHeader());
  }

  public update(advert: Advert): Observable<void> {
    let url = this.baseUrl() + "/advert/update";
    return this.httpClient.post<void>(url, advert, this.privateHeader());
  }
}
