import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Environment } from "src/environments/environment";
import { Router } from "@angular/router";
import { AlertService } from "../../services/alert.service";
import { AuthenticationService } from "../../services/authentication.service";
import { MessageService } from "../../services/message.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../services/user.service";
import { Alert } from "../../models/alert";
//import { Image } from "../models/image";
import { User } from "../../models/user";
import { Wallet } from "../../models/wallet";

@Component({
  selector: "dashboard-component",
  templateUrl: "dashboard.html",
  styleUrls: ["dashboard.css"],
})
export class DashboardComponent implements OnInit {
  public authUser: User = new User();
  private bitcoinWallet: Wallet = new Wallet();
  private ethereumWallet: Wallet = new Wallet();
  private ibanWallet: Wallet = new Wallet();
  private tokoWallet: Wallet = new Wallet();
  public messageUnsentCount: Number = 0;
  public messageUndeliveredCount: Number = 0;

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    private messageService: MessageService,
    private userService: UserService,
    private router: Router,
    private sanitizer: DomSanitizer,
    private translateService: TranslateService
  ) {
    this.getMyUser();
  }

  ngOnInit() {
    try {
      //this.authUser = this.userService.getMyUserLocal();
    } catch (error) {
      console.log("No current user found in dashboard" + error);
    }
  }

  private getMyUser() {
    this.userService.readMyUser().subscribe(
      (user) => {
        this.authUser = user;
        //this.authUser.avatar = this.shrinkImage(user.images[0].uri, 256,256);
        this.authenticateService.setUser(user);
        this.authenticateService.saveLocalUser(user);
      },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {
        this.getUnread();
        this.getUnsent();
        if (Environment.type != "production") {
          console.log(this.authUser);
        }
      }
    );
  }

  /*private toUpperCase(value: string): string {
        if (value) {
            return value.toUpperCase();
        }
    }*/

  private getUnread() {
    this.messageService.countUserUnsentMessages().subscribe(
      (count: Number) => {
        this.messageUnsentCount = count;
      },
      (error: Error) => {
        this.alertService.error(<any>error);
      },
      () => {}
    );
  }

  private getUnsent() {
    this.messageService.countUserUndeliveredMessages().subscribe(
      (count: Number) => {
        this.messageUndeliveredCount = count;
      },
      (error: Error) => {
        this.alertService.error(<any>error);
      },
      () => {}
    );
  }

  public toggleTrack(event: any) {
    if (event.checked == true) {
      this.authUser.track = true;
    } else {
      this.authUser.track = false;
    }

    this.userService.updateTrack(event.checked).subscribe(
      (alert: String) => {
        console.log(alert);
      },
      (error) => {
        this.alertService.error(<any>error);
      },
      () => {}
    );
  }

  public hasTransactions(): boolean {
    return Environment.Menu.Transactions;
  }
}
