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
import { ImageDialogComponent } from "../../image/dialog/image-dialog.component";
import { Advert } from "../../../models/advert";
import { Alert } from "../../../models/alert";
import { Config } from "../../../config/settings";
import { Image } from "../../../models/image";
import { Location } from "../../../models/location";
import { Tag } from "../../../models/tag";
import { User } from "../../../models/user";
import "hammerjs";
import { MatDialog } from "@angular/material/dialog";
import { MatIconRegistry } from "@angular/material/icon";

@Component({
  selector: "user-update-component",
  templateUrl: "user-update.html",
})
export class UserUpdateComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private form!: FormGroup;
  private screenWidth: number = 0;
  private authUser: User = new User();
  private location: Location = new Location();

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    private formBuilder: FormBuilder,
    private iconRegistry: MatIconRegistry,
    public imageUploadDialog: MatDialog,
    private sanitizer: DomSanitizer,
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
        if (!Config.system_production) {
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

  private showAlias(): boolean {
    return Config.business_model_alias;
  }

  private showName(): boolean {
    return Config.business_model_name;
  }

  /*********  Events *********/

  private onScreenResize(event: any) {
    this.screenWidth = event.target.innerWidth;
    console.log(this.screenWidth);
  }

  private handleAddressUpdate(location: Location) {
    if (location) {
      console.log("handleAddressUpdated true");
      location.type = "abode";
      this.form.patchValue({ address: location.address });
      this.authUser.location = location;
      // this.updateRegisterLocation(location.latitude, location.longitude, location.address);
    } else {
      console.log("handleAddressUpdate false");
      this.location.address = "";
    }
  }

  private handleNameUpdate(event: any) {
    if (this.authUser && this.authUser.name) {
      this.authUser.name = this.authUser.name.toLowerCase();
    }
  }

  private handlePositionUpdate(location: Location) {
    console.log("handlePositionUpdate");
    if (location) {
      location.type = "abode";
      this.authUser.location = location;
      // this.updateCurrentLocation(location.latitude, location.longitude, location.address);
    }
  }

  private handleTermsUpdate(agreeOrDisagree: boolean) {
    this.authUser.terms = agreeOrDisagree;
    this.userService.updateTerms(this.authUser).subscribe(
      () => {},
      (error: Error) => {
        this.alertService.error(<any>error);
      },
      () => {}
    );
  }

  private openImageDialog(source: number) {
    let dialogRef = this.imageUploadDialog.open(ImageDialogComponent);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        //  this.authUser.images.push(result);
        this.authUser.avatar = Image.shrinkImage(result.uri, 256, 256);
        localStorage.setItem("avatar", this.authUser.avatar);
      }
    });
  }

  private update() {
    if (!User.isUpdate(this.authUser)) {
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
