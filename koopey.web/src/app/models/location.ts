import { Audit } from "./base/audit";
import { LocationType } from "./type/LocationType";

export class Location extends Audit {
  constructor() {
    super();
    super.type = LocationType.Residence;
  }
  public altitude: number = 0;
  public latitude: number = 0;
  public longitude: number = 0;
}
