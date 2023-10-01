package com.tp2.primarias;

import java.util.Hashtable;

public class Estado {
	
	private String nombre; 
	private Hashtable<String, Accion> acciones; 
	
	public Estado(String nombre, Hashtable<String, Accion> acciones)
	{
		this.nombre = nombre; 
		this.acciones = acciones; 
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Hashtable<String, Accion> getAcciones() {
		return acciones;
	}

	public void setAcciones(Hashtable<String, Accion> acciones) {
		this.acciones = acciones;
	}

	
}
