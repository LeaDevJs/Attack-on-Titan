package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Suero {
	private double x;
	private double y;	
	private int ancho;
	private int alto;
	private Image vacuna;
	
	public Suero (double a, double b) {
		this.x=a;
		this.y=b;	
		ancho=20;
		alto=20;
		this.vacuna= Herramientas.cargarImagen("Imagenes/vacuna.png");
	}
	public void dibujarse (Entorno entorno) {
		//entorno.dibujarRectangulo(this.x,this.y,this.alto,this.ancho,1.570796,Color.green);
		entorno.dibujarImagen(this.vacuna, this.x, this.y, 0, 0.3);
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getAncho() {		
		return ancho;
	}		
	public int getAlto() {		
		return alto;
	}
}