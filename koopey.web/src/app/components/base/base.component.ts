import { Injectable } from "@angular/core";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { User } from "src/app/models/user";

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

  public getLanguage(): string {
    if (
      (localStorage.getItem("language") !== undefined ||
        localStorage.getItem("language") !== null) &&
      localStorage.getItem("language")!.length > 0
    ) {
      return localStorage.getItem("language")!.toString();
    } else {
      return "en";
    }
  }

  public getUserIdOnly(): User {
    let user: User = new User();
    user.id = localStorage.getItem("id")!;
    return user;
  }

  public isAuthenticated() {
    if (localStorage.getItem("token") !== null) {
      return true;
    } else {
      return false;
    }
  }
}
