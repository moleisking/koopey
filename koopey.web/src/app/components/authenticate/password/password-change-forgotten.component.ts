//Angular, Material, Libraries
import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router, ActivatedRoute } from "@angular/router";
//Services
import { AuthService } from "../services/auth.service";
import { UserService } from "../services/user.service";
import { AlertService } from "../services/alert.service";
import { TranslateService } from "ng2-translate";
//Objects
import { Config } from "../config/settings";
import { User } from "../models/user";

@Component({
    selector: "password-forgotten-repl-component",
    templateUrl: "../../views/password-change-forgotten.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class PasswordChangeForgottenComponent implements OnInit {

    private form: FormGroup;
    private secret: String;
    private user: User = new User();

    constructor(
        private authenticateService: AuthService,
        private route: ActivatedRoute,
        private router: Router,
        private formBuilder: FormBuilder,
        private userService: UserService,
        private alertService: AlertService,
        private translateService: TranslateService
    ) { }

    ngOnInit() {
        this.user.secret = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);
    }

    ngAfterContentInit() {
        this.form = this.formBuilder.group({
            newPassword: [this.user.newPassword, [Validators.required, Validators.minLength(5), Validators.maxLength(150)]]
        });
    }

    ngOnDestroy() { }

    public passwordChangeForgotten() {
        if (!this.form.dirty && !this.form.valid) {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        } else {
            this.authenticateService.passwordForgottenReply(this.user).subscribe(
                () => { this.alertService.info("PASSWORD_CHANGED"); },
                (error) => { this.alertService.error(<any>error) },
                () => {
                    this.authenticateService.logout();
                    this.router.navigate(["/login"]);
                }
            );
        }
    }
}
