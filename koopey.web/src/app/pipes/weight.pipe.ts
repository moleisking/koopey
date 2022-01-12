import { Environment } from "src/environments/environment";
import { MeasurementType } from "../models/type/MeasurementType";
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "weight",
})
export class WeightPipe implements PipeTransform {
  transform(weight: number): string {
    return this.getWeightAndUnit(weight);
  }

  private getWeightAndUnit(weight: number): string {
    let measurement = localStorage.getItem("measurement")
      ? localStorage.getItem("measurement")
      : Environment.Default.Measurement;
    if (measurement === MeasurementType.Imperial) {
      return Math.round(weight * 2.20462262185) + "lbs";
    } else {
      return weight + "kg";
    }
  }
}
