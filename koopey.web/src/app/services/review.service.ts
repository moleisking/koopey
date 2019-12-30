//Core
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Alert } from "../models/alert";
import { Article } from "../models/article";
import { Asset } from "../models/asset";
import { Config } from "../config/settings";
import { Review } from "../models/review";
import { User } from "../models/user";

@Injectable()
export class ReviewService {

    private static LOG_HEADER: string = 'REVIEW:SERVICE:';
    public review = new ReplaySubject<Review>();
    public reviews = new ReplaySubject<Array<Review>>();

    constructor(
        private http: Http,
        private translateService: TranslateService
    ) { }

    /*********  Object *********/

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

    /*********  Create *********/

    public createReview(review: Review): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(review);
        var url = Config.system_backend_url + "/review/create";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Read *********/

    public readReview(id: string): Observable<Review> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify({ 'id': id });
        var url = Config.system_backend_url + "/review/read/one/";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().review }).catch(this.handleError);
    }

    public readReviewCount(review: Review): Observable<Number> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(review);
        var url = Config.system_backend_url + "/review/read/count/";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().reviews.count }).catch(this.handleError);
    }

    public readArticleReviewCount(article: Article): Observable<Number> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify({ 'articleId': article.id });
        var url = Config.system_backend_url + "/review/read/count/article";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().reviews.count }).catch(this.handleError);
    }

    public readArticleReviews(article: Article): Observable<Array<Review>> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify({ 'articleId': article.id });
        var url = Config.system_backend_url + "/review/read/many/articles";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().reviews }).catch(this.handleError);
    }

    public readAssetReviewCount(asset: Asset): Observable<Number> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify({ 'assetId': asset.id });
        var url = Config.system_backend_url + "/review/read/count/asset";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().reviews.count }).catch(this.handleError);
    }

    public readAssetReviews(asset: Asset): Observable<Array<Review>> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify({ 'assetId': asset.id });
        var url = Config.system_backend_url + "/review/read/many/asset";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().reviews }).catch(this.handleError);
    }

    public readUserReviewCount(user: User): Observable<Number> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify({ 'userId': user.id });
        var url = Config.system_backend_url + "/review/read/count/user";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().reviews.count }).catch(this.handleError);
    }

    public readUserReviews(user: User): Observable<Array<Review>> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify({ 'userId': user.id });
        var url = Config.system_backend_url + "/review/read/many/user";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().reviews }).catch(this.handleError);
    }

    /*********  Update *********/

    public updateReview(review: Review): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(review);
        var url = Config.system_backend_url + "/review/update";
        return this.http.post(url, body, options).map((res: Response) => { return res.json() }).catch(this.handleError);
    }

    /*********  Delete *********/

    public deleteReview(review: Review): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify({ 'id': review.id });
        var url = Config.system_backend_url + "/review/delete";
        return this.http.post(url, body, options).map((res: Response) => { return res.json() }).catch(this.handleError);
    }

    public deleteAssetReviews(asset: Asset): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify({ 'assetId': asset.id });
        var url = Config.system_backend_url + "/review/delete/asset";
        return this.http.post(url, body, options).map((res: Response) => { return res.json() }).catch(this.handleError);
    }

    public deleteUserReviews(user: User): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify({ 'userId': user.id });
        var url = Config.system_backend_url + "/review/delete/user";
        return this.http.post(url, body, options).map((res: Response) => { return res.json() }).catch(this.handleError);
    }

    /*********  Error *********/

    private handleError(error: any) {
        return Observable.throw({ "ReviewService": { "Code": error.status, "Message": error.message } });
    }
}
