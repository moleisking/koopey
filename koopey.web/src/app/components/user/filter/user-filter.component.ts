import { AlertService } from "../../../services/alert.service";
import { Component, OnInit, OnDestroy, Output, EventEmitter } from "@angular/core";
import { Environment } from "../../../../environments/environment";
import { FormGroup, FormBuilder, Validators, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { Search } from "../../../models/search";
import { Subscription } from "rxjs";
import { UserService } from "../../../services/user.service";
import { User } from "../../../models/user";
import { TranslateModule } from "@ngx-translate/core";
import { MatIconModule } from "@angular/material/icon";

@Component({
  selector: "user-filter",
  styleUrls: ["user-filter.css"],
  templateUrl: "user-filter.html",
  imports: [FormsModule,MatIconModule,ReactiveFormsModule,TranslateModule],
  standalone: true
})
export class UserFilterComponent implements OnInit, OnDestroy {
  public formGroup!: FormGroup;
  public search: Search = new Search();
  private searchSubscription: Subscription = new Subscription();
  public users: Array<User> = new Array<User>();
  public searching: boolean = false;
  @Output() onSearch: EventEmitter<any> = new EventEmitter<any>();

  constructor(
    private alertService: AlertService,
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      alias: [this.search.alias, [Validators.minLength(5)]],
      name: [this.search.name, [Validators.minLength(5)]],
    });
  }

  ngOnDestroy() {
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  public isAliasVisible() {
    return Environment.Menu.Alias;
  }

  public isNameVisible() {
    return Environment.Menu.Name;
  }

  public find() {
    if (!this.search.alias && !this.search.name) {
      this.alertService.error("ERROR_NOT_LOCATION");
    } else if (this.search.alias || this.search.name) {
      this.searching = true;
      this.userService.search(this.search).subscribe(
        (users: Array<User>) => {
          this.users = users;
          this.userService.setUsers(this.users);
          this.onSearch.emit();
          console.log(users);
        },
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
    }
  }
}
