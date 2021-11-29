import { ActivatedRoute } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { Component, OnInit, OnDestroy } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { MessageService } from "../../../services/message.service";
import { Message } from "../../../models/message";
import { User } from "../../../models/user";
import { UserService } from "../../../services/user.service";

@Component({
  selector: "messages-component",
  styleUrls: ["message-list.css"],
  templateUrl: "message-list.html",
})
export class MessageListComponent implements OnInit, OnDestroy {
  private messageSubscription: Subscription = new Subscription();
  private messageListSubscription: Subscription = new Subscription();
  //private receiverSubscription: Subscription = new Subscription();
  public message: Message = new Message();
  //public template: Message = new Message();
  public messages: Array<Message> = new Array<Message>();
  // private authUser: User;
  //public receiver: User = new User();

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    public sanitizer: DomSanitizer,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.getMessage();
    this.getMessages();
  }

  ngAfterContentInit() {
    //Set sender
    /* for (var i = 0; i < this.template.users.length; i++) {
      if (this.template.users[i].id == localStorage.getItem("id")) {
        this.template.users[i].avatar = this.shrinkImage(
          localStorage.getItem("avatar")!,
          64,
          64
        );
        this.template.users[i].type = "sender";
      }
    }
    //Set receivers
    for (var i = 0; i < this.template.users.length; i++) {
      if (this.template.users[i].id != localStorage.getItem("id")) {
        this.template.users[i].type = "receiver";
      }
    }*/
  }

  ngAfterViewInit() {}

  ngAfterViewChecked() {
    //this.scrollToBottom();
  }

  ngOnDestroy() {
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
    if (this.messageListSubscription) {
      this.messageListSubscription.unsubscribe();
    }
  }

  /*public getOtherUser(message: Message): User {
    if (message.receiver.id === localStorage.getItem("id")) {
      return message.sender;
    } else {
      return message.receiver;
    }
  }*/

  /* private getReceiver() {
    this.receiverSubscription = this.userService
      .read(this.message.receiver.id)
      .subscribe(
        (receiver: User) => {
          this.receiver = receiver;
        },
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
  }*/

  private getMessage() {
    this.messageSubscription = this.messageService.getMessage().subscribe(
      (message: Message) => {
        this.message = message;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      },
      () => {
        // this.getReceiver();
      }
    );
  }

  private getMessages() {
    this.messageListSubscription = this.messageService
      .searchByReceiverOrSender()
      .subscribe(
        (messages: Array<Message>) => {
          this.messages = messages;
        },
        (error: Error) => {
          this.alertService.error(error.message);
        }
      );
  }

  public isMyUser(id: string) {
    if (id == localStorage.getItem("id")) {
      return true;
    } else {
      return false;
    }
  }

  public hasMessages(): boolean {
    if (!this.messages) {
      return false;
    } else if (this.messages.length == 0) {
      return false;
    } else if (this.messages.length > 0) {
      return true;
    } else {
      return false;
    }
  }

  public filterConversationMessages(messages: Array<Message>): Array<Message> {
    return messages.filter((message: Message) => {
      return (
        message.receiver.id === this.message.receiver.id ||
        message.sender.id === this.message.receiver.id
      );
    });
  }
  /*
 private scrollToBottom(): void {
    try {
      var divMessages = <HTMLDivElement>document.getElementById("divMessages");
      divMessages.scrollTop = divMessages.scrollHeight;
      //this.messageList.nativeElement.scrollTop = this.messageList.nativeElement.scrollHeight;
    } catch (error) {}
  }*/
}
