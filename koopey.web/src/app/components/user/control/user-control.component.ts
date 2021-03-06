import { Component, Input } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { UserService } from "../../../services/user.service";
import { Alert } from "../../../models/alert";
import { Environment } from "src/environments/environment";
import { Location } from "../../../models/location";
import { Review } from "../../../models/review";
import { Tag } from "../../../models/tag";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";

@Component({
  selector: "user-control-component",
  templateUrl: "user-control.html",
  styleUrls: ["user-control.css"],
})
export class UserControlComponent {
  @Input() user: User = new User();
  @Input() textVisible: boolean = false;
  @Input() imageRound: boolean = false;
  @Input() horizontal: boolean = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer
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

  private isMyUser(): boolean {
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
