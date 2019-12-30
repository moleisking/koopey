// Core modules
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { MaterialModule, MdInputModule, MdDatepickerModule, MdDatepickerIntl, MdDialog, MdDialogRef } from "@angular/material"
import { UUID } from 'angular2-uuid';
//Services
import { AlertService } from "../services/alert.service";
import { AuthService } from "../services/auth.service";
import { ClickService, CurrentComponent, ActionIcon } from "../services/click.service";
import { EventService } from "../services/event.service";
import { AssetService } from "../services/asset.service";
import { TransactionService } from "../services/transaction.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../services/user.service";
//Components
import { EventCreateComponent } from "../components/event-create.component";
//Objects
import { Event } from "../models/event";
import { Transaction } from "../models/transaction";
import { User } from "../models/user";
import 'hammerjs';

@Component({
    selector: 'event-create-dialog',
    templateUrl: '../../views/event-create-dialog.html',
})

export class EventCreateDialogComponent extends EventCreateComponent implements OnInit {

    private LOG_HEADER: string = 'EVENT:CREATE:DIALOG';

    constructor(
        private dialogRef: MdDialogRef<EventCreateDialogComponent>,
        protected alertService: AlertService,
        protected authService: AuthService,       
        protected clickService: ClickService,
        protected datePickerService: MdDatepickerIntl,
        protected eventService: EventService,
        protected router: Router,
        protected transactionService: TransactionService,
        protected translateService: TranslateService,
        protected assetService: AssetService,
        protected userService: UserService
       
    ) {
        super(
            alertService,
            authService,  
            clickService,
            datePickerService,
            eventService,
            router,
            transactionService,
            translateService,
            assetService,
            userService
        );
    }

    ngOnInit() {
        this.redirect = false;
    }

    public eventComplete(complete: boolean): void {
        console.log("eventComplete");
        console.log(complete);
    }

    public setEvent(event: Event) {
        this.event = event;
    }

    private cancel() {
        this.dialogRef.close(null);
    }
}