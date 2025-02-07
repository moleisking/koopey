import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { AuthenticationService } from "../../../../services/authentication.service";
import { AlertService } from "../../../../services/alert.service";
import { Change } from "../../../../models/authentication/change";

@Component({
  selector: "email-change-request-component",
  templateUrl: "email-change-request.html",
  styleUrls: ["email-change-request.css"],
})
export class EmailChangeRequestComponent implements OnInit {
  public form!: FormGroup;
  public emailChange: Change = new Change();

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      oldEmail: [
        this.emailChange.old,
        [
          Validators.required,
          Validators.email,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
      newEmail: [
        this.emailChange.new,
        [
          Validators.required,
          Validators.email,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
    });
  }

  public update() {
    if (!this.form.dirty && !this.form.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.authenticateService.emailChangeRequest(this.emailChange).subscribe(
        () => {},
        (error: Error) => {
          this.alertService.error(error.message);
        },
        () => {
          this.router.navigate(["/configuration"]);
        }
      );
    }
  }
}
