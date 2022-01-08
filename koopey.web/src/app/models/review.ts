import { Audit } from "./base/audit";
import { ReviewType } from "./type/ReviewType";

export class Review extends Audit {

  public assetId: string = "";
  public sellerId: string = "";
  public buyerId: string = "";
  public value: number = 0;

  public static size(reviews: Array<Review>): number {
    if (reviews) {
      return reviews.length;
    } else {
      return 0;
    }
  }

  public static getStarAverage(reviews: Array<Review>): Number {
    var denominator = 0;
    var numerator = 0;

    if (reviews) {
      for (var i = 0; i < reviews.length; i++) {
        if (reviews[i].type === ReviewType.Star) {
          denominator += reviews[i].value;
          numerator++;
        }
      }
    }

    return denominator == 0 || numerator == 0
      ? 0
      : Math.round(denominator / numerator);
  }

  public static getThumbAverage(reviews: Array<Review>): Number {
    var denominator = 0;
    var numerator = 0;

    if (reviews) {
      for (var i = 0; i < reviews.length; i++) {
        if (reviews[i].type === ReviewType.Thumb) {
          denominator += reviews[i].value;
          numerator++;
        }
      }
    }

    return denominator == 0 || numerator == 0
      ? 0
      : Math.round(denominator / numerator);
  }

  public static getPositive(reviews: Array<Review>): number {
    var positive = 0;
    if (reviews) {
      for (var i = 0; i < reviews.length; i++) {
        if (reviews[i].value > 0) {
          positive++;
        }
      }
    }
    return positive;
  }

  public static getNegative(reviews: Array<Review>): number {
    var negative = 0;
    if (reviews) {
      for (var i = 0; i < reviews.length; i++) {
        if (reviews[i].value <= 0) {
          negative++;
        }
      }
    }
    return negative;
  }

  public static isEmpty(review: Review): boolean {
    if (review.assetId && review.buyerId && review.sellerId && review.value) {
      return false;
    } else {
      return true;
    }
  }
}
