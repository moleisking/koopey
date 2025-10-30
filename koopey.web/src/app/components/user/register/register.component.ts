import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { BaseComponent } from "../../base/base.component";
import { ChangeDetectionStrategy, Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Location } from "../../../models/location";
import { LocationService } from "../../../services/location.service";
import { Router } from "@angular/router";
import { User } from "../../../models/user";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "register-component",
    standalone: false,
  styleUrls: ["register.css"],
  templateUrl: "register.html",
})
export class RegisterComponent extends BaseComponent implements OnInit {
  public formGroup!: FormGroup;
  private location!: Location;

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private locationService: LocationService,
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
    this.locationService.getPosition().subscribe((location: Location) => {
      this.location = location;
    });
  }

  public register() {
    if (!this.formGroup.dirty || !this.formGroup.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      let user: User = new User();
      user = this.formGroup.getRawValue();
      user.language = this.getLanguage();
      user.altitude = this.location.altitude;
      user.latitude = this.location.latitude;
      user.longitude = this.location.longitude;
      user.timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;

      this.authenticationService.register(user).subscribe(
        () => {},
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
