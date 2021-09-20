import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject, Subject } from "rxjs";
import { Environment } from "src/environments/environment";
import { TranslateService } from "@ngx-translate/core";
import { Search } from "../models/search";
import { Transaction } from "../models/transaction";

@Injectable()
export class TransactionService extends BaseService {
  public transaction = new ReplaySubject<Transaction>();
  public transactions = new ReplaySubject<Array<Transaction>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

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
    return this.httpClient.put<String>(
      url,
      transaction,
      this.privateHttpHeader
    );
  }

  public count(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/read/count/";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public delete(transaction: Transaction): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/delete";
    return this.httpClient.post<String>(
      url,
      transaction,
      this.privateHttpHeader
    );
  }

  public read(id: string): Observable<Transaction> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/read/" + id;
    return this.httpClient.get<Transaction>(url, this.privateHttpHeader);
  }

  public readMyTransactions(): Observable<Array<Transaction>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/read/me";
    return this.httpClient.get<Array<Transaction>>(url, this.privateHttpHeader);
  }

  public search(): Observable<Array<Transaction>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/search";
    return this.httpClient.get<Array<Transaction>>(url, this.privateHttpHeader);
  }

  public readTransactionsBetweenDates(
    search: Search
  ): Observable<Array<Transaction>> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/transaction/read/many/between/dates";
    return this.httpClient.post<Array<Transaction>>(
      url,
      search,
      this.privateHttpHeader
    );
  }

  public update(transaction: Transaction): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/transaction/update";
    return this.httpClient.post<String>(
      url,
      transaction,
      this.privateHttpHeader
    );
  }

  public updateStateByBuyer(transaction: Transaction): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/transaction/update/state/by/buyer";
    return this.httpClient.post<String>(
      url,
      transaction,
      this.privateHttpHeader
    );
  }

  public updateStateBySeller(transaction: Transaction): Observable<String> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/transaction/update/state/by/seller";
    return this.httpClient.post<String>(
      url,
      transaction,
      this.privateHttpHeader
    );
  }
}
