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
import { TransactionType } from "src/app/models/type/TransactionType";

@Component({
  selector: "asset-edit",
  styleUrls: ["asset-edit.css"],
  templateUrl: "asset-edit.html",
})
export class AssetEditComponent extends BaseComponent implements OnInit, OnDestroy {
  public transaction: Transaction = new Transaction();
  private transactionSubscription: Subscription = new Subscription();
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
    if (this.transaction.asset) {
      this.activatedRoute.queryParams.subscribe((parameter) => {
        if (parameter["type"] && this.transaction.asset) {
          this.transaction.asset.type = parameter["type"];
        } else if (this.transaction.asset) {
          this.transaction.asset.type = AssetType.Product
        }
      });

      this.assetService.getType().subscribe((type) => {
        this.operationType = type;
      });

      this.formGroup = this.formBuilder.group({
        firstImage: [
          this.transaction.asset.firstImage,
          [
            Validators.required,
            Validators.minLength(100),
            Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
          ],
        ],
        secondImage: [
          this.transaction.asset.secondImage,
          [
            Validators.minLength(100),
            Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
          ],
        ],
        thirdImage: [
          this.transaction.asset.thirdImage,
          [
            Validators.minLength(100),
            Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
          ],
        ],
        fourthImage: [
          this.transaction.asset.fourthImage,
          [
            Validators.minLength(100),
            Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
          ],
        ],
        currency: [this.transaction.asset.currency, [Validators.required]],
        name: [
          this.transaction.asset.name,
          [
            Validators.required,
            Validators.minLength(1),
            Validators.maxLength(150),
          ],
        ],
        type: [
          this.transaction.asset.type,
          [Validators.required, Validators.minLength(7), Validators.maxLength(7)],
        ],
        description: [this.transaction.asset.description, [Validators.maxLength(150)]],
        height: [
          this.transaction.asset.height,
          [
            Validators.min(0),
            Validators.minLength(1),
            Validators.maxLength(10),
          ],
        ],
        length: [
          this.transaction.asset.length,
          [
            Validators.min(0),
            Validators.minLength(1),
            Validators.maxLength(10),
          ],
        ],
        manufactureDate: [
          this.transaction.asset.manufactureDate,
          [
            Validators.minLength(5),
            Validators.maxLength(10),
          ],
        ],
        quantity: [
          this.transaction.asset.quantity,
          [
            Validators.required,
            Validators.min(1),
            Validators.minLength(1),
            Validators.maxLength(10),
          ],
        ],
        tags: [this.transaction.asset.tags, [Validators.required]],
        value: [
          this.transaction.asset.value,
          [
            Validators.required,
            Validators.minLength(1),
            Validators.maxLength(10),
          ],
        ],
        weight: [
          this.transaction.asset.weight,
          [
            Validators.min(0),
            Validators.minLength(1),
            Validators.maxLength(10),
          ],
        ],
        width: [
          this.transaction.asset.width,
          [
            Validators.min(0),
            Validators.minLength(1),
            Validators.maxLength(10),
          ],
        ],
      });
    }
  }

  ngAfterContentInit() {
    console.log("ngAfterContentInit()");
  }

  ngAfterViewInit() {
    console.log("ngAfterViewInit()");
    this.userService.readMyUser().subscribe(
      (user: User) => {
        this.transaction.seller = user;
        this.transaction.source= this.location;
      },
      (error: Error) => {
        this.alertService.error(<any>error);
      },
      () => { }
    );
  }

  ngOnDestroy() {
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
    if (this.classificationSubscription) {
      this.classificationSubscription.unsubscribe();
    }
  }

  public onToggleProductOrService(event: MatRadioChange) {
    if (event.value === "article") {
      this.transaction.asset!.type = "article";
    } else if (event.value === "product") {
      this.transaction.asset!.type = "product";
    } else if (event.value === "service") {
      this.transaction.asset!.type = "service";
      this.transaction.asset!.quantity = 1; //NOTE: Quantity needs to be at least 1 to apprear in search results
    }
  }

  public handleAdvertUpdate(advert: Advert) {
    console.log("handleAdvertUpdate");
    this.transaction.advert = advert;
  }

  public edit() {
    this.formGroup.patchValue(this.transaction);
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
    this.transactionSubscription = this.transactionService.getTransaction().subscribe(
      (transaction: Transaction) => {
        this.transaction = transaction;
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
    //NOTE: Location is set in the backend and the user is set during ngInit
    if (this.transaction.asset!.quantity <= 0) {
      this.alertService.error("ERROR_NO_QUANTITY");
    } else if (!this.formGroup.dirty && !this.formGroup.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.transaction.asset!.available = true;
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
      (id: String) => {
        asset.id = id.toString();
        this.createClassifications(asset)
        this.createTransaction(asset);
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  private createClassification(classification: Classification) {
    console.log(classification);
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
  }

  private createTransaction(asset: Asset) {
    let transaction: Transaction = new Transaction();
    transaction.sourceId = this.getLocationId();
    transaction.type = TransactionType.Quote;
    transaction.name = asset.name;
    transaction.assetId = asset.id;
    transaction.sellerId = this.getAuthenticationUserId();
    transaction.quantity = asset.quantity;
    transaction.value = asset.value;
    transaction.total = asset.value * asset.quantity;
    this.transactionService.create(transaction).subscribe(
      () => {   /* this.router.navigate(["/dashboard"]*);   */ },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  private updateAsset(asset: Asset) {
    this.assetService.update(this.transaction.asset!).subscribe(
      () => { },
      (error: Error) => {
        this.alertService.error(error.message);
      }
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
