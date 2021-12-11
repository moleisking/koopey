import { Component, OnInit, OnDestroy } from "@angular/core";
import { Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { AssetService } from "../../../services/asset.service";
import { TagService } from "../../../services/tag.service";
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "src/environments/environment";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { Tag } from "../../../models/tag";

@Component({
  selector: "user-assets-component", 
  styleUrls: ["user-assets.css"],
  templateUrl: "user-assets.html",
})
export class UserAssetsComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private assetSubscription: Subscription = new Subscription();
  public assets: Array<Asset> = new Array<Asset>();
  public columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
       private assetService: AssetService,
    private router: Router,
    public sanitizer: DomSanitizer,
    private tagService: TagService,
    private translateService: TranslateService
  ) {}

  ngOnInit() { 
 
  }

  ngAfterContentInit() {
    this.onScreenSizeChange(null);
  }

 

  ngOnDestroy() {
    if (this.assetSubscription) {
      this.assetSubscription.unsubscribe();
    }
  }

  private getAssets() {
    this.assetSubscription = this.assetService.searchByUser().subscribe(
      (assets: Array<Asset>) => {
        this.assets = assets;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  public onScreenSizeChange(event: any) {
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

  public gotoMyAsset(asset: Asset) {
    console.log(asset);
    this.assetService.setAsset(asset);
    this.router.navigate(["/asset/edit"]);
  }

  public createAsset() {
    console.log("createAsset()");
    this.router.navigate(["/asset/edit/"]); //, { 'queryParams': { 'type': 'product' } }
  }

  public showNoResults(): boolean {
    if (!this.assets || this.assets.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
