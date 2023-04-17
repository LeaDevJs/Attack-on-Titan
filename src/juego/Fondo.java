package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
public class Fondo {
private int x;
private int y;
private Image fondo;
private Image fondo2;
private Image fondo3;
private Image fondo4;
private Image fondo5;

 public Fondo (int x,int y) {
	this.x=x;
	this.y=y;
	this.fondo = Herramientas.cargarImagen("Imagenes/fondorpg.png");
	this.fondo2 = Herramientas.cargarImagen("Imagenes/snkinicio.jpg");
	this.fondo3 = Herramientas.cargarImagen("Imagenes/mikasaMuerta.png");
	this.fondo4=Herramientas.cargarImagen("Imagenes/ganaste.jpg");
	this.fondo5=Herramientas.cargarImagen("Imagenes/TITULO.jpg");
 }
 public void dibujarse(Entorno entorno) {
	entorno.dibujarImagen(this.fondo, this.x, this.y, 0, 2);
 }
 public void dibujarini(Entorno entorno) {
	entorno.dibujarImagen(this.fondo2, this.x, this.y, 0, 1);
 }
 public void dibujarmuerte(Entorno entorno) {
	entorno.dibujarImagen(this.fondo3, 400, 400, 0, 2);
 }
 public void dibujarganoe(Entorno entorno) {
	entorno.dibujarImagen(this.fondo4, this.x, this.y, 0, 1);
 }
 public void Cargando(Entorno entorno) {
	entorno.dibujarImagen(this.fondo5, this.x, this.y, 0, 0.5);
 }
}
