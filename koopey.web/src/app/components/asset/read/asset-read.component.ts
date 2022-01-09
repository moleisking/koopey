import { Asset } from "../../../models/asset";
import { ActivatedRoute } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { AssetService } from "../../../services/asset.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { Component, OnInit, OnDestroy } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { MobileDialogComponent } from "../../common/mobile/mobile-dialog.component";
import { TransactionDialogComponent } from "../../transaction/dialog/transaction-dialog.component";
import { TransactionEditComponent } from "../../transaction/edit/transaction-edit.component";
import { SearchService } from "../../../services/search.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "src/environments/environment";
import { Location } from "../../../models/location";
import { Message } from "../../../models/message";
import { Search } from "../../../models/search";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";
import { MatDialog } from "@angular/material/dialog";
import { ModelHelper } from "src/app/helpers/ModelHelper";
import { TransactionType } from "src/app/models/type/TransactionType";

@Component({
  selector: "asset-read-component",
  styleUrls: ["asset-read.css"],
  templateUrl: "asset-read.html", 
})
export class AssetReadComponent implements OnInit, OnDestroy {

  //private authSubscription: Subscription = new Subscription();

  private reviewSubscription: Subscription = new Subscription();
  private searchSubscription: Subscription = new Subscription();
  private transactionSubscription: Subscription = new Subscription();
  public asset: Asset = new Asset();
  // private review: Review = new Review();
  public transaction!: Transaction;
  private authUser: User = new User();
  private user: User = new User();
  private search: Search = new Search();
  
  public permission: boolean = false;

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    public messageDialog: MatDialog,
    public mobileDialog: MatDialog,
    private assetService: AssetService,
    public reviewDialog: MatDialog,
    public transactionDialog: MatDialog,
    private reviewService: TransactionService,
    private searchService: SearchService,
    private route: ActivatedRoute,
    public sanitizer: DomSanitizer,
    private translateService: TranslateService,
    private transactionService: TransactionService
  ) { }

  ngOnInit() {
    //Load authorized user
    this.authUser = this.authenticationService.getMyUserFromStorage();

    this.getTransaction();
  }

  ngAfterContentInit() {
    //this.setReviews();
    //this.setTransactionName();
  }

  ngAfterViewInit() {
    this.checkPermissions();
  }

  ngOnDestroy() {
    if (this.reviewSubscription) {
      this.reviewSubscription.unsubscribe();
    }
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
  }

  private getTransaction() {
    this.route.params.subscribe((p) => {
      let id = p["id"];
      if (id) {
        this.transactionSubscription = this.transactionService.read(id, true).subscribe(
          (transaction: Transaction) => {
            this.transaction = transaction;
            console.log(transaction);
          },
          (error: Error) => {
            this.alertService.error(error.message);
          }
        );
      } else {
        this.transactionSubscription = this.transactionService.getTransaction().subscribe(
          (transaction: Transaction) => {
            this.transaction = transaction;
            console.log(transaction);

          },
          (error: Error) => {
            this.alertService.error(error.message);
          }
        );
      }
    });
  }

  /* private isEthereumWalletEmpty() {
         return Wallet.isEmpty(this.ethereumWallet);
     }*/

  public isTransactionVisible(): boolean {
    return Environment.Menu.Transactions;
  }

  public isAliasVisible(): boolean {
    return Environment.Menu.Alias;
  }

  public isMobilVisible(): boolean {
    return Environment.Menu.Mobile;
  }

  public isAddressVisible(): boolean {
    return Environment.Menu.Address;
  }

  private checkPermissions(): boolean {
    if (!this.transaction.asset || !this.transaction.seller || !this.authUser) {
      this.permission = false;
      return false;
    } else if (ModelHelper.equals(this.user, this.authUser)) {
      this.alertService.error("ERROR_OWN_USER");
      this.permission = false;
      return false;
    } else if (!User.isAuthenticated(this.authUser)) {
      this.alertService.error("ERROR_NOT_ACTIVATED");
      console.log(this.authUser);
      this.permission = false;
      return false;
    } else if (!User.isLegal(this.authUser)) {
      this.alertService.error("ERROR_NOT_LEGAL");
      this.permission = false;
      return false;
    } else {
      this.permission = true;
      return true;
    }
  }

/*  private setReviews() {
    console.log("setReviews()");
    this.reviewSubscription = this.reviewService
      .readAssetReviews(this.asset)
      .subscribe(
        (reviews) => {
          this.asset.reviews = reviews;
        },
        (error) => {
          console.log(error);
        },
        () => { }
      );
  }*/

  /*private setTransactionName() {
    this.translateService
      .get("PRODUCT")
      .subscribe((translatedPhrase: string) => {
        this.transaction.description =
          <any>translatedPhrase + ": " + this.asset.name;
      });
  }*/

  public isFileVisible() {
    if (Environment.Menu.Files && !(this.asset.data.length == 0)) {
      return true;
    } else {
      return false;
    }
  }

 

  

  public openMessage() { }

  public openMobileDialog() {
    if (this.checkPermissions()) {
      let dialogRef = this.mobileDialog.open(MobileDialogComponent, {
        height: "20%",
        width: "20%",
      });
      dialogRef.componentInstance.setMobile(this.asset.seller.mobile);
    }
  }

  /*public openReviewDialog() {
    if (this.checkPermissions()) {
      var review: Review = new Review();
      review.type = ReviewType.Stars;
      review.assetId = this.asset.id;
      //review.userId = this.asset.user.id;
      review.judgeId = localStorage.getItem("id")!;
      this.reviewService.setReview(review);
      let dialogRef = this.reviewDialog.open(ReviewDialogComponent, {});
      // height: '512px'
      // dialogRef.componentInstance.setUser(this.asset.user);
      dialogRef.componentInstance.setAsset(this.asset);
    }
  }*/

  public openTransactionDialog() {
    console.log("openTransactionDialog()");
    //NOTE* If user only wants to send donation, other user has to create fee
    if (this.checkPermissions()) {
      if (this.transaction.value == 0) {
        this.alertService.error("ERROR_FEE_REQUIRED");
      } else if (this.transaction.quantity == 0) {
        this.alertService.error("ERROR_QUANTITY_REQUIRED");
      } else {
        //NOTE:Transaction name set on getUser()
        let dialogRef = this.transactionDialog.open(
          TransactionDialogComponent,
          {}
        );
        //Set transaction seller
        var seller = this.asset.seller;
        seller.type = "seller";

        this.transaction.seller = seller;
        //Set transaction buyer
        var buyer = this.authenticationService.getMyUserFromStorage();
        buyer.type = "buyer";
        this.transaction.buyer = buyer;
        this.transaction.type = TransactionType.Quote;
        // this.transaction.asset = Asset.simplify(this.asset);
        this.transaction.quantity = 1;
        this.transaction.total =
          this.transaction.quantity * this.transaction.value;
        this.transactionService.setTransaction(this.transaction);
        //dialogRef.componentInstance.setTransaction(transaction);
        //dialogRef.close()
      }
    }
  }
}
