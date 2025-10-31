import { Component, OnInit, OnDestroy, ViewChild, ChangeDetectionStrategy, inject, Inject } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { TransactionService } from "../../../services/transaction.service";
import { AssetService } from "../../../services/asset.service";
import { UserService } from "../../../services/user.service";
import { WalletService } from "../../../services/wallet.service";
import { TransactionHelper } from "../../../helpers/TransactionHelper";
import { Alert } from "../../../models/alert";
import { Environment } from "./../../../../environments/environment";
import { Asset } from "../../../models/asset";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";
import { ModelHelper } from "./../../../helpers/ModelHelper";
import { UserType } from "./../../../models/type/UserType";
import { AssetType } from "./../../../models/type/AssetType";
import { CurrencyType } from "./../../../models/type/CurrencyType";
import { DomSanitizer } from "@angular/platform-browser";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { OperationType } from "./../../../models/type/OperationType";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: "transaction-edit-component",
  standalone: false,
  styleUrls: ["transaction-edit.css"],
  templateUrl: "transaction-edit.html",
})

//Note* Parameters such as buyer,seller and asset are normally set before TransactionCreateComponent. Quantity is controlled by form view.
export class TransactionEditComponent
  implements OnInit, OnDestroy {
  public formGroup!: FormGroup;
  private walletSubscription: Subscription = new Subscription();
  private transactionSubscription: Subscription = new Subscription();
  public transaction: Transaction = new Transaction();
  private operationType: String = "";
  /*@ViewChild(MdDatepicker ) datepicker: MdDatepicker<Date>;*/

  //calculate share of value
  // private buyerShareValue: number = 0;
  // private sellerShareValue: number = 0;

  protected alertService = inject(AlertService);
  protected authenticateService = inject(AuthenticationService);
  protected formBuilder = inject(FormBuilder);
  protected router = inject(Router);
  protected transactionService = inject(TransactionService);
 // public sanitizer = inject(DomSanitizer);
  protected assetService = inject(AssetService);
  protected userService = inject(UserService);
  protected walletService = inject(WalletService); /*,private dateAdapter:DateAdapter<Date>*/

  /*constructor(@Inject(DomSanitizer) sanitizer: DomSanitizer) {
    super(sanitizer);
  }*/
  
  ngOnInit() {
    this.transactionService.getType().subscribe((type) => {
      this.operationType = type;
    });

    this.formGroup = this.formBuilder.group({
      start: [this.transaction.start],
      end: [this.transaction.end],
      reference: [this.transaction.reference],
      currency: [this.transaction.currency],
      description: [
        this.transaction.description,
        [Validators.required, Validators.maxLength(150)],
      ],
      value: [
        this.transaction.value,
        [
          Validators.required,
          Validators.max(90),
          Validators.maxLength(10),
          Validators.min(-90),
          Validators.minLength(1),
        ],
      ],
      quantity: [
        this.transaction.quantity,
        [
          Validators.required,
          Validators.min(-180),
          Validators.minLength(1),
          Validators.max(180),
          Validators.maxLength(11),
        ],
      ],
      total: [
        this.transaction.total,
        [
          Validators.required,
          Validators.min(0),
          Validators.minLength(1),
          Validators.max(9999999999),
          Validators.maxLength(10),
        ],
      ],
      name: [
        this.transaction.name,
        [
          Validators.required,
          Validators.maxLength(100),
          Validators.minLength(3),
        ],
      ],
      type: [
        this.transaction.type,
        [Validators.minLength(4), Validators.maxLength(8)],
      ],
    });
  }

  ngAfterContentInit() {
    if (this.operationType === OperationType.Update) {
      this.transactionService.getTransaction().subscribe((transaction) => {
        this.transaction = transaction;
      });
    }
  }

  ngAfterViewInit() {
    //AuthUser must be listed in the transaction, add if not already added
    var authUser = this.authenticateService.getMyUserFromStorage();
    authUser.type = UserType.Buyer;
    /*  if (!ModelHelper.contains(this.transaction.users, authUser)) {
      this.transaction.users.push(authUser);
    }*/
  }

  ngOnDestroy() {
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
    if (this.walletSubscription) {
      this.walletSubscription.unsubscribe();
    }
  }

  private getTransaction() {
    this.transactionSubscription = this.transactionService
      .getTransaction()
      .subscribe(
        (transaction: Transaction) => {
          if (transaction) {
            this.transaction = transaction;
          } else {
            this.transaction = new Transaction();
          }
        },
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
  }

  public readWallets() {
    /* for (var i = 0; i < this.transaction.users.length; i++) {
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
    }*/
  }

  public review() {
    this.router.navigate(["/review/create"]);
  }

  public setTransaction(transaction: Transaction) {
    this.transaction = transaction;
  }

  public hasCurrency(currency: string): boolean {
    return Environment.Transaction.Currencies.includes(currency);
  }

  public onSellerAccountChange($event: any) {
    if (this.transaction.type == "AuthBuyerToUnAuthSeller") {
      var unAuthUser = new User();
      unAuthUser.id = Environment.Default.Id;
      unAuthUser.type = UserType.Seller;
      var wallet = new Wallet();
      wallet.currency = this.transaction.currency;
      //  wallet.name = this.sellerAccount;
      if ((this.transaction.currency = CurrencyType.Ethereum)) {
      }
    }
  }

  public onQuantityChange() {
    this.transaction.total = this.transaction.value * this.transaction.quantity;
  }

  public checkLocalBalances(): boolean {
    /* for (var i = 0; i < this.transaction.users.length; i++) {
      var buyerWallet = ModelHelper.find(
        this.transaction.users[i].wallets,
        CurrencyType.Local
      );
      if (buyerWallet) {
        if (
          buyerWallet.balance >=
          TransactionHelper.BuyerShareValue(this.transaction)
        ) {
          return true;
        } else {
          this.alertService.success("ERROR_NOT_ENOUGH_FUNDS");
          return false;
        }
      }
    }*/
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

  public save() {
    //readWallets

    this.readWallets();
    console.log(this.transaction);
    if (this.transaction && !ModelHelper.isEmpty(this.transaction)) {
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

  public createTransactionAuditTrail() {
    //Create transaction
    console.log("createTransactionTrail()");
    this.transactionService.create(this.transaction).subscribe(
      () => { },
      (error) => {
        this.alertService.error(<any>error);
      },
      () => {
        //update asset quantity if not null
        if (
          !ModelHelper.is(AssetType.Service, this.transaction.asset) &&
          ModelHelper.is(AssetType.Product, this.transaction.asset)
        ) {
          this.decrementQuantity();
        } else {
          /*  if (this.redirect) {
            console.log("createTransaction() route");
            this.router.navigate(["/transaction/read/list"]);
          } else {
            console.log("createTransaction() no route");
            this.alertService.success("INFO_COMPLETE");
          }*/
        }
      }
    );
  }

  public createTransactionLocal() {
    var cost = TransactionHelper.BuyerShareValue(this.transaction);
    //create all transactions
    /*for (var j = 0; j < this.transaction.users.length; j++) {
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
    }*/
    //createTransaction audit trail and update quantity
    this.createTransactionAuditTrail();
  }

  public decrementQuantity() {
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

  public showTransactionCompleteAlert(alert: Alert) {
    if (Alert.isSuccess(alert)) {
      this.alertService.success("INFO_COMPLETE");
    } else {
      this.alertService.error(alert.message);
    }
  }
}
