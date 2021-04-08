//Angular, Material, Libraries
import { Component, OnInit, OnDestroy } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
//Services
import { AuthService } from "../../../services/auth.service";
import { UserService } from "../../../services/user.service";
import { AlertService } from "../../../services/alert.service";
import { TranslateService } from "ng2-translate";
//Objects
import { Config } from "../../../config/settings";
import { User } from "../../../models/user";

@Component({
    selector: "password-change-forgotten-request-component",
    templateUrl: "../../views/password-forgotten-request.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class PasswordChangeForgottenRequestComponent implements OnInit, OnDestroy {

    private form: FormGroup | undefined;
    private authUser: User = new User();

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
        this.form = this.formBuilder.group({
            email: [this.authUser.email, [Validators.required, Validators.email, Validators.minLength(5), Validators.maxLength(150)]]
        });
    }

    ngOnDestroy() { }


    public passwordForgottenRequest() {
        if (this.form == undefined || !this.form.dirty && !this.form.valid) {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        } else {
            this.authenticateService.passwordForgottenRequest(this.authUser).subscribe(
                () => { },
                (error : any) => { this.alertService.error(<any>error) },
                () => { this.router.navigate(["/login"]) });
        }
    }
}
