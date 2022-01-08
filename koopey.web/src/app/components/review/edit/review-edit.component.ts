import { AlertService } from "../../../services/alert.service";
import { ActivatedRoute, Router } from "@angular/router";
import {
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
//import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ReviewService } from "../../../services/review.service";
import { Subscription } from "rxjs";
import { Asset } from "../../../models/asset";
import { Review } from "../../../models/review";
import { ReviewType } from "src/app/models/type/ReviewType";

@Component({
  selector: "review-edit",
  styleUrls: ["review-edit.css"],
  templateUrl: "review-edit.html",
})
export class ReviewEditComponent implements OnInit, OnDestroy {
  private reviewSubscription: Subscription = new Subscription();
  public review: Review = new Review();

  @Input() type!: ReviewType;

  constructor(
    protected alertService: AlertService,
    protected reviewService: ReviewService,
    protected router: Router
  ) {}

  ngOnInit() {
    this.getReview();
  }

  ngAfterContentInit() {
    this.review.buyerId = localStorage.getItem("id")!;
    this.review.value = 0;
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

  public setAsset(asset: Asset) {
    //  if (!Asset.isEmpty(asset)) {
    //  this.asset = asset;
    this.review.assetId = asset.id;
    this.review.buyerId = asset.buyer.id;
    // }
  }

  public thumbUp() {
    this.review.value = 100;
    this.create();
  }

  public thumbDown() {
    this.review.value = 0;
    this.create();
  }

  public handleStarChange(value: number) {
    this.review.value = value;
    //  this.createReview();
  }

  public handleSaveClick() {
    this.create();
  }

  public create() {
    console.log("createReview()");
    if (this.review.value < 0 || this.review.value > 100) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else if (this.review) {
      console.log(this.review);
      this.reviewService.create(this.review).subscribe(
        (alert: String) => {
          console.log(alert);
        },
        (error: Error) => {
          this.alertService.error("ERROR_DUPLICATE");
        },
        () => {
          /*  if (this.redirect) {
            this.router.navigate(["/asset/read/one"]);
          }*/
        }
      );
    }
  }
}
