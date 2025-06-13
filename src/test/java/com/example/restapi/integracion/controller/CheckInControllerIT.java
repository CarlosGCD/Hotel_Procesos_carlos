package com.example.restapi.integracion.controller;

import com.example.restapi.model.CheckIn;
import com.example.restapi.model.Reserva;
import com.example.restapi.repository.CheckInRepository;
import com.example.restapi.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.example.restapi.model.Habitacion;


import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CheckInControllerIT {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ReservaRepository reservaRepository;

   @Autowired
   private CheckInRepository checkInRepository;

   @Test
   void testBuscarPorReserva_conResultados() throws Exception {
        Habitacion habitacion = new Habitacion();
        setField(habitacion, "numero", "101");
        setField(habitacion, "tipo", "Doble");
        setField(habitacion, "precioPorNoche", 75.0);
     
      Reserva reserva = new Reserva(null, habitacion, LocalDate.now(), LocalDate.now().plusDays(2), "Reservada");
      reserva = reservaRepository.save(reserva);

  
      CheckIn checkIn = new CheckIn();

  
      setField(checkIn, "reserva", reserva);
      setField(checkIn, "fechaCheckIn", LocalDate.now());
      setField(checkIn, "fechaCheckOut", LocalDate.now().plusDays(2));
      setField(checkIn, "totalPagar", 200.0);
      setField(checkIn, "estado", "Confirmada");
      setField(checkIn, "metodoPago", "Tarjeta");

      checkIn = checkInRepository.save(checkIn);

  
      mockMvc.perform(MockMvcRequestBuilders
            .get("/api/checkin/buscarPorReserva")
            .param("reservaId", reserva.getId().toString()))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$[0].id").value(checkIn.getId()))
         .andExpect(jsonPath("$[0].reserva.id").value(reserva.getId()));
   }

   @Test
   void testBuscarPorReserva_sinResultados() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders
            .get("/api/checkin/buscarPorReserva")
            .param("reservaId", "999999"))
         .andExpect(status().isNotFound());
   }

   
   private void setField(Object target, String fieldName, Object value) throws Exception {
      Field field = target.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(target, value);
   }
}
