import { Environment } from "./../../environments/environment";
import { MeasurementType } from "../models/type/MeasurementType";
import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "dimension",
    standalone: true,
})
export class DimensionPipe implements PipeTransform {
  transform(dimension: number): string {
    return this.getDimensionAndUnit(dimension);
  }

  private getDimensionAndUnit(dimension: number): string {
    let measurement = localStorage.getItem("measurement") ? localStorage.getItem("measurement") : Environment.Default.Measurement;
    if (dimension < 0) {
      if(measurement === MeasurementType.Imperial) {
        return "ft";
      } else {
        return "cm";
      }      
    } else if (measurement === MeasurementType.Imperial) {
      let inches = dimension * 0.3937008;
      if (inches < 12) {
        return inches + "inches";
      } else {
        return Math.round(inches / 12) + "ft";
      }          
    } else {
      if (dimension < 100) {
        return dimension + "cm";
      } else {
        return Math.round(dimension) + "m";
      }
    }
  }
}
