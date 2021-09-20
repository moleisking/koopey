import { Injectable } from "@angular/core";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";

@Injectable()
export abstract class BaseComponent {
  constructor(public sanitizer: DomSanitizer) {}

  public getAlias(): string {
    return localStorage.getItem("alias")!;
  }

  public getAvatar(): SafeUrl {
    if (localStorage.getItem("avatar")) {
      return this.sanitizer.bypassSecurityTrustUrl(
        localStorage.getItem("avatar")!
      );
    } else {
      return "/images/default-user.svg";
    }
  }

  private getLanguage() {
    return localStorage.getItem("language")!;
  }
}
