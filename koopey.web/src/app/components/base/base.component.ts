import { Injectable } from "@angular/core";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";

@Injectable()
export abstract class BaseComponent {

  constructor(public sanitizer: DomSanitizer) {}

  public getAlias(): string {
    return localStorage.getItem("alias")!;
  }

  public getAuthenticationUserId(): string {
    return localStorage.getItem("id")!;
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

  public getCurrency(): string {
    if (
      localStorage.getItem("currency") !== undefined &&
      localStorage.getItem("currency") !== null &&
      localStorage.getItem("currency")!.length !== null &&
      localStorage.getItem("currency")!.length > 0
    ) {
      return localStorage.getItem("currency")!.toString();
    } else {
      return "eur";
    }
  }

  public getLanguage(): string {
    if (
      localStorage.getItem("language") !== undefined &&
      localStorage.getItem("language") !== null &&
      localStorage.getItem("language")!.length !== null &&
      localStorage.getItem("language")!.length > 0
    ) {
      return localStorage.getItem("language")!.toString();
    } else {
      return "en";
    }
  }

  public isAuthenticated() {
    if (localStorage.getItem("token") !== null) {
      return true;
    } else {
      return false;
    }
  }
}
