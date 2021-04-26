import { Component, OnInit } from "@angular/core";
import { TranslateService } from "ng2-translate";

@Component({
  selector: "faq-component",
  templateUrl: "faq.html",
})
export class FAQComponent implements OnInit {
  constructor(private translateService: TranslateService) {}

  ngOnInit() {}
}
