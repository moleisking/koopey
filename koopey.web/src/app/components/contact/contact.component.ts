import { Component, OnInit, OnDestroy } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { AlertService } from "../../services/alert.service";
import { HomeService } from "../../services/home.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../services/click.service";
import { TranslateService } from "@ngx-translate/core";
import { Message } from "../../models/message";
import { Contact } from "src/app/models/contact/contact";

@Component({
  selector: "contact-component",
  providers: [HomeService],
  templateUrl: "contact.html",
  styleUrls: ["contact.css"],
})
export class ContactComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  public contact: Contact = new Contact();
  public form!: FormGroup;

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    private formBuilder: FormBuilder,
    private homeService: HomeService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      name: [
        this.contact.name,
        [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(100),
        ],
      ],
      email: [
        this.contact.email,
        [
          Validators.required,
          Validators.email,
          Validators.minLength(5),
          Validators.maxLength(150),
        ],
      ],
      subject: [this.contact.subject, Validators.required],
      text: [this.contact.content, Validators.required],
    });
  }

  ngAfterContentInit() {
    this.clickService.createInstance(
      ActionIcon.EMAIL,
      CurrentComponent.ContactComponent
    );
    this.clickSubscription = this.clickService
      .getContactClick()
      .subscribe(() => {
        this.sendContactForm();
      });
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
  }

  public sendContactForm() {
    if (!this.form.dirty && !this.form.valid) {
      this.alertService.error("ERROR_FORM_NOT_VALID");
    } else {
      this.homeService.sendContactForm(this.contact).subscribe(
        (data: any) => {},
        (error: any) => {
          this.alertService.error("ERROR");
        },
        () => {
          this.alertService.success("INFO_COMPLETE");
        }
      );
    }
  }
}
