import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Alert } from "../models/alert";
import { Environment } from "src/environments/environment";

//@Injectable()
export class BaseService {
  public httpHeader = {
    headers: new HttpHeaders({
      Authorization: "JWT " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
      "Content-Language": String(localStorage.getItem("language")),
    }),
  };

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {}

  protected getApiUrl() {
    return Environment.ApiUrls.KoopeyApiUrl;
  }
}
