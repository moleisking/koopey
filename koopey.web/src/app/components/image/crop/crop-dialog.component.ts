import { AlertService } from "../../../services/alert.service";
import { Component, ElementRef, AfterViewInit, ViewChild } from "@angular/core";
import { Environment } from "src/environments/environment";
import { Image as ImageModel } from "../../../models/image";
import { ImageCroppedEvent } from "ngx-image-cropper";
import { MatDialogRef } from "@angular/material/dialog";
import { UUID } from "angular2-uuid";
import { NgControl } from "@angular/forms";

@Component({
  selector: "crop-dialog",
  styleUrls: ["crop-dialog.css"],
  templateUrl: "crop-dialog.html",
})
export class CropDialogComponent implements AfterViewInit {
  @ViewChild("fileInput") fileInput!: ElementRef;
  imageChangeEvent: any = "";
  previewImage: any = "";

  constructor(
    private alertService: AlertService,
    public dialogRef: MatDialogRef<CropDialogComponent>
  ) {}

  ngAfterViewInit() {
    this.openFileDialog();
  }

  public cancel() {
    this.dialogRef.close(null);
  }

  public close() {
    let image: ImageModel = new ImageModel();
    image.id = UUID.UUID();
    image.height = Environment.Image.Height;
    image.width = Environment.Image.Width;
    image.type = "png";
    image.uri = this.previewImage;
    this.dialogRef.close(image);
  }

  public cropperReady() {
    console.log("Crop ready");
  }

  public imageCropComplete(event: ImageCroppedEvent) {
    this.previewImage = event.base64;
  }

  public imageCropFail() {
    this.alertService.error("ERROR_CROP");
  }

  public imageLoadComplete() {
    console.log("Crop complete");
  }

  public setImage(image: ImageModel) {
    if (image) {
      this.previewImage = image.uri;
    }
  }

  public fileChangeListener(event: any) {
    let file: File = event.target.files[0];
    if (file.size <= Environment.Image.MaxSize) {
      this.imageChangeEvent = event;
    } else {
      this.alertService.error("ERROR_FILE_TOO_LARGE");
    }
  }

  public openFileDialog(): void {
    console.log("openFileDialog()");
    let event = new MouseEvent("click", { bubbles: false });
    this.fileInput.nativeElement.dispatchEvent(event);
  }
}
