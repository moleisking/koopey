import {
  Component,
  ElementRef,
  OnInit,
  OnDestroy,
  ViewChild,
  ChangeDetectorRef,
} from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormControl,
} from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { UserService } from "../../../services/user.service";
import { Location } from "../../../models/location";
import { User } from "../../../models/user";
import { MatDialog } from "@angular/material/dialog";
import { LocationService } from "src/app/services/location.service";
import { MatRadioChange } from "@angular/material/radio";
import { Transaction } from "src/app/models/transaction";
import { TransactionService } from "src/app/services/transaction.service";
import { BaseComponent } from "../../base/base.component";
import { DomSanitizer } from "@angular/platform-browser";
import { TransactionType } from "src/app/models/type/TransactionType";

@Component({
  selector: "location-edit",
  styleUrls: ["location-edit.css"],
  templateUrl: "location-edit.html",
})
export class LocationEditComponent extends BaseComponent
  implements OnInit, OnDestroy {
  public formGroup!: FormGroup;
  private transaction: Transaction = new Transaction();
  private location: Location = new Location();
  private locationSubscription: Subscription = new Subscription();
  public addressVisible: Boolean = false;
  public positionVisible: Boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private alertService: AlertService,
    private locationService: LocationService,
    private transactionService: TransactionService,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    public sanitizer: DomSanitizer
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((parameter) => {
      if (parameter["type"]) {
        this.location.type = parameter["type"];
      }
    });

    this.formGroup = this.formBuilder.group({
      address: [],
      position: [],
      description: [
        this.location.description,
        [Validators.required, Validators.maxLength(150)],
      ],
      latitude: [
        this.location.latitude,
        [
          Validators.required,
          Validators.max(90),
          Validators.maxLength(10),
          Validators.min(-90),
          Validators.minLength(1),
        ],
      ],
      longitude: [
        this.location.longitude,
        [
          Validators.required,
          Validators.min(-180),
          Validators.minLength(1),
          Validators.max(180),
          Validators.maxLength(11),
        ],
      ],
      name: [
        this.location.name,
        [
          Validators.required,
          Validators.maxLength(100),
          Validators.minLength(3),
        ],
      ],
      type: [
        this.location.type,
        [Validators.minLength(4), Validators.maxLength(8)],
      ],
    });
  }

  ngOnDestroy() {
    if (this.locationSubscription) {
      this.locationSubscription.unsubscribe();
    }
  }

  public onToggleInput(event: MatRadioChange) {
    if (event.value === "address") {
      this.addressVisible = true;
      this.positionVisible = false;
    } else if (event.value === "manuel") {
      this.addressVisible = false;
      this.positionVisible = false;
    } else if (event.value === "position") {
      this.addressVisible = false;
      this.positionVisible = true;
    }
  }

  public onAddress(location: Location) {
    this.formGroup.patchValue({
      description: location.description,
      latitude: location.latitude,
      longitude: location.longitude,
    });
  }

  public onPosition(location: Location) {
    console.log(location);
    this.formGroup.patchValue({
      description: "na",
      latitude: location.latitude,
      longitude: location.longitude,
    });
  }

  public edit() {
    this.formGroup.patchValue(this.location);
    //this.formGroup.controls["currency"].setValue("gbp");
  }

  private getLocation() {
    this.locationSubscription = this.locationService.getLocation().subscribe(
      (location: Location) => {
        this.location = location;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {}
    );
  }

  public save() {
    console.log("edit()");
    console.log(this.findInvalidControls());
    let location: Location = this.formGroup.getRawValue();
    console.log(location);

    if (!this.formGroup.dirty && !this.formGroup.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.updateLocation(location);
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

  private updateLocation(location: Location) {
    this.locationService.update(location).subscribe(
      () => {},
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {
        this.updateTransaction(location);
      }
    );
  }

  private updateTransaction(location: Location) {
    this.transaction.name = location.name;
    this.transaction.type = TransactionType.Template;
    this.transaction.seller = this.getUserIdOnly();
    this.transaction.source = this.location;
    this.transactionService.update(this.transaction).subscribe(
      () => {
        this.alertService.success("SAVED");
      },
      (error: Error) => {
        console.log(this.transaction);
        this.alertService.error(error.message);
      },
      () => {
        this.router.navigate(["/location/read/my/list"]);
      }
    );
  }
}
