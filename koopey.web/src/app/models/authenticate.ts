import { Config } from "../config/settings";
import { UUID } from 'angular2-uuid';

export class Authenticate {

    public alias: string;
    public email: string;
    public hash: string;
    public language: string = Config.default_language;
    public password: string;
    public secret: string;
    public type: string = "complete";
    public createTimeStamp: number = Date.now();
    public readTimeStamp: number = 0;
    public updateTimeStamp: number = 0;
    public deleteTimeStamp: number = 0;
    public guid: string ;
    public oldEmail: string;
    public oldPassword: string;
    public newEmail: string;
    public newPassword: string;

    public static isLogin(authenticate: Authenticate): boolean {
        if ((authenticate.alias ||
            authenticate.email) &&
            authenticate.password) {
            return true;
        } else {
            return false;
        }
    }

    public static isUpdateEmail(authenticate: Authenticate): boolean {
        if (authenticate.alias &&
            authenticate.alias.length >= 5 &&
            authenticate.oldEmail &&
            authenticate.oldEmail.length > 3 &&
            authenticate.newEmail &&
            authenticate.newEmail.length > 3) {
            return false;
        } else {
            return true;
        }
    }

    public static isUpdatePassword(authenticate: Authenticate): boolean {
        if (authenticate.oldPassword.length >= 5 &&
            authenticate.newPassword.length >= 5) {
            return false;
        } else {
            return true;
        }
    }

    public static isUpdatePasswordForgotten(authenticate: Authenticate): boolean {
        if ((authenticate.alias ||
            authenticate.email) &&
            authenticate.oldPassword.length >= 5 &&
            authenticate.newPassword.length >= 5) {
            return false;
        } else {
            return true;
        }
    }
}