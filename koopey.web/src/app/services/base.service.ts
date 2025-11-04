import { Environment } from "./../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { v7 as uuidv7 } from "uuid";

export class BaseService {

  public type = new ReplaySubject<String>();

  constructor(
    protected httpClient: HttpClient
  ) { }

  protected baseUrl(path?: string) {
    if (path !== undefined) {
      return Environment.ApiUrls.KoopeyApiUrl + path;
    } else {
      return Environment.ApiUrls.KoopeyApiUrl;
    }
  }

  public getType(): Observable<String> {
    return this.type.asObservable();
  }

  public setType(type: String) {
    this.type.next(type);
  }

  protected privateHeader() {
    return {
      headers: new HttpHeaders({
        "Access-Control-Allow-Origin": "http://localhost:4200",
        "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, OPTIONS",
        "Access-Control-Allow-Headers": "Content-Type, Authorization",
        "Authorization": "Bearer " + localStorage.getItem("token"),
        "Cache-Control": "no-cache, no-store, must-revalidate",
        "Content-Type": "application/json",
        "Content-Language": String(localStorage.getItem("language")),
        "TraceId": uuidv7()
      }),
    };
  }

  protected privateHeaderAndBody(body: any) {
    return {
      headers: this.privateHeader().headers,
      body: body
    };
  }

  protected publicHeader() {
    return {
      headers: new HttpHeaders({
        "Access-Control-Allow-Origin": "http://localhost:4200",
        "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, OPTIONS",
        "Access-Control-Allow-Headers": "Content-Type, Authorization",
        "Cache-Control": "no-cache, no-store, must-revalidate",
        "Content-Type": "application/json",
        "Content-Language": String(localStorage.getItem("language")),
        "TraceId": uuidv7()
      }),
    };
  }

}
