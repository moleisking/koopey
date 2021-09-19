import {
  Component,
  EventEmitter,
  Input,
  Output,
  OnChanges,
  OnInit,
} from "@angular/core";
import { AlertService } from "../../services/alert.service";
import { AuthenticationService } from "../../services/authentication.service";
import { GdprService } from "../../services/gdpr.service";
import { TranslateService } from "@ngx-translate/core";
import { MatRadioChange } from "@angular/material/radio";

@Component({
  selector: "gdpr-component",
  templateUrl: "gdpr.html",
})
export class GdprComponent implements OnInit {
  @Input() readOnly: boolean = false;
  @Input() consent: boolean = false;
  @Output() updateGdpr: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(
    public alertService: AlertService,
    public gdprService: GdprService,
    public translateService: TranslateService
  ) {}

  ngOnInit() {}

  public getContent(): String {
    return this.gdprService.readGdpr();
  }

  public onChange(event: MatRadioChange) {
    if (event.value === "agree") {
      this.consent = true;
      this.updateGdpr.emit(this.consent);
    } else if (event.value === "disagree") {
      this.consent = false;
      this.updateGdpr.emit(this.consent);
    }
  }

  public isReadOnly() {
    return this.readOnly;
  }
}
