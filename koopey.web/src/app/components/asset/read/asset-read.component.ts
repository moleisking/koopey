import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { MessageCreateDialogComponent } from "../../message/create/dialog/message-create-dialog.component";
import { MobileDialogComponent } from "../../mobile/mobile-dialog.component";
import { ReviewCreateDialogComponent } from "../../review/create/dialog/review-create-dialog.component";
import { TransactionCreateDialogComponent } from "../../transaction/create/dialog/transaction-create-dialog.component";
import { TransactionCreateComponent } from "../../transaction/create/transaction-create.component";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { AssetService } from "../../../services/asset.service";
import { ReviewService } from "../../../services/review.service";
import { SearchService } from "../../../services/search.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { Alert } from "../../../models/alert";
import { Config } from "../../../config/settings";
import { File as FileModel } from "../../../models/file";
import { Location } from "../../../models/location";
import { Message } from "../../../models/message";
import { Asset } from "../../../models/asset";
import { Review, ReviewType } from "../../../models/review";
import { Search } from "../../../models/search";
import { Transaction, TransactionType } from "../../../models/transaction";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";
import { MatDialog } from "@angular/material/dialog";

@Component({
  selector: "asset-read-component",
  templateUrl: "asset-read.html",
  styleUrls: ["asset-read.css"],
})
export class AssetReadComponent implements OnInit, OnDestroy {
  // private bitcoin: Bitcoin = new Bitcoin();
  // private ethereum: Ethereum = new Ethereum();
  private authSubscription: Subscription = new Subscription();
  private assetSubscription: Subscription = new Subscription();
  private reviewSubscription: Subscription = new Subscription();
  private searchSubscription: Subscription = new Subscription();
  public asset: Asset = new Asset();
  // private review: Review = new Review();
  private transaction: Transaction = new Transaction();
  private authUser: User = new User();
  private user: User = new User();
  private search: Search = new Search();
  //  private bitcoinWallet: Wallet = new Wallet();
  // private ethereumWallet: Wallet = new Wallet();
  private tokoWallet: Wallet = new Wallet();
  private permission: boolean = false;

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    public messageDialog: MatDialog,
    public mobileDialog: MatDialog,
    private assetService: AssetService,
    public reviewDialog: MatDialog,
    public transactionDialog: MatDialog,
    private reviewService: ReviewService,
    private searchService: SearchService,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private translateService: TranslateService,
    private transactionService: TransactionService
  ) {}

  ngOnInit() {
    //Load authorized user
    this.authUser = this.authenticationService.getLocalUser();
    //Load previous search
    this.searchSubscription = this.searchService.getSearch().subscribe(
      (search: Search) => {
        this.search = search;
      },
      (error: Error) => {
        console.log(error);
      },
      () => {}
    );
    //Load asset
    this.route.params.subscribe((p) => {
      let id = p["id"];
      if (id) {
        console.log("this.assetService.readAsset(id).subscribe");
        this.assetService.readAsset(id).subscribe(
          (asset) => {
            this.asset = asset;
            this.user = asset.user;
            console.log(asset);

            // this.bitcoinWallet = Wallet.readBitcoin(this.asset.user.wallets);
            //    this.ethereumWallet = Wallet.readEthereum(this.asset.user.wallets);
            //   this.tokoWallet = Wallet.readToko(this.asset.user.wallets);
            //  this.setTransactionName();
          },
          (error) => {
            this.alertService.error(<any>error);
          },
          () => {
            //  this.setReviews();
          }
        );
      } else {
        console.log(
          "this.assetSubscription = this.assetService.getAsset().subscribe("
        );
        this.assetSubscription = this.assetService.getAsset().subscribe(
          (asset) => {
            this.asset = asset;
            console.log(asset);
            // this.tokoWallet = Wallet.readToko(this.asset.user.wallets);

            // this.setTransactionName();
            //  this.bitcoinWallet = Wallet.readBitcoin(asset.user.wallets);
            //  this.ethereumWallet = Wallet.readEthereum(asset.user.wallets);
            // this.tokoWallet = Wallet.readToko(asset.user.wallets);
          },
          (error) => {
            console.log(error);
          },
          () => {
            //  this.setReviews();
          }
        );
      }
    });
  }

  ngAfterContentInit() {
    this.setReviews();
    this.setTransactionName();
  }

  ngAfterViewInit() {
    this.checkPermissions();
  }

  ngOnDestroy() {
    if (this.assetSubscription) {
      this.assetSubscription.unsubscribe();
    }
    if (this.reviewSubscription) {
      this.reviewSubscription.unsubscribe();
    }
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  private isImageEmpty(index: Number) {
    if (this.asset.images) {
      // console.log("image found")
      if (this.asset.images.length > index) {
        // console.log("image found:" + index)
        return true;
      } else {
        // console.log("no image found:" + index)
        return false;
      }
    } else {
      // console.log("no image found")
      return false;
    }
  }

  /* private isEthereumWalletEmpty() {
         return Wallet.isEmpty(this.ethereumWallet);
     }*/

  private isTransactionVisible(): boolean {
    return Config.business_model_transactions;
  }

  private isAliasVisible(): boolean {
    return Config.business_model_alias;
  }

  private isMobilVisible(): boolean {
    return Config.business_model_mobile;
  }

  private isAddressVisible(): boolean {
    return Config.business_model_address;
  }

  private checkPermissions(): boolean {
    if (!this.asset || !this.user || !this.authUser) {
      this.permission = false;
      return false;
    } else if (User.equals(this.user, this.authUser)) {
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

  /* private getBitcoinWallet(): string {
         if (this.asset && this.asset.user && this.asset.user.wallets &&
             Wallet.containsBitcoin(this.asset.user.wallets)) {
             return Wallet.readBitcoin(this.asset.user.wallets).name;
         } else { 
             return "";
         }       
     }*/

  /*private getEthereumWallet(): string{
        if (this.asset && this.asset.user && this.asset.user.wallets &&
            Wallet.containsEthereum(this.asset.user.wallets)) {
            return Wallet.readEthereum(this.asset.user.wallets).name;
        } else { 
            return "";
        }           
     }*/

  /* private getReviewAverage(): number {
        return Review.getAverage(this.asset.reviews);
    }
    public getPositive(): string {
        return Review.getPositive(this.asset.reviews).toString();
    }

    private getNegative(): string {
        return Review.getNegative(this.asset.reviews).toString();
    }*/

  private setReviews() {
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
        () => {}
      );
  }

  private setTransactionName() {
    this.translateService
      .get("PRODUCT")
      .subscribe((translatedPhrase: string) => {
        this.transaction.description =
          <any>translatedPhrase + ": " + this.asset.title;
      });
  }

  private isFileVisible() {
    if (Config.business_model_files && !FileModel.isEmpty(this.asset.file)) {
      return true;
    } else {
      return false;
    }
  }

  /* private handleFileDownloadEvent() {
    console.log("handleFileDownloadEvent");
    this.assetService.readFile(this.asset.id).subscribe(
      //Json script returned
      (asset) => {
        console.log("handleFileDownloadEvent() start download");
        // this.downloadFile(data);
        this.saveJSONToFile(asset.file.data, asset.file.type, asset.file.name);
      },
      (error) => {
        console.log("handleFileDownloadEvent() error");
        this.alertService.error(<any>error);
      },
      () => {
        console.log("handleFileDownloadEvent() end");
      }
    );
  }*/

  private saveJSONToFile(uri: any, type: string, filename: string) {
    var blob = this.dataURItoBlob(uri),
      e = document.createEvent("MouseEvents"),
      a = document.createElement("a");

    a.download = filename;
    a.href = window.URL.createObjectURL(blob);
    a.dataset.downloadurl = [type, a.download, a.href].join(":");
    e.initMouseEvent(
      "click",
      true,
      false,
      window,
      0,
      0,
      0,
      0,
      0,
      false,
      false,
      false,
      false,
      0,
      null
    );
    a.dispatchEvent(e);
  }

  private dataURItoBlob(dataURI: string): Blob {
    // convert base64/URLEncoded data component to raw binary data held in a string
    if (dataURI.split(",")[0].indexOf("base64") >= 0) {
      var byteString = atob(dataURI.split(",")[1]);

      // separate out the mime component
      var mimeString = dataURI.split(",")[0].split(":")[1].split(";")[0];

      // write the bytes of the string to a typed array
      // if (byteString != null)
      var ia = new Uint8Array(byteString.length);
      for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
      }

      return new Blob([ia], { type: mimeString });
    }
    return new Blob();
  }

  private openMessageDialog() {
    if (this.checkPermissions()) {
      let dialogRef = this.messageDialog.open(MessageCreateDialogComponent, {
        height: "20%",
        width: "20%",
      });
      var users: Array<User> = new Array<User>();
      users.push(this.user);
      //  dialogRef.componentInstance.setUsers(users);
    }
  }

  private openMobileDialog() {
    if (this.checkPermissions()) {
      let dialogRef = this.mobileDialog.open(MobileDialogComponent, {
        height: "20%",
        width: "20%",
      });
      dialogRef.componentInstance.setMobile(this.asset.user.mobile);
    }
  }

  private openReviewDialog() {
    if (this.checkPermissions()) {
      var review: Review = new Review();
      review.type = ReviewType.Stars;
      review.assetId = this.asset.id;
      review.userId = this.asset.user.id;
      review.judgeId = localStorage.getItem("id")!;
      this.reviewService.setReview(review);
      let dialogRef = this.reviewDialog.open(ReviewCreateDialogComponent, {});
      // height: '512px'
      // dialogRef.componentInstance.setUser(this.asset.user);
      dialogRef.componentInstance.setAsset(this.asset);
    }
  }

  private openTransactionDialog() {
    console.log("openTransactionDialog()");
    //NOTE* If user only wants to send donation, other user has to create fee
    if (this.checkPermissions()) {
      if (this.transaction.itemValue == 0) {
        this.alertService.error("ERROR_FEE_REQUIRED");
      } else if (this.transaction.quantity == 0) {
        this.alertService.error("ERROR_QUANTITY_REQUIRED");
      } else {
        //NOTE:Transaction name set on getUser()
        let dialogRef = this.transactionDialog.open(
          TransactionCreateDialogComponent,
          {}
        );
        //Set transaction seller
        var seller = this.asset.user;
        seller.type = "seller";

        this.transaction.users.push(seller);
        //Set transaction buyer
        var buyer = this.authenticationService.getLocalUser();
        buyer.type = "buyer";
        this.transaction.users.push(buyer);
        this.transaction.type = TransactionType.CashOnDelivery;
        // this.transaction.asset = Asset.simplify(this.asset);
        this.transaction.quantity = 1;
        this.transaction.totalValue =
          this.transaction.quantity * this.transaction.itemValue;
        this.transactionService.setTransaction(this.transaction);
        //dialogRef.componentInstance.setTransaction(transaction);
        //dialogRef.close()
      }
    }
  }
}
