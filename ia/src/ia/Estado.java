package ia;

import java.util.Hashtable;

public class Estado {
	
	
	private String nombre;
	private String[] acciones; 
	private Estado padre; 
	private Estado[] hijos;
	
	
	public Estado getPadre() {
		return padre;
	}
	public void setPadre(Estado padre) {
		this.padre = padre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String[] getAcciones() {
		return acciones;
	}
	public void setAcciones(String[] acciones) {
		this.acciones = acciones;
	}
	public Estado[] getHijos() {
		return hijos;
	}
	public void setHijos(Estado[] hijos) {
		this.hijos = hijos;
	} 
	
	
}
