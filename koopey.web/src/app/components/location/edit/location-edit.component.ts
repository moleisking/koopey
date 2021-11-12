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
import { User } from "../../../models/user";
import { MatDialog } from "@angular/material/dialog";
import { LocationService } from "src/app/services/location.service";
import { MatRadioChange } from "@angular/material/radio";
import { Transaction } from "src/app/models/transaction";
import { TransactionService } from "src/app/services/transaction.service";
import { BaseComponent } from "../../base/base.component";
import { DomSanitizer } from "@angular/platform-browser";
import { TransactionType } from "src/app/models/type/TransactionType";
import { OperationType } from "src/app/models/type/OperationType";

@Component({
  selector: "location-edit",
  styleUrls: ["location-edit.css"],
  templateUrl: "location-edit.html",
})
export class LocationEditComponent extends BaseComponent
  implements AfterContentInit, OnDestroy, OnInit {
  public formGroup!: FormGroup;
  private transaction: Transaction = new Transaction();
  private location: Location = new Location();
  private locationSubscription: Subscription = new Subscription();
  public addressVisible: Boolean = false;
  public positionVisible: Boolean = false;
  private operationType: String = "";

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
        [Validators.minLength(4), Validators.maxLength(8)],
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
      this.saveLocation(location);
    }
  }

  private saveLocation(location: Location) {
    if (this.operationType === OperationType.Create) {
      this.locationService.create(location).subscribe(
        (id: string) => {
          location.id = id;
        },
        (error: Error) => {
          this.alertService.error(error.message);
        },
        () => {
          this.createTransaction(location);
        }
      );
    } else if (this.operationType === OperationType.Create) {
      this.locationService.update(location).subscribe(
        () => {},
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
    }
  }

  private createTransaction(location: Location) {
    this.transaction.name = location.name;
    this.transaction.type = TransactionType.Template;
    this.transaction.sellerId = this.getAuthenticationUserId();
    this.transaction.source = location;
    this.transaction.sourceId = location.id;
    this.transactionService.create(this.transaction).subscribe(
      () => {
        this.router.navigate(["/location/list"]);
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }
}
