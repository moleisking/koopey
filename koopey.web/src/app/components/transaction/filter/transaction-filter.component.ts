import { AlertService } from "../../../services/alert.service";
import { Component, OnInit, OnDestroy, EventEmitter, Output, ChangeDetectionStrategy, inject, Inject } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { FormGroup, FormBuilder, Validators, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { Search } from "../../../models/search";
import { SearchService } from "../../../services/search.service";
import { Subscription } from "rxjs";
import { Transaction } from "../../../models/transaction";
import { TransactionService } from "../../../services/transaction.service";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatButtonModule } from "@angular/material/button";
import { MatDatepickerModule, MatDateSelectionModel } from "@angular/material/datepicker";
import { MatInputModule } from "@angular/material/input";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatIconModule } from "@angular/material/icon";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports : [FormsModule, MatButtonModule, MatDatepickerModule, MatIconModule, MatInputModule, MatFormFieldModule, MatProgressSpinnerModule, ReactiveFormsModule],
  selector: "transaction-filter",
  standalone: true,
  styleUrls: ["transaction-filter.css"],
  templateUrl: "transaction-filter.html",
})
export class TransactionFilterComponent
  implements OnInit, OnDestroy {
  public busy: boolean = false;
  public formGroup!: FormGroup;
  public search: Search = new Search();
  private searchSubscription: Subscription = new Subscription();

  private alertService = inject(AlertService);
  private formBuilder = inject(FormBuilder);
  private router = inject(Router);
  //public sanitizer = inject(DomSanitizer);
  private searchService = inject(SearchService);
  private transactionService = inject(TransactionService);

  /*constructor(@Inject(DomSanitizer) sanitizer: DomSanitizer) {
    super(sanitizer);
  }*/
  
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
