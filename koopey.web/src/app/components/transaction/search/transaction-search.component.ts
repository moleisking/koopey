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
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { MessageCreateDialogComponent } from "../../message/create/dialog/message-create-dialog.component";
import { DateHelper } from "../../../helpers/DateHelper";
import { Config } from "../../../config/settings";
import { Location } from "../../../models/location";
import { Search } from "../../../models/search";
import { Tag } from "../../../models/tag";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { Subscription } from "rxjs";
import { MatDatepickerIntl } from "@angular/material/datepicker";
import { MatDialog } from "@angular/material/dialog";

@Component({
  selector: "transaction-search-component",
  templateUrl: "transaction-search.html",
  styleUrls: ["transaction-search.css"],
})
export class TransactionSearchComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  public form!: FormGroup;
  private location: Location = new Location();
  public search: Search = new Search();
  public transactions: Array<Transaction> = new Array<Transaction>();
  private user: User = new User();
  public startDate: String = "2017-01-01";
  public endDate: String = "2017-01-28";
  public searching: boolean = false;

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    private datePickerService: MatDatepickerIntl,
    private sanitizer: DomSanitizer,
    private formBuilder: FormBuilder,
    public messageDialog: MatDialog,
    private router: Router,
    private transactionService: TransactionService,
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
      CurrentComponent.TransactionSearchComponent
    );
    this.clickSubscription = this.clickService
      .getTransactionSearchClick()
      .subscribe(() => {
        this.findTransactions();
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

  private handleStartUpdate(event: any) {
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

  private handleEndUpdate(event: any) {
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

  private findTransactions() {
    if (!this.search.start && !this.search.end) {
      this.alertService.error("ERROR_NOT_DATE");
    } else {
      console.log(this.search);
      //Set progress icon
      this.searching = true;
      this.transactionService
        .readTransactionsBetweenDates(this.search)
        .subscribe(
          (transactions) => {
            this.transactions = transactions;
            this.transactionService.setTransactions(this.transactions);
            console.log(transactions);
          },
          (error) => {
            this.alertService.error(<any>error);
          },
          () => {
            this.router.navigate(["/transaction/read/list"]);
          }
        );
    }
  }
}
