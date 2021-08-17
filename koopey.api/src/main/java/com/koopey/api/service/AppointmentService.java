package com.koopey.api.service;

import com.koopey.api.model.entity.Appointment;
import com.koopey.api.repository.AppointmentRepository;
import com.koopey.api.repository.BaseRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService  extends BaseService <Appointment, UUID> {
    
    @Autowired
    AppointmentRepository appointmentRepository;

    BaseRepository<Appointment, UUID> getRepository() {       
        return appointmentRepository;
    }
}
