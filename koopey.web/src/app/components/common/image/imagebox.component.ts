import { Component, EventEmitter, Input, Output } from "@angular/core";
import { ControlValueAccessor, FormControl, NgControl } from "@angular/forms";
import { CropDialogComponent } from "../crop/crop-dialog.component";
import { Environment } from "src/environments/environment";
import { Image as ImageModel } from "../../../models/image";
import { MatDialog } from "@angular/material/dialog";

@Component({
  selector: "imagebox",
  styleUrls: ["imagebox.css"],
  templateUrl: "imagebox.html",
})
export class ImageboxComponent implements ControlValueAccessor {
  @Input() image: ImageModel = new ImageModel();
  @Input() resizeLength: number = Environment.Image.Height;
  @Output() onImageChange: EventEmitter<ImageModel> = new EventEmitter<
    ImageModel
  >();
  public formControl = new FormControl("");
  public previewImage: any = "";
  private onTouched = Function;
  private onChange = (option: String) => {};

  constructor(public ngControl: NgControl, public imageCropDialog: MatDialog) {
    ngControl.valueAccessor = this;
  }

  public openCropDialog() {
    let dialog = this.imageCropDialog.open(CropDialogComponent, {
      height: "80%",
      width: "80%",
      data: {
        resizeLength: this.resizeLength,
      },
    });
    dialog.afterClosed().subscribe((image: ImageModel) => {
      if (image) {
        this.previewImage = image.uri;
        this.onChange(image.uri);
        this.onTouched();
        this.onImageChange.emit(image);
      }
    });
  }

  registerOnChange(fn: any) {
    this.onChange = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouched = fn;
  }

  writeValue(value: any) {
    if (value) {
      this.previewImage = value;
    }
  }
}
