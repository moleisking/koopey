import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  ElementRef,
  ChangeDetectionStrategy,
  TemplateRef,
} from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { DatePipe } from "@angular/common";
import { Observable, Subscription, Subject } from "rxjs";

/*import {
    CalendarEvent, CalendarEventAction, CalendarEventTimesChangedEvent,
    CalendarModule
} from 'angular-calendar';*/
import {
  startOfDay,
  endOfDay,
  subDays,
  subWeeks,
  subMonths,
  addDays,
  addMonths,
  addWeeks,
  endOfMonth,
  isSameDay,
  isSameMonth,
  addHours,
  addMinutes,
} from "date-fns";

import { AuthenticationService } from "../../../services/authentication.service";
import { AlertService } from "../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";

import { Transaction } from "../../../models/transaction";

const colors: any = {
  red: { primary: "#ad2121", secondary: "#FAE3E3" },
  blue: { primary: "#1e90ff", secondary: "#D1E8FF" },
  yellow: { primary: "#e3bc08", secondary: "#FDF1BA" },
};

@Component({
  selector: "user-calender-component",
  templateUrl: "user-calendar.html",
  styleUrls: ["angular-calendar.css"],
  providers: [DatePipe],
})
//Note: https://mattlewis92.github.io/angular-calendar/#/kitchen-sink
export class UserCalendarComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private transactionSubscription: Subscription = new Subscription();

  private transactions: Array<Transaction> = new Array<Transaction>();
  public viewDate = new Date();
  //private events: CalendarEvent[] = [];
  private refresh: Subject<any> = new Subject();
  private activeDayIsOpen: boolean = true;
  public selectedIndex: Number = 0;

  constructor(
    private authenticateService: AuthenticationService,
    private alertService: AlertService,
    private clickService: ClickService,
    private route: ActivatedRoute,
    private router: Router,
    // private userService: UserService,
    private sanitizer: DomSanitizer,
    private transactionService: TransactionService,
    private translateService: TranslateService,
    private userService: UserService,
    public datepipe: DatePipe
  ) {}

  ngOnInit() {
    this.getMyTransactions();
    this.clickService.createInstance(
      ActionIcon.LIST,
      CurrentComponent.TransactionCreateComponent
    );
    this.clickSubscription = this.clickService
      .getTransactionCreateClick()
      .subscribe(() => {
        this.gotoTransactionCreate();
      });
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
  }

  private isMyUser(id: string) {
    if (id == localStorage.getItem("id")) {
      return true;
    } else {
      return false;
    }
  }

  private isAuthenticated() {
    return this.authenticateService.isLoggedIn();
  }

  private getMyTransactions() {
    this.transactionSubscription = this.transactionService
      .readTransactions()
      .subscribe(
        (transactions) => {
          this.transactions = transactions;
        },
        (error) => {
          console.log(error);
        },
        () => {
          this.convertTransactionsToEvents();
          console.log("getMyTransactions complete");
        }
      );
  }

  private convertTransactionsToEvents() {
    //Note: Minimum time is 15 min
    /* for (var i = 0; i < this.transactions.length; i++) {
            this.events.push({
                title: this.transactions[i].description,
                start: DateHelper.convertEpochToDate(this.transactions[i].startTimeStamp),
                end: this.transactions[i].startTimeStamp == this.transactions[i].endTimeStamp ? addMinutes(DateHelper.convertEpochToDate(this.transactions[i].endTimeStamp), 15) : DateHelper.convertEpochToDate(this.transactions[i].endTimeStamp),
                color: colors.blue,
                draggable: true,
                resizable: {
                    beforeStart: true,
                    afterEnd: true
                },
                meta: this.transactions[i].id,
                actions: [{
                    label: this.transactions[i].description,
                    onClick: ({ event }: { event: CalendarEvent }): void => {
                        event.meta = this.transactions[i].id;
                        this.eventClicked(this.transactions[i].id, event);
                    }
                }]
            });
        }
        this.refresh.next();*/
  }

  private gotoTransaction(transaction: Transaction) {
    this.router.navigate(["/transaction/read/transaction/", transaction.id]);
  }

  private gotoTransactionCreate() {
    this.router.navigate(["/transaction/create/"]);
  }

  /* private getViewDate(): string {
        return this.datepipe.transform(this.viewDate, 'fullDate');
    }*/

  /* private eventClicked(action: string, event: CalendarEvent): void {
        this.router.navigate(["/transaction/read/my/transaction/", event.meta])
    }*/

  /* private dayClicked({ date, events }: { date: Date, events: CalendarEvent[] }): void {
        this.viewDate = date;
        this.selectedIndex = 1;
    }*/

  private next() {
    this.viewDate.setMonth(this.viewDate.getMonth() + 1);
    this.refresh.next();
  }

  private previous() {
    this.viewDate.setMonth(this.viewDate.getMonth() - 1);
    this.refresh.next();
  }

  private today() {
    this.viewDate = new Date();
    this.refresh.next();
  }

  private tagIndexChange(val: number) {
    this.selectedIndex = val;
  }

  /*  private timeClicked({ event, newStart, newEnd }: CalendarEventTimesChangedEvent): void {
        this.router.navigate(["/transaction/read/many", event.meta])
    }*/
}
