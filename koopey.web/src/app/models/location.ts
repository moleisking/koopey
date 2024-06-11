import { Base } from "./base/base";
import { LocationType } from "./type/LocationType";

export class Location extends Base {
  constructor() {
    super();
    this.type = LocationType.Residence;
  }
  public altitude: number = 0;
  public latitude: number = 0;
  public longitude: number = 0;
}
