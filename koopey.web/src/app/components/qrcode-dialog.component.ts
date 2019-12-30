import { Component, ElementRef, Input, ViewChild } from "@angular/core";
import { MaterialModule, MdInputModule, MdTextareaAutosize, MdDialog, MdDialogRef } from "@angular/material";
import { AlertService } from "../services/alert.service";

@Component({
    selector: 'qrcode-dialog',
    templateUrl: '../../views/qrcode-dialog.html'
})

export class QRCodeDialogComponent {

    protected text: string = '';

    constructor(
        private alertService: AlertService,
        public dialogRef: MdDialogRef<QRCodeDialogComponent>) {
    }

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