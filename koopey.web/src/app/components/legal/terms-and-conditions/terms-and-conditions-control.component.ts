import {
  Component,
  EventEmitter,
  Input,
  Output,
  OnChanges,
  OnInit,
} from "@angular/core";
import { AlertService } from "../../../services/alert.service";
import { TranslateService } from "ng2-translate";
import "hammerjs";
import { MatRadioChange } from "@angular/material/radio";

@Component({
  selector: "terms-and-conditions-control-component",
  templateUrl: "../../views/terms-and-conditions-control.html",
})
export class TermsAndConditionsControlComponent implements OnInit {
  @Input() readOnly: boolean = false;
  @Input() agreeOrDisagree: boolean = false;
  @Output() updateTermsAndConditions: EventEmitter<boolean> = new EventEmitter<
    boolean
  >();

  constructor(
    private alertService: AlertService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {}

  private onChange(event: MatRadioChange) {
    if (event.value === "agree") {
      this.agreeOrDisagree = true;
      this.updateTermsAndConditions.emit(this.agreeOrDisagree);
    } else if (event.value === "disagree") {
      this.agreeOrDisagree = false;
      this.updateTermsAndConditions.emit(this.agreeOrDisagree);
    }
  }

  private isReadOnly() {
    return this.readOnly;
  }
}
