import { Component, Input, OnInit, Output } from "@angular/core";

import { CurrencyHelper } from "../../../helpers/CurrencyHelper";
import { Wallet } from "../../../models/wallet";
import { Transaction } from "../../../models/transaction";
import "hammerjs";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "wallet-dialog",
  templateUrl: "../../views/wallet-dialog.html",
})
export class WalletDialogComponent implements OnInit {
  private LOG_HEADER: string = "WALLET:DIALOG";
  private wallet: Wallet = new Wallet();

  constructor(public dialogRef: MatDialogRef<WalletDialogComponent>) {}

  ngOnInit() {}

  public setWallet(wallet: Wallet) {
    if (!Wallet.isEmpty(wallet)) {
      this.wallet = wallet;
    }
  }

  private getCurrencySymbol(currency: string): string {
    return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
  }

  private cancel() {
    this.dialogRef.close(null);
  }
}
