import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ElementRef,
  ViewChild,
} from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { UUID } from "angular2-uuid";
import { ImageCroppedEvent } from "ngx-image-cropper";
import { AlertService } from "../../../services/alert.service";
import { Environment } from "src/environments/environment";
import { Image as ImageModel } from "../../../models/image";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "image-edit",
  templateUrl: "image-edit.html",
  styleUrls: ["image-edit.css"],
})
export class ImageDialogComponent implements OnInit {
  //@ViewChild("cropper") cropper!: ImageCropperComponent;
  @Input() uri: string = "";
  @Output() onImageChange: EventEmitter<ImageModel> = new EventEmitter<
    ImageModel
  >();

  public imageObject: any;
  // private imageChange: boolean = false;
  public formGroup!: FormGroup;
  //private avatarCheckboxVisible: boolean = true;

  //public configuration: CropperSettings;
  private shrink: boolean = false;
  private filePath: string;
  // private primary = false;
  // private source: string = "user";

  imgChangeEvt: any = "";
  cropImgPreview: any = "";

  cropImg(e: ImageCroppedEvent) {
    this.cropImgPreview = e.base64;
  }

  constructor(
    private alertService: AlertService,
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<ImageDialogComponent>
  ) {
    /* this.configuration = new CropperSettings();
    this.configuration.height = Environment.Image.Height;
    this.configuration.width = Environment.Image.Width;
    this.configuration.croppedHeight = Environment.Image.Height;
    this.configuration.croppedWidth = Environment.Image.Width;

    //this.configuration.canvasWidth = 400;
    //this.configuration.canvasHeight = 300;
    this.configuration.noFileInput = true;*/
    this.imageObject = {};
    this.filePath = "";
  }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      imageFilePath: [
        this.filePath,
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(300),
        ],
      ],
    });
  }

  imageCropped(event: ImageCroppedEvent) {
    this.cropImgPreview = event.base64;
  }
  imageLoadComplete() {
    console.log("Crop complete");
    // show cropper
  }
  cropperReady() {
    console.log("Crop ready");
    // cropper ready
  }
  imageCropFail() {
    // show message
    console.log("Crop failed");
  }

  public buildImage(src: any): ImageModel {
    let image: ImageModel = new ImageModel();
    image.id = UUID.UUID();
    image.height = Environment.Image.Height;
    image.width = Environment.Image.Width;
    image.type = "png";
    image.uri = src;

    console.log("buildImage");
    console.log(image);
    return image;
  }

  /* public cancel() {
    this.dialogRef.close(null);
  }

  public close() {
    //NOTE: JSON object used due to name conflict between Image object
    let image: ImageModel = new ImageModel();
    image.id = UUID.UUID();
    image.height = Environment.Image.Height;
    image.width = Environment.Image.Width;
    image.type = "png";

    if (this.shrink) {
      image.uri = this.shrinkImage(this.imageObject.image, 256, 256);
    } else {
      image.uri = this.imageObject.image;
    }

    console.log("imagedialog");
    console.log(image);
    this.dialogRef.close(image);
  }*/

  public fileChangeListener(event: any) {
    this.imgChangeEvt = event;
    /*let file: File = $event.target.files[0];
    let fileReader: FileReader = new FileReader();
    let htmlImageElement: any = new Image();
    let that = this;
    console.log("fileChangeListener");
    if (file.size <= Environment.Image.MaxSize) {
      fileReader.onloadend = (e: any) => {
        htmlImageElement.src = e.target.result;
        console.log(htmlImageElement.src);
        that.cropper.setImage(htmlImageElement);

        let image: ImageModel = this.buildImage(htmlImageElement.src);
        this.setImage(image);
        this.onImageChange.emit(image);
      };
      fileReader.readAsDataURL(file);
      //this.imageChange = true;
    } else {
      this.alertService.error("ERROR_FILE_TOO_LARGE");
    }*/
  }

  /* public setImage(image: ImageModel) {
    if (image) {
      let htmlImageElement: any = new Image();
      htmlImageElement.src = image.uri;
      this.cropper.setImage(htmlImageElement);
    }
  }*/

  public setShrink(shrink: boolean) {
    this.shrink = shrink;
  }

  private shrinkImage(imageUri: string, width: number, height: number) {
    let sourceImage = new HTMLImageElement();
    sourceImage.src = imageUri;

    // Create a canvas with the desired dimensions
    let canvas = document.createElement("canvas");
    let ctx = canvas.getContext("2d");
    canvas.width = width;
    canvas.height = height;

    // Scale and draw the source image to the canvas
    if (ctx != null) {
      ctx.drawImage(sourceImage, 0, 0, width, height);
    }

    // Convert the canvas to a data URL in PNG format
    let data = canvas.toDataURL();
    return data;
  }
}
