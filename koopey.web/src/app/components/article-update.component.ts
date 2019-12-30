//Angular, Material, Libraries
import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { MdDatepickerModule, MdDatepickerIntl } from '@angular/material';
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../services/alert.service";
import { ClickService, CurrentComponent, ActionIcon } from "../services/click.service";
import { ArticleService } from "../services/article.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../services/user.service";
//Helpers
import { CurrencyHelper } from "../helpers/CurrencyHelper";
import { DateHelper } from "../helpers/DateHelper";
import { TransactionHelper } from "../helpers/TransactionHelper";
//Objects
import { Alert } from "../models/alert";
import { Bitcoin } from "../models/bitcoin";
import { Ethereum } from "../models/ethereum";
import { Article } from "../models/article";
import { User } from "../models/user";
import { Wallet } from "../models/wallet";

@Component({
    selector: "article-update-component",
    templateUrl: "../../views/article-update.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class ArticleUpdateComponent implements OnInit, OnDestroy {

    protected LOG_HEADER: string = 'ARTICLE:UPDATE';   
    private clickSubscription: Subscription;
    private articleSubscription: Subscription;
  
    protected article: Article = new Article();

    constructor(
        protected alertService: AlertService,
                private clickService: ClickService,
              private route: ActivatedRoute,
        protected router: Router,
        protected articleService: ArticleService,
        private translateService: TranslateService
    ) {     }

    ngOnInit() {
        this.articleSubscription = this.articleService.getArticle().subscribe(
            (article) => {                this.article = article;            },
            (error) => { console.log(error); },
            () => { });
           }

    ngAfterContentInit() {     
                this.clickService.createInstance(ActionIcon.PAYMENT, CurrentComponent.TransactionUpdateComponent);
                this.clickSubscription = this.clickService.getTransactionUpdateClick().subscribe(() => {
                    //Buyer completes transactions
                    this.updateArticle();
                });   
    }

    ngAfterViewInit() {      }

    ngOnDestroy() {
        if (this.articleSubscription) {
            this.articleSubscription.unsubscribe();
        }
        if (this.clickSubscription) {
            this.clickService.destroyInstance();
            this.clickSubscription.unsubscribe();
        }        
    }

   

    public getArticle() {
        this.route.params.subscribe(p => {
            let id = p["id"];
            if (id) {
                this.articleService.readArticle(id).subscribe(
                    (article) => {
                        this.article = article;
                    },
                    error => { this.alertService.error(<any>error) },
                    () => { console.log("gettransaction success") }
                );
            } else {
                this.articleSubscription = this.articleService.getArticle().subscribe(
                    (article) => {
                        this.article = article;
                    },
                    (error) => { console.log(error); },
                    () => { });
            }
        });
    }
  


    private updateArticle() {
        if (Article.isEmpty(this.article)) {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        } else {
            this.articleService.createArticle(this.article).subscribe(
                (alert : Alert) => { this.router.navigate(["/article/read/list"]) },
                (error: any) => { this.alertService.error(<any>error) },
                () => { this.alertService.success("INFO_COMPLETE") }
            );
        }
    }

   
}