//Angular, Material, Libraries
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
//import { Fee } from "../models/fee";
import { Location } from "../models/location";
import { User } from "../models/user";
import { Search } from "../models/search";
import { Tag } from "../models/tag";
import { Wallet } from "../models/wallet";

@Injectable()
export class AuthService {

    private static LOG_HEADER: string = 'AUTH:SERVICE:';
    public user = new ReplaySubject<User>();

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

    public getLocalCurrency(): string {
        return localStorage.getItem("currency");
    }

    public setLocalCurrency(currency: string) {
        localStorage.setItem("currency", currency);
    }

    public getLocalLanguage(): string {
        return localStorage.getItem("language");
    }

    public setLocalLanguage(language: string) {
        localStorage.setItem("language", language);
    }

    public getLocalUser(): User {
        var user: User = new User();
        user.alias = localStorage.getItem("alias");
        user.avatar = localStorage.getItem("avatar");
        user.currency = localStorage.getItem("currency");
        user.id = localStorage.getItem("id");
        user.language = localStorage.getItem('language');
        user.name = localStorage.getItem("name");
        user.location = JSON.parse(localStorage.getItem("location"));
        user.wallets = JSON.parse(localStorage.getItem("wallets"));
        user.terms = localStorage.getItem('terms') == "true" ? true : false;
        user.notify = localStorage.getItem('notify') == "true" ? true : false;
        user.authenticated = localStorage.getItem('authenticated') == "true" ? true : false;
        return user;
    }

    /*********  Authentication *********/

    public login(user: User): Observable<any> {
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(user);
        var url = Config.system_backend_url + "/authenticate/login?language=" + this.translateService.currentLang;
        return this.http.post(url, body, options).map((res: Response) => {
            //Only stores the key and not JWT header, can't store objects, only strings  
            if (res.json().user.avatar) {
                localStorage.setItem("avatar", res.json().user.avatar);
            } else {
                localStorage.setItem("avatar", Config.default_user_image_uri);
            }
            localStorage.setItem("alias", res.json().user.alias);
            localStorage.setItem("currency", res.json().user.currency);
            localStorage.setItem("id", res.json().user.id);
            localStorage.setItem("token", res.json().user.token.split(" ")[1]);
            localStorage.setItem("name", res.json().user.name);
            localStorage.setItem("wallets", JSON.stringify(res.json().user.wallets));
            localStorage.setItem("location", JSON.stringify(res.json().user.location));
            localStorage.setItem("measure", res.json().user.measure);
            localStorage.setItem("terms", res.json().user.terms);
            localStorage.setItem("cookies", res.json().user.cookies);
            localStorage.setItem("notify", res.json().user.notify);
            localStorage.setItem("authenticated", res.json().user.authenticated);
            if (Wallet.containsCurrency(res.json().user.wallets, "tok")) {
                localStorage.setItem("toko", Wallet.readByCurrency(res.json().user.wallets, "tok").id);
            }
            if (Wallet.containsCurrency(res.json().user.wallets, "btc")) {
                localStorage.setItem("bitcoin", Wallet.readByCurrency(res.json().user.wallets, "btc").name);
            }
            if (Wallet.containsCurrency(res.json().user.wallets, "eth")) {
                localStorage.setItem("ethereum", Wallet.readByCurrency(res.json().user.wallets, "eth").name);
            }
            return res.json().user;
        }).catch(this.handleError);
    }

    public logout() {
        try {
            localStorage.removeItem("alias");
            localStorage.removeItem("avatar");
            localStorage.removeItem("currency");
            localStorage.removeItem("id");
            localStorage.removeItem("token");
            localStorage.removeItem('language');
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

    public activate(user: User): Observable<Alert> {
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        let options = new RequestOptions({ headers: headers });
        let body = JSON.stringify(user);
        var url = Config.system_backend_url + "/authenticate/activate/reply?language=" + this.translateService.currentLang;
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public activateForgotten(): Observable<Alert> {
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        let options = new RequestOptions({ headers: headers });
        var url = Config.system_backend_url + "/authenticate/activate/forgotten?language=" + this.translateService.currentLang;
        return this.http.get(url, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public emailChangeRequest(user: User): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Content-Type", "application/json");
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        let options = new RequestOptions({ headers: headers });
        let body = JSON.stringify(user);
        var url = Config.system_backend_url + "/authenticate/email/change/request?language=" + this.translateService.currentLang;
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public emailChangeReply(user: User): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Content-Type", "application/json");
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        let options = new RequestOptions({ headers: headers });
        let body = JSON.stringify(user);
        var url = Config.system_backend_url + "/authenticate/email/change/reply?language=" + this.translateService.currentLang;
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public passwordChange(user: User): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Content-Type", "application/json");
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        let options = new RequestOptions({ headers: headers });
        let body = JSON.stringify(user);
        console.log("passwordChange");
        console.log(user);
        var url = Config.system_backend_url + "/authenticate/password/change?language=" + this.translateService.currentLang;
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /* public passwordChangeForgotten(user: User): Observable<Alert> {
         let headers = new Headers();
         headers.append("Content-Type", "application/json");
         headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
         let options = new RequestOptions({ headers: headers });
         let body = JSON.stringify(user);
         var url = Config.system_backend_url + "/authenticate/password/forgotten/change?language=" + this.translateService.currentLang;
         return this.http.post(url, body, options).catch(this.handleError);
     }*/

    public passwordForgottenReply(user: User): Observable<Alert> {
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        let options = new RequestOptions({ headers: headers });
        let body = JSON.stringify(user);
        var url = Config.system_backend_url + "/authenticate/password/forgotten/reply?language=" + this.translateService.currentLang;
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public passwordForgottenRequest(user: User): Observable<Alert> {
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        let options = new RequestOptions({ headers: headers });
        let body = JSON.stringify(user);
        var url = Config.system_backend_url + "/authenticate/password/forgotten/request?language=" + this.translateService.currentLang;
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    private handleError(error: any) {
        return Observable.throw({ "AuthServiceError": { "Code": error.status, "Message": error.message } });
    }
}
