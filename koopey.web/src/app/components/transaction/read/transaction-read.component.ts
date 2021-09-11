import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { TransactionHelper } from "../../../helpers/TransactionHelper";
import { Alert } from "../../../models/alert";
import { Transaction } from "../../../models/transaction";

@Component({
  selector: "transaction-read-component",
  templateUrl: "transaction-read.html",
  styleUrls: ["transaction-read.css"],
})
export class TransactionReadComponent implements OnInit, OnDestroy {
  private transactionSubscription: Subscription = new Subscription();
  public transaction: Transaction = new Transaction();

  constructor(
    private route: ActivatedRoute,
    private alertService: AlertService,
    private translateService: TranslateService,
    private transactionService: TransactionService
  ) {}

  ngOnInit() {
    this.getTransaction();
  }

  ngOnDestroy() {}

  public isAuthBuyer(): boolean {
    return TransactionHelper.isAuthBuyer(this.transaction);
    /* if (this.transaction && this.transaction.buyer && this.transaction.buyer.id == localStorage.getItem("id")) {
            return true;
        } else {
            return false;
        }*/
  }

  public isInvoice() {
    return Transaction.isInvoice(this.transaction);
  }

  public isQuote() {
    return Transaction.isQuote(this.transaction);
  }

  public isReceipt() {
    return Transaction.isReceipt(this.transaction);
  }

  public isAuthSeller() {
    return TransactionHelper.isAuthSeller(this.transaction);
    /* if (this.transaction && this.transaction.seller && this.transaction.seller.id == localStorage.getItem("id")) {
            return true;
        } else {
            return false;
        }*/
  }

  public isLoggedIn() {
    if (localStorage.getItem("id")) {
      return true;
    } else {
      return false;
    }
  }

  private getTransaction() {
    this.route.params.subscribe((p) => {
      let id = p["id"];
      if (id) {
        this.transactionService.read(id).subscribe(
          (transaction: Transaction) => {
            this.transaction = transaction;
          },
          (error: Error) => {
            this.alertService.error(error.message);
          },
          () => {
            console.log("gettransaction success");
          }
        );
      } else {
        this.transactionSubscription = this.transactionService
          .getTransaction()
          .subscribe(
            (transaction) => {
              this.transaction = transaction;
            },
            (error) => {
              console.log(error);
            },
            () => {}
          );
      }
    });
  }
}
