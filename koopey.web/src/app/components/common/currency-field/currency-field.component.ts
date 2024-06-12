import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output, forwardRef } from "@angular/core";
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule } from "@angular/forms";
import { Environment } from "src/environments/environment";
import { MatSelectChange, MatSelectModule } from "@angular/material/select";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatOptionModule } from "@angular/material/core";
import { CommonModule } from "@angular/common";

interface Currency {
  value: string;
  symbol: string;
}

@Component({
  selector: "currency-field",
  styleUrls: ["currency-field.css"],
  templateUrl: "currency-field.html",
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CurrencyFieldComponent),
      multi: true
    }
  ]
})
export class CurrencyFieldComponent implements ControlValueAccessor {

  @Input() value: String = Environment.Default.Currency;
  @Input() disabled = false;
  @Output() onCurrencyChange: EventEmitter<String> = new EventEmitter<String>();

  protected onChange: any = (value: string) => { };
  protected onTouched: any = () => { };

  protected currencies: Currency[] = [
    { value: 'btc', symbol: '฿' },
    { value: 'eur', symbol: '€' },
    { value: 'gbp', symbol: '£' },
    { value: 'usd', symbol: '$' },
    { value: 'zar', symbol: 'R' },
  ];

  protected onSelectChange(event: MatSelectChange) {
    this.writeValue(event.value);
    this.onChange(event.value);
    this.onTouched(event.value);
    this.onCurrencyChange.emit(event.value);
  }

  registerOnChange(fn: (value: string) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  writeValue(value: string) {
    if (value) {
      this.value = value;
    }
  }

}
