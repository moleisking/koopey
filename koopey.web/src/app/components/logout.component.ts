import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { BrowserModule } from "@angular/platform-browser";//for ng-if
import { CommonModule } from '@angular/common';

@Component({
    selector: "logout-component",
    templateUrl: "../../views/login.html"
})

export class LogoutComponent implements OnInit {

    constructor(
        private router: Router
    ) { }

    ngOnInit() { }

    deleteCurrentUser() {
        localStorage.removeItem('token');
        localStorage.removeItem('name');
        localStorage.removeItem('id');
        localStorage.removeItem('language');
        localStorage.removeItem("longitude");
        localStorage.removeItem("latitude");
    }

    login() {
        this.router.navigate(["/login"]);
    }

    logout() {
        this.deleteCurrentUser();
        this.router.navigate(["/login"]);
    }

    isEmpty() {
        if (!localStorage.getItem('name')) {
            return true;
        }
        else {
            return false;
        }
    }

}
