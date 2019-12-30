import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable } from "rxjs/Rx";
import { Alert } from "../models/alert";
import { Bitcoin } from "../models/bitcoin";
import { Config } from "../config/settings";
import { User } from "../models/user";
import { Wallet } from "../models/wallet";

@Injectable()
export class BitcoinService {

    constructor(
        private http: Http
    ) { }

    /*********  Create *********/

    public createAddress(): Observable<Wallet> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/bitcoin/create/address";
        return this.http.get(url, options).map((res: Response) => { return res.json().wallet }).catch(this.handleError);
    }

    public createReceipt(bitcoin: Bitcoin): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(bitcoin);
        var url = Config.system_backend_url + "/bitcoin/create/receipt";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public createSystemInvoice(bitcoin: Bitcoin): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(bitcoin);
        var url = Config.system_backend_url + "/bitcoin/create/system/invoice";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public createSystemReceiptFromInvoice(bitcoin: Bitcoin): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(bitcoin);
        var url = Config.system_backend_url + "/bitcoin/create/system/receipt/from/invoice";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public createSystemReceipt(bitcoin: Bitcoin): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(bitcoin);
        var url = Config.system_backend_url + "/bitcoin/create/system/receipt";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Read *********/

    public readBalance(bitcoin: Bitcoin): Observable<Bitcoin> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(bitcoin);
        var url = Config.system_backend_url + "/bitcoin/read/balance";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().bitcoin }).catch(this.handleError);
    }
    
    public readHash(): Observable<Bitcoin> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });      
        var url = Config.system_backend_url + "/bitcoin/read/hash";
        return this.http.get(url, options).map((res: Response) => { return res.json().bitcoin }).catch(this.handleError);
    }

    public readBlockchainInfo(): Observable<Bitcoin> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });        
        var url = Config.system_backend_url + "/bitcoin/read/blockchaininfo";
        return this.http.get(url, options).map((res: Response) => { return res.json().bitcoin }).catch(this.handleError);
    }

    public readNetworkInfo(): Observable<Bitcoin> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });        
        var url = Config.system_backend_url + "/bitcoin/read/networkinfo";
        return this.http.get(url, options).map((res: Response) => { return res.json().bitcoin }).catch(this.handleError);
    }

    public readWalletInfo(): Observable<Bitcoin> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });        
        var url = Config.system_backend_url + "/bitcoin/read/walletinfo";
        return this.http.get(url, options).map((res: Response) => { return res.json().bitcoin }).catch(this.handleError);
    }

    /*********  Error *********/

    private handleError(error: any) {
        return Observable.throw({ "BitcoinService:": { "Code": error.status, "Message": error.message } });
    }
}