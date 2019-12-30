const SHA256 = require("crypto-js/sha256");
import { UUID } from 'angular2-uuid';

export enum ReviewType {
    Comment = 'comment',   
    Stars = 'stars',
    Thumbs = 'thumbs'   
};

export class Review {
    public id: string = UUID.UUID();
    public type: string = ReviewType.Stars;
    public articleId: string;
    public userId: string;
    public judgeId: string;
    public assetId: string;
    public value: number = 0;
    public comment: string;
    public hash: string;
    public createtimestamp: number = Date.now();
    public readtimestamp: number;
    public updatetimestamp: number;
    public deletetimestamp: number;

    public static size(reviews: Array<Review>): number {
        if (reviews) {
            return reviews.length;
        } else {
            return 0;
        }
    }

    public static getStarAverage(reviews: Array<Review>): number {
        var denominator = 0;
        var numerator = 0

        if (reviews) {
            for (var i = 0; i < reviews.length; i++) {
                if (reviews[i].type === ReviewType.Stars) {
                    denominator += reviews[i].value;
                    numerator++;
                }
            }
        }

        return denominator == 0 || numerator == 0 ? 0 : Math.round(denominator / numerator);
    }

    public static getThumbAverage(reviews: Array<Review>): number {
        var denominator = 0;
        var numerator = 0

        if (reviews) {
            for (var i = 0; i < reviews.length; i++) {
                if (reviews[i].type === ReviewType.Thumbs) {
                    denominator += reviews[i].value;
                    numerator++;
                }
            }
        }

        return denominator == 0 || numerator == 0 ? 0 : Math.round(denominator / numerator);
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
        if (review.userId
            && review.judgeId
            && review.value) {
            return false;
        } else {
            return true;
        }
    }

    public static contains(reviews: Array<Review>, judgeId: string): boolean {
        if (reviews && reviews.length > 0) {
            for (var i = 0; i < reviews.length; i++) {
                //Exclude current fee
                if (reviews[i] &&
                    reviews[i].judgeId == judgeId) {
                    //Current item is not unique                     
                    return true;
                } else if (i == reviews.length - 1) {
                    //Last item and unique  
                    return false;
                }
            }
        } else {
            return false;
        }
    }


    public static create(reviews: Array<Review>, review: Review): Array<Review> {
        if (reviews.length == 0 || !Review.contains(reviews, review.judgeId)) {
            reviews.push(review);
            return reviews;
        } else {
            return reviews;
        }
    }

    public static read(reviews: Array<Review>, review: Review): Review {
        if (reviews && reviews.length > 0) {
            for (var i = 0; i < reviews.length; i++) {
                if (reviews[i] &&
                    reviews[i].id == review.id) {
                    return reviews[i];
                }
            }
        }
    }

    public static update(reviews: Array<Review>, review: Review): Array<Review> {
        if (reviews && reviews.length > 0) {
            for (var i = 0; i < reviews.length; i++) {
                if (reviews[i] &&
                    reviews[i].id == review.id) {
                    reviews[i] = review;
                    return reviews;
                }
            }
        }
    }

    public static delete(reviews: Array<Review>, review: Review): Array<Review> {
        if (reviews && reviews.length > 0) {
            for (var i = 0; i < reviews.length; i++) {
                if (reviews[i] &&
                    reviews[i].id == review.id) {
                    reviews.splice(i, 1);
                    return reviews;
                }
            }
        }
    }
}
