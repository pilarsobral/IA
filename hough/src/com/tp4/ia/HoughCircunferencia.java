package com.tp4.ia;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class HoughCircunferencia {
	

	public static void main(String[] args) 
	{
		   // carga la libreria opencv 
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        reconocerLineas("C:\\Users\\fliab\\Downloads\\motorArandela.jpg"); // actualizar con imagen tomada por la camara. 
	}
	private static void reconocerLineas(String archivoImagen)
	{
		Mat imagenOriginal = Imgcodecs.imread(archivoImagen, Imgcodecs.IMREAD_COLOR);
		Mat imagenEnGris = new Mat();
		//convierte imagen en tonos de gris
        Imgproc.cvtColor(imagenOriginal, imagenEnGris, Imgproc.COLOR_BGR2GRAY);
        
        //se reduce el ruido 
        Imgproc.medianBlur(imagenEnGris, imagenEnGris, 5);
        Mat circulos = new Mat();
        Imgproc.HoughCircles(imagenEnGris, circulos, Imgproc.HOUGH_GRADIENT, 1.0,
                (double)imagenEnGris.rows()/16, // distancia minima entre los circulos para detectarlo
                100.0, 30.0, 1, 30); //los ultimos parametros indican el radio minimo y maximo de las circunferencias a detectar

        for (int x = 0; x < circulos.cols(); x++) 
        {
            double[] c = circulos.get(0, x);
            Point centro  = new Point(Math.round(c[0]), Math.round(c[1]));
           
            Imgproc.circle(imagenOriginal, centro, 1,  new Scalar(0, 0, 255), 3, 8, 0 );
            int radius = (int) Math.round(c[2]);
            Imgproc.circle(imagenOriginal, centro, radius,  new Scalar(0, 0, 255), 3, 8, 0 );
        }
        HighGui.imshow("Circulos detectados", imagenOriginal);
        HighGui.waitKey();

        System.exit(0);
	}

}
