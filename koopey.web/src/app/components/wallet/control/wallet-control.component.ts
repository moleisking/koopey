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
import { WalletDialogComponent } from "../dialog/wallet-dialog.component";
import { AlertService } from "../../../services/alert.service";
import { UserService } from "../../../services/user.service";
import { WalletService } from "../../../services/wallet.service";
import { Alert } from "../../../models/alert";
import { Environment } from "../../../../environments/environment";
import { Location } from "../../../models/location";
import { Tag } from "../../../models/tag";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";

import { MatDialogModule } from "@angular/material/dialog";
import { CodeToSymbolPipe } from "@pipes/code-to-symbol.pipe";
import { MatCardModule } from "@angular/material/card";
import { MatListModule, MatNavList } from "@angular/material/list";

@Component({
  imports: [CodeToSymbolPipe, MatCardModule, MatListModule, MatNavList],
  selector: "wallet-control-component",
  standalone: true,
  templateUrl: "wallet-control.html",
})
export class WalletControlComponent implements OnInit {
  @Input() wallets: Array<Wallet> = new Array<Wallet>();

  constructor(
    private alertService: AlertService,
    public walletDialog: MatDialog,
    private userService: UserService,
    private walletService: WalletService
  ) {}

  ngOnInit() {}

  private readWalletDialog(wallet: Wallet) {
    let dialogRef = this.walletDialog.open(WalletDialogComponent, {
      height: "320px",
      width: "90vw",
    });
    dialogRef.componentInstance.setWallet(wallet);
  }
}
