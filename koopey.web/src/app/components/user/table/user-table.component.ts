import { AlertService } from "../../../services/alert.service";
import {
  AfterViewChecked,
  AfterViewInit,
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
} from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Location } from "../../../models/location";
import { MatDialog } from "@angular/material/dialog";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { MatSort } from "@angular/material/sort";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { User } from "../../../models/user";
import { UserService } from "../../../services/user.service";
import { DistanceHelper } from "../../../helpers/DistanceHelper";
import { LocationService } from "../../../services/location.service";

@Component({
  selector: "user-table-component",
    standalone: false,
  styleUrls: ["user-table.css"],
  templateUrl: "user-table.html",
})
export class UserTableComponent
  implements AfterViewChecked, AfterViewInit, OnInit, OnDestroy {
  public location!: Location;
  public users: Array<User> = new Array<User>();
  private userSubscription: Subscription = new Subscription();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild("paginatorElement") paginatorElement: MatPaginator | undefined;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = [
    "id",
    "avatar",
    "alias",
    "name",
    "latitude",
    "longitude",
    "distance",
  ];
  dataSource = new MatTableDataSource<Location>();

  constructor(
    protected alertService: AlertService,
    private locationServer: LocationService,
    public messageDialog: MatDialog,
    private router: Router,
    public sanitizer: DomSanitizer,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.locationServer.getPosition().subscribe((location: Location) => {
      this.location = location;
    });
  }

  ngAfterContentInit() {
    this.userSubscription = this.userService
      .getUsers()
      .subscribe((users: Array<User>) => {
        this.users = users;
      });
  }

  ngAfterViewChecked() {
    if (this.users.length <= 10) {
      this.paginatorElement!.disabled = true;
      this.paginatorElement!.hidePageSize = true;
      this.paginatorElement!.showFirstLastButtons = false;
    }
  }

  ngAfterViewInit() {
    this.refreshDataSource();
  }

  ngOnDestroy() {
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  public getUsers() {
    this.userSubscription = this.userService.getUsers().subscribe(
      (users: Array<User>) => {
        this.users = users;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {
        this.refreshDataSource();
      }
    );
  }

  public getDistance(latitude: number, longitude: number): string {
    if (latitude && longitude) {
      return DistanceHelper.distanceAndUnit(
        latitude,
        longitude,
        this.location.latitude,
        this.location.longitude
      );
    } else {
      return "";
    }
  }

  public gotoUser(user: User) {
    this.userService.setUser(user);
    this.router.navigate(["/user/read/" + user.id]);
  }

  private refreshDataSource() {
    this.dataSource = new MatTableDataSource<Location>(
      this.users as Array<any>
    );
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
