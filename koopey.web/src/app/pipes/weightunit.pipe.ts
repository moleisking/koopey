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
    let measurementType = localStorage.getItem("measurementType")
      ? localStorage.getItem("measurementType")
      : Environment.Default.MeasurementType;
    if (measurementType === MeasurementType.Imperial) {
      return "lbs";
    } else {
      return "kg";
    }
  }
}
