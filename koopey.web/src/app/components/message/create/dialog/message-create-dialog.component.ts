import { Component, ElementRef, Input, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { MessageCreateComponent } from "../message-create.component";
import { AlertService } from "../../../../services/alert.service";
import { AuthenticationService } from "../../../../services/authentication.service";
import { ClickService } from "../../../../services/click.service";
import { MessageService } from "../../../../services/message.service";
import { Message } from "../../../../models/message";
import { User } from "../../../../models/user";
import { MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "message-create-dialog",
  templateUrl: "message-create-dialog.html",
})
export class MessageCreateDialogComponent extends MessageCreateComponent {
  constructor(
    protected alertService: AlertService,
    protected authenticationService: AuthenticationService,
    public dialogRef: MatDialogRef<MessageCreateDialogComponent>,
    protected messageService: MessageService,
    protected router: Router
  ) {
    super(alertService, authenticationService, messageService, router);
  }

  public setMessage(message: Message) {
    //NOTE* If not set in child message is blank in parent
    if (message) {
      this.message = message;
    }
  }
}
