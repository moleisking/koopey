import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "distance-to-miles",
})
export class DistanceToMilesPipe implements PipeTransform {
  transform(distance: number): string {
    return this.convertDistanceToMiles(distance);
  }

  public convertDistanceToMiles(distance: number): string {
    if (distance <= 5280) {
      //5280ft in a mile
      return distance + " ft";
    } else {
      return Math.round(distance / 5280) + " mi";
    }
  }
}
