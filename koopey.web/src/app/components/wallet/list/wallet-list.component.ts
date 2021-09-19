import {
  Component,
  ElementRef,
  Input,
  OnInit,
  OnDestroy,
  ViewChild,
} from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { MatDialog, MatDialogRef } from "@angular/material/dialog";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { WalletService } from "../../../services/wallet.service";
import { WalletDialogComponent } from "../dialog/wallet-dialog.component";
import { Environment } from "../../../../environments/environment";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";

@Component({
  selector: "wallet-list-component",
  templateUrl: "wallet-list.html",
  styleUrls: ["wallet-list.css"],
})
export class WalletListComponent implements OnInit, OnDestroy {
  private walletSubscription: Subscription = new Subscription();
  public wallets: Array<Wallet> = new Array<Wallet>();
  public columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private router: Router,
    public sanitizer: DomSanitizer,
    private translateService: TranslateService,
    private walletService: WalletService,
    public walletDialog: MatDialog
  ) {}

  ngOnInit() {
    this.walletSubscription = this.walletService.readUserWallets().subscribe(
      (wallets: any) => {
        console.log(wallets);
        this.wallets = wallets;
        //  this.bitcoinWallet = Wallet.readBitcoin(wallets);
        //    this.ethereumWallet = Wallet.readEthereum(wallets);
      },
      (error: any) => {
        console.log(error);
      },
      () => {}
    );
  }

  ngAfterContentInit() {
    this.onScreenSizeChange();
  }

  ngOnDestroy() {
    if (this.walletSubscription) {
      this.walletSubscription.unsubscribe();
    }
  }

  public openWalletDialog(wallet: Wallet) {
    let dialogRef = this.walletDialog.open(WalletDialogComponent, {});
    dialogRef.componentInstance.setWallet(wallet);
  }

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

  public isEmpty(wallet: Wallet) {
    if (!wallet) {
      return true;
    } else {
      return false;
    }
  }

  public gotoWallet(wallet: Wallet) {
    this.walletService.setWallet(wallet);
    this.router.navigate(["/wallet/read/one"]);
  }

  public showNoResults(): boolean {
    if (!this.wallets || this.wallets.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
