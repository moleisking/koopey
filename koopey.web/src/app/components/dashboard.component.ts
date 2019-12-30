//Angular, Material, Libraries
import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
//Services
import { AlertService } from "../services/alert.service";
import { AuthService } from "../services/auth.service";
import { BitcoinService } from "../services/bitcoin.service";
import { EthereumService } from "../services/ethereum.service";
import { MessageService } from "../services/message.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../services/user.service";
//Objects
import { Alert } from "../models/alert";
import { Bitcoin } from "../models/bitcoin";
import { Config } from "../config/settings";
import { Ethereum } from "../models/ethereum";
//import { Image } from "../models/image";
import { User } from "../models/user";
import { Wallet } from "../models/wallet";

@Component({
    selector: "dashboard-component",
    templateUrl: "../../views/dashboard.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class DashboardComponent implements OnInit {

    private LOG_HEADER: string = 'DASHBOARD:';
    private authUser: User = new User();
    private bitcoin: Bitcoin = new Bitcoin();
    private ethereum: Ethereum = new Ethereum();
    private bitcoinWallet: Wallet = new Wallet();
    private ethereumWallet: Wallet = new Wallet();
    private ibanWallet: Wallet = new Wallet();
    private tokoWallet: Wallet = new Wallet();
    private messageUnsentCount: Number = 0;
    private messageUndeliveredCount: Number = 0;

    constructor(
        private alertService: AlertService,
        private authenticateService: AuthService,
        private bitcoinService: BitcoinService,
        private ethereumService: EthereumService,
        private messageService: MessageService,
        private userService: UserService,
        private router: Router,
        private sanitizer: DomSanitizer,
        private translateService: TranslateService
    ) {
        this.getMyUser();
    }

    ngOnInit() {
        try {
            //this.authUser = this.userService.getMyUserLocal();            
        } catch (error) {
            console.log("No current user found in dashboard" + error);
        }
    }

    private getMyUser() {
        this.userService.readMyUser().subscribe(
            (user) => {
                this.authUser = user;
                //this.authUser.avatar = this.shrinkImage(user.images[0].uri, 256,256);
                this.bitcoinWallet = Wallet.readBitcoin(this.authUser.wallets);
                this.ethereumWallet = Wallet.readEthereum(this.authUser.wallets);
                this.tokoWallet = Wallet.readLocal(this.authUser.wallets);
                this.authenticateService.setUser(user);
            },
            (error) => { console.log(error);/*this.alertService.error(<any>error) */ },
            () => {
                this.getBitcoinBalance();
                this.getEthereumBalance();
                this.getUnread();
                this.getUnsent();
                if (!Config.system_production) { console.log(this.authUser); }
            }
        );
    }

    /*private toUpperCase(value: string): string {
        if (value) {
            return value.toUpperCase();
        }
    }*/

    private getUnread() {
        this.messageService.readMessagesUnsentCount().subscribe(
            (count) => {
                this.messageUnsentCount = count;
            },
            (error) => { this.alertService.error(<any>error) },
            () => { }
        );
    }

    private getUnsent() {
        this.messageService.readMessagesUndeliveredCount().subscribe(
            (count) => {
                this.messageUndeliveredCount = count;
            },
            (error) => { this.alertService.error(<any>error) },
            () => { }
        );
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

    private toggleTrack(event: any) {       
        if (event.checked == true) {           
            this.authUser.track = true;
        } else {           
            this.authUser.track = false;
        }
        
        this.userService.updateTrack(this.authUser).subscribe(
            (alert: Alert) => { console.log(alert); },
            (error) => { this.alertService.error(<any>error);  },
            () => { }
        );
    }  

    private hasTransactions(): boolean {
        return Config.business_model_transactions;
    }

}
