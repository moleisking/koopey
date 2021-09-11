import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { User } from "../models/user";
import { Search } from "../models/search";
import { BaseService } from "./base.service";

@Injectable()
export class UserService extends BaseService {
  public user = new ReplaySubject<User>();
  public users = new ReplaySubject<Array<User>>();

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
    let url = this.getApiUrl() + "/user/read/count/";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public create(user: User): Observable<String> {
    var url = this.getApiUrl() + "/user/create";

    return this.httpClient.put<String>(url, user, this.httpHeader);
  }

  public delete(user: User): Observable<String> {
    var url = this.getApiUrl() + "/user/delete";
    return this.httpClient.post<String>(url, user, this.httpHeader);
  }

  public read(userId: string): Observable<User> {
    let url = this.getApiUrl() + "/user/read/" + userId;
    return this.httpClient.get<User>(url, this.httpHeader);
  }

  public readMyUser(): Observable<User> {
    let url = this.getApiUrl() + "/user/read/me";
    return this.httpClient.get<User>(url, this.httpHeader);
  }

  public search(search: Search): Observable<Array<User>> {
    let url = this.getApiUrl() + "/user/search";
    return this.httpClient.post<Array<User>>(url, search, this.httpHeader);
  }

  public update(user: User): Observable<String> {
    var url = this.getApiUrl() + "/user/update";
    return this.httpClient.post<String>(url, user, this.httpHeader);
  }

  public updateCookie(cookie: Boolean): Observable<String> {
    localStorage.setItem("cookie", String(cookie));
    var url = this.getApiUrl() + "/user/update/cookie" + cookie;
    return this.httpClient.post<String>(url, this.httpHeader);
  }

  public updateGdpr(gdpr: Boolean): Observable<String> {
    localStorage.setItem("gdpr", String(gdpr));
    var url = this.getApiUrl() + "/user/update/gdpr" + gdpr;
    return this.httpClient.post<String>(url, this.httpHeader);
  }

  public updateLanguage(language: String): Observable<String> {
    localStorage.setItem("language", String(language));
    var url = this.getApiUrl() + "/user/update/language" + language;
    return this.httpClient.post<String>(url, this.httpHeader);
  }

  public updateNotify(notify: Boolean): Observable<String> {
    localStorage.setItem("notify", String(notify));
    var url = this.getApiUrl() + "/user/update/notify/" + notify;
    return this.httpClient.get<String>(url, this.httpHeader);
  }

  public updateTrack(track: Boolean): Observable<String> {
    localStorage.setItem("track", String(track));
    var url = this.getApiUrl() + "/user/update/track/" + track;
    return this.httpClient.post<String>(url, this.httpHeader);
  }
}
