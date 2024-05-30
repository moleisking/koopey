import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "src/environments/environment";
import { User } from "../models/user";
import { AuthenticationToken } from "../models/authentication/authenticationToken";
import { Change } from "../models/authentication/change";
import { Login } from "../models/login";
import { Location } from "../models/location";
import { BaseService } from "./base.service";

@Injectable()
export class AuthenticationService extends BaseService {
  public user = new ReplaySubject<User>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient);
  }

  public activate(user: User): Observable<String> {
    let url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/activate/reply?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(url, user, this.publicHeader());
  }

  public activateForgotten(): Observable<String> {
    let url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/activate/forgotten?language=" +
      this.translateService.currentLang;
    return this.httpClient.get<String>(url, this.publicHeader());
  }

  public emailChangeRequest(changeEmail: Change): Observable<String> {
    let url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/email/change/request?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(url, changeEmail, this.privateHeader());
  }

  public emailChangeReply(user: User): Observable<String> {
    let url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/email/change/reply?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(url, user, this.privateHeader());
  }

  public isAuthenticated() {
    if (localStorage.getItem("token") !== null) {
      return true;
    } else {
      return false;
    }
  }

  public getLocalCurrency(): string {
    return JSON.parse(localStorage.getItem("currency") || "usd");
  }

  public setLocalCurrency(currency: string) {
    localStorage.setItem("currency", currency);
  }

  public getLocalLanguage(): string {
    if (localStorage.getItem("language") !== null) {
      console.log(
        "Local language set to ",
        <string>localStorage.getItem("language")
      );
      return <string>localStorage.getItem("language");
    } else {
      console.log("Local language set to default");
      localStorage.setItem("language", "en");
      return <string>localStorage.getItem("language");
    }
  }

  public setLocalLanguage(language: string) {
    localStorage.setItem("language", language);
  }

  public getLocalLatitude(): number {
    if (
      localStorage.getItem("latitude") !== undefined &&
      localStorage.getItem("latitude") !== null &&
      localStorage.getItem("latitude")!.length !== null &&
      localStorage.getItem("latitude")!.length > 0
    ) {
      return  parseInt(localStorage.getItem("latitude")!);
    } else {      
      return  Environment.Default.Latitude;
    }
  }

  public getLocalLongitude(): number {
    if (
      localStorage.getItem("longitude") !== undefined &&
      localStorage.getItem("longitude") !== null &&
      localStorage.getItem("longitude")!.length !== null &&
      localStorage.getItem("longitude")!.length > 0
    ) {
      return  parseInt(localStorage.getItem("longitude")!);
    } else {      
      return  Environment.Default.Longitude;
    }
  }

  public getMyUserFromStorage(): User {
    let user: User = new User();
    user.alias = localStorage.getItem("alias")!;
    user.avatar = localStorage.getItem("avatar")!;
    user.currency = localStorage.getItem("currency")!;
    user.id = localStorage.getItem("id")!;
    user.language = localStorage.getItem("language")!;
    user.name = localStorage.getItem("name")!;
    if (
      localStorage.getItem("wallets") &&
      localStorage.getItem("wallets") !== null &&
      localStorage.getItem("wallets") !== undefined &&
      localStorage.getItem("wallets") !== "undefined"
    ) {
      console.log(localStorage.getItem("wallets"));
      user.wallets = JSON.parse(localStorage.getItem("wallets")!);
    }
    user.gdpr = localStorage.getItem("gdpr") == "true" ? true : false;
    user.notify = localStorage.getItem("notify") == "true" ? true : false;
    user.verify = localStorage.getItem("verify") == "true" ? true : false;
    return user;
  }

  public getUser(): Observable<User> {
    return this.user.asObservable();
  }

  public setUser(user: User): void {
    this.user.next(user);
  }

  public login(login: Login): Observable<AuthenticationToken> {
    let url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/login?language=" +
      this.translateService.currentLang;

    return this.httpClient.post<AuthenticationToken>(
      url,
      login,
      this.publicHeader()
    );
  }

  public logout() {
    try {
      localStorage.removeItem("address");
      localStorage.removeItem("alias");
      localStorage.removeItem("avatar");
      localStorage.removeItem("verify");
      localStorage.removeItem("cookie");
      localStorage.removeItem("currency");
      localStorage.removeItem("gdpr");
      localStorage.removeItem("id");
      localStorage.removeItem("language");
      localStorage.removeItem("latitude");
      localStorage.removeItem("longitude");
      localStorage.removeItem("measurement");
      localStorage.removeItem("name");
      localStorage.removeItem("tags");
      localStorage.removeItem("token");
      localStorage.removeItem("track");
      localStorage.removeItem("type");
      localStorage.removeItem("wallets");
      localStorage.removeItem("toko");
      localStorage.removeItem("terms");
      localStorage.removeItem("notify");
    } catch (error) {
      console.log(error);
    }
  }

  public passwordChange(changePassword: Change): Observable<String> {
    let url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/password/change?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(
      url,
      changePassword,
      this.privateHeader()
    );
  }

  public passwordChangeForgotten(changePassword: Change): Observable<String> {
    let url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/password/forgotten/change?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(
      url,
      changePassword,
      this.publicHeader()
    );
  }

  public passwordForgottenReply(changePassword: Change): Observable<String> {
    let url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/password/forgotten/reply?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(
      url,
      changePassword,
      this.publicHeader()
    );
  }

  public passwordForgottenRequest(user: User): Observable<String> {
    let url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/password/forgotten/request?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(url, user, this.publicHeader());
  }

  public register(user: User): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/authenticate/register";
    return this.httpClient.post<String>(url, user, this.publicHeader());
  }

  public saveLocalAuthToken(authToken: AuthenticationToken) {
    localStorage.setItem("token", authToken.token);
  }

  public saveLocalUser(user: User) {
    localStorage.setItem("id", user.id);
    if (user.avatar) {
      localStorage.setItem("avatar", user.avatar);
    } else {
      localStorage.setItem("avatar", Environment.Default.ImageUri);
    }
    localStorage.setItem("alias", user.alias);
    localStorage.setItem("currency", user.currency);
    localStorage.setItem("id", user.id);
    localStorage.setItem("name", user.name);
    localStorage.setItem("language", user.language);
    localStorage.setItem("wallets", JSON.stringify(user.wallets));
    localStorage.setItem("latitude", String(user.latitude));
    localStorage.setItem("longitude", String(user.longitude));
    localStorage.setItem("measurement", user.measurement);
    localStorage.setItem("gdpr", String(user.gdpr));
    localStorage.setItem("cookie", String(user.cookie));
    localStorage.setItem("notify", String(user.notify));
    localStorage.setItem("verify", String(user.verify));
  }

  public saveLanguage(language: String) {
    localStorage.setItem("language", String(language));
  }

  public saveLocation(location: Location) {
    localStorage.setItem("latitude", String(location.latitude));
    localStorage.setItem("longitude", String(location.longitude));
  }
}
