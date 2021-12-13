//import { CalendarModule } from 'angular-calendar';
import { appRouterProvider } from "../../routes/app.routes";
//import { TypeaheadModule } from "../../../com/typeahead/typeahead.module";
import { QRCodeModule } from "angular2-qrcode";
// import { NgxZxingModule } from 'ngx-zxing';
import { AboutComponent } from "../about/about.component";
import { AddressboxComponent } from "../common/address/addressbox.component";
import { AdvertboxComponent } from "../common/advert/advertbox.component";
import { AlertService } from "../../services/alert.service";
import { AppComponent } from "./application.component";
import { ArticleReadComponent } from "../article/read/article-read.component";
import { ArticleEditComponent } from "../article/edit/article-edit.component";
import { ArticleListComponent } from "../article/list/article-list.component";
import { AssetReadComponent } from "../asset/read/asset-read.component";
import { AssetEditComponent } from "../asset/edit/asset-edit.component";
import { AssetListComponent } from "../asset/list/asset-list.component";
import { AssetMapComponent } from "../asset/map/asset-map.component";
import { AssetSearchComponent } from "../asset/search/asset-search.component";
import { AssetService } from "../../services/asset.service";
import { AuthenticationService } from "../../services/authentication.service";
import { BarcodeService } from "../../services/barcode.service";
import { BarcodeScannerComponent } from "../common/barcode/scanner/barcode-scanner.component";
import { BrowserModule, HammerModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ConfigurationComponent } from "../configuration/configuration.component";
import { ConfirmDialogComponent } from "../confirm/confirm-dialog.component";
import { ContactComponent } from "../contact/contact.component";
import { ConversationListComponent } from "../conversation/conversation-list.component";
import { CropDialogComponent } from "../common/crop/crop-dialog.component";
import { CurrencyboxComponent } from "../common/currency/currencybox.component";
import { CodeToSymbolPipe } from "../../pipes/code-to-symbol.pipe";
import { DistancePipe } from "../../pipes/distance.pipe";
import { DistanceUnitPipe } from "../../pipes/distanceunit.pipe";
import { DimensionPipe } from "../../pipes/dimension.pipe";
import { DimensionUnitPipe } from "../../pipes/dimensionunit.pipe";
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
import { FooterComponent } from "../footer/footer.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
//import "hammerjs";
import { ImageCropperModule } from "ngx-image-cropper";
import { ImageboxComponent } from "../common/image/imagebox.component";
import { GDPRComponent } from "../gdpr/gdpr.component";
import { LocationService } from "../../services/location.service";

import { LocationEditComponent } from "../location/edit/location-edit.component";
import { LocationListComponent } from "../location/list/location-list.component";
import { LocationTypeComponent } from "../location/type/location-type.component";
//import { LocationReadComponent } from "../location/read/location-read.component";

import { LogInOutComponent } from "../authentication/loginout-button/loginout-button.component";
import { LoginComponent } from "../authentication/login/login.component";
import { MessageCreateComponent } from "../message/create/message-create.component";
import { MessageListComponent } from "../message/list/message-list.component";
import { MessageReadComponent } from "../message/read/message-read.component";
import { MobileDialogComponent } from "../common/mobile/mobile-dialog.component";
import { NegativeButtonComponent } from "../review/negative/negativebutton.component";
import { PasswordForgottenRequestComponent } from "../authentication/password/forgotten/request/password-forgotten-request.component";
import { PasswordChangeComponent } from "../authentication/password/change/password-change.component";
import { PasswordChangeForgottenComponent } from "../authentication/password/forgotten/password-change-forgotten.component";
import { PositionButtonComponent } from "../common/position/positionbutton.component";
import { PositiveButtonComponent } from "../review/positive/positivebutton.component";
import { QRCodeDialogComponent } from "../common/barcode/qrcode/qrcode-dialog.component";
import { RegisterComponent } from "../user/register/register.component";
import { ReportComponent } from "../report/report.component";
import { ReviewEditComponent } from "../review/edit/review-edit.component";
import { ReviewDialogComponent } from "../review/dialog/review-dialog.component";
import { ReviewListComponent } from "../review/list/review-list.component";
import { RoutesManager } from "../../routes/route.manager";
import { StarboxComponent } from "../review/star/starbox.component";
import { TagSearchComponent } from "../tag/search/tag-search.component";
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { TransactionSearchComponent } from "../transaction/search/transaction-search.component";
import { TagboxComponent } from "../common/tag/tagbox.component";
import { GameService } from "../../services/game.service";
import { GdprboxComponent } from "../common/gdpr/gdprbox.component";
import { GdprService } from "../../services/gdpr.service";
import { HomeService } from "../../services/home.service";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { MatBadgeModule } from "@angular/material/badge";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatChipsModule } from "@angular/material/chips";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatDialogModule } from "@angular/material/dialog";
import { MatExpansionModule } from "@angular/material/expansion";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatListModule } from "@angular/material/list";
import { MatMenuModule } from "@angular/material/menu";
import { MatIconModule } from "@angular/material/icon";
import { MatNativeDateModule } from "@angular/material/core";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatRadioModule } from "@angular/material/radio";
import { MatTableModule } from "@angular/material/table";
import { MatTabsModule } from "@angular/material/tabs";
import { MatSelectModule } from "@angular/material/select";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatSliderModule } from "@angular/material/slider";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatSortModule } from "@angular/material/sort";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MessageService } from "../../services/message.service";
import { PeriodboxComponent } from "../common/period/periodbox.component";
import { ReviewService } from "../../services/review.service";
import { SearchService } from "../../services/search.service";
import { TagService } from "../../services/tag.service";
import { TransactionEditComponent } from "../transaction/edit/transaction-edit.component";
import { TransactionDialogComponent } from "../transaction/dialog/transaction-dialog.component";
import { TransactionListComponent } from "../transaction/list/transaction-list.component";
import { TransactionReadComponent } from "../transaction/read/transaction-read.component";
import { TransactionService } from "../../services/transaction.service";
import { UserService } from "../../services/user.service";
import { UserActivateComponent } from "../authentication/activate/user-activate.component";
import { UserboxComponent } from "../user/control/userbox.component";
import { UserCalendarComponent } from "../user/calendar/user-calendar.component";
import { UserListComponent } from "../user/list/user-list.component";
import { UserAssetsComponent } from "../user/assets/user-assets.component";
import { UserEditComponent } from "../user/edit/user-edit.component";
import { UserReadComponent } from "../user/read/user-read.component";
import { UserSearchComponent } from "../user/search/user-search.component";
import { WalletControlComponent } from "../wallet/control/wallet-control.component";
import { WalletDialogComponent } from "../wallet/dialog/wallet-dialog.component";
import { WalletListComponent } from "../wallet/list/wallet-list.component";
import { WalletReadComponent } from "../wallet/read/wallet-read.component";
import { WalletService } from "../../services/wallet.service";
import { WeightPipe } from "../../pipes/weight.pipe";
import { WeightUnitPipe } from "../../pipes/weightunit.pipe";
import { ZXingScannerModule } from "@zxing/ngx-scanner";
import { ToolbarService } from "src/app/services/toolbar.service";
import { ClassificationService } from "src/app/services/classification.service";

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
    AdvertboxComponent,
    AppComponent,
    ArticleListComponent,
    ArticleReadComponent,
    ArticleEditComponent,
    AssetMapComponent,
    AssetEditComponent,
    AssetListComponent,
    AssetReadComponent,
    AssetSearchComponent,
    BarcodeScannerComponent,
    TagSearchComponent,
    ConfigurationComponent,
    ConfirmDialogComponent,
    ContactComponent,
    ConversationListComponent,
    CropDialogComponent,
    CurrencyboxComponent,
    CodeToSymbolPipe,
    DashboardComponent,
    DimensionPipe,
    DimensionUnitPipe,
    DistancePipe,
    DistanceUnitPipe,
    EpochToDatePipe,
    EmailChangeReplyComponent,
    EmailChangeRequestComponent,
    FAQComponent,
    FooterComponent,
    GdprboxComponent,
    HomeComponent,
    ImageboxComponent,
    GDPRComponent,
    LocationEditComponent,
    LocationListComponent,
    LocationTypeComponent,
    LogInOutComponent,
    LoginComponent,
    MessageCreateComponent,
    MessageListComponent,
    MessageReadComponent,
    MobileDialogComponent,
    NegativeButtonComponent,
    PasswordChangeComponent,
    PasswordChangeForgottenComponent,
    PasswordForgottenRequestComponent,
    PeriodboxComponent,
    PositionButtonComponent,
    PositiveButtonComponent,
    QRCodeDialogComponent,
    ReportComponent,
    ReviewDialogComponent,
    ReviewEditComponent,
    ReviewListComponent,
    RegisterComponent,
    StarboxComponent,
    UserActivateComponent,
    UserCalendarComponent,
    UserboxComponent,
    UserListComponent,
    UserAssetsComponent,
    UserEditComponent,
    UserReadComponent,
    UserSearchComponent,
    TransactionSearchComponent,
    TagboxComponent,
    TransactionEditComponent,
    TransactionDialogComponent,
    TransactionListComponent,
    TransactionReadComponent,
    WalletControlComponent,
    WalletDialogComponent,
    WalletListComponent,
    WalletReadComponent,
    WeightPipe,
    WeightUnitPipe,
  ],
  entryComponents: [
    ConfirmDialogComponent,
    MobileDialogComponent,
    QRCodeDialogComponent,
    ReviewDialogComponent,
    TransactionDialogComponent,
  ],
  exports: [],
  imports: [
    appRouterProvider,
    BrowserAnimationsModule,
    BrowserModule,
    FlexLayoutModule,
    FormsModule,
    HammerModule,
    HttpClientModule,
    ImageCropperModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatMenuModule,
    MatRadioModule,
    MatTableModule,
    MatTabsModule,
    MatSidenavModule,
    MatSelectModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
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
    ClassificationService, 
    CodeToSymbolPipe,
    DimensionPipe,
    DimensionUnitPipe,
    DistancePipe,
    DistanceUnitPipe,
    EpochToDatePipe,
    LocationService,
    GameService,
    GdprService,
    HomeService,
    MessageService,
    ReviewService,
    SearchService,
    TagService,
    ToolbarService,
    TransactionService,
    UserService,
    WalletService,
    WeightPipe,
    WeightUnitPipe,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule {}
