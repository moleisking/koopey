import { BaseService } from "./base.service";
import { Environment } from "src/environments/environment";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Location } from "../models/location";
import { LocationType } from "../models/type/LocationType";
import { Observable, ReplaySubject } from "rxjs";
import { Search } from "../models/search";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class LocationService extends BaseService {
  public location = new ReplaySubject<Location>();
  public locations = new ReplaySubject<Array<Location>>();
  public type = new ReplaySubject<String>();

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

  public getType(): Observable<String> {
    return this.type.asObservable();
  }

  public setType(type: String) {
    this.type.next(type);
  }

  public count(): Observable<Number> {
    let url = this.baseUrl() + "/location/count";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public create(location: Location): Observable<String> {
    console.log("location:service:create")
    let url = this.baseUrl() + "/location/create";
    return this.httpClient.post<String>(url, location, this.privateHeader());
  }

  public delete(location: Location): Observable<String> {
    let url = this.baseUrl() + "/location/delete";
    return this.httpClient.post<String>(url, location, this.privateHeader());
  }

  public getPosition(): Observable<Location> {
    let location: Location = new Location();
    location.latitude = Environment.Default.Latitude;
    location.longitude = Environment.Default.Longitude;

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        location.latitude = position.coords.latitude;
        location.longitude = position.coords.longitude;
        location.type = LocationType.Position;
        this.location.next(location);
      });
    }

    return this.location;
  }

  public read(locationId: string): Observable<Location> {
    let url = this.baseUrl() + "/location/read/" + locationId;
    return this.httpClient.get<Location>(url, this.privateHeader());
  }

  public search(search: Search): Observable<Array<Location>> {
    let url = this.baseUrl() + "/location/search";
    return this.httpClient.post<Array<Location>>(
      url,
      search,
      this.privateHeader()
    );
  }

  public searchByBuyerAndDestination(): Observable<Array<Location>> {
    let url = this.baseUrl() + "/location/search/by/buyer/and/destination";
    return this.httpClient.get<Array<Location>>(url, this.privateHeader());
  }

  public searchByBuyerAndSource(): Observable<Array<Location>> {
    let url = this.baseUrl() + "/location/search/by/buyer/and/source";
    return this.httpClient.get<Array<Location>>(url, this.privateHeader());
  }

  public searchByDestinationAndSeller(): Observable<Array<Location>> {
    let url = this.baseUrl() + "/location/search/by/destination/and/seller";
    return this.httpClient.get<Array<Location>>(url, this.privateHeader());
  }

  public searchByGeocode(location: Location): Observable<Location> {
    let url = this.baseUrl() + "/location/search/by/geocode";
    return this.httpClient.post<Location>(url, location, this.privateHeader());
  }

  public searchByPlace(location: Location): Observable<Location> {
    let url = this.baseUrl() + "/location/search/by/place";
    return this.httpClient.post<Location>(url, location, this.privateHeader());
  }

  public searchBySellerAndSource(): Observable<Array<Location>> {
    let url = this.baseUrl() + "/location/search/by/seller/and/source";
    return this.httpClient.get<Array<Location>>(url, this.privateHeader());
  }

  public update(location: Location): Observable<void> {
    let url = this.baseUrl() + "/location/update";
    return this.httpClient.post<void>(url, location, this.privateHeader());
  }
}
