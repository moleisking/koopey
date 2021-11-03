import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { AppointmentService } from "../../../services/appointment.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { AssetService } from "../../../services/asset.service";
import { UserService } from "../../../services/user.service";
import { TransactionHelper } from "../../../helpers/TransactionHelper";
import { Alert } from "../../../models/alert";
import { Appointment } from "../../../models/appointment";
import { Asset } from "../../../models/asset";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { MatDatepicker, MatDatepickerIntl } from "@angular/material/datepicker";

@Component({
  selector: "appointment-edit",
  templateUrl: "appointment-edit.html",
  styleUrls: ["appointment-edit.css"],
})
export class AppointmentEditComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private appointmentSubscription: Subscription = new Subscription();
  public appointment: Appointment = new Appointment();
  public startDate: Date = new Date();
  public endDate: Date = new Date();
  public startTime: string = "08:00";
  public endTime: string = "09:00";
  public min: Date = new Date();
  public max: Date = new Date();
  protected redirect: boolean = true;
  @ViewChild(MatDatepicker) datepicker!: MatDatepicker<Date>;

  constructor(
    protected alertService: AlertService,
    protected authenticationService: AuthenticationService,
    protected clickService: ClickService,
    protected datePickerService: MatDatepickerIntl,
    protected appointmentService: AppointmentService,
    protected router: Router,
    protected transactionService: TransactionService,
    protected translateService: TranslateService,
    protected assetService: AssetService,
    protected userService: UserService /*,private dateAdapter:DateAdapter<Date>*/
  ) {
    //dateAdapter.setLocale('de'); // DD.MM.YYYY
  }

  ngOnInit() {
    this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.AppointmentCreateComponent
    );
    this.clickSubscription = this.clickService
      .getEventCreateClick()
      .subscribe(() => {
        this.createAppointment();
      });
  }

  ngAfterContentInit() {
    this.appointmentSubscription = this.appointmentService
      .getAppointment()
      .subscribe(
        (appointment: Appointment) => {
          if (appointment) {
            this.appointment = appointment;
          } else {
            this.appointment = new Appointment();
          }
        },
        (error: Error) => {
          console.log(error);
        },
        () => {}
      );
  }

  ngAfterViewInit() {
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
    if (this.appointmentSubscription) {
      this.appointmentSubscription.unsubscribe();
    }
  }

  public setEvent(appointment: Appointment) {
    this.appointment = appointment;
  }

  public onStartTimeStampChange(event: any) {
    console.log("onStartTimeStampChange");
    if (this.startDate) {
      this.startDate.setHours(Number(this.startTime.split(":")[0]));
      this.startDate.setMinutes(Number(this.startTime.split(":")[1]));
      if (
        this.startDate.getFullYear() > 1900 &&
        this.startDate.getMonth() >= 0 &&
        this.startDate.getDate() > 0
      ) {
        this.appointment.start = this.startDate.getTime();
        console.log(this.startDate);
        console.log(this.startDate.getTime());
      }
    }
  }

  public onEndTimeStampChange(event: any) {
    console.log("onEndTimeStampChange");
    if (this.endDate) {
      this.endDate.setHours(Number(this.endTime.split(":")[0]));
      this.endDate.setMinutes(Number(this.endTime.split(":")[1]));
      if (
        this.endDate.getFullYear() > 1900 &&
        this.endDate.getMonth() >= 0 &&
        this.endDate.getDate() > 0
      ) {
        this.appointment.end = this.endDate.getTime();
      }
    }
  }

  private createAppointment() {
    if (Appointment.isEmpty(this.appointment)) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.appointmentService.create(this.appointment).subscribe(
        (alert) => {
          this.alertService.success("INFO_COMPLETE");
        },
        (error: Error) => {
          this.alertService.error(<any>error);
        },
        () => {}
      );
    }
  }

  public showEventCompleteAlert(alert: Alert) {
    if (Alert.isSuccess(alert)) {
      this.alertService.success("INFO_COMPLETE");
    } else {
      this.alertService.error(alert.message);
    }
  }
}
