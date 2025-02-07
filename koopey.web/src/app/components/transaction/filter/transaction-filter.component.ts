import { AlertService } from "../../../services/alert.service";
import { BaseComponent } from "../../base/base.component";
import { Component, OnInit, OnDestroy, EventEmitter, Output } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Search } from "../../../models/search";
import { SearchService } from "../../../services/search.service";
import { Subscription } from "rxjs";
import { Transaction } from "../../../models/transaction";
import { TransactionService } from "../../../services/transaction.service";

@Component({
  selector: "transaction-filter",
  styleUrls: ["transaction-filter.css"],
  templateUrl: "transaction-filter.html",
})
export class TransactionFilterComponent extends BaseComponent
  implements OnInit, OnDestroy {
  public busy: boolean = false;
  public formGroup!: FormGroup;
  public search: Search = new Search();
  private searchSubscription: Subscription = new Subscription();

  constructor(
    private alertService: AlertService,
    private formBuilder: FormBuilder,
    private router: Router,
    public sanitizer: DomSanitizer,
    private searchService: SearchService,
    private transactionService: TransactionService
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      start: [this.search.start, Validators.required],
      end: [this.search.end, Validators.required],
      reference: [this.search.reference, [Validators.minLength(5)]],
    });
  }

  ngAfterContentInit() {
    this.searchSubscription = this.searchService
      .getSearch()
      .subscribe((search: Search) => {
        this.search = search;
      });
  }

  ngOnDestroy() {
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  public find() {
    let search: Search = this.formGroup.getRawValue();
    search.latitude = Number(localStorage.getItem("latitude")!);
    search.longitude = Number(localStorage.getItem("longitude")!);

    if (!this.search.start && !this.search.end) {
      this.alertService.error("ERROR_NOT_DATE");
    } else {
      this.busy = true;
      this.transactionService.searchBetweenDates(this.search).subscribe(
        (transactions: Array<Transaction>) => {
          this.transactionService.setTransactions(transactions);
          console.log(transactions);
        },
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
    }
  }
}
