import { Component, ElementRef, Input, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { MaterialModule, MdInputModule, MdTextareaAutosize, MdDialog, MdDialogRef } from "@angular/material";
//Components
import { MessageCreateComponent } from "./message-create.component";
//Services
import { AlertService } from "../../../services/alert.service";
import { AuthService } from "../../../services/auth.service";
import { ClickService } from "../../../services/click.service";
import { MessageService } from "../../../services/message.service";
//Models
import { Config } from "../../../config/settings";
import { Message } from "../../../models/message";
import { User } from "../../../models/user";

@Component({
    selector: 'message-create-dialog',
    templateUrl: '../../views/message-create-dialog.html'
})

export class MessageCreateDialogComponent extends MessageCreateComponent {

    constructor(
        protected alertService: AlertService,
        protected authService: AuthService,
        public dialogRef: MdDialogRef<MessageCreateDialogComponent>,
        protected messageService: MessageService,
        protected router: Router) {
        super(
            alertService,
            authService,
            messageService,
            router
        );
    }

    public setMessage(message: Message) {
        //NOTE* If not set in child message is blank in parent
        if (message) {
            this.message = message;
        }
    }
}