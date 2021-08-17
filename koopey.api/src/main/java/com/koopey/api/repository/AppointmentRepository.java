package com.koopey.api.repository;

import com.koopey.api.model.entity.Appointment;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends BaseRepository<Appointment, UUID> {

}