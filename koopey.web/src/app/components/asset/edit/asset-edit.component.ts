import {
  Component,
  ElementRef,
  OnInit,
  OnDestroy,
  ViewChild,
} from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
//import { ImageDialogComponent } from "../../image/edit/image-edit.component";
import { AlertService } from "../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { AssetService } from "../../../services/asset.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { Advert } from "../../../models/advert";
import { Environment } from "src/environments/environment";
import { Image } from "../../../models/image";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { Tag } from "../../../models/tag";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";
import { MatDialog } from "@angular/material/dialog";
import { MatRadioChange } from "@angular/material/radio";

@Component({
  selector: "asset-edit",
  styleUrls: ["asset-edit.css"],
  templateUrl: "asset-edit.html",
})
export class AssetEditComponent implements OnInit, OnDestroy {
  // private clickSubscription: Subscription = new Subscription();
  public formGroup!: FormGroup;
  private location: Location = new Location();
  private locations: Array<Location> = new Array<Location>();
  public asset: Asset = new Asset();
  public wallet: Wallet = new Wallet();
  public manufactureDate: number = 0;
  public screenWidth: number = 0;
  public tangible: boolean = true; //NOTE: Default asset.type is product

  constructor(
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService,
    private formBuilder: FormBuilder,
    public imageUploadDialog: MatDialog,
    private assetService: AssetService,
    private userService: UserService,
    private router: Router,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((parameter) => {
      if (parameter["type"]) {
        this.asset.type = parameter["type"];
        this.tangible = Asset.isProduct(this.asset);
      }
    });

    this.formGroup = this.formBuilder.group({
      currency: [this.asset.currency],
      title: [
        this.asset.title,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
      description: [this.asset.description, [Validators.maxLength(150)]],
      height: [
        this.asset.height,
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      length: [
        this.asset.length,
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      manufactureDate: [
        this.manufactureDate,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(10),
        ],
      ],
      quantity: [
        this.asset.quantity,
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      tags: [this.asset.tags, [Validators.required]],
      value: [
        this.asset.value,
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      weight: [
        this.asset.weight,
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      width: [
        this.asset.width,
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
    });
  }

  ngAfterContentInit() {
    /*  this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.AssetCreateComponent
    );
    this.clickSubscription = this.clickService
      .getAssetCreateClick()
      .subscribe(() => {
        this.create();
      });*/
  }

  ngAfterViewInit() {
    this.userService.readMyUser().subscribe(
      (user: User) => {
        this.asset.user = user;
        this.asset.locations.push(this.location);
      },
      (error: Error) => {
        this.alertService.error(<any>error);
      },
      () => {}
    );
    if (Environment.type != "production") {
      console.log(this.asset);
    }
  }

  ngOnDestroy() {
    /* if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }*/
  }

  public onToggleProductOrService(event: MatRadioChange) {
    if (event.value === "product") {
      this.asset.type = "product";
      this.tangible = true;
    } else if (event.value === "service") {
      //NOTE: Quantity needs to be at least 1 to apprear in search results
      this.asset.type = "service";
      this.asset.quantity = 1;
      this.tangible = false;
    }
  }

  public handleAdvertUpdate(advert: Advert) {
    console.log("handleAdvertUpdate");
    this.asset.advert = advert;
  }

  public handleManufactureDateUpdate(event: any) {
    var utcDate = new Date(event.target.value);
    if (
      utcDate.getFullYear() > 1900 &&
      utcDate.getMonth() >= 0 &&
      utcDate.getDate() > 0
    ) {
      this.asset.manufactureDate = utcDate.getTime();
    }
  }

  public handleTagUpdated(selectedTags: Array<Tag>) {
    console.log("handleTagUpdated");

    this.asset.tags = selectedTags;
    this.formGroup.patchValue({ tags: selectedTags });
  }

  public create() {
    //NOTE: Location is set in the backend and the user is set during ngInit
    if (!this.asset.value && this.asset.value <= 0) {
      this.alertService.error("ERROR_VALUE_REQUIRED");
      /*  } else if (!this.asset.tags && this.asset.tags.length() != 0) {
      this.alertService.error("ERROR_TAG_REQUIRED");*/
    } else if (this.asset.quantity <= 0) {
      this.alertService.error("ERROR_NO_QUANTITY");
    } else if (!this.formGroup.dirty && !this.formGroup.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.asset.available = true;
      this.assetService.create(this.asset).subscribe(
        () => {},
        (error: Error) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.router.navigate(["/dashboard"]);
        }
      );

      /*this.assetService.update(this.asset).subscribe(
        () => {
          this.alertService.success("SAVED");
        },
        (error) => {
          this.alertService.error(<any>error);
        },
        () => { });*/
    }
  }
}
