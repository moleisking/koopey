import { Component, OnInit, ElementRef, ViewChild } from "@angular/core";
import { UUID } from "angular2-uuid";
import { ImageCropperComponent, CropperSettings } from "ngx-img-cropper";
import { AlertService } from "../../../services/alert.service";
import { Environment } from "src/environments/environment";
//import { Image } from "../models/image";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "image-upload-dialog",
  templateUrl: "image-dialog.html",
  styleUrls: ["image-dialog.css"],
})
export class ImageDialogComponent implements OnInit {
  @ViewChild("cropper", undefined) cropper!: ImageCropperComponent;

  public imageObject: any;
  private imageChange: boolean = false;
  private avatarCheckboxVisible: boolean = true;
  private IMAGE_SIZE: number = 512;
  public cropperSettings: CropperSettings;
  private shrink: boolean = false;
  // private primary = false;
  // private source: string = "user";

  constructor(
    private alertService: AlertService,
    public dialogRef: MatDialogRef<ImageDialogComponent>
  ) {
    this.cropperSettings = new CropperSettings();
    this.cropperSettings.width = this.IMAGE_SIZE;
    this.cropperSettings.height = this.IMAGE_SIZE;
    this.cropperSettings.croppedWidth = this.IMAGE_SIZE;
    this.cropperSettings.croppedHeight = this.IMAGE_SIZE;
    //this.cropperSettings.canvasWidth = 400;
    //this.cropperSettings.canvasHeight = 300;
    this.cropperSettings.noFileInput = true;
    this.imageObject = {};
  }

  ngOnInit() {}

  public fileChangeListener($event: any) {
    //Uploads image and passes image to cropper, checks for size in KB
    var image: any = new Image();
    var file: File = $event.target.files[0];
    var myReader: FileReader = new FileReader();
    var that = this;

    if (file.size <= Environment.Image.MinSize) {
      myReader.onloadend = function (loadEvent: any) {
        image.src = loadEvent.target.result;
        that.cropper.setImage(image);
      };
      myReader.readAsDataURL(file);
      this.imageChange = true;
    } else {
      this.alertService.error("ERROR_FILE_TOO_LARGE");
    }
  }

  public setShrink(shrink: boolean) {
    this.shrink = shrink;
  }

  public setImage(image: any) {
    //Receives JSON image object
    if (image) {
      console.log("setImage");
      console.log(image);
      var thisimage: any = new Image();
      thisimage.src = image.uri;
      this.cropper.setImage(thisimage);
      // this.primary = image.primary;
      //  this.source = image.source;
    }
  }

  private shrinkImage(imageUri: string, width: number, height: number) {
    var sourceImage = new HTMLImageElement();
    sourceImage.src = imageUri;

    // Create a canvas with the desired dimensions
    var canvas = document.createElement("canvas");
    var ctx = canvas.getContext("2d");
    canvas.width = width;
    canvas.height = height;

    // Scale and draw the source image to the canvas
    if (ctx != null) {
      ctx.drawImage(sourceImage, 0, 0, width, height);
    }

    // Convert the canvas to a data URL in PNG format
    var data = canvas.toDataURL();
    return data;
  }

  public close() {
    //NOTE: JSON object used due to name conflict between Image object
    if (this.shrink) {
      var img1 = {
        id: UUID.UUID(),
        uri: this.shrinkImage(this.imageObject.image, 256, 256),
        width: this.IMAGE_SIZE,
        height: this.IMAGE_SIZE,
        type: "png",
      };
      console.log("imagedialog");
      console.log(img1);
      this.dialogRef.close(img1);
    } else {
      var img2 = {
        id: UUID.UUID(),
        uri: this.imageObject.image,
        width: this.IMAGE_SIZE,
        height: this.IMAGE_SIZE,
        type: "png",
      };
      console.log("imagedialog");
      console.log(img2);
      this.dialogRef.close(img2);
    }
  }

  public cancel() {
    this.dialogRef.close(null);
  }
}
