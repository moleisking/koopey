import { Environment } from "./../../environments/environment";
import { MeasurementType } from "../models/type/MeasurementType";
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "distanceunit",
})
export class DistanceUnitPipe implements PipeTransform {
  transform(): string {
    return this.getDistanceUnit();
  }

  public getDistanceUnit(): string {
    let measurement = localStorage.getItem("measurement")
      ? localStorage.getItem("measurement")
      : Environment.Default.Measurement;
    if (measurement === MeasurementType.Imperial) {
      return " ft";
    } else {
      return "m";
    }
  }
}
