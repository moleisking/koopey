import { Component, Input, OnInit, Output } from "@angular/core";
import { Wallet } from "../../../models/wallet";
import { Transaction } from "../../../models/transaction";
import { MatDialogRef } from "@angular/material/dialog";
import { ModelHelper } from "../../../helpers/ModelHelper";

@Component({
  selector: "wallet-dialog",
    standalone: false,
  templateUrl: "wallet-dialog.html",
})
export class WalletDialogComponent implements OnInit {
  public wallet: Wallet = new Wallet();

  constructor(public dialogRef: MatDialogRef<WalletDialogComponent>) {}

  ngOnInit() {}

  public setWallet(wallet: Wallet) {
    if (!ModelHelper.isEmpty(wallet)) {
      this.wallet = wallet;
    }
  }

  public cancel() {
    this.dialogRef.close(null);
  }
}
