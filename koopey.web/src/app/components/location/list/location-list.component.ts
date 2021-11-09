import {
  Component,
  ElementRef,
  Input,
  OnInit,
  OnDestroy,
  ViewChild,
  AfterContentInit,
} from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { MatDialog, MatDialogRef } from "@angular/material/dialog";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { LocationService } from "../../../services/location.service";
//import { LocationDialogComponent } from "../dialog/location-dialog.component";

import { Location } from "../../../models/location";

@Component({
  selector: "location-list-component",
  templateUrl: "location-list.html",
  styleUrls: ["location-list.css"],
})
export class LocationListComponent
  implements AfterContentInit, OnInit, OnDestroy {
  private locationSubscription: Subscription = new Subscription();
  public locations: Array<Location> = new Array<Location>();
  public columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private router: Router,
    public sanitizer: DomSanitizer,
    private translateService: TranslateService,
    private locationService: LocationService,
    public locationDialog: MatDialog
  ) {}

  ngOnInit() {
    this.getMyLocations();
  }

  ngAfterContentInit() {
    this.onScreenSizeChange();
  }

  ngOnDestroy() {
    if (this.locationSubscription) {
      this.locationSubscription.unsubscribe();
    }
  }

  public create() {
    this.router.navigate(["/location/edit/"]); //, { 'queryParams': { 'type': 'product' } }
  }

  private getMyLocations() {
    this.locationSubscription = this.locationService
      .searchBySellerAndSource()
      .subscribe(
        (locations: Array<Location>) => {
          console.log(locations);
          this.locations = locations;
        },
        (error: Error) => {
          console.log(error.message);
        },
        () => {}
      );
  }

  public openLocationDialog(Location: Location) {
    /* let dialogRef = this.locationDialog.open(LocationDialogComponent, {});
    dialogRef.componentInstance.setLocation(Location);*/
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

  public isEmpty(Location: Location) {
    if (!location) {
      return true;
    } else {
      return false;
    }
  }

  public gotoLocation(location: Location) {
    this.locationService.setLocation(location);
    this.router.navigate(["/location/read/" + location.id]);
  }

  public showNoResults(): boolean {
    if (!this.locations || this.locations.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
