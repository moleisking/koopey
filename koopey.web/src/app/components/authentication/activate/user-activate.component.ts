import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { AuthenticationService } from "../../../services/authentication.service";
import { UserService } from "../../../services/user.service";
import { AlertService } from "../../../services/alert.service";
import { User } from "../../../models/user";

@Component({
  selector: "user-activate-component",
      standalone: false,
  templateUrl: "user-activate.html",
})
export class UserActivateComponent implements OnInit, OnDestroy {
  private user: User = new User();
  public userAuthenticated: boolean = false;

  constructor(
    private authenticateService: AuthenticationService,
    private route: ActivatedRoute,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    this.user.secret = window.location.href.substr(
      window.location.href.lastIndexOf("/") + 1
    );
  }

  ngAfterContentInit() {
    this.authenticateService.activate(this.user).subscribe(
      (data) => {
        this.userAuthenticated = true;
        localStorage.setItem("verify", "true");
      },
      (error) => {
        this.userAuthenticated = false;
        localStorage.setItem("verify", "false");
      },
      () => {}
    );
  }

  ngOnDestroy() {}
}
