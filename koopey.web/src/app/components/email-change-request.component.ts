//Angular, Material, Libraries
import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
//Services
import { AuthService } from "../services/auth.service";
import { UserService } from "../services/user.service";
import { AlertService } from "../services/alert.service";
import { TranslateService } from "ng2-translate";
//Objects
import { Config } from "../config/settings";
import { User } from "../models/user";

@Component({
    selector: "email-change-request-component",
    templateUrl: "../../views/email-change-request.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class EmailChangeRequestComponent implements OnInit {

    private form: FormGroup;
    private authenticateUser: User = <User>{};

    constructor(
        private alertService: AlertService,
        private authenticateService: AuthService,      
        private formBuilder: FormBuilder,
        private router: Router
    ) { } 

    ngOnInit() {
        this.form = this.formBuilder.group({
            oldEmail: [this.authenticateUser.oldEmail, [Validators.required, Validators.email, Validators.minLength(5), Validators.maxLength(150)]],
            newEmail: [this.authenticateUser.newEmail, [Validators.required, Validators.email, Validators.minLength(5), Validators.maxLength(150)]],
            password: [this.authenticateUser.password, [Validators.required, Validators.minLength(5), Validators.maxLength(150)]]
        });
    }


    public emailChange() {
        if (!this.form.dirty && !this.form.valid) {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        } else {
            this.authenticateService.emailChangeRequest(this.authenticateUser).subscribe(
                () => { },
                (error) => { this.alertService.error(<any>error) },
                () => { this.router.navigate(["/settings"]); });
        }
    }
}
