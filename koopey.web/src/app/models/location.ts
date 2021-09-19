import { BaseModel } from "./baseModel";

export class Location extends BaseModel {
  public latitude: number = 0;
  public longitude: number = 0;
  public position: any = {};
  public address: string = "";
  public startTimeStamp: number = 0;
  public endTimeStamp: number = 0;

  public static convertToPosition(longitude: number, latitude: number): any {
    if (longitude != 0 && latitude != 0) {
      return {
        type: "Point",
        coordinates: [Number(longitude), Number(latitude)],
      };
    } else {
      return {};
    }
  }

  public static isEmpty(location: Location): boolean {
    if (
      location &&
      location.type &&
      location.longitude == 0 &&
      location.latitude == 0
    ) {
      return true;
    } else {
      return false;
    }
  }
}
