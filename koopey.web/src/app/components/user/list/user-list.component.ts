import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { AssetService } from "../../../services/asset.service";
import {
  Component,
  OnInit,
  OnDestroy,
  ChangeDetectionStrategy,
} from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { SearchService } from "../../../services/search.service";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { Search } from "../../../models/search";
import { User } from "../../../models/user";

import { MatDialog } from "@angular/material/dialog";
import { UserService } from "../../../services/user.service";
import { MatListModule } from "@angular/material/list";
import { MatCardModule } from "@angular/material/card";
import { MatGridListModule } from "@angular/material/grid-list";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [MatCardModule, MatGridListModule],
  selector: "user-list",
  standalone: true,
  styleUrls: ["user-list.css"],
  templateUrl: "user-list.html",
})
export class UserListComponent implements OnInit, OnDestroy {
  private userListSubscription: Subscription = new Subscription();
  private searchSubscription: Subscription = new Subscription();
  private location: Location = new Location();
  public users: Array<User> = new Array<User>();
  private search: Search = new Search();

  public columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    public messageDialog: MatDialog,
    private assetService: AssetService,
    private router: Router,
    public sanitizer: DomSanitizer,
    private searchService: SearchService,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.getUsers();
  }



  ngAfterViewInit() {
    this.onScreenSizeChange();
  }

  ngOnDestroy() {
    if (this.userListSubscription) {
      this.userListSubscription.unsubscribe();
    }
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  private getUsers() {
    this.userListSubscription = this.userService.getUsers().subscribe(
      (users: Array<User>) => {
        this.users = users; //Asset.sort(assets);
        console.log(users);
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
    this.searchSubscription = this.searchService.getSearch().subscribe(
      (search) => {
        this.search = search;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  /* public convertValuePlusMargin(asset: user): number {
     return userHelper.AssetValuePlusMargin(asset.asset);
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

  public gotoAssetMap() {
    this.router.navigate(["/user/map"]);
  }

  public gotoAsset(asset: Asset) {
    this.assetService.setAsset(asset);
    this.router.navigate(["/user/read"]);
  }

  public showNoResults(): boolean {
    if (!this.users || this.users.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
