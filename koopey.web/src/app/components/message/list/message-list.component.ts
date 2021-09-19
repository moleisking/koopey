import {
  Component,
  Input,
  OnInit,
  OnDestroy,
  ViewChild,
  ElementRef,
} from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Observable, Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import { UserService } from "../../../services/user.service";
import { MessageService } from "../../../services/message.service";
import { TranslateService } from "@ngx-translate/core";
import { Message } from "../../../models/message";
import { User } from "../../../models/user";
import { Environment } from "src/environments/environment";

@Component({
  selector: "messages-component",
  templateUrl: "message-list.html",
})
export class MessageListComponent implements OnInit, OnDestroy {
  @ViewChild("messageElement") messageElement!: ElementRef;
  @ViewChild("messageList") private messageList!: ElementRef;

  private messageSubscription: Subscription = new Subscription();
  public text: string = "";
  public compressedWidth = 128;
  public compressedHeight = 128;
  public message: Message = new Message();
  public template: Message = new Message();
  public messages: Array<Message> = new Array<Message>();
  // private authUser: User;
  // private users: Array<User>;

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    public sanitizer: DomSanitizer,
    private translateService: TranslateService,
    private userService: UserService
  ) {}

  ngOnInit() {
    //Message from ConverstaionList via MessageService
    this.messageService.getMessage().subscribe(
      (message) => {
        this.template = message;
      },
      (error) => {
        this.alertService.error(<any>error);
      },
      () => {
        if (Environment.type != "production") {
          console.log(this.messages);
        }
      }
    );
    //Messages
    this.messageService.readMessages().subscribe(
      (messages) => {
        this.messages = messages;
      },
      (error) => {
        this.alertService.error(<any>error);
      },
      () => {
        if (Environment.type != "production") {
          console.log(this.messages);
        }
      }
    );
  }

  ngAfterContentInit() {
    //Set sender
    for (var i = 0; i < this.template.users.length; i++) {
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
    }
  }

  ngAfterViewInit() {}

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  ngOnDestroy() {
    /*  if (this.authSubscription) {
              this.authSubscription.unsubscribe();
          }*/
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
    /* if (this.userSubscription) {
             this.userSubscription.unsubscribe();
         }*/
  }

  public isMyUser(id: string) {
    if (id == localStorage.getItem("id")) {
      return true;
    } else {
      return false;
    }
  }

  public isMyMessage(message: Message) {
    if (User.readSender(message.users).id == localStorage.getItem("id")) {
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
    return User.readSender(message.users).avatar;
  }

  public filterConversationMessages(messages: Array<Message>): Array<Message> {
    var conversationMessages: Array<Message> = new Array<Message>();
    for (var i = 0; i < this.messages.length; i++) {
      if (User.equalsArray(this.messages[i].users, this.template.users)) {
        conversationMessages.push(this.messages[i]);
      }
    }

    return conversationMessages;
  }

  public create() {
    //NOTE* Message credit charge is done in the backend
    if (!this.message.text || this.message.text.length < 1) {
      this.alertService.error("ERROR_NOT_ENOUGH_CHARACTERS");
    } else if (this.message.text.length > 500) {
      this.alertService.error("ERROR_TOO_MANY_CHARACTERS");
    } else {
      //Build message object before sending
      this.message.users = this.template.users;
      this.messageService.create(this.message).subscribe(
        () => {},
        (error) => {
          this.alertService.error(<any>error);
        },
        () => {
          if (Environment.type != "production") {
            console.log(this.message);
          }
          this.messages.push(this.message);
          this.message = new Message();
        }
      );
      console.log(this.message);
    }
  }

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
  }
}
