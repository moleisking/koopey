import { catchError } from "rxjs/operators";
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NEVER, Observable, throwError } from "rxjs";

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {
    constructor(     ) {    }

    public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            catchError(error => {
                console.log("scan 401 error")
                if (!!error.status && error.status === 401) {
                    localStorage.removeItem("token");
                    window.location.href =  "/login";
                    console.log("got 401 error")
                    return NEVER;
                }
                return throwError(error);
            }));
    }
}