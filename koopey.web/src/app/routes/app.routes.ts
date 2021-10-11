import { AboutComponent } from "../components/about/about.component";
import { AssetEditComponent } from "../components/asset/edit/asset-edit.component";
import { AssetListComponent } from "../components/asset/list/asset-list.component";
import { AssetMapComponent } from "../components/asset/map/asset-map.component";
import { AssetReadComponent } from "../components/asset/read/asset-read.component";
import { AppointmentCreateComponent } from "../components/appointment/edit/appointment-create.component";
import { AppointmentListComponent } from "../components/appointment/list/appointment-list.component";
import { AppointmentMapComponent } from "../components/appointment/map/appointment-map.component";
import { AppointmentReadComponent } from "../components/appointment/read/appointment-read.component";
import { AppointmentSearchComponent } from "../components/appointment/search/appointment-search.component";
import { AppointmentUpdateComponent } from "../components/appointment/edit/appointment-update.component";
import { BarcodeScannerComponent } from "../components/common/barcode/scanner/barcode-scanner.component";
import { CategorySearchComponent } from "../components/search/category-search.component";
import { ConfigurationComponent } from "../components/configuration/configuration.component";
import { ContactComponent } from "../components/contact/contact.component";
import { ConversationListComponent } from "../components/conversation/conversation-list.component";
import { DashboardComponent } from "../components/dashboard/dashboard.component";
import { EmailChangeRequestComponent } from "../components/authentication/email-change/request/email-change-request.component";
import { EmailChangeReplyComponent } from "../components/authentication/email-change/reply/email-change-reply.component";
import { FAQComponent } from "../components/faq/faq.component";
import { GdprboxComponent } from "../components/common/gdpr/gdprbox.component";
import { HomeComponent } from "../components/home/home.component";
import { LegalComponent } from "../components/legal/legal.component";
import { LogInOutComponent } from "../components/authentication/loginout-button/loginout-button.component";
import { LoginComponent } from "../components/authentication/login/login.component";
import { MemberSearchComponent } from "../components/user/search/member/member-search.component";
import { MessageCreateComponent } from "../components/message/create/message-create.component";
import { MessageReadComponent } from "../components/message/read/message-read.component";
import { MessageListComponent } from "../components/message/list/message-list.component";
import { PasswordForgottenRequestComponent } from "../components/authentication/password/forgotten/request/password-forgotten-request.component";
import { PasswordChangeComponent } from "../components/authentication/password/change/password-change.component";
import { PasswordChangeForgottenComponent } from "../components/authentication/password/forgotten/password-change-forgotten.component";
import { RegisterComponent } from "../components/user/register/register.component";
import { TagboxComponent } from "../components/common/tag/tagbox.component";
import { TransactionCreateComponent } from "../components/transaction/edit/transaction-create.component";
import { TransactionListComponent } from "../components/transaction/list/transaction-list.component";
import { TransactionMapComponent } from "../components/transaction/map/transaction-map.component";
import { TransactionReadComponent } from "../components/transaction/read/transaction-read.component";
import { TransactionUpdateComponent } from "../components/transaction/edit/transaction-update.component";
import { ReportComponent } from "../components/report/report.component";
import { Routes, RouterModule } from "@angular/router";
import { RoutesManager } from "./route.manager";
import { ProductSearchComponent } from "../components/asset/search/product/product-search.component";
import { ServiceSearchComponent } from "../components/asset/search/service/service-search.component";
import { TransactionSearchComponent } from "../components/transaction/search/transaction-search.component";
import { UserActivateComponent } from "../components/authentication/activate/user-activate.component";
import { UserCalendarComponent } from "../components/user/calendar/user-calendar.component";
import { UserListComponent } from "../components/user/list/user-list.component";
import { UserUpdateComponent } from "../components/user/edit/user-update.component";
import { UserAssetsComponent } from "../components/user/assets/user-assets.component";
import { UserReadComponent } from "../components/user/read/user-read.component";
import { WalletListComponent } from "../components/wallet/list/wallet-list.component";
import { WalletReadComponent } from "../components/wallet/read/wallet-read.component";

export const routes: Routes = [
  { path: "about", component: AboutComponent },
  {
    path: "asset/edit",
    component: AssetEditComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/search/products",
    component: ProductSearchComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/search/services",
    component: ServiceSearchComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/search/categories",
    component: CategorySearchComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/read/one",
    component: AssetReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/read/one/:id",
    component: AssetReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/read/my/list",
    component: UserAssetsComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/read/list",
    component: AssetListComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/read/map",
    component: AssetMapComponent,
    canActivate: [RoutesManager],
  },
  { path: "barcode", component: BarcodeScannerComponent },
  { path: "contact", component: ContactComponent },
  {
    path: "dashboard",
    component: DashboardComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "appointment/create",
    component: AppointmentCreateComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "appointment/read/one",
    component: AppointmentReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "appointment/read/one/:id",
    component: AppointmentReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "appointment/read/list",
    component: AppointmentListComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "appointment/read/map",
    component: AppointmentMapComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "appointment/update",
    component: AppointmentUpdateComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "appointment/search/dates",
    component: AppointmentSearchComponent,
    canActivate: [RoutesManager],
  },
  { path: "faq", component: FAQComponent },
  { path: "home", component: HomeComponent },
  { path: "legal", component: LegalComponent },
  { path: "gdpr", component: GdprboxComponent },
  { path: "login", component: LoginComponent },
  {
    path: "logout",
    component: LogInOutComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "message/create",
    component: MessageCreateComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "message/read/one",
    component: MessageReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "message/read/list/messages",
    component: MessageListComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "message/read/list/conversations",
    component: ConversationListComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "configuration",
    component: ConfigurationComponent,
    canActivate: [RoutesManager],
  },
  { path: "report", component: ReportComponent, canActivate: [RoutesManager] },
  {
    path: "transaction/create",
    component: TransactionCreateComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "transaction/read/one",
    component: TransactionReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "transaction/read/one/:id",
    component: TransactionReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "transaction/read/list",
    component: TransactionListComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "transaction/read/map",
    component: TransactionMapComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "transaction/update",
    component: TransactionUpdateComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "transaction/search/dates",
    component: TransactionSearchComponent,
    canActivate: [RoutesManager],
  },
  { path: "tag", component: TagboxComponent },
  { path: "register", component: RegisterComponent },
  {
    path: "user/update",
    component: UserUpdateComponent,
    canActivate: [RoutesManager],
  },
  { path: "user/update/email/request", component: EmailChangeRequestComponent },
  {
    path: "user/update/email/reply/:secret",
    component: EmailChangeReplyComponent,
  },
  {
    path: "user/update/password/forgotten/request",
    component: PasswordForgottenRequestComponent,
  },
  {
    path: "user/update/password/forgotten/reply/:secret",
    component: PasswordChangeForgottenComponent,
  },
  {
    path: "user/update/password/request",
    component: PasswordChangeComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "user/update/activate/reply/:secret",
    component: UserActivateComponent,
  },
  {
    path: "user/delete",
    component: UserUpdateComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "user/search/member",
    component: MemberSearchComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "user/read/one",
    component: UserReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "user/read/one/:id",
    component: UserReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "user/read/calendar",
    component: UserCalendarComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "user/read/list",
    component: UserListComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "wallet/read/one",
    component: WalletReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "wallet/read/list",
    component: WalletListComponent,
    canActivate: [RoutesManager],
  },
  { path: "", redirectTo: "dashboard", pathMatch: "full" },
];

export const appRouterProvider = RouterModule.forRoot(routes);
