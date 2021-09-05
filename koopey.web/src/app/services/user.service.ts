import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "src/environments/environment";
import { User } from "../models/user";
import { Search } from "../models/search";

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
      Environment.ApiUrls.KoopeyApiUrl +
      "/user/create?language=" +
      this.translateService.currentLang;
    return this.httpClient.put<String>(url, user, this.httpHeader);
  }

  public delete(user: User): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/user/delete";
    return this.httpClient.post<String>(url, user, this.httpHeader);
  }

  public count(): Observable<Number> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/user/read/count/";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public read(id: string): Observable<User> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/user/read/" + id;
    return this.httpClient.get<User>(url, this.httpHeader);
  }

  public readMyUser(): Observable<User> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/user/read/me";
    return this.httpClient.get<User>(url, this.httpHeader);
  }

  public search(search: Search): Observable<Array<User>> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/user/search";
    return this.httpClient.post<Array<User>>(url, search, this.httpHeader);
  }

  public update(user: User): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/user/update";
    return this.httpClient.post<String>(url, user, this.httpHeader);
  }

  public updateGdpr(gdpr: Boolean): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/user/update/gdpr";
    return this.httpClient.post<String>(url, this.httpHeader);
  }

  public updateNotify(notify: Boolean): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/user/update/notify";
    return this.httpClient.get<String>(url, this.httpHeader);
  }

  public updateTrack(user: User): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/user/update/track";
    return this.httpClient.post<String>(url, this.httpHeader);
  }
}
