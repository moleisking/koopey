import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { AuthenticationService } from "../../../../services/authentication.service";
import { UserService } from "../../../../services/user.service";
import { AlertService } from "../../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { Config } from "../../../../config/settings";
import { ChangePassword } from "../../../../models/authentication/changePassword";

@Component({
  selector: "password-forgotten-repl-component",
  templateUrl: "password-change-forgotten.html",
  styleUrls: ["password-change-forgotten.css"],
})
export class PasswordChangeForgottenComponent implements OnInit {
  private form!: FormGroup;
  private secret!: String;
  private changePassword: ChangePassword = new ChangePassword();

  constructor(
    private authenticateService: AuthenticationService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private alertService: AlertService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    /*   this.changePassword.secret = window.location.href.substr(
      window.location.href.lastIndexOf("/") + 1
    );*/
  }

  ngAfterContentInit() {
    this.form = this.formBuilder.group({
      newPassword: [
        //  this.user.newPassword,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
    });
  }

  ngOnDestroy() {}

  public passwordChangeForgotten() {
    if (!this.form.dirty && !this.form.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      /*this.authenticateService.passwordForgottenReply(this.user).subscribe(
        () => {
          this.alertService.info("PASSWORD_CHANGED");
        },
        (error) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.authenticateService.logout();
          this.router.navigate(["/login"]);
        }
      );*/
    }
  }
}
