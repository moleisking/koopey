import { ActivatedRoute, Router } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { Asset } from "../../../models/asset";
import { AssetService } from "../../../services/asset.service";
import { AssetType } from "src/app/models/type/AssetType";
import { BaseComponent } from "src/app/components/base/base.component";
import { Component, EventEmitter, OnDestroy, OnInit, Output } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Environment } from "src/environments/environment";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Search } from "../../../models/search";
import { SearchService } from "../../../services/search.service";
import { Subscription } from "rxjs";
import { MeasurementType } from "src/app/models/type/MeasurementType";
import { MatSliderChange } from "@angular/material/slider";
import { TransactionService } from "src/app/services/transaction.service";
import { Transaction } from "src/app/models/transaction";

@Component({
  selector: "asset-search",
  styleUrls: ["asset-search.css"],
  templateUrl: "asset-search.html",
})
export class AssetSearchComponent extends BaseComponent
  implements OnInit, OnDestroy {
  public busy: boolean = false;
  public formGroup!: FormGroup;
  public metric: boolean = true;
  public search: Search = new Search();
  private searchSubscription: Subscription = new Subscription();
  @Output() onSearch: EventEmitter<any> = new EventEmitter<any>();

  constructor(
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService,
    private assetService: AssetService,
    private formBuilder: FormBuilder,
    private router: Router,
    public sanitizer: DomSanitizer,
    private searchService: SearchService,
    private transactionService: TransactionService,
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    if (localStorage.getItem("measurement") === MeasurementType.Metric) {
      this.metric = true;
    } else {
      this.metric = false;
    }

    this.activatedRoute.queryParams.subscribe((parameter) => {
      this.search.type = parameter["type"] || "product";
    });

    this.searchSubscription = this.searchService
      .getSearch()
      .subscribe((search: Search) => {
        this.search.radius = Environment.Default.Radius;
        this.search.type = "service";
        this.search = search;
      });

    this.formGroup = this.formBuilder.group({
      radius: [this.search.radius, [Validators.required]],
      tags: [this.search.tags, [Validators.required]],
      min: [
        this.search.min,
        [
          Validators.required,
          Validators.min(0),
          Validators.minLength(1),
          Validators.max(999999999),
          Validators.maxLength(9),
        ],
      ],
      max: [
        this.search.max,
        [
          Validators.required,
          Validators.min(0),
          Validators.minLength(1),
          Validators.max(999999999),
          Validators.maxLength(9),
        ],
      ],
      currency: [this.search.currency, [Validators.required]],
      period: [this.search.period],
    });
  }

  ngOnDestroy() {
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  public radiusChange(event: MatSliderChange) {
    if (event.value != null) {
      this.search.radius = event.value;
      console.log(this.search.radius);
    }
  }

  public find() {
    let search: Search = this.formGroup.getRawValue();
    search.latitude = Number(localStorage.getItem("latitude")!);
    search.longitude = Number(localStorage.getItem("longitude")!);
    if (this.search.type == AssetType.Product) {
      search.period = "once";
    }

    if (!search.latitude && !search.longitude) {
      this.alertService.error("ERROR_NOT_LOCATION");
    } else if (search.min == null && search.max == null) {
      this.alertService.error("ERROR_VALUES_OUT_OF_RANGE");
    } else {
      this.busy = true;
      this.transactionService.searchByTypeEqualQuote(search).subscribe(
        (assets: Array<Transaction>) => {
          this.transactionService.setTransactions(assets);
          this.searchService.setSearch(search);
          this.onSearch.emit();
        },
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
    }
  }
}
