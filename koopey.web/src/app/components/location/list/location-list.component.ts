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
import { Environment } from "../../../../environments/environment";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
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

  ngOnInit() {}

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

  private getLocations() {
    this.locationSubscription = this.locationService
      .readMyLocations()
      .subscribe(
        (Locations: Array<Location>) => {
          console.log(Locations);
          this.locations = Locations;
        },
        (error: Error) => {
          console.log(error);
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

  public gotoLocation(Location: Location) {
    this.locationService.setLocation(Location);
    this.router.navigate(["/location/read/one"]);
  }

  public showNoResults(): boolean {
    if (!this.locations || this.locations.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
