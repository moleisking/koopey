import { Environment } from "src/environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { TranslateService } from "@ngx-translate/core";

export class BaseService {
  protected privateHttpHeader = {
    headers: new HttpHeaders({
      Authorization: "Bearer " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
      "Content-Language": String(localStorage.getItem("language")),
    }),
  };

  public publicHttpHeader = {
    headers: new HttpHeaders({
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
      "Content-Language": String(localStorage.getItem("language")),
    }),
  };

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {}

  protected ApiUrl() {
    return Environment.ApiUrls.KoopeyApiUrl;
  }
}
