const SHA256 = require("crypto-js/sha256");
import { Advert } from "../models/advert";
import { UUID } from 'angular2-uuid';
import { Image } from "../models/image";
import { Review } from "../models/review";
import { Tag } from "../models/tag";

export class Article {
    public id: string = UUID.UUID();
    public type: string = "";
    public userId: string;
    public title: string;
    public content: string;
    public hash: string;
    public available: boolean = true;
    public advert: Advert = new Advert();
    public images: Array<Image> = new Array<Image>();
    public reviews: Array<Review> = new Array<Review>();
    public tags: Array<Tag> = new Array<Tag>();
    public createtimestamp: number = Date.now();
    public readtimestamp: number;
    public updatetimestamp: number;
    public deletetimestamp: number;
  

    public static isEmpty(article: Article): boolean {
        if (article.title
            && article.content
            && article.userId) {
            return false;
        } else {
            return true;
        }
    }

    public static contains(articles: Array<Article>, article: Article): boolean {
        if (articles && articles.length > 0) {
            for (var i = 0; i < articles.length; i++) {
                //Exclude current fee
                if (articles[i] &&
                    articles[i].title == article.title &&
                    articles[i].title == article.userId) {
                    //Current item is not unique                     
                    return true;
                } else if (i == articles.length - 1) {
                    //Last item and unique  
                    return false;
                }
            }
        } else {
            return false;
        }
    }


    public static create(articles: Array<Article>, article: Article): Array<Article> {
        if (articles.length == 0 || !Article.contains(articles, article)) {
            articles.push(article);
            return articles;
        } else {
            return articles;
        }
    }

    public static read(articles: Array<Article>, article: Article): Article {
        if (articles && articles.length > 0) {
            for (var i = 0; i < articles.length; i++) {
                if (articles[i] &&
                    articles[i].id == article.id) {
                    return articles[i];
                }
            }
        }
    }

    public static update(articles: Array<Article>, article: Article): Array<Article> {
        if (articles && articles.length > 0) {
            for (var i = 0; i < articles.length; i++) {
                if (articles[i] &&
                    articles[i].id == article.id) {
                    articles[i] = article;
                    return articles;
                }
            }
        }
    }

    public static delete(articles: Array<Article>, article: Article): Array<Article> {
        if (articles && articles.length > 0) {
            for (var i = 0; i < articles.length; i++) {
                if (articles[i] &&
                    articles[i].id == article.id) {
                    articles.splice(i, 1);
                    return articles;
                }
            }
        }
    }
}
