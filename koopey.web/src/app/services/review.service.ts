import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Article } from "../models/article";
import { Asset } from "../models/asset";
import { User } from "../models/user";
import { Environment } from "src/environments/environment";
import { Review } from "../models/review";

@Injectable()
export class ReviewService {
  public review = new ReplaySubject<Review>();
  public reviews = new ReplaySubject<Array<Review>>();

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
    return this.httpClient.put<String>(url, review, this.httpHeader);
  }

  public count(): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/read/count";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public countArticleReviews(article: Article): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/count/article";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public countAssetReviews(asset: Asset): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/count/asset";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public countUserReviews(user: User): Observable<Number> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/count/user";
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public delete(review: Review): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/delete";
    return this.httpClient.post<String>(url, review, this.httpHeader);
  }

  public readArticleReviews(article: Article): Observable<Array<Review>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/read/many/articles";
    return this.httpClient.get<Array<Review>>(url, this.httpHeader);
  }

  public readAssetReviews(asset: Asset): Observable<Array<Review>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/read/many/asset";
    return this.httpClient.get<Array<Review>>(url, this.httpHeader);
  }

  public readUserReviews(user: User): Observable<Array<Review>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/read/many/user";
    return this.httpClient.get<Array<Review>>(url, this.httpHeader);
  }

  public readReview(review: Review): Observable<Review> {
    var url =
      Environment.ApiUrls.KoopeyApiUrl + "/review/read/one/" + review.id;
    return this.httpClient.get<Review>(url, this.httpHeader);
  }

  public update(review: Review): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/review/update";
    return this.httpClient.post<String>(url, review, this.httpHeader);
  }
}
