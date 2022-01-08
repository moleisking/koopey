import { ActivatedRoute, Router } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { Asset } from "../../../models/asset";
import { AssetService } from "../../../services/asset.service";
import { Component, OnInit, OnDestroy, ViewChild, AfterViewChecked, AfterViewInit } from "@angular/core";
import { DistanceHelper } from "src/app/helpers/DistanceHelper";
import { DomSanitizer } from "@angular/platform-browser";
import { MatPaginator } from "@angular/material/paginator";
import { MatSort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";
import { OperationType } from "src/app/models/type/OperationType";
import { Review } from "src/app/models/review";
import { ReviewService } from "src/app/services/review.service";
import { Subscription } from "rxjs";
import { Transaction } from "src/app/models/transaction";

@Component({
  selector: "asset-table-component",
  styleUrls: ["asset-table.css"],
  templateUrl: "asset-table.html",
})
export class AssetTableComponent implements AfterViewChecked, AfterViewInit, OnInit, OnDestroy {
//Todo: Modify to transaction
  private assetSubscription: Subscription = new Subscription();
  public assets: Array<Asset> = new Array<Asset>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild("paginatorElement") paginatorElement: MatPaginator | undefined;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns: string[] = [
    "name",
    "firstImage",
    "positive",
    "negative",
    "distance",
    "review", "edit"
  ];
  dataSource = new MatTableDataSource<Asset>();

  constructor(
    private route: ActivatedRoute,
    private alertService: AlertService,
    private assetService: AssetService,
    private authenticationService: AuthenticationService,
    private router: Router,
    private reviewService: ReviewService,
    public sanitizer: DomSanitizer,
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe((parameters) => {
      if (parameters["type"] === "sales") {
        this.getMySales();
      } else {
        this.getMyPurchases();
      }
    });
  }

  ngAfterViewChecked() {
    if (this.assets && this.assets.length <= 10) {
      this.paginatorElement!.disabled = true;
      this.paginatorElement!.hidePageSize = true;
      this.paginatorElement!.showFirstLastButtons = false;
    }
  }

  ngAfterViewInit() {
    this.refreshDataSource();
  }

  ngOnDestroy() {
    if (this.assetSubscription) {
      this.assetSubscription.unsubscribe();
    }
  }

  public getDistance(latitude: number, longitude: number): string {
    if (latitude && longitude) {
      return DistanceHelper.distanceAndUnit(
        this.authenticationService.getLocalLatitude(),
        this.authenticationService.getLocalLatitude(),
        latitude,
        longitude
      );
    } else {
      return "";
    }
  }

  private getMyPurchases() {
    this.assetSubscription = this.assetService.searchByBuyer().subscribe(
      (assets: Array<Asset>) => {
        this.assets = assets && assets.length ? assets : new Array<Asset>();
      },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {
        this.refreshDataSource();
      }
    );
  }

  private getMySales() {
    this.assetSubscription = this.assetService.searchBySeller().subscribe(
      (assets: Array<Asset>) => {
        this.assets = assets && assets.length ? assets : new Array<Asset>();
      },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {
        this.refreshDataSource();
      }
    );
  }

  public gotoMyAsset(asset: Asset) {
    console.log(asset);
    this.assetService.setAsset(asset);
    this.router.navigate(["/asset/edit"]);
  }

  public create() {
    this.assetService.setType(OperationType.Create);
    this.router.navigate(["/asset/edit/"]); //, { 'queryParams': { 'type': 'product' } }
  }

  public edit(transaction: Transaction) {
    if (transaction.asset != undefined) {
      this.assetService.setType(OperationType.Update);
      this.assetService.setAsset(transaction.asset);
      this.router.navigate(["/asset/edit/"]);
    }
  }

  public openReview(transaction: Transaction) {
    if (transaction.asset != undefined && transaction.buyer != undefined && transaction.seller != undefined) {
      let review: Review = new Review();
      review.assetId = transaction.asset.id;
      review.buyerId = transaction.buyer.id;
      review.sellerId = transaction.seller.id;
      this.reviewService.setReview(review);
      this.router.navigate(["/review/edit/"]);
    }
    console.log(transaction);
  }

  private refreshDataSource() {
    this.dataSource = new MatTableDataSource<Asset>(
      this.assets as Array<any>
    );
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

}
