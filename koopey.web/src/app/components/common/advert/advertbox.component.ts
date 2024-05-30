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
import { AlertService } from "../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { DateHelper } from "../../../helpers/DateHelper";
import { Advert } from "../../../models/advert";
import { Environment } from "src/environments/environment";
import { Wallet } from "../../../models/wallet";
import { MatRadioChange } from "@angular/material/radio";

@Component({
  selector: "advertbox",
  styleUrls: ["advertbox.css"],
  templateUrl: "advertbox.html",
})
export class AdvertboxComponent {
  public period: string = "none";
  public value: number = 0;
  @Input() wallet: Wallet = new Wallet();
  @Input() advert: Advert = new Advert();
  @Output() updateAdvert: EventEmitter<Advert> = new EventEmitter<Advert>();

  constructor(
    private alertService: AlertService
  ) {}

  ngAfterViewInit() {}

  public onChange(event: MatRadioChange) {
    let advert: Advert = new Advert();
    advert.start = new Date();
    if (event.value === "day") {
      advert.end = new Date( advert.end.getDate() + 1); 
      this.value = Environment.Advert.DayValue;
      this.updateAdvert.emit(advert);
    } else if (event.value === "week") {
      advert.end = new Date( advert.end.getDate() + 7); 
      this.value = Environment.Advert.WeekValue;
      this.updateAdvert.emit(advert);
    } else if (event.value === "month") {
      advert.end = new Date( advert.end.getDate() + 30);         
      this.value = Environment.Advert.MonthValue;
      this.updateAdvert.emit(advert);
    } else if (event.value === "none") {
      advert.end = advert.start;
      this.value = 0;
      this.updateAdvert.emit(advert);
    }
  }

  public getTokoWalletBalance(): number {
    return this.wallet.balance;
  }

  public hasCredit(): boolean {
    if (this.value <= this.wallet.balance) {
      return true;
    } else {
      return false;
    }
  }
}
