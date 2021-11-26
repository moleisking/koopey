import { ActivatedRoute } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { Component, OnInit, OnDestroy } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { Subscription } from "rxjs";
import { MessageService } from "../../../services/message.service";
import { Message } from "../../../models/message";
import { ModelHelper } from "src/app/helpers/ModelHelper";
import { User } from "../../../models/user";
import { UserService } from "../../../services/user.service";
import { UserType } from "src/app/models/type/UserType";

@Component({
  selector: "messages-component",
  styleUrls: ["message-list.css"],
  templateUrl: "message-list.html",
})
export class MessageListComponent implements OnInit, OnDestroy {
  private messageSubscription: Subscription = new Subscription();

  public message: Message = new Message();
  //public template: Message = new Message();
  public messages: Array<Message> = new Array<Message>();
  // private authUser: User;
  // private users: Array<User>;

  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    public sanitizer: DomSanitizer,
    private userService: UserService
  ) {}

  ngOnInit() {
    //Message from ConverstaionList via MessageService
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
  }

  private getMessage() {
    this.messageService.getMessage().subscribe(
      (message: Message) => {
        this.message = message;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  private getMessages() {
    //let other: User = this.getOtherUsers().pop()!;

    this.messageService.searchByReceiverOrSender().subscribe(
      (messages: Array<Message>) => {
        this.messages = messages;
      },
      (error: Error) => {
        this.alertService.error(error.message);
      }
    );
  }

  public getOtherUsers(): Array<User> {
    return ModelHelper.exclude(
      this.message.users,
      this.authenticationService.getMyUserFromStorage()
    );
  }

  public isMyUser(id: string) {
    if (id == localStorage.getItem("id")) {
      return true;
    } else {
      return false;
    }
  }

  public isMyMessage(message: Message) {
    if (ModelHelper.find(message.users, localStorage.getItem("id"))) {
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

  public getSenderAvatar(message: Message): string {
    return ModelHelper.find(message.users, UserType.Sender).avatar;
  }

  public filterConversationMessages(messages: Array<Message>): Array<Message> {
    var conversationMessages: Array<Message> = new Array<Message>();
    for (var i = 0; i < this.messages.length; i++) {
      if (ModelHelper.equalsArray(this.messages[i].users, this.message.users)) {
        conversationMessages.push(this.messages[i]);
      }
    }

    return conversationMessages;
  }

  /*
  private shrinkImage(imageUri: string, width: number, height: number) {
    var sourceImage = new Image();
    sourceImage.src = imageUri;

    // Create a canvas with the desired dimensions
    var canvas = document.createElement("canvas");
    var ctx = canvas.getContext("2d");
    canvas.width = width;
    canvas.height = height;

    // Scale and draw the source image to the canvas
    if (ctx != null) {
      ctx.drawImage(sourceImage, 0, 0, width, height);
    }

    // Convert the canvas to a data URL in PNG format
    var data = canvas.toDataURL();
    return data;
  }

  private scrollToBottom(): void {
    try {
      var divMessages = <HTMLDivElement>document.getElementById("divMessages");
      divMessages.scrollTop = divMessages.scrollHeight;
      //this.messageList.nativeElement.scrollTop = this.messageList.nativeElement.scrollHeight;
    } catch (error) {}
  }*/
}
