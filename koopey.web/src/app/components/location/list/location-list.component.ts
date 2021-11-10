import {
  Component,
  ElementRef,
  Input,
  OnInit,
  OnDestroy,
  ViewChild,
  AfterContentInit,
  AfterViewInit,
  AfterViewChecked,
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
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { MatSort, Sort } from "@angular/material/sort";
import { LiveAnnouncer } from "@angular/cdk/a11y";

@Component({
  selector: "location-list-component",
  styleUrls: ["location-list.css"],
  templateUrl: "location-list.html",
})
export class LocationListComponent
  implements AfterViewInit, AfterViewChecked, OnInit, OnDestroy {
  private locationSubscription: Subscription = new Subscription();
  public locations: Array<Location> = new Array<Location>();

  @ViewChild("paginatorElement") paginatorElement: MatPaginator | undefined;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  public hidden!: boolean;

  displayedColumns: string[] = [
    "name",
    "type",
    "address",
    "latitude",
    "longitude",
  ];
  dataSource = new MatTableDataSource<Location>();

  constructor(
    public locationDialog: MatDialog,
    private locationService: LocationService,
    private router: Router,
    public sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    this.getMyLocations();
  }

  ngAfterViewInit() {
    this.refreshDataSource();
  }

  ngAfterViewChecked() {
    if (this.locations.length <= 10) {
      this.paginatorElement!.disabled = true;
      this.paginatorElement!.hidePageSize = true;
      this.paginatorElement!.showFirstLastButtons = false;
    }
  }

  ngOnDestroy() {
    if (this.locationSubscription) {
      this.locationSubscription.unsubscribe();
    }
  }

  public create() {
    this.router.navigate(["/location/edit/"]);
  }

  public edit(location: Location) {
    console.log(location);
    this.locationService.setLocation(location);
    this.router.navigate(["/location/edit?id=" + location.id]); //, { 'queryParams': { 'type': 'product' } }
  }

  private getMyLocations() {
    this.locationSubscription = this.locationService
      .searchBySellerAndSource()
      .subscribe(
        (locations: Array<Location>) => {
          this.locations = locations;
        },
        (error: Error) => {
          console.log(error.message);
        },
        () => {
          this.refreshDataSource();
        }
      );
  }

  private refreshDataSource() {
    this.dataSource = new MatTableDataSource<Location>(
      this.locations as Array<any>
    );
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  public openLocationDialog(Location: Location) {
    /* let dialogRef = this.locationDialog.open(LocationDialogComponent, {});
    dialogRef.componentInstance.setLocation(Location);*/
  }

  public gotoLocation(location: Location) {
    this.locationService.setLocation(location);
    this.router.navigate(["/location/read/" + location.id]);
  }
}
