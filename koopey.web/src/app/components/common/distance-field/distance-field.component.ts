import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output, forwardRef } from "@angular/core";
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule } from "@angular/forms";
import { Environment } from "src/environments/environment";
import { MatFormFieldModule } from "@angular/material/form-field";
import { CommonModule } from "@angular/common";
import { MeasurementType } from "src/app/models/type/MeasurementType";
import { MatSliderModule } from "@angular/material/slider";

@Component({
  selector: "distance-field",
  styleUrls: ["distance-field.css"],
  templateUrl: "distance-field.html",
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSliderModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => DistanceFieldComponent),
      multi: true
    }
  ]
})
export class DistanceFieldComponent implements ControlValueAccessor {

  @Input() value: number = Environment.Default.Radius;
  @Input() disabled = false;
  @Output() onDistanceChange: EventEmitter<number> = new EventEmitter<number>();

  protected onChange: any = (value: string) => { };
  protected onTouched: any = () => { };

  protected onSliderChange() {   
    this.writeValue(this.value);
    this.onChange(this.value);
    this.onTouched(this.value);
    this.onDistanceChange.emit(this.value);   
  }

  protected isMetric(): boolean {
    switch (localStorage.getItem("measurement")) {
      case undefined || 'undefined':
        return true;
      case MeasurementType.Metric:
        return true;
      case MeasurementType.Imperial:
        return false;
      default:
        return true;
    }
  }

  registerOnChange(fn: (value: number) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  writeValue(value: number) {
    if (value) {
      this.value = value;
    }
  }

}
