import {
    Component, ElementRef, EventEmitter, forwardRef,
    Input, OnChanges, OnInit, Output, ViewChild
} from '@angular/core';
import {
    MaterialModule, MdAutocompleteModule, MdCardModule, MdIconModule, MdIconRegistry, MdInputModule,
    MdTabsModule, MdTextareaAutosize, MdDialog, MdDialogRef, MdAutocompleteTrigger
} from "@angular/material"
//Components
import { WalletDialogComponent } from "./dialog/wallet-dialog.component";
//Services
import { AlertService } from "../../services/alert.service";
import { UserService } from "../../services/user.service";
import { WalletService } from "../../services/wallet.service";
//Helpers
import { CurrencyHelper } from '../../helpers/CurrencyHelper';
//Objects
import { Alert } from "../../models/alert";
import { Bitcoin } from "../../models/bitcoin";
import { Config } from "../../config/settings";
import { Ethereum } from "../../models/ethereum";
//import { Fee } from "../models/fee";
import { Image } from "../../models/image";
import { Location } from "../../models/location";
import { Tag } from "../../models/tag";
import { User } from "../../models/user";
import { Wallet } from "../../models/wallet";
//Javascript
import 'hammerjs';

@Component({
    selector: "wallet-control-component",
    templateUrl: "../../views/wallet-control.html"
})

export class WalletControlComponent implements OnInit {

    private LOG_HEADER: string = 'WALLET:CONTROL';
   
    @Input() wallets: Array<Wallet> = new Array<Wallet>();   

    constructor(
        private alertService: AlertService,
        public walletDialog: MdDialog,
        private userService: UserService,
        private walletService: WalletService
    ) { }

    ngOnInit() { }

    private convertCurrencyCodeToSymbol(currency: string) {
        return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
    }  

    private readWalletDialog(wallet: Wallet) {
        let dialogRef = this.walletDialog.open(WalletDialogComponent, {
            height: '320px',
            width: '90vw',
        });
        dialogRef.componentInstance.setWallet(wallet);  
    }   
}