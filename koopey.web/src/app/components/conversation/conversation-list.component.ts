import { AlertService } from "../../services/alert.service";
import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  ElementRef,
  ChangeDetectionStrategy,
} from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Observable, Subscription } from "rxjs";
import { AuthenticationService } from "../../services/authentication.service";
import { UserService } from "../../services/user.service";
import { MessageService } from "../../services/message.service";
import { Message } from "../../models/message";
import { User } from "../../models/user";
import { BaseComponent } from "../base/base.component";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "conversation-list",
    standalone: false,
  styleUrls: ["conversation-list.css"],
  templateUrl: "conversation-list.html",
})
export class ConversationListComponent extends BaseComponent
  implements OnInit, OnDestroy {
  @ViewChild("messagetext")
  messageText!: ElementRef;

  public message: string = "";
  public authUser: User = new User();
  public messages: Array<Message> = new Array<Message>();
  public conversations: Array<Message> = new Array<Message>();

  constructor(
    private alertService: AlertService,
    private authenticateService: AuthenticationService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private router: Router,
    // private userService: UserService,
    public sanitizer: DomSanitizer,
    private userService: UserService
  ) {
    super(sanitizer);
  }

  ngOnInit() {
    this.authUser = this.authenticateService.getMyUserFromStorage();
    this.getMessages();
  }

  ngOnDestroy() {}

  private isDuplicateConversations(message: Message): boolean {
    for (var i = 0; i < this.conversations.length; i++) {
      if (
        this.getOtherUser(message).id ===
        this.getOtherUser(this.conversations[i]).id
      ) {
        return true;
      }
    }
    return false;
  }

  public isMyUser(id: string) {
    if (id == localStorage.getItem("id")) {
      return true;
    } else {
      return false;
    }
  }

  public getMessages() {
    console.log("getMessages");
    this.messageService.searchByReceiverOrSender().subscribe(
      (messages: Array<Message> ) => {
        this.messages = messages;
        console.log(messages);
      },
      (error: any) => {
        this.alertService.error(<any>error);
      },
      () => {
        //filter converstaions
        //Todo: start from the bottom
        this.getConversations();
      }
    );
  }

  public getOtherUser(message: Message): User {
    if (message.receiver.id === localStorage.getItem("id")) {
      return message.sender;
    } else {
      return message.receiver;
    }
  }

  public getConversations() {
    if (this.messages) {
      for (var i = 0; i < this.messages.length; i++) {
        if (!this.isDuplicateConversations(this.messages[i])) {
          this.conversations.push(this.messages[i]);
        }
      }
    }   
  }

  public getConversationText(conversation: Message): string {
    if (conversation && conversation.description) {
      return conversation.description.length <= 50
        ? conversation.description
        : conversation.description.substring(1, 74) + "...";
    } else {
      return "";
    }
  }

  public gotoConversationMessages(conversation: Message) {
    if (this.isAuthenticated()) {
      this.messageService.setMessage(conversation);
      this.router.navigate(["/message/read/list/messages"]);
    }
  }

  public hasAvatar(user: User): boolean {
    if (user && user.avatar && user.avatar.length > 0) {
      return true;
    } else {
      return false;
    }
  }
}
