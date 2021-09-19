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

  public readGdpr(): String {
    let data: String = "";
    if (localStorage.getItem("language") === "cn") {
      this.httpClient
        .get("assets/gdpr/gdpr.cn.txt", { responseType: "text" })
        .subscribe((content) => {
          data = content;
        });
    } else if (localStorage.getItem("language") === "en") {
      this.httpClient
        .get("assets/gdpr/gdpr.en.txt", { responseType: "text" })
        .subscribe((content) => {
          data = content;
        });
    } else if (localStorage.getItem("language") === "de") {
      this.httpClient
        .get("assets/gdpr/gdpr.de.txt", { responseType: "text" })
        .subscribe((content) => {
          data = content;
        });
    } else if (localStorage.getItem("language") === "fr") {
      this.httpClient
        .get("assets/gdpr/gdpr.fr.txt", { responseType: "text" })
        .subscribe((content) => {
          data = content;
        });
    } else if (localStorage.getItem("language") === "pt") {
      this.httpClient
        .get("assets/gdpr/gdpr.pt.txt", { responseType: "text" })
        .subscribe((content) => {
          data = content;
        });
    } else {
      this.httpClient
        .get("assets/gdpr/gdpr.en.txt", { responseType: "text" })
        .subscribe((content) => {
          data = content;
        });
    }
    return data;
  }
}
