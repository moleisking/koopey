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
//import { ImageEditComponent } from "../../image/edit/image-edit.component";
import { AlertService } from "../../../services/alert.service";
//import { AddressTextboxComponent } from "../../common/address-textbox/address-textbox.component";

import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { TranslateService } from "@ngx-translate/core";
import { UserService } from "../../../services/user.service";
import { Advert } from "../../../models/advert";
import { Environment } from "src/environments/environment";
import { Image } from "../../../models/image";
import { Location } from "../../../models/location";
import { MatDialog } from "@angular/material/dialog";
import { MatIconRegistry } from "@angular/material/icon";
import { MatDatepickerIntl } from "@angular/material/datepicker";
import { User } from "../../../models/user";

@Component({
  selector: "register-component",
  templateUrl: "register.html",
  styleUrls: ["register.css"],
})
export class RegisterComponent implements OnInit {
  private clickSubscription: Subscription = new Subscription();
  public form!: FormGroup;
  public user: User = new User();
  public birthday: number = 0;
  private location: Location = new Location();

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    private datePickerService: MatDatepickerIntl,
    private formBuilder: FormBuilder,
    private iconRegistry: MatIconRegistry,
    //public imageUploadDialog: MatDialog,
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
      alias: [this.user.alias, [Validators.required, Validators.minLength(5)]],
      birthday: [this.birthday, Validators.required],
      description: [this.user.description, Validators.maxLength(150)],
      education: [this.user.description, Validators.maxLength(150)],
      email: [
        this.user.email,
        [
          Validators.required,
          Validators.email,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
      mobile: [
        this.user.mobile,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(20),
        ],
      ],
      name: [
        this.user.name,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(100),
        ],
      ],
      password: [
        this.user.password,
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
      CurrentComponent.RegisterComponent
    );
    this.clickSubscription = this.clickService
      .getUserCreateClick()
      .subscribe(() => {
        this.register();
      });
  }

  ngAfterViewInit() {}

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
  }

  public handleAliasUpdate(event: any) {
    if (this.user && this.user.alias) {
      this.user.alias = this.user.alias.toLowerCase();
    }
  }

  public handleAddressUpdate(location: Location) {
    if (location) {
      location.type = "abode";
      //  location.position = Location.convertToPosition(location.longitude, location.latitude);
      this.user.location = location;
      this.form.patchValue({ address: location.address });
      //  this.updateRegisterLocation(location.latitude, location.longitude, location.address);
      console.log("handleAddressUpdate");
      console.log(location);
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
      this.user.birthday = utcDate.getTime();
    }
  }

  public handleEmailUpdate(event: any) {
    if (this.user && this.user.email) {
      this.user.email = this.user.email.toLowerCase();
    }
  }

  public handleNameUpdate(event: any) {
    if (this.user && this.user.name) {
      this.user.name = this.user.name.toLowerCase();
    }
  }

  public handlePositionUpdate(location: Location) {
    console.log("handlePositionUpdate");
    console.log(location);
    if (location) {
      location.type = "abode";
      this.user.location = location;
      // this.updateCurrentLocation(location.latitude, location.longitude, location.address);
    }
  }

  public changeGdpr(consent: boolean) {
    console.log("changeGdpr");
    this.user.gdpr = consent;
  }

  public openImageDialog(source: number) {
    /*  let dialogRef = this.imageUploadDialog.open(ImageDialogComponent);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.user.avatar = Image.shrinkImage(result.uri, 256, 256);
      }
    });*/
  }

  public register() {
    if (!this.form.dirty || !this.form.valid || !User.isCreate(this.user)) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else if (!this.user.gdpr) {
      this.alertService.error("ERROR_NOT_LEGAL");
    } else {
      this.userService.create(this.user).subscribe(
        () => {
          //Note* Router only works here on create
          if (Environment.type != "production") {
            this.user;
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
