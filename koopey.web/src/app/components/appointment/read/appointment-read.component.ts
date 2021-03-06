import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AppointmentService } from "../../../services/appointment.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { TransactionHelper } from "../../../helpers/TransactionHelper";
import { Alert } from "../../../models/alert";
import { Appointment } from "../../../models/appointment";
import { Transaction } from "../../../models/transaction";

@Component({
  selector: "appointment-read-component",
  templateUrl: "appointment-read.html",
  styleUrls: ["appointment-read.css"],
})
export class AppointmentReadComponent implements OnInit, OnDestroy {
  private appointmentSubscription: Subscription = new Subscription();
  public appointment: Appointment = new Appointment();

  constructor(
    private route: ActivatedRoute,
    private alertService: AlertService,
    private appointmentService: AppointmentService,
    private translateService: TranslateService,
    private transactionService: TransactionService
  ) {}

  ngOnInit() {
    this.getAppointment();
  }

  ngOnDestroy() {}

  public isLoggedIn() {
    if (localStorage.getItem("id")) {
      return true;
    } else {
      return false;
    }
  }

  private getAppointment() {
    this.route.params.subscribe((p) => {
      let id = p["id"];
      if (id) {
        this.appointmentService.readAppointment(id).subscribe(
          (appointment: Appointment) => {
            this.appointment = appointment;
          },
          (error: Error) => {
            this.alertService.error(<any>error);
          },
          () => {}
        );
      } else {
        this.appointmentSubscription = this.appointmentService
          .getAppointment()
          .subscribe(
            (appointment: Appointment) => {
              this.appointment = appointment;
            },
            (error: Error) => {
              this.alertService.error(<any>error);
            },
            () => {}
          );
      }
    });
  }

  private getTimeStampText(epoch: any): string {
    var date = new Date(epoch);
    date.setHours(0, 0, 0, 0);
    return date.toDateString();
  }
}
