package com.predictmind.backend.PredictMindapp.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="pregunta")
public class Pregunta implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(nullable=false, unique=true)
	private String pregunta;
	
	@NotEmpty
	@Column(nullable= false)
	private String area;
	
	private boolean prediccion;
	
	private boolean prioridad;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public boolean isPrediccion() {
		return prediccion;
	}

	public void setPrediccion(boolean prediccion) {
		this.prediccion = prediccion;
	}

	public boolean isPrioridad() {
		return prioridad;
	}

	public void setPrioridad(boolean prioridad) {
		this.prioridad = prioridad;
	}



	private static final long serialVersionUID = 1L;
}
