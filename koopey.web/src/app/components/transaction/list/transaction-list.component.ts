//Angular, Material, Libraries
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../../../services/alert.service";
import { AuthService } from "../../../services/auth.service";
import { ClickService, CurrentComponent, ActionIcon } from "../../../services/click.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "ng2-translate";
//Helpers
import { CurrencyHelper } from "../../../helpers/CurrencyHelper";
import { DateHelper } from "../../../helpers/DateHelper";
//Objects
import { Config } from "../../../config/settings";
import { Transaction, TransactionType } from "../../../models/transaction";
import { User } from "../../../models/user";

@Component({
    selector: "transaction-list-component",
    templateUrl: "../../views/transaction-list.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class TransactionListComponent implements OnInit {
    //Controls
    private clickSubscription: Subscription;
    private transactionSubscription: Subscription;
    //Objects   
    private LOG_HEADER: string = 'TRANSACTION:LIST';
    private transactions: Array<Transaction> = new Array<Transaction>();
    //Numbers
    private columns: number = 1;
    private screenWidth: number = window.innerWidth;

    constructor(
        private alertService: AlertService,
        private authService: AuthService,
        private clickService: ClickService,
        private router: Router,
        private transactionService: TransactionService,
        private translateService: TranslateService
    ) { }

    ngOnInit() {
        this.transactionSubscription = this.transactionService.getTransactions().subscribe(
            (transactions) => {
                this.transactions = transactions;
            },
            (error) => { console.log(error); },
            () => { });
    }

    ngAfterContentInit() {
        this.clickService.createInstance(ActionIcon.CREATE, CurrentComponent.TransactionListComponent);
        this.clickSubscription = this.clickService.getTransactionListClick().subscribe(() => {
            this.gotoTransactionCreate();
        });
    }

    ngAfterViewInit() {
        this.onScreenSizeChange(null);
    }

    ngOnDestroy() {
        if (this.clickSubscription) {
            this.clickService.destroyInstance();
            this.clickSubscription.unsubscribe();
        }
        if (this.transactionSubscription) {
            this.transactionSubscription.unsubscribe();
        }
    }

    private isQuote(transaction: Transaction) {
        return Transaction.isQuote(transaction);
    }

    private isInvoice(transaction: Transaction) {
        return Transaction.isInvoice(transaction);
    }

    private isReceipt(transaction: Transaction) {
        return Transaction.isReceipt(transaction);
    }

    private isBuyer(transaction: Transaction) {
        if (transaction && transaction.users && transaction.users.length >= 2) {
            for (var i = 0; i < transaction.users.length; i++) {
            if (User.isBuyer(transaction.users[i]) /*&& (transaction.users[i].id == localStorage.getItem("id"))*/ ) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private isSeller(transaction: Transaction) {
        if (transaction && transaction.users && transaction.users.length >= 2) {
            for (var i = 0; i < transaction.users.length; i++) {
                if (User.isSeller(transaction.users[i]) /* && transaction.users[i].id == localStorage.getItem("id") */) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }   

    private getValue(transaction: Transaction) {
        if (this.isBuyer(transaction)) {
            return -1 * transaction.itemValue;
        } else {
            return transaction.itemValue;
        }
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

    private getCurrencySymbol(currency: string): string {
        if (currency != null) {
            return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
        }
    }

    private getDateTimeString(startTimeStamp: number): string {
        if (startTimeStamp) {
            return DateHelper.convertEpochToDateTimeString(startTimeStamp);
        } else {
            return DateHelper.convertEpochToDateTimeString(0);
        }
    }

    private gotoTransactionUpdate(transaction: Transaction) {
        if (this.isBuyer(transaction) || this.isSeller(transaction)) {
            if (Transaction.isReceipt(transaction)) {
                //Read
                this.transactionService.setTransaction(transaction);
                this.router.navigate(["/transaction/read/one"])
            } else if (Transaction.isInvoice(transaction) || Transaction.isQuote(transaction)) {
                //Update              
                this.transactionService.setTransaction(transaction);
                this.router.navigate(["/transaction/update"])
            }
        }
    }

    private gotoTransactionCreate() {
        console.log("gotoTransactionCreate()");
        var transaction = new Transaction();
        transaction.type = TransactionType.DirectTransfer;
        var buyer = this.authService.getLocalUser();
        buyer.type = "buyer";
        transaction.users.push(buyer);

        this.transactionService.setTransaction(transaction);
        this.router.navigate(["/transaction/create"])
    }

    private showNoResults(): boolean {
        if (!this.transactions || this.transactions.length == 0) {
            return true;
        } else {
            return false;
        }
    }
}
