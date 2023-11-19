package com.tp4.ia;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class HoughRecta 
{

	public static void main(String[] args) 
	{
		   // carga la libreria opencv 
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        reconocerLineas("C:\\Users\\fliab\\Downloads\\motor.png"); // actualizar con imagen tomada por la camara. 
	}
	private static void reconocerLineas(String archivoImagen)
	{
		 Mat dst = new Mat();
		 Mat imagenEnGris = new Mat();
		 
		 Mat imagenOriginal = Imgcodecs.imread(archivoImagen, Imgcodecs.IMREAD_GRAYSCALE);
		 
		 // deteccion de los bordes. 
		 Imgproc.Canny(imagenOriginal, dst, 50, 200, 3, false);
		 // transformar imagen en tonos de gris
		 Imgproc.cvtColor(dst, imagenEnGris, Imgproc.COLOR_GRAY2BGR);
		 
		 // se van a cargar las lineas detectadas 
		 Mat lineas = new Mat();
		
		 Imgproc.HoughLines(dst, lineas, 1, Math.PI/180, 150) ; //deteccion de lineas
		 
		 for (int x = 0; x < lineas.rows(); x++) 
		 {
			 double rho = lineas.get(x, 0)[0];
			 double theta = lineas.get(x, 0)[1];
			 
			 double a = Math.cos(theta);
			 double b = Math.sin(theta);
	         double x0 = a*rho;
	         double y0 = b*rho;
	         Point pt1 = new Point(Math.round(x0 + 1000*(-b)), Math.round(y0 + 1000*(a)));
	         Point pt2 = new Point(Math.round(x0 - 1000*(-b)), Math.round(y0 - 1000*(a)));
	         
	         // se actualiza la imagen en gris poniendole una linea roja donde se detecto la linea. 
	         Imgproc.line(imagenEnGris, pt1, pt2, new Scalar(0, 0, 255), 3, Imgproc.LINE_AA, 0); 
		 }
		 
        HighGui.imshow("Imagen Original", imagenOriginal);
        HighGui.imshow("Lineas detectadas en Rojo - Transformada de Hough", imagenEnGris);
	      
        HighGui.waitKey(50000);
        System.exit(0);

	}
}
