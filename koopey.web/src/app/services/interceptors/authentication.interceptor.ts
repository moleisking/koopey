import { catchError } from "rxjs/operators";
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NEVER, Observable, throwError } from "rxjs";
import { Router } from "@angular/router";

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {
    constructor(private router: Router) { }

    public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            catchError(error => {
                if (!!error.status && error.status === 401) {
                    localStorage.removeItem("token");                   
                    this.router.navigate(["/login"]);
                    return NEVER;
                }
                return throwError(error);
            }));
    }
}