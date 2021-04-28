import {
  Component,
  OnInit,
  OnDestroy,
  ElementRef,
  ViewChild,
} from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../../services/click.service";
import { AssetService } from "../../../../services/asset.service";
import { SearchService } from "../../../../services/search.service";
import { TranslateService } from "@ngx-translate/core";
import { Config } from "../../../../config/settings";
import { Location } from "../../../../models/location";
import { Asset } from "../../../../models/asset";
import { Search } from "../../../../models/search";
import { Tag } from "../../../../models/tag";
import { User } from "../../../../models/user";

@Component({
  selector: "product-search-component",
  templateUrl: "product-search.html",
  styleUrls: ["product-search.css"],
})
/*Note* Do not use fors as it blocks location controls*/
export class ProductSearchComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private searchSubscription: Subscription = new Subscription();
  public assets: Array<Asset> = new Array<Asset>();
  public search: Search = new Search();
  private searching: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService,
    private clickService: ClickService,
    private assetService: AssetService,
    private router: Router,
    private sanitizer: DomSanitizer,
    private searchService: SearchService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {}

  ngAfterContentInit() {
    this.clickService.createInstance(
      ActionIcon.SEARCH,
      CurrentComponent.AssetSearchComponent
    );
    this.clickSubscription = this.clickService
      .getAssetSearchClick()
      .subscribe(() => {
        this.findAssets();
      });
  }

  ngAfterViewInit() {
    this.activatedRoute.queryParams.subscribe((parameter) => {
      this.search.type = parameter["type"] || "product";
    });
    this.search.period = "once";
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  private getMeasurementUnit(): string {
    return Config.default_measure;
  }

  private hasCurrency(currency: string): boolean {
    return Config.transaction_currencies.includes(currency);
  }

  private hasTransactions(): boolean {
    return Config.business_model_transactions;
  }

  public onUpdateAddress(location: Location) {
    if (location) {
      console.log("updateAddress true");
      this.search.latitude = location.latitude;
      this.search.longitude = location.longitude;
    } else {
      console.log("updateAddress false");
    }
  }

  public onUpdatePosition(location: Location) {
    if (location) {
      console.log("updatePosition true");
      this.search.latitude = location.latitude;
      this.search.longitude = location.longitude;
    } else {
      console.log("updatePosition false");
    }
  }

  public handleTagUpdated(selectedTags: Array<Tag>) {
    this.search.tags = selectedTags;
  }

  private findAssets() {
    console.log("findAssets()");
    if (!this.search.latitude && !this.search.longitude) {
      this.alertService.error("ERROR_NOT_LOCATION");
    } else if (this.search.min == null && this.search.max == null) {
      this.alertService.error("ERROR_VALUES_OUT_OF_RANGE");
    } else {
      //Set progress icon
      this.searching = true;
      console.log(this.search);
      this.assetService.readAssets(this.search).subscribe(
        (assets) => {
          this.assets = assets;
          this.assetService.setAssets(this.assets);
          this.searchService.setSearch(this.search);
          console.log(this.assets);
        },
        (error) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.router.navigate(["/asset/read/list"]);
        }
      );
    }
  }
}
