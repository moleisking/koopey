import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { DomSanitizer, SafeHtml } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { ReviewCreateDialogComponent } from "../../review/create/dialog/review-create-dialog.component";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { ArticleService } from "../../../services/article.service";
import { ReviewService } from "../../../services/review.service";
import { SearchService } from "../../../services/search.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { Alert } from "../../../models/alert";
import { Location } from "../../../models/location";
import { Article } from "../../../models/article";
import { Review, ReviewType } from "../../../models/review";
import { Search } from "../../../models/search";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";
import { MatDialog } from "@angular/material/dialog";

@Component({
  selector: "article-read-component",
  templateUrl: "article-read.html",
  styleUrls: ["article-read.css"],
})
export class ArticleReadComponent implements OnInit, OnDestroy {
  private authSubscription: Subscription = new Subscription();
  private articleSubscription: Subscription = new Subscription();
  private reviewSubscription: Subscription = new Subscription();
  public article: Article = new Article();
  // private review: Review = new Review();
  private authUser: User = new User();
  private user: User = new User();
  public permission: boolean = false;

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    public messageDialog: MatDialog,
    public mobileDialog: MatDialog,
    private articleService: ArticleService,
    public reviewDialog: MatDialog,
    public transactionDialog: MatDialog,
    private reviewService: ReviewService,
    private searchService: SearchService,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private translateService: TranslateService,
    private transactionService: TransactionService
  ) {}

  ngOnInit() {
    //Load authorized user
    this.authUser = this.authenticationService.getLocalUser();
    //Load asset
    this.route.params.subscribe((p) => {
      let id = p["id"];
      if (id) {
        this.articleSubscription = this.articleService
          .readArticle(id)
          .subscribe(
            (article) => {
              this.article = article;
            },
            (error) => {
              this.alertService.error(<any>error);
            },
            () => {}
          );
      } else {
        this.articleSubscription = this.articleService.getArticle().subscribe(
          (article) => {
            this.article = article;
          },
          (error) => {
            console.log(error);
          },
          () => {}
        );
      }
    });
  }

  ngAfterContentInit() {
    this.reviewSubscription = this.reviewService
      .readArticleReviews(this.article)
      .subscribe(
        (reviews) => {
          this.article.reviews = reviews;
        },
        (error) => {
          console.log(error);
        },
        () => {}
      );
  }

  ngAfterViewInit() {
    this.checkPermissions();
  }

  ngOnDestroy() {
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
    if (this.reviewSubscription) {
      this.reviewSubscription.unsubscribe();
    }
  }

  private sanitize(html: string): SafeHtml {
    //<div [innerHTML]="authUser.description"></div>
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  private checkPermissions(): boolean {
    if (!this.article || !this.user || !this.authUser) {
      this.permission = false;
      return false;
    } else if (User.equals(this.user, this.authUser)) {
      this.alertService.error("ERROR_OWN_USER");
      this.permission = false;
      return false;
    } else if (!User.isAuthenticated(this.authUser)) {
      this.alertService.error("ERROR_NOT_ACTIVATED");
      console.log(this.authUser);
      this.permission = false;
      return false;
    } else if (!User.isLegal(this.authUser)) {
      this.alertService.error("ERROR_NOT_LEGAL");
      this.permission = false;
      return false;
    } else {
      this.permission = true;
      return true;
    }
  }

  private openReviewDialog() {
    if (this.checkPermissions()) {
      var review: Review = new Review();
      review.type = ReviewType.Stars;
      review.articleId = this.article.id;
      review.judgeId = JSON.parse(localStorage.getItem("id")!);
      this.reviewService.setReview(review);
      let dialogRef = this.reviewDialog.open(ReviewCreateDialogComponent, {});
      // height: '512px'
      // dialogRef.componentInstance.setUser(this.asset.user);
      // dialogRef.componentInstance.setArticle(this.article);
    }
  }
}
