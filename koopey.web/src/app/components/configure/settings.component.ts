//Angular, Material, Libraries
import { Component, ElementRef, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { Router } from "@angular/router";
import {
    MaterialModule, MdAutocompleteModule, MdIconModule, MdIconRegistry,
    MdInputModule, MdTextareaAutosize, MdDialog, MdDialogRef, MdAutocompleteTrigger
} from "@angular/material"

import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../../services/alert.service";
import { AuthService } from "../../services/auth.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../../services/user.service";
//Components
import { ConfirmDialogComponent } from "../confirm/confirm-dialog.component";
//Objects
import { User } from "../../models/user";
import { Config } from "../../config/settings";


@Component({
    selector: 'settings-component',
    template: require("../../views/settings.html")
})

export class SettingsComponent {

    private LOG_HEADER: string = 'SettingsComponent';
    private authUser: User = new User();
    private authenticateSubscription: Subscription;
    private userSubscription: Subscription;

    constructor(
        private alertService: AlertService,
        private authenticateService: AuthService,
        public confirmDialog: MdDialog,
        private router: Router,
        private userService: UserService,
        private translateService: TranslateService
    ) { }

    ngOnInit() {
        this.userSubscription = this.userService.readMyUser().subscribe(
            (user) => { this.authUser = user; },
            (error) => { this.alertService.error(<any>error) },
            () => { console.log("getMyUser success"); }
        );
    }

    ngAfterContentInit() {

    }

    ngAfterViewInit() {

    }

    ngOnDestroy() {
        if (this.authenticateSubscription) {
            this.authenticateSubscription.unsubscribe();
        }
        if (this.userSubscription) {
            this.userSubscription.unsubscribe();
        }
    }

    public emailChangeRequest() {
        this.router.navigate(["/user/update/email/request"]);
    }

    public passwordChangeRequest() {
        this.router.navigate(["/user/update/password/request"]);
    }

    private refreshMyUser() {
        this.userService.readMyUser().subscribe(
            (user) => {
                localStorage.setItem("alias", user.alias);
                localStorage.setItem("authenticated", user.authenticated.toString());
                localStorage.setItem("currency", user.currency);
                localStorage.setItem("id", user.id);
                localStorage.setItem("name", user.name);
                localStorage.setItem("wallets", JSON.stringify(user.wallets));
                localStorage.setItem("location", JSON.stringify(user.location));
                console.log("localStorage.getItem(authenticated");
                console.log(localStorage.getItem("authenticated"));
            },
            (error) => { this.alertService.error(<any>error); },
            () => { }
        );
    }

    private forgottenActivationEmail() {
        this.authenticateSubscription = this.authenticateService.activateForgotten().subscribe(
            () => { },
            (error) => { this.alertService.error(<any>error); },
            () => { }
        );
    }

    private toggleTrack() {
        this.userService.updateTrack(this.authUser).subscribe(
            () => { },
            (error) => { this.alertService.error(<any>error); },
            () => { console.log("trackChanged updated") }
        );
    }

    /* private toggleAvailableLocation() {
         this.userService.updateAvailable(this.authUser).subscribe(
             () => { },
             (error) => { this.alertService.error(<any>error); },
             () => { console.log("availableChanged updated") }
         );
     }*/

    private toggleNotify() {
        this.userService.updateNotify(this.authUser).subscribe(
            () => { },
            (error) => { this.alertService.error(<any>error); },
            () => { }
        );
    }

    private openDeleteMyUserDialog() {
        console.log("openDeleteMyUserDialog()");
        let dialogRef = this.confirmDialog.open(ConfirmDialogComponent);
        dialogRef.afterClosed().subscribe(result => {
            if (result == true) {
                console.log("delete user");
                this.userService.delete(this.authUser).subscribe(
                    () => { this.router.navigate(["/login"]) },
                    (error) => { this.alertService.error(<any>error); },
                    () => { this.alertService.success("INFO_COMPLETE") }
                );
            } else if (result == false) {
                console.log("dont delete user");
            }
        });
    }
}