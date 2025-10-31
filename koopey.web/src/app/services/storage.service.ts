import { inject, Injectable } from "@angular/core";
import { BaseService } from "./base.service";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class StorageService extends BaseService {

    constructor(
        protected httpClient: HttpClient
    ) {
        super(httpClient);
    }

    public sanitizer = inject(DomSanitizer);
    //constructor(public sanitizer: DomSanitizer) {}

    public getAlias(): string {
        return localStorage.getItem("alias")!;
    }

    public getAuthenticationUserId(): string {
        return localStorage.getItem("id")!;
    }

    public getAvatar(): SafeUrl {
        if (localStorage.getItem("avatar")) {
            return this.sanitizer.bypassSecurityTrustUrl(
                localStorage.getItem("avatar")!
            );
        } else {
            return "/images/default-user.svg";
        }
    }

    public getCurrency(): string {
        if (
            localStorage.getItem("currency") !== undefined &&
            localStorage.getItem("currency") !== null &&
            localStorage.getItem("currency")!.length !== null &&
            localStorage.getItem("currency")!.length > 0
        ) {
            return localStorage.getItem("currency")!.toString();
        } else {
            localStorage.setItem("currency", "eur")
            return "eur";
        }
    }

    public getLanguage(): string {
        if (
            localStorage.getItem("language") !== undefined &&
            localStorage.getItem("language") !== null &&
            localStorage.getItem("language")!.length !== null &&
            localStorage.getItem("language")!.length > 0
        ) {
            return localStorage.getItem("language")!.toString();
        } else {
            localStorage.setItem("language", "en");
            return "en";
        }
    }

    public getLocationId(): string {
        if (
            localStorage.getItem("locationId") !== undefined &&
            localStorage.getItem("locationId") !== null &&
            localStorage.getItem("locationId")!.length !== null &&
            localStorage.getItem("locationId")!.length > 0
        ) {
            return localStorage.getItem("locationId")!.toString();
        } else {
            localStorage.setItem("locationId", "a62102c7-c103-4546-90ce-91cff7395894");
            return "a62102c7-c103-4546-90ce-91cff7395894";
        }
    }

    public isAuthenticated() {
        if (localStorage.getItem("token") !== null) {
            return true;
        } else {
            return false;
        }
    }

}