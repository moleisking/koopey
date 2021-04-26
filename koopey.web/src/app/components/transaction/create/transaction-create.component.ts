import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { AssetService } from "../../../services/asset.service";
import { UserService } from "../../../services/user.service";
import { WalletService } from "../../../services/wallet.service";
import { TransactionHelper } from "../../../helpers/TransactionHelper";
import { Alert } from "../../../models/alert";
import { Config } from "../../../config/settings";
import { Asset } from "../../../models/asset";
import { Transaction, TransactionType } from "../../../models/transaction";
import { User, UserType } from "../../../models/user";
import { Wallet, CurrencyType } from "../../../models/wallet";

@Component({
  selector: "transaction-create-component",
  templateUrl: "transaction-create.html",
  styleUrls: ["transaction-create.css"],
})

//Note* Parameters such as buyer,seller and asset are normally set before TransactionCreateComponent. Quantity is controlled by form view.
export class TransactionCreateComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private walletSubscription: Subscription = new Subscription();
  private transactionSubscription: Subscription = new Subscription();
  protected transaction: Transaction = new Transaction();
  private sellerAccount: string = "";
  protected redirect: boolean = true;
  /*@ViewChild(MdDatepicker ) datepicker: MdDatepicker<Date>;*/

  //calculate share of value
  // private buyerShareValue: number = 0;
  // private sellerShareValue: number = 0;

  constructor(
    protected alertService: AlertService,
    protected authenticateService: AuthenticationService,
    protected clickService: ClickService,

    protected router: Router,
    protected transactionService: TransactionService,
    protected translateService: TranslateService,
    protected assetService: AssetService,
    protected userService: UserService,
    protected walletService: WalletService /*,private dateAdapter:DateAdapter<Date>*/
  ) {
    //dateAdapter.setLocale('de'); // DD.MM.YYYY
  }

  ngOnInit() {
    this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.TransactionCreateComponent
    );
    this.clickSubscription = this.clickService
      .getTransactionCreateClick()
      .subscribe(() => {
        this.createTransaction();
      });
  }

  ngAfterContentInit() {
    //Read stored transaction, then check auth user is added, then wallets for each user.
    this.transactionSubscription = this.transactionService
      .getTransaction()
      .subscribe(
        (transaction) => {
          if (transaction) {
            this.transaction = transaction;
          } else {
            this.transaction = new Transaction();
          }
        },
        (error) => {
          console.log(error);
        },
        () => {}
      );
  }

  ngAfterViewInit() {
    //AuthUser must be listed in the transaction, add if not already added
    var authUser = this.authenticateService.getLocalUser();
    authUser.type = UserType.Buyer;
    if (!User.contains(this.transaction.users, authUser)) {
      this.transaction.users.push(authUser);
    }
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
    if (this.walletSubscription) {
      this.walletSubscription.unsubscribe();
    }
  }

  protected readWallets() {
    for (var i = 0; i < this.transaction.users.length; i++) {
      this.walletSubscription = this.walletService
        .readWallets(this.transaction.users[i])
        .subscribe(
          (wallets) => {
            for (var j = 0; j < wallets.length; j++) {
              //Only copy wallet of selected currency, including authUser
              if (wallets[j].type == this.transaction.currency) {
                this.transaction.users[i].wallets.push(wallets[j]);
              }
            }
          },
          (error) => {
            console.log(error);
          },
          () => {}
        );
    }
  }

  public setTransaction(transaction: Transaction) {
    this.transaction = transaction;
  }

  private hasCurrency(currency: string): boolean {
    return Config.transaction_currencies.includes(currency);
  }

  protected onSellerAccountChange($event: any) {
    if (this.transaction.type == "AuthBuyerToUnAuthSeller") {
      var unAuthUser = new User();
      unAuthUser.id = Config.default_id;
      unAuthUser.type = UserType.Seller;
      var wallet = new Wallet();
      wallet.currency = this.transaction.currency;
      wallet.name = this.sellerAccount;
      if ((this.transaction.currency = CurrencyType.Ethereum)) {
      }
    }
  }

  protected onQuantityChange($event: any) {
    this.transaction.totalValue =
      this.transaction.itemValue * this.transaction.quantity;
  }

  private checkLocalBalances(): boolean {
    for (var i = 0; i < this.transaction.users.length; i++) {
      var buyerWallet = Wallet.readLocal(this.transaction.users[i].wallets);
      if (buyerWallet) {
        if (
          buyerWallet.value >=
          TransactionHelper.BuyerShareValue(this.transaction)
        ) {
          return true;
        } else {
          this.alertService.success("ERROR_NOT_ENOUGH_FUNDS");
          return false;
        }
      }
    }
    return false;
  }

  /*private checkQuantity(): boolean {
        if (Asset.isProduct(this.transaction.asset)) {
            if (this.transaction.asset.quantity <= this.transaction.quantity) {
                return true;
            } else {
                this.alertService.error("ERROR_TOO_MUCH_QUANTITY");
                return false;
            }
        } else {
            return true;
        }
    }*/

  private createTransaction() {
    //readWallets

    this.readWallets();
    console.log(this.transaction);
    if (this.transaction && !Transaction.isEmpty(this.transaction)) {
      if (
        this.transaction.currency == CurrencyType.Local &&
        this.checkLocalBalances()
      ) {
        this.createTransactionLocal();
      } else if (
        this.transaction.currency == CurrencyType.Euro ||
        this.transaction.currency == CurrencyType.BritishPound ||
        this.transaction.currency == CurrencyType.UnitedStatesDollar ||
        this.transaction.currency == CurrencyType.SouthAfricanRand
      ) {
        this.createTransactionAuditTrail();
        // this.createTransactionFiat();
      }
    } else {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    }
  }

  private createTransactionAuditTrail() {
    //Create transaction
    console.log("createTransactionTrail()");
    this.transactionService.create(this.transaction).subscribe(
      () => {},
      (error) => {
        this.alertService.error(<any>error);
      },
      () => {
        //update asset quantity if not null
        if (
          !Asset.isEmpty(this.transaction.asset) &&
          Asset.isProduct(this.transaction.asset)
        ) {
          this.decrementQuantity();
        } else {
          if (this.redirect) {
            console.log("createTransaction() route");
            this.router.navigate(["/transaction/read/list"]);
          } else {
            console.log("createTransaction() no route");
            this.alertService.success("INFO_COMPLETE");
          }
        }
      }
    );
  }

  private createTransactionLocal() {
    var cost = TransactionHelper.BuyerShareValue(this.transaction);
    //create all transactions
    for (var j = 0; j < this.transaction.users.length; j++) {
      if (this.transaction.users[j].type == "buyer") {
        //update buyer
        this.walletService
          .updateWalletByAddition(this.transaction.users[j].id, -cost)
          .subscribe(
            () => {},
            (error) => {
              this.alertService.error(<any>error);
            },
            () => {}
          );
      } else if (this.transaction.users[j].type == "seller") {
        //update seller
        this.walletService
          .updateWalletByAddition(this.transaction.users[j].id, cost)
          .subscribe(
            () => {},
            (error) => {
              this.alertService.error(<any>error);
            },
            () => {}
          );
      }
    }
    //createTransaction audit trail and update quantity
    this.createTransactionAuditTrail();
  }

  private decrementQuantity() {
    /*  this.assetService
      .updateQuantity(this.transaction.asset, -1 * this.transaction.quantity)
      .subscribe(
        () => {
          console.log("createTransaction() updateQuantity");
        },
        (error) => {
          this.alertService.error("ERROR");
          console.log(error);
        },
        () => {
          if (this.redirect) {
            console.log("createTransaction() updateQuantity route");
            this.router.navigate(["/transaction/read/list"]);
          } else {
            console.log("createTransaction() updateQuantity no route");
            this.alertService.success("INFO_COMPLETE");
          }
        }
      );*/
  }

  private showTransactionCompleteAlert(alert: Alert) {
    if (Alert.isSuccess(alert)) {
      this.alertService.success("INFO_COMPLETE");
    } else {
      this.alertService.error(alert.message);
    }
  }
}
