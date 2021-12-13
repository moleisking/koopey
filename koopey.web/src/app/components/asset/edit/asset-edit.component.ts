import { BaseComponent } from "../../base/base.component";
import { Classification } from "../../../models/classification";
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
import { AlertService } from "../../../services/alert.service";
import { AssetService } from "../../../services/asset.service";
import { UserService } from "../../../services/user.service";
import { Advert } from "../../../models/advert";
import { Environment } from "src/environments/environment";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { Tag } from "../../../models/tag";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";
import { MatRadioChange } from "@angular/material/radio";
import { OperationType } from "src/app/models/type/OperationType";
import { AssetType } from "src/app/models/type/AssetType";


import { DomSanitizer } from "@angular/platform-browser";
import { ToolbarService } from "src/app/services/toolbar.service";
import { ClassificationService } from "src/app/services/classification.service";
import { Transaction } from "src/app/models/transaction";
import { TransactionService } from "src/app/services/transaction.service";

@Component({
  selector: "asset-edit",
  styleUrls: ["asset-edit.css"],
  templateUrl: "asset-edit.html",
})
export class AssetEditComponent extends BaseComponent implements OnInit, OnDestroy {
  public asset: Asset = new Asset();
  private assetSubscription: Subscription = new Subscription();
  private classificationSubscription: Subscription = new Subscription();
  public formGroup!: FormGroup;
  private location: Location = new Location();
  private operationType: String = "";
  public wallet: Wallet = new Wallet();

  constructor(
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService,
    private assetService: AssetService,
    private classificationService: ClassificationService,
    private formBuilder: FormBuilder,
    public sanitizer: DomSanitizer,
    private toolbarService: ToolbarService,
    private transactionService: TransactionService,
    private userService: UserService,
    private router: Router
  ) {
    super(sanitizer)
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((parameter) => {
      if (parameter["type"]) {
        this.asset.type = parameter["type"];
      } else {
        this.asset.type = AssetType.Product
      }
    });

    this.assetService.getType().subscribe((type) => {
      this.operationType = type;
    });

    this.formGroup = this.formBuilder.group({
      firstImage: [
        this.asset.firstImage,
        [
          Validators.required,
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      secondImage: [
        this.asset.secondImage,
        [
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      thirdImage: [
        this.asset.thirdImage,
        [
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      fourthImage: [
        this.asset.fourthImage,
        [
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      currency: [this.asset.currency, [Validators.required]],
      name: [
        this.asset.name,
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(150),
        ],
      ],
      type: [
        this.asset.type,
        [Validators.required, Validators.minLength(7), Validators.maxLength(7)],
      ],
      description: [this.asset.description, [Validators.maxLength(150)]],
      height: [
        this.asset.height,
        [
          Validators.min(0),
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      length: [
        this.asset.length,
        [
          Validators.min(0),
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      manufactureDate: [
        this.asset.manufactureDate,
        [
          Validators.minLength(5),
          Validators.maxLength(10),
        ],
      ],
      quantity: [
        this.asset.quantity,
        [
          Validators.required,
          Validators.min(1),
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
          Validators.min(0),
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      width: [
        this.asset.width,
        [
          Validators.min(0),
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
    });
  }

  ngAfterContentInit() {
    console.log("ngAfterContentInit()");
  }

  ngAfterViewInit() {
    console.log("ngAfterViewInit()");
    this.userService.readMyUser().subscribe(
      (user: User) => {
        this.asset.seller = user;
        this.asset.sources.push(this.location);
      },
      (error: Error) => {
        this.alertService.error(<any>error);
      },
      () => { }
    );
  }

  ngOnDestroy() {
    if (this.assetSubscription) {
      this.assetSubscription.unsubscribe();
    }
    if (this.classificationSubscription) {
      this.classificationSubscription.unsubscribe();
    }
  }

  public onToggleProductOrService(event: MatRadioChange) {
    if (event.value === "product") {
      this.asset.type = "product";
    } else if (event.value === "service") {
      this.asset.type = "service";
      this.asset.quantity = 1; //NOTE: Quantity needs to be at least 1 to apprear in search results
    }
  }

  public handleAdvertUpdate(advert: Advert) {
    console.log("handleAdvertUpdate");
    this.asset.advert = advert;
  }

  public edit() {
    this.formGroup.patchValue(this.asset);
    //this.formGroup.controls["currency"].setValue("gbp");
  }

  public findInvalidControls() {
    const invalid = [];
    const controls = this.formGroup.controls;
    for (const name in controls) {
      if (controls[name].invalid) {
        invalid.push(name);
      }
    }
    return invalid;
  }

  private getAsset() {
    this.assetSubscription = this.assetService.getAsset().subscribe(
      (asset: Asset) => {
        this.asset = asset;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  public save() {
    console.log("edit()");
    console.log(this.findInvalidControls());
    let asset: Asset = this.formGroup.getRawValue();
    asset.tags = new Array<Tag>();
    console.log(asset);
    //NOTE: Location is set in the backend and the user is set during ngInit
    if (this.asset.quantity <= 0) {
      this.alertService.error("ERROR_NO_QUANTITY");
    } else if (!this.formGroup.dirty && !this.formGroup.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.asset.available = true;
      if (this.operationType === OperationType.Update) {
        this.updateAsset(asset);

      }
      else {
        this.createAsset(asset);
      }

    }
  }

  private createAsset(asset: Asset) {
    this.assetService.create(asset).subscribe(
      () => {
        this.createClassifications(asset)
        
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  private createClassification(classification: Classification) {
    this.classificationService.create(classification).subscribe(
      () => { },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  private createClassifications(asset: Asset) {
    asset.tags.forEach((tag: Tag) => {
      let classification: Classification = new Classification();
      classification.assetId = asset.id;
      classification.tagId = tag.id;
      this.createClassification(classification);
    });
    this.createTransaction(asset);
  }  

  private createTransaction(asset: Asset) {
    let transaction : Transaction = new Transaction();
    transaction.assetId = asset.id;
    transaction.sellerId =  this.getAuthenticationUserId();
    this.transactionService.create(transaction).subscribe(
      () => {    this.router.navigate(["/dashboard"]);           },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  private updateAsset(asset: Asset) {
    this.assetService.update(this.asset).subscribe(
      () => { },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => { }
    );
  }

  private updateClassification(classification: Classification) {
    this.classificationService.update(classification).subscribe(
      () => { },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

}
