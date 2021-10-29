import { Environment } from "src/environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { TranslateService } from "@ngx-translate/core";

export class BaseService {
  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {}

  protected baseUrl(path?: string) {
    if (path !== undefined) {
      return Environment.ApiUrls.KoopeyApiUrl + path;
    } else {
      return Environment.ApiUrls.KoopeyApiUrl;
    }
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
