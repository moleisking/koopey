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
import { TranslateService } from "ng2-translate";
import { Config } from "../../../config/settings";
import { Appointment } from "../../../models/appointment";
import { Transaction, TransactionType } from "../../../models/transaction";
import { User } from "../../../models/user";

@Component({
  selector: "appointment-list-component",
  templateUrl: "appointment-list.html",
  styleUrls: ["appointment-list.css"],
})
export class AppointmentListComponent implements OnInit {
  private clickSubscription: Subscription = new Subscription();
  private appointmentSubscription: Subscription = new Subscription();

  private appointments: Array<Appointment> = new Array<Appointment>();
  private columns: number = 1;
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
      CurrentComponent.EventListComponent
    );
    this.clickSubscription = this.clickService
      .getEventListClick()
      .subscribe(() => {
        this.gotoEventCreate();
      });
  }

  ngAfterViewInit() {
    this.onScreenSizeChange(null);
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

  private onScreenSizeChange(event: any) {
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

  private gotoEventUpdate(appointment: Appointment) {
    this.appointmentService.setAppointment(appointment);
    this.router.navigate(["/appointment/update"]);
  }

  private gotoEventCreate() {
    var appointment = new Appointment();
    this.appointmentService.setAppointment(appointment);
    this.router.navigate(["/appointment/create"]);
  }

  private showNoResults(): boolean {
    if (!this.appointments || this.appointments.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
