import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { User } from "../models/user";
import { Search } from "../models/search";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class UserService extends BaseService {
  public user = new ReplaySubject<User>();
  public users = new ReplaySubject<Array<User>>();

  constructor(
    protected httpClient: HttpClient
  ) {
    super(httpClient);
  }

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

  public count(): Observable<Number> {
    let url = this.baseUrl() + "/user/read/count/";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public delete(user: User): Observable<void> {
    let url = this.baseUrl() + "/user/delete";
    return this.httpClient.delete<void>(url,  this.privateHeaderAndBody(user));
  }

  public read(userId: string): Observable<User> {
    let url = this.baseUrl() + "/user/read/" + userId;
    return this.httpClient.get<User>(url, this.privateHeader());
  }

  public readMyUser(): Observable<User> {
    let url = this.baseUrl() + "/user/read/me";
    return this.httpClient.get<User>(url, this.privateHeader());
  }

  public search(search: Search): Observable<Array<User>> {
    let url = this.baseUrl() + "/user/search";
    return this.httpClient.post<Array<User>>(url, search, this.privateHeader());
  }

  public update(user: User): Observable<String> {
    let url = this.baseUrl() + "/user/update";
    return this.httpClient.put<String>(url, user, this.privateHeader());
  }

  public updateCookie(cookie: Boolean): Observable<String> {
    localStorage.setItem("cookie", String(cookie));
    let url = this.baseUrl() + "/user/update/cookie/" + cookie;
    return this.httpClient.patch<String>(url, this.privateHeader());
  }

  public updateGdpr(gdpr: Boolean): Observable<String> {
    localStorage.setItem("gdpr", String(gdpr));
    let url = this.baseUrl() + "/user/update/gdpr/" + gdpr;
    return this.httpClient.patch<String>(url, this.privateHeader());
  }

  public updateLanguage(language: String): Observable<String> {
    localStorage.setItem("language", String(language));
    let url = this.baseUrl() + "/user/update/language/" + language;
    return this.httpClient.patch<String>(url, this.privateHeader());
  }

  public updateNotify(notify: Boolean): Observable<String> {
    localStorage.setItem("notify", String(notify));
    let url = this.baseUrl() + "/user/update/notify/" + notify;
    return this.httpClient.patch<String>(url, this.privateHeader());
  }

  public updateTrack(track: Boolean): Observable<String> {
    localStorage.setItem("track", String(track));
    let url = this.baseUrl() + "/user/update/track/" + track;
    return this.httpClient.patch<String>(url, this.privateHeader());
  }
}
