import { Component, EventEmitter, Input, Output } from "@angular/core";
import { ControlValueAccessor, FormControl, NgControl } from "@angular/forms";
import { LocationType } from "src/app/models/type/LocationType";
import { MatSelectChange } from "@angular/material/select";

@Component({
  selector: "locationtype",
  styleUrls: ["location-type.css"],
  templateUrl: "location-type.html",
})
export class LocationTypeComponent implements ControlValueAccessor {
  @Input() type: String = LocationType.Delivery;
  @Output() optionChange: EventEmitter<String> = new EventEmitter<String>();
  public formControl: FormControl = new FormControl(LocationType.Delivery);
  private onChange = (option: String) => {};
  private onTouched = Function;

  constructor(public ngControl: NgControl) {
    this.formControl = new FormControl(LocationType.Delivery);
    ngControl.valueAccessor = this;
  }

  public onOptionChange(event: MatSelectChange) {
    this.type = event.value;
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
      this.type = value;
    }
  }
}
