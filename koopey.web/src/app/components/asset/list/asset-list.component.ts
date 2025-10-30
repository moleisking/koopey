import {
  Component,
  OnInit,
  OnDestroy,
  ChangeDetectionStrategy,
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
import { TransactionService } from "../../../services/transaction.service";
import { Transaction } from "../../../models/transaction";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: "asset-list",
  standalone: false,
  styleUrls: ["asset-list.css"],
  templateUrl: "asset-list.html",
})
export class AssetListComponent implements OnInit, OnDestroy {
  private location: Location = new Location();
  public transactions: Array<Transaction> = new Array<Transaction>();
  private transactionSubscription: Subscription = new Subscription();

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    public messageDialog: MatDialog, 
    private router: Router,
    public sanitizer: DomSanitizer,  
    private transactionService: TransactionService
  ) { }

  ngOnInit() {
    this.getTransactions();
  }

  ngOnDestroy() {
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
  }

  private getTransactions() {
    this.transactionSubscription = this.transactionService.getTransactions().subscribe(
      (transactions: Array<Transaction>) => {
        this.transactions = transactions;        
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  public gotoAssetMap() {
    this.router.navigate(["/asset/map"]);
  }

  public gotoTransaction(transaction: Transaction) {
    this.transactionService.setTransaction(transaction);
    this.router.navigate(["/asset/read/" + transaction.id]);
  }

}
