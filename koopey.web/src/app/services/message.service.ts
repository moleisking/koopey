import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Environment } from "src/environments/environment";
import { Message } from "../models/message";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class MessageService extends BaseService {
  public message = new ReplaySubject<Message>();
  public messages = new ReplaySubject<Array<Message>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

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
    var url = this.getApiUrl() + "/message/read/many/unsent/count";
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
