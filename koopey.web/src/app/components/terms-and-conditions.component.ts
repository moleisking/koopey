//Angular, Material, Libraries
import { Component, OnInit } from "@angular/core";
import {
    MdRadioChange
} from "@angular/material"
//Services
import { AlertService } from "../services/alert.service";
import { AuthService } from "../services/auth.service";
import { TranslateService } from "ng2-translate";
import 'hammerjs';

@Component({
    selector: "terms-and-conditions-component",
    templateUrl: "../../views/terms-and-conditions.html"
})

// Note* Data items in JSON format as a structure that can build documents dynamically    
export class TermsAndConditionsComponent implements OnInit {

    private LOG_HEADER: string = 'TERMS_AND_CONDITIONS';
    private currentLanguage: any;
    private content: String;

    constructor(
        private alertService: AlertService,        
        private translateService: TranslateService    ) { }

    ngOnInit() {  }
}