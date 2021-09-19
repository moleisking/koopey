import { BaseService } from "./base.service";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { Environment } from "../../environments/environment";
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
    var url = this.getApiUrl() + "/appointment/create/one";
    return this.httpClient.put<String>(url, appointment, this.httpHeader);
  }

  public createAppointments(
    appointments: Array<Appointment>
  ): Observable<String> {
    var url = this.getApiUrl() + "/appointment/create/many";
    return this.httpClient.put<String>(url, appointments, this.httpHeader);
  }

  public deleteAppointment(appointment: Appointment): Observable<String> {
    var url = this.getApiUrl() + "/appointment/delete/one";
    return this.httpClient.put<String>(url, appointment, this.httpHeader);
  }

  public deleteAppointments(
    appointments: Array<Appointment>
  ): Observable<String> {
    var url = this.getApiUrl() + "/appointment/delete/many";
    return this.httpClient.put<String>(url, appointments, this.httpHeader);
  }

  public readAppointment(appointment: Appointment): Observable<Appointment> {
    var url = this.getApiUrl() + "/appointment/read/one";
    return this.httpClient.put<Appointment>(url, appointment, this.httpHeader);
  }

  public readAppointments(): Observable<Array<Appointment>> {
    var url = this.getApiUrl() + "/appointment/read/many";
    return this.httpClient.put<Array<Appointment>>(url, this.httpHeader);
  }

  public readAppointmentsBetweenDates(
    search: Search
  ): Observable<Array<Appointment>> {
    var url = this.getApiUrl() + "/appointment/read/many/between/dates";
    return this.httpClient.post<Array<Appointment>>(
      url,
      search,
      this.httpHeader
    );
  }

  public readUserAppointment(): Observable<Appointment> {
    var url = this.getApiUrl() + "/appointment/read/one/mine";
    return this.httpClient.get<Appointment>(url, this.httpHeader);
  }

  public readUserAppointments(search: Search): Observable<Array<Appointment>> {
    var url = this.getApiUrl() + "/appointment/read/many/mine";
    return this.httpClient.get<Array<Appointment>>(url, this.httpHeader);
  }

  public readMyAppointmentsBetweenDates(
    search: Search
  ): Observable<Array<Appointment>> {
    var url = this.getApiUrl() + "/appointment/read/many/between/dates/mine";
    return this.httpClient.post<Array<Appointment>>(
      url,
      search,
      this.httpHeader
    );
  }

  public updateAppointment(appointment: Appointment): Observable<String> {
    var url = this.getApiUrl() + "/appointment/update";
    return this.httpClient.post<String>(url, appointment, this.httpHeader);
  }

  public updateAppointments(
    appointment: Array<Appointment>
  ): Observable<String> {
    var url = this.getApiUrl() + "/appointment/update";
    return this.httpClient.post<String>(url, appointment, this.httpHeader);
  }
}
