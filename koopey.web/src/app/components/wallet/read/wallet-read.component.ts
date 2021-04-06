//Angular, Material, Libraries
import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
//Services
import { AlertService } from "../../../services/alert.service";
import { BitcoinService } from "../../../services/bitcoin.service";
import { EthereumService } from "../../../services/ethereum.service";
import { WalletService } from "../../../services/wallet.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../../../services/user.service";
//Helpers
import { CurrencyHelper } from "../../../helpers/CurrencyHelper";
//Objects
import { Bitcoin } from "../../../models/bitcoin";
import { Config } from "../../../config/settings";
import { Ethereum } from "../../../models/ethereum";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";

@Component({
    selector: "wallet-read-component",
    templateUrl: "../../views/wallet-read.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class WalletReadComponent implements OnInit {

    private LOG_HEADER: string = 'WALLET:READ';
    private bitcoin: Bitcoin = new Bitcoin();
    private ethereum: Ethereum = new Ethereum();
    private wallet: Wallet = new Wallet();


    constructor(
        private alertService: AlertService,
        private bitcoinService: BitcoinService,
        private ethereumService: EthereumService,
        private walletService: WalletService,
        private userService: UserService,
        private router: Router,
        private sanitizer: DomSanitizer,
        private translateService: TranslateService
    ) { }

    ngOnInit() {
        this.walletService.getWallet().subscribe(
            wallet => {
                this.wallet = wallet;
            },
            error => { this.alertService.error(<any>error) },
            () => {
                if (this.wallet.currency == "tok") {
                    this.wallet.name = window.location.protocol + "//" + window.location.host + "/user/read/one/" + localStorage.getItem("id");
                }
            }
        );
    }

    private getBitcoinBalance() {
        if (this.wallet) {
            this.bitcoin.account = this.wallet.name;
            this.bitcoinService.readBalance(this.bitcoin).subscribe(
                bitcoin => { this.bitcoin = bitcoin; },
                error => { this.alertService.error(<any>error) },
                () => { console.log("getBitcoinBalance success"); console.log(this.bitcoin) }
            );
        }
    }

    public getCurrencyText(wallet: Wallet): string {
        if (wallet && wallet.currency) {
            return CurrencyHelper.convertCurrencyCodeToSymbol(wallet.currency);
        }
    }

    private getEthereumBalance() {
        if (this.wallet) {
            this.ethereum.account = this.wallet.name;
            this.ethereumService.readBalance(this.ethereum).subscribe(
                ethereum => { this.ethereum = ethereum; },
                error => { this.alertService.error(<any>error) },
                () => { console.log("getEthereumBalance success"); console.log(this.ethereum) }
            );
        }
    }

    private hasTransactions(): boolean {
        return Config.business_model_transactions;
    }

}
