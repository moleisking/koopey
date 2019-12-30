//Core
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Message } from "../models/message";

@Injectable()
export class MessageService {

    private LOG_HEADER: string = 'MESSAGE:SERVICE:';
    public message = new ReplaySubject<Message>();
    public messages = new ReplaySubject<Array<Message>>();

    constructor(
        private http: Http,
        private translate: TranslateService
    ) { }

    /*********  Object *********/

    public getMessage(): Observable<Message> {
        return this.message.asObservable();
    }

    public setMessage(message: Message) {
        this.message.next(message);
    }

    public getMessages(): Observable<Array<Message>> {
        return this.messages.asObservable();
    }

    public setMessages(messages: Array<Message>) {
        this.messages.next(messages);
    }

    /*********  Create *********/

    public create(message: Message): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(message);
        var url = Config.system_backend_url + "/message/create";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Read *********/

    public readCount(): Observable<Number> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/message/read/count/";
        return this.http.get(url, options).map((res: Response) => { return res.json().messages.count }).catch(this.handleError);
    }

    public readMessage(message: Message): Observable<Message> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        let body = JSON.stringify(message);
        var url = Config.system_backend_url + "/message/read/one" + name;
        return this.http.post(url, options).map((res: Response) => res.json().messages).catch(this.handleError);
    }

    public readMessages(): Observable<Array<Message>> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/message/read/many";
        return this.http.get(url, options).map((res: Response) => res.json().messages).catch(this.handleError);
    }

    public readMessagesUndelivered(): Observable<Array<Message>> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/message/read/many/undelivered";
        return this.http.get(url, options).map((res: Response) => res.json().messages).catch(this.handleError);
    }


    public readMessagesUnsent(): Observable<Array<Message>> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/message/read/many/unsent";
        return this.http.get(url, options).map((res: Response) => res.json().messages).catch(this.handleError);
    }

    public readMessagesUndeliveredCount(): Observable<Number> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/message/read/many/undelivered/count";
        return this.http.get(url, options).map((res: Response) => res.json().messages.count).catch(this.handleError);
    }


    public readMessagesUnsentCount(): Observable<Number> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ 'headers': headers });
        var url = Config.system_backend_url + "/message/read/many/unsent/count";
        return this.http.get(url, options).map((res: Response) => res.json().messages.count).catch(this.handleError);
    }

    /*********  Error *********/

    private handleError(error: any) {
        return Observable.throw({ "MessageService": { "Code": error.status, "Message": error.message } });
    }
}