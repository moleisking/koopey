import { Component, OnInit, ElementRef, ViewChild } from "@angular/core";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "file-upload-dialog",
  templateUrl: "../../views/confirm-dialog.html",
})
export class ConfirmDialogComponent {
  constructor(public dialogRef: MatDialogRef<ConfirmDialogComponent>) {}
}
