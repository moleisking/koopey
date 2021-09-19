import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { ActivatedRoute, Router } from "@angular/router";
import { Environment } from "src/environments/environment";
import { Subscription } from "rxjs";
import { AuthenticationService } from "../../services/authentication.service";
import { AlertService } from "../../services/alert.service";
import { ClickService, CurrentComponent } from "../../services/click.service";
import { AssetService } from "../../services/asset.service";
import { TranslateService } from "@ngx-translate/core";
import { TransactionService } from "../../services/transaction.service";
import { UserService } from "../../services/user.service";
import { WalletService } from "../../services/wallet.service";
import { User } from "../../models/user";
import { Search } from "../../models/search";
import { Transaction } from "src/app/models/transaction";
import { MatIconModule, MatIconRegistry } from "@angular/material/icon";

@Component({
  selector: "application",
  templateUrl: "application.html",
  styleUrls: ["application.css"],
})
export class AppComponent implements OnInit {
  private actionVisibleSubscription: Subscription = new Subscription();
  private actionIconSubscription: Subscription = new Subscription();
  private currentComponentSubscription: Subscription = new Subscription();

  public supportedLanguages: any[] = [];
  public currentLanguage: any;
  public authUser: User = new User();
  public actionIcon: String = "error";
  public actionVisible: Boolean = false;

  constructor(
    private alertService: AlertService,
    private assetService: AssetService,
    private authenticateService: AuthenticationService,
    private clickService: ClickService,
    private router: Router,
    public sanitizer: DomSanitizer,
    private translateService: TranslateService,
    private transactionService: TransactionService,
    private userService: UserService,
    private walletService: WalletService
  ) {}

  ngOnInit() {
    try {
      this.authUser = this.authenticateService.getLocalUser();
    } catch (e) {}
    this.supportedLanguages = [
      { display: "Chinese", value: "ch" },
      { display: "English", value: "en" },
      { display: "Español", value: "es" },
      { display: "German", value: "de" },
      { display: "Italiano", value: "it" },
    ];
    this.currentLanguage = this.authenticateService.getLocalLanguage();
  }

  ngOnDestroy() {
    if (this.actionIconSubscription) {
      this.actionIconSubscription.unsubscribe();
    }
    if (this.actionVisibleSubscription) {
      this.actionVisibleSubscription.unsubscribe();
    }
    if (this.currentComponentSubscription) {
      this.currentComponentSubscription.unsubscribe();
    }
  }

  ngAfterContentInit() {
    console.log("ngAfterContentInit called");
    //Used to update language html elements after component load
    //var option = (<HTMLSelectElement>document.getElementById("lstLanguage"));
    if (!this.currentLanguage) {
      console.log("ngAfterContentInit !this.localLanguage");
      // this language will be used as a fallback when a translation isn't found in the current language
      this.currentLanguage = Environment.Default.Language;
      //option.value = this.localLanguage
      this.changeLanguage(this.currentLanguage);
    } else {
      console.log("ngAfterContentInit this.localLanguage");
      // this language will be used as a fallback when a translation isn't found in the current language
      //option.value = this.localLanguage
      this.changeLanguage(this.currentLanguage);
    }

    this.showActionButton();
  }

  public click() {
    this.currentComponentSubscription = this.clickService
      .getCurrentComponent()
      .subscribe(
        (currentComponent: any) => {
          if (currentComponent == CurrentComponent.AboutComponent) {
            this.clickService.setAboutClick();
          } else if (
            currentComponent == CurrentComponent.BarcodeScannerControlComponent
          ) {
            this.clickService.setBarcodeScannerClick();
          } else if (currentComponent == CurrentComponent.ContactComponent) {
            this.clickService.setContactClick();
          } else if (
            currentComponent == CurrentComponent.ImageCreateComponent
          ) {
            this.clickService.setImageCreateClick();
          } else if (currentComponent == CurrentComponent.ImageListComponent) {
            this.clickService.setImageListClick();
          } else if (currentComponent == CurrentComponent.ImageReadComponent) {
            this.clickService.setImageReadClick();
          } else if (
            currentComponent == CurrentComponent.ImageUpdateComponent
          ) {
            this.clickService.setImageUpdateClick();
          } else if (currentComponent == CurrentComponent.AssetListComponent) {
            this.clickService.setAssetListClick();
          } else if (currentComponent == CurrentComponent.AssetMapComponent) {
            this.clickService.setAssetMapClick();
          } else if (
            currentComponent == CurrentComponent.AssetUpdateComponent
          ) {
            this.clickService.setAssetUpdateClick();
          } else if (currentComponent == CurrentComponent.AssetReadComponent) {
            this.clickService.setAssetReadClick();
          } else if (
            currentComponent == CurrentComponent.AssetSearchComponent
          ) {
            this.clickService.setAssetCreateClick();
          } else if (
            currentComponent == CurrentComponent.TransactionSearchComponent
          ) {
            this.clickService.setTransactionSearchClick();
          } else if (
            currentComponent == CurrentComponent.MemberSearchComponent
          ) {
            this.clickService.setMemberSearchClick();
          } else if (
            currentComponent == CurrentComponent.ServiceSearchComponent
          ) {
            this.clickService.setServiceSearchClick();
          } else if (
            currentComponent == CurrentComponent.TransactionCreateComponent
          ) {
            this.clickService.setTransactionCreateClick();
          } else if (
            currentComponent == CurrentComponent.TransactionListComponent
          ) {
            this.clickService.setTransactionListClick();
          } else if (
            currentComponent == CurrentComponent.TransactionUpdateComponent
          ) {
            this.clickService.setTransactionUpdateClick();
          } else if (
            currentComponent == CurrentComponent.UserCalendarComponent
          ) {
            this.clickService.setUserCalendarClick();
          } else if (currentComponent == CurrentComponent.UserCreateComponent) {
            this.clickService.setUserCreateClick();
          } else if (currentComponent == CurrentComponent.UserListComponent) {
            this.clickService.setUserListClick();
          } else if (currentComponent == CurrentComponent.UserMapComponent) {
            this.clickService.setUserMapClick();
          } else if (currentComponent == CurrentComponent.UserUpdateComponent) {
            this.clickService.setUserUpdateClick();
          } else if (currentComponent == CurrentComponent.UserReadComponent) {
            this.clickService.setUserReadClick();
          } else if (
            currentComponent == CurrentComponent.AssetCreateComponent
          ) {
            this.clickService.setAssetCreateClick();
          }
        },
        (error: any) => {
          this.actionVisible = false;
        },
        () => {}
      );
    /**/
  }

  //*** Authentication ***/

  public isAuthenticated() {
    return this.authenticateService.isLoggedIn();
  }

  public login() {
    this.router.navigate(["/login"]);
  }

  public logout() {
    this.authenticateService.logout();
    this.router.navigate(["/login"]);
  }

  //*** Language options ***/

  public changeLanguage(lang: string) {
    console.log("changeLanguage(" + lang + ") called");
    this.translateService.use(lang);
    this.authenticateService.setLocalLanguage(lang);
  }

  private getLanguageCode() {
    return this.currentLanguage;
  }

  public getLanguageText() {
    for (var i = 0; i < this.supportedLanguages.length; i++) {
      if (this.currentLanguage == this.supportedLanguages[i].value) {
        return this.supportedLanguages[i].display;
      }
    }
  }

  private getLstLanguage() {
    var option = <HTMLSelectElement>document.getElementById("lstLanguage");
    return option.value;
  }

  //*** Menu links ***/

  public gotoAbout() {
    this.router.navigate(["/about"]);
  }

  public gotoBarcode() {
    this.router.navigate(["/barcode"]);
  }

  public gotoCalendar() {
    if (this.isAuthenticated()) {
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

  public gotoContactUs() {
    this.router.navigate(["/contact"]);
  }

  public gotoConversations() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/message/read/list/conversations"]);
    }
  }

  public gotoDashboard() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/dashboard"]);
    }
  }

  public gotoFAQ() {
    this.router.navigate(["/faq"]);
  }

  public gotoHome() {
    this.router.navigate(["/home"]);
  }

  public gotoMyUser() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/user/update"]);
    }
  }

  public gotoMyAssets() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/asset/read/my/list"]);
    }
  }

  public gotoMyProducts() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/asset/read/my/list"], {
        queryParams: { type: "product" },
      });
    }
  }

  public gotoMyServices() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/asset/read/my/list"], {
        queryParams: { type: "service" },
      });
    }
  }

  public gotoMyMessages() {
    console.log("gotoMyMessages()");
    if (this.isAuthenticated()) {
      this.router.navigate(["/message/read/my/messages"]);
    }
  }

  public gotoUserCreate() {
    if (!this.isAuthenticated()) {
      this.router.navigate(["/register"]);
    }
  }

  public gotoSearchAssets() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/asset/search/generic"]);
    }
  }

  public gotoSearchMember() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/user/search/member"]);
    }
  }

  public gotoSearchProducts() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/asset/search/products"], {
        queryParams: { type: "product" },
      });
    }
  }

  public gotoSearchServices() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/asset/search/services"], {
        queryParams: { type: "service" },
      });
    }
  }

  public gotoSearchTransactions() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/transaction/search/dates"]);
    }
  }

  public gotoTransactions() {
    if (this.isAuthenticated()) {
      this.transactionService.readMyTransactions().subscribe(
        (transactions: Array<Transaction>) => {
          console.log(transactions);
          this.transactionService.setTransactions(transactions);
        },
        (error: any) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.router.navigate(["/transaction/read/list"]);
        }
      );
    }
  }

  public gotoUserMap() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/user/read/map"]);
    }
  }

  public gotoAssetMap() {
    if (this.isAuthenticated()) {
      this.router.navigate(["/asset/read/map"]);
    }
  }

  public gotoWallets() {
    if (this.isAuthenticated()) {
      this.walletService.setWallets(this.authUser.wallets);
      this.router.navigate(["/wallet/read/list"]);
    }
  }

  /*
   selectLanguage(event: any, lang: string) {
      console.log("selectLanguage called" );
      if (event !== null) {       
        event.preventDefault(); 
      }
      
      this.translateService.use(lang);
   
      if (this.localLanguage != lang) {   
        console.log("localLanguage reset to:" + lang);
        this.userService.setLocalLanguage(lang); 
      }     
    }
  */
  /*selectLanguage(event: any, lang: string) {
    if (!lang) {
      lang = (<HTMLSelectElement>document.getElementById("lstLanguage")).value;
       console.log("selectLanguage2LangNew:" + lang);
    }
    else {
      console.log("selectLanguage2LangSave:" + lang);
     }
     
    
   
    console.log("selectLanguage2Event:" + event);
    if (event !== null) {       
      event.preventDefault(); 
    }
    
    this.translateService.use(lang);
 
    if (this.localLanguage != lang) {   
      console.log("localLanguage reset to:" + lang);
      this.userService.setLocalLanguage(lang); 
    }     
  }*/

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
    return lang === this.translateService.currentLang;
  }

  /* private actionButtonIcon(): any {
     // var result : Boolean = false;
     this.clickService.getIcon().subscribe(
       (icon) => {        
           return icon;       
       },
       (error) => { return false; },
       () => { });
   }*/

  /*  GUI */
  private showActionButton() {
    console.log("showActionButton()");
    this.actionVisibleSubscription = this.clickService.getVisible().subscribe(
      (visible: any) => {
        this.actionVisible = visible;
      },
      (error: Error) => {
        this.actionVisible = false;
      },
      () => {}
    );

    this.actionIconSubscription = this.clickService.getIcon().subscribe(
      (icon: any) => {
        this.actionIcon = icon;
      },
      (error: Error) => {
        this.actionIcon = "error";
      },
      () => {}
    );
  }
}
