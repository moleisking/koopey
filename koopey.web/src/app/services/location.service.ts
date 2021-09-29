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

  public getLocation(): Observable<Location> {
    return this.location.asObservable();
  }

  public setLocation(location: Location) {
    this.location.next(location);
  }

  public getLocations(): Observable<Array<Location>> {
    return this.locations.asObservable();
  }

  public setLocations(locations: Array<Location>) {
    this.locations.next(locations);
  }

  public create(location: Location): Observable<String> {
    let url = this.baseUrl() + "/location/create";
    return this.httpClient.put<String>(url, location, this.privateHttpHeader);
  }

  public count(): Observable<Number> {
    let url = this.baseUrl() + "/location/read/count";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public delete(location: Location): Observable<String> {
    let url = this.baseUrl() + "/location/delete";
    return this.httpClient.post<String>(url, location, this.privateHttpHeader);
  }

  public readMyLocations(): Observable<Array<Location>> {
    let url = this.baseUrl + "/location/read/me/many";
    return this.httpClient.get<Array<Location>>(url, this.privateHttpHeader);
  }

  public search(location: Location): Observable<Array<Location>> {
    let url = this.baseUrl() + "/location/search";
    return this.httpClient.post<Array<Location>>(
      url,
      location,
      this.privateHttpHeader
    );
  }

  public searchPlace(location: Location): Observable<Location> {
    let url = this.baseUrl() + "/location/search/place";
    return this.httpClient.post<Location>(
      url,
      location,
      this.privateHttpHeader
    );
  }

  public searchGeocode(location: Location): Observable<Location> {
    let url = this.baseUrl() + "/location/search/geocode";
    return this.httpClient.post<Location>(
      url,
      location,
      this.privateHttpHeader
    );
  }

  public update(location: Location): Observable<String> {
    let url = this.baseUrl() + "/location/update";
    return this.httpClient.post<String>(url, location, this.privateHttpHeader);
  }
}
