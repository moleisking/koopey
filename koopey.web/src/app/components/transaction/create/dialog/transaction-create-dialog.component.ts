import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { MatDialogRef } from "@angular/material/dialog";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { UUID } from "angular2-uuid";
import { AlertService } from "../../../../services/alert.service";
import { AuthenticationService } from "../../../../services/authentication.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../../services/click.service";
import { AssetService } from "../../../../services/asset.service";
import { TransactionService } from "../../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../../services/user.service";
import { WalletService } from "../../../../services/wallet.service";
import { TransactionCreateComponent } from "../transaction-create.component";
import { Transaction } from "../../../../models/transaction";

@Component({
  selector: "transaction-create-dialog",
  templateUrl: "transaction-create-dialog.html",
})
export class TransactionCreateDialogComponent extends TransactionCreateComponent
  implements OnInit {
  constructor(
    private dialogRef: MatDialogRef<TransactionCreateDialogComponent>,
    protected alertService: AlertService,
    protected authenticateService: AuthenticationService,
    protected clickService: ClickService,
    protected datePickerService: MatDatepickerModule,
    protected router: Router,
    protected transactionService: TransactionService,
    protected translateService: TranslateService,
    protected assetService: AssetService,
    protected userService: UserService,
    protected walletService: WalletService
  ) {
    super(
      alertService,
      authenticateService,
      clickService,
      router,
      transactionService,
      translateService,
      assetService,
      userService,
      walletService
    );
  }

  ngOnInit() {
    this.redirect = false;
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
