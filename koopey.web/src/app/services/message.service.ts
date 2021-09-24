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
    return this.httpClient.put<String>(url, message, this.privateHttpHeader);
  }

  public count(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/message/count/";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public countUserUndeliveredMessages(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/message/count/notarrive";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public countUserUnsentMessages(): Observable<Number> {
    var url = this.baseUrl() + "/message/count/notsent";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public readMessage(message: Message): Observable<Message> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/message/read/one" + name;
    return this.httpClient.get<Message>(url, this.privateHttpHeader);
  }

  public readMessages(): Observable<Array<Message>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/message/read/many";
    return this.httpClient.get<Array<Message>>(url, this.privateHttpHeader);
  }

  public readMessagesUndelivered(): Observable<Array<Message>> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/message/read/many/undelivered";
    return this.httpClient.get<Array<Message>>(url, this.privateHttpHeader);
  }

  public readMessagesUnsent(): Observable<Array<Message>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/message/read/many/unsent";
    return this.httpClient.get<Array<Message>>(url, this.privateHttpHeader);
  }
}
