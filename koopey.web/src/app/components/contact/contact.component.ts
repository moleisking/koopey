//Angular, Material, Libraries
import { Component, OnInit, OnDestroy } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../../services/alert.service";
import { HomeService } from "../../services/home.service";
import { ClickService, CurrentComponent, ActionIcon } from "../../services/click.service";
import { TranslateService } from "ng2-translate";
//Objects
import { Message } from "../../models/message";

@Component({
    selector: "contact-component",
    providers: [HomeService],
    templateUrl: "../../views/contact.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class ContactComponent implements OnInit, OnDestroy {

    private LOG_HEADER: string = "ContactComponent"
    private form: FormGroup;    
    private email: string;
    private language: string;
    private subject: string;
    private name: string;
    private text: string;
    private message: Message = new Message();
    private clickSubscription: Subscription;

    constructor(
        private alertService: AlertService,
        private clickService: ClickService,
        private formBuilder: FormBuilder,
        private homeService: HomeService,
        private translateService: TranslateService
    ) { }

    ngOnInit() {
        this.form = this.formBuilder.group({
            name: [this.name, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
            email: [this.email, [Validators.required, Validators.email, Validators.minLength(5), Validators.maxLength(150)]],
            subject: [this.subject, Validators.required],
            text: [this.text, Validators.required]
        });
    }

    ngAfterContentInit() {
        this.clickService.createInstance(ActionIcon.EMAIL, CurrentComponent.ContactComponent)
        this.clickSubscription = this.clickService.getContactClick().subscribe(() => {
            this.sendContactForm();
        });
    }

    ngAfterViewInit() {       

    }

    ngOnDestroy() {
        if (this.clickSubscription) {
            this.clickService.destroyInstance();
            this.clickSubscription.unsubscribe();
        }
    }

    private sendContactForm() {        
        if (!this.form.dirty && !this.form.valid) {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        } else {
            console.log(this.message);
            this.homeService.sendContactForm(this.name,this.email, this.subject, this.text, this.language ).subscribe(
                data => { },
                error => { this.alertService.error("ERROR") },
                () => { this.alertService.success("INFO_COMPLETE") }
            );
        }
    }
}
