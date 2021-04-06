// Core modules
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { MaterialModule, MdInputModule, MdDatepickerModule, MdDatepickerIntl, MdDialog, MdDialogRef } from "@angular/material"
import { UUID } from 'angular2-uuid';
//Services
import { AlertService } from "../services/alert.service";
import { AuthService } from "../services/auth.service";
import { BitcoinService } from "../services/bitcoin.service";
import { ClickService, CurrentComponent, ActionIcon } from "../services/click.service";
import { EthereumService } from "../services/ethereum.service";
import { AssetService } from "../services/asset.service";
import { TransactionService } from "../services/transaction.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../services/user.service";
import { WalletService } from "../services/wallet.service";
//Components
import { TransactionCreateComponent } from "../components/transaction-create.component";
//Objects
import { Transaction } from "../models/transaction";
import 'hammerjs';

@Component({
    selector: 'transaction-create-dialog',
    templateUrl: '../../views/transaction-create-dialog.html',
})

export class TransactionCreateDialogComponent extends TransactionCreateComponent implements OnInit {

    private LOG_HEADER: string = 'TRANSACTION:CREATE:DIALOG';

    constructor(
        private dialogRef: MdDialogRef<TransactionCreateDialogComponent>,
        protected alertService: AlertService,
        protected authenticateService: AuthService,
        protected bitcoinService: BitcoinService,
        protected ethereumService: EthereumService,
        protected clickService: ClickService,
        protected datePickerService: MdDatepickerIntl,
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
            bitcoinService,
            ethereumService,
            clickService,
            datePickerService,
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

    private cancel() {
        this.dialogRef.close(null);
    }
}