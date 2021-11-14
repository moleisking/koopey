import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { ReviewService } from "../../../services/review.service";
import { UserService } from "../../../services/user.service";
import { MessageCreateDialogComponent } from "../../message/create/dialog/message-create-dialog.component";
import { Environment } from "src/environments/environment";
import { Location } from "../../../models/location";
import { Message } from "../../../models/message";
import { Review } from "../../../models/review";
import { User } from "../../../models/user";
import { MatDialog } from "@angular/material/dialog";
import { ModelHelper } from "src/app/helpers/ModelHelper";
import { DistanceHelper } from "src/app/helpers/DistanceHelper";

@Component({
  selector: "user-read-component",
  styleUrls: ["user-read.css"],
  templateUrl: "user-read.html",
})
export class UserReadComponent implements OnInit, OnDestroy {
  public user: User = new User();
  private userSubscription: Subscription = new Subscription();

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    public messageDialog: MatDialog,
    public transactionDialog: MatDialog,
    private reviewService: ReviewService,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    public sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    this.route.params.subscribe((p) => {
      let id = p["id"];
      if (id) {
        this.userSubscription = this.userService.read(id).subscribe(
          (user: User) => {
            this.user = user;
          },
          (error: Error) => {
            this.alertService.error(error.message);
          }
        );
      } else {
        this.userSubscription = this.userService.getUser().subscribe(
          (user: User) => {
            this.user = user;
          },
          (error: Error) => {
            this.alertService.error(error.message);
          }
        );
      }
    });
  }

  ngOnDestroy() {
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  private checkPermissions(): boolean {
    if (User.isEmpty(this.user)) {
      this.alertService.error("ERROR_EMPTY");
      return false;
    } else if (this.user.id === localStorage.getItem("id")!) {
      this.alertService.error("ERROR_OWN_USER");
      return false;
    } else if (!User.isLegal(this.user)) {
      this.alertService.error("ERROR_NOT_LEGAL");
      return false;
    } else {
      return true;
    }
  }

  public isImageEmpty() {
    if (this.user.avatar && this.user.avatar.length > 0) {
      return true;
    } else {
      return false;
    }
  }

  public isAliasVisible(): boolean {
    return Environment.Menu.Alias;
  }

  public isMobileVisible(): boolean {
    return Environment.Menu.Mobile;
  }

  public isAddressVisible(): boolean {
    return Environment.Menu.Address;
  }

  public isMyUser() {
    //window.location used to get id because this method is run during form load and not through subscription
    if (
      window.location.href.substr(window.location.href.lastIndexOf("/") + 1) ==
      localStorage.getItem("id")
    ) {
      return true;
    } else {
      return false;
    }
  }

  public isLoggedIn() {
    if (localStorage.getItem("id")) {
      return true;
    } else {
      return false;
    }
  }

  public getCurrency(): string {
    if (this.user && this.user.currency) {
      return this.user.currency.toUpperCase();
    } else {
      return Environment.Default.Currency;
    }
  }

  public getDistance(): number {
    return DistanceHelper.calculate(
      this.user.latitude,
      this.user.longitude,
      localStorage.get("latitude"),
      localStorage.get("longitude")
    );
  }

  /*  private getReviewAverage(): number {
          return Review.getAverage(this.user.reviews);
      }
      public getPositive(): string {
          return Review.getPositive(this.user.reviews).toString();
      }
  
      private getNegative(): string {
          return Review.getNegative(this.user.reviews).toString();
      }*/

  public openMessageDialog() {
    if (this.checkPermissions()) {
      let dialogRef = this.messageDialog.open(MessageCreateDialogComponent, {
        width: "90%",
      });
      var message: Message = new Message();
      //Receiver
      var receiver: User = this.user;
      receiver.type = "receiver";
      message.users.push(receiver);
      //Sender
      var sender: User = this.authenticationService.getLocalUser();
      sender.type = "sender";
      message.users.push(sender);
      console.log("openMessageDialog");
      console.log(message);
      // var users: Array<User> = new Array<User>();
      //users.push(this.user);
      dialogRef.componentInstance.setMessage(message);
    }
  }
}
