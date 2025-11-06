import { AlertService } from "../../../services/alert.service";

import { ChangeDetectionStrategy, Component, inject, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
//import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { Location } from "../../../models/location";
import { LocationService } from "../../../services/location.service";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [],
  selector: "location-read",
  standalone: true,
  styleUrls: ["location-read.css"],
  templateUrl: "location-read.html",
})
export class LocationReadComponent implements OnInit {

  protected location: Location = new Location();
  private locationSubscription: Subscription = new Subscription();
  private alertService = inject(AlertService);
  private locationService = inject(LocationService);

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
