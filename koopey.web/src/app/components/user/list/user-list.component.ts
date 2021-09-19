import {
  Component,
  ElementRef,
  Input,
  OnInit,
  OnDestroy,
  ViewChild,
} from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { UserService } from "../../../services/user.service";
import { SearchService } from "../../../services/search.service";
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "src/environments/environment";
import { Location } from "../../../models/location";
import { Review } from "../../../models/review";
import { Search } from "../../../models/search";
import { User } from "../../../models/user";
import { MatDialog } from "@angular/material/dialog";

@Component({
  selector: "user-list-component",
  templateUrl: "user-list.html",
  styleUrls: ["user-list.css"],
})
export class UserListComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private searchSubscription: Subscription = new Subscription();
  private userSubscription: Subscription = new Subscription();

  private location: Location = new Location();
  private search: Search = new Search();
  public users: Array<User> = new Array<User>();

  public columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    private clickService: ClickService,
    private router: Router,
    public sanitizer: DomSanitizer,
    public messageDialog: MatDialog,
    private searchService: SearchService,
    private translateService: TranslateService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.searchSubscription = this.searchService.getSearch().subscribe(
      (search: Search) => {
        this.search = search;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {
        if (Environment.type != "production") {
          console.log(this.search);
        }
      }
    );
    this.userSubscription = this.userService.getUsers().subscribe(
      (users) => {
        this.users = users;
      },
      (error) => {
        this.alertService.error(error);
      },
      () => {
        if (Environment.type != "production") {
          console.log(this.users);
        }
      }
    );
  }

  ngAfterContentInit() {
    this.clickService.createInstance(
      ActionIcon.MAP,
      CurrentComponent.UserListComponent
    );
    this.clickSubscription = this.clickService
      .getUserListClick()
      .subscribe(() => {
        this.gotoUserMap();
      });
  }

  ngAfterViewInit() {
    this.onScreenSizeChange();
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  /*private convertValuePlusMargin(fee: Fee): number {
        return CurrencyHelper.convertValuePlusMargin(fee);
    }*/

  public onScreenSizeChange() {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth <= 512) {
      this.columns = 1;
    } else if (this.screenWidth > 512 && this.screenWidth <= 1024) {
      this.columns = 2;
    } else if (this.screenWidth > 1024 && this.screenWidth <= 2048) {
      this.columns = 3;
    } else if (this.screenWidth > 2048 && this.screenWidth <= 4096) {
      this.columns = 4;
    }
  }

  public isAliasVisible(): boolean {
    return Environment.Menu.Alias;
  }

  private gotoUserMap() {
    this.router.navigate(["/user/read/map"]);
  }

  public gotoUser(user: User) {
    this.userService.setUser(user);
    this.router.navigate(["/user/read/one"]);
  }

  public showNoResults(): boolean {
    if (!this.users || this.users.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
