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
import { ReviewService } from "../../../services/review.service";
import { Review } from "../../../models/review";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { UserService } from "src/app/services/user.service";

@Component({
  selector: "review-list-component",
  styleUrls: ["review-list.css"],
  templateUrl: "review-list.html",
})
export class ReviewListComponent
  implements AfterViewChecked, AfterViewInit, OnDestroy, OnInit {
  private reviews: Array<Review> = new Array<Review>();
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
  dataSource = new MatTableDataSource<Location>();

  constructor(
    private alertService: AlertService,
    private reviewService: ReviewService,
    private router: Router,
    public sanitizer: DomSanitizer,
    private userService: UserService
  ) {}

  ngOnInit() {}

  ngAfterContentInit() {
    this.reviewSubscription = this.reviewService
      .getReviews()
      .subscribe((reviews: Array<Review>) => {
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
    this.reviewSubscription = this.reviewService.getReviews().subscribe(
      (reviews: Array<Review>) => {
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

  private gotoReview(review: Review) {
    this.reviewService.setReview(review);
    this.router.navigate(["/review/read/one"]);
  }

  private refreshDataSource() {
    this.dataSource = new MatTableDataSource<Location>(
      this.reviews as Array<any>
    );
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
