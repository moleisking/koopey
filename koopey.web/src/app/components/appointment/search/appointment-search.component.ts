import {
  Component,
  ElementRef,
  OnInit,
  OnDestroy,
  ViewChild,
} from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators,
} from "@angular/forms";

import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { AppointmentService } from "../../../services/appointment.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { MessageCreateDialogComponent } from "../../message/create/dialog/message-create-dialog.component";
import { DateHelper } from "../../../helpers/DateHelper";
import { Config } from "../../../config/settings";
import { Appointment } from "../../../models/appointment";
import { Location } from "../../../models/location";
import { Search } from "../../../models/search";
import { Tag } from "../../../models/tag";
import { User } from "../../../models/user";
import { MatDatepickerIntl } from "@angular/material/datepicker";
import { MatDialog } from "@angular/material/dialog";
import { Subscription } from "rxjs";

@Component({
  selector: "appointment-search-component",
  templateUrl: "appointment-search.html",
  styleUrls: ["appointment-search.css"],
})
export class AppointmentSearchComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  public form!: FormGroup;
  private location: Location = new Location();
  public search: Search = new Search();
  private appointments: Array<Appointment> = new Array<Appointment>();
  private user: User = new User();
  public startDate: String = "2017-01-01";
  public endDate: String = "2017-01-28";
  public busy: boolean = false;

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    private datePickerService: MatDatepickerIntl,
    private sanitizer: DomSanitizer,
    private formBuilder: FormBuilder,
    public messageDialog: MatDialog,
    private router: Router,
    private eventService: AppointmentService,
    private translateService: TranslateService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      id: [this.search.transactionId, [Validators.minLength(5)]],
      start: [this.startDate, Validators.required],
      end: [this.endDate, Validators.required],
    });
  }

  ngAfterContentInit() {
    this.clickService.createInstance(
      ActionIcon.SEARCH,
      CurrentComponent.AppointmentSearchComponent
    );
    this.clickSubscription = this.clickService
      .getTransactionSearchClick()
      .subscribe(() => {
        this.find();
      });
  }

  ngAfterViewInit() {
    this.startDate = DateHelper.convertEpochToDateString(this.search.start);
    this.endDate = DateHelper.convertEpochToDateString(this.search.end);
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
  }

  public handleStartUpdate(event: any) {
    var utcDate = new Date(event.target.value);
    if (
      utcDate.getFullYear() > 1900 &&
      utcDate.getMonth() >= 0 &&
      utcDate.getDate() > 0
    ) {
      this.startDate = DateHelper.convertEpochToDateString(utcDate.getTime());
      this.search.start = utcDate.getTime();
    }
  }

  public handleEndUpdate(event: any) {
    var utcDate = new Date(event.target.value);
    if (
      utcDate.getFullYear() > 1900 &&
      utcDate.getMonth() >= 0 &&
      utcDate.getDate() > 0
    ) {
      this.endDate = DateHelper.convertEpochToDateString(utcDate.getTime());
      this.search.end = utcDate.getTime();
    }
  }

  public find() {
    if (!this.search.start && !this.search.end) {
      this.alertService.error("ERROR_NOT_DATE");
    } else {
      console.log(this.search);
      //Set progress icon
      this.busy = true;
      this.eventService.readAppointmentsBetweenDates(this.search).subscribe(
        (appointments: Array<Appointment>) => {
          this.appointments = appointments;
        },
        (error: Error) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.router.navigate(["/appointment/read/list"]);
        }
      );
    }
  }
}
