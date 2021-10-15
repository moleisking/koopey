import { Component, EventEmitter, Input, Output } from "@angular/core";
import { ControlValueAccessor, FormControl, NgControl } from "@angular/forms";
import { MatSelectChange } from "@angular/material/select";

@Component({
  selector: "currencybox",
  styleUrls: ["currencybox.css"],
  templateUrl: "currencybox.html",
})
export class CurrencyboxComponent implements ControlValueAccessor {
  @Input() currency: String = "eur";
  @Output() optionChange: EventEmitter<String> = new EventEmitter<String>();
  public formControl = new FormControl("");
  private onChange = (option: String) => {};
  private onTouched = Function;

  constructor(public ngControl: NgControl) {
    ngControl.valueAccessor = this;
  }

  public onOptionChange(event: MatSelectChange) {
    this.currency = event.value;
    this.onChange(event.value);
    this.onTouched();
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
      this.currency = value;
    }
  }
}
