import { Component, OnInit } from "@angular/core";
import { HomeService } from "../../services/home.service";

@Component({
    selector: "about-component",
    templateUrl: "../../views/about.html",
})

export class AboutComponent implements OnInit {

    private message: string;

    constructor(private web: HomeService) {
        this.message = "Here will come About Text from backend.";
    }

    ngOnInit() {
        this.getAboutText();
    }

    getAboutText() {
        this.web.about().subscribe(
            text => this.message = text,
            error => this.message = <any>error
        );
    }


}
