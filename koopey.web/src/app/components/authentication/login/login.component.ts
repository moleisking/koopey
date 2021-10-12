import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { AuthenticationService } from "../../../services/authentication.service";
import { UserService } from "../../../services/user.service";
import { TranslateService } from "@ngx-translate/core";
import { AlertService } from "../../../services/alert.service";
import { Alert } from "../../../models/alert";
import { User } from "../../../models/user";
import { Login } from "../../../models/login";
import { AuthToken } from "src/app/models/authentication/authToken";

@Component({
  selector: "login-component",
  templateUrl: "login.html",
  styleUrls: ["login.css"],
})
export class LoginComponent implements OnInit {
  public formGroup!: FormGroup;

  public login: Login = new Login();

  constructor(
    private authenticateService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      alias: [
        this.login.alias,
        [
          Validators.required,
          Validators.minLength(3),
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
    if (!this.formGroup.dirty && !this.formGroup.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.authenticateService.login(this.login).subscribe(
        (authToken: AuthToken) => {
          this.authenticateService.saveLocalAuthToken(authToken);
        },
        (error: Error) => {
          this.alertService.error("ERROR_AUTHENTICATION_FAILURE");
          console.log(error);
        },
        () => {
          this.getMyUser();
          this.router.navigate(["/dashboard"]);
        }
      );
    }
  }

  private getMyUser() {
    this.userService.readMyUser().subscribe(
      (user: User) => {
        this.authenticateService.setUser(user);
        this.authenticateService.saveLocalUser(user);
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  public register() {
    this.router.navigate(["/register"]);
  }

  public requestForgottenPassword() {
    this.router.navigate(["/user/update/password/forgotten/request"]);
  }
}
