import { AlertService } from "../../../services/alert.service";
import {
  AfterViewChecked,
  AfterViewInit,
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
} from "@angular/core";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { MatSort } from "@angular/material/sort";
import { DomSanitizer } from "@angular/platform-browser";
import { Location } from "../../../models/location";
import { MatDialog } from "@angular/material/dialog";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { User } from "../../../models/user";
import { UserService } from "../../../services/user.service";
import { DistanceHelper } from "src/app/helpers/DistanceHelper";
import { LocationService } from "src/app/services/location.service";

@Component({
  selector: "user-list-component",
  styleUrls: ["user-list.css"],
  templateUrl: "user-list.html",
})
export class UserListComponent
  implements AfterViewChecked, AfterViewInit, OnInit, OnDestroy {
  private userSubscription: Subscription = new Subscription();
  public users: Array<User> = new Array<User>();
  public location!: Location;

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

  private refreshDataSource() {
    this.dataSource = new MatTableDataSource<Location>(
      this.users as Array<any>
    );
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
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
}
