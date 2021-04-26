import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable } from "rxjs/Rx";
import { Alert } from "../models/alert";
import { Ethereum } from "../models/ethereum";
import { Config } from "../config/settings";
import { User } from "../models/user";
import { Wallet } from "../models/wallet";

@Injectable()
export class EthereumService {

    constructor(
        private http: Http
    ) { }

    /*********  Create *********/

    public createAccount(): Observable<Ethereum> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/ethereum/create/account";
        return this.http.get(url, options).map((res: Response) => { return res.json().ethereum }).catch(this.handleError);
    }

    /* public createTransaction(ethereum: Ethereum): Observable<Wallet> {
         let headers = new Headers();
         headers.append("Authorization", "JWT " + localStorage.getItem("token"));
         headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
         headers.append("Content-Type", "application/json");
         let options = new RequestOptions({ headers: headers });
         let body = JSON.stringify(ethereum);
         var url = Config.system_backend_url + "/ethereum/create/transaction";
         return this.http.post(url, body, options).map((res: Response) => res.json().wallet).catch(this.handleError);
     }*/

    /* public createTransactionSystem(ethereum: Ethereum): Observable<Wallet> {
         let headers = new Headers();
         headers.append("Authorization", "JWT " + localStorage.getItem("token"));
         headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
         headers.append("Content-Type", "application/json");
         let options = new RequestOptions({ headers: headers });
         let body = JSON.stringify(ethereum);
         var url = Config.system_backend_url + "/ethereum/create/transaction/system";
         return this.http.post(url, body, options).map((res: Response) => res.json().wallet).catch(this.handleError);
     }*/

    public createReceipt(ethereum: Ethereum): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(ethereum);
        var url = Config.system_backend_url + "/ethereum/create/receipt";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public createSystemInvoice(ethereum: Ethereum): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(ethereum);
        var url = Config.system_backend_url + "/ethereum/create/system/invoice";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public createSystemReceiptFromInvoice(ethereum: Ethereum): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(ethereum);
        var url = Config.system_backend_url + "/ethereum/create/system/receipt/from/invoice";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public createSystemReceipt(ethereum: Ethereum): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(ethereum);
        var url = Config.system_backend_url + "/ethereum/create/system/receipt";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Read *********/

    public readBlockNumber(): Observable<Ethereum> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
              var url = Config.system_backend_url + "/ethereum/read/block";
        return this.http.get(url, options).map((res: Response) => { return res.json().ethereum }).catch(this.handleError);
    }

    public readBalance(ethereum: Ethereum): Observable<Ethereum> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(ethereum);
        var url = Config.system_backend_url + "/ethereum/read/balance";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().ethereum }).catch(this.handleError);
    }

    /*********  Error *********/

    private handleError(error: any) {
        return Observable.throw({ "EthereumService": { "Code": error.status, "Message": error.message } });
    }

}