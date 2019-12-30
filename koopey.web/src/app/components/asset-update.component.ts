//Angular, Material, Libraries
import { Component, ElementRef, Input, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import {
    MaterialModule, MdIconModule, MdIconRegistry, MdInputModule,
    MdTextareaAutosize, MdDialog, MdDialogRef
} from "@angular/material"
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from 'rxjs/Subscription';
//Components
import { ImageDialogComponent } from "./image-dialog.component";
//Services
import { AlertService } from "../services/alert.service";
import { ClickService, CurrentComponent, ActionIcon } from "../services/click.service";
import { UserService } from "../services/user.service";
import { AssetService } from "../services/asset.service";
//Objects
import { Advert } from "../models/advert";
import { Config } from "../config/settings";
import { File as FileModel } from "../models/file";
import { Image } from "../models/image";
import { Location } from "../models/location";
import { User } from "../models/user";
import { Asset } from "../models/asset";
import { Tag } from "../models/tag";

import 'hammerjs';
import { DateHelper } from "../helpers/DateHelper";

@Component({
    selector: "asset-update-component",
    templateUrl: "../../views/asset-update.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class AssetUpdateComponent implements OnInit, OnDestroy {

    private clickSubscription: Subscription;
    private assetSubscription: Subscription;
    private LOG_HEADER: string = 'PRODUCT:UPDATE';
    private form: FormGroup;
    private asset: Asset = new Asset();
    private manufactureTimestamp: number = 0;
    private manufactureDate: Date = new Date();
    private manufactureString: String = "2011-10-05";//"2011-10-05T14:48:00.000"
    private IMAGE_SIZE: number = 512;
    private IMAGE_COUNT: number = 4;
    //private fileChange: boolean = false;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private formBuilder: FormBuilder,
        private userService: UserService,
        private assetService: AssetService,
        private alertService: AlertService,
        private iconRegistry: MdIconRegistry,
        private clickService: ClickService,
        public imageUploadDialog: MdDialog
    ) { }

    ngOnInit() {
        this.clickService.createInstance(ActionIcon.UPDATE, CurrentComponent.AssetUpdateComponent);
        this.clickSubscription = this.clickService.getAssetUpdateClick().subscribe(() => {
            this.updateAsset();
        });

        this.form = this.formBuilder.group({
            available: [this.asset.available, Validators.required],
            currency: [this.asset.currency],
            description: [this.asset.description],
            fileName: [this.asset.file.name],
            height: [this.asset.height, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
            length: [this.asset.length, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
            manufactureDate: [this.manufactureString, [Validators.required, Validators.minLength(5), Validators.maxLength(10)]],
            quantity: [this.asset.quantity, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
            tags: [this.asset.tags, [Validators.required]],
            title: [this.asset.title, [Validators.required, Validators.minLength(5), Validators.maxLength(150)]],
            value: [this.asset.value, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
            weight: [this.asset.weight, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
            width: [this.asset.width, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]]
        });
    }

    ngAfterContentInit() {
        console.log("au ngAfterContentInit")
        this.route.params.subscribe(p => {
            let id = p["id"];
            if (id) {
                this.assetService.readAsset(id).subscribe(
                    (asset) => {
                        this.asset = asset;
                        this.manufactureString = DateHelper.convertEpochToDateString(this.asset.manufactureDate);
                    },
                    (error) => { this.alertService.error(<any>error) },
                    () => {
                        if (!Config.system_production) {
                            console.log(this.asset);
                        }
                    }
                );
            } else {
                this.assetSubscription = this.assetService.getAsset().subscribe(
                    (asset) => {
                        this.asset = asset;
                        this.manufactureString = DateHelper.convertEpochToDateString(this.asset.manufactureDate);
                    },
                    (error) => { this.alertService.error(<any>error) },
                    () => {
                        if (!Config.system_production) {
                            console.log(this.asset);
                        }
                    });
            }
        });
    }

    ngAfterViewInit() {
        //NOTE: Call after asset loaded to refresh user data in asset.
        this.userService.readMyUser().subscribe(
            (user) => {
                this.asset.user = User.simplify(user);
                this.asset.location = user.location;
            },
            (error) => { this.alertService.error(<any>error) },
            () => { }
        );
    }

    ngOnDestroy() {
        if (this.clickSubscription) {
            this.clickService.destroyInstance();
            this.clickSubscription.unsubscribe();
        }
        if (this.assetSubscription) {
            this.assetSubscription.unsubscribe();
        }
    }

    /*********  Functions *********/

    private hasImage(index: number): boolean {
        return this.asset.images && this.asset.images.length > index ? true : false;
    }

    private hasFile(): boolean {
        return FileModel.isEmpty(this.asset.file);
    }

    private getDimensionUnit(): string {
        return Config.default_dimension_unit;
    }

    private getWeightUnit(): string {
        return Config.default_weight_unit;
    }

    private isFileVisible() {
        return Config.business_model_files;
    }

    /*********  Events *********/

    private fileChangeListener($event: any) {
        var file: File = $event.target.files[0];
        var myReader: FileReader = new FileReader();
        var that = this;

        if (file.size <= Config.file_max_value) {
            var fileModel: FileModel = new FileModel();
            fileModel.size = file.size;
            fileModel.name = file.name;
            fileModel.type = file.type;

            myReader.onloadend = function (loadEvent: any) {
                fileModel.data = loadEvent.target.result;
                that.assetService.updateFile(that.asset, fileModel).subscribe(
                    (success) => {
                        console.log("file upload success");
                    },
                    (error) => { console.log("file upload fail"); console.log(fileModel); },
                    () => { console.log("file upload end") }
                );
            };
            //Adds:data:application/x-zip-compressed;base64,XXX
            myReader.readAsDataURL(file);
        } else {
            this.alertService.error("ERROR_FILE_TOO_LARGE");
        }
    }

    private handleAdvertUpdate(advert: Advert) {
        console.log("handleAdvertUpdate");
        this.asset.advert = advert;
    }

    private handleManufactureDateUpdate(event: any) {
        var utcDate = new Date(event.target.value);
        if (utcDate.getFullYear() > 1900 && utcDate.getMonth() >= 0 && utcDate.getDate() > 0) {
            this.asset.manufactureDate = utcDate.getTime();
            console.log("handleManufactureDateUpdate(event: any)")
            console.log(this.asset.manufactureDate)
        }
    }

    private handleTagUpdated(selectedTags: Array<Tag>) {
        this.asset.tags = selectedTags;
        this.form.patchValue({ 'tags': selectedTags });
    }

    private openImageDialog(imageIndex: number) {
        let dialogRef = this.imageUploadDialog.open(ImageDialogComponent);
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                //Update DAL object with image
                if (this.asset.images[imageIndex]) {
                    //case: Keep or reset current image                   
                    this.asset.images[imageIndex].uri = result.uri;
                } else {
                    //case: add new image  
                    this.asset.images.push(result);
                }
            }
        });
    }

    /*********  Actions *********/

    /*private updateFile(file: File) {
        this.assetService.updateFile(this.asset.id, file).subscribe(
            success => {
                console.log("file upload success");
            },
            error => { console.log("file upload fail") },
            () => { console.log("file upload end") }
        );
    }*/

    /* private createAdvert() {
         //Create advert           
         var advert: Advert = new Advert();
         advert.startTimeStamp = this.location.latitude;
         advert.endTimeStamp = this.location.longitude;
         //Write to database
         if (!this.authyUser.advert) {
             //Post updated user
             this.userService.updateAdvert(advert).subscribe(
                 () => { },
                 error => { this.alertService.error(<any>error); },
                 () => { this.alertService.info("SAVED"); }
             );
         }
     }*/

    private updateAsset() {
        //Note* do not set regLat and regLng as already set during registration 
        console.log("AssetSaveComponent:updateAsset()");
        console.log(this.asset);
        console.log(this.asset.user);
        if (Asset.isUpdate(this.asset)) {
            this.alertService.error("ERROR_CORRUPT");
        } else if (this.asset.user.id != localStorage.getItem("id")) {
            this.alertService.error("ERROR_AUTHENTICATION_FAILURE");
        } else if (!User.isAuthenticated(this.asset.user)) {
            this.alertService.error("ERROR_NOT_ACTIVATED");
        } else if (!User.isLegal(this.asset.user)) {
            this.alertService.error("ERROR_NOT_LEGAL");
        } else if (!this.asset.value && this.asset.value <= 0) {
            this.alertService.error("ERROR_VALUE_REQUIRED");
        } else if (!this.asset.tags && this.asset.tags.length != 0) {
            this.alertService.error("ERROR_TAG_REQUIRED");
        } else if (Asset.isEmpty(this.asset)) {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        } else {
            this.assetService.update(this.asset).subscribe(
                () => { this.alertService.success("SAVED") },
                (error) => { this.alertService.error(<any>error) },
                () => { }
            );
        }
    }
}
