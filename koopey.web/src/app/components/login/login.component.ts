//Angular, Material, Libraries
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
//Services
import { AuthService } from "../../services/auth.service";
import { UserService } from "../../services/user.service";
import { TranslateService } from "ng2-translate";
import { AlertService } from "../../services/alert.service";
//Objects
import { Alert } from "../../models/alert";
import { User } from "../../models/user";

@Component({
    selector: "login-component",
    templateUrl: "../../views/login.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class LoginComponent implements OnInit {

    private static LOG_HEADER: string = 'LOGIN';
    private form: FormGroup;
    private alert: Alert = new Alert();
    private user: User = new User();

    constructor(
        private authenticateService: AuthService,
        private alertService: AlertService,
        private formBuilder: FormBuilder,
        private router: Router,
        private translateService: TranslateService
    ) { }

    ngOnInit() {
        this.form = this.formBuilder.group({
            email: [this.user.email, [Validators.email, Validators.required, Validators.minLength(5), Validators.maxLength(150)]],
            password: [this.user.password, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]]
        });
    }

    private login() {
        if (!this.form.dirty && !this.form.valid) {
            this.alertService.error("ERROR_FORM_NOT_VALID")
        } else {
            this.authenticateService.login(this.user).subscribe(
                (user) => { this.user = user; },
                (error) => { this.alertService.error("ERROR_AUTHENTICATION_FAILURE"); console.log(error); },
                () => {
                    if (!User.isEmpty(this.user)) {
                        console.log('LOGIN');
                        console.log(this.user);
                        this.router.navigate(["/dashboard"])
                    }
                }
            );
        }
    }

    private register() {
        this.router.navigate(["/user/create"]);
    }

    private requestForgottenPassword() {
        this.router.navigate(["/user/update/password/forgotten/request"]);
    }
}
