package com.predictmind.backend.PredictMindapp.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="paciente")
public class Paciente implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(nullable=false, unique=true)
	private String identificacion;
	
	@NotEmpty
	private String nombre;
	
	@Column(name="fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@NotNull(message="no puede estar vacio")
	@Column(name="fecha_ingreso")
	@Temporal(TemporalType.DATE)
	private Date fechaIngreso;
	
	@NotEmpty
	@Email
	@Column(nullable= false, unique= true)
	private String email;
	
	private String direccion;
	
	private Long numero;
	
	private String descripcion;
	
	private boolean ansiedad;
	
	private boolean depresion;
	
	@NotEmpty
	private String programa;
	
	private String facultad;
	
	private int semestre;
	
	private int edad;
	
	private String ciudad;
	
	private boolean diagnostico;
	
	private boolean prioridad;
	
	@NotNull(message="el psicologo no puede ser vacia")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="psicologo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Psicologo psicologo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Psicologo getPsicologo() {
		return psicologo;
	}

	public void setPsicologo(Psicologo psicologo) {
		this.psicologo = psicologo;
	}

	public boolean isAnsiedad() {
		return ansiedad;
	}

	public void setAnsiedad(boolean ansiedad) {
		this.ansiedad = ansiedad;
	}

	public boolean isDepresion() {
		return depresion;
	}

	public void setDepresion(boolean depresion) {
		this.depresion = depresion;
	}
	
	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public boolean isDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(boolean diagnostico) {
		this.diagnostico = diagnostico;
	}





	public boolean isPrioridad() {
		return prioridad;
	}

	public void setPrioridad(boolean prioridad) {
		this.prioridad = prioridad;
	}





	private static final long serialVersionUID = 1L;
}
