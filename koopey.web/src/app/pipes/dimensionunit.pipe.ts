import { Environment } from "../../environments/environment";
import { MeasurementType } from "../models/type/MeasurementType";
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "dimensionunit",
})
export class DimensionUnitPipe implements PipeTransform {
  transform(): string {
    return this.getDimensionUnit();
  }

  private getDimensionUnit(): string {
    let measurement = localStorage.getItem("measurement")
      ? localStorage.getItem("measurement")
      : Environment.Default.Measurement;
    if (measurement === MeasurementType.Imperial) {
      return "inches";
    } else {
      return "cm";
    }
  }
}
