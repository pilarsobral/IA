package ia;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.tp2.primarias.Accion;
import com.tp2.primarias.Estado;
import com.tp2.primarias.Nodo;
import com.tp2.primarias.Problema;

/***
 * los nodos de la posicion del Motor Son
 * 
 * @author fliab
 *
 */
public class posicionarMotorHeu {

	public static void main(String[] args) {
		
		/* Se inicializan los nodos de Busqueda */
		Problema problema = inicializar("B0");
		
		Nodo resultado = busquedaAEstrella(problema); 
		publicarSolucion(problema, resultado);
	}
	
	public static Nodo busquedaAEstrella(Problema problema)
	{
		Nodo raiz = crearNodoRaiz(problema);

		ArrayList<Nodo> frontera = new ArrayList<Nodo>(); 
		frontera.add(raiz);  
		ArrayList<Estado> explorados = new ArrayList<Estado>(); 
		while (true)
		{
			if (frontera.size() == 0)
				return null;  //si no quedan nodos en la frontera, no se encontro solucion
			Nodo nodo = frontera.get(0);  // se toma el primer nodo de la frontera
			frontera.remove(0); 
			if (problema.esObjetivo(nodo.getEstado())) // si es el objetivo lo devuelve
				return nodo; 

			explorados.add(nodo.getEstado()); // pasa el nodo de la frontera a los explorados
			if (nodo.getAcciones() == null ) // en caso que el nodo sea hoja, o sea que no tenga acciones, continua con el siguiente nodo
				continue; 

			Enumeration acciones = nodo.getAcciones().keys();
			while (acciones.hasMoreElements()) //por cada una de las acciones evalua el siguiente paso a hacer
			{
				Accion accion = new Accion((String)acciones.nextElement());
				Nodo hijo = crearNodoHijo(problema, nodo, accion); 
				
				boolean estaEnExpl = false;
				boolean estaEnFront = false;
				if (explorados.contains(hijo.getEstado()))
						estaEnExpl = true; 
				
				for (int i=0;i<frontera.size();i++) // busca el nodo en la frontera. 
				{
					//en caso de encontrarlo, se fija si el que esta en la frontera tiene menor valor o no. si tiene menor valor > reemplaza el nodo por este. 
					if (frontera.get(i).getEstado().getNombre().equals(hijo.getEstado().getNombre())) 
					{
						estaEnFront = true; 
						if (hijo.getValor() < frontera.get(i).getValor())
							frontera.set(i, hijo) ;
						break;
					}
				}
				/* si no esta ni en la frontera ni en explorados, lo reemplaza*/
				if (!estaEnExpl && !estaEnFront)
					frontera.add(hijo);
				/* lo ordena por el valor */
				frontera.sort((o1,o2) -> Integer.compare(o1.getValor(), o2.getValor()));
			}
		}
		
	}
	
	/**
	 * Se crea el nodo de inicio del problema. 
	 * @param problema Esquema de todos los posibles movimientos desde un estado.
	 * @return Devuelve el nodo de inicio de la busqueda. Posicion en la que se encuentra el motor
	 */
	private static Nodo crearNodoRaiz(Problema problema)
	{
		Estado estadoRaiz = problema.getEstadoInicial();
		Hashtable<String, Estado> accionesRaiz = new Hashtable<String, Estado> () ;
		if (problema.getAcciones().containsKey(estadoRaiz.getNombre()))
			accionesRaiz = problema.getAcciones().get(estadoRaiz.getNombre()); 
		Nodo raiz = new Nodo (estadoRaiz, null, accionesRaiz, null);
		raiz.setCoste(0);
		return raiz; 
	}
	/**
	 * Se crean el nodo hijo. a partir de un estado y una accion, devuelve el estado que el que va a quedar el motor. 
	 * @param problema Esquema de todos los posibles movimientos desde un estado. 
	 * @param padre Nodo origen del movimiento
	 * @param accion Accion que se va a hacer, a la izquierda o derecha
	 * @return devuelve el nodo resultado. Posicion en la que va a quedar el motor.
	 */
	private static Nodo crearNodoHijo(Problema problema, Nodo padre, Accion accion)
	{
		Estado nuevoEstado = problema.getResultado(padre.getEstado(), accion); 
		Hashtable<String, Estado> nuevasAcciones = new Hashtable<String, Estado> () ;
		if (problema.getAcciones().containsKey(nuevoEstado.getNombre()))
			nuevasAcciones = problema.getAcciones().get(nuevoEstado.getNombre()); 
		Nodo hijo = new Nodo (nuevoEstado, accion, nuevasAcciones, padre);
		hijo.setCoste(padre.getCoste() + problema.getCosteAccion(padre.getEstado(), accion));
		padre.getHijos().add(hijo); 
		return hijo; 
	}
	/**
	 * A partir del nodo imprime la cadena de estados y movimientos que se hicieron para llegar desde el estado inicial a la posicion correcta
	 * @param nodo
	 */
	private static void publicarSolucion(Problema problema, Nodo nodo)
	{
		if (nodo == null)
		{
			System.out.println("NO HAY SOLUCION");
			return; 
		}

		String mensaje = ""; 
		Nodo auxNodo = nodo; 
		System.out.println("Estado: " + nodo.getEstado().getNombre() + " COSTO TOTAL: " + nodo.getCoste());
		while (auxNodo != null)
		{
			mensaje =  " Estado: " + auxNodo.getEstado().getNombre() + mensaje ; 
			if (auxNodo.getAccion() != null)
				mensaje =  " (" + auxNodo.getAccion().getNombre() + "[" + problema.getCosteAccion(auxNodo.getPadre().getEstado(), auxNodo.getAccion()) +"]) > " + mensaje; 
			auxNodo = auxNodo.getPadre();
		}
		System.out.println(mensaje); 
	}
	
	/**
	 * Inicializa el arbol de busqueda. Se cargan los estados (posiciones) posibles y los movimientos que se pueden hacer a partir de un estado .
	 * @param origen Posicion origen del motor. Este va a ser el nodo raiz, la posicion desde la que se debe llegar a la posicion A.
	 * @return
	 */
	private static Problema  inicializar(String origen)
	{
		/* se cargan las acciones posibles. Al ser un solo eje, solo se puede mover para la izquierda o derecha*/
		Accion accIzq = new Accion("I"); 
		Accion accDer = new Accion("D");
		
		/* a cada estado se le cargan los posibles movimientos, y el estado en el que quedaria el motor */
		Hashtable<String, Accion> htB0 = new Hashtable<String, Accion>(); 
		htB0.put("I", accIzq); 
		Estado b0 = new Estado("B0", htB0); 
		
		Hashtable<String, Accion> htB1 = new Hashtable<String, Accion>(); 
		htB1.put("I", accIzq); 
		htB1.put("D", accDer); 
		Estado b1 = new Estado("B1", htB1); 
		
		Hashtable<String, Accion> htB2 = new Hashtable<String, Accion>(); 
		htB2.put("I", accIzq); 
		htB2.put("D", accDer); 
		Estado b2 = new Estado("B2", htB2); 
		
		Hashtable<String, Accion> htB3 = new Hashtable<String, Accion>(); 
		htB3.put("I", accIzq); 
		htB3.put("D", accDer); 
		Estado b3 = new Estado("B3", htB3); 
		
		Hashtable<String, Accion> htB4 = new Hashtable<String, Accion>(); 
		htB4.put("I", accIzq); 
		htB4.put("D", accDer); 
		Estado b4 = new Estado("B4", htB4); 
		
		Hashtable<String, Accion> htB5 = new Hashtable<String, Accion>(); 
		htB5.put("I", accIzq); 
		htB5.put("D", accDer); 
		Estado b5 = new Estado("B5", htB5); 
		
		Hashtable<String, Accion> htB6 = new Hashtable<String, Accion>(); 
		htB6.put("I", accIzq); 
		htB6.put("D", accDer); 
		Estado b6 = new Estado("B6", htB6); 
		
		Hashtable<String, Accion> htA = new Hashtable<String, Accion>(); 
		htA.put("I", accIzq); 
		htA.put("D", accDer); 
		Estado a = new Estado("A", htA); 

		Hashtable<String, Hashtable<String, Estado>> posiciones = new Hashtable<String, Hashtable<String, Estado>>(); 
		
		Hashtable<String, Estado> posResultadoB0 = new Hashtable<String, Estado>(); 
		posResultadoB0.put("I", b1); 

		Hashtable<String, Estado> posResultadoB1 = new Hashtable<String, Estado>(); 
		posResultadoB1.put("I", b2); 
		posResultadoB1.put("D", b1); 

		Hashtable<String, Estado> posResultadoB2 = new Hashtable<String, Estado>(); 
		posResultadoB2.put("I", b3); 
		posResultadoB2.put("D", b2); 

		Hashtable<String, Estado> posResultadoB3 = new Hashtable<String, Estado>(); 
		posResultadoB3.put("I", b4); 
		posResultadoB3.put("D", b2); 

		Hashtable<String, Estado> posResultadoB4 = new Hashtable<String, Estado>(); 
		posResultadoB4.put("I", b5); 
		posResultadoB4.put("D", b3); 

		Hashtable<String, Estado> posResultadoB5 = new Hashtable<String, Estado>(); 
		posResultadoB5.put("I", b6); 
		posResultadoB5.put("D", b4); 

		Hashtable<String, Estado> posResultadoB6 = new Hashtable<String, Estado>(); 
		posResultadoB5.put("I", a); 
		posResultadoB6.put("D", b5); 

		posiciones.put("B0", posResultadoB0); 
		posiciones.put("B1", posResultadoB1); 
		posiciones.put("B2", posResultadoB2); 
		posiciones.put("B3", posResultadoB3); 
		posiciones.put("B4", posResultadoB4); 
		posiciones.put("B5", posResultadoB5); 
		posiciones.put("B6", posResultadoB6); 
		posiciones.put("A", new Hashtable<String, Estado> ()); 
		
		
		/* costes */

		Hashtable<String, Hashtable<String, Integer>> costes = new Hashtable<String, Hashtable<String, Integer>>(); 
		
		Hashtable<String, Integer> cosResultadoB0 = new Hashtable<String, Integer>(); 
		cosResultadoB0.put("I", 1); 

		Hashtable<String, Integer> cosResultadoB1 = new Hashtable<String, Integer>(); 
		cosResultadoB1.put("I", 1 ); 
		cosResultadoB1.put("D", 1 ); 

		Hashtable<String, Integer> cosResultadoB2 = new Hashtable<String, Integer>(); 
		cosResultadoB2.put("I", 1 ); 
		cosResultadoB2.put("D", 1 ); 

		Hashtable<String, Integer> cosResultadoB3 = new Hashtable<String, Integer>(); 
		cosResultadoB3.put("I", 1 ); 
		cosResultadoB3.put("D", 1 ); 

		Hashtable<String, Integer> cosResultadoB4 = new Hashtable<String, Integer>(); 
		cosResultadoB4.put("I", 1 ); 
		cosResultadoB4.put("D", 1 ); 

		Hashtable<String, Integer> cosResultadoB5 = new Hashtable<String, Integer>(); 
		cosResultadoB5.put("I", 1 ); 
		cosResultadoB5.put("D", 1 ); 

		Hashtable<String, Integer> cosResultadoB6 = new Hashtable<String, Integer>(); 
		cosResultadoB5.put("I", 1 ); 
		cosResultadoB6.put("D", 1 ); 

		costes.put("B0", cosResultadoB0); 
		costes.put("B1", cosResultadoB1); 
		costes.put("B2", cosResultadoB2); 
		costes.put("B3", cosResultadoB3); 
		costes.put("B4", cosResultadoB4); 
		costes.put("B5", cosResultadoB5); 
		costes.put("B6", cosResultadoB6); 
		costes.put("A", new Hashtable<String, Integer> ()); 
		
		Hashtable res = new Hashtable();
		res.put("A", a);
		
		Estado estadoInicial = null; 
		
		if (origen.trim().equals("B0"))
			estadoInicial = b0; 
		else if (origen.trim().equals("B1"))
			estadoInicial = b1; 
		else if (origen.trim().equals("B2"))
			estadoInicial = b2; 
		else if (origen.trim().equals("B3"))
			estadoInicial = b3; 
		else if (origen.trim().equals("B4"))
			estadoInicial = b4; 
		else if (origen.trim().equals("B5"))
			estadoInicial = b5; 
		else if (origen.trim().equals("B6"))
			estadoInicial = b6; 
			
		Problema motorPosB0 = new Problema(estadoInicial, res, posiciones, costes); 

		return motorPosB0;
		

	}
}
