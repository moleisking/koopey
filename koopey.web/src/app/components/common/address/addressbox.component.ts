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
//import { TranslateService } from "@ngx-translate/core";
import { Location } from "../../../models/location";
import { LocationService } from "src/app/services/location.service";
import {
  ControlValueAccessor,
  FormControl,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
} from "@angular/forms";

@Component({
  providers: [
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
  ],
  selector: "addressbox",
  styleUrls: ["addressbox.css"],
  templateUrl: "addressbox.html",
})
export class AddressboxComponent implements ControlValueAccessor {
  @ViewChild("addressElement") addressElement: ElementRef | undefined;

  @Input() addressButton: boolean = false;
  @Input() location: Location = new Location();
  @Input() positionButton: boolean = false;
  @Input() required: boolean = false;
  public address: string = "";
  public trigger: boolean = false;

  @Output() updateAddress: EventEmitter<Location> = new EventEmitter<
    Location
  >();
  @Output() updatePosition: EventEmitter<Location> = new EventEmitter<
    Location
  >();

  private propagateChange = (_: any) => {};
  private validateFn: any = () => {};

  constructor(
    private alertService: AlertService,
    private locationService: LocationService //  private translateService: TranslateService, // private iconRegistry: MatIconRegistry
  ) {}

  ngAfterViewInit() {}

  onBlur(event: any) {
    if (this.trigger == false && this.address.length > 5) {
      this.getAddress();
    }
  }

  public isPositionEnabled(): Boolean {
    if (navigator.geolocation) {
      return true;
    } else {
      return false;
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

  public getPosition() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.location.latitude = position.coords.latitude;
          this.location.longitude = position.coords.longitude;
          this.location.position = Location.convertToPosition(
            this.location.longitude,
            this.location.latitude
          );
          console.log(
            "geolocation permission success" + JSON.stringify(this.location)
          );
          this.updatePosition.emit(this.location);
        },
        (error: any) => {
          console.log("geolocation permission denied:" + error);
          this.updatePosition.emit(undefined);
        }
      );
    } else {
      this.alertService.error("ERROR_LOCATION_NOT_PERMITTED");
    }
  }

  registerOnChange(fn: any) {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any) {}

  validate(c: FormControl) {
    return this.validateFn(c);
  }

  writeValue(value: any) {
    if (value) {
      this.address = value;
    }
  }
}
