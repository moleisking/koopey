import {
  Component,
  ElementRef,
  EventEmitter,
  OnInit,
  OnDestroy,
  ViewChild,
} from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators,
} from "@angular/forms";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { UUID } from "angular2-uuid";
import { AlertService } from "../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { UserService } from "../../../services/user.service";
import { DomSanitizer, SafeHtml } from "@angular/platform-browser";
import { Environment } from "src/environments/environment";
import { Image } from "../../../models/image";
import { Location } from "../../../models/location";
import { User } from "../../../models/user";
import { MatDialog } from "@angular/material/dialog";
import { MatIconRegistry } from "@angular/material/icon";

@Component({
  selector: "user-edit",
  styleUrls: ["user-edit.css"],
  templateUrl: "user-edit.html",
})
export class UserEditComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  public formGroup!: FormGroup;
  private screenWidth: number = 0;
  public authUser: User = new User();
  public location: Location = new Location();

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    private formBuilder: FormBuilder,
    private iconRegistry: MatIconRegistry,
    public imageUploadDialog: MatDialog,
    public sanitizer: DomSanitizer,
    private router: Router,
    private userService: UserService
  ) {
    this.userService.readMyUser().subscribe(
      (user) => {
        this.authUser = user;
        // this.location = Location.read(this.authUser.locations, "abode");
        //this.bitcoinWallet = Wallet.readBitcoin(this.authUser.wallets);
        //this.ethereumWallet = Wallet.readEthereum(this.authUser.wallets);
        //this.fees = user.fees;
      },
      (error) => {
        this.alertService.error(<any>error);
      },
      () => {
        if (Environment.type != "production") {
          console.log(this.authUser);
        }
      }
    );
  }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
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

  ngAfterContentInit() {
    /*this.clickService.createInstance(
      ActionIcon.UPDATE,
      CurrentComponent.UserUpdateComponent
    );
    this.clickSubscription = this.clickService
      .getUserUpdateClick()
      .subscribe(() => {
        this.update();
      });*/
  }

  ngAfterViewInit() {}

  ngOnDestroy() {
    /*if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }*/
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
      location.type = "abode";
      this.formGroup.patchValue({ address: location.description });
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
      location.type = "abode";
      this.authUser.deliveries.push(location);
      // this.updateCurrentLocation(location.latitude, location.longitude, location.address);
    }
  }

  public update() {
    if (!this.formGroup.dirty || !this.formGroup.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.userService.update(this.authUser).subscribe(
        (alert: String) => {
          console.log(alert);
        },
        (error) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.alertService.info("SAVED");
        }
      );
    }
  }
}
