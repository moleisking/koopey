import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { TranslateService } from "ng2-translate";
import { Message } from "../models/message";
import { Config } from "../config/settings";

@Injectable()
export class HomeService {
  public httpHeader = {
    headers: new HttpHeaders({
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private translateService: TranslateService
  ) {}

  public sendContactForm(
    name: string,
    email: string,
    subject: string,
    text: string,
    language: string
  ): Observable<any> {
    let body = JSON.stringify({
      name: name,
      email: email,
      subject: subject,
      text: text,
      language: language,
    });
    var url =
      Config.system_backend_url +
      "/sendcontactform?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(url, body, this.httpHeader);
  }
}
