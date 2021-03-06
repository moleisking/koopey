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

import { Environment } from "src/environments/environment";
import { AlertService } from "../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { Location } from "../../../models/location";
import { MatIconRegistry } from "@angular/material/icon";
//declare let google: any;

//import {} from '@types/googlemaps';
//import PlaceResult = google.maps.places.PlaceResult;

@Component({
  selector: "address-textbox",
  templateUrl: "address-textbox.html",
  styleUrls: ["address-textbox.css"],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AddressTextboxComponent),
      multi: true,
    } /*,
        {
            provide: NG_VALIDATORS,
            useExisting: forwardRef(() => AddressComponent),
            multi: true
        }*/,
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
    private translateService: TranslateService,
    private iconRegistry: MatIconRegistry
  ) {}

  ngAfterViewInit() {
    /* if (!this.location) {
             this.location = new Location();
         }*/
    //Must be called here to avoid google undefined error, ngOnInit fails
    setTimeout(() => {
      this.startAddressListener();
    }, 2000);
  }

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

  public getGoogleAPIKey(): String {
    return "ENVIRONMENT.GOOGLE_API_KEY";
  }

  public isGPSEnabled(): Boolean {
    return this.GPSEnabled;
  }

  public isGoogleAPIConnected(): Boolean {
    try {
      if (!google || !google.maps) {
        console.log("Google Maps JS library is not loaded!");
        return false;
      } else if (!google.maps.places) {
        console.log("Google Maps JS library does not have the Places module");
        return false;
      }
    } catch (error) {
      console.log("Google Maps JS library not loaded");
      console.log(error);
      return false;
    }
    return true;
  }

  private startAddressListener() {
    try {
      if (!google || !google.maps) {
        console.log("Google Maps JS library is not loaded!");
      } else if (!google.maps.places) {
        console.log("Google Maps JS library does not have the Places module");
      }
      let options = {
        // return only geocoding results, rather than business results.
        types: ["geocode"],
        componentRestrictions: { country: Environment.Default.Country },
      };
      if (this.addressElement != undefined) {
        let autocomplete = new google.maps.places.Autocomplete(
          this.addressElement.nativeElement,
          options
        );
        //let autocomplete = new google.maps.places.Autocomplete(searchBox, options);
        // Add listener to the place changed event
        autocomplete.addListener("place_changed", () => {
          this.getAddress(autocomplete.getPlace());
        });
      }
    } catch (error) {
      console.log("Google Maps JS library not loaded");
      console.log(error);
    }
  }

  public getAddress(place: any) {
    //only called when inserting a new address
    console.log("getAddress called");
    //Check reply address data exists
    if (place["formatted_address"]) {
      let address = place["formatted_address"];
      if (address) {
        console.log("getAddress emit true:" + address);
        this.location.address = address;
        this.validAddress = address;
        if (place["formatted_address"]) {
          let p = place["geometry"]["location"];
          if (p) {
            this.location.latitude = p.lat();
            this.location.longitude = p.lng();
            this.location.position = Location.convertToPosition(
              this.location.longitude,
              this.location.latitude
            );
            this.GPSEnabled = false;
            console.log("getAddress emit loc:" + JSON.stringify(this.location));
          }
        }
        this.updateAddress.emit(this.location);
      } else {
        console.log("getAddress emit false");
        this.updateAddress.emit(undefined);
      }
    } else {
      console.log("getAddress emit false");
      this.updateAddress.emit(undefined);
    }
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
