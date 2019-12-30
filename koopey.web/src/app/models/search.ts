import { Config } from "../config/settings";
import { UUID } from 'angular2-uuid';
import { Tag } from "../models/tag";

export class Search {
    public id: string = '';
    public type: string = '';
    public eventId: string = '';
    public userId: string = '';
    public productId: string = '';
    public transactionId: string = '';
    public period: string = Config.default_period;
    public currency: string = Config.default_currency;
    public name: string = '';
    public alias: string = '';
    public start: number = Date.now() - 2419200000; //86400000 (day) // 604800000 (week) // 2419200000 (28 days)
    public end: number = Date.now() + 2419200000;
    public max: number = 5000;
    public min: number = 0;
    public hash: string;
    public measure: string = Config.default_measure;
    public latitude: number = 0;
    public longitude: number = 0;
    public radius: number = Config.default_radius;
    public tags: Array<Tag> = new Array<Tag>();
    public createTimeStamp: number = Date.now();

    public static isEmpty(search: Search): boolean {
        if (search.latitude
            && search.longitude
            && search.tags.length > 0) {
            return true;
        } else {
            return false;
        }
    }
}