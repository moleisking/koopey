//Core
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Event } from "../models/event";
import { Search } from "../models/search";

@Injectable()
export class EventService {

    private static LOG_HEADER: string = 'EVENT:SERVICE:';
    public event = new ReplaySubject<Event>();
    public events = new ReplaySubject<Array<Event>>();

    constructor(
        private http: Http,
        private translateService: TranslateService
    ) { }

    /*********  Object *********/

    public getEvent(): Observable<Event> {
        return this.event.asObservable();
    }

    public setEvent(event: Event): void {
        this.event.next(event);
    }

    public getEvents(): Observable<Array<Event>> {
        return this.events.asObservable();
    }

    public setEvents(events: Array<Event>): void {
        this.events.next(events);
    }

    /*********  Create *********/

    public createEvent(event: Event): Observable<Alert> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(event);
        var url = Config.system_backend_url + "/event/create/one";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public createEvents(events: Array<Event>): Observable<Alert> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(events);
        var url = Config.system_backend_url + "/event/create/many";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Read *********/

    public readEvent(id: string): Observable<Event> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/event/read/one";
        return this.http.get(url, options).map((res: Response) => { return res.json().event }).catch(this.handleError);
    }

    public readEvents(): Observable<Array<Event>> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/event/read/many";
        return this.http.get(url, options).map((res: Response) => { return res.json().events }).catch(this.handleError);
    }

    public readEventsBetweenDates(search: Search): Observable<Array<Event>> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(search);
        var url = Config.system_backend_url + "/event/read/many/between/dates";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().events }).catch(this.handleError);
    }

    public readMyEvent(id: string): Observable<Event> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/event/read/one/mine";
        return this.http.get(url, options).map((res: Response) => { return res.json().event }).catch(this.handleError);
    }

    public readMyEvents(search: Search): Observable<Array<Event>> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(search);
        var url = Config.system_backend_url + "/event/read/many/mine";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().events }).catch(this.handleError);
    }

    public readMyEventsBetweenDates(search: Search): Observable<Array<Event>> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(search);
        var url = Config.system_backend_url + "/event/read/many/between/dates/mine";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().events }).catch(this.handleError);
    }

    /*********  Update *********/

    public updateEvent(event: Event): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(event);
        var url = Config.system_backend_url + "/event/update";
        return this.http.post(url, body, options).map((res: Response) => { return  res.json().alert }).catch(this.handleError);
    }

    public updateEvents(event: Array<Event>): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(event);
        var url = Config.system_backend_url + "/event/update";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Delete *********/

    public deleteEvent(event: Event): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(event);
        var url = Config.system_backend_url + "/event/delete/one";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    public deleteEvents(events: Array<Event>): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(events);
        var url = Config.system_backend_url + "/event/delete/many";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Errors *********/

    private handleError(error: any) {
        return Observable.throw({ "EventService": { "Code": error.status, "Message": error.message } });
    }
}
