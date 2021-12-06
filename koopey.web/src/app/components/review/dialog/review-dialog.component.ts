import { Component, OnInit, Input, ElementRef, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { AssetService } from "../../../services/asset.service";
import { ReviewService } from "../../../services/review.service";
import { UserService } from "../../../services/user.service";
import { TranslateService } from "@ngx-translate/core";
import { ReviewEditComponent } from "../edit/review-edit.component";
import { Review } from "../../../models/review";
import { Asset } from "../../../models/asset";
import { User } from "../../../models/user";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "review-dialog",
  templateUrl: "review-dialog.html",
})
export class ReviewDialogComponent extends ReviewEditComponent
  implements OnInit {
  constructor(
    protected alertService: AlertService,
    public dialogRef: MatDialogRef<ReviewDialogComponent>,
    protected userService: UserService,
    protected reviewService: ReviewService,
    protected router: Router
  ) {
    super(alertService, reviewService, router);
  }

  ngOnInit() {
    //this.redirect = false;
  }

  public setReview(review: Review) {
    this.review = review;
  }

  private cancel() {
    this.dialogRef.close(null);
  }
}
