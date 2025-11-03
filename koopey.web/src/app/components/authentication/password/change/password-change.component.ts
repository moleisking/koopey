import { ChangeDetectionStrategy, Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { AuthenticationService } from "../../../../services/authentication.service";
import { UserService } from "../../../../services/user.service";
import { AlertService } from "../../../../services/alert.service";
import { User } from "../../../../models/user";
import { Change } from "../../../../models/authentication/change";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatFormFieldModule } from "@angular/material/form-field";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [FormsModule, MatButtonModule, MatFormFieldModule,MatIconModule, MatInputModule, ReactiveFormsModule],
  selector: "password-change-component",
  standalone: true,
  templateUrl: "password-change.html",
  styleUrls: ["password-change.css"],
})
export class PasswordChangeComponent implements OnInit {
  public form!: FormGroup;
  public authUser: User = <User>{};
  public changePassword: Change = new Change();

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      oldPassword: [this.changePassword.old, Validators.required],
      newPassword: [
        this.changePassword.new,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
    });
  }

  public update() {
    if (!this.form.dirty && !this.form.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.authenticationService.passwordChange(this.changePassword).subscribe(
        () => {
          this.alertService.info("PASSWORD_CHANGED");
        },
        (error: Error) => {
          this.alertService.error(error.message);
        },
        () => {
          this.alertService.success("INFO_COMPLETE");
          localStorage.removeItem("token");
          localStorage.removeItem("name");
          localStorage.removeItem("id");
          localStorage.removeItem("language");
          this.router.navigate(["/login"]);
        }
      );
    }
  }
}
