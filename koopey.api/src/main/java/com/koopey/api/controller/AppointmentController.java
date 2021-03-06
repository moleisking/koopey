package com.koopey.api.controller;

import com.koopey.api.model.Appointment;
import com.koopey.api.repository.AppointmentRepository;

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
@RequestMapping("appointments")
public class AppointmentController {

    private static Logger LOGGER = Logger.getLogger(AppointmentController.class.getName());

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PutMapping(value ="create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> create(@RequestBody Appointment appointment) {
        LOGGER.log(Level.INFO, "createAppointment(" + appointment.getId() + ")");
        appointmentRepository.save(appointment);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @PostMapping(value ="delete", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestBody Appointment appointment) {
        LOGGER.log(Level.INFO, "delete(" + appointment.getId() + ")");
        appointmentRepository.delete(appointment);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value ="update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> update(@RequestBody Appointment appointment) {
        LOGGER.log(Level.INFO, "delete(" + appointment.getId() + ")");      
        appointmentRepository.save(appointment);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value ="read/{appointmentId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Appointment> read(@PathVariable("appointmentId") UUID appointmentId) {

        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);

        if (appointment.isPresent()){
            return new ResponseEntity<Appointment> (appointment.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<Appointment> (appointment.get(), HttpStatus.NOT_FOUND);
        }      
    }

    @PostMapping(value ="search", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
        MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Appointment>> search(@RequestBody Appointment appointment) {
        return new ResponseEntity<List<Appointment>>(appointmentRepository.findAll(), HttpStatus.OK);
    }

}