import { AfterContentInit, AfterViewChecked, ChangeDetectionStrategy, Component, Inject, inject, OnInit, SecurityContext } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { ActivatedRoute, Router } from "@angular/router";
import { Environment } from "../../environments/environment";
import { Subscription } from "rxjs";
import { AuthenticationService } from "../services/authentication.service";
import { AlertService } from "../services/alert.service";
import { AssetService } from "../services/asset.service";
import { TranslateService } from "@ngx-translate/core";
import { TransactionService } from "../services/transaction.service";
import { UserService } from "../services/user.service";
import { WalletService } from "../services/wallet.service";
import { User } from "../models/user";
import { Transaction } from "../models/transaction";
import { ToolbarService } from "../services/toolbar.service";
import { StorageService } from "@services/storage.service";


@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: "application",
  standalone: false,
  styleUrls: ["application.css"],
  templateUrl: "application.html",
})
export class AppComponent implements OnInit, AfterContentInit, AfterViewChecked {

  public authUser: User = new User();
  public currentLanguage: any;
  public languages: any[] = [];
  public title: String = "";
  private toolbarSubscription: Subscription = new Subscription();

  private alertService = inject(AlertService);
  private assetService = inject(AssetService);
  private authenticateService = inject(AuthenticationService);
  private transactionService = inject(TransactionService);
  private userService = inject(UserService);
  private walletService = inject(WalletService);
  private router = inject(Router);
 // public sanitizer :DomSanitizer;
  private toolbarService = inject(ToolbarService);
  protected store = inject(StorageService);
  
  ngOnInit() {
    try {
      this.authUser = this.authenticateService.getMyUserFromStorage();
    } catch (e) { }
    this.languages = [
      { display: "Chinese", value: "ch" },
      { display: "English", value: "en" },
      { display: "Español", value: "es" },
      { display: "German", value: "de" },
      { display: "Italiano", value: "it" },
    ];
    this.currentLanguage = this.authenticateService.getLocalLanguage();
  }

  ngOnDestroy() {
    if (this.toolbarSubscription) {
      this.toolbarSubscription.unsubscribe();
    }
  }

  ngAfterContentInit() {
    if (!this.currentLanguage) {
      this.currentLanguage = Environment.Default.Language;
      this.changeLanguage(this.currentLanguage);
    } else {
      this.changeLanguage(this.currentLanguage);
    }
  }

  ngAfterViewChecked(): void {
    this.getToolbar();
  }

  public getToolbar() {
    this.toolbarSubscription = this.toolbarService.getTitleKey().subscribe(
      (title: String) => {
        $localize`{title}`
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  //*** Authentication ***/

  public login() {
    this.router.navigate(["/login"]);
  }

  public logout() {
    this.authenticateService.logout();
    this.router.navigate(["/login"]);
  }

  //*** Language options ***/

  public changeLanguage(language: string) {
    // this.translationService.use(language);
    this.authenticateService.setLocalLanguage(language);
  }

  public getLanguageText() {
    for (var i = 0; i < this.languages.length; i++) {
      if (this.currentLanguage == this.languages[i].value) {
        return this.languages[i].display;
      }
    }
  }

  //*** Menu links ***/

  public about() {
    this.router.navigate(["/about"]);
  }

  public barcode() {
    this.router.navigate(["/barcode"]);
  }

  public contactUs() {
    this.router.navigate(["/contact"]);
  }

  public conversations() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/conversation/list"]);
    }
  }

  public dashboard() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/dashboard"]);
    }
  }

  public home() {
    this.router.navigate(["/home"]);
  }

  public faq() {
    this.router.navigate(["/faq"]);
  }

  public register() {
    if (!this.store.isAuthenticated()) {
      this.router.navigate(["/register"]);
    }
  }

  public gotoCalendar() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/user/read/calendar"]);
    }
  }

  public gotoGameDashboard() {
    this.router.navigate(["/game/dashboard"]);
  }

  public gotoFourWayChess() {
    this.router.navigate(["/game/fourwaychess"]);
  }

  public gotoTwoWayChess() {
    this.router.navigate(["/game/twowaychess"]);
  }

  public gotoMyUser() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/user/edit"]);
    }
  }

  public gotoPurchases() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/asset/table"], {
        queryParams: { type: "purchases" },
      });
    }
  }

  public gotoSales() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/asset/table"], {
        queryParams: { type: "sales" },
      });
    }
  }

  public gotoMyLocations() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/location/list"]);
    }
  }

  public gotoMyProducts() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/asset/my/list"], {
        queryParams: { type: "product" },
      });
    }
  }

  public gotoMyServices() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/asset/my/list"], {
        queryParams: { type: "service" },
      });
    }
  }

  public gotoMyMessages() {
    console.log("gotoMyMessages()");
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/message/list"]);
    }
  }

  public gotoSearchAssets() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/asset/search/generic"]);
    }
  }

  public gotoReviews() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/review/list"]);
    }
  }

  public searchUsers() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/user/search"]);
    }
  }

  public searchProducts() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/asset/search"], {
        queryParams: { type: "product" },
      });
    }
  }

  public searchServices() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/asset/search"], {
        queryParams: { type: "service" },
      });
    }
  }

  public searchTransactions() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/transaction/search"]);
    }
  }


  public gotoTransactionFilter() {
    if (this.store.isAuthenticated()) {
      this.transactionService.searchByBuyerOrSeller().subscribe(
        (transactions: Array<Transaction>) => {
          console.log(transactions);
          this.transactionService.setTransactions(transactions);
        },
        (error: any) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.router.navigate(["/transaction/filter"]);
        }
      );
    }
  }

  public searchAssets() {
    if (this.store.isAuthenticated()) {
      this.transactionService.searchByBuyerOrSeller().subscribe(
        (transactions: Array<Transaction>) => {
          console.log(transactions);
          this.transactionService.setTransactions(transactions);
        },
        (error: any) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.router.navigate(["/asset/search"]);
        }
      );
    }
  }

  public gotoUserMap() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/user/map"]);
    }
  }

  public gotoAssetMap() {
    if (this.store.isAuthenticated()) {
      this.router.navigate(["/asset/map"]);
    }
  }

  public gotoWallets() {
    if (this.store.isAuthenticated()) {
      this.walletService.setWallets(this.authUser.wallets);
      this.router.navigate(["/wallet/list"]);
    }
  }

  public showCalendar() {
    return Environment.Menu.Events;
  }

  public showFiles() {
    return Environment.Menu.Files;
  }

  public showAssets() {
    return Environment.Menu.Assets;
  }

  public showTransactions() {
    return Environment.Menu.Transactions;
  }

  public isCurrentLanguage(lang: string) {
    return true; //lang === this.translationService.currentLang;
  }

}
