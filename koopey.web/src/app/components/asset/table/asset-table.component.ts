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
import { Review } from "src/app/models/review";
import { ReviewService } from "src/app/services/review.service";
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
    private reviewService: ReviewService,
    private router: Router,
    private transactionService: TransactionService,
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe((parameters) => {
      if (parameters["type"] === "sales") {
        this.getMySales();
      } else {
        this.getMyPurchases();
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

  private getMyPurchases() {
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

  private getMySales() {
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

  public gotoMyAsset(transaction: Transaction) {
    console.log(transaction.asset);
    this.transactionService.setTransaction(transaction);
    this.router.navigate(["/asset/edit"]);
  }

  public create() {
    this.transactionService.setType(OperationType.Create);
    this.router.navigate(["/asset/edit/"]); //, { 'queryParams': { 'type': 'product' } }
  }

  public edit(transaction: Transaction) {
    if (transaction.asset != undefined) {
     this.transactionService.setType(OperationType.Update);
      this.transactionService.setTransaction(transaction);
      this.router.navigate(["/asset/edit/"]);
    }
  }

  public openReview(transaction: Transaction) {
    if (transaction.asset != undefined && transaction.buyer != undefined && transaction.seller != undefined) {
      let review: Review = new Review();
      review.assetId = transaction.asset.id;
      review.buyerId = transaction.buyer.id;
      review.sellerId = transaction.seller.id;
      this.reviewService.setReview(review);
      this.router.navigate(["/review/edit/"]);
    }
    console.log(transaction);
  }

  private refreshDataSource() {
    this.dataSource = new MatTableDataSource<Transaction>(
      this.transactions as Array<any>
    );
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

}
