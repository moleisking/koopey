import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { UUID } from "angular2-uuid";
import { AlertService } from "../../../../services/alert.service";
import { AuthenticationService } from "../../../../services/authentication.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../../services/click.service";
import { AppointmentService } from "../../../../services/appointment.service";
import { AssetService } from "../../../../services/asset.service";
import { TransactionService } from "../../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../../services/user.service";
import { AppointmentCreateComponent } from "../appointment-create.component";
import { Appointment } from "../../../../models/appointment";
import { Transaction } from "../../../../models/transaction";
import { User } from "../../../../models/user";
import { MatDialogRef } from "@angular/material/dialog";
import { MatDatepickerIntl } from "@angular/material/datepicker";

@Component({
  selector: "appointment-create-dialog",
  templateUrl: "appointment-create-dialog.html",
})
export class AppointmentCreateDialogComponent extends AppointmentCreateComponent
  implements OnInit {
  constructor(
    private dialogRef: MatDialogRef<AppointmentCreateDialogComponent>,
    protected alertService: AlertService,
    protected authenticationService: AuthenticationService,
    protected clickService: ClickService,
    protected datePickerService: MatDatepickerIntl,
    protected appointmentService: AppointmentService,
    protected router: Router,
    protected transactionService: TransactionService,
    protected translateService: TranslateService,
    protected assetService: AssetService,
    protected userService: UserService
  ) {
    super(
      alertService,
      authenticationService,
      clickService,
      datePickerService,
      appointmentService,
      router,
      transactionService,
      translateService,
      assetService,
      userService
    );
  }

  ngOnInit() {
    this.redirect = false;
  }

  public appointmentComplete(complete: boolean): void {
    console.log("appointmentComplete");
    console.log(complete);
  }

  public setAppointment(appointment: Appointment) {
    this.appointment = appointment;
  }

  private cancel() {
    this.dialogRef.close(null);
  }
}
