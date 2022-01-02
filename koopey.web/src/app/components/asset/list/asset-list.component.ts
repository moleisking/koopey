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
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "src/environments/environment";
//import { Fee } from "../models/fee";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { Review } from "../../../models/review";
import { Search } from "../../../models/search";
import { User } from "../../../models/user";
import { TransactionHelper } from "../../../helpers/TransactionHelper";
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

  public columns: number = 1;
  private screenWidth: number = window.innerWidth;

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



  ngAfterViewInit() {
    this.onScreenSizeChange();
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

  public onScreenSizeChange() {
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

  public gotoAssetMap() {
    this.router.navigate(["/asset/map"]);
  }

  public gotoTransaction(transaction: Transaction) {
    this.transactionService.setTransaction(transaction);
    this.router.navigate(["/asset/read"]);
  }

  public showNoResults(): boolean {
    if (!this.transactions || this.transactions.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
