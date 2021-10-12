import { Component, EventEmitter, Output } from "@angular/core";
import { AlertService } from "../../../services/alert.service";
import { Location } from "../../../models/location";
import { ControlValueAccessor, NgControl } from "@angular/forms";

@Component({
  selector: "positionbutton",
  styleUrls: ["positionbutton.css"],
  templateUrl: "positionbutton.html",
})
export class PositionButtonComponent implements ControlValueAccessor {
  @Output() updatePosition: EventEmitter<Location> = new EventEmitter<
    Location
  >();

  public location: Location = new Location();
  private onChange = (option: String) => {};
  private onTouched = Function;

  constructor(private alertService: AlertService, public ngControl: NgControl) {
    ngControl.valueAccessor = this;
  }

  public isPositionEnabled(): Boolean {
    if (navigator.geolocation) {
      return true;
    } else {
      return false;
    }
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
          this.onChange(JSON.stringify(this.location));
          this.onTouched(JSON.stringify(this.location));
          this.updatePosition.emit(this.location);
        },
        (error: GeolocationPositionError) => {
          console.log(error.message);
          this.updatePosition.emit(undefined);
        }
      );
    } else {
      this.alertService.error("ERROR_LOCATION_NOT_PERMITTED");
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
      this.location.address = value;
    }
  }
}
