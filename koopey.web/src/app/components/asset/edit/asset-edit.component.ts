import { Classification } from "../../../models/classification";
import {
  Component,
  ElementRef,
  OnInit,
  OnDestroy,
  ViewChild,
  ChangeDetectionStrategy,
  inject,
} from "@angular/core";
import { FormGroup, FormBuilder, Validators, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AssetService } from "../../../services/asset.service";
import { UserService } from "../../../services/user.service";
import { Advert } from "../../../models/advert";
import { Environment } from "../../../../environments/environment";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { Tag } from "../../../models/tag";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";
import { MatRadioChange, MatRadioModule } from "@angular/material/radio";
import { OperationType } from "../../../models/type/OperationType";
import { AssetType } from "../../../models/type/AssetType";
import { DomSanitizer } from "@angular/platform-browser";
import { ToolbarService } from "../../../services/toolbar.service";
import { ClassificationService } from "../../../services/classification.service";
import { Transaction } from "../../../models/transaction";
import { TransactionService } from "../../../services/transaction.service";
import { TransactionType } from "../../../models/type/TransactionType";
import { DimensionPipe } from "@pipes/dimension.pipe";
import { WeightPipe } from "@pipes/weight.pipe";
import { NgIf } from "@angular/common";
import { MatIconModule } from "@angular/material/icon";
import { MatFormFieldModule } from "@angular/material/form-field";
import { AdvertboxComponent } from "@components/common/advert/advertbox.component";
import { MatExpansionModule } from "@angular/material/expansion";
import { TagFieldComponent } from "@components/common/tag-field/tag-field.component";
import { CurrencyFieldComponent } from "@components/common/currency-field/currency-field.component";
import { ImageboxComponent } from "@components/common/image/imagebox.component";
import { MatTabsModule } from "@angular/material/tabs";
import { StorageService } from "@services/storage.service";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [AdvertboxComponent, CurrencyFieldComponent, DimensionPipe, FormsModule, ImageboxComponent, MatExpansionModule, MatFormFieldModule, MatIconModule, MatRadioModule, MatTabsModule,
    ReactiveFormsModule, TagFieldComponent, WeightPipe],
  providers: [AlertService],
  selector: "asset-edit",
  standalone: true,
  styleUrls: ["asset-edit.css"],
  templateUrl: "asset-edit.html",
})
export class AssetEditComponent implements OnInit, OnDestroy {
  public transaction: Transaction = new Transaction();
  private transactionSubscription: Subscription = new Subscription();
  private classificationSubscription: Subscription = new Subscription();
  public formGroup!: FormGroup;
  private location: Location = new Location();
  private operationType: String = "";
  public wallet: Wallet = new Wallet();
  public x: number = 0;
  private alertService = inject(AlertService);

  private activatedRoute = inject(ActivatedRoute);
  private assetService = inject(AssetService);
  private classificationService = inject(ClassificationService);
  private formBuilder = inject(FormBuilder);
  public sanitizer = inject(DomSanitizer);
  private toolbarService = inject(ToolbarService);
  private transactionService = inject(TransactionService);
  private userService = inject(UserService);
  private router = inject(Router);
    private store = inject(StorageService);

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((parameters) => {
      if (parameters["operation"] === OperationType.Create) {
        this.transaction = new Transaction();
        this.transaction.asset = new Asset();
        if (parameters["type"]) {
          this.transaction.asset.type = parameters["type"];
        } else {
          this.transaction.asset.type = AssetType.Product
        }
      } else {
        this.getTransaction();
      }
    });


    this.formGroup = this.formBuilder.group({
      firstImage: [
        this.transaction.asset?.firstImage,
        [
          Validators.required,
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      secondImage: [
        this.transaction.asset?.secondImage,
        [
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      thirdImage: [
        this.transaction.asset?.thirdImage,
        [
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      fourthImage: [
        this.transaction.asset?.fourthImage,
        [
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      currency: [this.transaction.asset?.currency, [Validators.required]],
      name: [
        this.transaction.asset?.name,
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(150),
        ],
      ],
      type: [
        this.transaction.asset?.type,
        [Validators.required, Validators.minLength(7), Validators.maxLength(7)],
      ],
      description: [this.transaction.asset?.description, [Validators.maxLength(150)]],
      height: [
        this.transaction.asset?.height,
        [
          Validators.min(0),
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      length: [
        this.transaction.asset?.length,
        [
          Validators.min(0),
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      manufactureDate: [
        this.transaction.asset?.manufactureDate,
        [
          Validators.minLength(5),
          Validators.maxLength(10),
        ],
      ],
      quantity: [
        this.transaction.asset?.quantity,
        [
          Validators.required,
          Validators.min(1),
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      tags: [this.transaction.asset?.tags, [Validators.required]],
      value: [
        this.transaction.asset?.value,
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      weight: [
        this.transaction.asset?.weight,
        [
          Validators.min(0),
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      width: [
        this.transaction.asset?.width,
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
        this.transaction.seller = user;
        this.transaction.source = this.location;
      },
      (error: Error) => {
        this.alertService.error(<any>error);
      }
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
    transaction.sourceId = this.store.getLocationId();
    transaction.type = TransactionType.Quote;
    transaction.name = asset.name;
    transaction.assetId = asset.id;
    transaction.sellerId = this.store.getAuthenticationUserId();
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

  public patch() {
    this.formGroup.controls["currency"].setValue(this.transaction?.currency);
    this.formGroup.controls["description"].setValue(this.transaction.description);
    this.formGroup.controls["firstImage"].setValue(this.transaction.asset?.firstImage);
    this.formGroup.controls["secondImage"].setValue(this.transaction.asset?.secondImage);
    this.formGroup.controls["thirdImage"].setValue(this.transaction.asset?.thirdImage);
    this.formGroup.controls["fourthImage"].setValue(this.transaction.asset?.fourthImage);
    this.formGroup.controls["manufactureDate"].setValue(this.transaction.asset?.manufactureDate);
    this.formGroup.controls["name"].setValue(this.transaction.name);
    this.formGroup.controls["height"].setValue(this.transaction.asset?.height);
    this.formGroup.controls["length"].setValue(this.transaction.asset?.length);
    this.formGroup.controls["quantity"].setValue(this.transaction.asset?.quantity);
    this.formGroup.controls["tags"].setValue(this.transaction?.asset?.tags);
    this.formGroup.controls["value"].setValue(this.transaction.asset?.value);
    this.formGroup.controls["width"].setValue(this.transaction.asset?.width);
    this.formGroup.controls["weight"].setValue(this.transaction.asset?.weight);
  }

  public isArticle() {
    this.transaction && this.transaction.asset && this.transaction.asset.type && this.transaction.asset.type === 'article' ? true : false;
  }

  public isProduct() {
    this.transaction && this.transaction.asset && this.transaction.asset.type && this.transaction.asset.type === 'product' ? true : false;
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

  private getTransaction() {
    this.activatedRoute.params.subscribe((parameters) => {
      if (parameters["id"]) {
        this.transactionSubscription = this.transactionService.read(parameters["id"], true).subscribe(
          (transaction: Transaction) => {
            console.log("read from db")
            console.log(transaction)
            this.transaction = transaction;
            this.patch();
          },
          (error: Error) => {
            this.alertService.error(error.message);
          }
        );
      } else {
        this.transactionSubscription = this.transactionService.getTransaction().subscribe(
          (transaction: Transaction) => {
            console.log("read from service")
            console.log(transaction)
            this.transaction = transaction;
            this.patch();
          },
          (error: Error) => {
            this.alertService.error(error.message);
          }
        );
      }
    });
  }

  public handleAdvertUpdate(advert: Advert) {
    console.log("handleAdvertUpdate");
    this.transaction.advert = advert;
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
