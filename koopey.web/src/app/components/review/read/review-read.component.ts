import { AlertService } from "../../../services/alert.service";
import { BaseComponent } from "../../base/base.component";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
//import { ActivatedRoute, Router } from "@angular/router";

import { Review } from "../../../models/review";
import { ReviewService } from "src/app/services/review.service";
import { Subscription } from "rxjs";

@Component({
  selector: "review-read",
  styleUrls: ["review-read.css"],
  templateUrl: "review-read.html",
})
export class ReviewReadComponent extends BaseComponent implements OnInit {
  private review: Review = new Review();
  private reviewSubscription: Subscription = new Subscription();

  constructor(
    // private activatedRoute: ActivatedRoute,
    private alertService: AlertService,
    private reviewService: ReviewService,
    public sanitizer: DomSanitizer
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    /* this.activatedRoute.queryParams.subscribe((parameter) => {
      if (parameter["type"]) {
        this.location.type = parameter["type"];
      }
      if (parameter["id"]) {
        console.log(parameter["id"]);
        this.locationService.getLocation().subscribe((location) => {
          this.location = location;
        });
      }
    });*/
    this.getReview();
  }

  ngOnDestroy() {
    if (this.reviewSubscription) {
      this.reviewSubscription.unsubscribe();
    }
  }

  private getReview() {
    this.reviewSubscription = this.reviewService.getReview().subscribe(
      (review: Review) => {
        this.review = review;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }
}
