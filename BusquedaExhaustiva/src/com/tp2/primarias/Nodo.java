package com.tp2.primarias;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Nodo 
{
	private Estado estado; 
	private Accion accion; 
	private Hashtable<String, Estado> acciones; 
	private Nodo padre; 
	private ArrayList<Nodo> hijos; 
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Accion getAccion() {
		return accion;
	}
	public void setAccion(Accion accion) {
		this.accion = accion;
	}
	public Hashtable<String, Estado> getAcciones() {
		return acciones;
	}
	public void setAcciones(Hashtable<String, Estado> acciones) {
		this.acciones = acciones;
	}
	public Nodo getPadre() {
		return padre;
	}
	public void setPadre(Nodo padre) {
		this.padre = padre;
	}
	public ArrayList<Nodo> getHijos() {
		return hijos;
	}
	public void setHijos(ArrayList<Nodo> hijos) {
		this.hijos = hijos;
	}
	public Nodo (Estado estado, Accion accion, Hashtable<String, Estado>acciones, Nodo padre)
	{
		this.estado = estado;
		this.accion = accion; 
		this.acciones = acciones; 
		this.padre = padre; 
		this.hijos = new ArrayList<Nodo>();
	}
	public Nodo (Estado estado)
	{
		this.estado = estado;
		this.accion = null; 
		this.acciones = null; 
		this.padre = null; 
		this.hijos = new ArrayList<Nodo>();
	}
	
	public  ArrayList<Nodo> expandir(Problema problema)
	{
		hijos = new ArrayList<Nodo>() ;
		if (acciones == null)
		{
			if (!problema.getAcciones().containsKey(this.estado.getNombre()))
				return hijos; 
			acciones = problema.getAcciones().get(this.estado.getNombre());
		}
		
		Enumeration accion = acciones.keys();
		
		while (accion.hasMoreElements())
		{
			Accion accionHijo = new Accion((String)accion.nextElement()) ;
			Estado nuevoEstado = problema.getResultado(estado, accionHijo);
			Hashtable<String, Estado> accionesNuevo = new Hashtable<String, Estado>(); 
			if (problema.getAcciones().containsKey(nuevoEstado.getNombre()))
				accionesNuevo = problema.getAcciones().get( nuevoEstado.getNombre()); 
			Nodo hijo = new Nodo(nuevoEstado, accionHijo, accionesNuevo, this);
			hijos.add(hijo); 
		}
		return hijos; 
	}
}
