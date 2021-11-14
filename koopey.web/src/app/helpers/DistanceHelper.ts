import { Environment } from "src/environments/environment";
import { MeasurementType } from "../models/type/MeasurementType";

export class DistanceHelper {
  public static calculate(
    latitudeA: number,
    longitudeA: number,
    latitudeB: number,
    longitudeB: number
  ): number {
    let measurementType = localStorage.getItem("measurement")
      ? localStorage.getItem("measurement")
      : Environment.Default.Measurement;
    let R = measurementType === MeasurementType.Imperial ? 3960 : 6371;
    let dLat = (latitudeB - latitudeA) * (Math.PI / 180);
    let dLon = (longitudeB - longitudeA) * (Math.PI / 180);
    let a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(latitudeA * (Math.PI / 180)) *
        Math.cos(latitudeB * (Math.PI / 180)) *
        Math.sin(dLon / 2) *
        Math.sin(dLon / 2);
    let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    let d = R * c;
    return d;
  }

  public static distanceAndUnit(
    latitudeA: number,
    longitudeA: number,
    latitudeB: number,
    longitudeB: number
  ): string {
    let distance: number = DistanceHelper.calculate(
      latitudeA,
      longitudeA,
      latitudeB,
      longitudeB
    );
    let measurementType = localStorage.getItem("measurement")
      ? localStorage.getItem("measurement")
      : Environment.Default.Measurement;
    if (measurementType === MeasurementType.Imperial) {
      let feet = distance * 3.2808;
      if (feet <= 5280) {
        return feet + " ft";
      } else {
        return Math.round(feet / 5280) + " mi";
      }
    } else {
      if (distance <= 100) {
        return "100m";
      } else if (distance > 100 && distance < 1000) {
        return Math.round(distance) + "m";
      } else {
        return Math.round(distance / 1000) + "km";
      }
    }
  }
}
