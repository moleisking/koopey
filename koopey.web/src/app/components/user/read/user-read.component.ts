import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { UserService } from "../../../services/user.service";
import { Environment } from "src/environments/environment";
import { Message } from "../../../models/message";
import { MatDialog } from "@angular/material/dialog";
import { DistanceHelper } from "src/app/helpers/DistanceHelper";
import { MessageService } from "src/app/services/message.service";
import { User } from "../../../models/user";
import { UserType } from "src/app/models/type/UserType";
import { MessageType } from "src/app/models/type/MessageType";

@Component({
  selector: "user-read-component",
  styleUrls: ["user-read.css"],
  templateUrl: "user-read.html",
})
export class UserReadComponent implements OnInit, OnDestroy {
  public dialog = false;
  public user: User = new User();
  private userSubscription: Subscription = new Subscription();

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private messageService: MessageService,
    public transactionDialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    public sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    this.getUser();
  }

  ngOnDestroy() {
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  private getUser() {
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

  public isMyUser() {
    if (
      window.location.href.substr(window.location.href.lastIndexOf("/") + 1) ==
      localStorage.getItem("id")
    ) {
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
      Number(localStorage!.getItem("latitude")),
      Number(localStorage!.getItem("longitude"))
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

  public openMessage() {
    let message: Message = new Message();
    message.type = MessageType.Sent;
    let sender: User = this.authenticationService.getMyUserFromStorage();
    sender.type = UserType.Sender;
    let receiver: User = this.user;
    receiver.type = UserType.Receiver;
    message.receiver = receiver;
    message.receiverId = receiver.id;
    message.sender = sender;
    message.senderId = sender.id;

    console.log("user-read:openMessage");
    console.log(message);
    if (sender.id === receiver.id) {
      this.alertService.error("ERROR_OWN_USER");
    } else {
      this.messageService.setMessage(message);
      this.router.navigate(["/message/list"]);
    }
  }
}
