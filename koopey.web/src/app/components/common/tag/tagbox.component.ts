import { AlertService } from "../../../services/alert.service";
import { COMMA, ENTER } from "@angular/cdk/keycodes";
import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import { ControlValueAccessor, FormControl, NgControl } from "@angular/forms";
import { Tag } from "../../../models/tag";
import { TagService } from "../../../services/tag.service";
import { Environment } from "src/environments/environment";
import { map, startWith } from "rxjs/operators";
import { ModelHelper } from "src/app/helpers/ModelHelper";
import { Observable } from "rxjs";
import { MatAutocompleteSelectedEvent } from "@angular/material/autocomplete";

@Component({
  selector: "tagbox",
  styleUrls: ["tagbox.css"],
  templateUrl: "tagbox.html",
})
export class TagboxComponent implements OnInit, ControlValueAccessor {
  @ViewChild("tagListElement") tagListElement!: ElementRef;
  @ViewChild("tagInputElement") tagInputElement!: ElementRef;
  @Input() chosenTags: Array<Tag> = new Array<Tag>();
  @Input() removable: Boolean = true;
  @Input() selectable: Boolean = true;
  @Output() tagUpdated = new EventEmitter();

  private tagOptions: Array<Tag> = new Array<Tag>();
  public tagInputControl: FormControl = new FormControl();
  public filteredTags: Observable<Array<Tag>>;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;

  private onChange = (option: any) => {};
  private onTouched = Function;

  constructor(
    private alertService: AlertService,
    private ngControl: NgControl,
    private tagService: TagService
  ) {
    this.getCacheTagOptions();

    this.filteredTags = this.tagInputControl.valueChanges.pipe(
      startWith(null),
      map((tagName: string | null) =>
        tagName ? this.filterTagsByName(tagName) : this.tagOptions
      )
    );

    ngControl.valueAccessor = this;
  }

  ngOnInit() {}

  filterTagsByName(value: string): Array<Tag> {
    const filterValue = value.length > 0 ? value.toLowerCase() : "";
    return this.tagOptions.filter((tag) =>
      this.getTagText(tag).includes(filterValue)
    );
  }

  /* public findTagByText(value: string): Tag {
    for (let i = 0; i < this.tagOptions.length; i++) {
      if (value == this.getTagText(this.tagOptions[i])) {
        return this.tagOptions[i];
      }
    }
    return new Tag();
  }*/

  /* public findTagById(value: string): Tag {
    for (let i = 0; i < this.tagOptions.length; i++) {
      if (value == this.tagOptions[i].id) {
        return this.tagOptions[i];
      }
    }
    return new Tag();
  }*/

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
    } else {
      this.tagInputControl.setValue(null);
      this.tagInputElement.nativeElement.value = "";
    }
  }

  writeValue(value: any) {
    if (value) {
      this.chosenTags = value;
    }
  }
}
