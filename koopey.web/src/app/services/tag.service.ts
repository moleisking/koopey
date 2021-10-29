import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Tag } from "../models/tag";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class TagService extends BaseService {
  public tag = new ReplaySubject<Tag>();
  public tags = new ReplaySubject<Array<Tag>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getTag(): Observable<Tag> {
    return this.tag.asObservable();
  }

  public setTag(tag: Tag): void {
    this.tag.next(tag);
  }

  public getTags(): Observable<Array<Tag>> {
    return this.tags.asObservable();
  }

  public setTags(tags: Array<Tag>): void {
    this.tags.next(tags);
  }

  public readTags(): Observable<Array<Tag>> {
    let url = this.baseUrl() + "/tag/read/many";
    return this.httpClient.get<Array<Tag>>(url, this.privateHeader());
  }

  public readSuggestions(value: String): Observable<Array<Tag>> {
    let url = this.baseUrl() + "/tag/read/suggestions/{value}";
    return this.httpClient.get<Array<Tag>>(url, this.privateHeader());
  }

  public readProducts(): Observable<Array<Tag>> {
    let url = this.baseUrl() + "/tag/read/many/products";
    return this.httpClient.get<Array<Tag>>(url, this.privateHeader());
  }

  public readServices(): Observable<Array<Tag>> {
    let url = this.baseUrl() + "/tag/read/many/services";
    return this.httpClient.get<Array<Tag>>(url, this.privateHeader());
  }
}
