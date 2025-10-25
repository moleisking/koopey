import { AlertService } from "../../../services/alert.service";
import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  AfterViewChecked,
  AfterViewInit,
} from "@angular/core";
import { MatPaginator, MatPaginatorModule } from "@angular/material/paginator";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { MatSort } from "@angular/material/sort";
import { OperationType } from "../../../models/type/OperationType";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { WalletService } from "../../../services/wallet.service";
import { Wallet } from "../../../models/wallet";
import { CodeToSymbolPipe } from "@pipes/code-to-symbol.pipe";
import { MatIconModule } from "@angular/material/icon";

@Component({
  imports: [CodeToSymbolPipe,  MatIconModule,   MatPaginatorModule,  MatTableModule],
  selector: "wallet-list",
    standalone: true,
  styleUrls: ["wallet-list.css"],
  templateUrl: "wallet-list.html",
})
export class WalletListComponent
  implements AfterViewChecked, AfterViewInit, OnInit, OnDestroy {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild("paginatorElement") paginatorElement: MatPaginator | undefined;
  @ViewChild(MatSort) sort!: MatSort;
  private walletSubscription: Subscription = new Subscription();
  public wallets: Array<Wallet> = new Array<Wallet>();

  displayedColumns: string[] = [
    "name",
    "type",
    "description",
    "balance",
    "currency",
  ];
  dataSource = new MatTableDataSource<Wallet>();

  constructor(
    protected alertService: AlertService,
    private router: Router,
    private walletService: WalletService
  ) {}

  ngOnInit() {
    this.getWallets();
  }

  ngAfterViewChecked() {
    if (this.wallets.length <= 10) {
      this.paginatorElement!.disabled = true;
      this.paginatorElement!.hidePageSize = true;
      this.paginatorElement!.showFirstLastButtons = false;
    }
  }

  ngAfterViewInit() {
    this.refreshDataSource();
  }

  ngOnDestroy() {
    if (this.walletSubscription) {
      this.walletSubscription.unsubscribe();
    }
  }

  public create() {
    this.walletService.setType(OperationType.Create);
    this.router.navigate(["/wallet/edit"]);
  }

  public edit(wallet: Wallet) {
    this.walletService.setWallet(wallet);
    this.walletService.setType(OperationType.Update);
    this.router.navigate(["/wallet/edit"]);
  }

  private getWallets() {
    this.walletSubscription = this.walletService.readUserWallets().subscribe(
      (wallets: Array<Wallet>) => {
        console.log(wallets);
        this.wallets = wallets;
      },
      (error: Error) => {
        console.log(error);
      },
      () => {}
    );
  }

  private refreshDataSource() {
    this.dataSource = new MatTableDataSource<Wallet>(
      this.wallets as Array<any>
    );
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
