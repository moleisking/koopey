import {
  Component,
  ElementRef,
  OnInit,
  OnDestroy,
  ViewChild,
} from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { ImageDialogComponent } from "../../image/dialog/image-dialog.component";
import { AlertService } from "../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { TranslateService } from "@ngx-translate/core";
import { WalletService } from "../../../services/wallet.service";
import { UserService } from "../../../services/user.service";
import { Advert } from "../../../models/advert";
import { Environment } from "src/environments/environment";
import { Image } from "../../../models/image";
import { Location } from "../../../models/location";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";
import { MatDialog } from "@angular/material/dialog";
import { MatIconRegistry } from "@angular/material/icon";
import { MatDatepickerIntl } from "@angular/material/datepicker";

@Component({
  selector: "user-create-component",
  templateUrl: "user-create.html",
  styleUrls: ["user-create.css"],
})
export class UserCreateComponent implements OnInit {
  private clickSubscription: Subscription = new Subscription();
  public form!: FormGroup;
  public authUser: User = new User();
  public birthday: number = 0;
  private IMAGE_SIZE: number = 512;
  private IMAGE_COUNT: number = 1;
  private location: Location = new Location();
  private wallet: Wallet = new Wallet();

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    private datePickerService: MatDatepickerIntl,
    private formBuilder: FormBuilder,
    private iconRegistry: MatIconRegistry,
    public imageUploadDialog: MatDialog,
    private router: Router,
    private translateService: TranslateService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      address: [
        this.location.address,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
      alias: [
        this.authUser.alias,
        [Validators.required, Validators.minLength(5)],
      ],
      birthday: [this.birthday, Validators.required],
      description: [this.authUser.description, Validators.maxLength(150)],
      education: [this.authUser.description, Validators.maxLength(150)],
      email: [
        this.authUser.email,
        [
          Validators.required,
          Validators.email,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
      mobile: [
        this.authUser.mobile,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(20),
        ],
      ],
      name: [
        this.authUser.name,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(100),
        ],
      ],
      password: [
        this.authUser.password,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
    });
  }

  ngAfterContentInit() {
    this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.UserCreateComponent
    );
    this.clickSubscription = this.clickService
      .getUserCreateClick()
      .subscribe(() => {
        this.create();
      });
  }

  ngAfterViewInit() {
    //Create wallet
    this.wallet.name = this.authUser.id;
    this.wallet.currency = "tok";
    this.wallet.type = "primary";
    this.wallet.balance = Environment.Default.Credit;
    this.authUser.wallets.push(this.wallet);
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
  }

  public handleAliasUpdate(event: any) {
    if (this.authUser && this.authUser.alias) {
      this.authUser.alias = this.authUser.alias.toLowerCase();
    }
  }

  public handleAddressUpdate(location: Location) {
    if (location) {
      location.type = "abode";
      //  location.position = Location.convertToPosition(location.longitude, location.latitude);
      this.authUser.location = location;
      this.form.patchValue({ address: location.address });
      //  this.updateRegisterLocation(location.latitude, location.longitude, location.address);
    } else {
      this.location.address = "";
    }
  }

  public handleBirthdayUpdate(event: any) {
    var utcDate = new Date(event.target.value);
    if (
      utcDate.getFullYear() > 1900 &&
      utcDate.getMonth() >= 0 &&
      utcDate.getDate() > 0
    ) {
      this.authUser.birthday = utcDate.getTime();
    }
  }

  public handleEmailUpdate(event: any) {
    if (this.authUser && this.authUser.email) {
      this.authUser.email = this.authUser.email.toLowerCase();
    }
  }

  private handleNameUpdate(event: any) {
    if (this.authUser && this.authUser.name) {
      this.authUser.name = this.authUser.name.toLowerCase();
    }
  }

  public handlePositionUpdate(location: Location) {
    console.log("handlePositionUpdate");
    if (location) {
      location.type = "abode";
      this.authUser.location = location;
      // this.updateCurrentLocation(location.latitude, location.longitude, location.address);
    }
  }

  public handleTermsAndConditionsUpdate(agreeOrDisagree: boolean) {
    this.authUser.gdpr = agreeOrDisagree;
  }

  private openImageDialog(source: number) {
    let dialogRef = this.imageUploadDialog.open(ImageDialogComponent);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.authUser.avatar = Image.shrinkImage(result.uri, 256, 256);
      }
    });
  }

  public create() {
    if (!this.form.dirty || !this.form.valid || !User.isCreate(this.authUser)) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else if (!this.authUser.gdpr) {
      this.alertService.error("ERROR_NOT_LEGAL");
    } else {
      this.userService.create(this.authUser).subscribe(
        () => {
          //Note* Router only works here on create
          if (Environment.type != "production") {
            this.authUser;
          }
        },
        (error) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.router.navigate(["/login"]);
        }
      );
    }
  }
}
