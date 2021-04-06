//Angular, Material, Libraries
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../../../services/alert.service";
import { AuthService } from "../../../services/auth.service";
import { EventService } from "../../../services/event.service";
import { ClickService, CurrentComponent, ActionIcon } from "../../../services/click.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "ng2-translate";
//Helpers
import { CurrencyHelper } from "../../../helpers/CurrencyHelper";
import { DateHelper } from "../../../helpers/DateHelper";
//Objects
import { Config } from "../../../config/settings";
import { Event } from "../../../models/event";
import { Transaction, TransactionType } from "../../../models/transaction";
import { User } from "../../../models/user";

@Component({
    selector: "event-list-component",
    templateUrl: "../../views/event-list.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class EventListComponent implements OnInit {
    //Controls
    private clickSubscription: Subscription;
    private eventSubscription: Subscription;
    //Objects   
    private LOG_HEADER: string = 'EVENT:LIST';
    private events: Array<Event> = new Array<Event>();
    //Numbers
    private columns: number = 1;
    private screenWidth: number = window.innerWidth;

    constructor(
        private alertService: AlertService,
        private authService: AuthService,
        private clickService: ClickService,
        private eventService: EventService,
        private router: Router,
        private transactionService: TransactionService,
        private translateService: TranslateService
    ) { }

    ngOnInit() {
        this.eventSubscription = this.eventService.getEvents().subscribe(
            (events) => {
                this.events = events;
            },
            (error) => { console.log(error); },
            () => { });
    }

    ngAfterContentInit() {
        this.clickService.createInstance(ActionIcon.CREATE, CurrentComponent.EventListComponent);
        this.clickSubscription = this.clickService.getEventListClick().subscribe(() => {
            this.gotoEventCreate();
        });
    }

    ngAfterViewInit() {
        this.onScreenSizeChange(null);
    }

    ngOnDestroy() {
        if (this.clickSubscription) {
            this.clickService.destroyInstance();
            this.clickSubscription.unsubscribe();
        }
        if (this.eventSubscription) {
            this.eventSubscription.unsubscribe();
        }
    }  

    private onScreenSizeChange(event: any) {
        this.screenWidth = window.innerWidth;
        if (this.screenWidth <= 512) {
            this.columns = 1;
        } else if ((this.screenWidth > 512) && (this.screenWidth <= 1024)) {
            this.columns = 2;
        } else if ((this.screenWidth > 1024) && (this.screenWidth <= 2048)) {
            this.columns = 3;
        } else if ((this.screenWidth > 2048) && (this.screenWidth <= 4096)) {
            this.columns = 4;
        }
    }

    private getCurrencySymbol(currency: string): string {
        if (currency != null) {
            return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
        }
    }

    private getDateTimeString(startTimeStamp: number): string {
        if (startTimeStamp) {
            return DateHelper.convertEpochToDateTimeString(startTimeStamp);
        } else {
            return DateHelper.convertEpochToDateTimeString(0);
        }
    }

    private gotoEventUpdate(event: Event) {  
                this.eventService.setEvent(event);
                this.router.navigate(["/event/update"])            
        
    }

    private gotoEventCreate() { 
        var event = new Event();
        this.eventService.setEvent(event);
        this.router.navigate(["/event/create"])
    }

    private showNoResults(): boolean {
        if (!this.events || this.events.length == 0) {
            return true;
        } else {
            return false;
        }
    }
}
