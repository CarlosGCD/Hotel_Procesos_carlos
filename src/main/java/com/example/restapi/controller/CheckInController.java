package com.example.restapi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List; //
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping; //
import org.springframework.web.bind.annotation.PathVariable; //
import org.springframework.web.bind.annotation.RequestParam; //
import com.example.restapi.model.CheckIn;
import com.example.restapi.service.CheckInService;
import com.example.restapi.repository.CheckInRepository; //

@RestController
@RequestMapping("/api/reservas")
public class CheckInController {

    private final CheckInService checkInService;

    @Autowired //
    private CheckInRepository checkInRepository; //


    @Autowired
    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping(value = "/checkin", consumes = "application/json")
    public ResponseEntity<String> realizarCheckIn(@RequestBody CheckIn datosCheckIn) {
        try {
            // Validar campos obligatorios
            if (datosCheckIn.getReservaId() == null ||
                datosCheckIn.getNombreHuesped() == null || datosCheckIn.getApellidosHuesped() == null ||
                datosCheckIn.getDocumentoTipo() == null || datosCheckIn.getDocumentoNumero() == null ||
                datosCheckIn.getTelefono() == null || datosCheckIn.getEmail() == null ||
                datosCheckIn.getFechaCheckIn() == null || datosCheckIn.getFechaCheckOut() == null ||
                datosCheckIn.getNumHuespedes() == null || datosCheckIn.getMetodoPago() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan campos obligatorios en los datos del check-in.");
            }

            // Validar fechas
            LocalDate fechaCheckIn = datosCheckIn.getFechaCheckIn();
            LocalDate fechaCheckOut = datosCheckIn.getFechaCheckOut();
            LocalDate hoy = LocalDate.now();
            if (fechaCheckIn.isBefore(hoy)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La fecha de check-in no puede ser anterior a hoy.");
            }
            if (fechaCheckOut.isBefore(fechaCheckIn) || fechaCheckOut.equals(fechaCheckIn)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La fecha de check-out debe ser posterior a la fecha de check-in.");
            }

            boolean exito = checkInService.realizarCheckIn(datosCheckIn);

            if (exito) {
                return ResponseEntity.ok("Check-in realizado con éxito.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo realizar el check-in. Verifica los datos y que la reserva exista.");
            }
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las fechas deben estar en formato YYYY-MM-DD.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error inesperado: " + e.getMessage());
        }
    }

    
    @GetMapping("/buscarPorReserva")
    public ResponseEntity<List<CheckIn>> buscarPorReserva(@RequestParam Long reservaId) {
        List<CheckIn> reservas = checkInService.buscarPorReservaId(reservaId);
        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservas);
    }

}