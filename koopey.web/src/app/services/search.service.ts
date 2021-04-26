import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Search } from "../models/search";

@Injectable()
export class SearchService {
  public search = new ReplaySubject<Search>();

  constructor() {}

  public getSearch(): Observable<Search> {
    return this.search.asObservable();
  }

  public setSearch(search: Search): void {
    this.search.next(search);
  }
}
