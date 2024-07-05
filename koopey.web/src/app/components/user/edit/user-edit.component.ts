import {
  Component,
  OnInit,
  OnDestroy,
  AfterContentInit,
  AfterViewInit,
} from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  Validators,
} from "@angular/forms";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { UserService } from "../../../services/user.service";
import { DomSanitizer, SafeHtml } from "@angular/platform-browser";
import { Environment } from "src/environments/environment";
import { Location } from "../../../models/location";
import { User } from "../../../models/user";
import { MatDialog } from "@angular/material/dialog";
import { LocationType } from "src/app/models/type/LocationType";

@Component({
  selector: "user-edit",
  styleUrls: ["user-edit.css"],
  templateUrl: "user-edit.html",
})
export class UserEditComponent implements OnInit, OnDestroy {
 
  public userEditFormGroup!: FormGroup;
  private screenWidth: number = 0;
  public authUser: User = new User();
  public location: Location = new Location();
  private userSubscription: Subscription = new Subscription();

  constructor(
    private alertService: AlertService,
    private formBuilder: FormBuilder,
    public imageUploadDialog: MatDialog,
    public sanitizer: DomSanitizer,
    private router: Router,
    private userService: UserService
  ) {
    
  }

  ngOnInit() {
    this.userSubscription = this.userService.readMyUser().subscribe(
      (user: User) => {
        this.authUser = user;
        this.formInit();
      },      
      (error: Error) => this.alertService.error(error.message),  
    );
  }

  formInit() {
    this.userEditFormGroup = this.formBuilder.group({
      address: [
        this.location.description,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
      name: [
        this.authUser.name,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
      description: [this.authUser.description],
      education: [this.authUser.description, Validators.maxLength(150)],
      mobile: [
        this.authUser.mobile,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(20),
        ],
      ],
      currency: [this.authUser.currency, Validators.required],
    });
  }

  ngOnDestroy() {
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  public showAlias(): boolean {
    return Environment.Menu.Alias;
  }

  public showName(): boolean {
    return Environment.Menu.Name;
  }

  public onScreenResize(event: any) {
    this.screenWidth = event.target.innerWidth;
    console.log(this.screenWidth);
  }

  public handleAddressUpdate(location: Location) {
    if (location) {
      console.log("handleAddressUpdated true");
      location.type = LocationType.Residence;
      this.userEditFormGroup.patchValue({ address: location.description });
      this.authUser.deliveries.push(location);
      // this.updateRegisterLocation(location.latitude, location.longitude, location.address);
    } else {
      console.log("handleAddressUpdate false");
      this.location.description = "";
    }
  }

  public handleNameUpdate(event: any) {
    if (this.authUser && this.authUser.name) {
      this.authUser.name = this.authUser.name.toLowerCase();
    }
  }

  public handlePositionUpdate(location: Location) {
    console.log("handlePositionUpdate");
    if (location) {
      location.type = LocationType.Residence;
      this.authUser.deliveries.push(location);
      // this.updateCurrentLocation(location.latitude, location.longitude, location.address);
    }
  }

  public update() {
    if (!this.userEditFormGroup.dirty || !this.userEditFormGroup.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.userService.update(this.authUser).subscribe(
        () => {        },
        (error: Error) => {
          this.alertService.error(error.message);
        },
        () => {
          this.alertService.info("SAVED");
        }
      );
    }
  }
}
