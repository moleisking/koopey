//Angular, Material, Libraries
import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { DomSanitizer } from "@angular/platform-browser";
import { Observable, Subscription } from "rxjs";
//Service
import { AuthService } from "../../services/auth.service";
import { UserService } from "../../services/user.service";
import { MessageService } from "../../services/message.service";
import { AlertService } from "../../services/alert.service";
import { TranslateService } from "ng2-translate";
//Objects
import { Message } from "../../models/message";
import { User } from "../../models/user";

@Component({
    selector: "conversation-list-component",
    templateUrl: "../../views/conversation-list.html"
})

export class ConversationListComponent implements OnInit, OnDestroy {

    @ViewChild("messagetext") messageText: ElementRef | undefined;

    private message: string = "";
    private authUser: User = new User();
    private messages: Array<Message> = new Array<Message>();
    private conversations: Array<Message> = new Array<Message>();

    constructor(
        private alertService: AlertService,
        private authenticateService: AuthService,
        private messageService: MessageService,
        private route: ActivatedRoute,
        private router: Router,
        // private userService: UserService,
        private sanitizer: DomSanitizer,
        private translateService: TranslateService,
        private userService: UserService
    ) { }

    ngOnInit() {
        this.authUser = this.authenticateService.getLocalUser();
        this.getMessages();
    }

    ngOnDestroy() { }

    private isAuthenticated() {
        return this.authenticateService.isLoggedIn();
    }

    private isDuplicateConversations(message: Message): boolean {
        for (var i = 0; i < this.conversations.length; i++) {
            if (User.equalsArray(message.users, this.conversations[i].users)) {
                return true;
            }
        }
        return false;
    }

    private isMyUser(id: string) {
        if (id == localStorage.getItem("id")) {
            return true;
        } else {
            return false;
        }
    }

    private getMessages() {
        console.log("getMessages");
        this.messageService.readMessages().subscribe(
            (messages : any) => {
                this.messages = messages;
                console.log(messages);
            },
            (error : any) => { this.alertService.error(<any>error) },
            () => {
                //filter converstaions
                //Todo: start from the bottom                
                this.getConversations();
            }
        );
    }    

    private getConversations() {
        for (var i = 0; i < this.messages.length; i++) {
            if (!this.isDuplicateConversations(this.messages[i])) {
                this.conversations.push(this.messages[i]);
            }
        }
    }
    private getOtherUsers(conversation: Message): Array<User> {     
        return User.exclude(conversation.users, this.authUser);
    }

    private getConversationText(conversation: Message): string {
        if (conversation && conversation.text) {
            return conversation.text.length <= 50 ? conversation.text : conversation.text.substring(1, 74) + '...';
        } else {
            return "";
        };
    }

    private gotoConversationMessages(conversation: Message) {
        if (this.isAuthenticated()) {
            this.messageService.setMessage(conversation)
            this.router.navigate(["/message/read/list/messages"])
        }
    }

    private hasAvatar(user: User): boolean {
        if (user && user.avatar && user.avatar.length > 0) {
            return true;
        } else {
            return false;
        }
    }
}