import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../services/alert.service";
import { AuthenticationService } from "../../services/authentication.service";
import { UserService } from "../../services/user.service";
import { ConfirmDialogComponent } from "../confirm/confirm-dialog.component";
import { User } from "../../models/user";
import { MatDialog } from "@angular/material/dialog";

@Component({
  selector: "configuration-component",
  templateUrl: "configuration.html",
})
export class ConfigurationComponent {
  public authUser: User = new User();
  private authenticateSubscription: Subscription = new Subscription();
  private userSubscription: Subscription = new Subscription();

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    public confirmDialog: MatDialog,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.userSubscription = this.userService.readMyUser().subscribe(
      (user) => {
        this.authUser = user;
      },
      (error) => {
        this.alertService.error(<any>error);
      },
      () => {
        console.log("getMyUser success");
      }
    );
  }

  ngAfterContentInit() {}

  ngAfterViewInit() {}

  ngOnDestroy() {
    if (this.authenticateSubscription) {
      this.authenticateSubscription.unsubscribe();
    }
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  public emailChangeRequest() {
    this.router.navigate(["/user/update/email/request"]);
  }

  public passwordChangeRequest() {
    this.router.navigate(["/user/update/password/request"]);
  }

  public refreshMyUser() {
    this.userService.readMyUser().subscribe(
      (user) => {
        localStorage.setItem("alias", user.alias);
        localStorage.setItem("authenticated", user.authenticated.toString());
        localStorage.setItem("currency", user.currency);
        localStorage.setItem("id", user.id);
        localStorage.setItem("name", user.name);
        localStorage.setItem("wallets", JSON.stringify(user.wallets));
        localStorage.setItem("location", JSON.stringify(user.locations));
        console.log("localStorage.getItem(authenticated");
        console.log(localStorage.getItem("authenticated"));
      },
      (error) => {
        this.alertService.error(<any>error);
      },
      () => {}
    );
  }

  public forgottenActivationEmail() {
    this.authenticateSubscription = this.authenticateService
      .activateForgotten()
      .subscribe(
        () => {},
        (error) => {
          this.alertService.error(<any>error);
        },
        () => {}
      );
  }

  public toggleTrack() {
    this.userService.updateTrack(this.authUser.track ? false : true).subscribe(
      () => {},
      (error) => {
        this.alertService.error(<any>error);
      },
      () => {
        console.log("trackChanged updated");
      }
    );
  }

  public toggleNotify() {
    this.userService
      .updateNotify(this.authUser.notify ? false : true)
      .subscribe(
        () => {},
        (error) => {
          this.alertService.error(<any>error);
        },
        () => {}
      );
  }

  public openDeleteMyUserDialog() {
    console.log("openDeleteMyUserDialog()");
    let dialogRef = this.confirmDialog.open(ConfirmDialogComponent);
    dialogRef.afterClosed().subscribe((result) => {
      if (result == true) {
        console.log("delete user");
        this.userService.delete(this.authUser).subscribe(
          () => {
            this.router.navigate(["/login"]);
          },
          (error) => {
            this.alertService.error(<any>error);
          },
          () => {
            this.alertService.success("INFO_COMPLETE");
          }
        );
      } else if (result == false) {
        console.log("dont delete user");
      }
    });
  }
}
