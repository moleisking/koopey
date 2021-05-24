import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Advert } from "../models/advert";
import { Alert } from "../models/alert";
import { Article } from "../models/article";
import { Environment } from "src/environments/environment";
import { File } from "../models/file";
import { Image } from "../models/image";
import { Location } from "../models/location";
import { Review } from "../models/review";
import { Search } from "../models/search";
import { Tag } from "../models/tag";

@Injectable()
export class ArticleService {
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
    var url = Environment.ApiUrls.KoopeyApiUrl + "/article/count/";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public create(article: Article): Observable<String> {
    let url = Environment.ApiUrls.KoopeyApiUrl + "/article/create";
    return this.httpClient.put<String>(url, article, this.httpHeader);
  }

  public delete(article: Article): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/article/delete";
    return this.httpClient.post<String>(url, article, this.httpHeader);
  }

  public readArticle(article: Article): Observable<Article> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/article/read/one/" + article.id;
    return this.httpClient.get<Article>(url, this.httpHeader);
  }

  public readArticles(search: Search): Observable<Array<Article>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/article/read/many";
    return this.httpClient.get<Array<Article>>(url, this.httpHeader);
  }

  public readUserArticles(): Observable<Array<Article>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/article/read/many/mine";
    return this.httpClient.get<Array<Article>>(url, this.httpHeader);
  }

  public update(article: Article): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/article/update";
    return this.httpClient.post<String>(url, article, this.httpHeader);
  }

  /*public readFile(id: string): Observable<any> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ id: id });
    var url = Environment.ApiUrls.KoopeyApiUrl + "/article/read/file/";
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
    var url = Environment.ApiUrls.KoopeyApiUrl + "/article/update/advert";
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
    var url = Environment.ApiUrls.KoopeyApiUrl + "/article/update/image";
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
    var url = Environment.ApiUrls.KoopeyApiUrl + "/article/update/reviews";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }*/

  /*********  Delete *********/
}
