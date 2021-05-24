import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "src/environments/environment";

@Injectable()
export class BarcodeService {
  private static LOG_HEADER: string = "BARCODE:SERVICE:";
  private barcode = new ReplaySubject<string>();

  constructor(private http: Http, private translateService: TranslateService) {}

  public getBarcode(): Observable<string> {
    return this.barcode.asObservable();
  }

  public setBarcode(barcode: string): void {
    this.barcode.next(barcode);
  }

  public isEmpty(): boolean {
    if (this.barcode.asObservable()) {
      return true;
    } else {
      return false;
    }
  }

  public clear(): void {
    this.barcode.next("");
  }

  private handleError(error: any) {
    return Observable.throw({
      BarcodeService: { Code: error.status, Message: error.message },
    });
  }
}
