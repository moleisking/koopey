import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class GdprService {
  public gdpr = new ReplaySubject<String>();

  constructor(private httpClient: HttpClient) {}

  public getGdpr(): Observable<String> {
    return this.gdpr.asObservable();
  }

  public setGdpr(gdpr: String): void {
    this.gdpr.next(gdpr);
  }

  public readGdpr(): Observable<String> {
    if (localStorage.getItem("language") === "cn") {
      return this.httpClient.get<String>("assets/gdpr/gdpr.cn.txt");
    } else if (localStorage.getItem("language") === "en") {
      return this.httpClient.get<String>("assets/gdpr/gdpr.en.txt");
    } else if (localStorage.getItem("language") === "de") {
      return this.httpClient.get<String>("assets/gdpr/gdpr.de.txt");
    } else if (localStorage.getItem("language") === "fr") {
      return this.httpClient.get<String>("assets/gdpr/gdpr.fr.txt");
    } else if (localStorage.getItem("language") === "pt") {
      return this.httpClient.get<String>("assets/gdpr/gdpr.pt.txt");
    } else {
      return this.httpClient.get<String>("assets/gdpr/gdpr.en.txt");
    }
  }
}
// , { responseType: "text", }
