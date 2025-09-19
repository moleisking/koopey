import { AlertService } from "../../../services/alert.service";
import {
  AfterViewChecked,
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { MatSort } from "@angular/material/sort";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { UserService } from "../../../services/user.service";
import { Transaction } from "../../../models/transaction";
import { TransactionService } from "../../../services/transaction.service";

@Component({
  selector: "review-table-component",
    standalone: false,
  styleUrls: ["review-table.css"],
  templateUrl: "review-table.html",
})
export class ReviewTableComponent
  implements AfterViewChecked, AfterViewInit, OnDestroy, OnInit {
  private reviews: Array<Transaction> = new Array<Transaction>();
  private reviewSubscription: Subscription = new Subscription();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild("paginatorElement") paginatorElement: MatPaginator | undefined;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = [
    "name",
    "assetId",
    "buyerId",
    "sellerId",
    "type",
    "value",
  ];
  dataSource = new MatTableDataSource<Transaction>();

  constructor(
    private alertService: AlertService,
    private reviewService: TransactionService,
    private router: Router,
    public sanitizer: DomSanitizer,
    private userService: UserService
  ) {}

  ngOnInit() {}

  ngAfterContentInit() {
    this.reviewSubscription = this.reviewService
      .getTransactions()
      .subscribe((reviews: Array<Transaction>) => {
        this.reviews = reviews;
      });
  }

  ngAfterViewChecked() {
    if (this.reviews.length <= 10) {
      this.paginatorElement!.disabled = true;
      this.paginatorElement!.hidePageSize = true;
      this.paginatorElement!.showFirstLastButtons = false;
    }
  }

  ngAfterViewInit() {
    this.refreshDataSource();
  }

  ngOnDestroy() {
    if (this.reviewSubscription) {
      this.reviewSubscription.unsubscribe();
    }
  }

  public getReviews() {
    this.reviewSubscription = this.reviewService.getTransactions().subscribe(
      (reviews: Array<Transaction>) => {
        this.reviews = reviews;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {
        this.refreshDataSource();
      }
    );
  }

  public gotoReview(review: Transaction) {
    this.reviewService.setTransaction(review);
    this.router.navigate(["/review/read/one"]);
  }

  private refreshDataSource() {
    this.dataSource = new MatTableDataSource<Transaction>(
      this.reviews as Array<any>
    );
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
