import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Location } from "../models/location";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class LocationService extends BaseService {
  public location = new ReplaySubject<Location>();
  public locations = new ReplaySubject<Array<Location>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getMessage(): Observable<Location> {
    return this.location.asObservable();
  }

  public setMessage(location: Location) {
    this.location.next(location);
  }

  public getMessages(): Observable<Array<Location>> {
    return this.locations.asObservable();
  }

  public setMessages(locations: Array<Location>) {
    this.locations.next(locations);
  }

  public create(location: Location): Observable<String> {
    let url = this.getApiUrl + "/location/create";
    return this.httpClient.put<String>(url, location, this.httpHeader);
  }

  public count(): Observable<Number> {
    let url = this.getApiUrl + "/location/read/count";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public delete(location: Location): Observable<String> {
    let url = this.getApiUrl + "/location/delete";
    return this.httpClient.post<String>(url, location, this.httpHeader);
  }

  public readLocations(location: Location): Observable<Array<Location>> {
    let url = this.getApiUrl + "/location/read/many";
    return this.httpClient.get<Array<Location>>(url, this.httpHeader);
  }

  public search(location: Location): Observable<Array<Location>> {
    let url = this.getApiUrl + "/location/search";
    return this.httpClient.post<Array<Location>>(
      url,
      location,
      this.httpHeader
    );
  }

  public searchPlace(location: Location): Observable<Location> {
    let url = this.getApiUrl + "/location/search/place";
    return this.httpClient.post<Location>(url, location, this.httpHeader);
  }

  public searchGeocode(location: Location): Observable<Location> {
    let url = this.getApiUrl + "/location/search/geocode";
    return this.httpClient.post<Location>(url, location, this.httpHeader);
  }

  public update(location: Location): Observable<String> {
    let url = this.getApiUrl + "/location/update";
    return this.httpClient.post<String>(url, location, this.httpHeader);
  }
}
