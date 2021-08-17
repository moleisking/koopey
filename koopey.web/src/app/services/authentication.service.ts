import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Alert } from "../models/alert";
import { Environment } from "src/environments/environment";
import { Location } from "../models/location";
import { User } from "../models/user";
import { Search } from "../models/search";
import { Tag } from "../models/tag";
import { Wallet } from "../models/wallet";
import { AuthToken } from "../models/authentication/authToken";
import { Change } from "../models/authentication/change";
import { Login } from "../models/login";

@Injectable()
export class AuthenticationService {
  public user = new ReplaySubject<User>();

  public httpAuthorizedHeader = {
    headers: new HttpHeaders({
      Authorization: "JWT " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
    }),
  };

  public httpUnAuthorizedHeader = {
    headers: new HttpHeaders({
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

  public getLocalCurrency(): string {
    return JSON.parse(localStorage.getItem("currency") || "usd");
  }

  public setLocalCurrency(currency: string) {
    localStorage.setItem("currency", currency);
  }

  public getLocalLanguage(): string {
    return JSON.parse(localStorage.getItem("language") || "en");
  }

  public setLocalLanguage(language: string) {
    localStorage.setItem("language", language);
  }

  public getLocalUser(): User {
    var user: User = new User();

    user.alias = JSON.parse(localStorage.getItem("alias")!);

    user.avatar = JSON.parse(localStorage.getItem("avatar")!);
    user.currency = JSON.parse(localStorage.getItem("currency")!);
    user.id = JSON.parse(localStorage.getItem("id")!);
    user.language = JSON.parse(localStorage.getItem("language")!);
    user.name = JSON.parse(localStorage.getItem("name")!);
    user.location = JSON.parse(localStorage.getItem("location")!);
    user.wallets = JSON.parse(localStorage.getItem("wallets")!);
    user.terms = localStorage.getItem("terms") == "true" ? true : false;
    user.notify = localStorage.getItem("notify") == "true" ? true : false;
    user.authenticated =
      localStorage.getItem("authenticated") == "true" ? true : false;

    return user;
  }

  public login(login: Login): Observable<AuthToken> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/login?language=" +
      this.translateService.currentLang;
    console.log(url);
    return this.httpClient.post<AuthToken>(
      url,
      login,
      this.httpUnAuthorizedHeader
    );
    /*.subscribe((authToken : AuthToken) => {
      if (user.avatar) {
          localStorage.setItem("avatar", user.avatar);
        } else {
          localStorage.setItem("avatar", Config.default_user_image_uri);
        }
        localStorage.setItem("alias", user.alias);
        localStorage.setItem("currency", user.currency);
        localStorage.setItem("id", user.id);
        localStorage.setItem("token", user.token.split(" ")[1]);
        localStorage.setItem("name", user.name);
        localStorage.setItem("wallets", JSON.stringify(user.wallets));
        localStorage.setItem("location", JSON.stringify(user.location));
        localStorage.setItem("measure", user.measure);
        localStorage.setItem("terms", String(user.terms));
        localStorage.setItem("cookies", String(user.cookies));
        localStorage.setItem("notify", String(user.notify));
        localStorage.setItem("authenticated", String(user.authenticated));
      }, () => { });*/
  }

  public logout() {
    try {
      localStorage.removeItem("alias");
      localStorage.removeItem("avatar");
      localStorage.removeItem("currency");
      localStorage.removeItem("id");
      localStorage.removeItem("token");
      localStorage.removeItem("language");
      localStorage.removeItem("measure");
      localStorage.removeItem("cookies");
      localStorage.removeItem("name");
      localStorage.removeItem("location");
      localStorage.removeItem("type");
      localStorage.removeItem("wallets");
      localStorage.removeItem("toko");
      localStorage.removeItem("bitcoin");
      localStorage.removeItem("ethereum");
      localStorage.removeItem("terms");
      localStorage.removeItem("notify");
      localStorage.removeItem("authenticated");
    } catch (error) {
      console.log(error);
    }
  }

  public isLoggedIn() {
    //Valid auth key is checked on backend
    if (localStorage.getItem("token") !== null) {
      return true;
    } else {
      return false;
    }
  }

  public activate(user: User): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/activate/reply?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(url, user, this.httpUnAuthorizedHeader);
  }

  public activateForgotten(): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/activate/forgotten?language=" +
      this.translateService.currentLang;
    return this.httpClient.get<String>(url, this.httpUnAuthorizedHeader);
  }

  public emailChangeRequest(changeEmail: Change): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/email/change/request?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(
      url,
      changeEmail,
      this.httpAuthorizedHeader
    );
  }

  public emailChangeReply(user: User): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/email/change/reply?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(url, user, this.httpAuthorizedHeader);
  }

  public passwordChange(changePassword: Change): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/password/change?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(
      url,
      changePassword,
      this.httpAuthorizedHeader
    );
  }

  public passwordChangeForgotten(changePassword: Change): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/password/forgotten/change?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(
      url,
      changePassword,
      this.httpUnAuthorizedHeader
    );
  }

  public passwordForgottenReply(changePassword: Change): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/password/forgotten/reply?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(
      url,
      changePassword,
      this.httpUnAuthorizedHeader
    );
  }

  public passwordForgottenRequest(user: User): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl +
      "/authenticate/password/forgotten/request?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(url, user, this.httpUnAuthorizedHeader);
  }

  private handleError(error: any) {
    return Observable.throw({
      AuthServiceError: { Code: error.status, Message: error.message },
    });
  }
}
