import { BaseService } from "./base.service";
import { Classification } from "../models/classification";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class ClassificationService extends BaseService {
  public classification = new ReplaySubject<Classification>();
  public classifications = new ReplaySubject<Array<Classification>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getClassification(): Observable<Classification> {
    return this.classification.asObservable();
  }

  public setClassification(cometition: Classification) {
    this.classification.next(cometition);
  }

  public getClassifications(): Observable<Array<Classification>> {
    return this.classifications.asObservable();
  }

  public setClassifications(classifications: Array<Classification>) {
    this.classifications.next(classifications);
  }

  public create(cometition: Classification): Observable<String> {
    let url = this.baseUrl() + "/classification/create";
    return this.httpClient.put<String>(url, cometition, this.privateHeader());
  }

  public count(): Observable<Number> {
    let url = this.baseUrl() + "/classification/read/count";
    return this.httpClient.get<Number>(url, this.privateHeader());
  }

  public delete(classification: Classification): Observable<void> {
    let url = this.baseUrl() + "/classification/delete";
    return this.httpClient.post<void>(url, classification, this.privateHeader());
  }

  public read(classification: Classification): Observable<Array<Classification>> {
    let url = this.baseUrl() + "/classification/read/many";
    return this.httpClient.get<Array<Classification>>(url, this.privateHeader());
  }

  public update(classification: Classification): Observable<void> {
    let url = this.baseUrl() + "/classification/update";
    return this.httpClient.post<void>(url, classification, this.privateHeader());
  }
}