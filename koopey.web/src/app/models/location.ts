import { BaseModel } from "./baseModel";
import { LocationType } from "./type/LocationType";

export class Location extends BaseModel {
  constructor() {
    super();
    super.type = LocationType.Abode;
  }
  public altitude: number = 0;
  public latitude: number = 0;
  public longitude: number = 0;
  public position: any = {};
}
