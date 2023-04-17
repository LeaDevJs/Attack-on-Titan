package juego;
import java.awt.Image;
import java.awt.Color;
import entorno.Entorno;
import entorno.Herramientas;
public class Proyectil {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private int sentido;
	private double angulo;
	private Image espada;

public Proyectil(double x, double y) {
	this.x = x;
	this.y = y;
	ancho = 10;
	alto = 50;
	this.espada=Herramientas.cargarImagen("Imagenes/proyectil2.png");
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
	//entorno.dibujarRectangulo(this.x, this.y, ancho, alto, this.angulo , Color.GRAY);
	entorno.dibujarImagen(this.espada, this.x, this.y , this.angulo , 1);
}
public void moverIzquierda() {
	this.x-=3;
}
public void moverDerecha() {
	this.x+=3;
}
public void moverAbajo() {
	this.y+=3;
}
public void moverArriba() {
	this.y-=3;
}
public int Versentido() {		
	return sentido;
}
public void sentido(int s) {
	this.sentido=s;
}
public void Darangulo(double d) {
	this.angulo=d;
}
}


