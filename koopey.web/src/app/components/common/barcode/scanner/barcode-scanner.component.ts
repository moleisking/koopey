import { Component, CUSTOM_ELEMENTS_SCHEMA, EventEmitter, Input, Output } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
//import { BarcodeFormat } from "@zxing/library/cjs";
import { AlertService } from "../../../../services/alert.service";
import { BarcodeService } from "../../../../services/barcode.service";
//import { ZXingScannerModule } from "@zxing/ngx-scanner";
//Test
//https://github.com/benjamind/delarre.docpad/blob/master/src/documents/posts/installing-node-canvas-for-windows.html.mat

//import { NgQRCodeReaderModule } from 'ng2-qrcode-reader';

@Component({
  imports: [ ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  selector: "barcode-scanner-component",
  standalone: true,
  styleUrls: ["barcode-scanner.css"],
  templateUrl: "barcode-scanner.html"
})
export class BarcodeScannerComponent {
  @Input() complete: boolean = false;
  @Output() updateBarcode: EventEmitter<String> = new EventEmitter<String>();

 /*public allowedFormats = [
    BarcodeFormat.QR_CODE,
    BarcodeFormat.EAN_13,
    BarcodeFormat.CODE_128,
    BarcodeFormat.DATA_MATRIX ,
  ];*/

  private cameraActive = false;
  private camera: any = null;
  private devices = new Array<any>();
  private result = "";

  constructor(
    private alertService: AlertService,
    private barcodeService: BarcodeService,
    protected router: Router
  ) { }

  public handleQrCodeResult(result: string) {
    this.result = result;
    this.barcodeService.setBarcode(result);
    this.updateBarcode.emit(result);
    this.gotoTransactionUpdate();
  }

  public handleCameraConnection(cameras: Array<any>) {
    this.devices = cameras;
    if (cameras && cameras.length > 0) {
      this.camera = cameras[0];
      this.cameraActive = true;
    }
  }

  public isEmpty() {
    if (this.result.length > 0) {
      return false;
    } else {
      return true;
    }
  }

  private gotoTransactionUpdate() {
    this.complete = true;
    this.router.navigate(["/transaction/update"]);
  }
}
