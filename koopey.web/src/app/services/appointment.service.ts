import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "ng2-translate";
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Event } from "../models/event";
import { Search } from "../models/search";

@Injectable()
export class AppointmentService {
  private static LOG_HEADER: string = "EVENT:SERVICE:";
  public event = new ReplaySubject<Event>();
  public events = new ReplaySubject<Array<Event>>();

  public httpHeader = {
    headers: new HttpHeaders({
      Authorization: "JWT " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private translateService: TranslateService
  ) {}

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

  public create(event: Event): Observable<String> {
    var url = Config.system_backend_url + "/event/create/one";
    return this.httpClient.put<String>(url, event, this.httpHeader);
  }

  public createEvents(events: Array<Event>): Observable<String> {
    var url = Config.system_backend_url + "/event/create/many";
    return this.httpClient.put<String>(url, events, this.httpHeader);
  }

  public deleteEvent(event: Event): Observable<String> {
    var url = Config.system_backend_url + "/event/delete/one";
    return this.httpClient.put<String>(url, event, this.httpHeader);
  }

  public deleteEvents(events: Array<Event>): Observable<String> {
    var url = Config.system_backend_url + "/event/delete/many";
    return this.httpClient.put<String>(url, events, this.httpHeader);
  }

  public readEvent(event: Event): Observable<Event> {
    var url = Config.system_backend_url + "/event/read/one";
    return this.httpClient.put<Event>(url, event, this.httpHeader);
  }

  public readEvents(): Observable<Array<Event>> {
    var url = Config.system_backend_url + "/event/read/many";
    return this.httpClient.put<Array<Event>>(url, this.httpHeader);
  }

  public readEventsBetweenDates(search: Search): Observable<Array<Event>> {
    var url = Config.system_backend_url + "/event/read/many/between/dates";
    return this.httpClient.post<Array<Event>>(url, search, this.httpHeader);
  }

  public readUserEvent(): Observable<Event> {
    var url = Config.system_backend_url + "/event/read/one/mine";
    return this.httpClient.get<Event>(url, this.httpHeader);
  }

  public readUserEvents(search: Search): Observable<Array<Event>> {
    var url = Config.system_backend_url + "/event/read/many/mine";
    return this.httpClient.get<Array<Event>>(url, this.httpHeader);
  }

  public readMyEventsBetweenDates(search: Search): Observable<Array<Event>> {
    var url = Config.system_backend_url + "/event/read/many/between/dates/mine";
    return this.httpClient.post<Array<Event>>(url, search, this.httpHeader);
  }

  public updateEvent(event: Event): Observable<String> {
    var url = Config.system_backend_url + "/event/update";
    return this.httpClient.post<String>(url, event, this.httpHeader);
  }

  public updateEvents(event: Array<Event>): Observable<String> {
    var url = Config.system_backend_url + "/event/update";
    return this.httpClient.post<String>(url, event, this.httpHeader);
  }
}
