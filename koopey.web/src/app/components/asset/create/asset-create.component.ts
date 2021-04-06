//Angular, Material, Libraries
import { Component, ElementRef, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import {
    MaterialModule, MdIconModule, MdIconRegistry, MdInputModule,
    MdRadioChange, MdTextareaAutosize, MdDialog, MdDialogRef
} from "@angular/material"
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from 'rxjs/Subscription';
//Components
import { ImageDialogComponent } from "../../image/dialog/image-dialog.component";
//Services
import { AlertService } from "../../../services/alert.service";
import { ClickService, CurrentComponent, ActionIcon } from "../../../services/click.service";
import { AssetService } from "../../../services/asset.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../../../services/user.service";
//Objects
import { Advert } from "../../../models/advert";
import { Config } from "../../../config/settings";
import { Image } from "../../../models/image";
import { Location } from "../../../models/location";
import { Asset } from "../../../models/asset";
import { Tag } from "../../../models/tag";
import { User } from "../../../models/user";
import { Wallet } from "../../../models/wallet";

@Component({
    selector: "asset-create-component",
    templateUrl: "../../views/asset-create.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class AssetCreateComponent implements OnInit, OnDestroy {

    private LOG_HEADER: string = 'ASSET:CREATE';
    private clickSubscription: Subscription;
    private form: FormGroup;   
    private locations: Array<Location> = new Array<Location>();
    private asset: Asset = new Asset();
    private IMAGE_SIZE: number = 512;
    private IMAGE_COUNT: number = 4;
    private wallet: Wallet = new Wallet();
    private manufactureDate: number = 0;
    private screenWidth: number = 0;
    private tangible: boolean = true;//NOTE: Default asset.type is product

    constructor(
        private activatedRoute: ActivatedRoute,
        private alertService: AlertService,
        private clickService: ClickService,
        private formBuilder: FormBuilder,
        public imageUploadDialog: MdDialog,
        private assetService: AssetService,
        private userService: UserService,
        private router: Router,
        private translateService: TranslateService
    ) {
       
    }

    ngOnInit() {
        this.activatedRoute.queryParams.subscribe((parameter) => {
            if (parameter['type']) {
                this.asset.type = parameter['type'];
                this.tangible = Asset.isProduct(this.asset);
            }
        });

        this.form = this.formBuilder.group({
            currency: [this.asset.currency],
            title: [this.asset.title, [Validators.required, Validators.minLength(5), Validators.maxLength(150)]],
            description: [this.asset.description, [Validators.maxLength(150)]],
            height: [this.asset.height, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
            length: [this.asset.length, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],           
            manufactureDate: [this.manufactureDate, [Validators.required, Validators.minLength(5), Validators.maxLength(10)]],
            quantity: [this.asset.quantity, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
            tags: [this.asset.tags, [Validators.required]],
            value: [this.asset.value, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],                
            weight: [this.asset.weight, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
            width: [this.asset.width, [Validators.required, Validators.minLength(1), Validators.maxLength(10)]]     
        });
    }

    ngAfterContentInit() {
        this.clickService.createInstance(ActionIcon.CREATE, CurrentComponent.AssetCreateComponent);
        this.clickSubscription = this.clickService.getAssetCreateClick().subscribe(() => {
            this.create();
        });
    }

    ngAfterViewInit() {
        this.userService.readMyUser().subscribe(
            (user) => {
                this.asset.user = User.simplify(user);
                this.asset.location = user.location;
            },
            (error) => { this.alertService.error(<any>error) },
            () => { }
        );
        if (!Config.system_production) {
            console.log(this.asset);
        }
    }

    ngOnDestroy() {
        if (this.clickSubscription) {
            this.clickService.destroyInstance()
            this.clickSubscription.unsubscribe();
        }
    }

    private getDimensionUnit(): string {
        return Config.default_dimension_unit;
    }

    private getWeightUnit(): string {
        return Config.default_weight_unit;
    }  

    private onToggleProductOrService(event: MdRadioChange) {
        if (event.value === "product") {
            this.asset.type = "product";
            this.tangible = true;
        } else if (event.value === "service") {
            //NOTE: Quantity needs to be at least 1 to apprear in search results
            this.asset.type = "service";            
            this.asset.quantity = 1;
            this.tangible = false;
        }
    }
  
    /*********  Events *********/

    private handleAdvertUpdate(advert: Advert) {
        console.log("handleAdvertUpdate");
        this.asset.advert = advert;
    }  

    private handleManufactureDateUpdate(event: any) {
        var utcDate = new Date(event.target.value);
        if (utcDate.getFullYear() > 1900 && utcDate.getMonth() >= 0 && utcDate.getDate() > 0) {
            this.asset.manufactureDate = utcDate.getTime();
        }
    }

    private handleTagUpdated(selectedTags: Array<Tag>) {
        console.log("handleTagUpdated");
        this.asset.tags = selectedTags;
        this.form.patchValue({ tags: selectedTags });
    }

    private openImageDialog(imageIndex: number) {
        let dialogRef = this.imageUploadDialog.open(ImageDialogComponent);        
        if (this.asset.images[imageIndex]) {
            dialogRef.componentInstance.setImage(this.asset.images[imageIndex]);
        }

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

    private create() {
        //NOTE: Location is set in the backend and the user is set during ngInit       
        if (!this.asset.value && this.asset.value <= 0) {
            this.alertService.error("ERROR_VALUE_REQUIRED");
        } else if (!this.asset.tags && this.asset.tags.length != 0) {
            this.alertService.error("ERROR_TAG_REQUIRED");
        } else if (this.asset.quantity <= 0) {
            this.alertService.error("ERROR_NO_QUANTITY");
        } else if (!this.form.dirty && !this.form.valid) {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        } else {
            this.asset.available = true;
            this.assetService.create(this.asset).subscribe(
                () => { },
                (error) => { this.alertService.error(<any>error) },
                () => { this.router.navigate(["/dashboard"]); }
            );
        }
    }
}