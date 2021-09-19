import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Search } from "../models/search";

@Injectable()
export class SearchService extends BaseService {
  public search = new ReplaySubject<Search>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getSearch(): Observable<Search> {
    return this.search.asObservable();
  }

  public setSearch(search: Search): void {
    this.search.next(search);
  }
}
