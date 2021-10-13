import { Component, EventEmitter, Input, Output } from "@angular/core";
import { ControlValueAccessor, FormControl, NgControl } from "@angular/forms";

@Component({
  selector: "currencybox",
  styleUrls: ["currencybox.css"],
  templateUrl: "currencybox.html",
})
export class CurrencyboxComponent implements ControlValueAccessor {
  @Input() currency: String = "eur";
  @Output() updateCurrency: EventEmitter<String> = new EventEmitter<String>();
  public formControl = new FormControl("");
  private onChange = (option: String) => {};
  private onTouched = Function;

  constructor(public ngControl: NgControl) {
    ngControl.valueAccessor = this;
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
