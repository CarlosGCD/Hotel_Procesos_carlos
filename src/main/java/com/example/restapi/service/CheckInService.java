package com.example.restapi.service;

import java.util.List; //
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.restapi.model.CheckIn;
import com.example.restapi.model.Reserva;
import com.example.restapi.repository.CheckInRepository;
import com.example.restapi.repository.ReservaRepository;

@Service
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final ReservaRepository reservaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    public CheckInService(CheckInRepository checkInRepository, ReservaRepository reservaRepository) {
        this.checkInRepository = checkInRepository;
        this.reservaRepository = reservaRepository;
    }

    public boolean realizarCheckIn(CheckIn datosCheckIn) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(datosCheckIn.getReservaId());
        if (reservaOpt.isEmpty()) {
            return false;
        }

        Reserva reserva = reservaOpt.get();
        if ("CheckIn Realizado".equals(reserva.getEstado())) {
            return false;
        }

        reserva.setEstado("CheckIn Realizado");
        reservaRepository.save(reserva);
        checkInRepository.save(datosCheckIn);

        
        if (reserva.getCliente() != null && reserva.getCliente().getEmail() != null) {
            String email = reserva.getCliente().getEmail();
            String nombre = reserva.getCliente().getNombre();
            String numeroHabitacion = reserva.getHabitacion() != null ? String.valueOf(reserva.getHabitacion().getNumero()) : "desconocida";
            String fecha = datosCheckIn.getFechaCheckIn() != null ? datosCheckIn.getFechaCheckIn().toString() : "hoy";
            emailService.enviarConfirmacionCheckIn(email, nombre, numeroHabitacion, fecha);
        }

        return true;
    }

    public List<CheckIn> buscarPorReservaId(Long reservaId) {
        return checkInRepository.findByReservaId(reservaId);
    }

}