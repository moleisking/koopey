//Core
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { User } from "../models/user";
import { Wallet } from "../models/wallet";

@Injectable()
export class WalletService {

  private static LOG_HEADER: string = 'WALLET:SERVICE:';
  public wallet = new ReplaySubject<Wallet>();
  public wallets = new ReplaySubject<Array<Wallet>>();

  constructor(
    private http: Http,
    private translateService: TranslateService
  ) { }

  /*********  Object *********/

  public getWallet(): Observable<Wallet> {
    return this.wallet.asObservable();
  }

  public setWallet(wallet: Wallet): void {
    this.wallet.next(wallet);
  }

  public getWallets(): Observable<Array<Wallet>> {
    return this.wallets.asObservable()
  }

  public setWallets(wallets: Array<Wallet>): void {
    this.wallets.next(wallets);
  }

  /*********  Create *********/

  public createWallet(wallet: Wallet): Observable<Alert> {
    let headers = new Headers();
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(wallet);
    var url = Config.system_backend_url + "/wallet/create/one";
    return this.http.post(url, body, options).catch(this.handleError);
  }

  public createWallets(wallets: Array<Wallet>): Observable<Alert> {
    let headers = new Headers();
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(wallets);
    var url = Config.system_backend_url + "/wallet/create/many";
    return this.http.post(url, body, options).catch(this.handleError);
  }

  /*********  Read *********/

  public readCount(): Observable<Number> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/wallet/read/count/";
    return this.http.get(url, options).map((res: Response) => { return res.json().users.count }).catch(this.handleError);
  }

  public readWallet(): Observable<Wallet> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/wallet/read/one";
    return this.http.get(url, options).map((res: Response) => { return res.json().wallet; }).catch(this.handleError);
  }

  public readWallets(user: User): Observable<Array<Wallet>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'userId' : user.id });
    var url = Config.system_backend_url + "/wallet/read/many";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().wallets; }).catch(this.handleError);
  }

  public readMyWallet(): Observable<Array<Wallet>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/wallet/read/one/mine";
    return this.http.get(url, options).map((res: Response) => { return res.json().wallet; }).catch(this.handleError);
  }

  public readMyWallets(): Observable<Array<Wallet>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/wallet/read/many/mine";
    return this.http.get(url, options).map((res: Response) => { return res.json().wallets; }).catch(this.handleError);
  }

  /*********  Update *********/

  public update(wallet: Wallet): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(wallet);
    var url = Config.system_backend_url + "/wallet/update";
    console.log(url);
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateWalletByAbsolute(userId: String, value: Number): Observable<Alert> {
    //Note: Used for addition and subtraction of Toko.
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let body = JSON.stringify({ 'userId': userId, 'value': value, 'currency': Config.local_currency });
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/wallet/update/absolute";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateWalletByAddition(userId: String, value: Number): Observable<Alert> {
    //Note: Used for addition and subtraction of Toko.
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'userId': userId, 'value': value, 'currency': Config.local_currency });
    var url = Config.system_backend_url + "/wallet/update/addition";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Delete *********/

  public delete(wallet: Wallet): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(wallet);
    var url = Config.system_backend_url + "/wallet/delete";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Error *********/

  private handleError(error: any) {
    return Observable.throw({ "WalletService": { "Code": error.status, "Message": error.message } });
  }
}