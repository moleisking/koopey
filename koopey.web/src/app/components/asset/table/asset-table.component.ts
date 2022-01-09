import { ActivatedRoute, Router } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { Component, OnInit, OnDestroy, ViewChild, AfterViewChecked, AfterViewInit } from "@angular/core";
import { DistanceHelper } from "src/app/helpers/DistanceHelper";
import { DomSanitizer } from "@angular/platform-browser";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { OperationType } from "src/app/models/type/OperationType";
import { Subscription } from "rxjs";
import { Transaction } from "src/app/models/transaction";
import { TransactionService } from "src/app/services/transaction.service";

@Component({
  selector: "asset-table-component",
  styleUrls: ["asset-table.css"],
  templateUrl: "asset-table.html",
})
export class AssetTableComponent implements AfterViewChecked, AfterViewInit, OnInit, OnDestroy {

  public transactions: Array<Transaction> = new Array<Transaction>();
  private transactionSubscription: Subscription = new Subscription();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild("paginatorElement") paginatorElement: MatPaginator | undefined;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = [
    "name",
    "firstImage",
    "positive",
    "negative",
    "distance",
    "review", "edit"
  ];
  dataSource = new MatTableDataSource<Transaction>();

  constructor(
    private route: ActivatedRoute,
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    public sanitizer: DomSanitizer,
    private router: Router,
    private transactionService: TransactionService,
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe((parameters) => {
      if (parameters["type"] === "sales") {
        this.getSales();
      } else {
        this.getPurchases();
      }
    });
  }

  ngAfterViewChecked() {
    if (this.transactions && this.transactions.length <= 10) {
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
    this.router.navigate(["/asset/edit/", { 'queryParams': { 'operation': 'create' } }]);
  }

  public edit(transaction: Transaction) {
    if (transaction.asset != undefined) {
      this.transactionService.setType(OperationType.Update);
      this.transactionService.setTransaction(transaction);
      this.router.navigate(["/asset/edit/" + transaction.id], { 'queryParams': { 'operation': 'update' } });
    }
  }

  public getDistance(latitude: number, longitude: number): string {
    if (latitude && longitude) {
      return DistanceHelper.distanceAndUnit(
        this.authenticationService.getLocalLatitude(),
        this.authenticationService.getLocalLatitude(),
        latitude,
        longitude
      );
    } else {
      return "";
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
        this.refreshDataSource();
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
        this.refreshDataSource();
      }
    );
  }

  public openReview(transaction: Transaction) {
    if (transaction.asset != undefined && transaction.buyer != undefined && transaction.seller != undefined) {
      this.transactionService.setTransaction(transaction);
      this.router.navigate(["/review/edit/" + transaction.id]);
    } else {
      this.alertService.error("ERROR_NOT_SOLD");
    }
  }

  public read(transaction: Transaction) {
    console.log(transaction.asset);
    this.transactionService.setTransaction(transaction);
    this.router.navigate(["/asset/read/" + transaction.id]);
  }

  private refreshDataSource() {
    this.dataSource = new MatTableDataSource<Transaction>(
      this.transactions as Array<any>
    );
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

}
