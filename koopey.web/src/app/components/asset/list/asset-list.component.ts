import {
  Component,
  OnInit,
  OnDestroy,
} from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { AssetService } from "../../../services/asset.service";
import { SearchService } from "../../../services/search.service";
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "src/environments/environment";
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
  styleUrls: ["asset-list.css"],
  templateUrl: "asset-list.html",
})
export class AssetListComponent implements OnInit, OnDestroy {
  private assetListSubscription: Subscription = new Subscription();
  private searchSubscription: Subscription = new Subscription();
  private location: Location = new Location();
  public assets: Array<Asset> = new Array<Asset>();
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
    private translateService: TranslateService
  ) { }

  ngOnInit() {
    this.getAssets();
  }



  ngAfterViewInit() {
    this.onScreenSizeChange();
  }

  ngOnDestroy() {
    if (this.assetListSubscription) {
      this.assetListSubscription.unsubscribe();
    }
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  private getAssets() {
    this.assetListSubscription = this.assetService.getAssets().subscribe(
      (assets: Array<Asset>) => {
        this.assets = assets; //Asset.sort(assets);
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

  public convertValuePlusMargin(asset: Asset): number {
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

  public isImageEmpty(asset: Asset) {
    if (!asset || !asset.images || asset.images.length == 0) {
      return true;
    }
    return false;
  }

  public gotoAssetMap() {
    this.router.navigate(["/asset/map"]);
  }

  public gotoAsset(asset: Asset) {
    this.assetService.setAsset(asset);
    this.router.navigate(["/asset/read"]);
  }

  public showNoResults(): boolean {
    if (!this.assets || this.assets.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
