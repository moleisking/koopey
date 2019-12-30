//Core
import { Injectable } from "@angular/core";
import { Http, Headers, Response } from "@angular/http";
import { Observable } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Message } from "../models/message";
import { Config } from "../config/settings";

@Injectable()
export class HomeService {

  private static LOG_HEADER: string = 'HOME:SERVICE:';

  constructor(
    private http: Http,
    private translateService: TranslateService
  ) { }

  public home(): Observable<string> {
    var url = Config.system_backend_url + "/home?language=" + this.translateService.currentLang;
    return this.http.get(url).map((res: Response) => res.json().home).catch(this.handleError);
  }

  public about(): Observable<string> {
    var url = Config.system_backend_url + "/about?language=" + this.translateService.currentLang;
    return this.http.get(url).map((res: Response) => res.json()).catch(this.handleError);
  }

  public sendContactForm(name: string, email: string, subject: string, text: string, language: string, ): Observable<any> {
    let headers = new Headers();
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let body = JSON.stringify({ 'name': name, 'email': email, 'subject': subject, 'text': text, 'language': language });
    var url = Config.system_backend_url + "/sendcontactform?language=" + this.translateService.currentLang;
    return this.http.post(url, body, { headers: headers }).map(this.extractData).catch(this.handleError);
  }

  private extractData(res: Response) {
    console.log(res);
    let body = res.json();
    return body.data || {};
  }

  private handleError(error: any) {
    return Observable.throw({ "HomeService": { "Code": error.status, "Message": error.message } });
  }
}
