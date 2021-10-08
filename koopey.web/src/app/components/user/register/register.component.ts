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
import { MatIconRegistry } from "@angular/material/icon";
import { User } from "../../../models/user";
import { AuthenticationService } from "src/app/services/authentication.service";
import { LocationService } from "src/app/services/location.service";

@Component({
  selector: "register-component",
  styleUrls: ["register.css"],
  templateUrl: "register.html",
})
export class RegisterComponent extends BaseComponent implements OnInit {
  private clickSubscription: Subscription = new Subscription();
  public formGroup!: FormGroup;

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    //private clickService: ClickService,
    private formBuilder: FormBuilder,
    private iconRegistry: MatIconRegistry,
    private router: Router,
    public sanitizer: DomSanitizer
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      alias: ["", [Validators.required, Validators.minLength(5)]],
      avatar: ["", [Validators.required, Validators.minLength(100)]],
      birthday: ["", Validators.required],
      description: ["", Validators.maxLength(150)],
      education: ["", Validators.maxLength(150)],
      email: [
        "",
        [
          Validators.required,
          Validators.email,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
      gdpr: ["", [Validators.required, Validators.pattern("true")]],
      mobile: [
        "",
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(20),
        ],
      ],
      name: [
        "",
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(100),
        ],
      ],
      password: [
        "",
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
    });
  }

  ngAfterContentInit() {
    /*  this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.RegisterComponent
    );
    this.clickSubscription = this.clickService
      .getUserCreateClick()
      .subscribe(() => {
        this.register();
      });*/
  }

  ngOnDestroy() {
    /*if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }*/
  }

  public register() {
    console.log("register()");
    if (!this.formGroup.dirty || !this.formGroup.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      let user: User = this.formGroup.getRawValue();

      user.language = this.getLanguage();
      user.timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;

      this.authenticationService.register(user).subscribe(
        (reply: String) => {
          this.authenticationService.saveLocalUser(user);
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
