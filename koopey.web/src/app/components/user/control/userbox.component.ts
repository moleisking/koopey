import { Component, Input } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Environment } from "src/environments/environment";
import { User } from "../../../models/user";

@Component({
  selector: "userbox",
  templateUrl: "userbox.html",
  styleUrls: ["userbox.css"],
})
export class UserboxComponent {
  @Input() user: User = new User();
  @Input() textVisible: boolean = false;
  @Input() imageRound: boolean = false;
  @Input() horizontal: boolean = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    public sanitizer: DomSanitizer
  ) {}

  public hasAlias(): boolean {
    if (this.user && this.user.alias && Environment.Menu.Alias) {
      return true;
    } else {
      return false;
    }
  }

  public hasImage(): boolean {
    if (this.user && this.user.avatar && this.user.avatar != "") {
      return false;
    } else {
      return true;
    }
  }

  public hasName(): boolean {
    if (this.user && this.user.name && !Environment.Menu.Alias) {
      return true;
    } else {
      return false;
    }
  }

  public isMyUser(): boolean {
    //window.location used to get id because this method is run during form load and not through subscription
    if (
      window.location.href.substr(window.location.href.lastIndexOf("/") + 1) ==
      localStorage.getItem("id")
    ) {
      return true;
    } else {
      return false;
    }
  }

  public gotoUser(user: User) {
    this.router.navigate(["/user/read/one", user.id]);
  }
}
