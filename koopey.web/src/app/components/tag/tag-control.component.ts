import {
  Component,
  ElementRef,
  EventEmitter,
  forwardRef,
  Input,
  OnChanges,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import {
  ControlValueAccessor,
  FormGroup,
  FormBuilder,
  FormControl,
  NG_VALUE_ACCESSOR,
  NG_VALIDATORS,
  Validators,
  Validator,
} from "@angular/forms";
import { Tag } from "../../models/tag";
import { TagService } from "../../services/tag.service";
import { AlertService } from "../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { Config } from "../../config/settings";
import { validateEvents } from "angular-calendar/modules/common/util";

@Component({
  selector: "tag-control-component",
  templateUrl: "tag-control.html",
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TagControlComponent),
      multi: true,
    },
  ],
})
//http://learnangular2.com/outputs/
export class TagControlComponent
  implements OnInit, ControlValueAccessor, OnChanges {
  @ViewChild("tagElement") tagElement!: ElementRef;
  @Input() selectedTags: Array<Tag> = new Array<Tag>();
  @Input() readOnly: Boolean = true;
  @Output() tagUpdated = new EventEmitter();

  private tags: Tag[] = [];
  private tagCtrl!: FormControl;
  private filteredTags: any;

  private propagateChange = (_: any) => {};
  validateFn: any = () => {};

  constructor(
    private alertService: AlertService,
    private formBuilder: FormBuilder,
    private tagService: TagService,
    private translateService: TranslateService
  ) {
    this.getTags();
  }

  ngOnInit() {
    //Needed for case when selectedTags are null
    if (!this.selectedTags) {
      this.selectedTags = [];
    }
    //Note* Call order is important
    //Start tag control listiners
    if (!this.readOnly) {
      console.log("Not ReadOnly");
      this.tagCtrl = new FormControl();
      /*this.filteredTags = this.tagCtrl.valueChanges.
        .startWith(null)
        .map((name) => this.filterTags(name));*/
    }
  }

  ngOnChanges(inputs: any) {
    if (!this.readOnly) {
      if (inputs) {
        //this.validTag = this.selectedTags;
        this.validateFn = this.selectedTags ? true : false;
        this.propagateChange(this.selectedTags);
      } else {
        console.log("address ngOnChanges custom validator here else");
      }
    }
  }

  //For ControlValueAccessor interface
  writeValue(value: any) {
    if (!this.readOnly) {
      console.log("tag writeValue");
      if (value) {
        //if (value !== this.address) {
        this.selectedTags = value;
      }
    }
  }

  //For ControlValueAccessor interface
  registerOnChange(fn: any) {
    if (!this.readOnly) {
      console.log("tag registerOnChange");
      this.propagateChange = fn;
    }
  }

  //For ControlValueAccessor interface
  registerOnTouched(fn: any) {}

  filterTags(value: string) {
    if (!this.readOnly) {
      if (value) {
        return value
          ? this.tags.filter((tag) =>
              new RegExp(`^${value}`, "gi").test(this.getTagText(tag))
            )
          : this.tags;
      }
    }
    return null;
  }

  public getTagText(tag: Tag): string {
    var language = String(Config.default_language);
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

  private getTags() {
    this.tagService.readTags().subscribe(
      (tags: Array<Tag>) => {
        //Note* this "text" field needs to be set for TagView
        var language = String(Config.default_language);
        this.tags = tags;
        //Set tags.text field for every item
        for (var i = 0; i < this.tags.length; i++) {
          this.getTagText(this.tags[i]);
        }
      },
      (error) => {
        console.log("getTags error:" + error);
      }
    );
  }

  private chipRemove(event: any, tag: Tag) {
    //Note* Event x and y cheked as sometimes triggers click unintentionally
    if (!this.readOnly) {
      if (event.x != 0 && event.y != 0) {
        console.log("chipRemove called");
        var temp: Array<Tag> = [];
        for (var i = 0; i < this.selectedTags.length; i++) {
          if (tag.id != this.selectedTags[i].id) {
            temp.push(this.selectedTags[i]);
          }
        }
        this.selectedTags = temp;
        // this.writeValue(temp);
        this.tagUpdated.emit(this.selectedTags);
      }
    }
  }

  private chipAdd(event: any, tag: Tag) {
    if (!this.readOnly) {
      //Check for duplicates
      var tag = this.findTagById(tag.id);
      var duplicate = false;
      for (var i = 0; i < this.selectedTags.length; i++) {
        if (tag.id == this.selectedTags[i].id) {
          duplicate = true;
          break;
        }
      }
      //No duplicate found so add tag
      if (duplicate == false) {
        // console.log("duplicate tag not found")
        this.selectedTags.push(tag);
        // this.writeValue( this.selectedTags);
        this.tagUpdated.emit(this.selectedTags);
      }
      this.tagCtrl.setValue("");
    }
  }

  private findTagByText(value: string): Tag {
    for (var i = 0; i < this.tags.length; i++) {
      if (value == this.getTagText(this.tags[i])) {
        return this.tags[i];
      }
    }
    return new Tag();
  }

  private findTagById(value: string): Tag {
    for (var i = 0; i < this.tags.length; i++) {
      if (value == this.tags[i].id) {
        return this.tags[i];
      }
    }
    return new Tag();
  }
}
