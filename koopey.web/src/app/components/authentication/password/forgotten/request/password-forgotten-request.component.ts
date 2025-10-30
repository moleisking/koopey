import { Component, OnInit, OnDestroy, ChangeDetectionStrategy } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { AuthenticationService } from "../../../../../services/authentication.service";
import { UserService } from "../../../../../services/user.service";
import { AlertService } from "../../../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { User } from "../../../../../models/user";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "password-forgotten-request-component",
  standalone: false,
  templateUrl: "password-forgotten-request.html",
  styleUrls: ["password-forgotten-request.css"],
})
export class PasswordForgottenRequestComponent implements OnInit, OnDestroy {
  public form!: FormGroup;
  public authUser: User = new User();

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
    this.form = this.formBuilder.group({
      email: [
        this.authUser.email,
        [
          Validators.required,
          Validators.email,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
    });
  }

  ngOnDestroy() {}

  public passwordForgottenRequest() {
    if (!this.form.dirty && !this.form.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.authenticateService
        .passwordForgottenRequest(this.authUser)
        .subscribe(
          () => {},
          (error: any) => {
            this.alertService.error(<any>error);
          },
          () => {
            this.router.navigate(["/login"]);
          }
        );
    }
  }
}
