import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { Search } from "../models/search";

@Injectable()
export class SearchService {
  public search = new ReplaySubject<Search>();

  constructor(private httpClient: HttpClient) {}

  public getSearch(): Observable<Search> {
    return this.search.asObservable();
  }

  public setSearch(search: Search): void {
    this.search.next(search);
  }
}
