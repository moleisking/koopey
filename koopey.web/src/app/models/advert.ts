import { Audit } from "./base/audit";

export class Advert extends Audit {
  public start: Date = new Date("1900-01-01");
  public end: Date = new Date("1900-01-01");
}
