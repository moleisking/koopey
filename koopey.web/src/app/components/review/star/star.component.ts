import { Component, OnInit, Input, ElementRef, ViewChild } from "@angular/core";
import { Review, ReviewType } from "../../../models/review";

@Component({
  selector: "star",
  templateUrl: "star.html",
})
export class ReviewStarControlComponent implements OnInit {
  @Input() reviews: Array<Review> = new Array<Review>();

  constructor() {}

  ngOnInit() {
    console.log(this.getAverage());
    console.log(this.reviews);
  }

  public isEmpty(): boolean {
    if (this.reviews && this.reviews.length > 0) {
      return false;
    } else {
      return true;
    }
  }

  public getAverage(): number {
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

  public getCount(): number {
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