//Angular, Material, Libraries
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Advert } from "../models/advert";
import { Alert } from "../models/alert";
import { Asset } from "../models/asset";
import { Config } from "../config/settings";
import { File } from "../models/file";
import { Image } from "../models/image";
import { Location } from "../models/location";
import { Review } from "../models/review";
import { Search } from "../models/search";
import { Tag } from "../models/tag";

@Injectable()
export class AssetService {

  private static LOG_HEADER: string = 'PRODUCT:SERVICE:';
  public asset = new ReplaySubject<Asset>()
  public assets = new ReplaySubject<Array<Asset>>()

  constructor(
    private http: Http,
    private translateService: TranslateService
  ) { }

  /*********  Object *********/

  public getAsset(): Observable<Asset> {
    return this.asset.asObservable();
  }

  public setAsset(asset: Asset): void {
    this.asset.next(asset);
  }

  public getAssets(): Observable<Array<Asset>> {
    return this.assets.asObservable();
  }

  public setAssets(assets: Array<Asset>) {
    this.assets.next(assets);
  }

  /*********  Create *********/

  public create(asset: Asset): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(asset);
    var url = Config.system_backend_url + "/asset/create";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

 /* public createFee(asset: Asset, fee: Fee): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    fee.assetId = asset.id;
    let body = JSON.stringify({ 'asset': Asset.simplify(asset), 'fee': fee });
    var url = Config.system_backend_url + "/asset/create/fee";
    return this.http.post(url, body, options).catch(this.handleError);
  }*/

  public createLocation(asset: Asset, location: Location): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ 'asset': Asset.simplify(asset), 'location': location });
    var url = Config.system_backend_url + "/asset/create/location";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public createReview(asset: Asset, review: Review): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(review);
    var url = Config.system_backend_url + "/asset/create/review";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Read *********/  

  public readAsset(id: string): Observable<Asset> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ id: id });
    var url = Config.system_backend_url + "/asset/read/one/";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().asset }).catch(this.handleError);
  } 

  public readAssets(search: Search): Observable<Array<Asset>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Content-Type", "application/json");
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(search);
    var url = Config.system_backend_url + "/asset/read/many";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().assets }).catch(this.handleError);
  }  

  public readCount(): Observable<Number> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    var url = Config.system_backend_url + "/asset/read/count/";
    return this.http.get(url, options).map((res: Response) => { return res.json().assets.count }).catch(this.handleError);
  }

  public readFile(id: string): Observable<any> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ id: id });
    var url = Config.system_backend_url + "/asset/read/file/";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().asset }).catch(this.handleError);
  }

  public readMyAssets(): Observable<Array<Asset>> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    var url = Config.system_backend_url + "/asset/read/many/mine";
    return this.http.get(url, options).map((res: Response) => { return res.json().assets }).catch(this.handleError);
  }  

  /*********  Update *********/

  public update(asset: Asset): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify(asset);
    var url = Config.system_backend_url + "/asset/update";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateAdvert(asset: Asset, advert: Advert): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'asset':  Asset.simplify(asset), 'advert': advert });
    var url = Config.system_backend_url + "/asset/update/advert";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }  

  /*public updateFee(asset: Asset, fee: Fee): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'asset': Asset.simplify(asset), 'fee': fee });
    var url = Config.system_backend_url + "/asset/update/fee";
    return this.http.post(url, body, options).catch(this.handleError);
  }*/

  public updateFile(asset: Asset, file: File): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'asset': Asset.simplify(asset), 'file': file });
    var url = Config.system_backend_url + "/asset/update/file";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateImage(asset: Asset, image: Image): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'asset': Asset.simplify(asset), 'image': image });
    var url = Config.system_backend_url + "/asset/update/image";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateLocation(asset: Asset, location: Location): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ 'headers': headers });
    let body = JSON.stringify({ 'asset': Asset.simplify(asset), 'location': location });
    var url = Config.system_backend_url + "/asset/update/location";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  public updateQuantity(asset: Asset, value: Number): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ 'asset':  Asset.simplify(asset), 'value': value });
    var url = Config.system_backend_url + "/asset/update/quantity";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Delete *********/

  public delete(asset: Asset): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(asset);
    var url = Config.system_backend_url + "/asset/delete";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*public deleteFee(asset: Asset, fee: Fee): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ 'asset': Asset.simplify(asset), 'fee': fee });
    var url = Config.system_backend_url + "/asset/delete/fee";
    return this.http.post(url, body, options).catch(this.handleError);
  }*/

  public deleteLocation(assetId: String, location: Location): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(location);
    var url = Config.system_backend_url + "/asset/delete/location";
    return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
  }

  /*********  Search *********/

  

  /*********  Error *********/

  private handleError(error: any) {
    return Observable.throw({ "AssetService": { "Code": error.status, "Message": error.message } });
  }
}
