import { Component, EventEmitter, Input, Output } from "@angular/core";
import { CropDialogComponent } from "../../image/crop/crop-dialog.component";
import { Image as ImageModel } from "../../../models/image";
import { MatDialog } from "@angular/material/dialog";

@Component({
  selector: "image-edit",
  styleUrls: ["image-edit.css"],
  templateUrl: "image-edit.html",
})
export class ImageEditComponent {
  @Input() image: ImageModel = new ImageModel();
  @Input() shrink: boolean = false;
  @Output() onImageChange: EventEmitter<ImageModel> = new EventEmitter<
    ImageModel
  >();

  previewImage: any = "";

  constructor(public imageCropDialog: MatDialog) {}

  public openCropDialog() {
    let dialog = this.imageCropDialog.open(CropDialogComponent, {
      height: "80%",
      width: "80%",
    });
    dialog.afterClosed().subscribe((image: ImageModel) => {
      if (image) {
        this.previewImage = image.uri;
        if (this.shrink === true) {
          image.uri = this.shrinkImage(image.uri, 256, 256);
        } else {
          this.onImageChange.emit(image);
        }
      }
    });
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
