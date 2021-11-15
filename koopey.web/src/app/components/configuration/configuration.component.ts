import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../services/alert.service";
import { AuthenticationService } from "../../services/authentication.service";
import { UserService } from "../../services/user.service";
import { ConfirmDialogComponent } from "../confirm/confirm-dialog.component";
import { User } from "../../models/user";
import { MatDialog } from "@angular/material/dialog";
import { MatSlideToggleChange } from "@angular/material/slide-toggle";

@Component({
  selector: "configuration-component",
  templateUrl: "configuration.html",
})
export class ConfigurationComponent implements OnInit {
  public user: User = new User();
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
    this.refreshMyUser();
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
      (user: User) => {
        localStorage.setItem("alias", user.alias);
        localStorage.setItem("verify", String(user.verify));
        localStorage.setItem("currency", user.currency);
        localStorage.setItem("id", user.id);
        localStorage.setItem("name", user.name);
        localStorage.setItem("wallets", JSON.stringify(user.wallets));
        localStorage.setItem("latitude", user.latitude.toString());
        localStorage.setItem("language", user.language);
        localStorage.setItem("longitude", user.longitude.toString());
        localStorage.setItem("address", user.address);
        localStorage.setItem("cookie", String(user.cookie));
        localStorage.setItem("track", String(user.track));
        localStorage.setItem("notify", String(user.notify));
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  public forgottenActivationEmail() {
    this.authenticateSubscription = this.authenticateService
      .activateForgotten()
      .subscribe(
        () => {},
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
  }

  public toggleTrack(event: MatSlideToggleChange) {
    this.userService.updateTrack(event.checked).subscribe(
      () => {
        localStorage.setItem("track", String(event.checked));
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  public toggleNotify(event: MatSlideToggleChange) {
    this.userService.updateNotify(event.checked).subscribe(
      () => {
        localStorage.setItem("notify", String(event.checked));
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  public openDeleteMyUserDialog() {
    console.log("openDeleteMyUserDialog()");
    let dialogRef = this.confirmDialog.open(ConfirmDialogComponent);
    dialogRef.afterClosed().subscribe((result) => {
      if (result == true) {
        this.userService.delete(this.user).subscribe(
          () => {
            this.router.navigate(["/login"]);
          },
          (error: Error) => {
            this.alertService.error(error.message);
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
