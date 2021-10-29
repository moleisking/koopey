import { BaseService } from "./base.service";
import { Contact } from "../models/contact/contact";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class HomeService extends BaseService {
  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public sendContactForm(contact: Contact): Observable<String> {
    let url = super.baseUrl + "/home/contact";
    return this.httpClient.post<String>(url, contact, this.publicHeader());
  }
}
