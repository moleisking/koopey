import {
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
//import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { ReviewService } from "../../../services/review.service";
import { AssetService } from "../../../services/asset.service";
//import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { Alert } from "../../../models/alert";
import { User } from "../../../models/user";
import { Asset } from "../../../models/asset";
import { Review, ReviewType } from "../../../models/review";

@Component({
  selector: "review-create",
  templateUrl: "review-create.html",
})
export class ReviewCreateComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private userSubscription: Subscription = new Subscription();
  private assetSubscription: Subscription = new Subscription();
  private reviewSubscription: Subscription = new Subscription();
  public review: Review = new Review();
  public user: User = new User();
  public asset: Asset = new Asset();
  protected redirect: boolean = true;

  @Input() type!: ReviewType;

  constructor(
    protected alertService: AlertService,
    // protected assetService: AssetService,
    protected clickService: ClickService,
    protected reviewService: ReviewService,
    protected router: Router
  ) {}

  ngOnInit() {
    this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.ReviewCreateComponent
    );
    this.clickSubscription = this.clickService
      .getUserCreateClick()
      .subscribe(() => {
        this.create();
      });
    //Subscribe to results
    /*  this.reviewSubscription = this.reviewService.getReview().subscribe(
              (review) => {
                  this.review = review;
              },
              (error) => { console.log(error); },
              () => { });*/
    /*this.userSubscription = this.userService.getUser().subscribe(
            (user) => {
                this.user = user;
                this.review.userId = user.id;
            },
            (error) => { console.log(error); },
            () => { });
        this.assetSubscription = this.assetService.getAsset().subscribe(
            (asset) => {
                this.asset = asset;
                this.review.assetId = asset.id;
            },
            (error) => { console.log(error); },
            () => { });*/
  }

  ngAfterContentInit() {
    //Set default
    this.review.judgeId = localStorage.getItem("id")!;
    this.review.value = 0;
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
    /* if (this.assetSubscription) {
             this.assetSubscription.unsubscribe();
         }
         if (this.reviewSubscription) {
             this.reviewSubscription.unsubscribe();
         }
         if (this.userSubscription) {
             this.userSubscription.unsubscribe();
         }*/
  }

  public setAsset(asset: Asset) {
    //  if (!Asset.isEmpty(asset)) {
    this.asset = asset;
    this.review.assetId = asset.id;
    this.review.userId = asset.buyer.id;
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
    } else if (this.asset) {
      console.log(this.review);
      this.reviewService.create(this.review).subscribe(
        (alert: String) => {
          console.log(alert);
        },
        (error: Error) => {
          this.alertService.error("ERROR_DUPLICATE");
        },
        () => {
          if (this.redirect) {
            this.router.navigate(["/asset/read/one"]);
          }
        }
      );
    }
  }
}
