import {
  ActionIcon,
  ClickService,
  CurrentComponent,
} from "../../../services/click.service";
import { AlertService } from "../../../services/alert.service";
import { BaseComponent } from "../../base/base.component";
import { Component, OnInit, OnDestroy } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Location, LocationType } from "../../../models/location";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { UserService } from "../../../services/user.service";
import { MatIconRegistry } from "@angular/material/icon";
import { User } from "../../../models/user";

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
      gdpr: [this.user.gdpr, [Validators.required]],
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
    this.location = this.getPosition();
    this.location.type = LocationType.Abode;
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
      this.user.locations.push(this.location);
      this.userService.create(this.user).subscribe(
        () => {
          this.user;
        },
        (error: Error) => {
          this.alertService.error(error.message);
        },
        () => {
          this.router.navigate(["/login"]);
        }
      );
    }
  }
}
