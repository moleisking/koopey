import {
  Component,
  ElementRef,
  EventEmitter,
  forwardRef,
  Input,
  Output,
  OnChanges,
  ViewChild,
} from "@angular/core";
import {
  ControlValueAccessor,
  FormGroup,
  FormBuilder,
  FormControl,
  NG_VALUE_ACCESSOR,
  NG_VALIDATORS,
  Validators,
  Validator,
} from "@angular/forms";

import { AlertService } from "../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { Location } from "../../../models/location";
import { MatIconRegistry } from "@angular/material/icon";
import { LocationService } from "src/app/services/location.service";

@Component({
  selector: "address-textbox",
  templateUrl: "address-textbox.html",
  styleUrls: ["address-textbox.css"],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AddressTextboxComponent),
      multi: true,
    },
  ],
})
export class AddressTextboxComponent
  implements ControlValueAccessor, OnChanges {
  //Added because of innerHTML conflict with name and id
  @ViewChild("addressElement") addressElement: ElementRef | undefined;

  @Input() location: Location = new Location();
  @Input() positionButton: boolean = false;
  @Input() required: boolean = false;
  @Output() updateAddress: EventEmitter<Location> = new EventEmitter<
    Location
  >();
  @Output() updatePosition: EventEmitter<Location> = new EventEmitter<
    Location
  >();

  public address: String = "";

  private propagateChange = (_: any) => {};
  validateFn: any = () => {};
  private validAddress: string = "";
  public GPSEnabled: boolean = false;

  constructor(
    private alertService: AlertService,
    private locationService: LocationService,
    private translateService: TranslateService,
    private iconRegistry: MatIconRegistry
  ) {}

  ngAfterViewInit() {}

  ngOnChanges(inputs: any) {
    if (inputs) {
      //console.log("address ngOnChanges custom validator here if");
      //console.log(this.location);
      // this.validAddress = this.location.address;
      this.validateFn = this.location.address ? true : false;
      this.propagateChange(this.location.address);
    } else {
      //console.log("address ngOnChanges custom validator here else");
    }
  }

  public isGPSEnabled(): Boolean {
    return this.GPSEnabled;
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
    //used only for current position
    if (navigator.geolocation) {
      if (this.GPSEnabled == false) {
        //device supports geolocation
        navigator.geolocation.getCurrentPosition(
          (position) => {
            this.GPSEnabled = true;
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
          (error) => {
            //geolocation permission denied
            console.log("geolocation permission denied:" + error);
            this.GPSEnabled = false;
            this.updatePosition.emit(undefined);
          }
        );
      } else {
        this.GPSEnabled = false;
      }
    } else {
      this.GPSEnabled = false;
      //device does not support geolocation
      this.alertService.error("ERROR_LOCATION_NOT_PERMITTED");
    }
  }

  //For ControlValueAccessor interface
  writeValue(value: any) {
    console.log("address writeValue");
    if (value) {
      this.location.address = value;
    }
  }

  //For ControlValueAccessor interface
  registerOnChange(fn: any) {
    console.log("address registerOnChange");
    this.propagateChange = fn;
  }

  //For ControlValueAccessor interface
  registerOnTouched(fn: any) {}

  // returns null when valid else the validation object
  // in this case we're checking if the json parsing has
  // passed or failed from the onChange method
  public validate(c: FormControl) {
    //console.log("address validate");
    return this.validateFn(c);
  }

  //For form validator
  public onChange(event: any) {
    // get value from text area
    setTimeout(() => {
      this.writeValue(event.target.value);
    }, 500);
    //  this.address = event.target.value;
    //console.log(this.address);
  }

  //Avoid incomplete or corrupt addresses
  public onBlur(event: any) {
    if (this.required == true) {
      if (!this.location.address) {
        this.location.address = this.validAddress;
      } else if (this.location.address != this.validAddress) {
        this.location.address = this.validAddress;
      }
    }
  }
}
