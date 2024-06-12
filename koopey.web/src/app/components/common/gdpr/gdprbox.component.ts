import { Component, EventEmitter, Input, Output, OnInit } from "@angular/core";
import { ControlValueAccessor, FormControl, NgControl } from "@angular/forms";
import { GdprService } from "../../../services/gdpr.service";
import { MatRadioChange } from "@angular/material/radio";

@Component({
  selector: "gdprbox",
  styleUrls: ["gdprbox.css"],
  templateUrl: "gdprbox.html",
})
export class GdprboxComponent implements ControlValueAccessor, OnInit {
  @Input() answer: String = "false";
  @Input() showOptions: boolean = false;
  @Output() optionChange: EventEmitter<String> = new EventEmitter<String>();
  public context: String = "";
  public gdprFormControl = new FormControl("");
  private onTouched = Function;
  private onChange = (option: String) => {};

  constructor(public gdprService: GdprService, public ngControl: NgControl) {
    ngControl.valueAccessor = this;
  }

  ngOnInit() {
    this.getContext();
  }

  private getContext() {
    this.gdprService.readGdpr().subscribe(
      (context: String) => {
        this.context = context;
      },
      (error: Error) => {
        console.log(error);
      }
    );
  }

  public onOptionChange(event: MatRadioChange) {
    if (this.showOptions) {
      this.answer = event.value;
      this.onChange(event.value);
      this.onTouched();
      this.optionChange.emit(event.value);
    }
  }

  registerOnChange(fn: any) {
    if (this.showOptions) {
      this.onChange = fn;
    }
  }

  registerOnTouched(fn: any) {
    if (!this.showOptions) {
      this.onTouched = fn;
    }
  }

  writeValue(value: string) {
    if (this.showOptions && value) {
      this.answer = value;
    }
  }
}
