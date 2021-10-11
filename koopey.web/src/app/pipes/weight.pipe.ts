import { Pipe, PipeTransform } from "@angular/core";
import { Environment } from "src/environments/environment";
import { MeasurementType } from "../models/type/MeasurementType";

@Pipe({
  name: "weight",
})
export class WeightPipe implements PipeTransform {
  transform(weight: number): string {
    return this.getWeightAndUnit(weight);
  }

  private getWeightAndUnit(weight: number): string {
    let measurementType = localStorage.getItem("measurementType")
      ? localStorage.getItem("measurementType")
      : Environment.Default.MeasurementType;
    if (measurementType === MeasurementType.Imperial) {
      return Math.round(weight * 2.20462262185) + "lbs";
    } else {
      return weight + "kg";
    }
  }
}
