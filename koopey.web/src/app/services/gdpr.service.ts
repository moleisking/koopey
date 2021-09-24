import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable()
export class GdprService {
  public gdpr = new ReplaySubject<String>();

  public fileHttpHeader: Object = {
    headers: new HttpHeaders({
      Accept: "text/html",
      "Content-Type": "text/plain; charset=utf-8",
      "Content-Language": String(localStorage.getItem("language")),
    }),
    responseType: "text" as const,
  };

  constructor(private httpClient: HttpClient) {}

  public getGdpr(): Observable<String> {
    return this.gdpr.asObservable();
  }

  public setGdpr(gdpr: String): void {
    this.gdpr.next(gdpr);
  }

  public readGdpr(): Observable<any> {
    if (localStorage.getItem("language") === "cn") {
      return this.httpClient.get<any>(
        "assets/gdpr/gdpr.cn.txt",
        this.fileHttpHeader
      );
    } else if (localStorage.getItem("language") === "en") {
      return this.httpClient.get<any>(
        "assets/gdpr/gdpr.en.txt",
        this.fileHttpHeader
      );
    } else if (localStorage.getItem("language") === "de") {
      return this.httpClient.get<any>(
        "assets/gdpr/gdpr.de.txt",
        this.fileHttpHeader
      );
    } else if (localStorage.getItem("language") === "fr") {
      return this.httpClient.get<any>(
        "assets/gdpr/gdpr.fr.txt",
        this.fileHttpHeader
      );
    } else if (localStorage.getItem("language") === "pt") {
      return this.httpClient.get<any>(
        "assets/gdpr/gdpr.pt.txt",
        this.fileHttpHeader
      );
    } else {
      return this.httpClient.get<any>(
        "assets/gdpr/gdpr.en.txt",
        this.fileHttpHeader
      );
    }
  }
}
