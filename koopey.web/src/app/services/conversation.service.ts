import { BaseService } from "./base.service";
import { Conversation } from "../models/conversation";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class ConversationService extends BaseService {
  public conversation = new ReplaySubject<Conversation>();
  public conversations = new ReplaySubject<Array<Conversation>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getConversation(): Observable<Conversation> {
    return this.conversation.asObservable();
  }

  public setConversation(conversation: Conversation) {
    this.conversation.next(conversation);
  }

  public getConversations(): Observable<Array<Conversation>> {
    return this.conversations.asObservable();
  }

  public setConversations(conversations: Array<Conversation>) {
    this.conversations.next(conversations);
  }

  public create(conversation: Conversation): Observable<String> {
    let url = this.baseUrl + "/conversation/create";
    return this.httpClient.put<String>(
      url,
      conversation,
      this.privateHttpHeader
    );
  }

  public count(): Observable<Number> {
    let url = this.baseUrl + "/conversation/read/count";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public delete(conversation: Conversation): Observable<String> {
    let url = this.baseUrl + "/conversation/delete";
    return this.httpClient.post<String>(
      url,
      conversation,
      this.privateHttpHeader
    );
  }

  public read(conversation: Conversation): Observable<Array<Conversation>> {
    let url = this.baseUrl + "/conversation/read/many";
    return this.httpClient.get<Array<Conversation>>(
      url,
      this.privateHttpHeader
    );
  }

  public update(conversation: Conversation): Observable<String> {
    let url = this.baseUrl + "/conversation/update";
    return this.httpClient.post<String>(
      url,
      conversation,
      this.privateHttpHeader
    );
  }
}
