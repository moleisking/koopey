import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "distancetokilometers",
})
export class DistanceToKilometersPipe implements PipeTransform {
  transform(distance: number): string {
    return this.convertDistanceToKilometers(distance);
  }

  private convertDistanceToKilometers(distance: number): string {
    if (distance <= 100) {
      return "100m";
    } else if (distance > 100 && distance < 1000) {
      return Math.round(distance) + "m";
    } else {
      return Math.round(distance / 1000) + "km";
    }
  }
}
