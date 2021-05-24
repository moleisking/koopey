import {
  enableProdMode,
  CUSTOM_ELEMENTS_SCHEMA,
  NgModule,
} from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule, HttpClient } from "@angular/common/http";
import { MaterialModule } from "./material/material.module";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import {
  TranslateModule,
  TranslateLoader,
  // TranslateStaticLoader,
} from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
//import { CalendarModule } from 'angular-calendar';
import { RoutesManager } from "../../routes/route.manager";
import { appRouterProvider } from "../../routes/app.routes";
//import { TypeaheadModule } from "../../../com/typeahead/typeahead.module";
//import { ImageCropperComponent } from "ngx-img-cropper";
import { UUID } from "angular2-uuid";
import { QRCodeModule } from "angular2-qrcode";
// import { NgxZxingModule } from 'ngx-zxing';
import { ZXingScannerModule } from "@zxing/ngx-scanner";
import { AboutComponent } from "../about/about.component";
import { AddressTextboxComponent } from "../common/address-textbox/address-textbox.component";
import { AdvertControlComponent } from "../advert/advert-control.component";
import { AgmCoreModule } from "@agm/core";
import { ArticleReadComponent } from "../article/read/article-read.component";
import { ArticleCreateComponent } from "../article/create/article-create.component";
import { ArticleListComponent } from "../article/list/article-list.component";
import { ArticleUpdateComponent } from "../article/edit/article-update.component";
import { AssetReadComponent } from "../asset/read/asset-read.component";
import { AssetCreateComponent } from "../asset/create/asset-create.component";
import { AssetListComponent } from "../asset/list/asset-list.component";
import { AssetMapComponent } from "../asset/map/asset-map.component";
import { AssetUpdateComponent } from "../asset/edit/asset-update.component";
import { AppComponent } from "./application.component";
import { BarcodeScannerComponent } from "../common/barcode/scanner/barcode-scanner.component";
import { ConfigurationComponent } from "../configuration/configuration.component";
import { ConfirmDialogComponent } from "../confirm/confirm-dialog.component";
import { ContactComponent } from "../contact/contact.component";
import { ConversationListComponent } from "../conversation/conversation-list.component";
import { DashboardComponent } from "../dashboard/dashboard.component";
import { EmailChangeRequestComponent } from "../authentication/email-change/request/email-change-request.component";
import { EmailChangeReplyComponent } from "../authentication/email-change/reply/email-change-reply.component";
import { AppointmentCreateComponent } from "../appointment/create/appointment-create.component";
import { AppointmentCreateDialogComponent } from "../appointment/create/dialog/appointment-create-dialog.component";
import { AppointmentListComponent } from "../appointment/list/appointment-list.component";
import { AppointmentMapComponent } from "../appointment/map/appointment-map.component";
import { AppointmentReadComponent } from "../appointment/read/appointment-read.component";
import { AppointmentUpdateComponent } from "../appointment/edit/appointment-update.component";
import { HomeComponent } from "../home/home.component";
import { FAQComponent } from "../faq/faq.component";
import { ImageDialogComponent } from "../image/dialog/image-dialog.component";
import { ImageListComponent } from "../image/list/image-list.component";
import { LegalComponent } from "../legal/legal.component";
import { LogInOutComponent } from "../login/loginout-button/loginout-button.component";
import { LoginComponent } from "../login/login.component";
import { MessageCreateComponent } from "../message/create/message-create.component";
import { MessageCreateDialogComponent } from "../message/create/dialog/message-create-dialog.component";
import { MessageListComponent } from "../message/list/message-list.component";
import { MessageReadComponent } from "../message/read/message-read.component";
import { MobileDialogComponent } from "../mobile/mobile-dialog.component";
import { PasswordForgottenRequestComponent } from "../authentication/password/forgotten/request/password-forgotten-request.component";
import { PasswordChangeComponent } from "../authentication/password/change/password-change.component";
import { PasswordChangeForgottenComponent } from "../authentication/password/forgotten/password-change-forgotten.component";
import { PrivacyPolicyComponent } from "../legal/privacy-policy/privacy-policy.component";
import { QRCodeDialogComponent } from "../common/barcode/qrcode/qrcode-dialog.component";
import { ReportComponent } from "../report/report.component";
import { ReviewStarControlComponent } from "../review/star/review-star-control.component";
import { ReviewThumbControlComponent } from "../review/thumb/review-thumb-control.component";
import { ReviewCreateComponent } from "../review/create/review-create.component";
import { ReviewCreateDialogComponent } from "../review/create/dialog/review-create-dialog.component";
import { AppointmentSearchComponent } from "../appointment/search/appointment-search.component";
import { ProductSearchComponent } from "../asset/search/product/product-search.component";
import { CategorySearchComponent } from "../search/category-search.component";
import { TransactionSearchComponent } from "../transaction/search/transaction-search.component";
import { ServiceSearchComponent } from "../asset/search/service/service-search.component";
import { MemberSearchComponent } from "../user/search/member/member-search.component";
import { TagTextboxComponent } from "../common/tag-textbox/tag-textbox.component";
import { TermsAndConditionsComponent } from "../legal/terms-and-conditions/terms-and-conditions.component";
import { TermsAndConditionsControlComponent } from "../legal/terms-and-conditions/control/terms-and-conditions-control.component";
import { TransactionCreateComponent } from "../transaction/create/transaction-create.component";
import { TransactionCreateDialogComponent } from "../transaction/create/dialog/transaction-create-dialog.component";
import { TransactionListComponent } from "../transaction/list/transaction-list.component";
import { TransactionMapComponent } from "../transaction/map/transaction-map.component";
import { TransactionReadComponent } from "../transaction/read/transaction-read.component";
import { TransactionUpdateComponent } from "../transaction/edit/transaction-update.component";
import { UserActivateComponent } from "../authentication/activate/user-activate.component";
import { UserCreateComponent } from "../user/create/user-create.component";
import { UserControlComponent } from "../user/control/user-control.component";
import { UserCalendarComponent } from "../user/calendar/user-calendar.component";
import { UserListComponent } from "../user/list/user-list.component";
import { UserMapComponent } from "../user/map/user-map.component";
import { UserAssetsComponent } from "../user/assets/user-assets.component";
import { UserUpdateComponent } from "../user/edit/user-update.component";
import { UserReadComponent } from "../user/read/user-read.component";
import { WalletControlComponent } from "../wallet/control/wallet-control.component";
import { WalletDialogComponent } from "../wallet/dialog/wallet-dialog.component";
import { WalletListComponent } from "../wallet/list/wallet-list.component";
import { WalletReadComponent } from "../wallet/read/wallet-read.component";
import { AuthenticationService } from "../../services/authentication.service";
import { AlertService } from "../../services/alert.service";
import { BarcodeService } from "../../services/barcode.service";
import { GameService } from "../../services/game.service";
import { ClickService } from "../../services/click.service";
import { HomeService } from "../../services/home.service";
import { MatGoogleMapsAutocompleteModule } from "@angular-material-extensions/google-maps-autocomplete";
import { MessageService } from "../../services/message.service";
import { AssetService } from "../../services/asset.service";
import { ReviewService } from "../../services/review.service";
import { ScoreService } from "../../services/score.service";
import { SearchService } from "../../services/search.service";
import { TransactionService } from "../../services/transaction.service";
import { TagService } from "../../services/tag.service";
import { UserService } from "../../services/user.service";
import { WalletService } from "../../services/wallet.service";
import { CurrencyCodeToSymbolPipe } from "../../pipes/currency-code-to-symbol.pipe";
import { DistanceToKilometersPipe } from "../../pipes/distance-to-kilometers.pipe";
import { DistanceToMilesPipe } from "../../pipes/distance-to-miles.pipe";
import { EpochToDatePipe } from "../../pipes/epoch-to-date.pipe copy";
import { FlexLayoutModule } from "@angular/flex-layout";
import { Environment } from "src/environments/environment";

if (Environment.type === "production" || Environment.type === "stage") {
  enableProdMode();
}

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  imports: [
    AgmCoreModule.forRoot({
      apiKey: Environment.ApiKeys.GoogleApiKey,
      libraries: ["places"],
    }),
    BrowserAnimationsModule,
    BrowserModule,
    FlexLayoutModule,
    FormsModule,
    //  GooglePlaceModule,
    HttpClientModule,
    ReactiveFormsModule,
    appRouterProvider,
    MaterialModule,
    MatGoogleMapsAutocompleteModule,
    //MatNativeDateModule,

    //  TypeaheadModule,
    // CalendarModule.forRoot(),
    TranslateModule.forRoot({
      defaultLanguage: "en",
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
    QRCodeModule,
    ZXingScannerModule,
    //NgxZxingModule.forRoot()
  ],
  bootstrap: [AppComponent],
  declarations: [
    AboutComponent,
    AddressTextboxComponent,
    AdvertControlComponent,
    AppComponent,
    ArticleCreateComponent,
    ArticleListComponent,
    ArticleReadComponent,
    ArticleUpdateComponent,
    AssetMapComponent,
    AssetCreateComponent,
    AssetListComponent,
    AssetUpdateComponent,
    AssetReadComponent,
    BarcodeScannerComponent,
    ConfigurationComponent,
    ConfirmDialogComponent,
    ContactComponent,
    ConversationListComponent,
    CurrencyCodeToSymbolPipe,
    DistanceToKilometersPipe,
    DistanceToMilesPipe,
    EpochToDatePipe,
    DashboardComponent,
    EmailChangeReplyComponent,
    EmailChangeRequestComponent,
    AppointmentCreateComponent,
    AppointmentCreateDialogComponent,
    AppointmentListComponent,
    AppointmentMapComponent,
    AppointmentReadComponent,
    AppointmentUpdateComponent,
    HomeComponent,
    //  OffClickDirective,
    // HighlightPipe,
    //ImageCropperComponent,
    ImageDialogComponent,
    ImageListComponent,
    FAQComponent,
    LegalComponent,
    LogInOutComponent,
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
    PasswordForgottenRequestComponent,
    QRCodeDialogComponent,
    ReportComponent,
    ReviewStarControlComponent,
    ReviewThumbControlComponent,
    ReviewCreateComponent,
    ReviewCreateDialogComponent,
    AppointmentSearchComponent,
    ProductSearchComponent,
    CategorySearchComponent,
    TransactionSearchComponent,
    MemberSearchComponent,
    ServiceSearchComponent,
    TagTextboxComponent,
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
    AppointmentCreateDialogComponent,
    ImageDialogComponent,
    MessageCreateDialogComponent,
    MobileDialogComponent,
    QRCodeDialogComponent,
    ReviewCreateDialogComponent,
    TransactionCreateDialogComponent,
  ],
  providers: [
    RoutesManager,
    AuthenticationService,
    AlertService,
    BarcodeService,
    ClickService,
    CurrencyCodeToSymbolPipe,
    DistanceToKilometersPipe,
    DistanceToMilesPipe,
    EpochToDatePipe,
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
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule {}
