import { ChangeDetectionStrategy, Component } from "@angular/core";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
    selector: "user-search",
      standalone: false,
    styleUrls: ["user-search.css"],
    templateUrl: "user-search.html",
  })
  export class UserSearchComponent {}