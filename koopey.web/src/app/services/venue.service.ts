import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject, Subject } from "rxjs";
import { Environment } from "src/environments/environment";
import { TranslateService } from "@ngx-translate/core";
import { Search } from "../models/search";
import { Venue } from "../models/venue";

@Injectable()
export class VenueService extends BaseService {
  public venue = new ReplaySubject<Venue>();
  public venues = new ReplaySubject<Array<Venue>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getVenue(): Observable<Venue> {
    return this.venue.asObservable();
  }

  public setVenue(venue: Venue) {
    this.venue.next(venue);
  }

  public getVenues(): Observable<Array<Venue>> {
    return this.venues.asObservable();
  }

  public setVenues(venues: Array<Venue>) {
    this.venues.next(venues);
  }

  public create(venue: Venue): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/venue/create";
    return this.httpClient.put<String>(
      url,
      venue,
      this.privateHttpHeader
    );
  }

  public count(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/venue/read/count/";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public delete(venue: Venue): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/venue/delete";
    return this.httpClient.post<String>(
      url,
      venue,
      this.privateHttpHeader
    );
  }

  public read(id: string): Observable<Venue> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/venue/read/" + id;
    return this.httpClient.get<Venue>(url, this.privateHttpHeader);
  }

  public search(): Observable<Array<Venue>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/venue/search";
    return this.httpClient.get<Array<Venue>>(url, this.privateHttpHeader);
  }

  public update(venue: Venue): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/venue/update";
    return this.httpClient.post<String>(
      url,
      venue,
      this.privateHttpHeader
    );
  }

}
