import { Component, OnInit, Input, ElementRef, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { ArticleService } from "../../../services/article.service";
import { ClickService } from "../../../services/click.service";
import { ReviewService } from "../../../services/review.service";
import { UserService } from "../../../services/user.service";
import { TranslateService } from "@ngx-translate/core";
import { ArticleEditComponent } from "../edit/article-edit.component";
import { Article } from "../../../models/article";
import { User } from "../../../models/user";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "article-dialog",
  templateUrl: "article-dialog.html",
})
export class ArticleDialogComponent extends ArticleEditComponent
  implements OnInit {
  constructor(
    protected alertService: AlertService,
    protected articleService: ArticleService,
    // protected clickService: ClickService,
    protected route: ActivatedRoute,
    public dialogRef: MatDialogRef<ArticleDialogComponent>,
    protected userService: UserService,
    protected reviewService: ReviewService,
    protected router: Router
  ) {
    super(alertService, route, router, articleService);
  }

  ngOnInit() {
    //this.redirect = false;
  }

  public setArticle(article: Article) {
    this.article = article;
  }

  private cancel() {
    this.dialogRef.close(null);
  }
}
