import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Article } from "../models/article";
import { Environment } from "src/environments/environment";
import { Search } from "../models/search";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class ArticleService extends BaseService {
  public article = new ReplaySubject<Article>();
  public articles = new ReplaySubject<Array<Article>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

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
    let url = this.baseUrl() + "/article/count/";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public create(article: Article): Observable<String> {
    let url = this.baseUrl() + "/article/create";
    return this.httpClient.put<String>(url, article, this.privateHeader());
  }

  public delete(article: Article): Observable<void> {
    let url = this.baseUrl() + "/article/delete";
    return this.httpClient.post<void>(url, article, this.privateHeader());
  }

  public readArticle(article: Article): Observable<Article> {
    let url = this.baseUrl() + "/article/read/one/" + article.id;
    return this.httpClient.get<Article>(url, this.privateHeader());
  }

  public readArticles(search: Search): Observable<Array<Article>> {
    let url = this.baseUrl() + "/article/read/many";
    return this.httpClient.get<Array<Article>>(url, this.privateHeader());
  }

  public readUserArticles(): Observable<Array<Article>> {
    let url = this.baseUrl() + "/article/read/many/mine";
    return this.httpClient.get<Array<Article>>(url, this.privateHeader());
  }

  public update(article: Article): Observable<void> {
    let url = this.baseUrl() + "/article/update";
    return this.httpClient.post<void>(url, article, this.privateHeader());
  }
}
