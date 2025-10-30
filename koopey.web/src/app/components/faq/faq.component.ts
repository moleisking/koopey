import { ChangeDetectionStrategy, Component } from "@angular/core";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "faq-component",
    standalone: false,
  templateUrl: "faq.html",
})
export class FAQComponent {}
