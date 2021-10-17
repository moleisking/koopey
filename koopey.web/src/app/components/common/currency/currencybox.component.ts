import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import { ControlValueAccessor, FormControl, NgControl } from "@angular/forms";
import { Environment } from "src/environments/environment";
import { MatSelectChange } from "@angular/material/select";

@Component({
  selector: "currencybox",
  styleUrls: ["currencybox.css"],
  templateUrl: "currencybox.html",
})
export class CurrencyboxComponent implements ControlValueAccessor, OnInit {
  @ViewChild("lstCurrencies") lstCurrencies!: ElementRef;
  @Input() currency: String = Environment.Default.Currency;
  @Output() optionChange: EventEmitter<String> = new EventEmitter<String>();
  public formControl = new FormControl("");
  private onChange = (option: String) => {};
  private onTouched = Function;

  constructor(public ngControl: NgControl) {
    ngControl.valueAccessor = this;
  }

  ngOnInit(): void {
    this.lstCurrencies.nativeElement.value = this.currency;
  }

  public onOptionChange(event: MatSelectChange) {
    console.log("onOptionChange()");
    console.log(event.value);
    this.currency = event.value;
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
      this.currency = value;
    }
  }
}
