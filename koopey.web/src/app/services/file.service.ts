import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "ng2-translate";
import { File } from "../models/file";
import { Config } from "../config/settings";

@Injectable()
export class FileService {
  public file = new ReplaySubject<File>();
  public files = new ReplaySubject<Array<File>>();

  public httpHeader = {
    headers: new HttpHeaders({
      Authorization: "JWT " + localStorage.getItem("token"),
      "Cache-Control": "no-cache, no-store, must-revalidate",
      "Content-Type": "application/json",
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private translateService: TranslateService
  ) {}

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

  public create(file: File): Observable<String> {
    var url = Config.system_backend_url + "/file/create";
    return this.httpClient.put<String>(url, file, this.httpHeader);
  }

  public delete(file: File): Observable<String> {
    var url = Config.system_backend_url + "/file/delete";
    return this.httpClient.post<String>(url, file, this.httpHeader);
  }

  public readFile(file: File): Observable<File> {
    var url = Config.system_backend_url + "/file/read/file/" + file.id;
    return this.httpClient.get<File>(url, this.httpHeader);
  }

  public readFiles(): Observable<Array<File>> {
    var url = Config.system_backend_url + "/file/read/files";
    return this.httpClient.get<Array<File>>(url, this.httpHeader);
  }

  public update(file: File): Observable<String> {
    var url = Config.system_backend_url + "/file/update";
    return this.httpClient.post<String>(url, file, this.httpHeader);
  }
}
