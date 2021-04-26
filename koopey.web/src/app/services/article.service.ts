//Angular, Material, Libraries
import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
//Services
import { TranslateService } from "@ngx-translate/core";
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
  private static LOG_HEADER: string = "ARTICLE:SERVICE:";
  public article = new ReplaySubject<Article>();
  public articles = new ReplaySubject<Array<Article>>();

  public httpHeader = {
    headers: new HttpHeaders({
      Authorization: "JWT " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private translateService: TranslateService
  ) {}

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

  public count(): Observable<Number> {
    var url = Config.system_backend_url + "/article/count/";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public create(article: Article): Observable<String> {
    let url = Config.system_backend_url + "/article/create";
    return this.httpClient.put<String>(url, article, this.httpHeader);
  }

  public delete(article: Article): Observable<String> {
    var url = Config.system_backend_url + "/article/delete";
    return this.httpClient.post<String>(url, article, this.httpHeader);
  }

  public readArticle(article: Article): Observable<Article> {
    var url = Config.system_backend_url + "/article/read/one/" + article.id;
    return this.httpClient.get<Article>(url, this.httpHeader);
  }

  public readArticles(search: Search): Observable<Array<Article>> {
    var url = Config.system_backend_url + "/article/read/many";
    return this.httpClient.get<Array<Article>>(url, this.httpHeader);
  }

  public readUserArticles(): Observable<Array<Article>> {
    var url = Config.system_backend_url + "/article/read/many/mine";
    return this.httpClient.get<Array<Article>>(url, this.httpHeader);
  }

  public update(article: Article): Observable<String> {
    var url = Config.system_backend_url + "/article/update";
    return this.httpClient.post<String>(url, article, this.httpHeader);
  }

  /*public readFile(id: string): Observable<any> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ id: id });
    var url = Config.system_backend_url + "/article/read/file/";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().article;
      })
      .catch(this.handleError);
  }*/

  /*********  Update *********/

  /*public updateAdvert(article: Article, advert: Advert): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ article: article, advert: advert });
    var url = Config.system_backend_url + "/article/update/advert";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public updateImage(article: Article, image: Image): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ article: article, image: image });
    var url = Config.system_backend_url + "/article/update/image";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public updateReviews(
    article: Article,
    reviews: Array<Review>
  ): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ article: article });
    var url = Config.system_backend_url + "/article/update/reviews";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }*/

  /*********  Delete *********/
}
