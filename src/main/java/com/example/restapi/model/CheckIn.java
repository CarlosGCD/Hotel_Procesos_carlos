package com.example.restapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "check_ins")
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reserva_id", nullable = false)
    private Long reservaId; // Cambiado de @OneToOne a @Column

    @Column(name = "nombre_huesped", nullable = false, length = 50)
    private String nombreHuesped;

    @Column(name = "apellidos_huesped", nullable = false, length = 50)
    private String apellidosHuesped;

    @Column(name = "documento_tipo", nullable = false, length = 20)
    private String documentoTipo;

    @Column(name = "documento_numero", nullable = false, length = 20)
    private String documentoNumero;

    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "fecha_check_in", nullable = false)
    private LocalDate fechaCheckIn;

    @Column(name = "fecha_check_out", nullable = false)
    private LocalDate fechaCheckOut;

    @Column(name = "num_huespedes", nullable = false)
    private Integer numHuespedes;

    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago;

    @Column(name = "preferencias", length = 200)
    private String preferencias;

    @Column(name = "comentarios", length = 500)
    private String comentarios;

    // Constructores
    public CheckIn() {}

    public CheckIn(Long reservaId, String nombreHuesped, String apellidosHuesped, String documentoTipo,
                   String documentoNumero, String telefono, String email, String direccion, LocalDate fechaCheckIn,
                   LocalDate fechaCheckOut, Integer numHuespedes, String metodoPago, String preferencias, String comentarios) {
        this.reservaId = reservaId;
        this.nombreHuesped = nombreHuesped;
        this.apellidosHuesped = apellidosHuesped;
        this.documentoTipo = documentoTipo;
        this.documentoNumero = documentoNumero;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
        this.numHuespedes = numHuespedes;
        this.metodoPago = metodoPago;
        this.preferencias = preferencias;
        this.comentarios = comentarios;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getReservaId() { return reservaId; }
    public void setReservaId(Long reservaId) { this.reservaId = reservaId; }
    public String getNombreHuesped() { return nombreHuesped; }
    public void setNombreHuesped(String nombreHuesped) { this.nombreHuesped = nombreHuesped; }
    public String getApellidosHuesped() { return apellidosHuesped; }
    public void setApellidosHuesped(String apellidosHuesped) { this.apellidosHuesped = apellidosHuesped; }
    public String getDocumentoTipo() { return documentoTipo; }
    public void setDocumentoTipo(String documentoTipo) { this.documentoTipo = documentoTipo; }
    public String getDocumentoNumero() { return documentoNumero; }
    public void setDocumentoNumero(String documentoNumero) { this.documentoNumero = documentoNumero; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public LocalDate getFechaCheckIn() { return fechaCheckIn; }
    public void setFechaCheckIn(LocalDate fechaCheckIn) { this.fechaCheckIn = fechaCheckIn; }
    public LocalDate getFechaCheckOut() { return fechaCheckOut; }
    public void setFechaCheckOut(LocalDate fechaCheckOut) { this.fechaCheckOut = fechaCheckOut; }
    public Integer getNumHuespedes() { return numHuespedes; }
    public void setNumHuespedes(Integer numHuespedes) { this.numHuespedes = numHuespedes; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public String getPreferencias() { return preferencias; }
    public void setPreferencias(String preferencias) { this.preferencias = preferencias; }
    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}