import { ChangeDetectionStrategy, Component } from "@angular/core";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "gdpr-component",
    standalone: false,
  templateUrl: "gdpr.html",
})
export class GDPRComponent {}
