import { Injectable } from "@angular/core";
import { Observable, ReplaySubject } from "rxjs";
import { File } from "../models/file";
import { Environment } from "src/environments/environment";
import { BaseService } from "./base.service";

@Injectable()
export class FileService extends BaseService {
  public file = new ReplaySubject<File>();
  public files = new ReplaySubject<Array<File>>();

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
    var url = Environment.ApiUrls.KoopeyApiUrl + "/file/create";
    return this.httpClient.put<String>(url, file, this.httpHeader);
  }

  public delete(file: File): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/file/delete";
    return this.httpClient.post<String>(url, file, this.httpHeader);
  }

  public readFile(file: File): Observable<File> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/file/read/file/" + file.id;
    return this.httpClient.get<File>(url, this.httpHeader);
  }

  public readFiles(): Observable<Array<File>> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/file/read/files";
    return this.httpClient.get<Array<File>>(url, this.httpHeader);
  }

  public update(file: File): Observable<String> {
    var url = Environment.ApiUrls.KoopeyApiUrl + "/file/update";
    return this.httpClient.post<String>(url, file, this.httpHeader);
  }
}
