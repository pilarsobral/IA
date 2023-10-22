package com.tp3.ia;

import java.util.ArrayList;

public class Imagen {

	/**
	 * De una imagen dada devuelve el patron con 1 y -1 segun corresponda. 
	 * @param imagen Se agrego un String como imagen para el prototipo, deberia tener como entrada la imagen tomada por la camara.
	 * @return Array de 1 y -1 que corresponden al patron de la imagen
	 */
    public ArrayList<Integer> convertirImagenAPatron(String imagen)
    {
    	ArrayList<Integer> pixels = new ArrayList<Integer>();
    	
//    	int n=0;
//    	for (int i=0;i< imagen.ancho;i++)
//    	{
//    		for (int j=0;j<imagen.alto;j++)
//    		{ 
//    			if (imagen.getPixel(i,j) == blanco ) /// SE ASUME QUE EL ARO DEL MOTOR ES BLANCO O CLARO COMPARADO CON EL RESTO DEL MOTOR
//    				pixels.add(n, 1); 
//    			else
//    				pixels.add(n, -1);
//    		}
//    	}
   	 int[] patron = {
    	1, -1, -1, 1, -1, -1, -1, -1, -1, -1, 
   		1, -1, -1, 1, -1, -1, -1, -1, -1, -1, 
   		1, -1, -1,  -1,  1, -1, -1, -1, -1, -1, 
   		1, -1,  1,  -1,  1,  1, -1, -1, -1, -1, 
   		1,  1,  1, -1, -1,  1,  1, -1, 1, -1, 
   		-1,  1,  1, -1, -1,  1,  1, -1, 1, 1, 
   		-1, -1,  1,  1,  1,  1, -1, -1, 1, 1, 
   		-1, -1, -1,  1,  1, -1, -1, -1, 1, 1, 
   		 1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 
   		 1,  1, -1, -1, -1, -1, -1, -1, 1, 1};


    	 for (int i=0;i<100;i++)
    		 pixels.add(i,patron[i]); 
	    	 	 
    	 return pixels; 
    }
    

	/**
	 * Devuelve el patron correcto del Aro . 
	 * @return Array de 1 y -1 que corresponden al patron de la imagen
	 */
    public ArrayList<Integer> getPatronEntrenamiento()
    {
    	ArrayList<Integer> pixels = new ArrayList<Integer>();

    	int[] patron = {
	    	-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    		-1, -1, -1,  1,  1, -1, -1, -1, -1, -1, 
    		-1, -1,  1,  1,  1,  1, -1, -1, -1, -1, 
    		-1,  1,  1, -1, -1,  1,  1, -1, -1, -1, 
    		-1,  1,  1, -1, -1,  1,  1, -1, -1, -1, 
    		-1, -1,  1,  1,  1,  1, -1, -1, -1, -1, 
    		-1, -1, -1,  1,  1, -1, -1, -1, -1, -1, 
    		 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    		 1,  1, -1, -1, -1, -1, -1, -1, -1, -1};

    	 for (int i=0;i<100;i++)
    		 pixels.add(i,patron[i]); 
	    	 	 
    	 return pixels; 
    }
}
