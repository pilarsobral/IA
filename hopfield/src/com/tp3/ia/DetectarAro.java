package com.tp3.ia;

import java.util.ArrayList;

public class DetectarAro {


    public static void main(String[] args) {
    	
        Hopfield red = new Hopfield();
        Imagen imagenAro = new Imagen(); 
        red.entrenamiento(imagenAro.getPatronEntrenamiento());
        ArrayList<Integer> output = red.detectarPatron(imagenAro.convertirImagenAPatron(""));

        System.out.println("------------PESOS-----------------------");
        red.imprimirWeights();
        
        System.out.println("-----------SALIDA ENCONTRADA------------");
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
        
    }

}
