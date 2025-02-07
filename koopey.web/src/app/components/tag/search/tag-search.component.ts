import { AlertService } from "../../../services/alert.service";
import { BaseComponent } from "../../../components/base/base.component";
import { Component, OnDestroy, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Search } from "../../../models/search";
import { SearchService } from "../../../services/search.service";
import { Subscription } from "rxjs";
import { Tag } from "../../../models/tag";
import { TagService } from "../../../services/tag.service";

@Component({
  selector: "tag-search-component",
  styleUrls: ["tag-search.css"],
  templateUrl: "tag-search.html",
})
export class TagSearchComponent extends BaseComponent
  implements OnInit, OnDestroy {
  public busy: boolean = false;
  public formGroup!: FormGroup;
  public search: Search = new Search();
  private searchSubscription: Subscription = new Subscription();

  constructor(
    private alertService: AlertService,
    private formBuilder: FormBuilder,
    private router: Router,
    public sanitizer: DomSanitizer,
    private searchService: SearchService,
    private tagService: TagService
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      tags: [this.search.tags, [Validators.required]],
    });
  }

  ngOnDestroy() {
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  public find() {
    let search: Search = this.formGroup.getRawValue();
    if (this.search) {
      this.tagService.readTagsByName(search).subscribe(
        (tags: Array<Tag>) => {
          this.tagService.setTags(tags);
          this.searchService.setSearch(search);
        },
        (error: Error) => {
          this.alertService.error(error.message);
        },
        () => {
          this.router.navigate(["/tag/list"]);
        }
      );
    }
  }
}
