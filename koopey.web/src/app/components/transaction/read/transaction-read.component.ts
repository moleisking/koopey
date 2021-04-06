//Angular, Material, Libraries
import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { MaterialModule, MdIconModule, MdIconRegistry, MdInputModule } from "@angular/material"
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../../../services/alert.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "ng2-translate";
//Helpers
import { CurrencyHelper } from "../../../helpers/CurrencyHelper";
import { TransactionHelper } from "../../../helpers/TransactionHelper";
//Objects
import { Alert } from "../../../models/alert";
import { Transaction } from "../../../models/transaction";

@Component({
    selector: "transaction-read-component",
    templateUrl: "../../views/transaction-read.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class TransactionReadComponent implements OnInit, OnDestroy {

    private transactionSubscription: Subscription;
    private transaction: Transaction = new Transaction();

    constructor(
        private route: ActivatedRoute,
        private alertService: AlertService,
        private translateService: TranslateService,
        private transactionService: TransactionService
    ) { }

    ngOnInit() {
        this.getTransaction();
    }

    ngOnDestroy() { }

    private isAuthBuyer():boolean {
        return TransactionHelper.isAuthBuyer(this.transaction);
       /* if (this.transaction && this.transaction.buyer && this.transaction.buyer.id == localStorage.getItem("id")) {
            return true;
        } else {
            return false;
        }*/
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
          return TransactionHelper.isAuthSeller(this.transaction);
       /* if (this.transaction && this.transaction.seller && this.transaction.seller.id == localStorage.getItem("id")) {
            return true;
        } else {
            return false;
        }*/
    }

    private isLoggedIn() {
        if (localStorage.getItem("id")) {
            return true;
        } else {
            return false;
        }
    }

    private getTransaction() {
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

    private getCurrencySymbol(currency: string): string {
        if (currency != null) {
            return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
        }
    }

    private getTimeStampText(epoch: Number): string {
        var date = new Date(epoch);
        date.setHours(0, 0, 0, 0);
        return date.toDateString();
    }
}
