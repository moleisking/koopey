import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { AuthenticationService } from "../../../../services/authentication.service";
import { UserService } from "../../../../services/user.service";
import { AlertService } from "../../../../services/alert.service";
import { TranslateService } from "@ngx-translate/core";
import { Config } from "../../../../config/settings";
import { User } from "../../../../models/user";

@Component({
  selector: "email-change-request-component",
  templateUrl: "email-change-reply.html",
})
export class EmailChangeReplyComponent implements OnInit, OnDestroy {
  private user: User = new User();
  private userAuthenticated: boolean = false;

  constructor(
    private authenticateService: AuthenticationService,
    private route: ActivatedRoute,
    private alertService: AlertService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.user.secret = window.location.href.substr(
      window.location.href.lastIndexOf("/") + 1
    );
  }

  ngAfterContentInit() {
    this.authenticateService.emailChangeReply(this.user).subscribe(
      (data) => {
        this.userAuthenticated = true;
        localStorage.setItem("email", "true");
      },
      (error) => {
        this.alertService.info(error);
      },
      () => {}
    );
  }

  ngOnDestroy() {}
}
