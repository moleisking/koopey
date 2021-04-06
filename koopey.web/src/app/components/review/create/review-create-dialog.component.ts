//Cores
import { Component, OnInit, Input, ElementRef, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { MaterialModule, MdInputModule, MdTextareaAutosize, MdDialog, MdDialogRef } from "@angular/material"
//Services
import { AlertService } from "../../../services/alert.service";
import { AssetService } from "../../../services/asset.service";
import { ClickService } from "../../../services/click.service";
import { ReviewService } from "../../../services/review.service";
import { UserService } from "../../../services/user.service";
import { TranslateService } from "ng2-translate";
//Components
import { ReviewCreateComponent } from "./review-create.component";
//Models
import { Review } from "../../../models/review";
import { Asset } from "../../../models/asset";
import { User } from "../../../models/user";

@Component({
    selector: 'review-create-dialog',
    templateUrl: '../../views/review-create-dialog.html',
})

export class ReviewCreateDialogComponent extends ReviewCreateComponent implements OnInit {
    
    constructor(
        protected alertService: AlertService,           
        protected clickService: ClickService,
        public dialogRef: MdDialogRef<ReviewCreateDialogComponent>,
        protected userService: UserService,
        protected reviewService: ReviewService,
        protected router: Router

    ) {
        super(
            alertService,       
            clickService,
            reviewService,
            router
        );
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