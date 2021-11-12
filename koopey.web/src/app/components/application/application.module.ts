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
import { AssetService } from "../../services/asset.service";
import { AuthenticationService } from "../../services/authentication.service";
import { BarcodeService } from "../../services/barcode.service";
import { BarcodeScannerComponent } from "../common/barcode/scanner/barcode-scanner.component";
import { BrowserModule, HammerModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ClickService } from "../../services/click.service";
import { ConfigurationComponent } from "../configuration/configuration.component";
import { ConfirmDialogComponent } from "../confirm/confirm-dialog.component";
import { ContactComponent } from "../contact/contact.component";
import { ConversationService } from "../../services/conversation.service";
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
import { ImageListComponent } from "../image/list/image-list.component";
import { GDPRComponent } from "../gdpr/gdpr.component";
import { LocationService } from "../../services/location.service";

import { LocationEditComponent } from "../location/edit/location-edit.component";
import { LocationListComponent } from "../location/list/location-list.component";
import { LocationTypeComponent } from "../location/type/location-type.component";
//import { LocationReadComponent } from "../location/read/location-read.component";

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
import { PositionButtonComponent } from "../common/position/positionbutton.component";
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
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
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
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatRadioModule } from "@angular/material/radio";
import { MatTableModule } from "@angular/material/table";
import { MatTabsModule } from "@angular/material/tabs";
import { MatSelectModule } from "@angular/material/select";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatSortModule } from "@angular/material/sort";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MessageService } from "../../services/message.service";
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
import { WalletControlComponent } from "../wallet/control/wallet-control.component";
import { WalletDialogComponent } from "../wallet/dialog/wallet-dialog.component";
import { WalletListComponent } from "../wallet/list/wallet-list.component";
import { WalletReadComponent } from "../wallet/read/wallet-read.component";
import { WalletService } from "../../services/wallet.service";
import { WeightPipe } from "../../pipes/weight.pipe";
import { WeightUnitPipe } from "../../pipes/weightunit.pipe";
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
    AdvertboxComponent,
    AppComponent,
    ArticleListComponent,
    ArticleReadComponent,
    ArticleEditComponent,
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
    ImageListComponent,
    GDPRComponent,
    LocationEditComponent,
    LocationListComponent,
    LocationTypeComponent,
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
    PositionButtonComponent,
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
    UserboxComponent,
    UserListComponent,
    UserAssetsComponent,
    UserReadComponent,
    UserEditComponent,
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
    MessageCreateDialogComponent,
    MobileDialogComponent,
    QRCodeDialogComponent,
    ReviewCreateDialogComponent,
    TransactionDialogComponent,
  ],
  exports: [
    /* HammerModule,
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
    MatMenuModule,
    MatRadioModule,
    MatTabsModule,
    MatSelectModule,
    MatSidenavModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatToolbarModule,*/
  ],
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
    MatPaginatorModule,
    MatMenuModule,
    MatRadioModule,
    MatTableModule,
    MatTabsModule,
    MatSidenavModule,
    MatSelectModule,
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
    ClickService,
    ConversationService,
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
    TransactionService,
    UserService,
    WalletService,
    WeightPipe,
    WeightUnitPipe,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule {}
