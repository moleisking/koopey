//Angular, Material, Libraries
import { Component, ElementRef, Input, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import {
    MaterialModule, MdIconModule, MdIconRegistry, MdInputModule,
    MdTextareaAutosize, MdDialog, MdDialogRef
} from "@angular/material"
import { Router } from "@angular/router";
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../services/alert.service";
import { BitcoinService } from "../services/bitcoin.service";
import { EthereumService } from "../services/ethereum.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../services/user.service";
import { WalletService } from "../services/wallet.service";
//Components
import { WalletDialogComponent } from "./wallet-dialog.component";
//Helpers
import { CurrencyHelper } from "../helpers/CurrencyHelper";
//Objects
import { Bitcoin } from "../models/bitcoin";
import { Config } from "../config/settings";
import { Ethereum } from "../models/ethereum";
import { Transaction } from "../models/transaction";
import { User } from "../models/user";
import { Wallet } from "../models/wallet";

@Component({
    selector: "wallet-list-component",
    templateUrl: "../../views/wallet-list.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class WalletListComponent implements OnInit, OnDestroy {
    //Controls    
    private walletSubscription: Subscription;
    //Objects      
    private bitcoin: Bitcoin = new Bitcoin();
    private ethereum: Ethereum = new Ethereum();
    private bitcoinWallet: Wallet = new Wallet();
    private ethereumWallet: Wallet = new Wallet();
    private wallets: Array<Wallet> = new Array<Wallet>();
    //Strings   
    private LOG_HEADER: string = "WalletListComponent"
    //Numbers
    private columns: number = 1;
    private screenWidth: number = window.innerWidth;
    
    constructor(
        private alertService: AlertService,
        private bitcoinService: BitcoinService,
        private ethereumService: EthereumService,
        private router: Router,
        private sanitizer: DomSanitizer,
        private translateService: TranslateService,        
        private walletService: WalletService,
        public walletDialog: MdDialog
    ) { }

    ngOnInit() {
        this.walletSubscription = this.walletService.readMyWallets().subscribe(
            (wallets) => {console.log(wallets);
                this.wallets = wallets;
                this.bitcoinWallet = Wallet.readBitcoin(wallets);
                this.ethereumWallet = Wallet.readEthereum(wallets);                
            },
            (error) => { console.log(error); },
            () => {
                this.getBitcoinBalance();
                this.getEthereumBalance();
            });
    }

    ngAfterContentInit() {
        this.onScreenSizeChange(null);
    }

    ngOnDestroy() {
        if (this.walletSubscription) {
            this.walletSubscription.unsubscribe();
        }
    }

    private openWalletDialog(wallet: Wallet) {
        let dialogRef = this.walletDialog.open(WalletDialogComponent, {});
        dialogRef.componentInstance.setWallet(wallet);
    }

    private onScreenSizeChange(event: any) {
        this.screenWidth = window.innerWidth;
        if (this.screenWidth <= 512) {
            this.columns = 1;
        } else if ((this.screenWidth > 512) && (this.screenWidth <= 1024)) {
            this.columns = 2;
        } else if ((this.screenWidth > 1024) && (this.screenWidth <= 2048)) {
            this.columns = 3;
        } else if ((this.screenWidth > 2048) && (this.screenWidth <= 4096)) {
            this.columns = 4;
        }
    }

    private isImageEmpty(wallet: Wallet) {
        if (!wallet && !wallet.name && wallet.name.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    private getBitcoinBalance() {
        if (this.bitcoinWallet) {
            this.bitcoin.address = this.bitcoinWallet.name;
            this.bitcoinService.readBalance(this.bitcoin).subscribe(
                (bitcoin) => { this.bitcoin = bitcoin; },
                (error) => { this.alertService.error(<any>error) },
                () => { console.log("getBitcoinBalance success"); console.log(this.bitcoin) }
            );
        }
    }

    private getEthereumBalance() {
        if (this.ethereumWallet) {
            this.ethereum.account = this.ethereumWallet.name;
            this.ethereumService.readBalance(this.ethereum).subscribe(
                (ethereum) => { this.ethereum = ethereum; },
                (error) => { this.alertService.error(<any>error) },
                () => { console.log("getEthereumBalance success"); console.log(this.ethereum) }
            );
        }
    }

    public getCurrencyText(wallet: Wallet): string {
        if (wallet && wallet.currency) {
            return CurrencyHelper.convertCurrencyCodeToSymbol(wallet.currency);
        }
    }

    private gotoWallet(wallet: Wallet) {
        this.walletService.setWallet(wallet);
        this.router.navigate(["/wallet/read/one"])
    }

    private showNoResults(): boolean {
        if (!this.wallets || this.wallets.length == 0) {
            return true;
        } else {
            return false;
        }
    }
}
