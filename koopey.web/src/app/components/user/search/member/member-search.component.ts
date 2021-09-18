import {
  Component,
  ElementRef,
  OnInit,
  OnDestroy,
  ViewChild,
} from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators,
} from "@angular/forms";
import { DomSanitizer } from "@angular/platform-browser";
import { Router } from "@angular/router";
import { Observable, Subscription } from "rxjs";
import { AlertService } from "../../../../services/alert.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../../services/click.service";
import { UserService } from "../../../../services/user.service";
import { TranslateService } from "@ngx-translate/core";
import { Environment } from "../../../../../environments/environment";
import { Location } from "../../../../models/location";
import { Search } from "../../../../models/search";
import { Tag } from "../../../../models/tag";
import { User } from "../../../../models/user";

@Component({
  selector: "member-search-component",
  templateUrl: "member-search.html",
  styleUrls: ["member-search.css"],
})
/*Note* Do not use fors as it blocks location controls*/
export class MemberSearchComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  public form!: FormGroup;
  // private location: Location = new Location();
  public search: Search = new Search();
  public users: Array<User> = new Array<User>();

  public hasGPS: boolean = false;
  public searching: boolean = false;

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    public sanitizer: DomSanitizer,
    private formBuilder: FormBuilder,
    private router: Router,
    private translateService: TranslateService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      alias: [this.search.alias, [Validators.minLength(5)]],
      name: [this.search.name, [Validators.minLength(5)]],
    });
  }

  ngAfterContentInit() {
    this.clickService.createInstance(
      ActionIcon.SEARCH,
      CurrentComponent.MemberSearchComponent
    );
    this.clickSubscription = this.clickService
      .getMemberSearchClick()
      .subscribe(() => {
        this.findUsers();
      });
  }

  ngAfterViewInit() {
    //Used to calculate distance
    //var locations: Array<Location> = JSON.parse(localStorage.getItem("locations"));
    let location: Location = JSON.parse(localStorage.getItem("location")!);
    console.log(location);

    this.search.type = "service";
    if (location) {
      this.search.latitude = location.latitude;
      this.search.longitude = location.longitude;
      console.log(this.search.latitude);
      console.log(this.search.longitude);
    }
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
  }

  public handleAliasUpdate(event: any) {
    if (this.search && this.search.alias) {
      this.search.alias = this.search.alias.toLowerCase();
    }
  }

  public handleNameUpdate(event: any) {
    if (this.search && this.search.name) {
      this.search.name = this.search.name.toLowerCase();
    }
  }

  public isAliasVisible() {
    return Environment.Menu.Alias;
  }

  public isNameVisible() {
    return Environment.Menu.Name;
  }

  public findUsers() {
    console.log("findUsers()");
    if (!this.search.alias && !this.search.name) {
      this.alertService.error("ERROR_NOT_LOCATION");
    } else if (this.search.alias || this.search.name) {
      //Set progress icon
      this.searching = true;
      this.userService.search(this.search).subscribe(
        (users) => {
          this.users = users;
          this.userService.setUsers(this.users);
          console.log(users);
        },
        (error) => {
          this.alertService.error(<any>error);
        },
        () => {
          this.router.navigate(["/user/read/list"]);
        }
      );
    }
  }
}
