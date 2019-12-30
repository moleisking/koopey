//Core
import { Injectable } from "@angular/core";
import { Http, Headers, Response, RequestOptions } from "@angular/http";
import { Observable, ReplaySubject } from "rxjs/Rx";
//Services
import { TranslateService } from "ng2-translate";
//Objects
import { Alert } from "../models/alert";
import { File } from "../models/file";
import { Config } from "../config/settings";

@Injectable()
export class FileService {

    private static LOG_HEADER: string = 'FILE:SERVICE:';
    public file = new ReplaySubject<File>();
    public files = new ReplaySubject<Array<File>>();

    constructor(
        private http: Http,
        private translateService: TranslateService
    ) { }

    /*********  Object *********/

    public getFile(): Observable<File> {
        return this.file.asObservable();
    }

    public setFile(file: File): void {
        this.file.next(file);
    }

    public getFiles(): Observable<Array<File>> {
        return this.files.asObservable();
    }

    public setFiles(files: Array<File>): void {
        this.files.next(files);
    }

    /*********  Create *********/

    public create(file: File): Observable<Alert> {
        let headers = new Headers();
        headers.append("authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ headers: headers });
        let body = JSON.stringify(file);
        var url = Config.system_backend_url + "/file/create";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Read *********/

    public readFile(id: string): Observable<File> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ headers: headers });
        var url = Config.system_backend_url + "/file/read/file/" + id;
        return this.http.get(url, options).map((res: Response) => { return res.json().transaction }).catch(this.handleError);
    }

    public readFiles(): Observable<File[]> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ headers: headers });
        var url = Config.system_backend_url + "/file/read/files";
        return this.http.get(url, options).map((res: Response) => { return res.json().transactions }).catch(this.handleError);
    }

    /*********  Update *********/

    public update(file: File): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ headers: headers });
        let body = JSON.stringify(file);
        var url = Config.system_backend_url + "/file/update";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Delete *********/

    public delete(file: File): Observable<Alert> {
        let headers = new Headers();
        headers.append("Authorization", "JWT " + localStorage.getItem("token"));
        headers.append("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.append("Content-Type", "application/json");
        let options = new RequestOptions({ headers: headers });
        let body = JSON.stringify(file);
        var url = Config.system_backend_url + "/file/delete";
        return this.http.post(url, body, options).map((res: Response) => { return res.json().alert }).catch(this.handleError);
    }

    /*********  Errors *********/

    private handleError(error: any) {
        return Observable.throw({ "TransactionService": { "Code": error.status, "Message": error.message } });
    }
}
