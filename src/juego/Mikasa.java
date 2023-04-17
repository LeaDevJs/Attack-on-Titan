package juego;
import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Mikasa {
	private double x;
	private double y;	
	private int ancho;
	private int alto;
	private boolean poder=false;
	private boolean mikasadisparo=false;
	private int sentido;
	private Image mikasa;
	private Image movizq;
	private Image movder;
	private Image mikatitanizq;
	private Image mikatitander;


public Mikasa (double e, double d) {
	this.x = e;
	this.y = d;
	if (this.poder) {
		this.ancho=40;
		this.alto=80;
	}else {
		this.ancho=30;
		this.alto=50;
	}
	this.mikasa=Herramientas.cargarImagen("Imagenes/mikasa.png");
	this.movizq=Herramientas.cargarImagen("Imagenes/mikasaMovIzq.png");
	this.movder=Herramientas.cargarImagen("Imagenes/mikasaMovDer.png");	
	this.mikatitanizq=Herramientas.cargarImagen("Imagenes/titanmikaizq.png");	
	this.mikatitander=Herramientas.cargarImagen("Imagenes/titanmikaDer.png");	
}


public void dibuja (Entorno entorno) {
	if (!this.Verpoder()) {
		entorno.dibujarImagen(this.mikasa, this.x, this.y, 0, 1);
//		entorno.dibujarRectangulo(this.x,this.y,this.alto,this.ancho,1.570796,Color.blue);
	}else {
		entorno.dibujarImagen(this.mikatitanizq, this.x, this.y , 0 , 1);
//		entorno.dibujarRectangulo(this.x,this.y,80,40,1.570796,Color.blue);
	}
}

public void dibujarder (Entorno entorno) {
	if (!this.Verpoder()) {
		entorno.dibujarImagen(this.movder, this.x, this.y, 0, 1);
//		entorno.dibujarRectangulo(this.x,this.y,this.alto,this.ancho,1.570796,Color.blue);
	}else {
		entorno.dibujarImagen(this.mikatitander, this.x, this.y , 0 , 1);
//		entorno.dibujarRectangulo(this.x,this.y,80,40,1.570796,Color.blue);
	}
}

public void dibujarizq (Entorno entorno) {
	if (!this.Verpoder()) {
		entorno.dibujarImagen(this.movizq, this.x, this.y, 0, 1);
//		entorno.dibujarRectangulo(this.x,this.y,this.alto,this.ancho,1.570796,Color.blue);
	}else {
		entorno.dibujarImagen(this.mikatitanizq, this.x, this.y , 0 , 1);
//		entorno.dibujarRectangulo(this.x,this.y,80,40,1.570796,Color.blue);
	}
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

public void moverIzquierda() {
	this.x-=2;
	this.sentido=-1;
}
public void moverDerecha() {
	this.x+=2;
	this.sentido=1;
}

public void moverAbajo() {
	this.y+=2;
	this.sentido=-2;
}
public void moverArriba() {
	this.y-=2;
	this.sentido=2;
}

public int Versentido() {		
	return sentido;
}

public void Convertir(boolean con) {
	this.poder=con;
}

public boolean Verpoder() {		
	return poder;
}

public void mikadispara(boolean bala) {
	this.mikasadisparo=bala;
}

public boolean verdisparo() {		
	return mikasadisparo;
}

}
