import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { TransactionService } from "../../../services/transaction.service";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { ModelHelper } from "src/app/helpers/ModelHelper";
import { UserService } from "../../../services/user.service";
import { UserType } from "src/app/models/type/UserType";
import { TransactionType } from "src/app/models/type/TransactionType";

@Component({
  selector: "transaction-list-component",
  styleUrls: ["transaction-list.css"],
  templateUrl: "transaction-list.html",
})
export class TransactionListComponent implements OnInit {
  public columns: number = 1;
  private screenWidth: number = window.innerWidth;
  private transactionSubscription: Subscription = new Subscription();
  public transactions: Array<Transaction> = new Array<Transaction>();

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private router: Router,
    private transactionService: TransactionService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.getTranasactions();
  }

  ngAfterViewInit() {
    this.onScreenSizeChange();
  }

  ngOnDestroy() {
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
  }

  public getTranasactions() {
    this.transactionSubscription = this.transactionService
      .getTransactions()
      .subscribe(
        (transactions) => {
          this.transactions = transactions;
        },
        (error: Error) => {
          console.log(error.message);
        }
      );
  }

  public isQuote(transaction: Transaction) {
    return ModelHelper.is(transaction, TransactionType.Quote);
  }

  public isInvoice(transaction: Transaction) {
    return ModelHelper.is(transaction, TransactionType.Invoice);
  }

  public isReceipt(transaction: Transaction) {
    return ModelHelper.is(transaction, TransactionType.Receipt);
  }

  public isBuyer(transaction: Transaction): boolean {
    return localStorage.getItem("id") === transaction.buyerId ? true : false;
  }

  public isSeller(transaction: Transaction): boolean {
    return localStorage.getItem("id") === transaction.buyerId ? true : false;
  }

  public getUser(transaction: Transaction): User {
    let user: User = new User();
    this.userService.read(transaction.buyerId!).subscribe((u: User) => {
      user = u;
    });
    return user;
  }

  public getValue(transaction: Transaction): number {
    if (this.isBuyer(transaction)) {
      return -1 * transaction.value;
    } else {
      return transaction.value;
    }
  }

  public gotoTransactionEdit(transaction: Transaction) {
    if (this.isBuyer(transaction) || this.isSeller(transaction)) {
      if (ModelHelper.is(transaction, TransactionType.Receipt)) {
        //Read
        this.transactionService.setTransaction(transaction);
        this.router.navigate(["/transaction/read/" + transaction.id]);
      } else if (
        ModelHelper.is(transaction, TransactionType.Invoice) ||
        ModelHelper.is(transaction, TransactionType.Quote)
      ) {
        //Update
        this.transactionService.setTransaction(transaction);
        this.router.navigate(["/transaction/edit"]);
      }
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

  public showNoResults(): boolean {
    if (!this.transactions || this.transactions.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
