import { Component, OnInit, ElementRef, ViewChild } from "@angular/core";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "confirm-dialog",
  templateUrl: "confirm-dialog.html",
})
export class ConfirmDialogComponent {
  constructor(public dialogRef: MatDialogRef<ConfirmDialogComponent>) {}
}
