import {
  Component,
  EventEmitter,
  forwardRef,
  Input,
  Output,
  OnInit,
} from "@angular/core";
import {
  ControlValueAccessor,
  FormControl,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
} from "@angular/forms";
import { GdprService } from "../../services/gdpr.service";
import { MatRadioChange } from "@angular/material/radio";

@Component({
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => GdprComponent),
      multi: true,
    },

    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => GdprComponent),
      multi: true,
    },
  ],
  selector: "gdpr-component",
  styleUrls: ["gdpr.css"],
  templateUrl: "gdpr.html",
})
export class GdprComponent implements ControlValueAccessor, OnInit {
  @Input() readOnly: boolean = false;
  @Input() consent: boolean = false;
  @Output() updateGdpr: EventEmitter<boolean> = new EventEmitter<boolean>();
  public content: String = "";

  private propagateChange = (_: any) => {};
  private validateFn: any = () => {};

  constructor(public gdprService: GdprService) {}

  ngOnInit() {
    this.getContent();
  }

  public getContent() {
    this.gdprService.readGdpr().subscribe(
      (content: String) => {
        this.content = content;
      },
      (error: Error) => {
        console.log(error);
      }
    );
  }

  public onChange(event: MatRadioChange) {
    if (event.value === "agree") {
      this.consent = true;
      this.updateGdpr.emit(true);
    } else if (event.value === "disagree") {
      this.consent = false;
      this.updateGdpr.emit(false);
    }
  }

  public isReadOnly() {
    return this.readOnly;
  }

  registerOnChange(fn: any) {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any) {}

  validate(c: FormControl) {
    return this.validateFn(c);
  }

  writeValue(value: any) {
    if (value) {
      this.consent = value;
    }
  }
}
