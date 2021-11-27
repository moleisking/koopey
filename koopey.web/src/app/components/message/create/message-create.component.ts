import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Message } from "../../../models/message";
import { MessageService } from "../../../services/message.service";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { User } from "../../../models/user";
import { UserService } from "src/app/services/user.service";

@Component({
  selector: "message-create",
  styleUrls: ["message-create.css"],
  templateUrl: "message-create.html",
})
export class MessageCreateComponent implements OnDestroy, OnInit {
  public formGroup!: FormGroup;
  private messageSubscription: Subscription = new Subscription();
  private receiverSubscription: Subscription = new Subscription();
  public message: Message = new Message();
  public receiver: User = new User();

  constructor(
    protected alertService: AlertService,
    protected authenticationService: AuthenticationService,
    protected formBuilder: FormBuilder,
    protected messageService: MessageService,
    protected router: Router,
    protected userService: UserService
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
    this.getReceiver();
  }

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

  private getReceiver() {
    this.receiverSubscription = this.userService.getUser().subscribe(
      (receiver: User) => {
        this.receiver = receiver;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  public send() {
    let message: Message = new Message();
    message = this.formGroup.getRawValue();
    message.sender = this.authenticationService.getMyUserFromStorage();
    message.receiver = this.receiver;
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
