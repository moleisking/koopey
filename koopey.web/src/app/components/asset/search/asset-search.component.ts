import { AlertService } from "../../../services/alert.service";
import { Asset } from "../../../models/asset";
import { AssetService } from "../../../services/asset.service";
import { BaseComponent } from "src/app/components/base/base.component";
import {
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Environment } from "src/environments/environment";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Location } from "../../../models/location";
import { ActivatedRoute, Router } from "@angular/router";
import { Search } from "../../../models/search";
import { SearchService } from "../../../services/search.service";
import { Subscription } from "rxjs";
import { Tag } from "../../../models/tag";
import { AssetType } from "src/app/models/type/AssetType";

@Component({
  selector: "asset-search",
  styleUrls: ["asset-search.css"],
  templateUrl: "asset-search.html",
})
export class AssetSearchComponent extends BaseComponent
  implements OnInit, OnDestroy {
  public assets: Array<Asset> = new Array<Asset>();
  public busy: boolean = false;
  public formGroup!: FormGroup;
  public search: Search = new Search();
  private searchSubscription: Subscription = new Subscription();

  constructor(
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService,
    private assetService: AssetService,
    private formBuilder: FormBuilder,
    private router: Router,
    public sanitizer: DomSanitizer,
    private searchService: SearchService
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((parameter) => {
      this.search.type = parameter["type"] || "product";
    });
    if (this.search.type == AssetType.Product) {
      this.search.period = "once";
    }

    this.searchSubscription = this.searchService
      .getSearch()
      .subscribe((search: Search) => {
        this.search.radius = Environment.Default.Radius;
        this.search.type = "service";
        this.search = search;
      });

    this.formGroup = this.formBuilder.group({
      radius: [this.search.radius],
      tags: [this.search.tags, [Validators.required]],
      min: [
        this.search.min,
        [
          Validators.required,
          Validators.min(-180),
          Validators.minLength(1),
          Validators.max(180),
          Validators.maxLength(11),
        ],
      ],
      max: [
        this.search.max,
        [
          Validators.required,
          Validators.min(-180),
          Validators.minLength(1),
          Validators.max(180),
          Validators.maxLength(11),
        ],
      ],
      currency: [this.search.currency],
      period: [this.search.period],
    });
  }

  ngOnDestroy() {
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  private hasCurrency(currency: string): boolean {
    return Environment.Transaction.Currencies.includes(currency);
  }

  private hasTransactions(): boolean {
    return Environment.Menu.Transactions;
  }

  public find() {
    if (!this.search.latitude && !this.search.longitude) {
      this.alertService.error("ERROR_NOT_LOCATION");
    } else if (this.search.min == null && this.search.max == null) {
      this.alertService.error("ERROR_VALUES_OUT_OF_RANGE");
    } else {
      this.busy = true;
      this.assetService.readAssets(this.search).subscribe(
        (assets) => {
          this.assets = assets;
          this.assetService.setAssets(this.assets);
          this.searchService.setSearch(this.search);
        },
        (error: Error) => {
          this.alertService.error(error.message);
        },
        () => {
          this.router.navigate(["/asset/list"]);
        }
      );
    }
  }
}
