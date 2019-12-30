//Core
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Config } from "../config/settings";

@Injectable()
export class BarcodeService {

  private static LOG_HEADER: string = 'BARCODE:SERVICE:';
  private barcode = new ReplaySubject<string>();

  constructor(
    private http: Http,
    private translateService: TranslateService
  ) { }

  /*********  Object *********/

  public getBarcode(): Observable<string> {
    return this.barcode.asObservable();
  }

  public setBarcode(barcode: string): void {
    this.barcode.next(barcode);
  } 

  public isEmpty(): boolean {
    if (this.barcode.asObservable().isEmpty()) {
      return true;
    } else { 
      return false;
    }
  } 

  public clear(): void {
    this.barcode.next("");
  } 

  private handleError(error: any) {
    return Observable.throw({ "BarcodeService": { "Code": error.status, "Message": error.message } });
  }
}
