import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  Output,
  ViewChild,
} from "@angular/core";
import { AlertService } from "../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { Location } from "../../../models/location";
import { LocationService } from "src/app/services/location.service";
import { MatIconRegistry } from "@angular/material/icon";

@Component({
  selector: "address-textbox",
  templateUrl: "address-textbox.html",
  styleUrls: ["address-textbox.css"],
})
export class AddressTextboxComponent {
  @ViewChild("addressElement") addressElement: ElementRef | undefined;

  @Input() addressButton: boolean = false;
  @Input() location: Location = new Location();
  @Input() positionButton: boolean = false;
  @Input() required: boolean = false;

  @Output() updateAddress: EventEmitter<Location> = new EventEmitter<
    Location
  >();
  @Output() updatePosition: EventEmitter<Location> = new EventEmitter<
    Location
  >();

  constructor(
    private alertService: AlertService,
    private locationService: LocationService,
    private translateService: TranslateService,
    private iconRegistry: MatIconRegistry
  ) {}

  ngAfterViewInit() {}

  onChange(input: any) {
    if (input) {
      this.location.address = input;
      this.updateAddress.emit(this.location);
      console.log("address ngOnChanges custom validator here if");
    } else {
      console.log("address ngOnChanges custom validator here else");
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
    this.locationService.searchPlace(this.location).subscribe(
      (location: Location) => {
        this.location = location;
      },
      (error: Error) => {
        console.log(error.message);
      },
      () => {}
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
}
