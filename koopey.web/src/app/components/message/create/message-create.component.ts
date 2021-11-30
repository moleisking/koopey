import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import {
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Message } from "../../../models/message";
import { MessageService } from "../../../services/message.service";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { User } from "../../../models/user";
import { UserService } from "src/app/services/user.service";
import { MessageType } from "src/app/models/type/MessageType";

@Component({
  selector: "message-create",
  styleUrls: ["message-create.css"],
  templateUrl: "message-create.html",
})
export class MessageCreateComponent implements OnDestroy, OnInit {
  @Output() messageSent: EventEmitter<Message> = new EventEmitter<Message>();

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
      name: [
        this.message.name,
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
        console.log("getMessage");
        console.log(this.message);
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
    let formData: Message = new Message();
    formData = this.formGroup.getRawValue();
    this.message.receiverId = this.receiver.id;
    this.message.senderId = String(localStorage.getItem("id"));
    this.message.name = formData.name;
    this.message.type = MessageType.Sent;
    if (!this.message.name || this.message.name.length < 1) {
      console.log("send");
      console.log(this.message);
      this.alertService.error("ERROR_NOT_ENOUGH_CHARACTERS");
    } else if (this.message.name.length > 500) {
      this.alertService.error("ERROR_TOO_MANY_CHARACTERS");
    } else {
      this.messageService.create(this.message).subscribe(
        () => {},
        (error: Error) => {
          this.alertService.error(error.message);
        },
        () => {
          this.messageSent.emit(this.message);
        }
      );
    }
  }
}
