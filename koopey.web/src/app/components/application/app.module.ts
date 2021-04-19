//Angular, Material, Libraries
import { enableProdMode, CUSTOM_ELEMENTS_SCHEMA, NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Http, HttpModule } from "@angular/http";
import { MaterialModule } from "./material/material.module"
//import { MaterialModule, MdIconModule, MdAutocompleteModule, MdSnackBarModule, MdSnackBar, MatNativeDateModule } from "@angular/material"
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateModule, TranslateLoader, TranslateStaticLoader } from "ng2-translate";
//import { CalendarModule } from 'angular-calendar';
import { RoutesManager } from "../../routes/route.manager";
import { appRouterProvider } from "../../routes/app.routes";
import { TypeaheadModule } from "../../../com/typeahead/typeahead.module";
import { ImageCropperComponent } from 'ng2-img-cropper';
import { UUID } from 'angular2-uuid';
import { QRCodeModule } from 'angular2-qrcode';
// import { NgxZxingModule } from 'ngx-zxing';
import { ZXingScannerModule } from "@zxing/ngx-scanner";
//Objects
import { Config } from "../../config/settings";
//Components
import { AboutComponent } from "../../components/about/about.component";
import { AddressControlComponent } from "../../components/address/address-control.component";
import { AdvertControlComponent } from "../../components/advert/advert-control.component";
import { ArticleReadComponent } from "../../components/article/read/article-read.component";
import { ArticleCreateComponent } from "../../components/article/create/article-create.component";
import { ArticleListComponent } from "../../components/article/list/article-list.component";
import { ArticleUpdateComponent } from "../../components/article/edit/article-update.component";

import { AssetReadComponent } from "../../components/asset/read/asset-read.component";
import { AssetCreateComponent } from "../../components/asset/create/asset-create.component";
import { AssetListComponent } from "../../components/asset/list/asset-list.component";
import { AssetMapComponent } from "../../components/asset/map/asset-map.component";
import { AssetUpdateComponent } from "../../components/asset/edit/asset-update.component";
import { AppComponent } from "./app.component";
import { BarcodeScannerComponent } from "../../components/barcode/scanner/barcode-scanner.component";
import { ConfirmDialogComponent } from "../../components/confirm/confirm-dialog.component";
import { ContactComponent } from "../../components/contact/contact.component";
import { ConversationListComponent } from "../../components/conversation/conversation-list.component";
import { DashboardComponent } from "../../components/dashboard/dashboard.component";
import { EmailChangeRequestComponent } from "../../components/authenticate/email-change/email-change-request.component";
import { EmailChangeReplyComponent } from "../../components/authenticate/email-change/email-change-reply.component";
import { EventCreateComponent } from "../../components/event/create/event-create.component";
import { EventCreateDialogComponent } from "../../components/event/create/event-create-dialog.component";
import { EventListComponent } from "../../components/event/list/event-list.component";
import { EventMapComponent } from "../../components/event/map/event-map.component";
import { EventReadComponent } from "../../components/event/read/event-read.component";
import { EventUpdateComponent } from "../../components/event/edit/event-update.component";
import { HomeComponent } from "../../components/home/home.component";
import { FAQComponent } from "../../components/faq/faq.component";
import { ImageDialogComponent } from "../../components/image/dialog/image-dialog.component";
import { ImageListComponent } from "../../components/image/list/image-list.component";
import { LegalComponent } from "../../components/legal/legal.component";
import { LogoutComponent } from "../../components/logout/logout.component";
import { LoginComponent } from "../../components/login/login.component";
import { MessageCreateComponent } from "../../components/message/create/message-create.component";
import { MessageCreateDialogComponent } from "../../components/message/create/message-create-dialog.component";
import { MessageListComponent } from "../../components/message/list/message-list.component";
import { MessageReadComponent } from "../../components/message/read/message-read.component";
import { MobileDialogComponent } from "../../components/mobile/mobile-dialog.component";
import { PasswordChangeForgottenRequestComponent } from "../../components/authenticate/password/password-change-forgotten-request.component";
import { PasswordChangeComponent } from "../../components/authenticate/password/password-change.component";
import { PasswordChangeForgottenComponent } from "../../components/authenticate/password/password-change-forgotten.component";
import { PrivacyPolicyComponent } from "../../components/legal/privacy-policy/privacy-policy.component";
import { QRCodeDialogComponent } from "../../components/barcode/qrcode/qrcode-dialog.component";
import { ReportComponent } from "../../components/report/report.component";
import { ReviewStarControlComponent } from "../../components/review/star/review-star-control.component";
import { ReviewThumbControlComponent } from "../../components/review/thumb/review-thumb-control.component";
import { ReviewCreateDialogComponent } from "../../components/review/create/review-create-dialog.component";
import { SettingsComponent } from "../../components/configure/settings.component";
import { SearchEventsComponent } from "../../components/event/search/search-events.component";
import { SearchProductsComponent } from "../../components/asset/search/search-products.component";
import { SearchCategoriesComponent } from "../../components/search/search-categories.component";
import { SearchTransactionsComponent } from "../../components/transaction/search/search-transactions.component";
import { SearchServicesComponent } from "../../components/asset/search/search-services.component";
import { SearchMembersComponent } from "../../components/user/search/search-members.component";
import { TagControlComponent } from "../../components/tag/tag-control.component";
import { TermsAndConditionsComponent } from "../../components/legal/terms-and-conditions/terms-and-conditions.component";
import { TermsAndConditionsControlComponent } from "../../components/legal/terms-and-conditions/terms-and-conditions-control.component";
import { TransactionCreateComponent } from "../../components/transaction/create/transaction-create.component";
import { TransactionCreateDialogComponent } from "../../components/transaction/create/transaction-create-dialog.component";
import { TransactionListComponent } from "../../components/transaction/list/transaction-list.component";
import { TransactionMapComponent } from "../../components/transaction/map/transaction-map.component";
import { TransactionReadComponent } from "../../components/transaction/read/transaction-read.component";
import { TransactionUpdateComponent } from "../../components/transaction/edit/transaction-update.component";
import { UserActivateComponent } from "../../components/authenticate/activate/user-activate.component";
import { UserCreateComponent } from "../../components/user/create/user-create.component";
import { UserControlComponent } from "../../components/user/control/user-control.component";
import { UserCalendarComponent } from "../../components/user/calendar/user-calendar.component";
import { UserListComponent } from "../../components/user/list/user-list.component";
import { UserMapComponent } from "../../components/user/map/user-map.component";
import { UserAssetsComponent } from "../../components/user/assets/user-assets.component";
import { UserUpdateComponent } from "../../components/user/edit/user-update.component";
import { UserReadComponent } from "../../components/user/read/user-read.component";
import { WalletControlComponent } from "../../components/wallet/wallet-control.component";
import { WalletDialogComponent } from "../../components/wallet/dialog/wallet-dialog.component";
import { WalletListComponent } from "../../components/wallet/list/wallet-list.component";
import { WalletReadComponent } from "../../components/wallet/read/wallet-read.component";
//Services
import { AuthService } from "../../services/auth.service";
import { AlertService } from "../../services/alert.service";
import { BarcodeService } from "../../services/barcode.service";
import { BitcoinService } from "../../services/bitcoin.service";
import { GameService } from "../../services/game.service";
import { ClickService } from "../../services/click.service";
import { EthereumService } from "../../services/ethereum.service";
import { HomeService } from "../../services/home.service";
import { MessageService } from "../../services/message.service";
import { AssetService } from "../../services/asset.service";
import { ReviewService } from "../../services/review.service";
import { ScoreService } from "../../services/score.service";
import { SearchService } from "../../services/search.service";
import { TransactionService } from "../../services/transaction.service";
import { TagService } from "../../services/tag.service";
import { UserService } from "../../services/user.service";
import { WalletService } from "../../services/wallet.service";

if (Config.system_production) {
    enableProdMode();
}

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        //  GooglePlaceModule,        
        HttpModule,
        ReactiveFormsModule,
        appRouterProvider,
        MaterialModule,
        //MatNativeDateModule,
        BrowserAnimationsModule,
        TypeaheadModule,
       // CalendarModule.forRoot(),
        TranslateModule.forRoot({
            provide: TranslateLoader,
            useFactory: (http: Http) => new TranslateStaticLoader(http, "./localization", ".json"),
            deps: [Http]
        }),
        QRCodeModule,
        ZXingScannerModule
        //NgxZxingModule.forRoot()
    ],
    bootstrap: [
        AppComponent,
    ],
    declarations: [
        AboutComponent,
        AddressControlComponent,
        AdvertControlComponent,
        AppComponent,
        AssetMapComponent,
        AssetCreateComponent,
        AssetListComponent,
        AssetUpdateComponent,
        AssetReadComponent,
        BarcodeScannerComponent,     
        ConfirmDialogComponent,
        ContactComponent,
        ConversationListComponent,
        DashboardComponent,
        EmailChangeReplyComponent,
        EmailChangeRequestComponent,
        EventCreateComponent,
        EventCreateDialogComponent,
        EventListComponent,
        EventMapComponent,
        EventReadComponent,
        EventUpdateComponent,
        HomeComponent,        
        //  OffClickDirective,
        // HighlightPipe,
        ImageCropperComponent,
        ImageDialogComponent,
        ImageListComponent,
        FAQComponent,        
        LegalComponent,
        LogoutComponent,
        LoginComponent,
        MessageCreateComponent,
        MobileDialogComponent,
        MessageListComponent,
        MessageReadComponent,
        UserActivateComponent,
        UserCalendarComponent,
        UserControlComponent,
        UserCreateComponent,
        UserListComponent,
        UserMapComponent,
        UserAssetsComponent,
        UserReadComponent,
        UserUpdateComponent,
        MessageCreateDialogComponent,
        PrivacyPolicyComponent,
        PasswordChangeComponent,
        PasswordChangeForgottenComponent,
        PasswordChangeForgottenRequestComponent,
        QRCodeDialogComponent,
        ReportComponent,
        ReviewStarControlComponent,
        ReviewThumbControlComponent,
        ReviewCreateDialogComponent,
        SearchEventsComponent,
        SearchProductsComponent,
        SearchCategoriesComponent,
        SearchTransactionsComponent,
        SearchMembersComponent,
        SearchServicesComponent,
        SettingsComponent,
        TagControlComponent,
        TermsAndConditionsComponent,
        TermsAndConditionsControlComponent,
        TransactionCreateComponent,
        TransactionCreateDialogComponent,
        TransactionListComponent,
        TransactionMapComponent,
        TransactionReadComponent,
        TransactionUpdateComponent,
        WalletControlComponent,
        WalletDialogComponent,
        WalletListComponent,
        WalletReadComponent,
    ],
    entryComponents: [
        ConfirmDialogComponent,    
        EventCreateDialogComponent,
        ImageDialogComponent,
        MessageCreateDialogComponent,
        MobileDialogComponent,
        QRCodeDialogComponent,
        ReviewCreateDialogComponent,
        TransactionCreateDialogComponent,
    ],
    providers: [
        RoutesManager,
        AuthService,
        AlertService,
        BarcodeService,
        BitcoinService,   
        ClickService,
        EthereumService,
        GameService,
        HomeService,
        MessageService,
        AssetService,
        ReviewService,
        ScoreService,
        SearchService,
        TagService,
        TransactionService,
        UserService,
        WalletService,
    ],
    schemas: [
        CUSTOM_ELEMENTS_SCHEMA
    ],
})

export class AppModule { }
