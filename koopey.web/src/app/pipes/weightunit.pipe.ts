import { Pipe, PipeTransform } from "@angular/core";
import { Environment } from "src/environments/environment";
import { MeasurementType } from "../models/type/MeasurementType";

@Pipe({
  name: "weightunit",
})
export class WeightUnitPipe implements PipeTransform {
  transform(): string {
    return this.getWeightUnit();
  }

  private getWeightUnit(): string {
    let measurementType = localStorage.getItem("measurement")
      ? localStorage.getItem("measurement")
      : Environment.Default.Measurement;
    if (measurementType === MeasurementType.Imperial) {
      return "lbs";
    } else {
      return "kg";
    }
  }
}
