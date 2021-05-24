import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Alert } from "../models/alert";
import { Environment } from "src/environments/environment";
import { Message } from "../models/message";

@Injectable()
export class MessageService {
  public message = new ReplaySubject<Message>();
  public messages = new ReplaySubject<Array<Message>>();

  public httpHeader = {
    headers: new HttpHeaders({
      Authorization: "JWT " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private translate: TranslateService
  ) {}

  public getMessage(): Observable<Message> {
    return this.message.asObservable();
  }

  public setMessage(message: Message) {
    this.message.next(message);
  }

  public getMessages(): Observable<Array<Message>> {
    return this.messages.asObservable();
  }

  public setMessages(messages: Array<Message>) {
    this.messages.next(messages);
  }

  public create(message: Message): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/message/create";
    return this.httpClient.put<String>(url, message, this.httpHeader);
  }

  public count(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/message/count/";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public countUserUndeliveredMessages(): Observable<Number> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/message/read/many/undelivered/count";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public countUserUnsentMessages(): Observable<Number> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/message/read/many/unsent/count";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public readMessage(message: Message): Observable<Message> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/message/read/one" + name;
    return this.httpClient.get<Message>(url, this.httpHeader);
  }

  public readMessages(): Observable<Array<Message>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/message/read/many";
    return this.httpClient.get<Array<Message>>(url, this.httpHeader);
  }

  public readMessagesUndelivered(): Observable<Array<Message>> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/message/read/many/undelivered";
    return this.httpClient.get<Array<Message>>(url, this.httpHeader);
  }

  public readMessagesUnsent(): Observable<Array<Message>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/message/read/many/unsent";
    return this.httpClient.get<Array<Message>>(url, this.httpHeader);
  }
}
