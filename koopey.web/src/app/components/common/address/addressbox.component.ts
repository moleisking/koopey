import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  Output,
  ViewChild,
} from "@angular/core";
import { AlertService } from "../../../services/alert.service";
import { Location } from "../../../models/location";
import { LocationService } from "src/app/services/location.service";
import { ControlValueAccessor, FormControl, NgControl } from "@angular/forms";

@Component({
  selector: "addressbox",
  styleUrls: ["addressbox.css"],
  templateUrl: "addressbox.html",
})
export class AddressboxComponent implements ControlValueAccessor {
  @ViewChild("addressElement") addressElement: ElementRef | undefined;
  public addressFormControl: FormControl = new FormControl();
  @Input() location: Location = new Location();
  @Input() required: boolean = false;
  public address: string = "";
  public trigger: boolean = false;

  @Output() updateAddress: EventEmitter<Location> = new EventEmitter<
    Location
  >();

  private onChange = (option: String) => {};
  private onTouched = Function;

  constructor(
    private locationService: LocationService,
    public ngControl: NgControl
  ) {
    this.addressFormControl = new FormControl();
    ngControl.valueAccessor = this;
  }

  public getAddress() {
    this.location.description = this.address;
    this.locationService.searchByPlace(this.location).subscribe(
      (location: Location) => {
        this.location = location;
      },
      (error: Error) => {
        console.log(error.message);
      },
      () => {
        this.trigger = true;
        this.updateAddress.emit(this.location);
      }
    );
  }

  onBlur(event: any) {
    if (this.trigger == false && this.address.length > 5) {
      this.getAddress();
    }
  }

  registerOnChange(fn: any) {
    this.onChange = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouched = fn;
  }

  writeValue(value: any) {
    if (value) {
      this.address = value;
    }
  }
}
