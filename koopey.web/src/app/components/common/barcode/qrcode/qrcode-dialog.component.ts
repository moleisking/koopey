import { ChangeDetectionStrategy, Component, ElementRef, Input, ViewChild } from "@angular/core";
import { MatDialogRef } from "@angular/material/dialog";
import { AlertService } from "../../../../services/alert.service";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "qrcode-dialog",
    standalone: false,
  templateUrl: "qrcode-dialog.html",
})
export class QRCodeDialogComponent {
  public text: string = "";

  constructor(
    private alertService: AlertService,
    public dialogRef: MatDialogRef<QRCodeDialogComponent>
  ) {}

  public hasQRCodeText() {
    if (this.text && this.text.length > 0) {
      return true;
    } else {
      return false;
    }
  }

  public setQRCodeText(text: string) {
    this.text = text;
  }
}
