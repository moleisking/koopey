import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Search } from "../models/search";
import { Transaction } from "../models/transaction";
import { TranslateService } from "@ngx-translate/core";

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

  public count(): Observable<Number> {
    let url = this.baseUrl() + "/transaction/count/";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public create(transaction: Transaction): Observable<String> {
    let url = this.baseUrl() + "/transaction/create";
    return this.httpClient.post<String>(url, transaction, this.privateHeader());
  }

  public delete(transaction: Transaction): Observable<String> {
    let url = this.baseUrl() + "/transaction/delete";
    return this.httpClient.post<String>(url, transaction, this.privateHeader());
  }

  public read(id: string, children: boolean): Observable<Transaction> {
    let url = this.baseUrl() + "/transaction/read/" + id;
    children == true ? url + "?children=true" : url + "?children=false";
    return this.httpClient.get<Transaction>(url, this.privateHeader());
  }

  public search(): Observable<Array<Transaction>> {
    let url = this.baseUrl() + "/transaction/search";
    return this.httpClient.get<Array<Transaction>>(url, this.privateHeader());
  }

  public searchBetweenDates(search: Search): Observable<Array<Transaction>> {
    let url = this.baseUrl() + "/transaction/search/between/dates";
    return this.httpClient.post<Array<Transaction>>(
      url,
      search,
      this.privateHeader()
    );
  }

  public searchByBuyer(children: boolean): Observable<Array<Transaction>> {
    let url = this.baseUrl() + "/transaction/search/by/buyer" + "?children=" + children;
    return this.httpClient.get<Array<Transaction>>(url, this.privateHeader());
  }

  public searchByBuyerOrSeller(): Observable<Array<Transaction>> {
    let url = this.baseUrl() + "/transaction/search/by/buyer/or/seller";
    return this.httpClient.get<Array<Transaction>>(url, this.privateHeader());
  }

  public searchBySeller(children: boolean): Observable<Array<Transaction>> {
    let url = this.baseUrl() + "/transaction/search/by/seller" + "?children=" + children;
    return this.httpClient.get<Array<Transaction>>(url, this.privateHeader());
  }

  public searchByTypeEqualQuote(search: Search): Observable<Array<Transaction>> {
    let url = this.baseUrl() + "/transaction/search/by/type/equal/quote";
    return this.httpClient.post<Array<Transaction>>(url, search, this.privateHeader());
  }

  public update(transaction: Transaction): Observable<void> {
    let url = this.baseUrl() + "/transaction/update";
    return this.httpClient.post<void>(url, transaction, this.privateHeader());
  }
}
