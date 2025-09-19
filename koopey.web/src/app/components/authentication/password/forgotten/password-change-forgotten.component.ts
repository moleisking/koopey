import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
import { AuthenticationService } from "../../../../services/authentication.service";
import { UserService } from "../../../../services/user.service";
import { AlertService } from "../../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { Change } from "../../../../models/authentication/change";

@Component({
  selector: "password-forgotten-reply-component",
      standalone: false,
  templateUrl: "password-change-forgotten.html",
  styleUrls: ["password-change-forgotten.css"],
})
export class PasswordChangeForgottenComponent implements OnInit {
  public form!: FormGroup;
  private secret!: String;
  public changePassword: Change = new Change();

  constructor(
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    this.secret = window.location.href.substr(
      window.location.href.lastIndexOf("/") + 1
    );
  }

  ngAfterContentInit() {
    this.form = this.formBuilder.group({
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

  public passwordChangeForgotten() {
    if (!this.form.dirty && !this.form.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.authenticationService
        .passwordForgottenReply(this.changePassword)
        .subscribe(
          () => {
            this.alertService.info("PASSWORD_CHANGED");
          },
          (error: Error) => {
            this.alertService.error(error.message);
          },
          () => {
            this.authenticationService.logout();
            this.router.navigate(["/login"]);
          }
        );
    }
  }
}
