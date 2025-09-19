import { Environment } from "./../../environments/environment";
import { MeasurementType } from "../models/type/MeasurementType";
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "distance",
    standalone: true,
})
export class DistancePipe implements PipeTransform {
  transform(distance: number): string {
    return this.getDistanceAndUnit(distance);
  }

  public getDistanceAndUnit(distance: number): string {
    let measurementType = localStorage.getItem("measurement")
      ? localStorage.getItem("measurement")
      : Environment.Default.Measurement;
    if (measurementType === MeasurementType.Imperial) {
      let feet = distance * 3.2808;
      if (feet <= 5280) {
        return feet + " ft";
      } else {
        return Math.round(feet / 5280) + " mi";
      }
    } else {
      if (distance <= 100) {
        return "100m";
      } else if (distance > 100 && distance < 1000) {
        return Math.round(distance) + "m";
      } else {
        return Math.round(distance / 1000) + "km";
      }
    }
  }
}
