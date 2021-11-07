import { BaseService } from "./base.service";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { Appointment } from "../models/appointment";
import { Search } from "../models/search";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class AppointmentService extends BaseService {
  public appointment = new ReplaySubject<Appointment>();
  public appointments = new ReplaySubject<Array<Appointment>>();

  constructor(
    protected httpClient: HttpClient,
    protected translateService: TranslateService
  ) {
    super(httpClient, translateService);
  }

  public getAppointment(): Observable<Appointment> {
    return this.appointment.asObservable();
  }

  public setAppointment(appointment: Appointment): void {
    this.appointment.next(appointment);
  }

  public getAppointments(): Observable<Array<Appointment>> {
    return this.appointments.asObservable();
  }

  public setAppointments(appointments: Array<Appointment>): void {
    this.appointments.next(appointments);
  }

  public create(appointment: Appointment): Observable<String> {
    let url = this.baseUrl() + "/appointment/create/one";
    return this.httpClient.put<String>(url, appointment, this.privateHeader());
  }

  public createMany(appointments: Array<Appointment>): Observable<void> {
    let url = this.baseUrl() + "/appointment/create/many";
    return this.httpClient.put<void>(url, appointments, this.privateHeader());
  }

  public delete(appointment: Appointment): Observable<void> {
    let url = this.baseUrl() + "/appointment/delete";
    return this.httpClient.put<void>(url, appointment, this.privateHeader());
  }

  public deleteMany(appointments: Array<Appointment>): Observable<void> {
    let url = this.baseUrl() + "/appointment/delete/many";
    return this.httpClient.put<void>(url, appointments, this.privateHeader());
  }

  public read(appointment: Appointment): Observable<Appointment> {
    let url = this.baseUrl() + "/appointment/read/one";
    return this.httpClient.put<Appointment>(
      url,
      appointment,
      this.privateHeader()
    );
  }

  public search(): Observable<Array<Appointment>> {
    let url = this.baseUrl() + "/appointment/read/many";
    return this.httpClient.put<Array<Appointment>>(url, this.privateHeader());
  }

  public searchBetweenDates(search: Search): Observable<Array<Appointment>> {
    let url = this.baseUrl() + "/appointment/read/many/between/dates";
    return this.httpClient.post<Array<Appointment>>(
      url,
      search,
      this.privateHeader()
    );
  }

  public searchByUser(search: Search): Observable<Array<Appointment>> {
    let url = this.baseUrl() + "/appointment/read/many/mine";
    return this.httpClient.get<Array<Appointment>>(url, this.privateHeader());
  }

  public searchMyAppointmentsBetweenDates(
    search: Search
  ): Observable<Array<Appointment>> {
    let url = this.baseUrl() + "/appointment/read/many/between/dates/mine";
    return this.httpClient.post<Array<Appointment>>(
      url,
      search,
      this.privateHeader()
    );
  }

  public updateAppointment(appointment: Appointment): Observable<String> {
    let url = this.baseUrl() + "/appointment/update";
    return this.httpClient.post<String>(url, appointment, this.privateHeader());
  }

  public updateAppointments(
    appointment: Array<Appointment>
  ): Observable<String> {
    let url = this.baseUrl() + "/appointment/update";
    return this.httpClient.post<String>(url, appointment, this.privateHeader());
  }
}
