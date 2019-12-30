//Core
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Config } from "../config/settings";
import { Tag } from "../models/tag";

@Injectable()
export class TagService {

  private static LOG_HEADER: string = 'TAG:SERVICE:';
  public tag = new ReplaySubject<Tag>();
  public tags = new ReplaySubject<Array<Tag>>();

  constructor(
    private http: Http,
    private translateService: TranslateService
  ) { }

  /*********  Object *********/

  public getTag(): Observable<Tag> {
    return this.tag.asObservable();
  }

  public setTag(tag: Tag): void {
    this.tag.next(tag);
  }

  public getTags(): Observable<Array<Tag>> {
    return this.tags.asObservable()
  }

  public setTags(tags: Array<Tag>): void {
    this.tags.next(tags);
  }

  /*********  Read *********/

  public readAll(): Observable<Array<Tag>> {
    let headers = new Headers();
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    let options = new RequestOptions({ headers: headers });
    var url = Config.system_backend_url + "/tag/read/many";
    return this.http.get(url, { headers: headers }).map((res: Response) => res.json().tags).catch(this.handleError);
  }

  public readProducts(): Observable<Array<Tag>> {
    let headers = new Headers();
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    let options = new RequestOptions({ headers: headers });
    var url = Config.system_backend_url + "/tag/read/many/products";
    return this.http.get(url, { headers: headers }).map((res: Response) => res.json().tags).catch(this.handleError);
  }

  public readServices(): Observable<Array<Tag>> {
    let headers = new Headers();
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    let options = new RequestOptions({ headers: headers });
    var url = Config.system_backend_url + "/tag/read/many/services";
    return this.http.get(url, { headers: headers }).map((res: Response) => res.json().tags).catch(this.handleError);
  }

  /*queryTags(query : String, tags TagModel[]) : Observable<TagModel[]>{
      var lowercaseQuery = query.toLowerCase();
        return tags.filter(function (tag : TagModel) {  
            return tag.en.toLowerCase().indexOf(lowercaseQuery) === 0;
        }) 
  }*/

  private handleError(error: any) {
    return Observable.throw({ "TagService": { "Code": error.status, "Message": error.message } });
  }
}
