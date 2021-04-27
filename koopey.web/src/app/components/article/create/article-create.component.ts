import {
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";

import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { ReviewService } from "../../../services/review.service";
import { ArticleService } from "../../../services/article.service";
//import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { Alert } from "../../../models/alert";
import { User } from "../../../models/user";
import { Article } from "../../../models/article";
import { Review, ReviewType } from "../../../models/review";

@Component({
  selector: "article-create",
  templateUrl: "article-create.html",
})
export class ArticleCreateComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();

  private articleSubscription: Subscription = new Subscription();
  public article: Article = new Article();
  protected redirect: boolean = true;
  //@Input() type: ReviewType;

  constructor(
    protected alertService: AlertService,
    protected articleService: ArticleService,
    protected clickService: ClickService,
    protected reviewService: ReviewService,
    protected router: Router
  ) {}

  ngOnInit() {
    this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.ArticleCreateComponent
    );
    this.clickSubscription = this.clickService
      .getUserCreateClick()
      .subscribe(() => {
        this.createArticle();
      });
  }

  ngAfterContentInit() {
    this.article.userId = JSON.parse(localStorage.getItem("id")!);
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
  }

  public setArticle(article: Article) {
    if (!Article.isEmpty(article)) {
      this.article = article;
    }
  }

  private handleThumbUpClick() {
    //  this.review.value = 100;
    // this.createReview();
  }

  private handleThumbDownClick() {
    //  this.review.value = 0;
    //  this.createReview();
  }

  private handleStarChange(value: number) {
    //  this.review.value = value;
    //  this.createReview();
  }

  private createArticle() {
    if (this.article) {
      this.articleService.create(this.article).subscribe(
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
