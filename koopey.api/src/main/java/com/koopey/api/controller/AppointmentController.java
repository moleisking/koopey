package com.koopey.api.controller;

import com.koopey.api.model.entity.Appointment;
import com.koopey.api.service.AppointmentService;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("appointment")
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;

    @PutMapping(value ="create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> create(@RequestBody Appointment appointment) {      
        appointmentService.save(appointment);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @PostMapping(value ="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Appointment appointment) {      
        appointmentService.delete(appointment);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value ="update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Appointment appointment) {          
        appointmentService.save(appointment);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value ="read/{appointmentId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Appointment> read(@PathVariable("appointmentId") UUID appointmentId) {

        Optional<Appointment> appointment = appointmentService.findById(appointmentId);

        if (appointment.isPresent()){
            return new ResponseEntity<Appointment> (appointment.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<Appointment> (appointment.get(), HttpStatus.NOT_FOUND);
        }      
    }

    @PostMapping(value ="search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Appointment>> search(@RequestBody Appointment appointment) {

        List<Appointment> appointments= appointmentService.findAll();     

        if (appointments.isEmpty()) {
            return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);           
        }
        
    }

}