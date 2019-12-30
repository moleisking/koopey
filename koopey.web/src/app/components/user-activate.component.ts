//Angular, Material, Libraries
import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
//Services
import { AuthService } from "../services/auth.service";
import { UserService } from "../services/user.service";
import { AlertService } from "../services/alert.service";
import { TranslateService } from "ng2-translate";
//Objects
import { Config } from "../config/settings";
import { User } from "../models/user";

@Component({
    selector: "user-activate-component",
    templateUrl: "../../views/user-activate.html"
})

export class UserActivateComponent implements OnInit, OnDestroy {

    private user: User = new User();
    private userAuthenticated: boolean = false;

    constructor(
        private authenticateService: AuthService,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private translateService: TranslateService
    ) { }

    ngOnInit() {
        this.user.secret = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);
    }

    ngAfterContentInit() {
        this.authenticateService.activate(this.user).subscribe(
            (data) => { this.userAuthenticated = true; localStorage.setItem("authenticated", "true"); },
            (error) => { this.userAuthenticated = false; localStorage.setItem("authenticated", "false"); },
            () => { }
        );
    }

    ngOnDestroy() { }
}
