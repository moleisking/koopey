import { Component } from "@angular/core";
import { MatDialogRef } from "@angular/material/dialog";
import { User } from "../../models/user";

@Component({
  selector: "mobile-dialog",
  templateUrl: "mobile-dialog.html",
})
export class MobileDialogComponent {
  private mobile: string = "";

  constructor(public dialogRef: MatDialogRef<MobileDialogComponent>) {
    // this.mobile = user.mobile;
  }

  public setMobile(mobile: string) {
    this.mobile = mobile;
  }
}
