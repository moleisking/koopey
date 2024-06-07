import { Environment } from "src/environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";

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
        Authorization: "Bearer " + localStorage.getItem("token"),
        "Cache-Control": "no-cache, no-store, must-revalidate",
        "Content-Type": "application/json",
        "Content-Language": String(localStorage.getItem("language")),
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
        "Cache-Control": "no-cache, no-store, must-revalidate",
        "Content-Type": "application/json",
        "Content-Language": String(localStorage.getItem("language")),
      }),
    };
  }

}
