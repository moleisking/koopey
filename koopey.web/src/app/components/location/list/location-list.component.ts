import { AlertService } from "src/app/services/alert.service";
import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  AfterViewInit,
  AfterViewChecked,
} from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { LocationService } from "../../../services/location.service";
import { Location } from "../../../models/location";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { MatSort } from "@angular/material/sort";
import { OperationType } from "src/app/models/type/OperationType";

@Component({
  selector: "location-list",
  styleUrls: ["location-list.css"],
  templateUrl: "location-list.html",
})
export class LocationListComponent
  implements AfterViewChecked, AfterViewInit, OnInit, OnDestroy {
  private locationSubscription: Subscription = new Subscription();
  public locations: Array<Location> = new Array<Location>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild("paginatorElement") paginatorElement: MatPaginator | undefined;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = [
    "name",
    "type",
    "address",
    "latitude",
    "longitude",
  ];
  dataSource = new MatTableDataSource<Location>();

  constructor(
    protected alertService: AlertService,
    private locationService: LocationService,
    private router: Router
  ) {}

  ngOnInit() {
    this.getLocations();
  }

  ngAfterViewChecked() {
    if (this.locations && this.locations.length <= 10) {
      this.paginatorElement!.disabled = true;
      this.paginatorElement!.hidePageSize = true;
      this.paginatorElement!.showFirstLastButtons = false;
    }
  }

  ngAfterViewInit() {
    this.refreshDataSource();
  }

  ngOnDestroy() {
    if (this.locationSubscription) {
      this.locationSubscription.unsubscribe();
    }
  }

  public create() {
    this.locationService.setType(OperationType.Create);
    this.router.navigate(["/location/edit"]);
  }

  public edit(location: Location) {
    this.locationService.setLocation(location);
    this.locationService.setType(OperationType.Update);
    this.router.navigate(["/location/edit"]);
  }

  private getLocations() {
    this.locationSubscription = this.locationService
      .searchBySellerAndSource()
      .subscribe(
        (locations: Array<Location>) => {
          this.locations = locations;
        },
        (error: Error) => {
          this.alertService.error(error.message);
        },
        () => {
          this.refreshDataSource();
        }
      );
  }

  private refreshDataSource() {
    if (this.locations) {
      this.dataSource = new MatTableDataSource<Location>(
        this.locations as Array<any>
      );
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }
  }
}
