const SHA256 = require("crypto-js/sha256");
import { UUID } from "angular2-uuid";

export class Tag {
  public id: string = UUID.UUID();
  public type: string = "";
  public hash: string = "";
  public cn: string = "";
  public de: string = "";
  public en: string = "";
  public es: string = "";
  public fr: string = "";
  public it: string = "";
  public pt: string = "";
  public zh: string = "";

  public static getText(tag: Tag, language: string): string {
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
