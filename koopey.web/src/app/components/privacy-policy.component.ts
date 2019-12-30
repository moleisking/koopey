import { Component, OnInit } from "@angular/core";
import { HomeService } from "../services/home.service";

@Component({
    selector: "privacy-policy-component",
    templateUrl: "../../views/privacy-policy.html"
})

export class PrivacyPolicyComponent implements OnInit {

    private message: string;

    constructor(private homeService: HomeService) {
        this.message = ""; // Here will come Privacy Policy Data Protection Text from backend.
    }

    getPrivacyPolicyDataProtection() {
      /*  this.homeService.privacyPolicyDataProtection().subscribe(
            text => this.message = text,
            error => this.message = <any>error
        );*/
    }

    ngOnInit() {
        this.getPrivacyPolicyDataProtection();
    }
}
