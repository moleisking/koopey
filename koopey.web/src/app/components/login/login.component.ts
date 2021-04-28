import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { AuthenticationService } from "../../services/authentication.service";
import { UserService } from "../../services/user.service";
import { TranslateService } from "@ngx-translate/core";
import { AlertService } from "../../services/alert.service";
import { Alert } from "../../models/alert";
import { User } from "../../models/user";
import { Login } from "../../models/login";
import { AuthToken } from "src/app/models/authentication/authToken";

@Component({
  selector: "login-component",
  templateUrl: "login.html",
  styleUrls: ["login.css"],
})
export class LoginComponent implements OnInit {
  public form!: FormGroup;
  private alert: Alert = new Alert();
  public login: Login = new Login();
  private user: User = new User();

  private authToken: AuthToken = new AuthToken();

  constructor(
    private authenticateService: AuthenticationService,
    private alertService: AlertService,
    private formBuilder: FormBuilder,
    private router: Router,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      email: [
        this.login.email,
        [
          Validators.email,
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
      password: [
        this.login.password,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(100),
        ],
      ],
    });
  }

  public authenticateUser() {
    if (!this.form.dirty && !this.form.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.authenticateService.login(this.login).subscribe(
        (authToken: AuthToken) => {
          this.authToken = authToken;
        },
        (error: Error) => {
          this.alertService.error("ERROR_AUTHENTICATION_FAILURE");
          console.log(error);
        },
        () => {
          if (!User.isEmpty(this.user)) {
            console.log("LOGIN");
            console.log(this.user);
            this.router.navigate(["/dashboard"]);
          }
        }
      );
    }
  }

  public register() {
    this.router.navigate(["/user/create"]);
  }

  public requestForgottenPassword() {
    this.router.navigate(["/user/update/password/forgotten/request"]);
  }
}
