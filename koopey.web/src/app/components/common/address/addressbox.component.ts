import {
  Component,
  ElementRef,
  EventEmitter,
  forwardRef,
  Input,
  OnChanges,
  Output,
  SimpleChanges,
  ViewChild,
} from "@angular/core";
import { AlertService } from "../../../services/alert.service";
import { Location } from "../../../models/location";
import { LocationService } from "src/app/services/location.service";
import {
  ControlValueAccessor,
  FormControl,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
} from "@angular/forms";

@Component({
  /* providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AddressboxComponent),
      multi: true,
    },

    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => AddressboxComponent),
      multi: true,
    },
  ],*/
  selector: "addressbox",
  styleUrls: ["addressbox.css"],
  templateUrl: "addressbox.html",
})
export class AddressboxComponent implements ControlValueAccessor {
  @ViewChild("addressElement") addressElement: ElementRef | undefined;
  @Input() location: Location = new Location();
  @Input() required: boolean = false;
  public address: string = "";
  public trigger: boolean = false;

  @Output() updateAddress: EventEmitter<Location> = new EventEmitter<
    Location
  >();

  private onChange = (option: String) => {};
  private onTouched = Function;

  constructor(private locationService: LocationService) {}

  onBlur(event: any) {
    if (this.trigger == false && this.address.length > 5) {
      this.getAddress();
    }
  }

  public getAddress() {
    this.location.address = this.address;
    this.locationService.searchPlace(this.location).subscribe(
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
