import {
  AfterViewChecked,
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { MatSort } from "@angular/material/sort";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { TransactionService } from "../../../services/transaction.service";
import { Transaction } from "../../../models/transaction";
import { OperationType } from "src/app/models/type/OperationType";

@Component({
  selector: "transaction-list-component",
  styleUrls: ["transaction-list.css"],
  templateUrl: "transaction-list.html",
})
export class TransactionListComponent
  implements AfterViewChecked, AfterViewInit, OnInit, OnDestroy {
  private transactionSubscription: Subscription = new Subscription();
  public transactions: Array<Transaction> = new Array<Transaction>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild("paginatorElement") paginatorElement: MatPaginator | undefined;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = [
    "name",
    "reference",
    "type",
    "value",
    "quantity",
    "total",
    "currency",
    "start",
    "end",
  ];
  dataSource = new MatTableDataSource<Location>();

  constructor(
    private router: Router,
    private transactionService: TransactionService
  ) {}

  ngOnInit() {
    this.getTranasactions();
  }

  ngAfterViewChecked() {
    if (this.transactions.length <= 10) {
      this.paginatorElement!.disabled = true;
      this.paginatorElement!.hidePageSize = true;
      this.paginatorElement!.showFirstLastButtons = false;
    }
  }

  ngAfterViewInit() {
    this.refreshDataSource();
  }

  ngOnDestroy() {
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
  }

  public create() {
    this.transactionService.setType(OperationType.Create);
    this.router.navigate(["/transaction/edit"]);
  }

  public edit(transaction: Transaction) {
    this.transactionService.setTransaction(transaction);
    this.transactionService.setType(OperationType.Update);
    this.router.navigate(["/transaction/edit"]);
  }

  public getTranasactions() {
    this.transactionSubscription = this.transactionService
      .searchByBuyerOrSeller()
      .subscribe(
        (transactions) => {
          this.transactions = transactions;
        },
        (error: Error) => {
          console.log(error.message);
        },
        () => {
          this.refreshDataSource();
        }
      );
  }

  private refreshDataSource() {
    this.dataSource = new MatTableDataSource<Location>(
      this.transactions as Array<any>
    );
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  /*public isQuote(transaction: Transaction) {
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
  }*/
}
