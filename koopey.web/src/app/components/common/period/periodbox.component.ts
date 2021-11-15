import { Component, EventEmitter, Input, Output } from "@angular/core";
import { ControlValueAccessor, FormControl, NgControl } from "@angular/forms";
import { Environment } from "src/environments/environment";
import { MatSelectChange } from "@angular/material/select";

@Component({
  selector: "periodbox",
  styleUrls: ["periodbox.css"],
  templateUrl: "periodbox.html",
})
export class PeriodboxComponent implements ControlValueAccessor {
  @Input() period: String = Environment.Default.Period;
  @Output() optionChange: EventEmitter<String> = new EventEmitter<String>();
  public formControl: FormControl = new FormControl(
    Environment.Default.Period
  );
  private onChange = (option: String) => {};
  private onTouched = Function;

  constructor(public ngControl: NgControl) {
    ngControl.valueAccessor = this;
    this.formControl = new FormControl(this.period);
  }

  public onOptionChange(event: MatSelectChange) {
    this.period = event.value;
    this.onChange(event.value);
    this.onTouched(event.value);
    this.optionChange.emit(event.value);
  }

  registerOnChange(fn: any) {
    this.onChange = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouched = fn;
  }

  writeValue(value: any) {
    if (value) {
      this.period = value;
    }
  }
}
