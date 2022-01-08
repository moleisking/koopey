import {
  Component,
  OnInit,
  OnDestroy,
} from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { AssetService } from "../../../services/asset.service";
import { SearchService } from "../../../services/search.service";
import { Location } from "../../../models/location";
import { Search } from "../../../models/search";
import { MatDialog } from "@angular/material/dialog";
import { TransactionService } from "src/app/services/transaction.service";
import { Transaction } from "src/app/models/transaction";

@Component({
  selector: "asset-list",
  styleUrls: ["asset-list.css"],
  templateUrl: "asset-list.html",
})
export class AssetListComponent implements OnInit, OnDestroy {
  private transactionListSubscription: Subscription = new Subscription();
  private searchSubscription: Subscription = new Subscription();
  private location: Location = new Location();
  public transactions: Array<Transaction> = new Array<Transaction>();
  private search: Search = new Search();

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    public messageDialog: MatDialog,
    private assetService: AssetService,
    private router: Router,
    public sanitizer: DomSanitizer,
    private searchService: SearchService,
    private transactionService: TransactionService
  ) { }

  ngOnInit() {
    this.getTransactions();
  }

  ngOnDestroy() {
    if (this.transactionListSubscription) {
      this.transactionListSubscription.unsubscribe();
    }
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  private getTransactions() {
    this.transactionListSubscription = this.transactionService.getTransactions().subscribe(
      (transactions: Array<Transaction>) => {
        this.transactions = transactions;
        console.log(transactions);
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
    this.searchSubscription = this.searchService.getSearch().subscribe(
      (search) => {
        this.search = search;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  /* public convertValuePlusMargin(asset: Transaction): number {
     return TransactionHelper.AssetValuePlusMargin(asset.asset);
   }*/

  public gotoAssetMap() {
    this.router.navigate(["/asset/map"]);
  }

  public gotoTransaction(transaction: Transaction) {
    this.transactionService.setTransaction(transaction);
    this.router.navigate(["/asset/read/" + transaction.id]);
  }

}
