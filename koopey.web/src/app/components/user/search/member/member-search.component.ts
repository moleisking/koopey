import { AlertService } from "../../../../services/alert.service";
import { Component, OnInit, OnDestroy } from "@angular/core";
import { Environment } from "../../../../../environments/environment";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Search } from "../../../../models/search";
import { Subscription } from "rxjs";
import { UserService } from "../../../../services/user.service";
import { User } from "../../../../models/user";

@Component({
  selector: "member-search-component",
  styleUrls: ["member-search.css"],
  templateUrl: "member-search.html",
})
export class MemberSearchComponent implements OnInit, OnDestroy {
  public formGroup!: FormGroup;
  public search: Search = new Search();
  private searchSubscription: Subscription = new Subscription();
  public users: Array<User> = new Array<User>();
  public searching: boolean = false;

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
          console.log(users);
        },
        (error: Error) => {
          this.alertService.error(error.message);
        },
        () => {
          this.searching = false;
          this.router.navigate(["/user/list"]);
        }
      );
    }
  }
}
