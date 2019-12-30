//Angular, Material, Libraries
import { Component, Input } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { MaterialModule, MdIconModule, MdIconRegistry, MdInputModule } from "@angular/material"
import { Subscription } from 'rxjs/Subscription';
//Services
import { UserService } from "../services/user.service";
//Objects
import { Alert } from "../models/alert";
import { Bitcoin } from "../models/bitcoin";
import { Config } from "../config/settings";
import { Ethereum } from "../models/ethereum";
import { Location } from "../models/location";
import { Review } from "../models/review";
import { Tag } from "../models/tag";
import { Transaction } from "../models/transaction";
import { User } from "../models/user";
import { Wallet } from "../models/wallet";

@Component({
    selector: "user-control-component",
    templateUrl: "../../views/user-control.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class UserControlComponent {

    @Input() user: User = new User();
    @Input() textVisible: boolean = false;
    @Input() imageRound: boolean = false;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private sanitizer: DomSanitizer
    ) { }

    private hasAlias(): boolean {
        if (this.user && this.user.alias && Config.business_model_alias) {
            return true;
        } else {
            return false;
        }
    }

    private hasImage(): boolean {
        if (this.user && this.user.avatar && this.user.avatar != "") {
            return false;
        } else {
            return true;
        }
    }

    private hasName(): boolean {
        if (this.user && this.user.name && !Config.business_model_alias) {
            return true;
        } else {
            return false;
        }
    }    

    private isMyUser(): boolean {
        //window.location used to get id because this method is run during form load and not through subscription       
        if (window.location.href.substr(window.location.href.lastIndexOf('/') + 1) == localStorage.getItem("id")) {
            return true;
        } else {
            return false;
        }
    }

    private gotoUser(user: User) {
        this.router.navigate(["/user/read/one", user.id])
    }
}