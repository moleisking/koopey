import { Component, EventEmitter, Input, Output, OnInit } from "@angular/core";
import { AlertService } from "../../services/alert.service";
import { GdprService } from "../../services/gdpr.service";
import { TranslateService } from "@ngx-translate/core";
import { MatRadioChange } from "@angular/material/radio";

@Component({
  selector: "gdpr-component",
  styleUrls: ["gdpr.css"],
  templateUrl: "gdpr.html",
})
export class GdprComponent implements OnInit {
  @Input() readOnly: boolean = false;
  @Input() consent: boolean = false;
  @Output() updateGdpr: EventEmitter<boolean> = new EventEmitter<boolean>();
  public content: String = "";

  constructor(
    public alertService: AlertService,
    public gdprService: GdprService,
    public translateService: TranslateService
  ) {}

  ngOnInit() {
    this.getContent();
  }

  public getContent() {
    this.gdprService.readGdpr().subscribe(
      (content: String) => {
        this.content = content;
      },
      (error: Error) => {
        console.log(error);
      }
    );
  }

  public onChange(event: MatRadioChange) {
    if (event.value === "agree") {
      this.consent = true;
      this.updateGdpr.emit(true);
    } else if (event.value === "disagree") {
      this.consent = false;
      this.updateGdpr.emit(false);
    }
  }

  public isReadOnly() {
    return this.readOnly;
  }
}
