import { ChangeDetectionStrategy, Component, inject, OnInit } from "@angular/core";
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
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: "transaction-dialog",
  standalone: false,
  templateUrl: "transaction-dialog.html",
})
export class TransactionDialogComponent extends TransactionEditComponent
  implements OnInit {

  private dialogRef = inject(MatDialogRef<TransactionDialogComponent>);
  protected alertService = inject(AlertService);
  protected authenticateService = inject(AuthenticationService);
  protected formBuilder = inject(FormBuilder);
  protected datePickerService = inject(MatDatepickerModule);
  protected router = inject(Router);
  protected transactionService = inject(TransactionService);
  public sanitizer = inject(DomSanitizer);
  protected assetService = inject(AssetService);
  protected userService = inject(UserService);
  protected walletService = inject(WalletService);
  
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
