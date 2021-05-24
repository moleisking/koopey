import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Message } from "../models/message";

import { Environment } from "src/environments/environment";

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
      Environment.ApiUrls.KoopeyApiUrl +
      "/sendcontactform?language=" +
      this.translateService.currentLang;
    return this.httpClient.post<String>(url, body, this.httpHeader);
  }

  public getEnvironmentalVarieable(): String {
    return Environment.ApiKeys.GoogleApiKey;
  }
}
