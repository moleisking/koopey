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
import { Subscription } from "rxjs";
import { Asset } from "../../../models/asset";
import { ReviewType } from "../../../models/type/ReviewType";
import { Transaction } from "../../../models/transaction";
import { TransactionService } from "../../../services/transaction.service";

@Component({
  selector: "review-edit",
  styleUrls: ["review-edit.css"],
  templateUrl: "review-edit.html",
})
export class ReviewEditComponent implements OnInit, OnDestroy {
  private reviewSubscription: Subscription = new Subscription();
  public review: Transaction = new Transaction();

  @Input() type!: ReviewType;

  constructor(
    protected alertService: AlertService,
    protected reviewService: TransactionService,
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
    this.reviewSubscription = this.reviewService.getTransaction().subscribe(
      (review: Transaction) => {
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
