import { Component, OnInit, Input, ElementRef, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { ArticleService } from "../../../services/article.service";
import { ClickService } from "../../../services/click.service";
import { ReviewService } from "../../../services/review.service";
import { UserService } from "../../../services/user.service";
import { TranslateService } from "@ngx-translate/core";
import { ArticleCreateComponent } from "../create/article-create.component";
import { Article } from "../../../models/article";
import { User } from "../../../models/user";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "article-create-dialog",
  templateUrl: "../../views/article-create-dialog.html",
})
export class ArticleCreateDialogComponent extends ArticleCreateComponent
  implements OnInit {
  constructor(
    protected alertService: AlertService,
    protected articleService: ArticleService,
    protected clickService: ClickService,
    public dialogRef: MatDialogRef<ArticleCreateDialogComponent>,
    protected userService: UserService,
    protected reviewService: ReviewService,
    protected router: Router
  ) {
    super(alertService, articleService, clickService, reviewService, router);
  }

  ngOnInit() {
    this.redirect = false;
  }

  public setArticle(article: Article) {
    this.article = article;
  }

  private cancel() {
    this.dialogRef.close(null);
  }
}
