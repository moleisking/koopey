import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { ChangeDetectionStrategy, Component, Inject, inject, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from "@angular/forms";
import { Location } from "../../../models/location";
import { LocationService } from "../../../services/location.service";
import { Router } from "@angular/router";
import { User } from "../../../models/user";
import { StorageService } from "@services/storage.service";
import { MatFormField, MatHint } from "@angular/material/form-field";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { GdprboxComponent } from "@components/common/gdpr/gdprbox.component";
import { MatInputModule } from "@angular/material/input";
import { ImageboxComponent } from "@components/common/image/imagebox.component";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [ GdprboxComponent , ImageboxComponent,  MatButtonModule, MatFormField, MatIconModule, MatInputModule, ReactiveFormsModule],
  selector: "register-component",
  standalone: true,
  styleUrls: ["register.css"],
  templateUrl: "register.html",
})
export class RegisterComponent implements OnInit {
  public formGroup!: FormGroup;
  private location!: Location;

  private alertService = inject(AlertService);
  private authenticationService = inject(AuthenticationService);
  private formBuilder = inject(FormBuilder);
  private locationService = inject(LocationService);
  private router = inject(Router);
  private store = inject(StorageService);
  
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
      user.language = this.store.getLanguage();
      user.altitude = this.location.altitude;
      user.latitude = this.location.latitude;
      user.longitude = this.location.longitude;
      user.timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;

      this.authenticationService.register(user).subscribe(
        () => { },
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
