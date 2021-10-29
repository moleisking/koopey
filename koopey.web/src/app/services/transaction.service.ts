import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject, Subject } from "rxjs";
import { Environment } from "src/environments/environment";
import { TranslateService } from "@ngx-translate/core";
import { Search } from "../models/search";
import { Transaction } from "../models/transaction";
import { LoginComponent } from "../components/authentication/login/login.component";

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
    let url = this.baseUrl() + "/transaction/create";
    return this.httpClient.put<String>(url, transaction, this.privateHeader());
  }

  public count(): Observable<Number> {
    let url = this.baseUrl() + "/transaction/read/count/";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public delete(transaction: Transaction): Observable<String> {
    let url = this.baseUrl() + "/transaction/delete";
    return this.httpClient.post<String>(url, transaction, this.privateHeader());
  }

  public read(id: string): Observable<Transaction> {
    let url = this.baseUrl() + "/transaction/read/" + id;
    return this.httpClient.get<Transaction>(url, this.privateHeader());
  }

  public readMyTransactions(): Observable<Array<Transaction>> {
    let url = this.baseUrl() + "/transaction/read/me";
    return this.httpClient.get<Array<Transaction>>(url, this.privateHeader());
  }

  public search(): Observable<Array<Transaction>> {
    let url = this.baseUrl() + "/transaction/search";
    return this.httpClient.get<Array<Transaction>>(url, this.privateHeader());
  }

  public searchBetweenDates(search: Search): Observable<Array<Transaction>> {
    let url = this.baseUrl() + "/transaction/read/many/between/dates";
    return this.httpClient.post<Array<Transaction>>(
      url,
      search,
      this.privateHeader()
    );
  }

  public update(transaction: Transaction): Observable<void> {
    let url = this.baseUrl() + "/transaction/update";
    console.log(url);
    console.log(transaction);
    return this.httpClient.post<void>(url, transaction, this.privateHeader());
  }
}
