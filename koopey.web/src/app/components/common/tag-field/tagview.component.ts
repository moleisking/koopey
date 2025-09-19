import { Component, Input } from "@angular/core";
import { Tag } from "../../../models/tag";
import { Environment } from "../../../../environments/environment";

@Component({
    selector: "tagview",
      standalone: false,
    styleUrls: ["tagview.css"],
    templateUrl: "tagview.html",
  })
  export class TagviewComponent  {
    
    @Input() tags: Array<Tag> = new Array<Tag>();

    public getTagText(tag: Tag): string {
        let language =
          localStorage.getItem("language") != null
            ? localStorage.getItem("language")
            : Environment.Default.Language;
        if (language == "de") {
          return tag.de;
        } else if (language == "cn") {
          return tag.cn;
        } else if (language == "en") {
          return tag.en;
        } else if (language == "es") {
          return tag.es;
        } else if (language == "fr") {
          return tag.fr;
        } else if (language == "it") {
          return tag.it;
        } else if (language == "pt") {
          return tag.pt;
        } else if (language == "zh") {
          return tag.zh;
        } else {
          return tag.en;
        }
      }
  }