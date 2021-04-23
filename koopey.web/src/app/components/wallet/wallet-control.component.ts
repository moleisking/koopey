import {
  Component,
  ElementRef,
  EventEmitter,
  forwardRef,
  Input,
  OnChanges,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
/*import {
  MaterialModule,
  MatAutocompleteModule,
  MatCardModule,
  MatIconModule,
  MatIconRegistry,
  MatInputModule,
  MatTabsModule,
  MatTextareaAutosize,
  MatDialog,
  MatDialogRef,
  MatAutocompleteTrigger,
} from "@angular/material";*/

import { WalletDialogComponent } from "./dialog/wallet-dialog.component";

import { AlertService } from "../../services/alert.service";
import { UserService } from "../../services/user.service";
import { WalletService } from "../../services/wallet.service";

import { CurrencyHelper } from "../../helpers/CurrencyHelper";

import { Alert } from "../../models/alert";

import { Config } from "../../config/settings";

import { Image } from "../../models/image";
import { Location } from "../../models/location";
import { Tag } from "../../models/tag";
import { User } from "../../models/user";
import { Wallet } from "../../models/wallet";

import "hammerjs";
import { MatDialogModule } from "@angular/material/dialog";

@Component({
  selector: "wallet-control-component",
  templateUrl: "../../views/wallet-control.html",
})
export class WalletControlComponent implements OnInit {
  private LOG_HEADER: string = "WALLET:CONTROL";

  @Input() wallets: Array<Wallet> = new Array<Wallet>();

  constructor(
    private alertService: AlertService,
    public walletDialog: MatDialog,
    private userService: UserService,
    private walletService: WalletService
  ) {}

  ngOnInit() {}

  private convertCurrencyCodeToSymbol(currency: string) {
    return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
  }

  private readWalletDialog(wallet: Wallet) {
    let dialogRef = this.walletDialog.open(WalletDialogComponent, {
      height: "320px",
      width: "90vw",
    });
    dialogRef.componentInstance.setWallet(wallet);
  }
}
