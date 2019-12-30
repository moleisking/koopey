//Angular, Material, Libraries
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Advert } from "../models/advert";
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Game } from "../models/game";
import { Location } from "../models/location";
import { User } from "../models/user";
import { Review } from "../models/review";
import { Search } from "../models/search";
import { Tag } from "../models/tag";
import { Score } from "../models/score";
import { Wallet } from "../models/wallet";

@Injectable()
export class UserService {

  private static LOG_HEADER: string = 'USER:SERVICE:';
  public user = new ReplaySubject<User>();
  public users = new ReplaySubject<Array<User>>();

  constructor(
    private http: Http,
    private translateService: TranslateService
  ) { }

  /*********  Object *********/

  public getUser(): Observable<User> {
    return this.user.asObservable();
  }

  public setUser(user: User): void {
    this.user.next(user);
  }

  public getUsers(): Observable<Array<User>> {
    return this.users.asObservable()
  }

  public setUsers(users: Array<User>): void {
    this.users.next(users);
  }

  /*********  Create *********/

  public create(user: User): Observable<Alert> {
    let headers = new Headers();
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(user);
    var url = Config.system_backend_url + "/user/create?language=" + this.translateService.currentLang;
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public createScore(score: Score): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(score);
    var url = Config.system_backend_url + "/user/create/score";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public createLocation(location: Location): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(location);
    var url = Config.system_backend_url + "/user/create/location";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public createReview(review: Review): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(review);
    var url = Config.system_backend_url + "/user/create/review";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public createWallet(wallet: Wallet): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(wallet);
    var url = Config.system_backend_url + "/wallet/create";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Read *********/

  public readCount(): Observable<Number> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/user/read/count/";
    return this.http.get(url, options).map((res: Response) => { return res.json().users.count }).catch(this.handleError);
  }

  public readGames(): Observable<Array<Game>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/user/read/my/games";
    return this.http.get(url, options).map((res: Response) => { return res.json().games; }).catch(this.handleError);
  }

  public readUser(id: string): Observable<User> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'id': id });
    var url = Config.system_backend_url + "/user/read/one/";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().user }).catch(this.handleError);
  }

  public readUsers(search: Search): Observable<Array<User>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Content-Type", "application/json");
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(search);
    var url = Config.system_backend_url + "/user/read/many";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().users }).catch(this.handleError);
  }

  public readMyUser(): Observable<User> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/user/read/me";
    return this.http.get(url, options).map((res: Response) => { return res.json().user }).catch(this.handleError);
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

  public readMyGames(): Observable<Array<Game>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/user/read/my/games";
    return this.http.get(url, options).map((res: Response) => { return res.json().games; }).catch(this.handleError);
  }

  public readMyReviews(): Observable<Array<Review>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/user/read/my/reviews";
    return this.http.get(url, options).map((res: Response) => { return res.json().reviews; }).catch(this.handleError);
  }

  /*public readMyWallets(): Observable<Array<Wallet>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/user/read/my/wallets";
    return this.http.get(url, options).map((res: Response) => { return res.json().wallets; }).catch(this.handleError);
  }*/

  public readReview(judgeId: String, userId: String): Observable<Review> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'judgeId': judgeId, 'userId': userId });
    var url = Config.system_backend_url + "/user/read/review";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().review; }).catch(this.handleError);
  }

  public readReviews(userId: String): Observable<Array<Review>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'id': userId });
    var url = Config.system_backend_url + "/user/read/reviews";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().reviews; }).catch(this.handleError);
  }  

  /*********  Update *********/

  public update(user: User): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(user);
    var url = Config.system_backend_url + "/user/update/me";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateAdvert(advert: Advert): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(advert);
    var url = Config.system_backend_url + "/user/update/advert";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateLocation(location: Location): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(location);
    var url = Config.system_backend_url + "/user/update/location";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateNotify(user: User): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");

    //Strip images and other data intesive parameters    
    let userLite: User = <User>{};
    userLite.id = user.id; //Not necesary
    userLite.notify = user.notify;
    let body = JSON.stringify(userLite);

    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/user/update/notify";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateTerms(user: User): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");

    //Strip images and other data intesive parameters    
    let userLite: User = new User();
    userLite.id = user.id; //Not necesary
    userLite.terms = user.terms;
    let body = JSON.stringify(userLite);

    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/user/update/terms";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateTrack(user: User): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'id': user.id, 'track': user.track });
    var url = Config.system_backend_url + "/user/update/track";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Delete *********/

  public delete(user: User): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(user);
    var url = Config.system_backend_url + "/user/delete";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public deleteLocation(location: Location): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(location);
    var url = Config.system_backend_url + "/user/delete/location";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Search *********/

  private handleError(error: any) {
    return Observable.throw({ "UserService": { "Code": error.status, "Message": error.message } });
  }
}