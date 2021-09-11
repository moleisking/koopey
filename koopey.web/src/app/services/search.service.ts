import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Search } from "../models/search";
import { BaseService } from "./base.service";

@Injectable()
export class SearchService extends BaseService {
  public search = new ReplaySubject<Search>();

  public getSearch(): Observable<Search> {
    return this.search.asObservable();
  }

  public setSearch(search: Search): void {
    this.search.next(search);
  }
}
