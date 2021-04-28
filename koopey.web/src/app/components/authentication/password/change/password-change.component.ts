import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { AuthenticationService } from "../../../../services/authentication.service";
import { UserService } from "../../../../services/user.service";
import { AlertService } from "../../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { Config } from "../../../../config/settings";
import { User } from "../../../../models/user";
import { Change } from "src/app/models/authentication/change";

@Component({
  selector: "password-change-component",
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
    private translateService: TranslateService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

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

  public passwordChange() {
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
