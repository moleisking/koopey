import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject, Subject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "src/environments/environment";
import { Search } from "../models/search";
import { Transaction } from "../models/transaction";

@Injectable()
export class TransactionService {
  public transaction = new ReplaySubject<Transaction>();
  public transactions = new ReplaySubject<Array<Transaction>>();

  public httpHeader = {
    headers: new HttpHeaders({
      Authorization: "JWT " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private translateService: TranslateService
  ) {}

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

  public create(transaction: Transaction): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/create";
    return this.httpClient.put<String>(url, transaction, this.httpHeader);
  }

  public count(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/read/count/";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public delete(transaction: Transaction): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/delete";
    return this.httpClient.post<String>(url, transaction, this.httpHeader);
  }

  public readTransaction(id: string): Observable<Transaction> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/read/one";
    return this.httpClient.get<Transaction>(url, this.httpHeader);
  }

  public readTransactions(): Observable<Array<Transaction>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/read/many";
    return this.httpClient.get<Array<Transaction>>(url, this.httpHeader);
  }

  public readTransactionsBetweenDates(
    search: Search
  ): Observable<Array<Transaction>> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/transaction/read/many/between/dates";
    return this.httpClient.post<Array<Transaction>>(
      url,
      search,
      this.httpHeader
    );
  }

  public update(transaction: Transaction): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/update";
    return this.httpClient.post<String>(url, transaction, this.httpHeader);
  }

  public updateStateByBuyer(transaction: Transaction): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/transaction/update/state/by/buyer";
    return this.httpClient.post<String>(url, transaction, this.httpHeader);
  }

  public updateStateBySeller(transaction: Transaction): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/transaction/update/state/by/seller";
    return this.httpClient.post<String>(url, transaction, this.httpHeader);
  }
}
