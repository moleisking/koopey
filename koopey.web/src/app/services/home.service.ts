import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Message } from "../models/message";

import { Environment } from "src/environments/environment";
import { BaseService } from "./base.service";

@Injectable()
export class HomeService extends BaseService {
  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

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
    var url = Environment.ApiUrls.KoopeyApiUrl + "/sendcontactform";
    return this.httpClient.post<String>(url, body, this.publicHttpHeader);
  }

  public getEnvironmentalVarieable(): String {
    return Environment.ApiKeys.GoogleApiKey;
  }
}
