import { Pipe, PipeTransform } from "@angular/core";
import { Environment } from "src/environments/environment";
import { MeasurementType } from "../models/type/MeasurementType";

@Pipe({
  name: "dimensionunit",
})
export class DimensionUnitPipe implements PipeTransform {
  transform(): string {
    return this.getDimensionUnit();
  }

  private getDimensionUnit(): string {
    let measurementType = localStorage.getItem("measurementType")
      ? localStorage.getItem("measurementType")
      : Environment.Default.MeasurementType;
    if (measurementType === MeasurementType.Imperial) {
      return "inches";
    } else {
      return "cm";
    }
  }
}
