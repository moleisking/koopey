import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { WalletService } from "../../../services/wallet.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { Environment } from "../../../../environments/environment";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";
import { CodeToSymbolPipe } from "@pipes/code-to-symbol.pipe";
import { QRCodeComponent } from 'angularx-qrcode';
import { MatCardModule } from "@angular/material/card";
import { NgIf } from "@angular/common";

@Component({
  imports: [CodeToSymbolPipe, MatCardModule, QRCodeComponent],

  selector: "wallet-read-component",
  standalone: true,
  templateUrl: "wallet-read.html",
  styleUrls: ["wallet-read.css"],
})
export class WalletReadComponent implements OnInit {
  public wallet: Wallet = new Wallet();

  constructor(
    private alertService: AlertService,
    private walletService: WalletService,
    private userService: UserService,
    private router: Router,
    public sanitizer: DomSanitizer
  ) { }

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

  public hasTransactions(): boolean {
    return Environment.Menu.Transactions;
  }
}
