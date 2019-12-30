//Note
//  https://angular.io/api/router/Routes
//Core
import { Routes, RouterModule } from "@angular/router";
import { RoutesManager } from "./route.manager";
//Components
import { AboutComponent } from "../components/about.component";
import { AssetCreateComponent } from "../components/asset-create.component";
import { AssetListComponent } from "../components/asset-list.component";
import { AssetMapComponent } from "../components/asset-map.component";
import { AssetReadComponent } from "../components/asset-read.component";
import { AssetUpdateComponent } from "../components/asset-update.component";
import { BarcodeScannerComponent } from "../components/barcode-scanner.component";
import { FourWayChessBoardComponent } from "../components/four-way-chess-board.component";
import { ContactComponent } from "../components/contact.component";
import { ConversationListComponent } from "../components/conversation-list.component";
import { DashboardComponent } from "../components/dashboard.component";
import { EmailChangeRequestComponent } from "../components/email-change-request.component";
import { EmailChangeReplyComponent } from "../components/email-change-reply.component";
import { EventCreateComponent } from "../components/event-create.component";
import { EventListComponent } from "../components/event-list.component";
import { EventMapComponent } from "../components/event-map.component";
import { EventReadComponent } from "../components/event-read.component";
import { EventUpdateComponent } from "../components/event-update.component";
import { FAQComponent } from "../components/faq.component";
import { GameDashboardComponent } from "../components/game-dashboard.component";
import { HomeComponent } from "../components/home.component";
import { LegalComponent } from "../components/legal.component";
import { LogoutComponent } from "../components/logout.component";
import { LoginComponent } from "../components/login.component";
import { MessageCreateComponent } from "../components/message-create.component";
import { MessageReadComponent } from "../components/message-read.component";
import { MessageListComponent } from "../components/message-list.component";
import { PasswordChangeForgottenRequestComponent } from "../components/password-change-forgotten-request.component";
import { PasswordChangeComponent } from "../components/password-change.component";
import { PasswordChangeForgottenComponent } from "../components/password-change-forgotten.component";
import { PrivacyPolicyComponent } from "../components/privacy-policy.component";
import { TagControlComponent } from "../components/tag-control.component";
import { TermsAndConditionsComponent } from "../components/terms-and-conditions.component";
import { TransactionCreateComponent } from "../components/transaction-create.component";
import { TransactionListComponent } from "../components/transaction-list.component";
import { TransactionMapComponent } from "../components/transaction-map.component";
import { TransactionReadComponent } from "../components/transaction-read.component";
import { TransactionUpdateComponent } from "../components/transaction-update.component";
import { ReportComponent } from "../components/report.component";
import { SearchEventsComponent } from "../components/search-events.component";
import { SearchProductsComponent } from "../components/search-products.component";
import { SearchCategoriesComponent } from "../components/search-categories.component";
import { SearchTransactionsComponent } from "../components/search-transactions.component";
import { SearchMembersComponent } from "../components/search-members.component";
import { SearchServicesComponent } from "../components/search-services.component";
import { SettingsComponent } from "../components/settings.component";
import { UserActivateComponent } from "../components/user-activate.component";
import { UserCalendarComponent } from "../components/user-calendar.component";
import { UserCreateComponent } from "../components/user-create.component";
import { UserListComponent } from "../components/user-list.component";
import { UserUpdateComponent } from "../components/user-update.component";
import { UserMapComponent } from "../components/user-map.component";
import { UserAssetsComponent } from "../components/user-assets.component";
import { UserReadComponent } from "../components/user-read.component";
import { WalletListComponent } from "../components/wallet-list.component";
import { WalletReadComponent } from "../components/wallet-read.component";

export const routes: Routes = [
  { path: "about", component: AboutComponent },
  { path: "asset/create", component: AssetCreateComponent, canActivate: [RoutesManager] },
  { path: "asset/update", component: AssetUpdateComponent, canActivate: [RoutesManager] },
  { path: "asset/search/products", component: SearchProductsComponent, canActivate: [RoutesManager] },
  { path: "asset/search/services", component: SearchServicesComponent, canActivate: [RoutesManager] },
  { path: "asset/search/categories", component: SearchCategoriesComponent, canActivate: [RoutesManager] },
  { path: "asset/delete", component: AssetUpdateComponent, canActivate: [RoutesManager] },
  { path: "asset/read/one", component: AssetReadComponent, canActivate: [RoutesManager] },
  { path: "asset/read/one/:id", component: AssetReadComponent, canActivate: [RoutesManager] },
  { path: "asset/read/my/list", component: UserAssetsComponent, canActivate: [RoutesManager] },
  { path: "asset/read/list", component: AssetListComponent, canActivate: [RoutesManager] },
  { path: "asset/read/map", component: AssetMapComponent, canActivate: [RoutesManager] },
  { path: "barcode", component: BarcodeScannerComponent },
  { path: "contact", component: ContactComponent },
  { path: "dashboard", component: DashboardComponent, canActivate: [RoutesManager] },
  { path: "event/create", component: EventCreateComponent, canActivate: [RoutesManager] },
  { path: "event/read/one", component: EventReadComponent, canActivate: [RoutesManager] },
  { path: "event/read/one/:id", component:EventReadComponent, canActivate: [RoutesManager] },
  { path: "event/read/list", component: EventListComponent, canActivate: [RoutesManager] },
  { path: "event/read/map", component: EventMapComponent, canActivate: [RoutesManager] },
  { path: "event/update", component: EventUpdateComponent, canActivate: [RoutesManager] },
  { path: "event/search/dates", component: SearchEventsComponent, canActivate: [RoutesManager] },
  { path: "faq", component: FAQComponent },
  { path: "game/dashboard", component: GameDashboardComponent, canActivate: [RoutesManager] },
  { path: "game/fourwaychess", component: FourWayChessBoardComponent, canActivate: [RoutesManager] },
  { path: "home", component: HomeComponent },
  { path: "home/cn", component: HomeComponent },
  { path: "home/en", component: HomeComponent },
  { path: "home/es", component: HomeComponent },
  { path: "home/de", component: HomeComponent },
  { path: "home/fr", component: HomeComponent },
  { path: "home/it", component: HomeComponent },
  { path: "home/pt", component: HomeComponent },
  { path: "legal", component: LegalComponent },
  { path: "legal/privacypolicy", component: PrivacyPolicyComponent },
  { path: "legal/termsandconditions", component: TermsAndConditionsComponent },
  { path: "login", component: LoginComponent },
  { path: "logout", component: LogoutComponent, canActivate: [RoutesManager] },
  { path: "message/create", component: MessageCreateComponent, canActivate: [RoutesManager] },
  { path: "message/read/one", component: MessageReadComponent, canActivate: [RoutesManager] },
  { path: "message/read/list/messages", component: MessageListComponent, canActivate: [RoutesManager] },
  { path: "message/read/list/conversations", component: ConversationListComponent, canActivate: [RoutesManager] },
  { path: "settings", component: SettingsComponent, canActivate: [RoutesManager] },
  { path: "report", component: ReportComponent, canActivate: [RoutesManager] },
  { path: "transaction/create", component: TransactionCreateComponent, canActivate: [RoutesManager] },
  { path: "transaction/read/one", component: TransactionReadComponent, canActivate: [RoutesManager] },
  { path: "transaction/read/one/:id", component: TransactionReadComponent, canActivate: [RoutesManager] },
  { path: "transaction/read/list", component: TransactionListComponent, canActivate: [RoutesManager] },
  { path: "transaction/read/map", component: TransactionMapComponent, canActivate: [RoutesManager] },
  { path: "transaction/update", component: TransactionUpdateComponent, canActivate: [RoutesManager] },
  { path: "transaction/search/dates", component: SearchTransactionsComponent, canActivate: [RoutesManager] },
  { path: "tag", component: TagControlComponent },
  { path: "user/create", component: UserCreateComponent },
  { path: "user/update", component: UserUpdateComponent, canActivate: [RoutesManager] },
  { path: "user/update/email/request", component: EmailChangeRequestComponent },
  { path: "user/update/email/reply/:secret", component: EmailChangeReplyComponent },
  { path: "user/update/password/forgotten/request", component: PasswordChangeForgottenRequestComponent },
  { path: "user/update/password/forgotten/reply/:secret", component: PasswordChangeForgottenComponent },
  { path: "user/update/password/request", component: PasswordChangeComponent, canActivate: [RoutesManager] },
  { path: "user/update/activate/reply/:secret", component: UserActivateComponent },
  { path: "user/delete", component: UserUpdateComponent, canActivate: [RoutesManager] },
  { path: "user/search/member", component: SearchMembersComponent, canActivate: [RoutesManager] },
  { path: "user/read/one", component: UserReadComponent, canActivate: [RoutesManager] },
  { path: "user/read/one/:id", component: UserReadComponent, canActivate: [RoutesManager] },
  { path: "user/read/calendar", component: UserCalendarComponent, canActivate: [RoutesManager] },
  { path: "user/read/list", component: UserListComponent, canActivate: [RoutesManager] },
  { path: "user/read/map", component: UserMapComponent, canActivate: [RoutesManager] },  
  { path: "wallet/read/one", component: WalletReadComponent, canActivate: [RoutesManager] },
  { path: "wallet/read/list", component: WalletListComponent, canActivate: [RoutesManager] },
  { path: "", redirectTo: "dashboard", pathMatch: "full" }
];

export const appRouterProvider = RouterModule.forRoot(routes);