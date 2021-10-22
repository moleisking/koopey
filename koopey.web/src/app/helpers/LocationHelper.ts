export class LocationHelper {
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
}
