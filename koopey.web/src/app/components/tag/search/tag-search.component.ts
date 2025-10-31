import { AlertService } from "../../../services/alert.service";
import { ChangeDetectionStrategy, Component, Inject, inject, OnDestroy, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Search } from "../../../models/search";
import { SearchService } from "../../../services/search.service";
import { Subscription } from "rxjs";
import { Tag } from "../../../models/tag";
import { TagService } from "../../../services/tag.service";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: "tag-search-component",
  standalone: false,
  styleUrls: ["tag-search.css"],
  templateUrl: "tag-search.html",
})
export class TagSearchComponent
  implements OnInit, OnDestroy {
  public busy: boolean = false;
  public formGroup!: FormGroup;
  public search: Search = new Search();
  private searchSubscription: Subscription = new Subscription();

  private alertService = inject(AlertService);
  private formBuilder = inject(FormBuilder);
  private router = inject(Router);
  public sanitizer = inject(DomSanitizer);
  private searchService = inject(SearchService);
  private tagService = inject(TagService);

 /* constructor(@Inject(DomSanitizer) sanitizer: DomSanitizer) {
    super(sanitizer);
  }*/
  
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
