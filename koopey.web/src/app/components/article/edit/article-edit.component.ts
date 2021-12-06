import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { ArticleService } from "../../../services/article.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { DateHelper } from "../../../helpers/DateHelper";
import { TransactionHelper } from "../../../helpers/TransactionHelper";
import { Alert } from "../../../models/alert";
import { Article } from "../../../models/article";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";

@Component({
  selector: "article-edit",
  styleUrls: ["article-edit.css"],
  templateUrl: "article-edit.html",
  
})
export class ArticleEditComponent implements OnInit, OnDestroy {
  public article: Article = new Article();
  private articleSubscription: Subscription = new Subscription();

  constructor(
    protected alertService: AlertService,
    protected articleService: ArticleService,
    protected route: ActivatedRoute,
    protected router: Router   
  ) 
  {}

  ngOnInit() {
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

  ngAfterContentInit() {
    /*  this.clickService.createInstance(
      ActionIcon.PAYMENT,
      CurrentComponent.TransactionUpdateComponent
    );
    this.clickSubscription = this.clickService
      .getTransactionUpdateClick()
      .subscribe(() => {
        //Buyer completes transactions
        this.update();
      });*/
  }

  ngAfterViewInit() {}

  ngOnDestroy() {
    if (this.articleSubscription) {
      this.articleSubscription.unsubscribe();
    }
    /* if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }*/
  }

  public getArticle() {
    this.route.params.subscribe((p) => {
      let id = p["id"];
      if (id) {
        this.articleSubscription =  this.articleService.readArticle(id).subscribe(
          (article :  Article) => {
            this.article = article;
          },
          (error : Error) => {
            this.alertService.error(error.message);
          }
        );
      } else {
        this.articleSubscription = this.articleService.getArticle().subscribe(
          (article:  Article) => {
            this.article = article;
          },
          (error: Error) => {
            this.alertService.error(error.message);
          }
        );
      }
    });
  }

  private update() {
    if (Article.isEmpty(this.article)) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.articleService.create(this.article).subscribe(
        (alert: String) => {
          this.router.navigate(["/article/read/list"]);
        },
        (error: any) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.alertService.success("INFO_COMPLETE");
        }
      );
    }
  }
}
