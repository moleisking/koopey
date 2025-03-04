import { MatTableDataSource } from "@angular/material/table";
import { Subscription } from "rxjs/internal/Subscription";
import { User } from "../../../models/user";
import { AlertService } from "../../../services/alert.service";
import { Transaction } from "@models/transaction";
import { TransactionService } from "@services/transaction.service";

export class TransactionDataSource extends MatTableDataSource<Transaction> {

    public transactions: Array<Transaction> = new Array<Transaction>();
    private transactionSubscription: Subscription = new Subscription();

    constructor(private alertService: AlertService, private transactionService: TransactionService,) {
        super();
        this.getTransactions();
    }

    public getTransactions() {
        this.transactionSubscription = this.transactionService.getTransactions().subscribe(
            (transactions: Array<Transaction>) => { this.transactions = transactions; },
            (error: Error) => { this.alertService.error(error.message); },
            () => { /*this.refreshDataSource();*/ }
        );
    }

    disconnect() {
        this.transactionSubscription.unsubscribe();
        super.disconnect();
    }
}