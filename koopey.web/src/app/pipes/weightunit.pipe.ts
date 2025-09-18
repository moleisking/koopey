import { Environment } from "./../../environments/environment";
import { MeasurementType } from "../models/type/MeasurementType";
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "weightunit",
})
export class WeightUnitPipe implements PipeTransform {
  public transform(weight: number): string {
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
