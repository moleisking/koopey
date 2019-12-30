//Angular, Material, Libraries
import { Component, Input, OnInit, Output } from "@angular/core";
import { MaterialModule, MdInputModule, MdDialog, MdDialogRef } from "@angular/material"
//Helpers
import { CurrencyHelper } from "../helpers/CurrencyHelper";
//Objects
import { Wallet } from "../models/wallet";
import { Transaction } from "../models/transaction";
import 'hammerjs';

@Component({
    selector: 'wallet-dialog',
    templateUrl: '../../views/wallet-dialog.html',
})

export class WalletDialogComponent implements OnInit {

    private LOG_HEADER: string = 'WALLET:DIALOG';
    private wallet: Wallet = new Wallet();

    constructor(public dialogRef: MdDialogRef<WalletDialogComponent>) { }

    ngOnInit() { }

    public setWallet(wallet: Wallet) {
        if (!Wallet.isEmpty(wallet)) {
            this.wallet = wallet;
        }
    }

    /*********  Functions *********/

    private getCurrencySymbol(currency: string): string {
        if (currency != null) {
            return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
        }
    }

    /*********  Actions *********/

    private cancel() {
        this.dialogRef.close(null);
    }
}