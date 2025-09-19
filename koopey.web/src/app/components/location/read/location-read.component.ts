import { AlertService } from "../../../services/alert.service";
import { BaseComponent } from "../../base/base.component";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
//import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { Location } from "../../../models/location";
import { LocationService } from "../../../services/location.service";

@Component({
  selector: "location-read",
    standalone: false,
  styleUrls: ["location-read.css"],
  templateUrl: "location-read.html",
})
export class LocationReadComponent extends BaseComponent implements OnInit {
  protected location: Location = new Location();
  private locationSubscription: Subscription = new Subscription();

  constructor(
    // private activatedRoute: ActivatedRoute,
    private alertService: AlertService,
    private locationService: LocationService,
    public sanitizer: DomSanitizer
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    /* this.activatedRoute.queryParams.subscribe((parameter) => {
      if (parameter["type"]) {
        this.location.type = parameter["type"];
      }
      if (parameter["id"]) {
        console.log(parameter["id"]);
        this.locationService.getLocation().subscribe((location) => {
          this.location = location;
        });
      }
    });*/
    this.getLocation();
  }

  ngOnDestroy() {
    if (this.locationSubscription) {
      this.locationSubscription.unsubscribe();
    }
  }

  private getLocation() {
    this.locationSubscription = this.locationService.getLocation().subscribe(
      (location: Location) => {
        this.location = location;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }
}
