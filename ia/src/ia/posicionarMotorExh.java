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
 * B0 (I) > B1 (I) > B3 (I) > B5 (I) > A
 * 							     (D) > B3 
 * 						(D) > B1
 * 			   (D) > B0
 *    (D) > B2 (I) > B0 
 *             (D) > B4 (I) > B2
 *                      (D) > B6 (I) > B4
 *    
 * @author fliab
 *
 */
public class posicionarMotorExh {

	public static void main(String[] args) {
		
		/* Se inicializan los nodos de Busqueda */
		Problema problema = inicializar("B6");
		
		Nodo resultado = busquedaAnchura(problema); 
		publicarSolucion(resultado);
	}
	
	public static Nodo busquedaAnchura(Problema problema)
	{
		Nodo raiz = crearNodoRaiz(problema);
		if (problema.esObjetivo(raiz.getEstado()))
			return raiz; 

		ArrayList<Nodo> frontera = new ArrayList<Nodo>(); 
		frontera.add(raiz); 
		ArrayList<Estado> explorados = new ArrayList<Estado>(); 
		while (true)
		{
			if (frontera.size() == 0)
				return null; 
			Nodo nodo = frontera.get(0); 
			frontera.remove(0); 
			explorados.add(nodo.getEstado()); 
			if (nodo.getAcciones() == null )
				continue; 

			Enumeration acciones = nodo.getAcciones().keys();
			while (acciones.hasMoreElements())
			{
				Accion accion = new Accion((String)acciones.nextElement());
				Nodo hijo = crearNodoHijo(problema, nodo, accion);
				
				boolean estaEnExpl = false;
				boolean estaEnFront = false;
				if (explorados.contains(hijo.getEstado()))
						estaEnExpl = true; 
				
				for (int i=0;i<frontera.size();i++)
				{
					if (frontera.get(i).getEstado().getNombre().equals(hijo.getEstado().getNombre()))
					{
						estaEnFront = true; 
						break;
					}
				}
				if (!estaEnExpl && !estaEnFront)
				{
					if (problema.esObjetivo(hijo.getEstado()))
						return hijo; 
					frontera.add(hijo);
				}
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
		padre.getHijos().add(hijo); 
		return hijo; 
	}
	/**
	 * A partir del nodo imprime la cadena de estados y movimientos que se hicieron para llegar desde el estado inicial a la posicion correcta
	 * @param nodo
	 */
	private static void publicarSolucion(Nodo nodo)
	{
		if (nodo == null)
		{
			System.out.println("NO HAY SOLUCION");
			return; 
		}

		String mensaje = ""; 
		Nodo auxNodo = nodo; 
		while (auxNodo != null)
		{
			mensaje =  " Estado: " + auxNodo.getEstado().getNombre() + mensaje ; 
			if (auxNodo.getAccion() != null)
				mensaje =  " (" + auxNodo.getAccion().getNombre() + ") > " + mensaje; 
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
		htB0.put("D", accDer); 
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
		posResultadoB0.put("D", b2); 

		Hashtable<String, Estado> posResultadoB1 = new Hashtable<String, Estado>(); 
		posResultadoB1.put("I", b3); 
		posResultadoB1.put("D", b0); 

		Hashtable<String, Estado> posResultadoB2 = new Hashtable<String, Estado>(); 
		posResultadoB2.put("I", b0); 
		posResultadoB2.put("D", b4); 

		Hashtable<String, Estado> posResultadoB3 = new Hashtable<String, Estado>(); 
		posResultadoB3.put("I", b5); 
		posResultadoB3.put("D", b1); 

		Hashtable<String, Estado> posResultadoB4 = new Hashtable<String, Estado>(); 
		posResultadoB4.put("I", b2); 
		posResultadoB4.put("D", b6); 

		Hashtable<String, Estado> posResultadoB5 = new Hashtable<String, Estado>(); 
		posResultadoB5.put("I", a); 
		posResultadoB5.put("D", b3); 

		Hashtable<String, Estado> posResultadoB6 = new Hashtable<String, Estado>(); 
		posResultadoB6.put("I", b4); 

		posiciones.put("B0", posResultadoB0); 
		posiciones.put("B1", posResultadoB1); 
		posiciones.put("B2", posResultadoB2); 
		posiciones.put("B3", posResultadoB3); 
		posiciones.put("B4", posResultadoB4); 
		posiciones.put("B5", posResultadoB5); 
		posiciones.put("B6", posResultadoB6); 
		posiciones.put("A", new Hashtable<String, Estado> ()); 
		
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
			
		Problema motorPosB0 = new Problema(estadoInicial, res, posiciones); 

		return motorPosB0;
		

	}
}
