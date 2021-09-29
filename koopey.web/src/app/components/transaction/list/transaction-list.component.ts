import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "src/environments/environment";
import { Transaction, TransactionType } from "../../../models/transaction";
import { User, UserType } from "../../../models/user";
import { ModelHelper } from "src/app/helpers/ModelHelper";

@Component({
  selector: "transaction-list-component",
  templateUrl: "transaction-list.html",
  styleUrls: ["transaction-list.css"],
})
export class TransactionListComponent implements OnInit {
  private clickSubscription: Subscription = new Subscription();
  private transactionSubscription: Subscription = new Subscription();
  public transactions: Array<Transaction> = new Array<Transaction>();
  public columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private clickService: ClickService,
    private router: Router,
    private transactionService: TransactionService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.transactionSubscription = this.transactionService
      .getTransactions()
      .subscribe(
        (transactions) => {
          this.transactions = transactions;
        },
        (error) => {
          console.log(error);
        },
        () => {}
      );
  }

  ngAfterContentInit() {
    this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.TransactionListComponent
    );
    this.clickSubscription = this.clickService
      .getTransactionListClick()
      .subscribe(() => {
        this.gotoTransactionCreate();
      });
  }

  ngAfterViewInit() {
    this.onScreenSizeChange();
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

  public isQuote(transaction: Transaction) {
    return Transaction.isQuote(transaction);
  }

  public isInvoice(transaction: Transaction) {
    return Transaction.isInvoice(transaction);
  }

  public isReceipt(transaction: Transaction) {
    return Transaction.isReceipt(transaction);
  }

  public isBuyer(transaction: Transaction) {
    if (transaction && transaction.users && transaction.users.length >= 2) {
      for (var i = 0; i < transaction.users.length; i++) {
        if (
          User.isBuyer(
            transaction.users[i]
          ) /*&& (transaction.users[i].id == localStorage.getItem("id"))*/
        ) {
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  }

  public isSeller(transaction: Transaction) {
    if (transaction && transaction.users && transaction.users.length >= 2) {
      for (var i = 0; i < transaction.users.length; i++) {
        if (
          User.isSeller(
            transaction.users[i]
          ) /* && transaction.users[i].id == localStorage.getItem("id") */
        ) {
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  }

  public getBuyer(transaction: Transaction) {
    return ModelHelper.count(UserType.Buyer, transaction.users);
  }

  public getSeller(transaction: Transaction) {
    return ModelHelper.count(UserType.Seller, transaction.users);
  }

  public getValue(transaction: Transaction) {
    if (this.isBuyer(transaction)) {
      return -1 * transaction.itemValue;
    } else {
      return transaction.itemValue;
    }
  }

  public onScreenSizeChange() {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth <= 512) {
      this.columns = 1;
    } else if (this.screenWidth > 512 && this.screenWidth <= 1024) {
      this.columns = 2;
    } else if (this.screenWidth > 1024 && this.screenWidth <= 2048) {
      this.columns = 3;
    } else if (this.screenWidth > 2048 && this.screenWidth <= 4096) {
      this.columns = 4;
    }
  }

  public gotoTransactionUpdate(transaction: Transaction) {
    if (this.isBuyer(transaction) || this.isSeller(transaction)) {
      if (Transaction.isReceipt(transaction)) {
        //Read
        this.transactionService.setTransaction(transaction);
        this.router.navigate(["/transaction/read/one"]);
      } else if (
        Transaction.isInvoice(transaction) ||
        Transaction.isQuote(transaction)
      ) {
        //Update
        this.transactionService.setTransaction(transaction);
        this.router.navigate(["/transaction/update"]);
      }
    }
  }

  public gotoTransactionCreate() {
    console.log("gotoTransactionCreate()");
    var transaction = new Transaction();
    transaction.type = TransactionType.DirectTransfer;
    var buyer = this.authenticationService.getLocalUser();
    buyer.type = "buyer";
    transaction.users.push(buyer);

    this.transactionService.setTransaction(transaction);
    this.router.navigate(["/transaction/create"]);
  }

  public showNoResults(): boolean {
    if (!this.transactions || this.transactions.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
