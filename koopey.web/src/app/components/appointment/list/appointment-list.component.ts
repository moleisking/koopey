import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { AppointmentService } from "../../../services/appointment.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { Appointment } from "../../../models/appointment";
import { Transaction, TransactionType } from "../../../models/transaction";
import { User, UserType } from "../../../models/user";
import { UserService } from "src/app/services/user.service";
import { ModelHelper } from "src/app/helpers/ModelHelper";

@Component({
  selector: "appointment-list-component",
  templateUrl: "appointment-list.html",
  styleUrls: ["appointment-list.css"],
})
export class AppointmentListComponent implements OnInit {
  private clickSubscription: Subscription = new Subscription();
  private appointmentSubscription: Subscription = new Subscription();

  public appointments: Array<Appointment> = new Array<Appointment>();
  public columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private clickService: ClickService,
    private appointmentService: AppointmentService,
    private router: Router,
    private transactionService: TransactionService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.appointmentSubscription = this.appointmentService
      .getAppointments()
      .subscribe(
        (appointments: Array<Appointment>) => {
          this.appointments = appointments;
        },
        (error: Error) => {
          console.log(error);
        },
        () => {}
      );
  }

  ngAfterContentInit() {
    this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.AppointmentListComponent
    );
    this.clickSubscription = this.clickService
      .getEventListClick()
      .subscribe(() => {
        this.gotoEventCreate();
      });
  }

  ngAfterViewInit() {
    this.onScreenSizeChange();
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

  public onScreenSizeChange() {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth <= 512) {
      this.columns = 1;
    } else if (this.screenWidth > 512 && this.screenWidth <= 1024) {
      this.columns = 2;
    } else if (this.screenWidth > 1024 && this.screenWidth <= 2048) {
      this.columns = 3;
    } else if (this.screenWidth > 2048 && this.screenWidth <= 4096) {
      this.columns = 4;
    }
  }

  public getBuyer(appointment: Appointment) {
    return ModelHelper.count(UserType.Buyer, appointment.users);
  }

  public getSeller(appointment: Appointment) {
    return ModelHelper.count(UserType.Seller, appointment.users);
  }

  public gotoEventUpdate(appointment: Appointment) {
    this.appointmentService.setAppointment(appointment);
    this.router.navigate(["/appointment/update"]);
  }

  public gotoEventCreate() {
    var appointment = new Appointment();
    this.appointmentService.setAppointment(appointment);
    this.router.navigate(["/appointment/create"]);
  }

  public showNoResults(): boolean {
    if (!this.appointments || this.appointments.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
