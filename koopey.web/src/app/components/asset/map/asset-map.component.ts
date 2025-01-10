import { Component, OnInit, OnDestroy } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";

import { AssetService } from "../../../services/asset.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { Environment } from "./../../../../environments/environment";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { User } from "../../../models/user";
import { ModelHelper } from "./../../../helpers/ModelHelper";
import { LocationType } from "./../../../models/type/LocationType";
import { LocationService } from "./../../../services/location.service";
declare var google: any;

@Component({
  selector: "asset-map-component",
  templateUrl: "asset-map.html",
  styleUrls: ["asset-map.css"],
})
export class AssetMapComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private assetSubscription: Subscription = new Subscription();

  public assets: Array<Asset> = new Array<Asset>();
  private map: any;

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,

    private route: ActivatedRoute,
    private router: Router,
    private assetService: AssetService,
    private locationService: LocationService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.assetSubscription = this.assetService.getAssets().subscribe(
      (assets) => {
        console.log(assets);
        this.assets = assets; // Asset.sort(assets);
      },
      (error) => {
        console.log(error);
      },
      () => {}
    );
  }

  ngAfterContentInit() {}

  ngAfterViewInit() {
    //Create map object
    this.createMap();
    //Full map object
    this.fillMap();
  }

  ngOnDestroy() {
    if (this.assetSubscription) {
      this.assetSubscription.unsubscribe();
    }
  }

  private addMarker(
    latitude: number,
    longitude: number,
    title: string,
    assetId: string
  ): void {
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

  private createMap(): void {
    var mapProp = {
      center: new google.maps.LatLng(
        Environment.Default.Latitude,
        Environment.Default.Longitude
      ),
      zoom: 5,
      mapTypeId: google.maps.MapTypeId.ROADMAP,
    };
    this.map = new google.maps.Map(
      document.getElementById("googleMap"),
      mapProp
    );
  }

  private fillMap(): void {
    if (this.assets && this.assets.length > 0) {
      for (var i = 0; i <= this.assets.length; i++) {
        var asset: Asset = this.assets[i];
        var location: Location = ModelHelper.find(
          asset.sources,
          LocationType.Position
        );
        this.addMarker(
          location.latitude,
          location.longitude,
          asset.name,
          asset.id
        );
      }
    }
  }

  private gotoAssetList() {
    this.router.navigate(["/asset/read/list"]);
  }
}
