const SHA256 = require("crypto-js/sha256");
import { Location } from "../models/location";
import { Transaction } from "../models/transaction";
import { User } from "../models/user";
import { UUID } from 'angular2-uuid';

export enum EventType {
    Once = 'once',
    Hour = 'hour',
    Day = 'day',
    Week = 'week',
    Month = 'month',
    Year = 'year'
 }

export class Event {
    public id: string = UUID.UUID();
    public users: Array<User> = new Array<User>();
    public transactions: Array<Transaction> = new Array<Transaction>();
    public name: string = '';  
    public description: string = '';  
    public type: string = 'once';    
    public timeZone: string = 'Etc/UTC';
    public secret: string = "secret";      
    public hash: string;
    public guid: string = UUID.UUID();
    public location: Location = new Location();
    public startTimeStamp: number = Date.now();
    public endTimeStamp: number = Date.now();
    public createTimeStamp: number = Date.now();
    public readTimeStamp: number = 0;
    public updateTimeStamp: number = 0;
    public deleteTimeStamp: number = 0;

    public static isEmpty(event: Event): boolean {
        if (event          
            && event.description
            && event.name          
            && event.startTimeStamp != 0
            && event.endTimeStamp != 0           
            && event.users.length >= 1           
            && event.type.match('once|hour|day|week|month|year')) {
            return false;
        } else {
            return true;
        }
    }  

    public static contains(events: Array<Event>, id: string): boolean {
        if (events && events.length > 0) {
            for (var i = 0; i <= events.length; i++) {             
                if (events[i] &&
                    events[i].id == id) {
                    //Current item is not unique                     
                    return true;
                } else if (i == events.length - 1) {
                    //Last item and unique  
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static create(events: Array<Event>, event: Event): Array<Event> {
        if (events.length == 0 || !Event.contains(events, event.id)) {
            events.push(event);
            return events;
        } else {
            return events;
        }
    }

    public static read(events: Array<Event>, event: Event): Event {
        if (events && events.length > 0) {
            for (var i = 0; i < events.length; i++) {
                if (events[i] &&
                    events[i].id == event.id) {
                    return events[i];
                }
            }
        }
    }

    public static update(events: Array<Event>, event: Event): Array<Event> {
        if (events && events.length > 0) {
            for (var i = 0; i < events.length; i++) {
                if (events[i] &&
                    events[i].id == event.id) {
                    events[i] == event;
                    return events;
                }
            }
        }
    }

    public static delete(events: Array<Event>, event: Event): Array<Event> {
        if (events && events.length > 0) {
            for (var i = 0; i < events.length; i++) {
                if (events[i] &&
                    events[i].id == event.id) {
                    events.splice(i, 1);
                    return events;
                }
            }
        }
    }
}