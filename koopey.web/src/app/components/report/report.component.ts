import { Component, ElementRef, Input, OnInit, ViewChild } from "@angular/core";
import { Observable, Subscription } from "rxjs";
import { AlertService } from "../../services/alert.service";
import { MessageService } from "../../services/message.service";
import { AssetService } from "../../services/asset.service";
import { TransactionService } from "../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../services/user.service";
import { Config } from "../../config/settings";
import { Message } from "../../models/message";
import { User } from "../../models/user";
import { Asset } from "../../models/asset";

@Component({
  selector: "report-component",
  templateUrl: "report.html",
})
export class ReportComponent implements OnInit {
  private assetSubscription: Subscription = new Subscription();
  private messageSubscription: Subscription = new Subscription();
  private transactionSubscription: Subscription = new Subscription();
  private userSubscription: Subscription = new Subscription();

  public messageCount: Number = 0;
  public assetCount: Number = 0;
  public transactionCount: Number = 0;
  public userCount: Number = 0;

  constructor(
    private alertService: AlertService,
    private assetService: AssetService,
    private messageService: MessageService,
    private transactionService: TransactionService,
    private translateService: TranslateService,
    private userService: UserService
  ) {}

  ngOnInit() {
    /*this.assetService.count().subscribe(
      (count: Number) => {
        this.assetCount = count;
      },
      (error: Error) => {
        this.alertService.error("ERROR_EMPTY");
      },
      () => {}
    );*/

    this.messageService.count().subscribe(
      (count: Number) => {
        this.messageCount = count;
      },
      (error: Error) => {
        this.alertService.error("ERROR_EMPTY");
      },
      () => {}
    );
    this.userService.count().subscribe(
      (count: Number) => {
        this.userCount = count;
      },
      (error: Error) => {
        this.alertService.error("ERROR_EMPTY");
      },
      () => {}
    );
    this.transactionService.count().subscribe(
      (count: Number) => {
        if (count) {
          this.transactionCount = count;
        }
      },
      (error: Error) => {
        this.alertService.error("ERROR_EMPTY");
      },
      () => {}
    );
  }

  ngOnDestroy() {
    if (this.assetSubscription) {
      this.assetSubscription.unsubscribe();
    }
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }
}
