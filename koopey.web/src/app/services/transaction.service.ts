//Angular, Material, Libraries
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject, Subject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Search } from "../models/search";
import { Transaction } from "../models/transaction";

@Injectable()
export class TransactionService {

    private static LOG_HEADER: string = 'TRANSACTION:SERVICE:';
    public transaction = new ReplaySubject<Transaction>();
    public transactions = new ReplaySubject<Array<Transaction>>();
  
    constructor(
        private http: Http,
        private translateService: TranslateService
    ) { }

    /*********  Object *********/  

    public getTransaction(): Observable<Transaction> {
        return this.transaction.asObservable();
    }

    public setTransaction(transaction: Transaction) {
        this.transaction.next(transaction);
    }

    public getTransactions(): Observable<Array<Transaction>> {
        return this.transactions.asObservable();
    }

    public setTransactions(transactions: Array<Transaction>) {
        this.transactions.next(transactions);
    }

    /*********  Create *********/

    public create(transaction: Transaction): Observable<Alert> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(transaction);
        var url = Config.system_backend_url + "/transaction/create";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }  

    /*********  Read *********/

    public readCount(): Observable<Number> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/transaction/read/count/";
        return this.http.get(url, options).map((res: Response) => { return res.json().transactions.count }).catch(this.handleError);
    }
    
    public readTransaction(id: string): Observable<Transaction> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify({ 'id': id });
        var url = Config.system_backend_url + "/transaction/read/one";
        return this.http.post(url, options).map((res: Response) => { return res.json().transaction }).catch(this.handleError);
    }

    public readTransactions(): Observable<Array<Transaction>> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/transaction/read/many";
        return this.http.get(url, options).map((res: Response) => { return res.json().transactions }).catch(this.handleError);
    }

    public readTransactionsBetweenDates(search: Search): Observable<Array<Transaction>> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(search);
        var url = Config.system_backend_url + "/transaction/read/many/between/dates";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().transactions }).catch(this.handleError);
    }

    /*********  Update *********/

    public update(transaction: Transaction): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(transaction);
        var url = Config.system_backend_url + "/transaction/update";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public updateStateByBuyer(transaction: Transaction): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(transaction);
        var url = Config.system_backend_url + "/transaction/update/state/by/buyer";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public updateStateBySeller(transaction: Transaction): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ headers: headers });
        let body = JSON.stringify(transaction);
        var url = Config.system_backend_url + "/transaction/update/state/by/seller";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Delete *********/

    public delete(transaction: Transaction): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(transaction);
        var url = Config.system_backend_url + "/transaction/delete";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Errors *********/

    private handleError(error: any) {
        return Observable.throw({ "TransactionService": { "Code": error.status, "Message": error.message } });
    }
}
