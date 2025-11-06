import { ChangeDetectionStrategy, Component, EventEmitter, Output } from "@angular/core";
import { AlertService } from "../../../services/alert.service";
import { Location } from "../../../models/location";
import { ControlValueAccessor, NgControl } from "@angular/forms";
import { LocationHelper } from "../../../helpers/LocationHelper";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [MatButtonModule, MatIconModule],
  selector: "position-button",
  standalone: true,
  styleUrls: ["position-button.css"],
  templateUrl: "position-button.html",
})
export class PositionButtonComponent implements ControlValueAccessor {
  @Output() updatePosition: EventEmitter<Location> = new EventEmitter<
    Location
  >();

  private onChange = (option: String) => { };
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
          console.log(position);
          let location: Location = new Location();
          location.latitude = position.coords.latitude;
          location.longitude = position.coords.longitude;
          /*location.position = LocationHelper.convertToPosition(
            location.longitude,
            location.latitude
          );*/
          this.onChange(JSON.stringify(location));
          this.onTouched(JSON.stringify(location));
          this.updatePosition.emit(location);
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
      console.log("writeValue()");
      console.log(value);
      // this.location.description = value;
    }
  }
}
