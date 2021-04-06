import { Component, OnInit } from "@angular/core";
import { HomeService } from "../../services/home.service";
import { TranslateService } from "ng2-translate";
import { AuthService } from "../../services/auth.service";

@Component({
    selector: "home-component",
    templateUrl: "../../views/home.html",
})

export class HomeComponent implements OnInit {

    private language: string;

    constructor(private homeService: HomeService,
        private translateService: TranslateService,
        private authenticateService: AuthService) { }

    ngOnInit() {
        this.language = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);
        if (this.language == "cn") {
            this.changeLanguage("cn");
        } else if (this.language == "en") {
            this.changeLanguage("en");
        } else if (this.language == "es") {
            this.changeLanguage("es");
        } else if (this.language == "de") {
            this.changeLanguage("de");
        } else if (this.language == "fr") {
            this.changeLanguage("fr");
        } else if (this.language == "pt") {
            this.changeLanguage("pt");
        }
    }

    private changeLanguage(lang: string) {
        console.log("changeLanguage(" + lang + ") called");
        this.translateService.use(lang);
        this.authenticateService.setLocalLanguage(lang);
    }
}