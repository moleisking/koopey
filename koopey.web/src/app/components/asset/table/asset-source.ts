import { MatTableDataSource } from "@angular/material/table";
import { Subscription } from 'rxjs';
import { Transaction } from '../../../models/transaction';
import { FilterType } from "../../../models/type/FilterType";
import { AlertService } from "../../../services/alert.service";
import { TransactionService } from "../../../services/transaction.service";

export class AssetDataSource extends MatTableDataSource<Transaction> {

    public transactions: Array<Transaction> = new Array<Transaction>();
    private transactionSubscription: Subscription = new Subscription();

    constructor(assetFilterType : FilterType, private alertService: AlertService, private transactionService: TransactionService,) {
        super();        
            if (assetFilterType === FilterType.Sales) {
                this.getSales();
              } else {
                this.getPurchases();
              }        
    }

    private getPurchases() {
        this.transactionSubscription = this.transactionService.searchByBuyer(true).subscribe(
          (transactions: Array<Transaction>) => {
            console.log(transactions);
            this.transactions = transactions && transactions.length ? transactions : new Array<Transaction>();
          },
          (error: Error) => {
            this.alertService.error(error.message);
          },
          () => {
           // this.refreshDataSource();
          }
        );
      }
    
      private getSales() {
        this.transactionSubscription = this.transactionService.searchBySeller(true).subscribe(
          (transactions: Array<Transaction>) => {
            console.log(transactions);
            this.transactions = transactions && transactions.length ? transactions : new Array<Transaction>();
          },
          (error: Error) => {
            this.alertService.error(error.message);
          },
          () => {
           // this.refreshDataSource();
          }
        );
      }

   disconnect() {
     this.transactionSubscription.unsubscribe();
     super.disconnect();
   }
}