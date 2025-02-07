import { Component, OnInit, Input, ElementRef, ViewChild } from "@angular/core";
import { Transaction } from "../../../models/transaction";
import { ReviewType } from "../../../models/type/ReviewType";

@Component({
  selector: "negativebutton",
  templateUrl: "negativebutton.html",
})
export class NegativeButtonComponent implements OnInit {
  @Input() public reviews: Array<Transaction> = new Array<Transaction>();

  constructor() {}

  ngOnInit() {}

  public isEmpty(): boolean {
    if (this.reviews && this.reviews.length > 0) {
      return false;
    } else {
      return true;
    }
  }

  public getCount(): number {
    var counter = 0;

    if (this.reviews) {
      for (var i = 0; i < this.reviews.length; i++) {
        if (this.reviews[i].type === ReviewType.Thumb) {
          counter++;
        }
      }
    }

    return counter;
  }

  public getNegative(): number {
    var counter = 0;

    if (this.reviews) {
      for (var i = 0; i < this.reviews.length; i++) {
        if (
          this.reviews[i].type === ReviewType.Thumb &&
          this.reviews[i].value == 0
        ) {
          counter++;
        }
      }
    }

    return counter;
  }

  public getAverage(): number {
    var denominator = 0;
    var numerator = 0;

    if (this.reviews) {
      for (var i = 0; i < this.reviews.length; i++) {
        if (this.reviews[i].type === ReviewType.Thumb) {
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
