//Angular, Material, Libraries
import { Component, OnInit, OnDestroy } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Subscription } from "rxjs/Subscription";
//Services
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { AssetService } from "../../../services/asset.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../../../services/user.service";
//Helpers
import { CurrencyHelper } from "../../../helpers/CurrencyHelper";
//Objects
import { Config } from "../../../config/settings";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { User } from "../../../models/user";
declare var google: any;

@Component({
  selector: "asset-map-component",
  templateUrl: "../../views/asset-map.html",
  styleUrls: ["../../styles/app-root.css"],
})
export class AssetMapComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription;
  private assetSubscription: Subscription;
  // private user: User = new User();
  private LOG_HEADER: string = "AssetMapComponent";
  private assets: Array<Asset>;
  private map: any;

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    private clickService: ClickService,
    private route: ActivatedRoute,
    private router: Router,
    private assetService: AssetService,
    private userService: UserService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.assetSubscription = this.assetService.getAssets().subscribe(
      (assets) => {
        console.log(assets);
        this.assets = Asset.sort(assets);
      },
      (error) => {
        console.log(error);
      },
      () => {}
    );
  }

  ngAfterContentInit() {
    this.clickService.createInstance(
      ActionIcon.LIST,
      CurrentComponent.AssetMapComponent
    );
    this.clickSubscription = this.clickService
      .getAssetMapClick()
      .subscribe(() => {
        this.gotoAssetList();
      });
  }

  ngAfterViewInit() {
    //Create map object
    var mapProp = {
      center: new google.maps.LatLng(
        Config.default_latitude,
        Config.default_longitude
      ),
      zoom: 5,
      mapTypeId: google.maps.MapTypeId.ROADMAP,
    };
    this.map = new google.maps.Map(
      document.getElementById("googleMap"),
      mapProp
    );
    //Full map object
    if (this.assets && this.assets.length > 0) {
      for (var i = 0; i <= this.assets.length; i++) {
        var asset: Asset = this.assets[i];
        var location: Location = asset.location;
        this.addMarker(
          location.latitude,
          location.longitude,
          asset.title,
          asset.id
        );
      }
    }
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

  private getCurrencySymbol(currency: string): string {
    return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
  }

  private addMarker(
    latitude: number,
    longitude: number,
    title: string,
    assetId: string
  ) {
    let myLatlng = new google.maps.LatLng(latitude, longitude); //new google.maps.LatLng(23.5454, 90.8785);
    let marker = new google.maps.Marker({
      draggable: true,
      animation: google.maps.Animation.DROP,
      position: myLatlng,
      map: this.map, //set map created here
      title: title,
    });
    marker.addListener("click", () => {
      console.log("marker.addListener click");
      this.router.navigate(["asset/read/one", assetId]);
    });
  }

  private gotoAssetList() {
    this.router.navigate(["/asset/read/list"]);
  }
}
