import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { Message } from "../../../models/message";
import { User } from "../../../models/user";
import { MessageService } from "../../../services/message.service";
import { ModelHelper } from "src/app/helpers/ModelHelper";
import { UserType } from "src/app/models/type/UserType";

@Component({
  selector: "message-create",
  styleUrls: ["message-create.css"],
  templateUrl: "message-create.html",
})
export class MessageCreateComponent implements OnDestroy, OnInit {
  public formGroup!: FormGroup;
  private messageSubscription: Subscription = new Subscription();
  public message: Message = new Message();

  constructor(
    protected alertService: AlertService,
    protected authenticationService: AuthenticationService,
    protected formBuilder: FormBuilder,
    protected messageService: MessageService,
    protected router: Router
  ) {}

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      description: [
        this.message.description,
        [
          Validators.required,
          Validators.maxLength(500),
          Validators.minLength(1),
        ],
      ],
    });
    this.getMessage();
  }

  ngAfterContentInit() {}

  ngAfterViewInit() {}

  ngAfterViewChecked() {}

  ngOnDestroy() {
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
  }

  private getMessage() {
    this.messageSubscription = this.messageService.getMessage().subscribe(
      (message: Message) => {
        this.message = message;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  public getReceiverId(): String {
    return ModelHelper.find(this.message.users, UserType.Receiver);
  }

  public getSenderId(): String {
    return ModelHelper.find(this.message.users, UserType.Sender);
  }

  public send() {
    let message: Message = new Message();
    message = this.formGroup.getRawValue();
    message.users = this.message.users;
    if (!this.message.description || this.message.description.length < 1) {
      this.alertService.error("ERROR_NOT_ENOUGH_CHARACTERS");
    } else if (this.message.description.length > 500) {
      this.alertService.error("ERROR_TOO_MANY_CHARACTERS");
    } else {
      this.messageService.create(this.message).subscribe(
        () => {},
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
    }
  }
}
