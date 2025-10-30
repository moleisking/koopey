import { AlertService } from "../../../services/alert.service";
import {
  Component,
  ElementRef,
  AfterViewInit,
  ViewChild,
  Inject,
  ChangeDetectionStrategy,
} from "@angular/core";
import { Environment } from "./../../../../environments/environment";
//import { Image as ImageModel } from "../../../models/image";
import { ImageCroppedEvent } from "ngx-image-cropper";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import "hammerjs";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "crop-dialog",
    standalone: false,
  styleUrls: ["crop-dialog.css"],
  templateUrl: "crop-dialog.html",
})
export class CropDialogComponent implements AfterViewInit {
  @ViewChild("fileInput") fileInput!: ElementRef;
  imageChangeEvent: any = "";
  previewImage: any = "";
  resizeLength = Environment.Image.Height;

  constructor(
    private alertService: AlertService,
    public dialogRef: MatDialogRef<CropDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { resizeLength: number }
  ) {}

  ngAfterViewInit() {
    if (this.data.resizeLength != null || this.data.resizeLength != 0) {
      this.resizeLength = this.data.resizeLength;
    }
    this.openFileDialog();
  }

  public cancel() {
    this.dialogRef.close(null);
  }

  public close() {
    /*let image: ImageModel = new ImageModel();
    image.id = UUID.UUID();
    image.height = Environment.Image.Height;
    image.width = Environment.Image.Width;
    image.type = "png";
    image.uri = */this.previewImage;
    this.dialogRef.close(this.previewImage);
  }

  public cropperReady() {
    console.log("Crop ready");
  }

  public fileChangeListener(event: any) {
    let file: File = event.target.files[0];
    if (file.size <= Environment.Image.MaxSize) {
      this.imageChangeEvent = event;
    } else {
      this.alertService.error("ERROR_FILE_TOO_LARGE");
    }
  }

  public imageCropComplete(event: ImageCroppedEvent) {
    this.previewImage = event.base64;
  }

  public imageCropFail() {
    this.alertService.error("ERROR_CROP");
  }

  public imageLoadComplete() {}

  public openFileDialog(): void {
    let event = new MouseEvent("click", { bubbles: false });
    this.fileInput.nativeElement.dispatchEvent(event);
  }

  public setImage(uri: String) {
    if (uri) {
      this.previewImage = uri;
    }
  }
}
