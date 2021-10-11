import { Pipe, PipeTransform } from "@angular/core";
import { Environment } from "src/environments/environment";
import { MeasurementType } from "../models/type/MeasurementType";

@Pipe({
  name: "distanceunit",
})
export class DistanceUnitPipe implements PipeTransform {
  transform(): string {
    return this.getDistanceUnit();
  }

  public getDistanceUnit(): string {
    let measurementType = localStorage.getItem("measurementType")
      ? localStorage.getItem("measurementType")
      : Environment.Default.MeasurementType;
    if (measurementType === MeasurementType.Imperial) {
      return " ft";
    } else {
      return "m";
    }
  }
}
