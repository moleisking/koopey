import { AlertService } from "../../../services/alert.service";
import { BaseComponent } from "../../base/base.component";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { TransactionService } from "../../../services/transaction.service";
import { Transaction } from "../../../models/transaction";

@Component({
  selector: "review-read",
  styleUrls: ["review-read.css"],
  templateUrl: "review-read.html",
})
export class ReviewReadComponent extends BaseComponent implements OnInit {
  private transaction: Transaction = new Transaction();
  private transactionSubscription: Subscription = new Subscription();

  constructor(
    // private activatedRoute: ActivatedRoute,
    private alertService: AlertService,
    private transactionService: TransactionService,
    public sanitizer: DomSanitizer
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    /* this.activatedRoute.queryParams.subscribe((parameter) => {
      if (parameter["type"]) {
        this.location.type = parameter["type"];
      }
      if (parameter["id"]) {
        console.log(parameter["id"]);
        this.locationService.getLocation().subscribe((location) => {
          this.location = location;
        });
      }
    });*/
    this.getReview();
  }

  ngOnDestroy() {
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
  }

  private getReview() {
    this.transactionSubscription = this.transactionService.getTransaction().subscribe(
      (transaction: Transaction) => {
        this.transaction = transaction;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }
}
