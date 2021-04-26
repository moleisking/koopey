import {
  Component,
  ElementRef,
  EventEmitter,
  forwardRef,
  Input,
  Output,
  OnChanges,
  ViewChild,
} from "@angular/core";
import {
  startOfDay,
  endOfDay,
  subDays,
  subWeeks,
  subMonths,
  addDays,
  addMonths,
  addWeeks,
  endOfMonth,
  isSameDay,
  isSameMonth,
  addHours,
  addMinutes,
} from "date-fns";
import { AlertService } from "../../services/alert.service";
import { TranslateService } from "ng2-translate";
import { DateHelper } from "../../helpers/DateHelper";
import { Advert } from "../../models/advert";
import { Config } from "../../config/settings";
import { Transaction } from "../../models/transaction";
import { Wallet } from "../../models/wallet";
import "hammerjs";
import { MatRadioChange } from "@angular/material/radio";

@Component({
  selector: "advert-control-component",
  templateUrl: "advert-control.html",
})
export class AdvertControlComponent {
  private period: string = "none";
  private value: number = 0;
  @Input() wallet: Wallet = new Wallet();
  @Input() advert: Advert = new Advert();
  @Output() updateAdvert: EventEmitter<Advert> = new EventEmitter<Advert>();

  constructor(
    private alertService: AlertService,
    private translateService: TranslateService
  ) {}

  ngAfterViewInit() {}

  private onChange(event: MatRadioChange) {
    var advert: Advert = new Advert();
    advert.startTimeStamp = Date.now();
    if (event.value === "day") {
      advert.endTimeStamp = DateHelper.convertDateToEpoch(
        addDays(advert.startTimeStamp, 1)
      );
      this.value = Config.advert_day_value;
      this.updateAdvert.emit(advert);
    } else if (event.value === "week") {
      advert.endTimeStamp = DateHelper.convertDateToEpoch(
        addWeeks(advert.startTimeStamp, 1)
      );
      this.value = Config.advert_week_value;
      this.updateAdvert.emit(advert);
    } else if (event.value === "month") {
      advert.endTimeStamp = DateHelper.convertDateToEpoch(
        addMonths(advert.startTimeStamp, 1)
      );
      this.value = Config.advert_month_value;
      this.updateAdvert.emit(advert);
    } else if (event.value === "none") {
      advert.endTimeStamp = advert.startTimeStamp;
      this.value = 0;
      this.updateAdvert.emit(advert);
    }
  }

  private getTokoWalletBalance(): number {
    return this.wallet.value;
  }

  private hasCredit(): boolean {
    if (this.value <= this.wallet.value) {
      return true;
    } else {
      return false;
    }
  }
}
