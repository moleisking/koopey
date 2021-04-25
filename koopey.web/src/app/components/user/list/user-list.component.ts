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
import { AuthService } from "../../../services/auth.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { UserService } from "../../../services/user.service";
import { SearchService } from "../../../services/search.service";
import { TranslateService } from "ng2-translate";
import { CurrencyHelper } from "../../../helpers/CurrencyHelper";
import { Config } from "../../../config/settings";
import { Location } from "../../../models/location";
import { Review } from "../../../models/review";
import { Search } from "../../../models/search";
import { User } from "../../../models/user";
import { MatDialog } from "@angular/material/dialog";

@Component({
  selector: "user-list-component",
  templateUrl: "../../views/user-list.html",
  styleUrls: ["../../styles/app-root.css"],
})
export class UserListComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private searchSubscription: Subscription = new Subscription();
  private userSubscription: Subscription = new Subscription();

  private location: Location = new Location();
  private search: Search = new Search();
  private users: Array<User> = new Array<User>();

  private columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthService,
    private clickService: ClickService,
    private router: Router,
    private sanitizer: DomSanitizer,
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
        if (!Config.system_production) {
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
        if (!Config.system_production) {
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
    this.onScreenSizeChange(null);
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

  private onScreenSizeChange(event: any) {
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

  private isAliasVisible(): boolean {
    return Config.business_model_alias;
  }

  private getCurrencySymbol(currency: string): string {
    return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
  }

  public getDistanceText(user: User): string {
    return Location.convertDistanceToKilometers(user.distance);
  }

  private gotoUserMap() {
    this.router.navigate(["/user/read/map"]);
  }

  private gotoUser(user: User) {
    this.userService.setUser(user);
    this.router.navigate(["/user/read/one"]);
  }

  private showNoResults(): boolean {
    if (!this.users || this.users.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
