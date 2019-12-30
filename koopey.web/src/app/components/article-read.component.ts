//Core
import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { DomSanitizer , SafeHtml } from "@angular/platform-browser";
import {
    MaterialModule, MdIconModule, MdIconRegistry, MdInputModule,
    MdTextareaAutosize, MdDialog, MdDialogRef
} from "@angular/material"
import { Subscription } from 'rxjs/Subscription';
//Components
import { ReviewCreateDialogComponent } from "./review-create-dialog.component";
//Services
import { AlertService } from "../services/alert.service";
import { AuthService } from "../services/auth.service";
import { ArticleService } from "../services/article.service";
import { ReviewService } from "../services/review.service";
import { SearchService } from "../services/search.service";
import { TransactionService } from "../services/transaction.service";
import { TranslateService } from "ng2-translate";
//Helpers
import { CurrencyHelper } from "../helpers/CurrencyHelper";
//Objects
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Location } from "../models/location";
import { Article } from "../models/article";
import { Review, ReviewType } from "../models/review";
import { Search } from "../models/search";
import { User } from "../models/user";
import { Wallet } from "../models/wallet";

@Component({
    selector: "article-read-component",
    templateUrl: "../../views/article-read.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class ArticleReadComponent implements OnInit, OnDestroy {

    private static LOG_HEADER: string = 'ArticleReadComponent';
 
    private authSubscription: Subscription;
    private articleSubscription: Subscription;
    private reviewSubscription: Subscription;
      private article: Article = new Article();
    // private review: Review = new Review();
    private authUser: User = new User();
    private user: User = new User();
      private permission: boolean = false;

    constructor(
        private alertService: AlertService,
        private authService: AuthService,
        public messageDialog: MdDialog,
        public mobileDialog: MdDialog,
        private articleService: ArticleService,
        public reviewDialog: MdDialog,
        public transactionDialog: MdDialog,
        private reviewService: ReviewService,
        private searchService: SearchService,
        private route: ActivatedRoute,
        private sanitizer: DomSanitizer,
        private translateService: TranslateService,
        private transactionService: TransactionService
    ) { }

    ngOnInit() {
        //Load authorized user
        this.authUser = this.authService.getLocalUser()
              //Load asset
        this.route.params.subscribe(p => {
            let id = p["id"];
            if (id) {               
                this.articleSubscription = this.articleService.readArticle(id).subscribe(
                    (article) => {                        this.article = article;                       },
                    (error) => { this.alertService.error(<any>error) },
                    () => {                   }
                );
            } else {             
                this.articleSubscription = this.articleService.getArticle().subscribe(
                    (article) => {                        this.article = article;                    },
                    (error) => { console.log(error); },
                    () => {                     });
            }
        });
    }

    ngAfterContentInit() {
        this.reviewSubscription = this.reviewService.readArticleReviews(this.article).subscribe(
            (reviews) => { this.article.reviews = reviews; },
            (error) => { console.log(error); },
            () => { }
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
            console.log(this.authUser)
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
            review.judgeId = localStorage.getItem("id");
            this.reviewService.setReview(review);
            let dialogRef = this.reviewDialog.open(ReviewCreateDialogComponent, {
               
            });
            // height: '512px'
            // dialogRef.componentInstance.setUser(this.asset.user);
           // dialogRef.componentInstance.setArticle(this.article);
        }
    }   
}
