import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { MatDialogRef } from "@angular/material/dialog";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { AssetService } from "../../../services/asset.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { WalletService } from "../../../services/wallet.service";
import { TransactionEditComponent } from "../edit/transaction-edit.component";
import { Transaction } from "../../../models/transaction";
import { DomSanitizer } from "@angular/platform-browser";
import { FormBuilder } from "@angular/forms";

@Component({
  selector: "transaction-dialog",
  templateUrl: "transaction-dialog.html",
})
export class TransactionDialogComponent extends TransactionEditComponent
  implements OnInit {
  constructor(
    private dialogRef: MatDialogRef<TransactionDialogComponent>,
    protected alertService: AlertService,
    protected authenticateService: AuthenticationService,
    protected formBuilder: FormBuilder,
    protected datePickerService: MatDatepickerModule,
    protected router: Router,
    protected transactionService: TransactionService,
    public sanitizer: DomSanitizer,
    protected assetService: AssetService,
    protected userService: UserService,
    protected walletService: WalletService
  ) {
    super(
      alertService,
      authenticateService,
      formBuilder,
      router,
      transactionService,     
      sanitizer,
      assetService,
      userService,
      walletService
    );
  }

  ngOnInit() {
    // this.redirect = false;
  }

  public transactionComplete(complete: boolean): void {
    console.log("transactionComplete");
    console.log(complete);
  }

  public setTransaction(transaction: Transaction) {
    this.transaction = transaction;
  }

  public cancel() {
    this.dialogRef.close(null);
  }
}
