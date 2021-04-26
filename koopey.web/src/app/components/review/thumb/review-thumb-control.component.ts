import { Component, OnInit, Input, ElementRef, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { ReviewService } from "../../../services/review.service";
import { TranslateService } from "@ngx-translate/core";
import { Review, ReviewType } from "../../../models/review";
import { Asset } from "../../../models/asset";
import { User } from "../../../models/user";

@Component({
  selector: "review-thumb-control",
  templateUrl: "review-thumb-control.html",
})
export class ReviewThumbControlComponent implements OnInit {
  @Input() reviews: Array<Review> = new Array<Review>();

  constructor() {}

  ngOnInit() {}

  private isEmpty(): boolean {
    if (this.reviews && this.reviews.length > 0) {
      return false;
    } else {
      return true;
    }
  }

  private getCount(): number {
    var counter = 0;

    if (this.reviews) {
      for (var i = 0; i < this.reviews.length; i++) {
        if (this.reviews[i].type === ReviewType.Thumbs) {
          counter++;
        }
      }
    }

    return counter;
  }

  private getPositive(): number {
    var counter = 0;

    if (this.reviews) {
      for (var i = 0; i < this.reviews.length; i++) {
        if (
          this.reviews[i].type === ReviewType.Thumbs &&
          this.reviews[i].value == 100
        ) {
          counter++;
        }
      }
    }

    return counter;
  }

  private getNegative(): number {
    var counter = 0;

    if (this.reviews) {
      for (var i = 0; i < this.reviews.length; i++) {
        if (
          this.reviews[i].type === ReviewType.Thumbs &&
          this.reviews[i].value == 0
        ) {
          counter++;
        }
      }
    }

    return counter;
  }

  private getReviewAverage(): number {
    var denominator = 0;
    var numerator = 0;

    if (this.reviews) {
      for (var i = 0; i < this.reviews.length; i++) {
        if (this.reviews[i].type === ReviewType.Thumbs) {
          denominator += this.reviews[i].value;
          numerator++;
        }
      }
    }

    return denominator == 0 || numerator == 0
      ? 0
      : Math.round(denominator / numerator);
  }
}
