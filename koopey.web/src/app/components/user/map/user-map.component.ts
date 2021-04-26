import { Component, OnInit, OnDestroy } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { AssetService } from "../../../services/asset.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { Config } from "../../../config/settings";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { User } from "../../../models/user";
declare var google: any;

@Component({
  selector: "user-map-component",
  templateUrl: "user-map.html",
  styleUrls: ["user-map.css"],
})
export class UserMapComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private userSubscription: Subscription = new Subscription();
  private users: Array<User> = new Array<User>();
  private map: any;

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.userSubscription = this.userService.getUsers().subscribe(
      (users) => {
        this.users = users;
      },
      (error) => {
        console.log(error);
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
      ActionIcon.LIST,
      CurrentComponent.UserMapComponent
    );
    this.clickSubscription = this.clickService
      .getUserMapClick()
      .subscribe(() => {
        this.gotoUserList();
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
    if (this.users && this.users.length > 0) {
      for (var i = 0; i <= this.users.length; i++) {
        var user: User = this.users[i];
        var location: Location = user.location; //Location.read(user.locations, "abode");
        this.addMarker(
          location.latitude,
          location.longitude,
          user.alias,
          user.id
        );
      }
    }
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  private addMarker(
    latitude: number,
    longitude: number,
    title: string,
    userId: string
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
      this.router.navigate(["user/read/user", userId]);
    });
  }

  private gotoUserList() {
    this.router.navigate(["/user/read/list"]);
  }
}
