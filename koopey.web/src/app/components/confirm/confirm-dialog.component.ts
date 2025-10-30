import { Component, OnInit, ElementRef, ViewChild, ChangeDetectionStrategy } from "@angular/core";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "confirm-dialog",
    standalone: false,
  templateUrl: "confirm-dialog.html",
})
export class ConfirmDialogComponent {
  constructor(public dialogRef: MatDialogRef<ConfirmDialogComponent>) {}
}
