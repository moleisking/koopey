import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Message } from "../models/message";
import { TranslateService } from "@ngx-translate/core";
import { User } from "../models/user";
import { MessageType } from "../models/type/MessageType";

@Injectable()
export class MessageService extends BaseService {
  public message = new ReplaySubject<Message>();
  public messages = new ReplaySubject<Array<Message>>();

  constructor(
    protected httpClient: HttpClient
  ) {
    super(httpClient);
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
    let url = this.baseUrl() + "/message/create";
    return this.httpClient.post<String>(url, message, this.privateHeader());
  }

  public count(): Observable<Number> {
    let url = this.baseUrl() + "/message/count";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public countByReceiver(type? : MessageType): Observable<Number> {
    let url = this.baseUrl() + "/message/count/by/receiver";

    if (type) {
      url += "?type=" + type;
    } 

    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public countByReceiverAndSender(type? : MessageType): Observable<Number> {
    let url = this.baseUrl() + "/message/count/by/receiver/and/sender";

    if (type) {
      url += "?type=" + type;
    } 

    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public countBySender(type? : MessageType): Observable<Number> {
    let url = this.baseUrl() + "/message/count/by/sender";

    if (type) {
      url += "?type=" + type;
    } 

    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public read(messageId: string): Observable<Message> {
    let url = this.baseUrl() + "/message/read/" + name;
    return this.httpClient.get<Message>(url, this.privateHeader());
  }

  public search(): Observable<Array<Message>> {
    let url = this.baseUrl() + "/message/search";
    return this.httpClient.get<Array<Message>>(url, this.privateHeader());
  }

  public searchByReceiverOrSender(): Observable<Array<Message>> {
    let url = this.baseUrl() + "/message/search/by/receiver/or/sender";
    return this.httpClient.get<Array<Message>>(url, this.privateHeader());
  }

  public searchByReceiverOrSenderAndType(type: MessageType): Observable<Array<Message>> {
    let url = this.baseUrl() + "/message/search/by/receiver/or/sender?type="+type;
    return this.httpClient.get<Array<Message>>(url, this.privateHeader());
  }

}
