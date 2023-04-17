package juego;
import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Kyojin {
	private double x;
	private double y;	
	private int ancho;
	private int alto;
	private boolean vivo=true;
    private Image titan;
	
	public Kyojin (double e, double d) {
		this.x = e;
		this.y = d;
		this.ancho=40;
		this.alto=80;
		this.titan=Herramientas.cargarImagen("Imagenes/kio.png");
	}
	
	public void dibujarse (Entorno entorno) {
//	entorno.dibujarRectangulo(this.x,this.y,this.alto,this.ancho,1.570796,Color.red);
	entorno.dibujarImagen(this.titan, this.x, this.y , 0 , 1.5);
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
	
	public boolean Estavivo() {		
		return vivo;
	}
	
	public void Cambiarvivo (boolean vof) {
		this.vivo=vof;
	}
	
}
