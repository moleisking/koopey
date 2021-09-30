import {
  Component,
  EventEmitter,
  forwardRef,
  Input,
  Output,
} from "@angular/core";
import {
  ControlValueAccessor,
  FormControl,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
} from "@angular/forms";
import { CropDialogComponent } from "../../image/crop/crop-dialog.component";
import { Image as ImageModel } from "../../../models/image";
import { MatDialog } from "@angular/material/dialog";

@Component({
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ImageEditComponent),
      multi: true,
    },

    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => ImageEditComponent),
      multi: true,
    },
  ],
  selector: "image-edit",
  styleUrls: ["image-edit.css"],
  templateUrl: "image-edit.html",
})
export class ImageEditComponent implements ControlValueAccessor {
  @Input() image: ImageModel = new ImageModel();
  @Input() shrink: boolean = false;
  @Output() onImageChange: EventEmitter<ImageModel> = new EventEmitter<
    ImageModel
  >();

  previewImage: any = "";
  private propagateChange = (_: any) => {};
  private validateFn: any = () => {};

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

  registerOnChange(fn: any) {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any) {}

  validate(c: FormControl) {
    return this.validateFn(c);
  }

  writeValue(value: any) {
    if (value) {
      this.previewImage = value;
    }
  }
}
