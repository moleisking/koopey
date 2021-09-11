import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Article } from "../models/article";
import { Environment } from "src/environments/environment";
import { Search } from "../models/search";
import { BaseService } from "./base.service";

@Injectable()
export class ArticleService extends BaseService {
  public article = new ReplaySubject<Article>();
  public articles = new ReplaySubject<Array<Article>>();

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
}
