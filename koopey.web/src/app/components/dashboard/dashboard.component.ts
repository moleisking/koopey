import { AlertService } from "../../services/alert.service";
import { AuthenticationService } from "../../services/authentication.service";
import { BaseComponent } from "../base/base.component";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { MessageService } from "../../services/message.service";
import { UserService } from "../../services/user.service";
import { User } from "../../models/user";
import { Wallet } from "../../models/wallet";

@Component({
  selector: "dashboard-component",
  templateUrl: "dashboard.html",
  styleUrls: ["dashboard.css"],
})
export class DashboardComponent extends BaseComponent implements OnInit {
  public user: User = new User();
  public bitcoinWallet: Wallet = new Wallet();
  public ethereumWallet: Wallet = new Wallet();
  public ibanWallet: Wallet = new Wallet();
  public tokoWallet: Wallet = new Wallet();
  public conversationNotSentCount: Number = 0;
  public conversationNotReceivedCount: Number = 0;

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private messageService: MessageService,
    private userService: UserService,
    public sanitizer: DomSanitizer
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    this.getUnread();
    this.getUnsent();
  }

  public getMyUser() {
    this.userService.readMyUser().subscribe(
      (user: User) => {
        this.user = user;
        this.authenticationService.setUser(user);
        this.authenticationService.saveLocalUser(user);
      },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {
        this.getUnread();
        this.getUnsent();
      }
    );
  }

  private getUnread() {
    this.messageService.countByDeliveredAndReceiver().subscribe(
      (conversationNotSentCount: Number) => {
        this.conversationNotSentCount = conversationNotSentCount;
      },
      (error: Error) => {
        this.alertService.error(<any>error);
      },
      () => {}
    );
  }

  private getUnsent() {
    this.messageService.countByDeliveiredAndSender().subscribe(
      (conversationNotReceivedCount: Number) => {
        this.conversationNotReceivedCount = conversationNotReceivedCount;
      },
      (error: Error) => {
        this.alertService.error(<any>error);
      },
      () => {}
    );
  }

  public toggleTrack(event: any) {
    if (event.checked == true) {
      this.user.track = true;
    } else {
      this.user.track = false;
    }

    this.userService.updateTrack(event.checked).subscribe(
      () => {},
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {}
    );
  }
}
