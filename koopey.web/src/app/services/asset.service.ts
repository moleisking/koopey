import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "ng2-translate";
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
  private static LOG_HEADER: string = "PRODUCT:SERVICE:";
  public asset = new ReplaySubject<Asset>();
  public assets = new ReplaySubject<Array<Asset>>();

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

  public count(asset: Asset): Observable<Number> {
    var url = Config.system_backend_url + "/asset/read/count/" + asset.id;
    return this.httpClient.get<Number>(url, this.httpHeader);
  }

  public create(asset: Asset): Observable<String> {
    let url = Config.system_backend_url + "/asset/create";
    return this.httpClient.put<String>(url, asset, this.httpHeader);
  }

  public delete(asset: Asset): Observable<String> {
    let url = Config.system_backend_url + "/asset/delete";
    return this.httpClient.post<String>(url, asset, this.httpHeader);
  }

  public readAsset(asset: Asset): Observable<Asset> {
    let url = Config.system_backend_url + "/asset/read/one/" + asset.id;
    return this.httpClient.get<Asset>(url, this.httpHeader);
  }

  public readAssets(search: Search): Observable<Array<Asset>> {
    let url = Config.system_backend_url + "/asset/read/many";
    return this.httpClient.post<Array<Asset>>(url, search, this.httpHeader);
  }

  public readUserAssets(): Observable<Array<Asset>> {
    var url = Config.system_backend_url + "/asset/read/many/mine";
    return this.httpClient.get<Array<Asset>>(url, this.httpHeader);
  }

  public update(asset: Asset): Observable<String> {
    var url = Config.system_backend_url + "/asset/update";
    return this.httpClient.post<String>(url, asset, this.httpHeader);
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

  /*public createLocation(asset: Asset, location: Location): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({
      asset: Asset.simplify(asset),
      location: location,
    });
    var url = Config.system_backend_url + "/asset/create/location";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }*/

  /* public createReview(asset: Asset, review: Review): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(review);
    var url = Config.system_backend_url + "/asset/create/review";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }*/

  /*********  Read *********/

  /*public readFile(id: string): Observable<any> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ id: id });
    var url = Config.system_backend_url + "/asset/read/file/";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().asset;
      })
      .catch(this.handleError);
  }*/

  /* public updateAdvert(asset: Asset, advert: Advert): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ asset: Asset.simplify(asset), advert: advert });
    var url = Config.system_backend_url + "/asset/update/advert";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }*/

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

  /* public updateFile(asset: Asset, file: File): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ asset: Asset.simplify(asset), file: file });
    var url = Config.system_backend_url + "/asset/update/file";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public updateImage(asset: Asset, image: Image): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ asset: Asset.simplify(asset), image: image });
    var url = Config.system_backend_url + "/asset/update/image";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public updateLocation(asset: Asset, location: Location): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({
      asset: Asset.simplify(asset),
      location: location,
    });
    var url = Config.system_backend_url + "/asset/update/location";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }

  public updateQuantity(asset: Asset, value: Number): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify({ asset: Asset.simplify(asset), value: value });
    var url = Config.system_backend_url + "/asset/update/quantity";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }*/

  /*********  Delete *********/

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

  /* public deleteLocation(
    assetId: String,
    location: Location
  ): Observable<Alert> {
    let headers = new Headers();
    headers.append("Authorization", "JWT " + localStorage.getItem("token"));
    headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.append("Content-Type", "application/json");
    let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(location);
    var url = Config.system_backend_url + "/asset/delete/location";
    return this.http
      .post(url, body, options)
      .map((res: Response) => {
        return res.json().alert;
      })
      .catch(this.handleError);
  }*/
}
