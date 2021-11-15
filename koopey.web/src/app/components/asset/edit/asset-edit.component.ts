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
import { AssetService } from "../../../services/asset.service";
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
  public formGroup!: FormGroup;
  private location: Location = new Location();
  public asset: Asset = new Asset();
  private assetSubscription: Subscription = new Subscription();
  public wallet: Wallet = new Wallet();

  constructor(
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService,
    private assetService: AssetService,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((parameter) => {
      if (parameter["type"]) {
        this.asset.type = parameter["type"];
      }
    });

    this.formGroup = this.formBuilder.group({
      firstImage: [
        "",
        [
          Validators.required,
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      secondImage: [
        "",
        [
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      thirdImage: [
        "",
        [
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      fourthImage: [
        "",
        [
          Validators.minLength(100),
          Validators.pattern(new RegExp("(data:image/png;base64,)(.*)")),
        ],
      ],
      currency: [this.asset.currency, [Validators.required]],
      title: [
        this.asset.title,
        [
          Validators.required,
          Validators.minLength(5),
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
          Validators.required,
          Validators.min(1),
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      length: [
        this.asset.length,
        [
          Validators.required,
          Validators.min(1),
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      manufactureDate: [
        this.asset.manufactureDate,
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
          Validators.required,
          Validators.min(1),
          Validators.minLength(1),
          Validators.maxLength(10),
        ],
      ],
      width: [
        this.asset.width,
        [
          Validators.required,
          Validators.min(1),
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
      () => {}
    );
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

  public save() {
    console.log("edit()");
    console.log(this.findInvalidControls());
    let asset: Asset = this.formGroup.getRawValue();
    console.log(asset);
    //NOTE: Location is set in the backend and the user is set during ngInit
    if (this.asset.quantity <= 0) {
      this.alertService.error("ERROR_NO_QUANTITY");
    } else if (!this.formGroup.dirty && !this.formGroup.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.asset.available = true;
      /* this.assetService.create(this.asset).subscribe(
        () => {},
        (error: Error) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.router.navigate(["/dashboard"]);
        }
      );*/

      this.assetService.update(this.asset).subscribe(
        () => {
          this.alertService.success("SAVED");
        },
        (error: Error) => {
          this.alertService.error(<any>error);
        },
        () => {}
      );
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
}
