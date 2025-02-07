import { AfterContentChecked, ChangeDetectorRef, Component, EventEmitter, Input, Output } from "@angular/core";
import { ControlValueAccessor, FormControl, NgControl, Validators } from "@angular/forms";
import { LocationType } from "../../../models/type/LocationType";
import { MatSelectChange } from "@angular/material/select";

@Component({
  selector: "locationtype",
  styleUrls: ["location-type.css"],
  templateUrl: "location-type.html"
})
export class LocationTypeComponent implements ControlValueAccessor, AfterContentChecked {
  @Input() type: String = LocationType.Residence;
  @Output() optionChange: EventEmitter<String> = new EventEmitter<String>();
  public formControl: FormControl = new FormControl(LocationType.Residence);
  private onChange!: Function;
  private onTouched!: Function;

  constructor(private changeDetectorRef: ChangeDetectorRef, public ngControl: NgControl) {
    this.formControl = new FormControl(this.type, [Validators.minLength(5), Validators.maxLength(10), Validators.required]);
    ngControl.valueAccessor = this;
  }

  ngAfterContentChecked(): void {
    this.changeDetectorRef.detectChanges();
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
    if (value !== undefined && this.type !== value && value !== "") {
      this.type = value;
    }
  }
}
