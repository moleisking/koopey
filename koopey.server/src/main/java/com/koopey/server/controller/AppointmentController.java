package com.koopey.server.controller;

import java.util.List;
import java.util.Optional;

import com.koopey.server.data.AppointmentRepository;
import com.koopey.server.model.Appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping("create")
    public ResponseEntity<Void> putAppointment(@RequestBody Appointment appointment) {

        appointmentRepository.save(appointment);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("{appointmentId}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable("appointmentId") String appointmentId) {

        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);

        if (appointment.isPresent()){
            return new ResponseEntity<Appointment> (appointment.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<Appointment> (appointment.get(), HttpStatus.NOT_FOUND);
        }      
    }

    @GetMapping("")
    public List<Appointment> getAppointments() {

        return appointmentRepository.findAll();
    }

}