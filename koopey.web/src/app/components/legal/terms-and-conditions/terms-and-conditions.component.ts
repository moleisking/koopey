import { Component, OnInit } from "@angular/core";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { TranslateService } from "@ngx-translate/core";

@Component({
  selector: "terms-and-conditions-component",
  templateUrl: "terms-and-conditions.html",
})
export class TermsAndConditionsComponent implements OnInit {
  private currentLanguage: any;
  private content: String = "";

  constructor(
    private alertService: AlertService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {}
}
