import { UUID } from 'angular2-uuid';

export class Alert {
    public id: string = UUID.UUID();
    public type: string = ""; //error,info
    public message: string = "";
    public object: any = "";
    public createTimeStamp: number = Date.now();

    public static isSuccess(alert: Alert): boolean {
        if (alert && alert.type && alert.type.toLowerCase().match("success")) {
            return true;
        } else {
            return false;
        }
    }

    public static isError(alert: Alert): boolean {
        if (alert && alert.type && alert.type.toLowerCase().match("error")) {
            return true;
        } else {
            return false;
        }
    }

}