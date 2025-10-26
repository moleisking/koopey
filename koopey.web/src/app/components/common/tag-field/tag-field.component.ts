import {
  AbstractControl,
  ControlValueAccessor,
  FormControl,
  FormsModule,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule,
  ValidationErrors,
  Validator,
} from "@angular/forms";
import { AlertService } from "../../../services/alert.service";
import { COMMA, ENTER } from "@angular/cdk/keycodes";
import {
  ChangeDetectionStrategy,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  Output,
  ViewChild,
  forwardRef,
} from "@angular/core";
import { Environment } from "./../../../../environments/environment";
import { Tag } from "../../../models/tag";
import { TagService } from "../../../services/tag.service";
import { map, startWith } from "rxjs/operators";
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from "@angular/material/autocomplete";
import { ModelHelper } from "./../../../helpers/ModelHelper";
import { Observable } from "rxjs";
import { AsyncPipe } from "@angular/common";
import { MatChipsModule } from "@angular/material/chips";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
 AsyncPipe,
    FormsModule,
    MatAutocompleteModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TagFieldComponent),
      multi: true
    }
  ],
  selector: "tag-field",
  standalone: true,
  styleUrls: ["tag-field.css"],
  templateUrl: "tag-field.html"

})
export class TagFieldComponent implements ControlValueAccessor, Validator {

  @ViewChild("tagInputElement") tagInputElement!: ElementRef<HTMLInputElement>;
  @ViewChild("tagListElement") tagListElement!: ElementRef;
  @Input() chosenTags: Array<Tag> = new Array<Tag>();
  @Input() removable: boolean = true;
  @Input() selectable: boolean = true;
  @Output() tagUpdated = new EventEmitter();

  private tagOptions: Array<Tag> = new Array<Tag>();
  public tagInputControl: FormControl = new FormControl();
  public filteredTags: Observable<Array<Tag>>;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;

  private onChange = (option: any) => { };
  private onTouched = Function;

  constructor(
    private alertService: AlertService,
    //  @Self() private ngControl: NgControl,
    private tagService: TagService
  ) {
    this.getCacheTagOptions();

    this.filteredTags = this.tagInputControl.valueChanges.pipe(
      startWith(null),
      map((tagName: string | null) =>
        tagName ? this.filterTagsByName(tagName) : this.tagOptions
      )
    );

    //   ngControl.valueAccessor = this;

  }

  filterTagsByName(value: string): Array<Tag> {
    const filterValue = value.length > 0 ? value.toLowerCase() : "";
    return this.tagOptions.filter((tag) =>
      this.getTagText(tag).includes(filterValue)
    );
  }

  private getCacheTagOptions() {
    let tags: Array<Tag> = JSON.parse(localStorage.getItem("tags")!);
    if (tags != null && tags.length > 0) {
      this.tagOptions = tags;
    } else {
      this.tagService.readTags().subscribe(
        (tags: Array<Tag>) => {
          this.tagOptions = tags;
          localStorage.setItem("tags", JSON.stringify(tags));
        },
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
    }
  }

  public getTagText(tag: Tag): string {
    let language =
      localStorage.getItem("language") != null
        ? localStorage.getItem("language")
        : Environment.Default.Language;
    if (language == "de") {
      return tag.de;
    } else if (language == "cn") {
      return tag.cn;
    } else if (language == "en") {
      return tag.en;
    } else if (language == "es") {
      return tag.es;
    } else if (language == "fr") {
      return tag.fr;
    } else if (language == "it") {
      return tag.it;
    } else if (language == "pt") {
      return tag.pt;
    } else if (language == "zh") {
      return tag.zh;
    } else {
      return tag.en;
    }
  }

  private isDuplicate(tag: Tag) {
    return ModelHelper.contains(this.chosenTags, tag);
  }

  registerOnChange(fn: any) {
    this.onChange = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouched = fn;
  }

  public remove(tag: Tag) {
    if (this.removable) {
      this.chosenTags = this.chosenTags.filter((t: Tag) => t.id != tag.id);
      this.onChange(this.chosenTags);
      // this.ngControl.control?.updateValueAndValidity();
      this.tagUpdated.emit(this.chosenTags);
    }
  }

  public select(event: MatAutocompleteSelectedEvent): void {
    let tag: Tag = event.option.value;
    if (!this.isDuplicate(tag)) {
      this.chosenTags.push(tag);
      this.tagInputElement.nativeElement.value = "";
      this.tagInputControl.setValue(null);
      this.tagUpdated.emit(this.chosenTags);
      //    this.ngControl.control?.updateValueAndValidity();
    } else {
      this.tagInputControl.setValue(null);
      this.tagInputElement.nativeElement.value = "";
    }
  }

  validate(control: AbstractControl): ValidationErrors | null {
    const tags = new Array<Tag>(control.value);
    return control.value && tags.length > 0 ? null : { invalid: true };
  }

  writeValue(value: any) {
    if (value) {
      this.chosenTags = value;
    }
  }
}
