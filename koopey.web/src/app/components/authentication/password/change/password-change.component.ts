import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { AuthenticationService } from "../../../../services/authentication.service";
import { UserService } from "../../../../services/user.service";
import { AlertService } from "../../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { Config } from "../../../../config/settings";
import { User } from "../../../../models/user";

@Component({
  selector: "password-change-component",
  templateUrl: "password-change.html",
  styleUrls: ["password-change.css"],
})
export class PasswordChangeComponent implements OnInit {
  private form!: FormGroup;
  private authUser: User = <User>{};

  constructor(
    private authenticateService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService,
    private translateService: TranslateService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {} // Profile form messages will be here.

  ngOnInit() {
    /* this.form = this.formBuilder.group({
      oldPassword: [this.authUser.oldPassword, Validators.required],
      newPassword: [
        this.authUser.newPassword,
        [Validators.required, Validators.minLength(5)],
      ],
    });*/
  }

  public passwordChange() {
    /*if (!this.form.dirty && !this.form.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.authenticateService.passwordChange(this.authUser).subscribe(
        () => {
          this.alertService.info("PASSWORD_CHANGED");
        },
        (error: any) => {
          this.alertService.error(<any>error);
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
    }*/
  }
}
