import { Component, OnInit } from "@angular/core";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { TranslateService } from "ng2-translate";

@Component({
  selector: "terms-and-conditions-component",
  templateUrl: "terms-and-conditions.html",
})

// Note* Data items in JSON format as a structure that can build documents dynamically
export class TermsAndConditionsComponent implements OnInit {
  private currentLanguage: any;
  private content: String = "";

  constructor(
    private alertService: AlertService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {}
}
