import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";

@Injectable()
export class ToolbarService {
    public title = new ReplaySubject<String>();

    constructor(    ) { }

    public getTitleKey(): Observable<String> {
        return this.title.asObservable();
    }

    public setTitleKey(title: String) {
        this.title.next(title);
    }

}