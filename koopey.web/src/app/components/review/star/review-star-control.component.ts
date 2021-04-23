import { Component, OnInit, Input, ElementRef, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { ReviewService } from "../../../services/review.service";
import { TranslateService } from "ng2-translate";
import { Review, ReviewType } from "../../../models/review";
import { Asset } from "../../../models/asset";
import { User } from "../../../models/user";

@Component({
  selector: "review-star-control",
  templateUrl: "../../views/review-star-control.html",
})
export class ReviewStarControlComponent implements OnInit {
  @Input() reviews: Array<Review> = new Array<Review>();

  constructor() {}

  ngOnInit() {
    console.log(this.getAverage());
    console.log(this.reviews);
  }

  private isEmpty(): boolean {
    if (this.reviews && this.reviews.length > 0) {
      return false;
    } else {
      return true;
    }
  }

  private getAverage(): number {
    var denominator = 0;
    var numerator = 0;

    if (this.reviews) {
      for (var i = 0; i < this.reviews.length; i++) {
        if (this.reviews[i].type === ReviewType.Stars) {
          denominator += this.reviews[i].value;
          numerator++;
        }
      }
    }

    return denominator == 0 || numerator == 0
      ? 0
      : Math.round(denominator / numerator);
  }

  private getCount(): number {
    var counter = 0;

    if (this.reviews) {
      for (var i = 0; i < this.reviews.length; i++) {
        if (this.reviews[i].type === ReviewType.Stars) {
          counter++;
        }
      }
    }

    return counter;
  }
}
