import { Advert } from "../models/advert";
import { Image } from "../models/image";
import { Review } from "../models/review";
import { Tag } from "../models/tag";
import { BaseModel } from "./baseModel";

export class Article extends BaseModel {
  public userId: string = "";
  public title: string = "";
  public content: string = "";
  public available: boolean = true;
  public advert: Advert = new Advert();
  public images: Array<Image> = new Array<Image>();
  public reviews: Array<Review> = new Array<Review>();
  public tags: Array<Tag> = new Array<Tag>();

  public static isEmpty(article: Article): boolean {
    if (article.title && article.content && article.userId) {
      return false;
    } else {
      return true;
    }
  }
}
