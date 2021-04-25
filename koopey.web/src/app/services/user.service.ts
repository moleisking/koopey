import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "ng2-translate";
import { Advert } from "../models/advert";
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Location } from "../models/location";
import { User } from "../models/user";
import { Review } from "../models/review";
import { Search } from "../models/search";
import { Tag } from "../models/tag";
import { Score } from "../models/score";
import { Wallet } from "../models/wallet";

@Injectable()
export class UserService {
  public user = new ReplaySubject<User>();
  public users = new ReplaySubject<Array<User>>();

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

  public getUser(): Observable<User> {
    return this.user.asObservable();
  }

  public setUser(user: User): void {
    this.user.next(user);
  }

  public getUsers(): Observable<Array<User>> {
    return this.users.asObservable();
  }

  public setUsers(users: Array<User>): void {
    this.users.next(users);
  }

  public create(user: User): Observable<String> {
    var url =
      Config.system_backend_url +
      "/user/create?language=" +
      this.translateService.currentLang;
    return this.httpClient.put<String>(url, user, this.httpHeader);
  }

  /*public createScore(score: Score): Observable<Alert> {
  
    var url = Config.system_backend_url + "/user/create/score";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public createLocation(location: Location): Observable<Alert> {
   
    var url = Config.system_backend_url + "/user/create/location";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public createReview(review: Review): Observable<Alert> {
   
    var url = Config.system_backend_url + "/user/create/review";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public createWallet(wallet: Wallet): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(wallet);
    var url = Config.system_backend_url + "/wallet/create";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }*/

  public count(): Observable<Number> {
    var url = Config.system_backend_url + "/user/read/count/";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public readUser(id: string): Observable<User> {
    var url = Config.system_backend_url + "/user/read/one/";
    return this.httpClient.get<User>(url, this.httpHeader);
  }

  public readUsers(search: Search): Observable<Array<User>> {
    var url = Config.system_backend_url + "/user/read/many";
    return this.httpClient.post<Array<User>>(url, search, this.httpHeader);
  }

  public readMyUser(): Observable<User> {
    var url = Config.system_backend_url + "/user/read/me";
    return this.httpClient.get<User>(url, this.httpHeader);
  }

  /*public readMyUserLite(): Observable<User> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    var url = Config.system_backend_url + "/user/read/me/lite";
    return this.http.get(url, options).map((res: Response) => { return res.json().user; }).catch(this.handleError);
  }*/

  /*public readMyReviews(): Observable<Array<Review>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    var url = Config.system_backend_url + "/user/read/my/reviews";
    return this.http
      .get(url, options)
      .map((res: Response) => {
        return res.json().reviews;
      })
      .catch(this.handleError);
  }*/

  /*public readMyWallets(): Observable<Array<Wallet>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/user/read/my/wallets";
    return this.http.get(url, options).map((res: Response) => { return res.json().wallets; }).catch(this.handleError);
  }*/

  /*public readReview(judgeId: String, userId: String): Observable<Review> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ judgeId: judgeId, userId: userId });
    var url = Config.system_backend_url + "/user/read/review";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().review;
      })
      .catch(this.handleError);
  }

  public readReviews(userId: String): Observable<Array<Review>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ id: userId });
    var url = Config.system_backend_url + "/user/read/reviews";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().reviews;
      })
      .catch(this.handleError);
  }*/

  /*********  Update *********/

  public update(user: User): Observable<String> {
    var url = Config.system_backend_url + "/user/update/me";
    return this.httpClient.post<String>(url, user, this.httpHeader);
  }

  public updateAdvert(advert: Advert): Observable<String> {
    var url = Config.system_backend_url + "/user/update/advert";
    return this.httpClient.post<String>(url, advert, this.httpHeader);
  }

  public updateLocation(location: Location): Observable<String> {
    var url = Config.system_backend_url + "/user/update/location";
    return this.httpClient.post<String>(url, location, this.httpHeader);
  }

  public updateNotify(user: User): Observable<String> {
    //Strip images and other data intesive parameters
    let userLight: User = <User>{};
    userLight.id = user.id; //Not necesary
    userLight.notify = user.notify;

    var url = Config.system_backend_url + "/user/update/notify";
    return this.httpClient.post<String>(url, userLight, this.httpHeader);
  }

  public updateTerms(user: User): Observable<String> {
    //Strip images and other data intesive parameters
    let userLight: User = <User>{};
    userLight.id = user.id; //Not necesary
    userLight.terms = userLight.terms;

    var url = Config.system_backend_url + "/user/update/terms";
    return this.httpClient.post<String>(url, userLight, this.httpHeader);
  }

  public updateTrack(user: User): Observable<String> {
    let body = JSON.stringify({ id: user.id, track: user.track });
    var url = Config.system_backend_url + "/user/update/track";
    return this.httpClient.post<String>(url, body, this.httpHeader);
  }

  public delete(user: User): Observable<String> {
    var url = Config.system_backend_url + "/user/delete";
    return this.httpClient.post<String>(url, user, this.httpHeader);
  }

  /* public deleteLocation(location: Location): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(location);
    var url = Config.system_backend_url + "/user/delete/location";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }*/
}
