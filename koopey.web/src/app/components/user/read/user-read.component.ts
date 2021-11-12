import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { ReviewService } from "../../../services/review.service";
import { SearchService } from "../../../services/search.service";
import { UserService } from "../../../services/user.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { MessageCreateDialogComponent } from "../../message/create/dialog/message-create-dialog.component";
import { MobileDialogComponent } from "../../mobile/mobile-dialog.component";
import { TransactionDialogComponent } from "../../transaction/dialog/transaction-dialog.component";
import { Alert } from "../../../models/alert";
import { Environment } from "src/environments/environment";
import { Location } from "../../../models/location";
import { Message } from "../../../models/message";
import { Review } from "../../../models/review";
import { Search } from "../../../models/search";
import { Tag } from "../../../models/tag";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";
import { MatDialog } from "@angular/material/dialog";
import { ModelHelper } from "src/app/helpers/ModelHelper";

@Component({
  selector: "user-read-component",
  templateUrl: "user-read.html",
  styleUrls: ["user-read.css"],
})
export class UserReadComponent implements OnInit, OnDestroy {
  private authSubscription: Subscription = new Subscription();
  private userSubscription: Subscription = new Subscription();
  private searchSubscription: Subscription = new Subscription();
  // private review: Review = new Review();
  private auth: User = new User();
  public user: User = new User();
  //  private search: Search = new Search();
  private transaction: Transaction = new Transaction();

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    public messageDialog: MatDialog,
    public mobileDialog: MatDialog,
    public reviewDialog: MatDialog,
    public transactionDialog: MatDialog,
    private reviewService: ReviewService,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    public sanitizer: DomSanitizer,
    private searchService: SearchService,
    private translateService: TranslateService,
    private transactionService: TransactionService,
    public qrcodeDialog: MatDialog
  ) {}

  ngOnInit() {
    //Load authorized user
    //NOTE: Authorized user subscription for security. Don't use localstorage here.
    this.authSubscription = this.userService.readMyUser().subscribe(
      (user) => {
        this.auth = user;
      },
      (error) => {
        console.log(error);
      },
      () => {}
    );
    //NOTE: PrevioLoad previous search
    /* this.searchSubscription = this.searchService.getSearch().subscribe(
             (search) => { this.search = search; },
             (error) => { console.log(error); },
             () => { }
         );*/
    //Load user
    this.route.params.subscribe((p) => {
      let id = p["id"];
      if (id) {
        this.userSubscription = this.userService.read(id).subscribe(
          (user) => {
            this.user = user;
          },
          (error) => {
            this.alertService.error(<any>error);
          },
          () => {
            if (Environment.type != "production") {
              console.log(this.user);
            }
            this.setTransactionName();
          }
        );
      } else {
        this.userSubscription = this.userService.getUser().subscribe(
          (user) => {
            this.user = user;
          },
          (error) => {
            this.alertService.error(<any>error);
          },
          () => {
            if (Environment.type != "production") {
              console.log(this.user);
            }
            this.setTransactionName();
          }
        );
      }
    });
  }

  ngAfterContentInit() {}

  ngAfterViewInit() {}

  ngAfterViewChecked() {}

  ngOnDestroy() {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  private checkPermissions(): boolean {
    if (!this.user || !this.auth) {
      return false;
    } else if (User.isEmpty(this.user)) {
      this.alertService.error("ERROR_EMPTY");
      return false;
    } else if (ModelHelper.equals(this.user, this.auth)) {
      this.alertService.error("ERROR_OWN_USER");
      return false;
    } else if (!User.isAuthenticated(this.auth)) {
      this.alertService.error("ERROR_NOT_ACTIVATED");
      return false;
    } else if (!User.isLegal(this.auth)) {
      this.alertService.error("ERROR_NOT_LEGAL");
      return false;
    } else {
      return true;
    }
  }

  public isImageEmpty() {
    if (this.user.avatar && this.user.avatar.length > 0) {
      return true;
    } else {
      return false;
    }
  }

  /* private isBitcoinWalletEmpty() {
         return Wallet.isEmpty(this.bitcoinWallet);
     }
 
     private isEthereumWalletEmpty() {
         return Wallet.isEmpty(this.ethereumWallet);
     }*/

  public isAliasVisible(): boolean {
    return Environment.Menu.Alias;
  }

  public isMobileVisible(): boolean {
    return Environment.Menu.Mobile;
  }

  public isAddressVisible(): boolean {
    return Environment.Menu.Address;
  }

  /* private isBitcoinVisible(): boolean {
         return Config.business_model_bitcoin;
     }
 
     private isEthereumVisible(): boolean {
         return Config.business_model_ethereum;
     }*/

  public isTransactionVisible(): boolean {
    return Environment.Menu.Transactions;
  }

  public isMyUser() {
    //window.location used to get id because this method is run during form load and not through subscription
    if (
      window.location.href.substr(window.location.href.lastIndexOf("/") + 1) ==
      localStorage.getItem("id")
    ) {
      return true;
    } else {
      return false;
    }
  }

  public isLoggedIn() {
    if (localStorage.getItem("id")) {
      return true;
    } else {
      return false;
    }
  }

  /* private isCreditAvailable() {
         if (this.tokoWallet.value > 0) {
             return true;
         } else {
             return false;
         }
     }*/

  public getTagText(tag: Tag): string {
    return Tag.getText(tag, this.authenticationService.getLocalLanguage());
  }

  public getCurrency(): string {
    if (this.user && this.user.currency) {
      return this.user.currency.toUpperCase();
    } else {
      return Environment.Default.Currency;
    }
  }

  /*  private getReviewAverage(): number {
          return Review.getAverage(this.user.reviews);
      }
      public getPositive(): string {
          return Review.getPositive(this.user.reviews).toString();
      }
  
      private getNegative(): string {
          return Review.getNegative(this.user.reviews).toString();
      }*/

  private setTransactionName() {
    this.translateService.get("USER").subscribe((translatedPhrase: string) => {
      this.transaction.description =
        <any>translatedPhrase + ": " + this.user.alias;
    });
  }

  /*********  Events *********/

  /* private handleFeeSelectedEvent(fee: Fee) {
        console.log("handleSelectedFeeChange");
        console.log(fee);
        this.transaction.itemValue = fee.value;
        this.transaction.currency = fee.currency;
    }*/

  /*   private openReviewDialog() {
           if (!this.isMyUser() && this.isLoggedIn()) {
               var review: Review = new Review();
               review.judgeId = localStorage.getItem("id");
               this.reviewService.setReview(review);
               let dialogRef = this.reviewDialog.open(ReviewDialogComponent, {});
               dialogRef.componentInstance.setUser(this.user);
           }
       }*/

  public openMessageDialog() {
    if (this.checkPermissions()) {
      let dialogRef = this.messageDialog.open(MessageCreateDialogComponent, {
        width: "90%",
      });
      var message: Message = new Message();
      //Receiver
      var receiver: User = this.user;
      receiver.type = "receiver";
      message.users.push(receiver);
      //Sender
      var sender: User = this.authenticationService.getLocalUser();
      sender.type = "sender";
      message.users.push(sender);
      console.log("openMessageDialog");
      console.log(message);
      // var users: Array<User> = new Array<User>();
      //users.push(this.user);
      dialogRef.componentInstance.setMessage(message);
    }
  }

  public openMobileDialog() {
    if (this.checkPermissions()) {
      let dialogRef = this.mobileDialog.open(MobileDialogComponent, {
        height: "20%",
        width: "90%",
      });
      dialogRef.componentInstance.setMobile(this.user.mobile);
    }
  }

  /*  private openQRCodeDialog() {
          let dialogRef = this.qrcodeDialog.open(QRCodeDialogComponent, {
              height: '20%',
              width: '90%',
          });
          if (this.bitcoinWallet.name.length > 0) {
              dialogRef.componentInstance.setQRCodeText(this.bitcoinWallet.name);
          } else {
              dialogRef.componentInstance.setQRCodeText("Null");
          }
  
      }*/

  public openTransactionDialog() {
    if (this.checkPermissions()) {
      //NOTE* If user only wants to send donation, other user has to create fee
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
        //Set transaction buyer
        var buyer = this.authenticationService.getLocalUser();
        buyer.type = "buyer";
        this.transaction.buyer = buyer;
        //Set transaction seller
        var seller = this.user;
        seller.type = "seller";
        this.transaction.seller = seller;
        this.transaction.quantity = 1;
        this.transaction.currency = this.user.currency;
        this.transaction.total =
          this.transaction.quantity * this.transaction.value;
        this.transaction.start = new Date();
        this.transaction.end = new Date();
        this.transactionService.setTransaction(this.transaction);
        //dialogRef.componentInstance.setTransaction(this.transaction);
      }
    }
  }
}
