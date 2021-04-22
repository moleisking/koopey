import { UUID } from "angular2-uuid";

export class Location {
  public id: string = UUID.UUID();
  public hash: string = "";
  public type: string = "current"; // current, abode
  public latitude: number = 0;
  public longitude: number = 0;
  public position: any = {};
  public address: string = "";
  public startTimeStamp: number = 0;
  public endTimeStamp: number = 0;
  public createTimeStamp: number = Date.now();
  public readTimeStamp: number = 0;
  public updateTimeStamp: number = 0;
  public deleteTimeStamp: number = 0;

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

  public static convertDistanceToKilometers(distance: number): string {
    if (distance <= 100) {
      return "100m";
    } else if (distance > 100 && distance < 1000) {
      return Math.round(distance) + "m";
    } else {
      return Math.round(distance / 1000) + "km";
    }
  }

  public static convertDistanceToMiles(distance: number): string {
    if (distance <= 5280) {
      //5280ft in a mile
      return distance + " ft";
    } else {
      return Math.round(distance / 5280) + " mi";
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
