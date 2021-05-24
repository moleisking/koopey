import { Component, OnInit } from "@angular/core";
import { UserService } from "../../services/user.service";
import { TagService } from "../../services/tag.service";
import { AlertService } from "../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";

import { User } from "../../models/user";

import { Search } from "../../models/search";
import { Tag } from "../../models/tag";
//import { DomSanitizer } from "@angular/platform-browser";

//declare let google: any;

@Component({
  selector: "category-search-component",
  templateUrl: "category-search.html",
  styleUrls: ["category-search.css"],
})
export class CategorySearchComponent implements OnInit {
  private users: Array<User> = new Array<User>();
  private tags: Array<Tag> = new Array<Tag>();
  //  private message: string;
  //public topTags = TopTags;
  private search: Search = new Search();
  private curLat: number = 0;
  private curLng: number = 0;
  //private tag: number;
  private radius: number = 5;

  constructor(
    private tagService: TagService,
    //private sanitizer: DomSanitizer,
    private userService: UserService,
    private alertService: AlertService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {}

  /*  getTags() {
         this.tagService.all().subscribe(
             tags => {
                 var language = String( Config.default_language);
                // this.tags = JSON.parse(JSON.stringify(tags).split(language).join('text'));
                 this.tags = tags;
                  for(var i = 0 ; i < this.tags.length ; i++) {                   
                     if(language == 'de')
                     {
                          this.tags[i].text = this.tags[i].de;
                     }
                     else if(language == 'en')
                     {                      
                         this.tags[i].text = this.tags[i].en;
                     }                  
                     else if(language == 'es')
                     {
                          this.tags[i].text = this.tags[i].es;
                     }
                      else if(language == 'fr')
                     {
                          this.tags[i].text = this.tags[i].fr;
                     }
                       else if(language == 'it')
                     {
                          this.tags[i].text = this.tags[i].it;
                     }
                       else if(language == 'pt')
                     {
                          this.tags[i].text = this.tags[i].pt;
                     }
                       else if(language == 'zh')
                     {
                          this.tags[i].text = this.tags[i].zh;
                     }
                 }   
             },
             error => { console.log(error) },
             () => this.translateService.get("COMPLETE").subscribe((res: string) => console.log(res))
         );
         console.log("Tag Size:" + TagService.length);
     }*/

  findTag(value: any): Tag {
    for (var i = 0; i < this.tags.length; i++) {
      if (value == this.tags[i].id) {
        return this.tags[i];
      }
    }
    return new Tag();
  }

  /* findUsers(tagid : string) {
         if (!this.curLat || !this.curLng) {
             this.alertService.error("LOCATION_NOT_VALID")
         }        else {
             //console.log(this.tagService.getStringOfArray(this.tagsActive));
             var tagsActive: any = [];
             tagsActive.push({id:tagid});
 
               var parameters = {
                 curLat : this.curLat,
                 curLng : this.curLng,
                 radius : this.radius,
              //   tagIds : [tagid],
                 tags : this.findTag(tagid)
             }
             this.userService.findUsers(search).subscribe(
                 (users) => {
                     this.users = users;
                     if (users.length === 0) {
                         this.alertService.info("SEARCH_NO_RESULTS");
                     }
                     else {
                        this.alertService.success("SEARCH_COMPLETE");
                     }
                 },
                 error => { this.alertService.error(<any>error) },
                 () => { this.translateService.get("SEARCH_COMPLETE").subscribe((res: string) => console.log(res)) }
             );
         }
     }*/
}
