//Angular, Material, Libraries
import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { MdDatepickerModule, MdDatepickerIntl } from '@angular/material';
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../services/alert.service";
import { ClickService, CurrentComponent, ActionIcon } from "../services/click.service";
import { BarcodeService } from "../services/barcode.service";
import { BitcoinService } from "../services/bitcoin.service";
import { EthereumService } from "../services/ethereum.service";
import { TransactionService } from "../services/transaction.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../services/user.service";
import { WalletService } from "../services/wallet.service";
//Helpers
import { CurrencyHelper } from "../helpers/CurrencyHelper";
import { DateHelper } from "../helpers/DateHelper";
import { TransactionHelper } from "../helpers/TransactionHelper";
//Objects
import { Alert } from "../models/alert";
import { Bitcoin } from "../models/bitcoin";
import { Ethereum } from "../models/ethereum";
import { Transaction } from "../models/transaction";
import { User } from "../models/user";
import { Wallet } from "../models/wallet";

@Component({
    selector: "transaction-update-component",
    templateUrl: "../../views/transaction-update.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class EventUpdateComponent implements OnInit, OnDestroy {

    protected LOG_HEADER: string = 'TRANSACTION:UPDATE';
    private barcodeSubscription: Subscription;
    private clickSubscription: Subscription;
    private transactionSubscription: Subscription;

    private bitcoin: Bitcoin = new Bitcoin();
    private ethereum: Ethereum = new Ethereum();
    protected transaction: Transaction = new Transaction();
    private startDate: Date = new Date();
    private endDate: Date = new Date();
    private barcode: string = "";
    private startTime: string = "08:00";
    private endTime: string = "09:00";
    private min: Date = new Date();
    private max: Date = new Date();
    private bitcoinWallet: Wallet = new Wallet();
    private ethereumWallet: Wallet = new Wallet();
    private tokoWallet: Wallet = new Wallet();
    private userWallets: Array<Wallet> = new Array<Wallet>();
    /*@ViewChild(MdDatepicker ) datepicker: MdDatepicker<Date>;*/


    constructor(
        protected alertService: AlertService,
        private barcodeService: BarcodeService,
        private bitcoinService: BitcoinService,
        private clickService: ClickService,
        private ethereumService: EthereumService,
        private datePickerService: MdDatepickerIntl,
        private route: ActivatedRoute,
        protected router: Router,
        protected transactionService: TransactionService,
        private translateService: TranslateService,
        private userService: UserService,
        private walletService: WalletService /*,private dateAdapter:DateAdapter<Date>*/
    ) {
        //dateAdapter.setLocale('de'); // DD.MM.YYYY
    }

    ngOnInit() {
        this.transactionSubscription = this.transactionService.getTransaction().subscribe(
            (transaction) => {
                this.transaction = transaction;
            },
            (error) => { console.log(error); },
            () => { });
        //Read barcode from previous view
        this.barcodeSubscription = this.barcodeService.getBarcode().subscribe(
            (barcode) => {
                this.barcode = barcode;
            },
            (error) => { console.log(error); },
            () => { });
    }

    ngAfterContentInit() {
        if (this.isQuote() || this.isInvoice()) {
            if (this.isAuthBuyer()) {
                this.clickService.createInstance(ActionIcon.PAYMENT, CurrentComponent.TransactionUpdateComponent);
                this.clickSubscription = this.clickService.getTransactionUpdateClick().subscribe(() => {
                    //Buyer completes transactions
                    this.update();
                });
            } else if (this.barcodeService.isEmpty() && (this.isAuthSeller())) {
                console.log("(this.barcodeService.isEmpty() && (this.transaction.seller.id == localStorage.getItem(id)))");
                this.clickService.createInstance(ActionIcon.CAMERA, CurrentComponent.TransactionUpdateComponent);
                this.clickSubscription = this.clickService.getTransactionUpdateClick().subscribe(() => {
                    //Seller completes transactions
                    this.router.navigate(["/barcode"])
                });
            } else if (!this.barcodeService.isEmpty() && (this.isAuthSeller())) {
                console.log("(!this.barcodeService.isEmpty() && (this.transaction.seller.id == localStorage.getItem(id)))");
                this.transaction.secret = this.barcode;
                this.barcodeService.clear();
                this.updateTransactionBySeller();
            }

        } else {
            this.router.navigate(["/transaction/read/list"])
        }
    }

    ngAfterViewInit() {
        this.max.setMonth(this.max.getMonth() + 6);
    }

    ngOnDestroy() {
        if (this.barcodeSubscription) {
            this.barcodeSubscription.unsubscribe();
        }
        if (this.clickSubscription) {
            this.clickService.destroyInstance();
            this.clickSubscription.unsubscribe();
        }
        if (this.transactionSubscription) {
            this.transactionSubscription.unsubscribe();
        }
    }

    private createTransactionAuditTrail() {
        //NOTE: update asset quantity if not necesarry as already doen in create               
        console.log("createTransactionTrail()");
        this.transactionService.create(this.transaction).subscribe(
            () => { },
            (error) => { this.alertService.error(<any>error) },
            () => {

                /*  if (this.useRoute) {
                      console.log("createTransaction() route");
                      this.router.navigate(["/transaction/read/list"])
                  } else {
                      console.log("createTransaction() no route");
                      this.alertService.success("INFO_COMPLETE");
                  }*/

            }
        );
    }

    private isAuthBuyer() {
        if (this.transaction && this.transaction.users && this.transaction.users.length >= 2) {
            for (var i = 0; i < this.transaction.users.length; i++) {
                if (User.isBuyer(this.transaction.users[i]) && (this.transaction.users[i].id == localStorage.getItem("id"))) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private isInvoice() {
        return Transaction.isInvoice(this.transaction);
    }

    private isQuote() {
        return Transaction.isQuote(this.transaction);
    }

    private isReceipt() {
        return Transaction.isReceipt(this.transaction);
    }

    private isAuthSeller() {
        if (this.transaction && this.transaction.users && this.transaction.users.length >= 2) {
            for (var i = 0; i < this.transaction.users.length; i++) {
                if (User.isSeller(this.transaction.users[i]) && this.transaction.users[i].id == localStorage.getItem("id")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private getCurrencySymbol(currency: string): string {
        if (currency != null) {
            return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
        }
    }

    private getDateTimeString(startTimeStamp: number): string {
        if (startTimeStamp) {
            return DateHelper.convertEpochToDateTimeString(this.transaction.startTimeStamp);
        } else {
            return DateHelper.convertEpochToDateTimeString(0);
        }
    }

    public getTransaction() {
        this.route.params.subscribe(p => {
            let id = p["id"];
            if (id) {
                this.transactionService.readTransaction(id).subscribe(
                    transaction => {
                        this.transaction = transaction;
                    },
                    error => { this.alertService.error(<any>error) },
                    () => { console.log("gettransaction success") }
                );
            } else {
                this.transactionSubscription = this.transactionService.getTransaction().subscribe(
                    (transaction) => {
                        this.transaction = transaction;
                    },
                    (error) => { console.log(error); },
                    () => { });
            }
        });
    }

    private getBitcoinBalance() {
        if (this.bitcoinWallet) {
            this.bitcoin.account = this.bitcoinWallet.name;
            this.bitcoinService.readBalance(this.bitcoin).subscribe(
                bitcoin => { this.bitcoin = bitcoin; },
                error => { this.alertService.error(<any>error) },
                () => { console.log("getBitcoinBalance success"); console.log(this.bitcoin) }
            );
        }
    }

    private getEthereumBalance() {
        if (this.ethereumWallet) {
            this.ethereum.account = this.ethereumWallet.name;
            this.ethereumService.readBalance(this.ethereum).subscribe(
                ethereum => { this.ethereum = ethereum; },
                error => { this.alertService.error(<any>error) },
                () => { console.log("getEthereumBalance success"); console.log(this.ethereum) }
            );
        }
    }

   /* private getWallets() {
        this.walletService.readWallets(this.).subscribe(
            wallets => {
                this.userWallets = wallets;
                this.bitcoinWallet = Wallet.readBitcoin(wallets);
                this.ethereumWallet = Wallet.readEthereum(wallets);
                this.tokoWallet = Wallet.readToko(wallets);
            },
            error => { this.alertService.error(<any>error) },
            () => {
                this.getBitcoinBalance();
                this.getEthereumBalance();
                console.log("getMyUser success");
            }
        );
    }*/

    private onQuantityChange($event: any) {
        this.transaction.totalValue = this.transaction.itemValue * this.transaction.quantity;
    }

    private onStartTimeStampChange(event: any) {
        console.log("onStartTimeStampChange");
        if (this.startDate) {
            this.startDate.setHours(Number(this.startTime.split(":")[0]));
            this.startDate.setMinutes(Number(this.startTime.split(":")[1]));
            if (this.startDate.getFullYear() > 1900 && this.startDate.getMonth() >= 0 && this.startDate.getDate() > 0) {
                this.transaction.startTimeStamp = this.startDate.getTime();
                console.log(this.startDate);
                console.log(this.startDate.getTime());
            }
        }
    }

    private onEndTimeStampChange(event: any) {
        console.log("onEndTimeStampChange");
        if (this.endDate) {
            this.endDate.setHours(Number(this.endTime.split(":")[0]));
            this.endDate.setMinutes(Number(this.endTime.split(":")[1]));
            if (this.endDate.getFullYear() > 1900 && this.endDate.getMonth() >= 0 && this.endDate.getDate() > 0) {
                this.transaction.endTimeStamp = this.endDate.getTime();
            }
        }
    }

    private showTransactionCompleteAlert(alert: Alert) { 
        if (Alert.isSuccess(alert)) {
            this.alertService.success("INFO_COMPLETE");
        } else {
            this.alertService.error(alert.message)
        }
    }

    private update() {
         console.log(this.transaction);
        if (this.transaction.currency == "btc") {
            this.updateTransactionBitcoin()
        } else if (this.transaction.currency == "eth") {
            this.updateTransactionEthereum()
        } else if (this.transaction.currency == "tok") {
            this.updateTransactionToko()
        } else if ((this.transaction.currency == "eur")
            || (this.transaction.currency == "gbp")
            || (this.transaction.currency == "usd")
            || (this.transaction.currency == "zar")) {
            this.updateTransactionFiat();
        }
    }

    private updateTransactionToko() {
        //TODO:Finish
        if (Transaction.isEmpty(this.transaction)) {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        } else {
          
            this.walletService.update(this.tokoWallet).subscribe(
                (alert : Alert) => {
                    //Transfer Toko
                },
                error => { this.alertService.error(<any>error) },
                () => { }
            );

            this.transactionService.create(this.transaction).subscribe(
                () => {
                    //Transfer Toko
                },
                error => { this.alertService.error(<any>error) },
                () => { this.router.navigate(["/transaction/read/list"]) }
            );
        }
    }

    private updateTransactionFiat() {
        if (Transaction.isEmpty(this.transaction)) {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        } else {
            this.transactionService.create(this.transaction).subscribe(
                () => { this.router.navigate(["/transaction/read/list"]) },
                error => { this.alertService.error(<any>error) },
                () => { this.alertService.success("INFO_COMPLETE") }
            );
        }
    }

    //https://bitcoin.org/en/developer-examples#simple-raw-transaction
    // var parameters = {
    //  "txid": "263c018582731ff54dc72c7d67e858c002ae298835501d\80200f05753de0edf0",
    //  "vout": 0,
    //  "address": "muhtvdmsnbQEPFuEmxcChX58fGvXaaUoVt",
    //  "scriptPubKey": "76a9149ba386253ea698158b6d34802bb9b550\f5ce36dd88ac",
    //  "amount": 40.00000000,
    //  "confirmations": 1,
    //  "spendable": true,
    //  "solvable": true
    //}
    private updateTransactionBitcoin() {
        //Note* userId and createTimeStamp set in backend 
        if (Transaction.isEmpty(this.transaction)) {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        } else {
            //Create transactions to sellers account from invoice account
            for (var i = 0; i < this.transaction.users.length; i++) {
                if (this.transaction.users[i].type == "seller") {
                    var bitcoinTransaction = new Bitcoin()
                    bitcoinTransaction.toaddress = Wallet.readBitcoin(this.transaction.users[i].wallets).name;                  
                    bitcoinTransaction.amount = TransactionHelper.BuyerShareValue( this.transaction);
                    this.bitcoinService.createSystemReceiptFromInvoice(bitcoinTransaction).subscribe(
                        (alert: Alert) => {this.showTransactionCompleteAlert(alert);  },
                        (error) => { this.alertService.error(<any>error) },
                        () => { this.createTransactionAuditTrail(); }
                    );
                    //his.router.navigate(["/transaction/read/list"])
                }
            }
        }
    }

    private updateTransactionEthereum() {
        //TODO: Test, gas, gasPrice
        if (Transaction.isEmpty(this.transaction)) {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        } else {
            //Create transactions to sellers account from invoice account
            for (var i = 0; i < this.transaction.users.length; i++) {
                if (this.transaction.users[i].type == "seller") {
                    var ethereumTransaction = new Ethereum()
                    ethereumTransaction.to = Wallet.readEthereum(this.transaction.users[i].wallets).name;
                    ethereumTransaction.value = TransactionHelper.BuyerShareValue( this.transaction).toString(16);//this.transaction.totalValue.toString(16);
                    this.ethereumService.createSystemReceiptFromInvoice(ethereumTransaction).subscribe(
                        (alert: Alert) => { this.showTransactionCompleteAlert(alert);  },
                        (error) => { this.alertService.error(<any>error) },
                        () => { this.createTransactionAuditTrail(); }
                    );
                    //this.router.navigate(["/transaction/read/list"])            
                }
            }
        }
    }

    public updateTransactionBySeller() {
        /*this.transactionService.updateStateBySeller(this.transaction).subscribe(
            transaction => {
                this.transaction = transaction;
            },
            error => { this.alertService.error(<any>error) },
            () => { console.log("gettransaction success") }
        );*/
    }
}