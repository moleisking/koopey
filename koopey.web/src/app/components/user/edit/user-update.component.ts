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
//import { ImageEditComponent } from "../../image/edit/image-edit.component";
import { Advert } from "../../../models/advert";
import { Alert } from "../../../models/alert";
import { Environment } from "src/environments/environment";
import { Image } from "../../../models/image";
import { Location } from "../../../models/location";
import { Tag } from "../../../models/tag";
import { User } from "../../../models/user";
import { MatDialog } from "@angular/material/dialog";
import { MatIconRegistry } from "@angular/material/icon";

@Component({
  selector: "user-update-component",
  templateUrl: "user-update.html",
})
export class UserUpdateComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  public form!: FormGroup;
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
    //Note* Call order is important, load data into form
    this.form = this.formBuilder.group({
      address: [
        this.location.address,
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
    this.clickService.createInstance(
      ActionIcon.UPDATE,
      CurrentComponent.UserUpdateComponent
    );
    this.clickSubscription = this.clickService
      .getUserUpdateClick()
      .subscribe(() => {
        this.update();
      });
  }

  ngAfterViewInit() {}

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
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
      location.type = "abode";
      this.form.patchValue({ address: location.address });
      this.authUser.locations.push(location);
      // this.updateRegisterLocation(location.latitude, location.longitude, location.address);
    } else {
      console.log("handleAddressUpdate false");
      this.location.address = "";
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
      this.authUser.locations.push(location);
      // this.updateCurrentLocation(location.latitude, location.longitude, location.address);
    }
  }

  public handleGdprUpdate(agreeOrDisagree: boolean) {
    this.userService.updateGdpr(agreeOrDisagree).subscribe(
      () => {},
      (error: Error) => {
        this.alertService.error(<any>error);
      },
      () => {}
    );
  }

  public openImageDialog(source: number) {
    /*let dialogRef = this.imageUploadDialog.open(ImageDialogComponent);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        //  this.authUser.images.push(result);
        this.authUser.avatar = Image.shrinkImage(result.uri, 256, 256);
        localStorage.setItem("avatar", this.authUser.avatar);
      }
    });*/
  }

  public update() {
    if (!this.form.dirty || !this.form.valid) {
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
