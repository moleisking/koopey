import {
  Component,
  ElementRef,
  EventEmitter,
  Output,
  ViewChild,
} from "@angular/core";
import { ControlValueAccessor, FormControl } from "@angular/forms";

@Component({
  selector: "datetimebox",
  templateUrl: "datetimebox.html",
})
export class DatetimeboxComponent implements ControlValueAccessor {
  @ViewChild("datetimeElement") addressElement: ElementRef | undefined;

  public datetime: string = "";

  @Output() updateDatetime: EventEmitter<number> = new EventEmitter<number>();

  public formControl = new FormControl("");
  private onTouched = Function;
  private onChange = (option: String) => {};

  constructor() {}

  ngAfterViewInit() {}

  registerOnChange(fn: any) {
    this.onChange = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouched = fn;
  }

  writeValue(value: any) {
    if (value) {
      this.datetime = value;
    }
  }
}
