import { Injectable } from "@angular/core";
import { Observable, Subject, BehaviorSubject } from "rxjs";

export enum CurrentComponent {
  AboutComponent = 1,
  AppointmentCalendarComponent = 5,
  AppointmentCreateComponent = 6,
  AppointmentDeleteComponent = 7,
  AppointmentListComponent = 8,
  AppointmentMapComponent = 9,
  AppointmentUpdateComponent = 10,
  AppointmentReadComponent = 11,
  AppointmentSearchComponent = 31,
  ArticleCreateComponent = 17,
  ArticleDeleteComponent = 18,
  ArticleListComponent = 19,
  ArticleUpdateComponent = 20,
  AssetCalanderComponent = 21,
  AssetCreateComponent = 22,
  AssetDeleteComponent = 23,
  AssetListComponent = 24,
  AssetMapComponent = 25,
  AssetReadComponent = 27,
  AssetSearchComponent = 29,
  AssetUpdateComponent = 26,
  BarcodeScannerControlComponent = 2,
  ContactComponent = 3,
  DesktopComponent = 4,
  ImageCreateComponent = 12,
  ImageDeleteComponent = 13,
  ImageListComponent = 14,
  ImageUpdateComponent = 15,
  ImageReadComponent = 16,
  MemberSearchComponent = 32,
  ReviewCreateComponent = 28,
  ServiceSearchComponent = 33,
  TransactionCreateComponent = 43,
  TransactionDeleteComponent = 44,
  TransactionListComponent = 45,
  TransactionMapComponent = 46,
  TransactionSearchComponent = 34,
  TransactionUpdateComponent = 47,
  TransactionReadComponent = 48,
  UserCalendarComponent = 36,
  RegisterComponent = 37,
  UserDeleteComponent = 38,
  UserListComponent = 39,
  UserMapComponent = 40,
  UserUpdateComponent = 41,
  UserReadComponent = 42,
}
const ActionIcon = {
  CREATE: "add" as "add",
  CAMERA: "photo_camera" as "photo_camera",
  DELETE: "delete" as "delete",
  ERROR: "error" as "error",
  EMAIL: "email" as "email",
  MAP: "layers" as "layers",
  LIST: "view_list" as "view_list",
  UPDATE: "save" as "save",
  SEARCH: "search" as "search",
  PAYMENT: "payment" as "payment",
};
type ActionIcon = typeof ActionIcon[keyof typeof ActionIcon];
export { ActionIcon };

@Injectable()
export class ClickService {
  private aboutClick = new Subject<any>();
  private barcodeScannerClick = new Subject<any>();
  private contactClick = new Subject<any>();
  private eventCalendarClick = new Subject<any>();
  private eventCreateClick = new Subject<any>();
  private eventDeleteClick = new Subject<any>();
  private eventListClick = new Subject<any>();
  private eventMapClick = new Subject<any>();
  private eventUpdateClick = new Subject<any>();
  private eventReadClick = new Subject<any>();
  private imageCreateClick = new Subject<any>();
  private imageDeleteClick = new Subject<any>();
  private imageListClick = new Subject<any>();
  private imageUpdateClick = new Subject<any>();
  private imageReadClick = new Subject<any>();
  private userCalendarClick = new Subject<any>();
  private userCreateClick = new Subject<any>();
  private userDeleteClick = new Subject<any>();
  private userListClick = new Subject<any>();
  private userMapClick = new Subject<any>();
  private userUpdateClick = new Subject<any>();
  private userReadClick = new Subject<any>();
  private articleCreateClick = new Subject<any>();
  private articleDeleteClick = new Subject<any>();
  private articleListClick = new Subject<any>();
  private articleUpdateClick = new Subject<any>();
  private articleReadClick = new Subject<any>();
  private assetCreateClick = new Subject<any>();
  private assetDeleteClick = new Subject<any>();
  private assetListClick = new Subject<any>();
  private assetMapClick = new Subject<any>();
  private assetUpdateClick = new Subject<any>();
  private assetReadClick = new Subject<any>();
  private reviewCreateClick = new Subject<any>();
  private searchAssetClick = new Subject<any>();
  private searchAssetsClick = new Subject<any>();
  private searchEventsClick = new Subject<any>();
  private searchMemberClick = new Subject<any>();
  private searchServicesClick = new Subject<any>();
  private searchTransactionClick = new Subject<any>();
  private searchTransactionsClick = new Subject<any>();
  private transactionCreateClick = new Subject<any>();
  private transactionDeleteClick = new Subject<any>();
  private transactionListClick = new Subject<any>();
  private transactionMapClick = new Subject<any>();
  private transactionUpdateClick = new Subject<any>();
  private transactionReadClick = new Subject<any>();
  // public currentComponent: CurrentComponent;
  private currentComponent = new BehaviorSubject<CurrentComponent>(
    CurrentComponent.DesktopComponent
  );
  private visible = new BehaviorSubject<Boolean>(false);
  private icon = new BehaviorSubject<ActionIcon>(ActionIcon.ERROR);

  constructor() {}

  //  About
  public setAboutClick(): void {
    this.aboutClick.next();
  }

  public getAboutUsClick(): Observable<any> {
    return this.aboutClick.asObservable();
  }

  //  Assets
  public setAssetCreateClick(): void {
    this.assetCreateClick.next();
  }

  public getAssetCreateClick(): Observable<any> {
    return this.assetCreateClick.asObservable();
  }

  public setAssetDeleteClick(): void {
    this.assetDeleteClick.next();
  }

  public getAssetDeleteClick(): Observable<any> {
    return this.assetDeleteClick.asObservable();
  }

  public setAssetUpdateClick(): void {
    this.assetUpdateClick.next();
  }

  public getAssetUpdateClick(): Observable<any> {
    return this.assetUpdateClick.asObservable();
  }

  public setAssetListClick(): void {
    this.assetListClick.next();
  }

  public getAssetListClick(): Observable<any> {
    return this.assetListClick.asObservable();
  }

  public setAssetMapClick(): void {
    this.assetMapClick.next();
  }

  public getAssetMapClick(): Observable<any> {
    return this.assetMapClick.asObservable();
  }

  public setAssetReadClick(): void {
    this.assetReadClick.next();
  }

  public getAssetReadClick(): Observable<any> {
    return this.assetReadClick.asObservable();
  }

  //  Barcode Scanner
  public setBarcodeScannerClick(): void {
    this.barcodeScannerClick.next();
  }

  public getBarcodeScannerClick(): Observable<any> {
    return this.barcodeScannerClick.asObservable();
  }

  //  Contact
  public setContactClick(): void {
    this.contactClick.next();
  }

  public getContactClick(): Observable<any> {
    return this.contactClick.asObservable();
  }

  //  Events
  public setEventCreateClick(): void {
    this.eventCreateClick.next();
  }

  public getEventCreateClick(): Observable<any> {
    return this.eventCreateClick.asObservable();
  }

  public setEventDeleteClick(): void {
    this.eventDeleteClick.next();
  }

  public getEventDeleteClick(): Observable<any> {
    return this.eventDeleteClick.asObservable();
  }

  public setEventUpdateClick(): void {
    this.eventUpdateClick.next();
  }

  public getEventUpdateClick(): Observable<any> {
    return this.eventUpdateClick.asObservable();
  }

  public setEventListClick(): void {
    this.eventListClick.next();
  }

  public getEventListClick(): Observable<any> {
    return this.eventListClick.asObservable();
  }

  public setEventMapClick(): void {
    this.eventMapClick.next();
  }

  public getEventMapClick(): Observable<any> {
    return this.eventMapClick.asObservable();
  }

  public setEventReadClick(): void {
    this.eventReadClick.next();
  }

  public getEventReadClick(): Observable<any> {
    return this.eventReadClick.asObservable();
  }

  //  Images
  public setImageCreateClick(): void {
    this.imageCreateClick.next();
  }

  public getImageCreateClick(): Observable<any> {
    return this.imageCreateClick.asObservable();
  }

  public setImageDeleteClick(): void {
    this.imageDeleteClick.next();
  }

  public getImageDeleteClick(): Observable<any> {
    return this.imageDeleteClick.asObservable();
  }

  public setImageListClick(): void {
    this.imageListClick.next();
  }

  public getImageListClick(): Observable<any> {
    return this.imageListClick.asObservable();
  }

  public setImageReadClick(): void {
    this.imageReadClick.next();
  }

  public getImageReadClick(): Observable<any> {
    return this.imageReadClick.asObservable();
  }

  public setImageUpdateClick(): void {
    this.imageUpdateClick.next();
  }

  public getImageUpdateClick(): Observable<any> {
    return this.imageUpdateClick.asObservable();
  }

  // Review

  public setReviewCreateClick(): void {
    this.reviewCreateClick.next();
  }

  public getReviewCreateClick(): Observable<any> {
    return this.reviewCreateClick.asObservable();
  }

  //  Search
  public setAssetSearchClick(): void {
    this.searchAssetClick.next();
  }

  public getAssetSearchClick(): Observable<any> {
    return this.searchAssetClick.asObservable();
  }

  public setAppointmentSearchClick(): void {
    this.searchEventsClick.next();
  }

  public getAppointmentSearchClick(): Observable<any> {
    return this.searchEventsClick.asObservable();
  }

  public setMemberSearchClick(): void {
    this.searchMemberClick.next();
  }

  public getMemberSearchClick(): Observable<any> {
    return this.searchMemberClick.asObservable();
  }

  public setServiceSearchClick(): void {
    this.searchServicesClick.next();
  }

  public getServiceSearchClick(): Observable<any> {
    return this.searchServicesClick.asObservable();
  }

  /*  Tranasactions  */
  public setTransactionCreateClick(): void {
    this.transactionCreateClick.next();
  }

  public getTransactionCreateClick(): Observable<any> {
    return this.transactionCreateClick.asObservable();
  }

  public setTransactionListClick(): void {
    this.transactionListClick.next();
  }

  public getTransactionListClick(): Observable<any> {
    return this.transactionListClick.asObservable();
  }

  public setTransactionMapClick(): void {
    this.transactionMapClick.next();
  }

  public getTransactionMapClick(): Observable<any> {
    return this.transactionMapClick.asObservable();
  }

  public setTransactionSearchClick(): void {
    this.searchTransactionClick.next();
  }

  public getTransactionSearchClick(): Observable<any> {
    return this.searchTransactionClick.asObservable();
  }

  public setTransactionUpdateClick(): void {
    this.transactionUpdateClick.next();
  }

  public getTransactionUpdateClick(): Observable<any> {
    return this.transactionUpdateClick.asObservable();
  }

  /*  Users   */
  public setUserCalendarClick(): void {
    this.userCalendarClick.next();
  }

  public getUserCalendarClick(): Observable<any> {
    return this.userCalendarClick.asObservable();
  }

  public setUserCreateClick(): void {
    this.userCreateClick.next();
  }

  public getUserCreateClick(): Observable<any> {
    return this.userCreateClick.asObservable();
  }

  public setUserUpdateClick(): void {
    this.userUpdateClick.next();
  }

  public getUserUpdateClick(): Observable<any> {
    return this.userUpdateClick.asObservable();
  }

  public setUserListClick(): void {
    this.userListClick.next();
  }

  public getUserListClick(): Observable<any> {
    return this.userListClick.asObservable();
  }

  public setUserMapClick(): void {
    this.userMapClick.next();
  }

  public getUserMapClick(): Observable<any> {
    return this.userMapClick.asObservable();
  }

  public setUserReadClick(): void {
    this.userReadClick.next();
  }

  public getUserReadClick(): Observable<any> {
    return this.userReadClick.asObservable();
  }

  //  Parameters

  public getCurrentComponent(): Observable<CurrentComponent> {
    return this.currentComponent.asObservable();
  }

  public setCurrentComponent(currentComponent: CurrentComponent): void {
    this.currentComponent.next(currentComponent);
  }

  public getIcon(): Observable<String> {
    return this.icon.asObservable();
  }

  public setIcon(icon: ActionIcon): void {
    this.icon.next(icon);
  }

  public getVisible(): Observable<Boolean> {
    return this.visible.asObservable();
  }

  public setVisible(visible: Boolean): void {
    this.visible.next(visible);
  }

  public createInstance(
    actionIcon: ActionIcon,
    currentComponent: CurrentComponent
  ): void {
    this.currentComponent.next(currentComponent);
    this.icon.next(actionIcon);
    this.visible.next(true);
  }
  public destroyInstance(): void {
    this.currentComponent.next(CurrentComponent.DesktopComponent);
    this.icon.next(ActionIcon.ERROR);
    this.visible.next(false);
  }
}
