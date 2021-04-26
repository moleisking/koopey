import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "epoch-to-date",
})
export class EpochToDatePipe implements PipeTransform {
  transform(epoch: number): Date {
    return this.epochToDate(epoch);
  }

  private epochToDate(epoch: number): Date {
    return new Date(epoch);
  }
}
