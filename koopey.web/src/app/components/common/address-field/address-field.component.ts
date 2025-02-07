import {
  ChangeDetectionStrategy,
  
  Component,
  ElementRef,
  EventEmitter,
  forwardRef,
  Input,
  Output,
  ViewChild,
} from "@angular/core";

import { AlertService } from "../../../services/alert.service";
import { Location } from "../../../models/location";
import { LocationService } from "../../../services/location.service";
import { AbstractControl, ControlValueAccessor, FormControl, FormsModule, NG_VALUE_ACCESSOR, NgControl,  ReactiveFormsModule, ValidationErrors, Validator } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from "@angular/material/autocomplete";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    CommonModule,
    FormsModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => AddressFieldComponent),
      multi: true
    }
  ],
  selector: "address-field",
  standalone: true,
  styleUrls: ["address-field.css"],
  templateUrl: "address-field.html"
})
export class AddressFieldComponent implements ControlValueAccessor, Validator {
 
  @ViewChild("addressInputElement") addressInputElement: ElementRef | undefined;
  @Input() location: Location = new Location();
  @Input() required: boolean = false;

  public address: string = "";
  public addressInputControl: FormControl = new FormControl();
  public trigger: boolean = false;

  @Output() updateAddress: EventEmitter<Location> = new EventEmitter<
    Location
  >();

  protected onChange = (value: String) => { };
  protected onTouched: any = () => { };

  constructor(
    private locationService: LocationService,
  //  public ngControl: NgControl
  ) {
    //this.addressInputControl = new FormControl();
   // ngControl.valueAccessor = this;
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

  validate(control: AbstractControl): ValidationErrors | null {
    return control.value && control.value.length > 0 ? null : { invalid: true };
  }

  writeValue(value: any) {
    if (value) {
      this.address = value;
    }
  }
  
}
