import { Component, OnInit, OnDestroy } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { TranslateService } from "@ngx-translate/core";
import { User } from "../../../models/user";
import { UserService } from "../../../services/user.service";
import { Image as ImageModel } from "../../../models/image";

@Component({
  selector: "image-list-component",
  templateUrl: "image-list.html",
  styleUrls: ["image-list.css"],
})
export class ImageListComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private userSubscription: Subscription = new Subscription();

  public images: Array<ImageModel> = new Array<ImageModel>();
  private compressedWidth = 128;
  private compressedHeight = 128;

  public columns: number = 1;
  private screenWidth: number = window.innerWidth;

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    //Subscribe to clicks
    this.clickService.createInstance(
      ActionIcon.CREATE,
      CurrentComponent.ImageListComponent
    );
    this.clickSubscription = this.clickService
      .getImageListClick()
      .subscribe(() => {
        //this.gotoUserMap();
      });

    //Subscribe to results
    this.userSubscription = this.userService.getUsers().subscribe(
      (users) => {
        //this.users = User.sort(users);
      },
      (error) => {
        console.log(error);
      },
      () => {}
    );
  }

  ngAfterContentInit() {
    this.onScreenSizeChange();
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickSubscription.unsubscribe();
    }
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  public onScreenSizeChange() {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth <= 512) {
      this.columns = 1;
    } else if (this.screenWidth > 512 && this.screenWidth <= 1024) {
      this.columns = 2;
    } else if (this.screenWidth > 1024 && this.screenWidth <= 2048) {
      this.columns = 3;
    } else if (this.screenWidth > 2048 && this.screenWidth <= 4096) {
      this.columns = 4;
    }
  }

  public isImageEmpty(image: ImageModel) {
    if (!image || image.uri.length === 0) {
      return true;
    } else {
      return false;
    }
  }
  public gotoUser(id: String) {
    this.router.navigate(["/user/read/user", id]);
  }

  public gotoAsset(id: String) {
    this.router.navigate(["/user/read/user", id]);
  }

  public showNoResults(): boolean {
    if (!this.images || this.images.length == 0) {
      return true;
    } else {
      return false;
    }
  }
}
