//Angular, Material, Libraries
import { Component, OnInit, OnDestroy } from "@angular/core";
import { Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../services/alert.service";
import { AuthService } from "../services/auth.service";
import { ClickService, CurrentComponent, ActionIcon } from "../services/click.service";
import { AssetService } from "../services/asset.service";
import { TagService } from "../services/tag.service";
import { TranslateService } from "ng2-translate";
//Helpers
import { CurrencyHelper } from "../helpers/CurrencyHelper";
//Objects
import { Config } from "../config/settings";
import { Location } from "../models/location";
import { Asset } from "../models/asset";
import { Tag } from "../models/tag";

@Component({
    selector: "user-assets-component",
    templateUrl: "../../views/user-assets.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class UserAssetsComponent implements OnInit, OnDestroy {
    //Subscriptions
    private clickSubscription: Subscription;
    private assetSubscription: Subscription;
    //Objects  
    private assets: Array<Asset>;
    //Strings
    private LOG_HEADER: string = "UserAssetsComponent"
    //Numbers
    private columns: number = 1;
    private screenWidth: number = window.innerWidth;

    constructor(
        private alertService: AlertService,
        private authenticateService: AuthService,
        private clickService: ClickService,
        private assetService: AssetService,
        private router: Router,
        private sanitizer: DomSanitizer,
        private tagService: TagService,
        private translateService: TranslateService
    ) { }

    ngOnInit() {
        this.clickService.createInstance(ActionIcon.CREATE, CurrentComponent.AssetCreateComponent);
        this.clickSubscription = this.clickService.getAssetCreateClick().subscribe(() => {
            this.createAsset();
        });
    }

    ngAfterContentInit() {
        this.onScreenSizeChange(null);
    }

    ngAfterViewInit() {
        this.assetSubscription = this.assetService.readMyAssets().subscribe(
            (assets) => { this.assets = assets; },
            (error) => { this.alertService.error(error) },
            () => { if (!Config.system_production) { console.log(this.assets); } }
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
        } else if ((this.screenWidth > 512) && (this.screenWidth <= 1024)) {
            this.columns = 2;
        } else if ((this.screenWidth > 1024) && (this.screenWidth <= 2048)) {
            this.columns = 3;
        } else if ((this.screenWidth > 2048) && (this.screenWidth <= 4096)) {
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

    private getCurrencySymbol(currency: string): string {
        return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
    }

    public getDistanceText(asset: Asset): string {
        if (asset.distance ) {
            return Location.convertDistanceToKilometers(asset.distance);
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
        this.router.navigate(["/asset/update"])
    }

    private createAsset() {
        console.log("createAsset()");
        this.router.navigate(["/asset/create/"]);//, { 'queryParams': { 'type': 'product' } }
    }

    private showNoResults(): boolean {
        if (!this.assets || this.assets.length == 0) {
            return true;
        } else {
            return false;
        }
    }
}