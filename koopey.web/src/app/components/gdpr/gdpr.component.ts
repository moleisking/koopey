import { ChangeDetectionStrategy, Component } from "@angular/core";
import { GdprboxComponent } from "@components/common/gdpr/gdprbox.component";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports:[GdprboxComponent],
  selector: "gdpr-component",
  standalone: true,
  templateUrl: "gdpr.html",
})
export class GDPRComponent { }
