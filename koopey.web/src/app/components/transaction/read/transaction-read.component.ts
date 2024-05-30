import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { TransactionHelper } from "../../../helpers/TransactionHelper";
import { Transaction } from "../../../models/transaction";
import { ModelHelper } from "src/app/helpers/ModelHelper";
import { TransactionType } from "src/app/models/type/TransactionType";

@Component({
  selector: "transaction-read-component",
  styleUrls: ["transaction-read.css"],
  templateUrl: "transaction-read.html",
})
export class TransactionReadComponent implements OnInit, OnDestroy {
  private transactionSubscription: Subscription = new Subscription();
  public transaction: Transaction = new Transaction();

  constructor(
    private route: ActivatedRoute,
    private alertService: AlertService,
    private transactionService: TransactionService
  ) { }

  ngOnInit() {
    this.getTransactionFromService();
  }

  ngOnDestroy() { }

  public isAuthBuyer(): boolean {
    return TransactionHelper.isAuthBuyer(this.transaction);
    /* if (this.transaction && this.transaction.buyer && this.transaction.buyer.id == localStorage.getItem("id")) {
            return true;
        } else {
            return false;
        }*/
  }

  public isInvoice() {
    return ModelHelper.is(this.transaction, TransactionType.Invoice);
  }

  public isQuote() {
    return ModelHelper.is(this.transaction, TransactionType.Quote);
  }

  public isReceipt() {
    return ModelHelper.is(this.transaction, TransactionType.Receipt);
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

  private getTransactionFromService() {
    this.transactionSubscription = this.transactionService
      .getTransaction()
      .subscribe(
        (transaction: Transaction) => {
          this.transaction = transaction;
          if (transaction.isEmpty()) {
            this.getTransactionFromServer();
          }
        },
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
  }

  private getTransactionFromServer() {
    this.route.params.subscribe((p) => {
      let id = p["id"];
      if (id) {
        this.transactionSubscription = this.transactionService.read(id, true).subscribe(
          (transaction: Transaction) => {
            this.transaction = transaction;
          },
          (error: Error) => {
            this.alertService.error(error.message);
          }
        );
      }
    });
  }

}
