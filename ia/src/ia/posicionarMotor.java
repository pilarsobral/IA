package ia;


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
public class posicionarMotor {

	public static void main(String[] args) {
		
		/* Se inicializan los nodos de Busqueda */
		Estado estadoInicial = inicializar(); 
		
		String resp = buscar(estadoInicial, "", "A");
		System.out.println("Secuencia de Movimientos " + resp);
		
	}
	/**
	 * 
	 * @return Arbol de nodos para hacer la busqueda
	 */
	private static Estado inicializar()
	{
		/* se cargan los estados posibles, con las posibles acciones a partir de estos */

		Estado b0 = new Estado(); 
		b0.setNombre("B0"); //id de la posicion del monitor
		b0.setAcciones(new String[] {"I","D"}); // posibles movimientos a partir de la posicion
		b0.setPadre(null); // en caso de tener un nodo padre
		
		Estado b1 = new Estado(); 
		b1.setNombre("B1");
		b1.setAcciones(new String[] {"I","D"});
		b1.setPadre(b0);
		
		Estado b2 = new Estado(); 
		b2.setNombre("B2");
		b2.setAcciones(new String[] {"I","D"});
		b2.setPadre(b0); 
		
		Estado b3 = new Estado(); 
		b3.setNombre("B3");
		b3.setAcciones(new String[] {"I","D"});
		b3.setPadre(b1);
		
		Estado b4 = new Estado(); 
		b4.setNombre("B4");
		b4.setAcciones(new String[] {"I","D"});
		b4.setPadre(b2);
		
		Estado b5 = new Estado(); 
		b5.setNombre("B5");
		b5.setAcciones(new String[] {"I","D"});
		b5.setPadre(b3);
		
		Estado b6 = new Estado(); 
		b6.setNombre("B6");
		b6.setAcciones(new String[] {"I"});
		b6.setPadre(b4);
		
		Estado a = new Estado(); 
		a.setNombre("A");
		a.setAcciones(null);
		a.setPadre(b5);
		
		/*carga de los hijos de cada nodo */
		
		b0.setHijos(new Estado[] {b1, b2}); // Se cargan los nodos hijos de cada estado
		b1.setHijos(new Estado[] {b3, b0});
		b2.setHijos(new Estado[] {b0, b4});
		b3.setHijos(new Estado[] {b5, b1});
		b4.setHijos(new Estado[] {b2, b6});
		b5.setHijos(new Estado[] {a, b3});
		b6.setHijos(new Estado[] {b4});
		
		return b0; // se retorna el nodo padre
	}

	/**
	 * 
	 * @param estado > Nodo que se va a explorar
	 * @param accion > Accion que se hizo para llegar al nodo (I o D) 
	 * @param buscado > Nodo buscado
	 * @return > String con la cadena de busqueda
	 */
	private static String buscar(Estado estado, String accion, String buscado)
	{
		System.out.println("Nombre de Nodo '"  + estado.getNombre() + "', Nodo Buscado '" + buscado + "'");

		if (estado.getNombre().equals(buscado)) // Si el estado es el buscado
			return "(" + accion + ") >" + estado.getNombre(); // retorna la accion que se hizo para llegar y el nodo destino
		else if (estado.getHijos() == null) // Si el nodo no tiene hijos, debe retornar sin devolver ninguna cadena. Esta rama del arbol no continene el nodo buscado.
			return ""; 
		else // En caso que se puede explorar la rama
		{
			String respuesta = ""; 
			for (int i=0;i<estado.getHijos().length;i++) // por cada uno d elos hijos
			{
				// En caso que no sea el nodo origen, se evalua si el nombre del hijo es igual al del padre, tiene que seguir con otro hijo. 
				// Esto es porque en caso de estar en una posicion x es posible seguir a la proxima posicion o volver a la anterior.
				//En casao de tener que volver a la anterior, no se deben explorar los hijos, porque quedaria en un bucle infinito.
				if (estado.getPadre() != null) 
				{
					if (estado.getHijos()[i].getNombre().equals(estado.getPadre().getNombre()))
						continue;
				}
				// por cada hijo se explora la rama
				respuesta = buscar(estado.getHijos()[i], estado.getAcciones()[i], buscado);
				if (!respuesta.trim().equals(""))
				{
					respuesta = "(" + accion + ") >" + estado.getNombre() +  respuesta;
					break;
				}
			}
			return respuesta; 
		}
	}
}
