//Angular, Material, Libraries
import { Component, ElementRef, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { FormGroup, FormBuilder, FormControl, Validators } from "@angular/forms";
import {
    MaterialModule, MdIconModule, MdIconRegistry, MdInputModule
} from "@angular/material"
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Observable, Subscription } from "rxjs/Rx";
//Services
import { AlertService } from "../../../services/alert.service";
import { ClickService, CurrentComponent, ActionIcon } from "../../../services/click.service";
import { UserService } from "../../../services/user.service";
import { TranslateService } from "ng2-translate";
//Objects
import { Config } from "../../../config/settings";
import { Location } from "../../../models/location";
import { Search } from "../../../models/search";
import { Tag } from "../../../models/tag";
import { User } from "../../../models/user";

@Component({
    selector: "search-member-component",
    templateUrl: "../../views/search-member.html",
    styleUrls: ["../../styles/app-root.css"]
})
/*Note* Do not use fors as it blocks location controls*/
export class SearchMembersComponent implements OnInit, OnDestroy {
    //Controls
    private clickSubscription: Subscription;
    private form: FormGroup;
    //Objects 
    // private location: Location = new Location();
    private search: Search = new Search();
    private users: Array<User>;
    //Strings
    private LOG_HEADER: string = "SearchMemberComponent"
    //Booleans 
    private hasGPS: boolean = false;
    private searching: boolean = false;

    constructor(
        private alertService: AlertService,
        private clickService: ClickService,
        private sanitizer: DomSanitizer,
        private formBuilder: FormBuilder,
        private router: Router,
        private translateService: TranslateService,
        private userService: UserService
    ) { }

    ngOnInit() {
        this.form = this.formBuilder.group({
            alias: [this.search.alias, [Validators.minLength(5)]],
            name: [this.search.name, [Validators.minLength(5)]]
        });
    }

    ngAfterContentInit() {
        this.clickService.createInstance(ActionIcon.SEARCH, CurrentComponent.SearchMemberComponent);
        this.clickSubscription = this.clickService.getSearchMemberClick().subscribe(() => {
            this.findUsers();
        });
    }

    ngAfterViewInit() {
        //Used to calculate distance
        //var locations: Array<Location> = JSON.parse(localStorage.getItem("locations"));
        var location: Location = JSON.parse(localStorage.getItem("location"));
        console.log(location);

        this.search.type = "service";
        if (location) {
            this.search.latitude = location.latitude;
            this.search.longitude = location.longitude;
            console.log(this.search.latitude);
            console.log(this.search.longitude);
        }


    }

    ngOnDestroy() {
        if (this.clickSubscription) {
            this.clickService.destroyInstance();
            this.clickSubscription.unsubscribe();
        }
    }

    private handleAliasUpdate(event: any) {
        if (this.search && this.search.alias) {
            this.search.alias = this.search.alias.toLowerCase();
        }
    }

    private handleNameUpdate(event: any) {
        if (this.search && this.search.name) {
            this.search.name = this.search.name.toLowerCase();
        }
    }

    private isAliasVisible() {
        return Config.business_model_alias;
    }

    private isNameVisible() {
        return Config.business_model_name;
    }

    private findUsers() {
        console.log("findUsers()")
        if (!this.search.alias && !this.search.name) {
            this.alertService.error("ERROR_NOT_LOCATION")
        } else if (this.search.alias || this.search.name) {
            //Set progress icon
            this.searching = true;
            this.userService.readUsers(this.search).subscribe(
                (users) => {
                    this.users = users;
                    this.userService.setUsers(this.users);
                    console.log(users);
                },
                error => { this.alertService.error(<any>error) },
                () => {
                    this.router.navigate(["/user/read/list"])
                }
            );
        }
    }
}
