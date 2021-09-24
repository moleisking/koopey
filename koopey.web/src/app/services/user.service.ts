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
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
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
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public create(user: User): Observable<String> {
    let url = this.baseUrl() + "/authenticate/register";
    return this.httpClient.put<String>(url, user, this.publicHttpHeader);
  }

  public delete(user: User): Observable<String> {
    let url = this.baseUrl() + "/user/delete";
    return this.httpClient.post<String>(url, user, this.privateHttpHeader);
  }

  public read(userId: string): Observable<User> {
    let url = this.baseUrl() + "/user/read/" + userId;
    return this.httpClient.get<User>(url, this.privateHttpHeader);
  }

  public readMyUser(): Observable<User> {
    let url = this.baseUrl() + "/user/read/me";
    return this.httpClient.get<User>(url, this.privateHttpHeader);
  }

  public search(search: Search): Observable<Array<User>> {
    let url = this.baseUrl() + "/user/search";
    return this.httpClient.post<Array<User>>(
      url,
      search,
      this.privateHttpHeader
    );
  }

  public update(user: User): Observable<String> {
    var url = this.baseUrl() + "/user/update";
    return this.httpClient.post<String>(url, user, this.privateHttpHeader);
  }

  public updateCookie(cookie: Boolean): Observable<String> {
    localStorage.setItem("cookie", String(cookie));
    var url = this.baseUrl() + "/user/update/cookie" + cookie;
    return this.httpClient.post<String>(url, this.privateHttpHeader);
  }

  public updateGdpr(gdpr: Boolean): Observable<String> {
    localStorage.setItem("gdpr", String(gdpr));
    var url = this.baseUrl() + "/user/update/gdpr" + gdpr;
    return this.httpClient.post<String>(url, this.privateHttpHeader);
  }

  public updateLanguage(language: String): Observable<String> {
    localStorage.setItem("language", String(language));
    var url = this.baseUrl() + "/user/update/language" + language;
    return this.httpClient.post<String>(url, this.privateHttpHeader);
  }

  public updateNotify(notify: Boolean): Observable<String> {
    localStorage.setItem("notify", String(notify));
    var url = this.baseUrl() + "/user/update/notify/" + notify;
    return this.httpClient.get<String>(url, this.privateHttpHeader);
  }

  public updateTrack(track: Boolean): Observable<String> {
    localStorage.setItem("track", String(track));
    var url = this.baseUrl() + "/user/update/track/" + track;
    return this.httpClient.post<String>(url, this.privateHttpHeader);
  }
}
