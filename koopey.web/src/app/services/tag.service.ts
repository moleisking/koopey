import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "src/environments/environment";
import { Tag } from "../models/tag";
import { BaseService } from "./base.service";

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
    var url = Environment.ApiUrls.KoopeyApiUrl + "/tag/read/many";
    return this.httpClient.get<Array<Tag>>(url, this.httpHeader);
  }

  public readProducts(): Observable<Array<Tag>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/tag/read/many/products";
    return this.httpClient.get<Array<Tag>>(url, this.httpHeader);
  }

  public readServices(): Observable<Array<Tag>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/tag/read/many/services";
    return this.httpClient.get<Array<Tag>>(url, this.httpHeader);
  }

  /*queryTags(query : String, tags TagModel[]) : Observable<TagModel[]>{
      var lowercaseQuery = query.toLowerCase();
        return tags.filter(function (tag : TagModel) {  
            return tag.en.toLowerCase().indexOf(lowercaseQuery) === 0;
        }) 
  }*/
}
