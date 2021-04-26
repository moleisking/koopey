import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { WalletService } from "../../../services/wallet.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { Config } from "../../../config/settings";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";

@Component({
  selector: "wallet-read-component",
  templateUrl: "wallet-read.html",
  styleUrls: ["wallet-read.css"],
})
export class WalletReadComponent implements OnInit {
  private wallet: Wallet = new Wallet();

  constructor(
    private alertService: AlertService,
    private walletService: WalletService,
    private userService: UserService,
    private router: Router,
    private sanitizer: DomSanitizer,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.walletService.getWallet().subscribe(
      (wallet) => {
        this.wallet = wallet;
      },
      (error) => {
        this.alertService.error(<any>error);
      },
      () => {
        if (this.wallet.currency == "tok") {
          this.wallet.name =
            window.location.protocol +
            "//" +
            window.location.host +
            "/user/read/one/" +
            localStorage.getItem("id");
        }
      }
    );
  }

  private hasTransactions(): boolean {
    return Config.business_model_transactions;
  }
}
