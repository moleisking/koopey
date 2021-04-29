import {
  Component,
  Input,
  OnInit,
  OnDestroy,
  ElementRef,
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
import { AssetService } from "../../../services/asset.service";
import { SearchService } from "../../../services/search.service";
import { TranslateService } from "@ngx-translate/core";
import { Config } from "../../../config/settings";
//import { Fee } from "../models/fee";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { Image } from "../../../models/image";
import { Review } from "../../../models/review";
import { Search } from "../../../models/search";
import { User } from "../../../models/user";
import { TransactionHelper } from "../../../helpers/TransactionHelper";
import { MatDialog } from "@angular/material/dialog";

@Component({
  selector: "asset-list-component",
  templateUrl: "asset-list.html",
  styleUrls: ["asset-list.css"],
})
/*Note* Do not use fors as it blocks location controls*/
export class AssetListComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private assetSubscription: Subscription = new Subscription();
  private searchSubscription: Subscription = new Subscription();
  private location: Location = new Location();
  public assets: Array<Asset> = new Array<Asset>();
  private search: Search = new Search();

  public columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    private clickService: ClickService,
    public messageDialog: MatDialog,
    private assetService: AssetService,
    private router: Router,
    private sanitizer: DomSanitizer,
    private searchService: SearchService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.assetSubscription = this.assetService.getAssets().subscribe(
      (assets) => {
        this.assets = assets; //Asset.sort(assets);
      },
      (error) => {
        this.alertService.error(error);
      },
      () => {
        if (!Config.system_production) {
          console.log(this.assets);
        }
      }
    );
    this.searchSubscription = this.searchService.getSearch().subscribe(
      (search) => {
        this.search = search;
      },
      (error) => {
        this.alertService.error(error);
      },
      () => {}
    );
  }

  ngAfterContentInit() {
    this.clickService.createInstance(
      ActionIcon.MAP,
      CurrentComponent.AssetListComponent
    );
    this.clickSubscription = this.clickService
      .getAssetListClick()
      .subscribe(() => {
        this.gotoAssetMap();
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
    if (this.assetSubscription) {
      this.assetSubscription.unsubscribe();
    }
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  private convertValuePlusMargin(asset: Asset): number {
    return TransactionHelper.AssetValuePlusMargin(asset);
  }

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

  private isImageEmpty(asset: Asset) {
    if (!asset || !asset.images || asset.images.length == 0) {
      return true;
    }
    return false;
  }

  private gotoAssetMap() {
    this.router.navigate(["/asset/read/map"]);
  }

  private gotoAsset(asset: Asset) {
    this.assetService.setAsset(asset);
    this.router.navigate(["/asset/read/one"]);
  }

  public showNoResults(): boolean {
    if (!this.assets || this.assets.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
