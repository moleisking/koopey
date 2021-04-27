import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { BarcodeService } from "../../../services/barcode.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { WalletService } from "../../../services/wallet.service";
import { DateHelper } from "../../../helpers/DateHelper";
import { TransactionHelper } from "../../../helpers/TransactionHelper";
import { Alert } from "../../../models/alert";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";
import { MatDatepickerIntl } from "@angular/material/datepicker";

@Component({
  selector: "transaction-update-component",
  templateUrl: "transaction-update.html",
  styleUrls: ["transaction-update.css"],
})
export class TransactionUpdateComponent implements OnInit, OnDestroy {
  private barcodeSubscription: Subscription = new Subscription();
  private clickSubscription: Subscription = new Subscription();
  private transactionSubscription: Subscription = new Subscription();

  public transaction: Transaction = new Transaction();
  private startDate: Date = new Date();
  private endDate: Date = new Date();
  private barcode: string = "";
  private startTime: string = "08:00";
  private endTime: string = "09:00";
  private min: Date = new Date();
  private max: Date = new Date();
  private bitcoinWallet: Wallet = new Wallet();
  private ethereumWallet: Wallet = new Wallet();
  private tokoWallet: Wallet = new Wallet();
  private userWallets: Array<Wallet> = new Array<Wallet>();
  /*@ViewChild(MdDatepicker ) datepicker: MdDatepicker<Date>;*/

  constructor(
    protected alertService: AlertService,
    private barcodeService: BarcodeService,
    private clickService: ClickService,
    private datePickerService: MatDatepickerIntl,
    private route: ActivatedRoute,
    protected router: Router,
    protected transactionService: TransactionService,
    private translateService: TranslateService,
    private userService: UserService,
    private walletService: WalletService /*,private dateAdapter:DateAdapter<Date>*/
  ) {
    //dateAdapter.setLocale('de'); // DD.MM.YYYY
  }

  ngOnInit() {
    this.transactionSubscription = this.transactionService
      .getTransaction()
      .subscribe(
        (transaction) => {
          this.transaction = transaction;
        },
        (error) => {
          console.log(error);
        },
        () => {}
      );
    //Read barcode from previous view
    this.barcodeSubscription = this.barcodeService.getBarcode().subscribe(
      (barcode) => {
        this.barcode = barcode;
      },
      (error) => {
        console.log(error);
      },
      () => {}
    );
  }

  ngAfterContentInit() {
    if (this.isQuote() || this.isInvoice()) {
      if (this.isAuthBuyer()) {
        this.clickService.createInstance(
          ActionIcon.PAYMENT,
          CurrentComponent.TransactionUpdateComponent
        );
        this.clickSubscription = this.clickService
          .getTransactionUpdateClick()
          .subscribe(() => {
            //Buyer completes transactions
            this.update();
          });
      } else if (this.barcodeService.isEmpty() && this.isAuthSeller()) {
        console.log(
          "(this.barcodeService.isEmpty() && (this.transaction.seller.id == localStorage.getItem(id)))"
        );
        this.clickService.createInstance(
          ActionIcon.CAMERA,
          CurrentComponent.TransactionUpdateComponent
        );
        this.clickSubscription = this.clickService
          .getTransactionUpdateClick()
          .subscribe(() => {
            //Seller completes transactions
            this.router.navigate(["/barcode"]);
          });
      } else if (!this.barcodeService.isEmpty() && this.isAuthSeller()) {
        console.log(
          "(!this.barcodeService.isEmpty() && (this.transaction.seller.id == localStorage.getItem(id)))"
        );
        this.transaction.secret = this.barcode;
        this.barcodeService.clear();
        this.updateTransactionBySeller();
      }
    } else {
      this.router.navigate(["/transaction/read/list"]);
    }
  }

  ngAfterViewInit() {
    this.max.setMonth(this.max.getMonth() + 6);
  }

  ngOnDestroy() {
    if (this.barcodeSubscription) {
      this.barcodeSubscription.unsubscribe();
    }
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
  }

  private createTransactionAuditTrail() {
    //NOTE: update asset quantity if not necesarry as already doen in create
    console.log("createTransactionTrail()");
    this.transactionService.create(this.transaction).subscribe(
      () => {},
      (error) => {
        this.alertService.error(<any>error);
      },
      () => {
        /*  if (this.useRoute) {
                      console.log("createTransaction() route");
                      this.router.navigate(["/transaction/read/list"])
                  } else {
                      console.log("createTransaction() no route");
                      this.alertService.success("INFO_COMPLETE");
                  }*/
      }
    );
  }

  private isAuthBuyer() {
    if (
      this.transaction &&
      this.transaction.users &&
      this.transaction.users.length >= 2
    ) {
      for (var i = 0; i < this.transaction.users.length; i++) {
        if (
          User.isBuyer(this.transaction.users[i]) &&
          this.transaction.users[i].id == localStorage.getItem("id")
        ) {
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  }

  private isInvoice() {
    return Transaction.isInvoice(this.transaction);
  }

  private isQuote() {
    return Transaction.isQuote(this.transaction);
  }

  private isReceipt() {
    return Transaction.isReceipt(this.transaction);
  }

  private isAuthSeller() {
    if (
      this.transaction &&
      this.transaction.users &&
      this.transaction.users.length >= 2
    ) {
      for (var i = 0; i < this.transaction.users.length; i++) {
        if (
          User.isSeller(this.transaction.users[i]) &&
          this.transaction.users[i].id == localStorage.getItem("id")
        ) {
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  }

  public getTransaction() {
    this.route.params.subscribe((p) => {
      let id = p["id"];
      if (id) {
        this.transactionService.readTransaction(id).subscribe(
          (transaction) => {
            this.transaction = transaction;
          },
          (error) => {
            this.alertService.error(<any>error);
          },
          () => {
            console.log("gettransaction success");
          }
        );
      } else {
        this.transactionSubscription = this.transactionService
          .getTransaction()
          .subscribe(
            (transaction) => {
              this.transaction = transaction;
            },
            (error) => {
              console.log(error);
            },
            () => {}
          );
      }
    });
  }

  /* private getWallets() {
        this.walletService.readWallets(this.).subscribe(
            wallets => {
                this.userWallets = wallets;
                this.bitcoinWallet = Wallet.readBitcoin(wallets);
                this.ethereumWallet = Wallet.readEthereum(wallets);
                this.tokoWallet = Wallet.readToko(wallets);
            },
            error => { this.alertService.error(<any>error) },
            () => {
                this.getBitcoinBalance();
                this.getEthereumBalance();
                console.log("getMyUser success");
            }
        );
    }*/

  private onQuantityChange($event: any) {
    this.transaction.totalValue =
      this.transaction.itemValue * this.transaction.quantity;
  }

  private onStartTimeStampChange(event: any) {
    console.log("onStartTimeStampChange");
    if (this.startDate) {
      this.startDate.setHours(Number(this.startTime.split(":")[0]));
      this.startDate.setMinutes(Number(this.startTime.split(":")[1]));
      if (
        this.startDate.getFullYear() > 1900 &&
        this.startDate.getMonth() >= 0 &&
        this.startDate.getDate() > 0
      ) {
        this.transaction.startTimeStamp = this.startDate.getTime();
        console.log(this.startDate);
        console.log(this.startDate.getTime());
      }
    }
  }

  private onEndTimeStampChange(event: any) {
    console.log("onEndTimeStampChange");
    if (this.endDate) {
      this.endDate.setHours(Number(this.endTime.split(":")[0]));
      this.endDate.setMinutes(Number(this.endTime.split(":")[1]));
      if (
        this.endDate.getFullYear() > 1900 &&
        this.endDate.getMonth() >= 0 &&
        this.endDate.getDate() > 0
      ) {
        this.transaction.endTimeStamp = this.endDate.getTime();
      }
    }
  }

  private showTransactionCompleteAlert(alert: Alert) {
    if (Alert.isSuccess(alert)) {
      this.alertService.success("INFO_COMPLETE");
    } else {
      this.alertService.error(alert.message);
    }
  }

  private update() {
    this.updateTransactionFiat();
  }

  private updateTransactionFiat() {
    if (Transaction.isEmpty(this.transaction)) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.transactionService.create(this.transaction).subscribe(
        () => {
          this.router.navigate(["/transaction/read/list"]);
        },
        (error) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.alertService.success("INFO_COMPLETE");
        }
      );
    }
  }

  //https://bitcoin.org/en/developer-examples#simple-raw-transaction

  public updateTransactionBySeller() {
    /*this.transactionService.updateStateBySeller(this.transaction).subscribe(
            transaction => {
                this.transaction = transaction;
            },
            error => { this.alertService.error(<any>error) },
            () => { console.log("gettransaction success") }
        );*/
  }
}
