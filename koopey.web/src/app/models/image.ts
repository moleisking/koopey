import { UUID } from "angular2-uuid";

export class Image {
  public id: string = UUID.UUID();
  public uri: string = "";
  public type: string = "";
  public width: number = 0;
  public height: number = 0;
  public hash: string = "";
  public createTimeStamp: number = Date.now();
  public readTimeStamp: number = 0;
  public updateTimeStamp: number = 0;
  public deleteTimeStamp: number = 0;

  public static shrinkImage(
    imageUri: string,
    width: number,
    height: number
  ): any {
    //Note* careful of confussion with Image object
    // var width: number = 256, height: number = 256;
    var sourceImage = document.createElement("img");
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

  public isEmpty(): boolean {
    if (this.uri && this.type && this.uri.length <= 0) {
      return true;
    } else {
      return false;
    }
  }
}
