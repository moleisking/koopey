import { AboutComponent } from "../components/about/about.component";
import { AssetEditComponent } from "../components/asset/edit/asset-edit.component";
import { AssetListComponent } from "../components/asset/list/asset-list.component";
import { AssetMapComponent } from "../components/asset/map/asset-map.component";
import { AssetReadComponent } from "../components/asset/read/asset-read.component";
import { AssetSearchComponent } from "../components/asset/search/asset-search.component";
import { BarcodeScannerComponent } from "../components/common/barcode/scanner/barcode-scanner.component";
import { ConfigurationComponent } from "../components/configuration/configuration.component";
import { ContactComponent } from "../components/contact/contact.component";
import { ConversationListComponent } from "../components/conversation/conversation-list.component";
import { DashboardComponent } from "../components/dashboard/dashboard.component";
import { EmailChangeRequestComponent } from "../components/authentication/email-change/request/email-change-request.component";
import { EmailChangeReplyComponent } from "../components/authentication/email-change/reply/email-change-reply.component";
import { FAQComponent } from "../components/faq/faq.component";
import { HomeComponent } from "../components/home/home.component";
import { GDPRComponent } from "../components/gdpr/gdpr.component";
import { LocationEditComponent } from "../components/location/edit/location-edit.component";
import { LocationListComponent } from "../components/location/list/location-list.component";
import { LogInOutComponent } from "../components/authentication/loginout-button/loginout-button.component";
import { LoginComponent } from "../components/authentication/login/login.component";
import { MessageCreateComponent } from "../components/message/create/message-create.component";
import { MessageReadComponent } from "../components/message/read/message-read.component";
import { MessageListComponent } from "../components/message/list/message-list.component";
import { PasswordForgottenRequestComponent } from "../components/authentication/password/forgotten/request/password-forgotten-request.component";
import { PasswordChangeComponent } from "../components/authentication/password/change/password-change.component";
import { PasswordChangeForgottenComponent } from "../components/authentication/password/forgotten/password-change-forgotten.component";
import { RegisterComponent } from "../components/user/register/register.component";
import { TagboxComponent } from "../components/common/tag/tagbox.component";
import { TagSearchComponent } from "../components/tag/search/tag-search.component";
import { TransactionEditComponent } from "../components/transaction/edit/transaction-edit.component";
import { TransactionListComponent } from "../components/transaction/list/transaction-list.component";
import { TransactionReadComponent } from "../components/transaction/read/transaction-read.component";
import { ReportComponent } from "../components/report/report.component";
import { Routes, RouterModule } from "@angular/router";
import { RoutesManager } from "./route.manager";
import { TransactionSearchComponent } from "../components/transaction/search/transaction-search.component";
import { UserActivateComponent } from "../components/authentication/activate/user-activate.component";
import { UserAssetsComponent } from "../components/user/assets/user-assets.component";
import { UserCalendarComponent } from "../components/user/calendar/user-calendar.component";
import { UserListComponent } from "../components/user/list/user-list.component";
import { UserEditComponent } from "../components/user/edit/user-edit.component";
import { UserSearchComponent } from "../components/user/search/user-search.component";
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
    path: "asset/search",
    component: AssetSearchComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "tag/search",
    component: TagSearchComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/read/:id",
    component: AssetReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/my/list",
    component: UserAssetsComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/list",
    component: AssetListComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "asset/map",
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
  { path: "faq", component: FAQComponent },
  { path: "home", component: HomeComponent },
  { path: "gdpr", component: GDPRComponent },
  { path: "location/edit", component: LocationEditComponent },
  { path: "location/list", component: LocationListComponent },
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
    path: "message/read",
    component: MessageReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "message/list/messages",
    component: MessageListComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "message/list/conversations",
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
    path: "transaction/edit",
    component: TransactionEditComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "transaction/read/:id",
    component: TransactionReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "transaction/list",
    component: TransactionListComponent,
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
    path: "user/edit",
    component: UserEditComponent,
    canActivate: [RoutesManager],
  },
  { path: "user/email/request", component: EmailChangeRequestComponent },
  {
    path: "user/email/reply/:secret",
    component: EmailChangeReplyComponent,
  },
  {
    path: "user/password/forgotten/request",
    component: PasswordForgottenRequestComponent,
  },
  {
    path: "user/password/forgotten/reply/:secret",
    component: PasswordChangeForgottenComponent,
  },
  {
    path: "user/password/request",
    component: PasswordChangeComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "user/activate/reply/:secret",
    component: UserActivateComponent,
  },
  {
    path: "user/delete",
    component: UserEditComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "user/search",
    component: UserSearchComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "user/read/:id",
    component: UserReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "user/read/calendar",
    component: UserCalendarComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "user/list",
    component: UserListComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "wallet/read/:id",
    component: WalletReadComponent,
    canActivate: [RoutesManager],
  },
  {
    path: "wallet/list",
    component: WalletListComponent,
    canActivate: [RoutesManager],
  },
  { path: "", redirectTo: "dashboard", pathMatch: "full" },
];

export const appRouterProvider = RouterModule.forRoot(routes);
