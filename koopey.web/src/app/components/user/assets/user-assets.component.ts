import { Component, OnInit, OnDestroy } from "@angular/core";
import { Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { AssetService } from "../../../services/asset.service";
import { TagService } from "../../../services/tag.service";
import { TranslateService } from "@ngx-translate/core";
import { Config } from "../../../config/settings";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { Tag } from "../../../models/tag";

@Component({
  selector: "user-assets-component",
  templateUrl: "user-assets.html",
  styleUrls: ["user-assets.css"],
})
export class UserAssetsComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private assetSubscription: Subscription = new Subscription();
  public assets: Array<Asset> = new Array<Asset>();
  private columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    private clickService: ClickService,
    private assetService: AssetService,
    private router: Router,
    private sanitizer: DomSanitizer,
    private tagService: TagService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.AssetCreateComponent
    );
    this.clickSubscription = this.clickService
      .getAssetCreateClick()
      .subscribe(() => {
        this.createAsset();
      });
  }

  ngAfterContentInit() {
    this.onScreenSizeChange(null);
  }

  ngAfterViewInit() {
    this.assetSubscription = this.assetService.readUserAssets().subscribe(
      (assets: Array<Asset>) => {
        this.assets = assets;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {
        if (!Config.system_production) {
          console.log(this.assets);
        }
      }
    );
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
    if (this.assetSubscription) {
      this.assetSubscription.unsubscribe();
    }
  }

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

  private hasAssetImage(asset: Asset) {
    if (asset.images.length > 0) {
      return true;
    } else {
      return false;
    }
  }

  /*public getFee(asset: Asset): Fee {
        if (asset && asset.fees) {
            if (asset.fees.length == 0) {
                return asset.fees[0];
            } else if (asset && asset.fees && asset.fees.length > 0 && Fee.containsCurrencyAndPeriod(asset.fees, this.authenticateService.getLocalCurrency(), 'once')) {
                return Fee.readCurrency(asset.fees, this.authenticateService.getLocalCurrency());
            } else if (asset.fees.length > 0) {
                return asset.fees[0];
            }
        }
    }*/

  private gotoMyAsset(asset: Asset) {
    console.log(asset);
    this.assetService.setAsset(asset);
    this.router.navigate(["/asset/update"]);
  }

  private createAsset() {
    console.log("createAsset()");
    this.router.navigate(["/asset/create/"]); //, { 'queryParams': { 'type': 'product' } }
  }

  private showNoResults(): boolean {
    if (!this.assets || this.assets.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
