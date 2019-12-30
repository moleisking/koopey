//Angular, Material, Libraries
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Advert } from "../models/advert";
import { Alert } from "../models/alert";
import { Article } from "../models/article";
import { Config } from "../config/settings";
import { File } from "../models/file";
import { Image } from "../models/image";
import { Location } from "../models/location";
import { Review } from "../models/review";
import { Search } from "../models/search";
import { Tag } from "../models/tag";

@Injectable()
export class ArticleService {

  private static LOG_HEADER: string = 'ARTICLE:SERVICE:';
  public article = new ReplaySubject<Article>()
  public articles = new ReplaySubject<Array<Article>>()

  constructor(
    private http: Http,
    private translateService: TranslateService
  ) { }

  /*********  Object *********/

  public getArticle(): Observable<Article> {
    return this.article.asObservable();
  }

  public setArticle(article: Article): void {
    this.article.next(article);
  }

  public getArticles(): Observable<Array<Article>> {
    return this.articles.asObservable();
  }

  public setArticles(articles: Array<Article>) {
    this.articles.next(articles);
  }

  /*********  Create *********/

  public createArticle(article: Article): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(article);
    var url = Config.system_backend_url + "/article/create";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Read *********/  

  public readArticle(id: string): Observable<Article> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ id: id });
    var url = Config.system_backend_url + "/article/read/one/";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().article }).catch(this.handleError);
  } 

  public readArticles(search: Search): Observable<Array<Article>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Content-Type", "application/json");
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(search);
    var url = Config.system_backend_url + "/article/read/many";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().articles }).catch(this.handleError);
  }  

  public readCount(): Observable<Number> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/article/read/count/";
    return this.http.get(url, options).map((res: Response) => { return res.json().articles.count }).catch(this.handleError);
  }

  public readFile(id: string): Observable<any> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ id: id });
    var url = Config.system_backend_url + "/article/read/file/";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().article }).catch(this.handleError);
  }

  public readMyArticles(): Observable<Array<Article>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    var url = Config.system_backend_url + "/article/read/many/mine";
    return this.http.get(url, options).map((res: Response) => { return res.json().articles }).catch(this.handleError);
  }  

  /*********  Update *********/

  public update(article: Article): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(article);
    var url = Config.system_backend_url + "/article/update";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateAdvert(article: Article, advert: Advert): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'article':  article, 'advert': advert });
    var url = Config.system_backend_url + "/article/update/advert";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }  
 

  public updateImage(article: Article, image: Image): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'article': article, 'image': image });
    var url = Config.system_backend_url + "/article/update/image";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }  

  public updateReviews(article: Article, reviews: Array<Review>): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'article':  article });
    var url = Config.system_backend_url + "/article/update/reviews";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }  

  /*********  Delete *********/

  public delete(article: Article): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(article);
    var url = Config.system_backend_url + "/article/delete";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }
 
  /*********  Error *********/

  private handleError(error: any) {
    return Observable.throw({ "ArticleService": { "Code": error.status, "Message": error.message } });
  }
}
