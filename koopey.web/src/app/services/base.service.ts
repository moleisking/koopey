import { Environment } from "src/environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { TranslateService } from "@ngx-translate/core";

export class BaseService {
  public httpHeader = {
    headers: new HttpHeaders({
      Authorization: "Bearer " + localStorage.getItem("token"),
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
