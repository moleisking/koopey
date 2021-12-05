import {
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
  ChangeDetectorRef,
  AfterContentInit,
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
import { LocationService } from "src/app/services/location.service";
import { MatRadioChange } from "@angular/material/radio";
import { Transaction } from "src/app/models/transaction";
import { TransactionService } from "src/app/services/transaction.service";
import { BaseComponent } from "../../base/base.component";
import { DomSanitizer } from "@angular/platform-browser";
import { TransactionType } from "src/app/models/type/TransactionType";
import { OperationType } from "src/app/models/type/OperationType";
import { LocationType } from "src/app/models/type/LocationType";
import { User } from "src/app/models/user";
import { AuthenticationService } from "src/app/services/authentication.service";

@Component({
  selector: "location-edit",
  styleUrls: ["location-edit.css"],
  templateUrl: "location-edit.html",
})
export class LocationEditComponent extends BaseComponent
  implements AfterContentInit, OnDestroy, OnInit {
  public addressVisible: Boolean = false;
  public formGroup!: FormGroup;
  private location: Location = new Location();
  private locationSubscription: Subscription = new Subscription();
  private operationType: String = "";
  public positionVisible: Boolean = false;
  private user : User = new User();

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
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
    this.locationService.getType().subscribe((type) => {
      this.operationType = type;
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
        [Validators.minLength(5), Validators.maxLength(10), Validators.required],
      ],
    });
  }

  ngAfterContentInit() {
    if (this.operationType === OperationType.Update) {
      this.locationService.getLocation().subscribe((location) => {
        this.location = location;
      });
    }
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
      }
    );
  }  

  public save() {
    let location: Location = this.formGroup.getRawValue();
    if (!this.formGroup.dirty && !this.formGroup.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      console.log("location:component:save")
      this.saveLocation(location);
    }
  }

  private saveLocation(location: Location) {
    if (this.operationType === OperationType.Update) {
      console.log("location:component:edit")
      this.locationService.update(location).subscribe(
        () => { },
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
    } else {
      console.log("location:component:location:create")
      this.locationService.create(location).subscribe(
        (id: String) => {
          location.id = id.toString();
        },
        (error: Error) => {
          this.alertService.error(error.message);
        },
        () => {
          console.log("location:component:transaction:creating")
          this.createTransaction(location);
        }
      );
    }
  }

  private createTransaction(location: Location) {
    let transaction: Transaction = new Transaction();
    transaction.name = location.name;
    transaction.type = TransactionType.Template;
    transaction.seller     = this.authenticationService.getMyUserFromStorage();
    transaction.sellerId = this.getAuthenticationUserId();
    transaction.source = location;
    transaction.sourceId = location.id;
    console.log("location:component:transaction:create")
    this.transactionService.create(transaction).subscribe(
      () => {
        console.log("location:component:transaction:create")
        this.router.navigate(["/location/list"]);
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
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
