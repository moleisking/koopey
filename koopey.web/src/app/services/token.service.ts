import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable } from "rxjs/Rx";
import { Alert } from "../models/alert";
import { Token } from "../models/authentication/authToken";
import { Config } from "../config/settings";
import { User } from "../models/user";
import { Wallet } from "../models/wallet";

@Injectable()
export class TokenService {
  constructor(private http: Http) {}

  /*********  Create *********/

  public createAddress(): Observable<Wallet> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    var url = Config.system_backend_url + "/token/create/address";
    return this.http
      .get(url, options)
      .map((res: Response) => {
        return res.json().wallet;
      })
      .catch(this.handleError);
  }

  public createReceipt(token: Token): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(token);
    var url = Config.system_backend_url + "/token/create/receipt";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public createSystemInvoice(token: Token): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(token);
    var url = Config.system_backend_url + "/token/create/system/invoice";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public createSystemReceiptFromInvoice(token: Token): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(token);
    var url =
      Config.system_backend_url + "/token/create/system/receipt/from/invoice";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public createSystemReceipt(token: Token): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(token);
    var url = Config.system_backend_url + "/token/create/system/receipt";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  /*********  Read *********/

  public readHash(): Observable<Token> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    var url = Config.system_backend_url + "/token/read/hash";
    return this.http
      .get(url, options)
      .map((res: Response) => {
        return res.json().token;
      })
      .catch(this.handleError);
  }

  public readInfo(): Observable<Token> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    var url = Config.system_backend_url + "/token/read/networkinfo";
    return this.http
      .get(url, options)
      .map((res: Response) => {
        return res.json().token;
      })
      .catch(this.handleError);
  }

  public readToken(): Observable<Token> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    var url = Config.system_backend_url + "/token/read/blockchaininfo";
    return this.http
      .get(url, options)
      .map((res: Response) => {
        return res.json().token;
      })
      .catch(this.handleError);
  }

  public readTokens(): Observable<Token> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });

    var url = Config.system_backend_url + "/token/read/many";
    return this.http
      .get(url, options)
      .map((res: Response) => {
        return res.json().token;
      })
      .catch(this.handleError);
  }

  /*********  Error *********/

  private handleError(error: any) {
    return Observable.throw({
      "TokenService:": { Code: error.status, Message: error.message },
    });
  }
}
