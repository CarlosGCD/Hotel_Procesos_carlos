package com.example.restapi.unitarios.controller;

import com.example.restapi.controller.CheckInController;
import com.example.restapi.model.CheckIn;
import com.example.restapi.service.CheckInService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Collections;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.HttpStatus;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CheckInControllerTest {

    @Mock
    private CheckInService checkInService;

    @InjectMocks
    private CheckInController checkInController;

    private MockMvc mockMvc;
    private CheckIn datosCheckIn;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(checkInController).build();
        datosCheckIn = new CheckIn();
        datosCheckIn.setReservaId(1L);
        datosCheckIn.setNombreHuesped("Juan");
        datosCheckIn.setApellidosHuesped("Pérez");
        datosCheckIn.setDocumentoTipo("DNI");
        datosCheckIn.setDocumentoNumero("12345678");
        datosCheckIn.setTelefono("123456789");
        datosCheckIn.setEmail("juan@example.com");
        datosCheckIn.setFechaCheckIn(LocalDate.now().plusDays(1));
        datosCheckIn.setFechaCheckOut(LocalDate.now().plusDays(3));
        datosCheckIn.setNumHuespedes(2);
        datosCheckIn.setMetodoPago("Tarjeta");
    }

    @Test
    public void testRealizarCheckIn_Exitoso() throws Exception {
        when(checkInService.realizarCheckIn(any(CheckIn.class))).thenReturn(true);

        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Check-in realizado con éxito."));

        verify(checkInService, times(1)).realizarCheckIn(any(CheckIn.class));
    }

    @Test
    public void testRealizarCheckIn_ReservaIdNulo() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Faltan campos obligatorios en los datos del check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_NombreHuespedNulo() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Faltan campos obligatorios en los datos del check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_ApellidosHuespedNulo() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Faltan campos obligatorios en los datos del check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_DocumentoTipoNulo() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Faltan campos obligatorios en los datos del check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_DocumentoNumeroNulo() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Faltan campos obligatorios en los datos del check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_TelefonoNulo() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Faltan campos obligatorios en los datos del check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_EmailNulo() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Faltan campos obligatorios en los datos del check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_FechaCheckInNulo() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Faltan campos obligatorios en los datos del check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_FechaCheckOutNulo() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Faltan campos obligatorios en los datos del check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_NumHuespedesNulo() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Faltan campos obligatorios en los datos del check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_MetodoPagoNulo() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Faltan campos obligatorios en los datos del check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_FechaCheckInAnterior() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().minusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La fecha de check-in no puede ser anterior a hoy."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_FechaCheckInHoy() throws Exception {
        when(checkInService.realizarCheckIn(any(CheckIn.class))).thenReturn(true);

        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now() + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Check-in realizado con éxito."));

        verify(checkInService, times(1)).realizarCheckIn(any(CheckIn.class));
    }

    @Test
    public void testRealizarCheckIn_FechaCheckOutIgualCheckIn() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(1) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La fecha de check-out debe ser posterior a la fecha de check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_FechaCheckOutAnteriorCheckIn() throws Exception {
        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(3) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(1) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La fecha de check-out debe ser posterior a la fecha de check-in."));

        verifyNoInteractions(checkInService);
    }

    @Test
    public void testRealizarCheckIn_ReservaNoValida() throws Exception {
        when(checkInService.realizarCheckIn(any(CheckIn.class))).thenReturn(false);

        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se pudo realizar el check-in. Verifica los datos y que la reserva exista."));

        verify(checkInService, times(1)).realizarCheckIn(any(CheckIn.class));
    }

    @Test
    public void testRealizarCheckIn_ErrorInesperado() throws Exception {
        when(checkInService.realizarCheckIn(any(CheckIn.class)))
                .thenThrow(new RuntimeException("Error en el servicio"));

        mockMvc.perform(post("/api/reservas/checkin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reservaId\":1,\"nombreHuesped\":\"Juan\",\"apellidosHuesped\":\"Pérez\",\"documentoTipo\":\"DNI\",\"documentoNumero\":\"12345678\",\"telefono\":\"123456789\",\"email\":\"juan@example.com\",\"fechaCheckIn\":\"" + LocalDate.now().plusDays(1) + "\",\"fechaCheckOut\":\"" + LocalDate.now().plusDays(3) + "\",\"numHuespedes\":2,\"metodoPago\":\"Tarjeta\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error inesperado: Error en el servicio"));

        verify(checkInService, times(1)).realizarCheckIn(any(CheckIn.class));
    }

    @Test
    public void buscarPorReserva_conResultados_devuelveListaDeCheckIn() {

        Long reservaId = 8L;
        List<CheckIn> mockCheckIns = List.of(new CheckIn()); 
        when(checkInService.buscarPorReservaId(reservaId)).thenReturn(mockCheckIns);
     
        ResponseEntity<List<CheckIn>> response = checkInController.buscarPorReserva(reservaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void buscarPorReserva_sinResultados_devuelveNoContent() {
       
        Long reservaId = 999L;
        when(checkInService.buscarPorReservaId(reservaId)).thenReturn(List.of());

        ResponseEntity<List<CheckIn>> response = checkInController.buscarPorReserva(reservaId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

}