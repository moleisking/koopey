import { Asset } from "../models/asset";
import { BaseService } from "./base.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { Search } from "../models/search";
import { TranslateService } from "@ngx-translate/core";
import { User } from "../models/user";

@Injectable()
export class AssetService extends BaseService {
  public asset = new ReplaySubject<Asset>();
  public assets = new ReplaySubject<Array<Asset>>();
 
  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

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
    let url = this.baseUrl() + "/asset/read/count/" + asset.id;
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public countUserAssets(user: User): Observable<Number> {
    let url = this.baseUrl() + "/asset/read/count/user/assets" + user.id;
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public create(asset: Asset): Observable<String> {
    let url = this.baseUrl() + "/asset/create";
    return this.httpClient.post<String>(url, asset, this.privateHeader());
  }

  public delete(asset: Asset): Observable<void> {
    let url = this.baseUrl() + "/asset/delete";
    return this.httpClient.post<void>(url, asset, this.privateHeader());
  }

  public read(asset: Asset): Observable<Asset> {
    let url = this.baseUrl() + "/asset/read/one/" + asset.id;
    return this.httpClient.get<Asset>(url, this.privateHeader());
  }

  public search(search: Search): Observable<Array<Asset>> {
    let url = this.baseUrl() + "/asset/search";
    return this.httpClient.post<Array<Asset>>(
      url,
      search,
      this.privateHeader()
    );
  }

  public searchByBuyer(): Observable<Array<Asset>> {
    let url = this.baseUrl() + "/asset/search/by/buyer";
    return this.httpClient.get<Array<Asset>>(url, this.privateHeader());
  }

  public searchByBuyerOrSeller(): Observable<Array<Asset>> {
    let url = this.baseUrl() + "/asset/search/by/buyer/or/seller";
    return this.httpClient.get<Array<Asset>>(url, this.privateHeader());
  }

  public searchBySeller(): Observable<Array<Asset>> {
    let url = this.baseUrl() + "/asset/search/by/seller";
    return this.httpClient.get<Array<Asset>>(url, this.privateHeader());
  }

  public update(asset: Asset): Observable<void> {
    let url = this.baseUrl() + "/asset/update";
    return this.httpClient.post<void>(url, asset, this.privateHeader());
  }
}
