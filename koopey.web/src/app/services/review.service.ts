import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Article } from "../models/article";
import { Asset } from "../models/asset";
import { User } from "../models/user";
import { Environment } from "src/environments/environment";
import { Review } from "../models/review";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class ReviewService extends BaseService {
  public review = new ReplaySubject<Review>();
  public reviews = new ReplaySubject<Array<Review>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getReview(): Observable<Review> {
    return this.review.asObservable();
  }

  public setReview(review: Review) {
    this.review.next(review);
  }

  public getReviews(): Observable<Array<Review>> {
    return this.reviews.asObservable();
  }

  public setReviews(reviews: Array<Review>) {
    this.reviews.next(reviews);
  }

  public create(review: Review): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/create";
    return this.httpClient.put<String>(url, review, this.privateHttpHeader);
  }

  public count(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/read/count";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public countArticleReviews(article: Article): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/count/article";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public countAssetReviews(asset: Asset): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/count/asset";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public countUserReviews(user: User): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/count/user";
    return this.httpClient.get<Number>(url, this.privateHttpHeader);
  }

  public delete(review: Review): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/delete";
    return this.httpClient.post<String>(url, review, this.privateHttpHeader);
  }

  public readArticleReviews(article: Article): Observable<Array<Review>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/read/many/articles";
    return this.httpClient.get<Array<Review>>(url, this.privateHttpHeader);
  }

  public readAssetReviews(asset: Asset): Observable<Array<Review>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/read/many/asset";
    return this.httpClient.get<Array<Review>>(url, this.privateHttpHeader);
  }

  public readUserReviews(user: User): Observable<Array<Review>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/read/many/user";
    return this.httpClient.get<Array<Review>>(url, this.privateHttpHeader);
  }

  public readReview(review: Review): Observable<Review> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/review/read/one/" + review.id;
    return this.httpClient.get<Review>(url, this.privateHttpHeader);
  }

  public update(review: Review): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/update";
    return this.httpClient.post<String>(url, review, this.privateHttpHeader);
  }
}
