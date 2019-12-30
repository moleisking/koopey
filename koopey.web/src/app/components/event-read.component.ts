//Angular, Material, Libraries
import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { MaterialModule, MdIconModule, MdIconRegistry, MdInputModule } from "@angular/material"
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../services/alert.service";
import { EventService } from "../services/event.service";
import { TransactionService } from "../services/transaction.service";
import { TranslateService } from "ng2-translate";
//Helpers
import { CurrencyHelper } from "../helpers/CurrencyHelper";
import { TransactionHelper } from "../helpers/TransactionHelper";
//Objects
import { Alert } from "../models/alert";
import { Event } from "../models/event";
import { Transaction } from "../models/transaction";

@Component({
    selector: "event-read-component",
    templateUrl: "../../views/event-read.html",
    styleUrls: ["../../styles/app-root.css"]
})

export class EventReadComponent implements OnInit, OnDestroy {

    private eventSubscription: Subscription;
    private event: Event = new Event();

    constructor(
        private route: ActivatedRoute,
        private alertService: AlertService,
        private eventService: EventService,
        private translateService: TranslateService,
        private transactionService: TransactionService
    ) { }

    ngOnInit() {
        this.getEvent();
    }

    ngOnDestroy() { }


    private isLoggedIn() {
        if (localStorage.getItem("id")) {
            return true;
        } else {
            return false;
        }
    }

    private getEvent() {
        this.route.params.subscribe(p => {
            let id = p["id"];
            if (id) {
                this.eventService.readEvent(id).subscribe(
                    (event) => { this.event = event; },
                    (error) => { this.alertService.error(<any>error) },
                    () => { }
                );
            } else {
                this.eventSubscription = this.eventService.getEvent().subscribe(
                    (event) => { this.event = event; },
                    (error) => { this.alertService.error(<any>error) },
                    () => { });
            }
        });
    }

    private getCurrencySymbol(currency: string): string {
        if (currency != null) {
            return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
        }
    }

    private getTimeStampText(epoch: any): string {
        var date = new Date(epoch);
        date.setHours(0, 0, 0, 0);
        return date.toDateString();
    }
}
