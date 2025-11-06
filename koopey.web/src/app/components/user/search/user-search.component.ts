import { ChangeDetectionStrategy, Component } from "@angular/core";
import { MatExpansionModule } from "@angular/material/expansion";
import { UserFilterComponent } from "../filter/user-filter.component";
import { MatIconModule } from "@angular/material/icon";
import { UserListComponent } from "../list/user-list.component";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [MatExpansionModule, MatIconModule, UserFilterComponent, UserListComponent],
  selector: "user-search",
  standalone: true,
  styleUrls: ["user-search.css"],
  templateUrl: "user-search.html",
})
export class UserSearchComponent { }