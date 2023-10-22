package com.tp3.ia;

import java.util.ArrayList;

public class Hopfield {

	    private double[][] weights;;
		int cantNeuronas = 0;
		/**
		 * Se carga el patron que debe detectar
		 * @param patronAro patron que debe detectar. 
		 */
	    public void entrenamiento(ArrayList<Integer> patronAro) {
	        cantNeuronas = patronAro.size();
	        weights = new double[cantNeuronas][cantNeuronas]; 
	        

			for(int i = 0; i < cantNeuronas; i++) {
				for(int j = 0; j < cantNeuronas; j++) {
				
					if (i == j) { //se saca la matriz identidad
					} else {
						double weight = 0;
						weight += patronAro.get(i) * patronAro.get(j);
						weights[i][j] = weight;
					}
				}
			}	
	    }



		/**
		 * Detecta si el patron pasado corresponde con el aro .
		 *
		 * @param imagen imagen que se detectara
		 * @return the output pattern
		 */
		public ArrayList<Integer> detectarPatron(ArrayList<Integer> imagen) {

				ArrayList<Integer> output = new ArrayList<>();
				
				for(int i = 0; i < cantNeuronas; i++) {

					double a = 0; 

					for (int j = 0; j < cantNeuronas; j++) {
						a+= weights[i][j] * imagen.get(j);
					}
					
					if (a >= 0) {	
						output.add(i, 1);
					} else {
						output.add(i, -1);
					}
				}
				
				// Se chequea que haya estabilidad. Si devuelve 2 veces el mismo patron entonces es correcto. 
				if(imagen.equals(output)) {
					/* impresion de la primer imagen */

			        System.out.println("-----------PRIMER SALIDA ENCONTRADA------------");
			        int n=0;
			        for(int y = 0; y < 10; y++) {
			        	String renglon ="";
			            for(int x = 0; x < 10; x++) {
			            		
			            	if (output.get(n) == -1) {
			            		renglon=renglon+"-";
			            		
			            	} else {
			            		renglon=renglon+"+";
			            	}
			            	n++;

			            }
			            System.out.println(renglon);
			        }
			        
					
					return output;
				}
				// Se vuelve a detectar el patron por si devuelve algo distinto.  
				return detectarPatron(output);
			}
		
		
		/**
		 * Prints the weights.
		 */
		public void imprimirWeights() {
			System.out.println("");
			
			int n = 0;
			
			for(int i = 0; i < weights.length; i++) {
				for(int j = 0; j < weights.length; j++) {
					
					System.out.print(weights[i][j]);
					System.out.print(" ");
					n++;
				}
				
				// print next line
				if (n % cantNeuronas == 0) {
					System.out.println("");
				}
			}	
			System.out.println("");
		}
	}

