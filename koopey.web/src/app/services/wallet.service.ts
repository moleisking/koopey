import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Alert } from "../models/alert";
import { Environment } from "src/environments/environment";
import { User } from "../models/user";
import { Wallet } from "../models/wallet";

@Injectable()
export class WalletService {
  public wallet = new ReplaySubject<Wallet>();
  public wallets = new ReplaySubject<Array<Wallet>>();

  public httpHeader = {
    headers: new HttpHeaders({
      Authorization: "JWT " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
      "Content-Language": String(localStorage.getItem("language")),
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private translateService: TranslateService
  ) {}

  public getWallet(): Observable<Wallet> {
    return this.wallet.asObservable();
  }

  public setWallet(wallet: Wallet): void {
    this.wallet.next(wallet);
  }

  public getWallets(): Observable<Array<Wallet>> {
    return this.wallets.asObservable();
  }

  public setWallets(wallets: Array<Wallet>): void {
    this.wallets.next(wallets);
  }

  public create(wallet: Wallet): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/wallet/create/one";
    return this.httpClient.post<String>(url, wallet, this.httpHeader);
  }

  public createWallets(wallets: Array<Wallet>): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/wallet/create/many";
    return this.httpClient.post<String>(url, wallets, this.httpHeader);
  }

  public count(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/wallet/read/count/";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public delete(wallet: Wallet): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/wallet/delete";
    return this.httpClient.post<String>(url, wallet, this.httpHeader);
  }

  public readWallet(wallet: Wallet): Observable<Wallet> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/wallet/read/one" + wallet.id;
    return this.httpClient.get<Wallet>(url, this.httpHeader);
  }

  public readWallets(user: User): Observable<Array<Wallet>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/wallet/read/many";
    return this.httpClient.get<Array<Wallet>>(url, this.httpHeader);
  }

  public readUserWallet(): Observable<Array<Wallet>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/wallet/read/one/mine";
    return this.httpClient.get<Array<Wallet>>(url, this.httpHeader);
  }

  public readUserWallets(): Observable<Array<Wallet>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/wallet/read/many/mine";
    return this.httpClient.get<Array<Wallet>>(url, this.httpHeader);
  }

  public update(wallet: Wallet): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/wallet/update";
    return this.httpClient.post<String>(url, wallet, this.httpHeader);
  }

  public updateWalletByAbsolute(
    userId: String,
    value: Number
  ): Observable<String> {
    //Note: Used for addition and subtraction of Toko.
    let body = JSON.stringify({
      userId: userId,
      value: value,
      currency: Environment.Default.Currency,
    });
    var url = Environment.ApiUrls.KoopeyApiUrl + "/wallet/update/absolute";
    return this.httpClient.post<String>(url, body, this.httpHeader);
  }

  public updateWalletByAddition(
    userId: String,
    value: Number
  ): Observable<String> {
    //Note: Used for addition and subtraction of Toko.

    let body = JSON.stringify({
      userId: userId,
      value: value,
      currency: Environment.Default.Currency,
    });
    var url = Environment.ApiUrls.KoopeyApiUrl + "/wallet/update/addition";
    return this.httpClient.post<String>(url, body, this.httpHeader);
  }
}
