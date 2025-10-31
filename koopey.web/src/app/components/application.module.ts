
import { appRouterProvider } from "../routes/app.routes";
//import { TypeaheadModule } from "../../../com/typeahead/typeahead.module";

import { AboutComponent } from "./about/about.component";
import { AddressFieldComponent } from "./common/address-field/address-field.component";
import { AdvertboxComponent } from "./common/advert/advertbox.component";
import { AlertService } from "../services/alert.service";
import { AppComponent } from "./application.component";
import { AssetReadComponent } from "./asset/read/asset-read.component";
import { AssetEditComponent } from "./asset/edit/asset-edit.component";
import { AssetFilterComponent } from "./asset/filter/asset-filter.component";
import { AssetListComponent } from "./asset/list/asset-list.component";
import { AssetMapComponent } from "./asset/map/asset-map.component";
import { AssetSearchComponent } from "./asset/search/asset-search.component";
import { AssetService } from "../services/asset.service";
import { AssetTableComponent } from "./asset/table/asset-table.component";
import { AuthenticationInterceptor } from "../services/interceptors/authentication.interceptor";
import { AuthenticationService } from "../services/authentication.service";
import { BarcodeService } from "../services/barcode.service";
import { BarcodeScannerComponent } from "./common/barcode/scanner/barcode-scanner.component";
import { BrowserModule, HammerModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ConfigurationComponent } from "./configuration/configuration.component";
import { ConfirmDialogComponent } from "./confirm/confirm-dialog.component";
import { ContactComponent } from "./contact/contact.component";
import { ConversationListComponent } from "./conversation/conversation-list.component";
import { CropDialogComponent } from "./common/crop/crop-dialog.component";
import { CurrencyFieldComponent } from "./common/currency-field/currency-field.component";
import { CodeToSymbolPipe } from "../pipes/code-to-symbol.pipe";
import { DistancePipe } from "../pipes/distance.pipe";
import { DistanceUnitPipe } from "../pipes/distanceunit.pipe";
import { DimensionPipe } from "../pipes/dimension.pipe";
import {
    enableProdMode,
    CUSTOM_ELEMENTS_SCHEMA,
    NgModule,
    importProvidersFrom,
    provideZonelessChangeDetection,
    provideBrowserGlobalErrorListeners,
    provideZoneChangeDetection,
} from "@angular/core";
import { Environment } from "../../environments/environment";
import { EpochToDatePipe } from "../pipes/epoch-to-date.pipe";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { EmailChangeRequestComponent } from "./authentication/email-change/request/email-change-request.component";
import { EmailChangeReplyComponent } from "./authentication/email-change/reply/email-change-reply.component";
import { HomeComponent } from "./home/home.component";
import { HttpClient, HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import { FAQComponent } from "./faq/faq.component";
import { FooterComponent } from "./footer/footer.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
//import "hammerjs";
import { ImageCropperComponent } from "ngx-image-cropper";

import { GDPRComponent } from "./gdpr/gdpr.component";
import { LocationService } from "../services/location.service";

import { LocationEditComponent } from "./location/edit/location-edit.component";
import { LocationListComponent } from "./location/list/location-list.component";
import { LocationTypeComponent } from "./location/type/location-type.component";
//import { LocationReadComponent } from "../location/read/location-read.component";

import { LogInOutComponent } from "./authentication/loginout-button/loginout-button.component";
import { LoginComponent } from "./authentication/login/login.component";
import { MessageCreateComponent } from "./message/create/message-create.component";
import { MessageListComponent } from "./message/list/message-list.component";
import { MessageReadComponent } from "./message/read/message-read.component";
import { NegativeButtonComponent } from "./review/negative/negativebutton.component";
import { PasswordForgottenRequestComponent } from "./authentication/password/forgotten/request/password-forgotten-request.component";
import { PasswordChangeComponent } from "./authentication/password/change/password-change.component";
import { PasswordChangeForgottenComponent } from "./authentication/password/forgotten/password-change-forgotten.component";
import { PositionButtonComponent } from "./common/position-button/position-button.component";
import { PositiveButtonComponent } from "./review/positive/positivebutton.component";
import { QRCodeDialogComponent } from "./common/barcode/qrcode/qrcode-dialog.component";
import { RegisterComponent } from "./user/register/register.component";
import { ReportComponent } from "./report/report.component";
import { ReviewEditComponent } from "./review/edit/review-edit.component";
import { ReviewDialogComponent } from "./review/dialog/review-dialog.component";
import { ReviewTableComponent } from "./review/table/review-table.component";
import { RoutesManager } from "../routes/route.manager";
import { StarboxComponent } from "./review/star/starbox.component";
import { TagSearchComponent } from "./tag/search/tag-search.component";
import { TranslateModule, TranslateLoader, provideTranslateService } from "@ngx-translate/core";
import { provideTranslateHttpLoader, TranslateHttpLoader } from "@ngx-translate/http-loader";

import { GameService } from "../services/game.service";
import { GdprboxComponent } from "./common/gdpr/gdprbox.component";
import { GdprService } from "../services/gdpr.service";
import { HomeService } from "../services/home.service";
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
import { MessageService } from "../services/message.service";
import { PeriodboxComponent } from "./common/period/periodbox.component";
import { SearchService } from "../services/search.service";
import { TagService } from "../services/tag.service";
import { TransactionEditComponent } from "./transaction/edit/transaction-edit.component";
import { TransactionDialogComponent } from "./transaction/dialog/transaction-dialog.component";
import { TransactionListComponent } from "./transaction/list/transaction-list.component";
import { TransactionReadComponent } from "./transaction/read/transaction-read.component";
import { TransactionTableComponent } from "./transaction/table/transaction-table.component";
import { TransactionFilterComponent } from "./transaction/filter/transaction-filter.component";
import { TransactionSearchComponent } from "./transaction/search/transaction-search.component";
import { TransactionService } from "../services/transaction.service";
import { UserService } from "../services/user.service";
import { UserActivateComponent } from "./authentication/activate/user-activate.component";
import { UserboxComponent } from "./user/control/userbox.component";
import { UserEditComponent } from "./user/edit/user-edit.component";
import { UserFilterComponent } from "./user/filter/user-filter.component";
import { UserTableComponent } from "./user/table/user-table.component";
import { UserReadComponent } from "./user/read/user-read.component";
import { UserSearchComponent } from "./user/search/user-search.component";
import { WalletControlComponent } from "./wallet/control/wallet-control.component";
import { WalletDialogComponent } from "./wallet/dialog/wallet-dialog.component";
import { WalletListComponent } from "./wallet/list/wallet-list.component";
import { WalletReadComponent } from "./wallet/read/wallet-read.component";
import { WalletService } from "../services/wallet.service";
import { WeightPipe } from "../pipes/weight.pipe";
//import { ZXingScannerModule } from "@zxing/ngx-scanner";
import { ToolbarService } from "../services/toolbar.service";
import { ClassificationService } from "../services/classification.service";
import { TagviewComponent } from "./common/tag-field/tagview.component";
import { FileDownloadComponent } from "./common/file/filedownload.component";
import { DistanceFieldComponent } from "./common/distance-field/distance-field.component";
import { TagFieldComponent } from "./common/tag-field/tag-field.component";
import { ImageboxComponent } from "./common/image/imagebox.component";
import { StorageService } from "@services/storage.service";

if (Environment.type === "production" || Environment.type === "stage") {
    enableProdMode();
}

/*export function HttpLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http, './i18n/', '.json');
}

const httpLoaderFactory: (http: HttpClient) => TranslateHttpLoader = (http: HttpClient) =>
    new TranslateHttpLoader(http, './i18n/', '.json');*/

@NgModule({
    bootstrap: [AppComponent],
    declarations: [
        AboutComponent,
        AppComponent,
        AssetMapComponent,

        AssetListComponent,
        AssetReadComponent,
        AssetSearchComponent,
        TagSearchComponent,
        ConfigurationComponent,
        ConfirmDialogComponent,
        ContactComponent,
        ConversationListComponent,
        CropDialogComponent,
        DashboardComponent,
        EmailChangeReplyComponent,
        EmailChangeRequestComponent,
        FAQComponent,
        FileDownloadComponent,
        FooterComponent,
        GdprboxComponent,
        HomeComponent,
        GDPRComponent,
        LocationEditComponent,
        LocationListComponent,
        LocationTypeComponent,
        LogInOutComponent,
        MessageCreateComponent,
        MessageListComponent,
        MessageReadComponent,
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
        ReviewTableComponent,
        RegisterComponent,
        StarboxComponent,
        UserActivateComponent,
        UserboxComponent,
        UserTableComponent,
        UserEditComponent,
        UserReadComponent,
        UserSearchComponent,
        TransactionSearchComponent,
        TagviewComponent,
        TransactionEditComponent,
        TransactionDialogComponent,
        TransactionFilterComponent,
        TransactionListComponent,
        TransactionReadComponent,
        TransactionSearchComponent,
        TransactionTableComponent,

        WalletDialogComponent
    ],
    /* entryComponents: [
         ConfirmDialogComponent,
         QRCodeDialogComponent,
         ReviewDialogComponent,
         TransactionDialogComponent,
     ],*/
    exports: [],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthenticationInterceptor,
            multi: true
        },
        // provideZonelessChangeDetection(),
        provideZoneChangeDetection({ eventCoalescing: true }),
        provideBrowserGlobalErrorListeners(),
       /* importProvidersFrom([TranslateModule.forRoot({
            defaultLanguage: "en",
            loader: {
                provide: TranslateLoader,
                useFactory: httpLoaderFactory,
                deps: [HttpClient],
            },
        })]),*/
        provideHttpClient(withInterceptorsFromDi()),
        provideTranslateService({
            lang: 'en',
            fallbackLang: 'en',
            loader: provideTranslateHttpLoader({
                prefix: '/i18n/',
                suffix: '.json'
            })
        }),
        AssetService,
        RoutesManager,
        AuthenticationService,
        AlertService,
        BarcodeService,
        ClassificationService,
        LocationService,
        GameService,
        GdprService,
        HomeService,
        MessageService,
        SearchService,
        StorageService,
        TagService,
        ToolbarService,
        TransactionService,
        UserService,
        WalletService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    imports: [
        appRouterProvider,
        BrowserModule,
        FormsModule,
        ImageCropperComponent,
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

        //TranslateModule.forRoot({
        //    defaultLanguage: "en",
        //    loader: {
        //        provide: TranslateLoader,
        //        useFactory: HttpLoaderFactory,
        //        deps: [HttpClient],
        //    },
        //}),
        AddressFieldComponent,
        AssetFilterComponent,
        AssetTableComponent,
        CurrencyFieldComponent,
        DistanceFieldComponent,
        TagFieldComponent,
        CodeToSymbolPipe,
        DistancePipe,
        ImageboxComponent
    ]
})
export class AppModule { }
