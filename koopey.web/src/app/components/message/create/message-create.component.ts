import { Component, ElementRef, Input, OnInit, ViewChild } from "@angular/core";
import { MaterialModule, MdInputModule, MdTextareaAutosize, MdDialog, MdDialogRef } from "@angular/material";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from 'rxjs/Subscription';
import { Message } from "../../../models/message";
import { User } from "../../../models/user";
import { AlertService } from "../../../services/alert.service";
import { AuthService } from "../../../services/auth.service";
import { MessageService } from "../../../services/message.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../../../services/user.service";
import { Config } from "../../../config/settings";

@Component({
    selector: 'message-create',
    templateUrl: '../../views/message-create.html'
})

export class MessageCreateComponent implements OnInit {

    private messageSubscription: Subscription;
    protected message: Message = new Message();
    private compressedWidth = 128;
    private compressedHeight = 128;

    constructor(
        protected alertService: AlertService,
        protected authService: AuthService,
        protected messageService: MessageService,
        protected router: Router) {
    }

    ngOnInit() {
        this.messageSubscription = this.messageService.getMessage().subscribe(
            (message) => { this.message = message; },
            (error) => { this.alertService.error(<any>error) },
            () => { if (!Config.system_production) { console.log(this.message) } });
    }

    ngAfterContentInit() {
        //Shrink avatar passed on by read user or asset
        for (var i = 0; i < this.message.users.length; i++) {       
            this.message.users[i].avatar = this.shrinkImage(this.message.users[i].avatar, 64, 64);
        }
     }
    

    ngAfterViewInit() { }

    ngAfterViewChecked() { }

    ngOnDestroy() {
        if (this.messageSubscription) {
            this.messageSubscription.unsubscribe();
        }
    }

    private create($event: any) {
        this.message.text = $event.target.value;
        //NOTE* Message credit charge is done in the backend
        if (!this.message.text || this.message.text.length < 1) {
            this.alertService.error("ERROR_NOT_ENOUGH_CHARACTERS");
        } else if (this.message.text.length > 500) {
            this.alertService.error("ERROR_TOO_MANY_CHARACTERS");
        } else {
            this.messageService.create(this.message).subscribe(
                () => { },
                (error) => { this.alertService.error(<any>error) },
                () => { if (Config.system_production) { console.log(this.message); } }
            );
        }
    } 

    private shrinkImage(imageUri: string, width: number, height: number) {
        var sourceImage = new Image();
        sourceImage.src = imageUri;

        // Create a canvas with the desired dimensions
        var canvas = document.createElement("canvas");
        var ctx = canvas.getContext('2d');
        canvas.width = width;
        canvas.height = height;

        // Scale and draw the source image to the canvas
        ctx.drawImage(sourceImage, 0, 0, width, height);

        // Convert the canvas to a data URL in PNG format
        var data = canvas.toDataURL();
        return data;
    }

}