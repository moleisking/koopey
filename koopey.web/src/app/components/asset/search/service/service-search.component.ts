import {
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Observable, Subscription } from "rxjs";
import { AlertService } from "../../../../services/alert.service";
import { AssetService } from "../../../../services/asset.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../../services/click.service";
import { SearchService } from "../../../../services/search.service";
import { TranslateService } from "@ngx-translate/core";
import { Asset } from "../../../../models/asset";
import { Environment } from "src/environments/environment";
import { Location } from "../../../../models/location";
import { Search } from "../../../../models/search";
import { Tag } from "../../../../models/tag";
import { User } from "../../../../models/user";
import { MatDialog } from "@angular/material/dialog";

@Component({
  selector: "service-search-component",
  templateUrl: "service-search.html",
  styleUrls: ["service-search.css"],
})
/*Note* Do not use fors as it blocks location controls*/
export class ServiceSearchComponent implements OnInit, OnDestroy {
  //Objects
  private clickSubscription: Subscription = new Subscription();
  private searchSubscription: Subscription = new Subscription();
  //  private location: Location = new Location();
  public search: Search = new Search();
  public assets: Array<Asset> = new Array<Asset>();

  //Booleans
  public busy: boolean = false;

  constructor(
    //  private formBuilder: FormBuilder,
    private alertService: AlertService,
    private clickService: ClickService,
    private sanitizer: DomSanitizer,
    public messageDialog: MatDialog,
    private router: Router,
    private searchService: SearchService,
    private translateService: TranslateService,
    private assetService: AssetService
  ) {}

  ngOnInit() {
    this.clickService.createInstance(
      ActionIcon.SEARCH,
      CurrentComponent.ServiceSearchComponent
    );
    this.clickSubscription = this.clickService
      .getServiceSearchClick()
      .subscribe(() => {
        this.findUsers();
      });
  }

  ngAfterContentInit() {}

  ngAfterViewInit() {
    this.search.type = "service";
    this.search.radius = Environment.Default.Radius;
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

  private hasCurrency(currency: string): boolean {
    return Environment.Transaction.Currencies.includes(currency);
  }

  private hasTransactions(): boolean {
    return Environment.Menu.Transactions;
  }

  public onUpdateAddress(location: Location) {
    console.log("updateAddress called");
    if (location) {
      console.log("updateAddress true:" + JSON.stringify(location));
      this.search.latitude = location.latitude;
      this.search.longitude = location.longitude;
      //this.location.latitude = location.latitude;
      //this.location.longitude = location.longitude;
      //this.myForm.patchValue({ address: location.address });
    } else {
      console.log("updateAddress false");
    }
  }

  public onUpdatePosition(location: Location) {
    console.log("updatedPosition");
    if (location) {
      console.log("updatedPosition true");
      this.search.latitude = location.latitude;
      this.search.longitude = location.longitude;
      //this.location.latitude = location.latitude;
      //this.location.longitude = location.longitude;
      // this.myForm.patchValue({ address: location.address });
      // console.log(JSON.stringify(this.location));
    } else {
      console.log("updatedPosition false");
    }
  }

  public handleTagUpdated(selectedTags: Array<Tag>) {
    this.search.tags = selectedTags;
  }

  private findUsers() {
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
