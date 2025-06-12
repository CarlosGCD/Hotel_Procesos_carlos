package com.example.restapi.unitarios.service;

import com.example.restapi.model.CheckIn;
import com.example.restapi.model.Cliente;
import com.example.restapi.model.Habitacion;
import com.example.restapi.model.Reserva;
import com.example.restapi.repository.CheckInRepository;
import com.example.restapi.repository.ReservaRepository;
import com.example.restapi.service.CheckInService;
import com.example.restapi.service.EmailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(MockitoExtension.class)
public class CheckInServiceTest {

    @Mock
    private CheckInRepository checkInRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private EmailService emailService; // AÑADIDO

    @InjectMocks
    private CheckInService checkInService;

    private CheckIn checkIn;
    private Reserva reserva;

    @BeforeEach
    public void setUp() {
        checkIn = new CheckIn();
        checkIn.setReservaId(1L);
        checkIn.setNombreHuesped("Ana");
        checkIn.setApellidosHuesped("Martínez");
        checkIn.setDocumentoTipo("DNI");
        checkIn.setDocumentoNumero("12345678A");
        checkIn.setTelefono("600123456");
        checkIn.setEmail("ana.martinez@example.com");
        checkIn.setFechaCheckIn(LocalDate.of(2025, 4, 24));
        checkIn.setFechaCheckOut(LocalDate.of(2025, 4, 27));
        checkIn.setNumHuespedes(2);
        checkIn.setMetodoPago("Tarjeta");

        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setEstado("Pendiente");

        // Inyectar el mock de EmailService manualmente
        ReflectionTestUtils.setField(checkInService, "emailService", emailService);
    }


    @Test
    public void testRealizarCheckIn_ReservaNoExiste() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        boolean resultado = checkInService.realizarCheckIn(checkIn);

        assertFalse(resultado);
        verify(reservaRepository, times(1)).findById(1L);
        verifyNoInteractions(checkInRepository);
        verifyNoInteractions(emailService);
    }

    @Test
    public void testRealizarCheckIn_ReservaYaCheckIn() {
        reserva.setEstado("CheckIn Realizado");
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        boolean resultado = checkInService.realizarCheckIn(checkIn);

        assertFalse(resultado);
        verify(reservaRepository, times(1)).findById(1L);
        verify(reservaRepository, never()).save(any());
        verify(checkInRepository, never()).save(any());
        verifyNoInteractions(emailService);
    }

    @Test
    public void testRealizarCheckIn_Exitoso_SinCliente() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any())).thenReturn(reserva);
        when(checkInRepository.save(any())).thenReturn(checkIn);

        boolean resultado = checkInService.realizarCheckIn(checkIn);

        assertTrue(resultado);
        verify(reservaRepository, times(1)).save(reserva);
        verify(checkInRepository, times(1)).save(checkIn);
        verifyNoInteractions(emailService); // No hay cliente
    }

    @Test
    public void testRealizarCheckIn_HabitacionNull() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Carlos");
        cliente.setEmail("carlos@example.com");
        reserva.setCliente(cliente);
        reserva.setHabitacion(null); // Habitacion null

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any())).thenReturn(reserva);
        when(checkInRepository.save(any())).thenReturn(checkIn);

        boolean resultado = checkInService.realizarCheckIn(checkIn);

        assertTrue(resultado);
        verify(emailService).enviarConfirmacionCheckIn(eq("carlos@example.com"), eq("Carlos"), eq("desconocida"), any());
    }

    @Test
    public void testRealizarCheckIn_ConHabitacionYCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Lucía");
        cliente.setEmail("lucia@example.com");

        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(305);

        reserva.setCliente(cliente);
        reserva.setHabitacion(habitacion);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any())).thenReturn(reserva);
        when(checkInRepository.save(any())).thenReturn(checkIn);

        boolean resultado = checkInService.realizarCheckIn(checkIn);

        assertTrue(resultado);
        verify(emailService).enviarConfirmacionCheckIn(eq("lucia@example.com"), eq("Lucía"), eq("305"), any());
    }

    @Test
    public void testCheckInSetAndGetNombreHuesped() {
        checkIn.setNombreHuesped("Laura");
        assertEquals("Laura", checkIn.getNombreHuesped());
    }

    @Test
    public void testCheckInSetAndGetNumHuespedes() {
        checkIn.setNumHuespedes(3);
        assertEquals(3, checkIn.getNumHuespedes());
    }

    @Test
    public void buscarPorReservaId_devuelveListaDeCheckIn() {
       
        Long reservaId = 8L;
        List<CheckIn> mockCheckIns = List.of(new CheckIn()); 
        when(checkInRepository.findByReservaId(reservaId)).thenReturn(mockCheckIns);

        List<CheckIn> resultado = checkInService.buscarPorReservaId(reservaId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(checkInRepository, times(1)).findByReservaId(reservaId);
    }

}
