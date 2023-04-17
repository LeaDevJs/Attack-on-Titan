package juego;
import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import java.util.Random;

public class Obstaculo {
	private double x;
	private double y;	
	private int ancho;
	private int alto;
	private Image casa;
	private Image arbol;
	Random Rango = new Random();
	int casaoarbol = Rango.nextInt(2) + 1;
	
    public Obstaculo (double a, double b) {
    	this.x=a;
		this.y=b;
    	if (casaoarbol==1) {
    		ancho=70;
    		alto=50;
    		this.casa=Herramientas.cargarImagen("Imagenes/casa.jpg");
    	}else {
    		ancho=50;
    		alto=70;
    		this.arbol=Herramientas.cargarImagen("Imagenes/arbol.png");
    	}	
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getAncho() {
		return ancho;
	}
	public double getAlto() {
		return alto;
	}
	public void dibujarse(Entorno entorno) {
		if (this.casaoarbol==1) {
//			entorno.dibujarRectangulo(this.x, this.y, ancho, alto, 0 , Color.GRAY);
			entorno.dibujarImagen(this.casa, this.x, this.y, 0, 1);
		}else {
//			entorno.dibujarRectangulo(this.x, this.y, ancho, alto, 0 , Color.GRAY);
			entorno.dibujarImagen(this.arbol, this.x, this.y, 0, 0.4);
		}
	}
}