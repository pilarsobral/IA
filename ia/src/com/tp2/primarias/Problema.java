package com.tp2.primarias;

import java.util.Hashtable;

public class Problema {

		private Estado estadoInicial; 
		private Hashtable<String, Estado> estadosObjetivo; 
		private Hashtable<String, Hashtable<String, Estado>> acciones; 
		
		public Problema(Estado estadoInicial, Hashtable<String, Estado> estadosObjetivos, Hashtable<String, Hashtable<String, Estado>> acciones)
		{
			this.estadoInicial = estadoInicial; 
			this.estadosObjetivo = estadosObjetivos;
			this.acciones = acciones; 
		}

		public Estado getEstadoInicial() {
			return estadoInicial;
		}

		public Hashtable<String, Hashtable<String, Estado>> getAcciones() {
			return acciones;
		}

		public void setAcciones(Hashtable<String, Hashtable<String, Estado>> acciones) {
			this.acciones = acciones;
		}

		public void setEstadoInicial(Estado estadoInicial) {
			this.estadoInicial = estadoInicial;
		}

		public Hashtable<String, Estado> getEstadosObjetivo() {
			return estadosObjetivo;
		}

		public void setEstadosObjetivo(Hashtable<String, Estado> estadosObjetivo) {
			this.estadosObjetivo = estadosObjetivo;
		}


		public boolean esObjetivo(Estado estado)
		{
			if (estadosObjetivo == null)
				return false; 
			
			return estadosObjetivo.containsKey(estado.getNombre()); 
		}
		public Estado getResultado(Estado estado, Accion accion)
		{
			if (!acciones.containsKey(estado.getNombre()))
				return null; 
			Hashtable <String, Estado> acciones_estado = acciones.get(estado.getNombre()); 
			if (!acciones_estado.containsKey(accion.getNombre()))
				return null; 
			return acciones_estado.get(accion.getNombre()) ;
		}
}
