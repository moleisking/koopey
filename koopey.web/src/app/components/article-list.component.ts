//Angular, Material, Libraries
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../services/alert.service";
import { ClickService, CurrentComponent, ActionIcon } from "../services/click.service";
import { ArticleService } from "../services/article.service";
import { TranslateService } from "ng2-translate";
//Objects
import { Article } from "../models/article";
import { Config } from "../config/settings";
import { Review } from "../models/review";

@Component({
    selector: "article-list-component",
    templateUrl: "../../views/articlew-list.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class ArticleListComponent implements OnInit {
    //Controls
    private clickSubscription: Subscription;
    private transactionSubscription: Subscription;
    //Objects   
    private LOG_HEADER: string = 'ARTICLE:LIST';
    private articles: Array<Article> = new Array<Article>();
    //Numbers
    private columns: number = 1;
    private screenWidth: number = window.innerWidth;

    constructor(
        private alertService: AlertService,
        private clickService: ClickService,
        private articleService: ArticleService,
        private router: Router,
        private translateService: TranslateService
    ) { }

    ngOnInit() { }

    ngAfterContentInit() {
        this.onScreenSizeChange(null);
    }

    ngAfterViewInit() { 
        
    }  

    ngOnDestroy() { }

    private onScreenSizeChange(event: any) {
        this.screenWidth = window.innerWidth;
        if (this.screenWidth <= 512) {
            this.columns = 1;
        } else if ((this.screenWidth > 512) && (this.screenWidth <= 1024)) {
            this.columns = 2;
        } else if ((this.screenWidth > 1024) && (this.screenWidth <= 2048)) {
            this.columns = 3;
        } else if ((this.screenWidth > 2048) && (this.screenWidth <= 4096)) {
            this.columns = 4;
        }
    }

    private gotoArticleRead(article: Article) {
        this.articleService.setArticle(article);
        this.router.navigate(["/article/read/one"])
    } 
}
