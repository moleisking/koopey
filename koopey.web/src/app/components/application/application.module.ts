//import { CalendarModule } from 'angular-calendar';
import { appRouterProvider } from "../../routes/app.routes";
//import { TypeaheadModule } from "../../../com/typeahead/typeahead.module";
import { UUID } from "angular2-uuid";
import { QRCodeModule } from "angular2-qrcode";
// import { NgxZxingModule } from 'ngx-zxing';
import { AboutComponent } from "../about/about.component";
import { AddressboxComponent } from "../common/address/addressbox.component";
import { AdvertControlComponent } from "../advert/advert-control.component";
import { AlertService } from "../../services/alert.service";
import { AppComponent } from "./application.component";
import { AppointmentCreateComponent } from "../appointment/edit/appointment-create.component";
import { AppointmentCreateDialogComponent } from "../appointment/dialog/appointment-create-dialog.component";
import { AppointmentListComponent } from "../appointment/list/appointment-list.component";
import { AppointmentMapComponent } from "../appointment/map/appointment-map.component";
import { AppointmentReadComponent } from "../appointment/read/appointment-read.component";
import { AppointmentSearchComponent } from "../appointment/search/appointment-search.component";
import { AppointmentUpdateComponent } from "../appointment/edit/appointment-update.component";
import { ArticleReadComponent } from "../article/read/article-read.component";
import { ArticleCreateComponent } from "../article/edit/article-create.component";
import { ArticleListComponent } from "../article/list/article-list.component";
import { ArticleUpdateComponent } from "../article/edit/article-update.component";
import { AssetReadComponent } from "../asset/read/asset-read.component";
import { AssetEditComponent } from "../asset/edit/asset-edit.component";
import { AssetListComponent } from "../asset/list/asset-list.component";
import { AssetMapComponent } from "../asset/map/asset-map.component";
import { AssetService } from "../../services/asset.service";
import { AuthenticationService } from "../../services/authentication.service";
import { BarcodeService } from "../../services/barcode.service";
import { BarcodeScannerComponent } from "../common/barcode/scanner/barcode-scanner.component";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ClickService } from "../../services/click.service";
import { ConfigurationComponent } from "../configuration/configuration.component";
import { ConfirmDialogComponent } from "../confirm/confirm-dialog.component";
import { ContactComponent } from "../contact/contact.component";
import { ConversationListComponent } from "../conversation/conversation-list.component";
import { CropDialogComponent } from "../common/crop/crop-dialog.component";
import { CurrencyCodeToSymbolPipe } from "../../pipes/currency-code-to-symbol.pipe";
import { DistanceUnitPipe } from "../../pipes/distanceunit.pipe";
import { DistancePipe } from "../../pipes/distance.pipe";
import {
  enableProdMode,
  CUSTOM_ELEMENTS_SCHEMA,
  NgModule,
} from "@angular/core";
import { Environment } from "src/environments/environment";
import { EpochToDatePipe } from "../../pipes/epoch-to-date.pipe";
import { DashboardComponent } from "../dashboard/dashboard.component";
import { EmailChangeRequestComponent } from "../authentication/email-change/request/email-change-request.component";
import { EmailChangeReplyComponent } from "../authentication/email-change/reply/email-change-reply.component";
import { HomeComponent } from "../home/home.component";
import { HttpClient, HttpClientModule } from "@angular/common/http";
import { FAQComponent } from "../faq/faq.component";
import { FlexLayoutModule } from "@angular/flex-layout";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ImageCropperModule } from "ngx-image-cropper";
import { ImageboxComponent } from "../common/image/imagebox.component";
import { ImageListComponent } from "../image/list/image-list.component";
import { LegalComponent } from "../legal/legal.component";
import { LocationService } from "../../services/location.service";
import { LogInOutComponent } from "../authentication/loginout-button/loginout-button.component";
import { LoginComponent } from "../authentication/login/login.component";
import { MessageCreateComponent } from "../message/create/message-create.component";
import { MessageCreateDialogComponent } from "../message/create/dialog/message-create-dialog.component";
import { MessageListComponent } from "../message/list/message-list.component";
import { MessageReadComponent } from "../message/read/message-read.component";
import { MobileDialogComponent } from "../mobile/mobile-dialog.component";
import { PasswordForgottenRequestComponent } from "../authentication/password/forgotten/request/password-forgotten-request.component";
import { PasswordChangeComponent } from "../authentication/password/change/password-change.component";
import { PasswordChangeForgottenComponent } from "../authentication/password/forgotten/password-change-forgotten.component";
import { QRCodeDialogComponent } from "../common/barcode/qrcode/qrcode-dialog.component";
import { RegisterComponent } from "../user/register/register.component";
import { ReportComponent } from "../report/report.component";
import { ReviewStarControlComponent } from "../review/star/star.component";
import { ReviewThumbControlComponent } from "../review/thumb/thumb.component";
import { ReviewCreateComponent } from "../review/create/review-create.component";
import { ReviewCreateDialogComponent } from "../review/create/dialog/review-create-dialog.component";
import { RoutesManager } from "../../routes/route.manager";
import { ProductSearchComponent } from "../asset/search/product/product-search.component";
import { CategorySearchComponent } from "../search/category-search.component";
import {
  TranslateModule,
  TranslateLoader,
  // TranslateStaticLoader,
} from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { TransactionSearchComponent } from "../transaction/search/transaction-search.component";
import { ServiceSearchComponent } from "../asset/search/service/service-search.component";
import { MemberSearchComponent } from "../user/search/member/member-search.component";
import { TagboxComponent } from "../common/tag/tagbox.component";
import { GameService } from "../../services/game.service";
import { GdprboxComponent } from "../common/gdpr/gdprbox.component";
import { GdprService } from "../../services/gdpr.service";
import { HomeService } from "../../services/home.service";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { MatBadgeModule } from "@angular/material/badge";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatChipsModule } from "@angular/material/chips";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatDialogModule } from "@angular/material/dialog";
import { MatExpansionModule } from "@angular/material/expansion";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatListModule } from "@angular/material/list";
import { MatMenuModule } from "@angular/material/menu";
import { MatIconModule } from "@angular/material/icon";
import { MatRadioModule } from "@angular/material/radio";
import { MatTabsModule } from "@angular/material/tabs";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatSelectModule } from "@angular/material/select";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MessageService } from "../../services/message.service";
import { ReviewService } from "../../services/review.service";
import { ScoreService } from "../../services/score.service";
import { SearchService } from "../../services/search.service";
import { TagService } from "../../services/tag.service";
import { TransactionCreateComponent } from "../transaction/edit/transaction-create.component";
import { TransactionCreateDialogComponent } from "../transaction/dialog/transaction-create-dialog.component";
import { TransactionListComponent } from "../transaction/list/transaction-list.component";
import { TransactionMapComponent } from "../transaction/map/transaction-map.component";
import { TransactionReadComponent } from "../transaction/read/transaction-read.component";
import { TransactionUpdateComponent } from "../transaction/edit/transaction-update.component";
import { TransactionService } from "../../services/transaction.service";
import { UserService } from "../../services/user.service";
import { UserActivateComponent } from "../authentication/activate/user-activate.component";
import { UserControlComponent } from "../user/control/user-control.component";
import { UserCalendarComponent } from "../user/calendar/user-calendar.component";
import { UserListComponent } from "../user/list/user-list.component";
import { UserAssetsComponent } from "../user/assets/user-assets.component";
import { UserEditComponent } from "../user/edit/user-edit.component";
import { UserReadComponent } from "../user/read/user-read.component";
import { WalletControlComponent } from "../wallet/control/wallet-control.component";
import { WalletDialogComponent } from "../wallet/dialog/wallet-dialog.component";
import { WalletListComponent } from "../wallet/list/wallet-list.component";
import { WalletReadComponent } from "../wallet/read/wallet-read.component";
import { WalletService } from "../../services/wallet.service";
import { ZXingScannerModule } from "@zxing/ngx-scanner";

if (Environment.type === "production" || Environment.type === "stage") {
  enableProdMode();
}

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  bootstrap: [AppComponent],
  declarations: [
    AboutComponent,
    AddressboxComponent,
    AdvertControlComponent,
    AppComponent,
    ArticleCreateComponent,
    ArticleListComponent,
    ArticleReadComponent,
    ArticleUpdateComponent,
    AppointmentCreateComponent,
    AppointmentCreateDialogComponent,
    AppointmentListComponent,
    AppointmentMapComponent,
    AppointmentReadComponent,
    AppointmentSearchComponent,
    AppointmentUpdateComponent,
    AssetMapComponent,
    AssetEditComponent,
    AssetListComponent,
    AssetReadComponent,
    BarcodeScannerComponent,
    CategorySearchComponent,
    ConfigurationComponent,
    ConfirmDialogComponent,
    ContactComponent,
    ConversationListComponent,
    CropDialogComponent,
    CurrencyCodeToSymbolPipe,
    DashboardComponent,
    DistancePipe,
    DistanceUnitPipe,
    EpochToDatePipe,
    EmailChangeReplyComponent,
    EmailChangeRequestComponent,
    FAQComponent,
    GdprboxComponent,
    HomeComponent,
    ImageboxComponent,
    ImageListComponent,
    LegalComponent,
    LogInOutComponent,
    LoginComponent,
    MemberSearchComponent,
    MessageCreateComponent,
    MessageCreateDialogComponent,
    MessageListComponent,
    MessageReadComponent,
    MobileDialogComponent,
    PasswordChangeComponent,
    PasswordChangeForgottenComponent,
    PasswordForgottenRequestComponent,
    ProductSearchComponent,
    QRCodeDialogComponent,
    ReportComponent,
    ReviewStarControlComponent,
    ReviewThumbControlComponent,
    ReviewCreateComponent,
    ReviewCreateDialogComponent,
    RegisterComponent,
    ServiceSearchComponent,
    UserActivateComponent,
    UserCalendarComponent,
    UserControlComponent,
    UserListComponent,
    UserAssetsComponent,
    UserReadComponent,
    UserEditComponent,
    TransactionSearchComponent,
    TagboxComponent,
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
    AppointmentCreateDialogComponent,
    ConfirmDialogComponent,
    MessageCreateDialogComponent,
    MobileDialogComponent,
    QRCodeDialogComponent,
    ReviewCreateDialogComponent,
    TransactionCreateDialogComponent,
  ],
  exports: [
    MatAutocompleteModule,
    MatBadgeModule,
    MatButtonModule,
    MatCardModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatRadioModule,
    MatTabsModule,
    MatSidenavModule,
    MatSelectModule,
    MatSnackBarModule,
    MatToolbarModule,
  ],
  imports: [
    appRouterProvider,
    /* AgmCoreModule.forRoot({
      apiKey: Environment.ApiKeys.GoogleApiKey,
      libraries: ["places"],
    }),*/
    BrowserAnimationsModule,
    BrowserModule,
    FlexLayoutModule,
    FormsModule,
    //  GooglePlaceModule,
    HttpClientModule,
    ImageCropperModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatButtonModule,
    MatCardModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatRadioModule,
    MatTabsModule,
    MatSidenavModule,
    MatSelectModule,
    MatSnackBarModule,
    MatToolbarModule,
    //MatGoogleMapsAutocompleteModule,
    //MatNativeDateModule,
    //  TypeaheadModule,
    // CalendarModule.forRoot(),
    ReactiveFormsModule,
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
  providers: [
    AssetService,
    RoutesManager,
    AuthenticationService,
    AlertService,
    BarcodeService,
    ClickService,
    CurrencyCodeToSymbolPipe,
    DistanceUnitPipe,
    DistancePipe,
    EpochToDatePipe,
    LocationService,
    GameService,
    GdprService,
    HomeService,
    MessageService,
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
