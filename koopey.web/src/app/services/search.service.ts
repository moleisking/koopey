//Angular, Material, Libraries
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Search } from "../models/search";


@Injectable()
export class SearchService {

    private static LOG_HEADER: string = 'SEARCH:SERVICE:';
    public search = new ReplaySubject<Search>()

    constructor(
        private http: Http,
        private translateService: TranslateService
    ) { }

    /*********  Object *********/

    public getSearch(): Observable<Search> {
        return this.search.asObservable();
    }

    public setSearch(search: Search): void {
        this.search.next(search);
    }

    /*********  Error *********/

    private handleError(error: any) {
        return Observable.throw({ "SearchService": { "Code": error.status, "Message": error.message } });
    }
}