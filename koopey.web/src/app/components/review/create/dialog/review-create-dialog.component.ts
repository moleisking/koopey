import { Component, OnInit, Input, ElementRef, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AlertService } from "../../../../services/alert.service";
import { AssetService } from "../../../../services/asset.service";
import { ClickService } from "../../../../services/click.service";
import { ReviewService } from "../../../../services/review.service";
import { UserService } from "../../../../services/user.service";
import { TranslateService } from "@ngx-translate/core";
import { ReviewCreateComponent } from "../review-create.component";
import { Review } from "../../../../models/review";
import { Asset } from "../../../../models/asset";
import { User } from "../../../../models/user";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "review-create-dialog",
  templateUrl: "review-create-dialog.html",
})
export class ReviewCreateDialogComponent extends ReviewCreateComponent
  implements OnInit {
  constructor(
    protected alertService: AlertService,
    protected clickService: ClickService,
    public dialogRef: MatDialogRef<ReviewCreateDialogComponent>,
    protected userService: UserService,
    protected reviewService: ReviewService,
    protected router: Router
  ) {
    super(alertService, clickService, reviewService, router);
  }

  ngOnInit() {
    this.redirect = false;
  }

  public setReview(review: Review) {
    this.review = review;
  }

  private cancel() {
    this.dialogRef.close(null);
  }
}
