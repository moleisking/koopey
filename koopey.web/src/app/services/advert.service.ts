import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Advert } from "../models/advert";
import { Alert } from "../models/alert";
import { Environment } from "src/environments/environment";
import { File } from "../models/file";
import { Image } from "../models/image";
import { Location } from "../models/location";
import { Review } from "../models/review";
import { Search } from "../models/search";
import { Tag } from "../models/tag";

@Injectable()
export class AdvertService {
  public advert = new ReplaySubject<Advert>();
  public adverts = new ReplaySubject<Array<Advert>>();

  public httpHeader = {
    headers: new HttpHeaders({
      Authorization: "JWT " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private translateService: TranslateService
  ) {}

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

  /*public readFile(id: string): Observable<any> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ id: id });
    var url = Environment.ApiUrls.KoopeyApiUrl + "/advert/read/file/";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().advert;
      })
      .catch(this.handleError);
  }*/

  /*********  Update *********/

  /*public updateAdvert(advert: Advert, advert: Advert): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ advert: advert, advert: advert });
    var url = Environment.ApiUrls.KoopeyApiUrl + "/advert/update/advert";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public updateImage(advert: Advert, image: Image): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ advert: advert, image: image });
    var url = Environment.ApiUrls.KoopeyApiUrl + "/advert/update/image";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public updateReviews(
    advert: Advert,
    reviews: Array<Review>
  ): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ advert: advert });
    var url = Environment.ApiUrls.KoopeyApiUrl + "/advert/update/reviews";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }*/

  /*********  Delete *********/
}
