//Angular, Material, Libraries
import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { MdDatepickerModule, MdDatepickerIntl } from "@angular/material";
import { Subscription } from "rxjs/Subscription";
//Services
import { AlertService } from "../../../services/alert.service";
import { AuthService } from "../../../services/auth.service";
import { BitcoinService } from "../../../services/bitcoin.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { EventService } from "../../../services/appointment.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "ng2-translate";
import { AssetService } from "../../../services/asset.service";
import { UserService } from "../../../services/user.service";

//Helpers
import { TransactionHelper } from "../../../helpers/TransactionHelper";
import { CurrencyHelper } from "../../../helpers/CurrencyHelper";
//Objects
import { Alert } from "../../../models/alert";
import { Config } from "../../../config/settings";
import { Event } from "../../../models/event";
import { Asset } from "../../../models/asset";
import { Transaction } from "../../../models/transaction";
import { User, UserType } from "../../../models/user";

@Component({
  selector: "event-create-component",
  templateUrl: "../../views/event-create.html",
  styleUrls: ["../../styles/app-root.css"],
})
export class EventCreateComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription;
  private eventSubscription: Subscription;
  protected event: Event = new Event();
  private startDate: Date = new Date();
  private endDate: Date = new Date();
  private startTime: string = "08:00";
  private endTime: string = "09:00";
  private min: Date = new Date();
  private max: Date = new Date();
  protected redirect: boolean = true;
  /*@ViewChild(MdDatepicker ) datepicker: MdDatepicker<Date>;*/

  constructor(
    protected alertService: AlertService,
    protected authService: AuthService,
    protected clickService: ClickService,
    protected datePickerService: MdDatepickerIntl,
    protected eventService: EventService,
    protected router: Router,
    protected transactionService: TransactionService,
    protected translateService: TranslateService,
    protected assetService: AssetService,
    protected userService: UserService
  ) /*,private dateAdapter:DateAdapter<Date>*/
  {
    //dateAdapter.setLocale('de'); // DD.MM.YYYY
  }

  ngOnInit() {
    this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.EventCreateComponent
    );
    this.clickSubscription = this.clickService
      .getEventCreateClick()
      .subscribe(() => {
        this.createEvent();
      });
  }

  ngAfterContentInit() {
    this.eventSubscription = this.eventService.getEvent().subscribe(
      (event) => {
        if (event) {
          this.event = event;
        } else {
          this.event = new Event();
        }
      },
      (error) => {
        console.log(error);
      },
      () => {}
    );
  }

  ngAfterViewInit() {
    console.log(this.event);
    //Set buyer
    //this.transaction.buyer = this.authenticateService.getLocalUser();
    //Set default date
    this.max.setMonth(this.max.getMonth() + 6);
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
    if (this.eventSubscription) {
      this.eventSubscription.unsubscribe();
    }
  }

  public setEvent(event: Event) {
    this.event = event;
  }

  private onStartTimeStampChange(event: any) {
    console.log("onStartTimeStampChange");
    if (this.startDate) {
      this.startDate.setHours(Number(this.startTime.split(":")[0]));
      this.startDate.setMinutes(Number(this.startTime.split(":")[1]));
      if (
        this.startDate.getFullYear() > 1900 &&
        this.startDate.getMonth() >= 0 &&
        this.startDate.getDate() > 0
      ) {
        this.event.startTimeStamp = this.startDate.getTime();
        console.log(this.startDate);
        console.log(this.startDate.getTime());
      }
    }
  }

  private onEndTimeStampChange(event: any) {
    console.log("onEndTimeStampChange");
    if (this.endDate) {
      this.endDate.setHours(Number(this.endTime.split(":")[0]));
      this.endDate.setMinutes(Number(this.endTime.split(":")[1]));
      if (
        this.endDate.getFullYear() > 1900 &&
        this.endDate.getMonth() >= 0 &&
        this.endDate.getDate() > 0
      ) {
        this.event.endTimeStamp = this.endDate.getTime();
      }
    }
  }

  private createEvent() {
    if (Event.isEmpty(this.event)) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.eventService.createEvent(this.event).subscribe(
        (alert) => {
          this.alertService.success("INFO_COMPLETE");
        },
        (error) => {
          this.alertService.error(<any>error);
        },
        () => {}
      );
    }
  }

  private showEventCompleteAlert(alert: Alert) {
    if (Alert.isSuccess(alert)) {
      this.alertService.success("INFO_COMPLETE");
    } else {
      this.alertService.error(alert.message);
    }
  }
}
