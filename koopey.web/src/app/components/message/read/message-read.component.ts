//Core
import {
    Component, ElementRef, Input, OnDestroy, OnInit, ViewChild
} from "@angular/core";
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute, Router } from "@angular/router";
import {
    MaterialModule, MdInputModule, MdTextareaAutosize, MdDialog, MdDialogRef
} from "@angular/material";
//Objects
import { Message } from "../../../models/message";
import { User } from "../../../models/user";
import { AlertService } from "../../../services/alert.service";
import { MessageService } from "../../../services/message.service";
import { TranslateService } from "ng2-translate";
import { Config } from "../../../config/settings";

@Component({
    selector: 'message-read',
    templateUrl: '../../views/message-read.html'
})

export class MessageReadComponent implements OnInit, OnDestroy {

    public message: Message = new Message();
    public user: User = new User();
    private messageSubscription: Subscription;
    private compressedWidth = 128;
    private compressedHeight = 128;

    constructor(
        private router: Router,
        private alertService: AlertService,
        private messageService: MessageService,
        private route: ActivatedRoute,
        private translateService: TranslateService) {
    }

    ngOnInit() {
        this.getMessage();
    }

    ngOnDestroy() {
        if (this.messageSubscription) {
            this.messageSubscription.unsubscribe();
        }
    }

    private getMessage() {
        this.route.params.subscribe(p => {
            let id = p["id"];
            if (id) {
                this.messageService.readMessage(id).subscribe(
                    (message) => {
                        this.message = message;                       
                    },
                    error => { this.alertService.error(<any>error) },
                    () => { console.log("getproduct success") }
                );
            } else {
                this.messageSubscription = this.messageService.getMessage().subscribe(
                    (message) => {
                        this.message = message;                        
                    },
                    (error) => { console.log(error); },
                    () => { });
            }
        });
    }


}