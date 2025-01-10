import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { AuthenticationToken } from "../../../models/authentication/authenticationToken";
import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { UserService } from "../../../services/user.service";
import { User } from "../../../models/user";
import { Login } from "../../../models/login";
import { TagService } from "../../../services/tag.service";
import { Tag } from "../../../models/tag";
import { TranslateModule } from "@ngx-translate/core";
import { MatInputModule } from "@angular/material/input";
import { MatIconModule } from "@angular/material/icon";

@Component({
  selector: "login-component",
  templateUrl: "login.html",
  styleUrls: ["login.css"],
  imports: [ FormsModule, MatIconModule, MatInputModule,ReactiveFormsModule,RouterModule,TranslateModule],
  standalone: true,
  providers :[]
})
export class LoginComponent implements OnInit {
  public formGroup!: FormGroup;

  public login: Login = new Login();

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    private formBuilder: FormBuilder,
    private router: Router,
    private tagService: TagService,
    private userService: UserService
  ) {}
  /*ngOnDestroy(): void {
    //throw new Error("Method not implemented.");
  }*/

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      alias: [
        this.login.alias,
        [
          Validators.pattern('[a-zA-Z0-9]*'),
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
        (authToken: AuthenticationToken) => {
          this.authenticateService.saveLocalAuthToken(authToken);
          this.getMyUser();
        },
        (error: Error) => {
          this.alertService.error("ERROR_AUTHENTICATION_FAILURE");
          console.log(error);
        },
        () => {
          this.getTags();
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

  private getTags() {
    this.tagService.readTags().subscribe(
      (tags: Array<Tag>) => {
        localStorage.setItem("tags", JSON.stringify(tags));
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
