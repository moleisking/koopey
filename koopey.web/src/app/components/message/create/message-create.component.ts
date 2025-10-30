import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import {
  ChangeDetectionStrategy,
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
import { UserService } from "../../../services/user.service";
import { MessageType } from "../../../models/type/MessageType";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "message-create",
    standalone: false,
  styleUrls: ["message-create.css"],
  templateUrl: "message-create.html",
})
export class MessageCreateComponent implements OnDestroy, OnInit {
  @Output() messageSent: EventEmitter<Message> = new EventEmitter<Message>();

  public formGroup!: FormGroup;
  public message: Message = new Message();
  private messageSubscription: Subscription = new Subscription();
  public receiver: User = new User();
  private receiverSubscription: Subscription = new Subscription();

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
    if (this.receiverSubscription) {
      this.receiverSubscription.unsubscribe();
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
    let formData: Message = new Message();
    formData = this.formGroup.getRawValue();
    this.message.receiverId = this.receiver.id;
    this.message.senderId = String(localStorage.getItem("id"));
    this.message.name = formData.name;
    this.message.type = MessageType.Sent;
    if (!this.message.name || this.message.name.length < 1) {
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
          this.formGroup.controls["name"].setValue("");
        }
      );
    }
  }
}
