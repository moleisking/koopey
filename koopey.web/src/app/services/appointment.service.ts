import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, ReplaySubject } from "rxjs";
import { TranslateService } from "ng2-translate";
import { Alert } from "../models/alert";
import { Config } from "../config/settings";
import { Appointment } from "../models/appointment";
import { Search } from "../models/search";

@Injectable()
export class AppointmentService {
  private static LOG_HEADER: string = "EVENT:SERVICE:";
  public appointment = new ReplaySubject<Appointment>();
  public appointments = new ReplaySubject<Array<Appointment>>();

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
    var url = Config.system_backend_url + "/appointment/create/one";
    return this.httpClient.put<String>(url, appointment, this.httpHeader);
  }

  public createAppointments(
    appointments: Array<Appointment>
  ): Observable<String> {
    var url = Config.system_backend_url + "/appointment/create/many";
    return this.httpClient.put<String>(url, appointments, this.httpHeader);
  }

  public deleteAppointment(appointment: Appointment): Observable<String> {
    var url = Config.system_backend_url + "/appointment/delete/one";
    return this.httpClient.put<String>(url, appointment, this.httpHeader);
  }

  public deleteAppointments(
    appointments: Array<Appointment>
  ): Observable<String> {
    var url = Config.system_backend_url + "/appointment/delete/many";
    return this.httpClient.put<String>(url, appointments, this.httpHeader);
  }

  public readAppointment(appointment: Appointment): Observable<Appointment> {
    var url = Config.system_backend_url + "/appointment/read/one";
    return this.httpClient.put<Appointment>(url, appointment, this.httpHeader);
  }

  public readAppointments(): Observable<Array<Appointment>> {
    var url = Config.system_backend_url + "/appointment/read/many";
    return this.httpClient.put<Array<Appointment>>(url, this.httpHeader);
  }

  public readAppointmentsBetweenDates(
    search: Search
  ): Observable<Array<Appointment>> {
    var url =
      Config.system_backend_url + "/appointment/read/many/between/dates";
    return this.httpClient.post<Array<Appointment>>(
      url,
      search,
      this.httpHeader
    );
  }

  public readUserAppointment(): Observable<Appointment> {
    var url = Config.system_backend_url + "/appointment/read/one/mine";
    return this.httpClient.get<Appointment>(url, this.httpHeader);
  }

  public readUserAppointments(search: Search): Observable<Array<Appointment>> {
    var url = Config.system_backend_url + "/appointment/read/many/mine";
    return this.httpClient.get<Array<Appointment>>(url, this.httpHeader);
  }

  public readMyAppointmentsBetweenDates(
    search: Search
  ): Observable<Array<Appointment>> {
    var url =
      Config.system_backend_url + "/appointment/read/many/between/dates/mine";
    return this.httpClient.post<Array<Appointment>>(
      url,
      search,
      this.httpHeader
    );
  }

  public updateAppointment(appointment: Appointment): Observable<String> {
    var url = Config.system_backend_url + "/appointment/update";
    return this.httpClient.post<String>(url, appointment, this.httpHeader);
  }

  public updateAppointments(
    appointment: Array<Appointment>
  ): Observable<String> {
    var url = Config.system_backend_url + "/appointment/update";
    return this.httpClient.post<String>(url, appointment, this.httpHeader);
  }
}
