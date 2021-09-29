import { AlertService } from "../../../services/alert.service";
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
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";

import { UserService } from "../../../services/user.service";
import { Environment } from "src/environments/environment";
import { Image } from "../../../models/image";
import { Location } from "../../../models/location";
import { MatIconRegistry } from "@angular/material/icon";
import { MatDatepickerIntl } from "@angular/material/datepicker";
import { User } from "../../../models/user";
import { BaseComponent } from "../../base/base.component";
import { DomSanitizer } from "@angular/platform-browser";

@Component({
  selector: "register-component",
  styleUrls: ["register.css"],
  templateUrl: "register.html",
})
export class RegisterComponent extends BaseComponent implements OnInit {
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
    private router: Router,
    public sanitizer: DomSanitizer,
    private userService: UserService
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
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

  public handleAliasUpdate() {
    if (this.user && this.user.alias) {
      this.user.alias = this.user.alias.toLowerCase();
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

  public handleEmailUpdate() {
    if (this.user && this.user.email) {
      this.user.email = this.user.email.toLowerCase();
    }
  }

  public handleGdprUpdate(consent: boolean) {
    console.log("changeGdpr");
    this.user.gdpr = consent;
  }

  public register() {
    if (!this.form.dirty || !this.form.valid || !User.isCreate(this.user)) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else if (!this.user.gdpr) {
      this.alertService.error("ERROR_NOT_LEGAL");
    } else {
      this.user.locations.push(this.getPosition());
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
