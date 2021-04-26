import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { ReviewService } from "../../../services/review.service";
import { TranslateService } from "ng2-translate";
import { Config } from "../../../config/settings";
import { Review } from "../../../models/review";

@Component({
  selector: "review-list-component",
  templateUrl: "review-list.html",
  styleUrls: ["review-list.css"],
})
export class ReviewListComponent implements OnInit {
  private clickSubscription: Subscription = new Subscription();
  private transactionSubscription: Subscription = new Subscription();
  private reviews: Array<Review> = new Array<Review>();
  private columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    private reviewService: ReviewService,
    private router: Router,
    private translateService: TranslateService
  ) {}

  ngOnInit() {}

  ngAfterContentInit() {
    this.onScreenSizeChange(null);
  }

  ngAfterViewInit() {}

  ngOnDestroy() {}

  private onScreenSizeChange(event: any) {
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

  private gotoReviewRead(review: Review) {
    this.reviewService.setReview(review);
    this.router.navigate(["/review/read/one"]);
  }

  private showNoResults(): boolean {
    if (!this.reviews || this.reviews.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
