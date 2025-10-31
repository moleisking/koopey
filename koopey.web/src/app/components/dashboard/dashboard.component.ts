import { AlertService } from "../../services/alert.service";
import { AuthenticationService } from "../../services/authentication.service";
import { ChangeDetectionStrategy, Component, Inject, inject, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { MessageService } from "../../services/message.service";
import { UserService } from "../../services/user.service";
import { User } from "../../models/user";
import { Wallet } from "../../models/wallet";
import { MessageType } from "../../models/type/MessageType";
import { StorageService } from "@services/storage.service";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: "dashboard-component",
  standalone: false,
  templateUrl: "dashboard.html",
  styleUrls: ["dashboard.css"],
})
export class DashboardComponent implements OnInit {
  public user: User = new User();
  public bitcoinWallet: Wallet = new Wallet();
  public ethereumWallet: Wallet = new Wallet();
  public ibanWallet: Wallet = new Wallet();
  public tokoWallet: Wallet = new Wallet();
  public conversationNotSentCount: Number = 0;
  public conversationNotReceivedCount: Number = 0;
  private alertService = inject(AlertService);
  private authenticationService = inject(AuthenticationService)
  private messageService = inject(MessageService)
  private userService = inject(UserService)
  public sanitizer = inject(DomSanitizer);
    protected store = inject(StorageService);

 /* constructor(@Inject(DomSanitizer) sanitizer: DomSanitizer) {
    super(sanitizer);
  }*/
  
  ngOnInit() {
    this.getUnread();
    this.getRead();
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
        this.getRead();
      }
    );
  }

  private getUnread() {
    this.messageService.countByReceiver(MessageType.Sent).subscribe(
      (conversationNotSentCount: Number) => {
        this.conversationNotSentCount = conversationNotSentCount;
      },
      (error: Error) => {
        this.alertService.error(<any>error);
      },
      () => { }
    );
  }

  private getRead() {
    this.messageService.countBySender(MessageType.Read).subscribe(
      (conversationNotReceivedCount: Number) => {
        this.conversationNotReceivedCount = conversationNotReceivedCount;
      },
      (error: Error) => {
        this.alertService.error(<any>error);
      },
      () => { }
    );
  }

  public toggleTrack(event: any) {
    if (event.checked == true) {
      this.user.track = true;
    } else {
      this.user.track = false;
    }

    this.userService.updateTrack(event.checked).subscribe(
      () => { },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => { }
    );
  }
}
