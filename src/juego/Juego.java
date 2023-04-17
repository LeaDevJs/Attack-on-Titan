package juego;
import java.awt.Color;
import java.awt.Font;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
import javax.sound.sampled.Clip;
import java.util.Random;



public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	// Variables de instancias para creacion de subclases
	private Mikasa mikasa;
    private Proyectil proyectil;
    private Fondo inicio;
    private Suero vacuna;
    private Kyojin[] titanes;
    private Obstaculo[] objetos;
    
    //booleans
    private boolean perdio=false;
    private boolean star=false; 
    private boolean estasuero=false;
    private boolean cargando=true;
    
    //contadores
    private int contador=0;
    private int contadorvivos=5;
    private int muertes=0;
    
    // clips de sonidos
    private Clip musica;
    private Clip colisionProyectilKyojin;
    
	// ...
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Attack on Titan, Final Season - Grupo 9 ", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		
	    this.mikasa= new Mikasa(400, 300);
	    this.titanes=new Kyojin[5];
	    this.objetos=new Obstaculo[6];
	    this.inicio=new Fondo(400,300);
	    
	    // inicializar titanes
	    titanes[0]=new Kyojin(50,50);
	    titanes[1]=new Kyojin(750,50);
	    titanes[2]=new Kyojin(50,550);
	    titanes[3]=new Kyojin(750,550);
	    titanes[4]=new Kyojin(400,550);
	    
	    // inicializar estructuras
	    objetos[0]=new Obstaculo(120,130);
	    objetos[1]=new Obstaculo(120,470);
	    objetos[2]=new Obstaculo(670,120);
	    objetos[3]=new Obstaculo(670,470);
	    objetos[4]=new Obstaculo(300,400);
	    objetos[5]=new Obstaculo(450,300);
	    
	    //inicializar sonidos
	    this.musica= Herramientas.cargarSonido("Imagenes/musica.wav");

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		/**
		 *preguntamos si nuestro personaje ya perdio, si es el caso mostrara una pantalla con el mensaje has perdido
		 */
	  if (perdio)	{ 
		  musica.stop();
		  entorno.cambiarFont(Font.MONOSPACED,50, Color.red);			
		  entorno.escribirTexto("¡Has Perdido!", 210, 300);
		  entorno.cambiarFont(Font.MONOSPACED,30, Color.blue);			
		  entorno.escribirTexto("Deseas reiniciar? Presiona ENTER:", 120, 500);
		  this.inicio.dibujarmuerte(entorno);
		     /**
			 *preguntamos: si queremos volver a jugar presionamos la tecla enter y de ese modo volveremos a jugar , los valores volveran a reiniciarse.
			 */
		  if (entorno.estaPresionada(entorno.TECLA_ENTER)) {
			  perdio=false;
			    mikasa=new Mikasa(400,300);
			    titanes[0]=new Kyojin(50,50);
			    titanes[1]=new Kyojin(750,50);
			    titanes[2]=new Kyojin(50,550);
			    titanes[3]=new Kyojin(750,550);
			    titanes[4]=new Kyojin(400,550);
			    this.muertes=0;
			    this.contadorvivos=5;
			    this.mikasa.mikadispara(false);
			    this.estasuero=false;
		  }
	  }else {
		  /**
			 *si el personaje no perdio seguimos a la siguiente pantalla, aqui verificaremos si el personaje mato a todos los titanes, si es asi aparecera un cartel diciendo que ganamos junto a una imagen
		  */
		  if (this.contadorvivos==0) {
			  musica.stop();
			  entorno.cambiarFont(Font.MONOSPACED,50, Color.red);			
			  entorno.escribirTexto("¡GANASTE!", 250 , 100);
			  this.inicio.dibujarganoe(entorno);
		  }else {
			  /**
				 *aqui crearemos la pantalla de inicio la cual nos servira para empezar el juego dandonos la opcion de si queremos iniciar presionamos enter si es asi nuestra varible estar iniciara
			  */
			this.musica.loop(musica.LOOP_CONTINUOUSLY);
			if (!star) {
				 this.inicio.dibujarini(entorno);
				 if (entorno.estaPresionada(entorno.TECLA_ENTER)) {
		               this.star=true;
					   this.cargando=false;	   
				 }
			}
			/**
			 *como pregunta extra antes de iniciar verificaremos que las condiciones anteriores esten inicializadas de forma correcta, si el jugador coloco star y aun no perdio podremos empezar a jugar
			 */
		    if (star && !perdio && cargando) {
		    	// Procesamiento de un instante de tiempo
				  this.inicio.dibujarse(entorno); // dibujamos el fondo del juego
				  contador+=1; // empezamos el contador de tick que sumara uno en cada vuelta
//////////////// funciones para el personaje mikasa 	
		        
				// mikasa aparicion 
				// mientras el jugador no aprete ninguna tecla veremos a una mikasa derecha
				if (!entorno.estaPresionada(entorno.TECLA_DERECHA) && !entorno.estaPresionada(entorno.TECLA_IZQUIERDA)   ) { 
					 this.mikasa.dibuja(entorno);
				}
				if (this.mikasa.getX()+mikasa.getAncho()/2>=entorno.ancho()-5 ||  this.mikasa.getX()-mikasa.getAncho()/2<=5 ) {
					this.mikasa.dibuja(entorno);
				}
				
		        //mikasa moviendose
				 // si se presiona la tecla derecha y el personaje no se valla del borde podremos movernos hacia esa direccion
				if(entorno.estaPresionada(entorno.TECLA_DERECHA)&&this.mikasa.getX()+mikasa.getAncho()/2<entorno.ancho()) {
						this.mikasa.dibujarder(entorno);
						mikasa.moverDerecha();
				}
				 // si se presiona la tecla izquierda y el personaje no se valla del borde podremos movernos hacia esa direccion
				if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)&&this.mikasa.getX()-mikasa.getAncho()/2>0) {
						this.mikasa.dibujarizq(entorno);
						mikasa.moverIzquierda();
				}
				if(entorno.estaPresionada(entorno.TECLA_ARRIBA)&&this.mikasa.getY()-mikasa.getAlto()/2>0) {
					mikasa.moverArriba();
				}
				if(entorno.estaPresionada(entorno.TECLA_ABAJO)&&this.mikasa.getY()+mikasa.getAlto()/2<entorno.alto()) {
					mikasa.moverAbajo();
				}
				
				//muerte mikasa
				for (int x=0;x<=this.titanes.length-1;x++) { //recorremos el arreglo donde estan los titanes
					/**
					 *si el personaje toca alguna de los 4 lados del objeto titan mikasa pierde
				    */
					// si la pared de abajo de mikasa toca la parte superior del titan mikasa muere
					 if (this.mikasa.getY()+this.mikasa.getAlto()/2 >=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && this.mikasa.getY()+this.mikasa.getAlto()/2 <=this.titanes[x].getY() && 
							 this.mikasa.getX()+this.mikasa.getAncho()/2 >= this.titanes[x].getX()- (this.titanes[x].getAncho()/2) && this.mikasa.getX()-this.mikasa.getAncho()/2<=this.titanes[x].getX()+(this.titanes[x].getAncho()/2) &&
				                  this.mikasa.Verpoder()==false ) {
							this.perdio=true;
					 }
					// si la pared de arriba de mikasa toca la parte inferior del titan mikasa muere
				     if (this.mikasa.getY()-this.mikasa.getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2) && this.mikasa.getY()-this.mikasa.getAlto()/2>=this.titanes[x].getY() &&  
				    		 this.mikasa.getX()+this.mikasa.getAncho()/2 >= this.titanes[x].getX()- (this.titanes[x].getAncho()/2) &&
				    		    this.mikasa.getX()-this.mikasa.getAncho()/2<=this.titanes[x].getX()+(this.titanes[x].getAncho()/2) && this.mikasa.Verpoder()==false ) {
							this.perdio=true;
				     }
					// si la pared derecha de mikasa toca la parte izquierda del titan mikasa muere
                     if (this.mikasa.getX()+this.mikasa.getAncho()/2 >=this.titanes[x].getX()-(this.titanes[x].getAncho()/2) && 
                    		 this.mikasa.getX()+this.mikasa.getAncho()/2 <=this.titanes[x].getX() && this.mikasa.getY()+this.mikasa.getAlto()/2>=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && 
                    		     this.mikasa.getY()-this.mikasa.getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2)  && this.mikasa.Verpoder()==false ) {
							this.perdio=true;
                     }
                  // si la pared izquierda de mikasa toca la parte derecha del titan mikasa muere
                     if (this.mikasa.getX()-this.mikasa.getAncho()/2 <=this.titanes[x].getX()+(this.titanes[x].getAncho()/2) && this.mikasa.getX()-this.mikasa.getAncho()/2 >=this.titanes[x].getX() && 
                    		 this.mikasa.getY()+this.mikasa.getAlto()/2>=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && this.mikasa.getY()-this.mikasa.getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2)  && 
                    		       this.mikasa.Verpoder()==false ) {
							this.perdio=true;
				     }
				 }
						 		
////////////////proyectil
				
				// creamos el proyectil al presionar la tecla espacio mientras el proyectil no halla sido lanzado y mikasa no sea un titan
				if (entorno.estaPresionada(entorno.TECLA_ESPACIO)&&this.mikasa.verdisparo()==false && mikasa.Verpoder()==false) {
					this.proyectil=new Proyectil(this.mikasa.getX(),this.mikasa.getY());
					this.mikasa.mikadispara(true);
					this.proyectil.sentido(this.mikasa.Versentido());
				}	
				
				// lanzar proyectil
				/**
				 *si el proyectil se lanzo, mientras no impacte contra algo seguira moviendose, preguntamos la orientacion para asi movernos y restringimos que no pase los bordes
			    */
				if (this.mikasa.verdisparo()) {
					if (this.proyectil.Versentido()==-2 && this.proyectil.getY()<entorno.alto()) {
						this.proyectil.moverAbajo();
						this.proyectil.dibujarse(entorno);
						this.proyectil.Darangulo(-2.356194);
					}else {
						if (this.proyectil.Versentido()==2 && this.proyectil.getY()>0) {
							this.proyectil.moverArriba();
							this.proyectil.dibujarse(entorno);
							this.proyectil.Darangulo(0.785398);
						}else {
							if (this.proyectil.Versentido()==1 && this.proyectil.getX()<entorno.ancho()) {
								this.proyectil.moverDerecha();
								this.proyectil.dibujarse(entorno);
								this.proyectil.Darangulo(2.356194);

							}else {
								if (this.proyectil.Versentido()==-1 && this.proyectil.getX()>0) {
									this.proyectil.moverIzquierda();
									this.proyectil.dibujarse(entorno);
									this.proyectil.Darangulo(-0.785398);
								}else {
									this.mikasa.mikadispara(false);
								}
							}
						}
					}
					
				}
				//fin codigo proyectil
				
////////////////kyojin
				
				//dibujamos a todos los titanes del arreglo en pantalla mientras esten vivos
				for (int x=0;x<=this.titanes.length-1;x++) {
					if (this.titanes[x].Estavivo()) {
					 this.titanes[x].dibujarse(entorno);
					} 
				}
				
				
				//colision proyectil con titanes
				if (this.mikasa.verdisparo()) {
					/**
					 *si el proyectil se lanzo e impacta alguno de los lados del algun titan el titan muere y los valores de x e y se colocaran en 0, tambien sumamos las muertes para el contador a mostrar
				    */
					for (int x=0;x<=this.titanes.length-1;x++) {
						 if (this.titanes[x]!=null&&this.proyectil.getY()+this.proyectil.getAlto()/2 >=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && this.proyectil.getY()+this.proyectil.getAlto()/2 <=this.titanes[x].getY() && 
								 this.proyectil.getX()+this.proyectil.getAncho()/2 >= this.titanes[x].getX()- (this.titanes[x].getAncho()/2) && this.proyectil.getX()-this.proyectil.getAncho()/2<=this.titanes[x].getX()+(this.titanes[x].getAncho()/2) || 
								     this.proyectil.getY()-this.proyectil.getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2) && this.proyectil.getY()-this.proyectil.getAlto()/2>=this.titanes[x].getY() &&  
								         this.proyectil.getX()+this.proyectil.getAncho()/2 >= this.titanes[x].getX()- (this.titanes[x].getAncho()/2) && this.proyectil.getX()-this.proyectil.getAncho()/2<=this.titanes[x].getX()+(this.titanes[x].getAncho()/2)  || 
								            this.proyectil.getX()+this.proyectil.getAncho()/2 >=this.titanes[x].getX()-(this.titanes[x].getAncho()/2) && this.proyectil.getX()+this.proyectil.getAncho()/2 <=this.titanes[x].getX() && 
								                this.proyectil.getY()+this.proyectil.getAlto()/2>=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && this.proyectil.getY()-this.proyectil.getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2)  || 
								                   this.proyectil.getX()-this.proyectil.getAncho()/2 <=this.titanes[x].getX()+(this.titanes[x].getAncho()/2) && this.proyectil.getX()-this.proyectil.getAncho()/2 >=this.titanes[x].getX() && 
								                      this.proyectil.getY()+this.proyectil.getAlto()/2>=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && this.proyectil.getY()-this.proyectil.getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2)  ) {
								this.titanes[x].Cambiarvivo(false);
								this.mikasa.mikadispara(false);
								this.titanes[x].setX(0);
								this.titanes[x].setY(0);
								this.muertes+=1;
								this.contadorvivos-=1;
								this.colisionProyectilKyojin= Herramientas.cargarSonido("Imagenes/ptitan.wav");
								this.colisionProyectilKyojin.start();
					    }
					}
				}
//				
//				/**
//				 *para revivir de forma aleatoria verificamos si algun titan esta muerto y si es asi cada cierto tiempo lo reviviremos siempre que mikasa no halla ganado o perdido el juego
//			    */
//				//revivir kiojin
//				if (this.contador%500 == 0) {
//					int primermuerto=0;
//					for (int x=0;x<=this.titanes.length-1;x++) {
//						if (this.titanes[x].Estavivo()==false && primermuerto==0) {
//							Random Rang = new Random();
//					    	int randy = Rang.nextInt(600) + 1;
//							this.titanes[x].Cambiarvivo(true);
//							this.titanes[x].setX(50);
//							this.titanes[x].setY(randy);
//							this.contadorvivos+=1;
//							primermuerto=1;
//						}
//					}
//				}
//				
//				//mover kiojin
//				/**
//				 *para mover el titan de forma automatica, haremos que los titanes muevan los X hacia el X de mikasa y lo mismo en Y , siempre fijandonos en que lado esten ubicados
//			    */
//				for (int x=0;x<=this.titanes.length-1;x++) {
//					if (this.mikasa.getX()>this.titanes[x].getX() && this.titanes[x].Estavivo()==true) {
//						this.titanes[x].setX(this.titanes[x].getX()+1);
//					}else {
//						this.titanes[x].setX(this.titanes[x].getX()-1);
//					}
//					if (this.mikasa.getY()>this.titanes[x].getY() && this.titanes[x].Estavivo()==true) {
//						this.titanes[x].setY(this.titanes[x].getY()+1);
//					}else {
//						this.titanes[x].setY(this.titanes[x].getY()-1);
//					}
//				}
//				
//				//se chocan kyojin con kyojin
//			    for (int k=0;k<=this.titanes.length-1;k++) { //recorremos cada titan
//					for (int x=0;x<=this.titanes.length-1;x++) { // y preguntamos por cada titan si toca alguna de las paredes de los demas
//						//techo superior de los objetos
//						if (this.titanes[k].getY()+this.titanes[k].getAlto()/2 >=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && this.titanes[k].getY()+this.titanes[k].getAlto()/2 <=this.titanes[x].getY() && 
//								this.titanes[k].getX()+this.titanes[k].getAncho()/2 >= this.titanes[x].getX()- (this.titanes[x].getAncho()/2) && this.titanes[k].getX()-this.titanes[k].getAncho()/2<=this.titanes[x].getX()+(this.titanes[x].getAncho()/2) ) {
//							this.titanes[k].setY(this.titanes[k].getY()-2);
//						}
//						if (this.titanes[k].getY()-this.titanes[k].getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2) && this.titanes[k].getY()-this.titanes[k].getAlto()/2>=this.titanes[x].getY() && 
//								this.titanes[k].getX()+this.titanes[k].getAncho()/2 >= this.titanes[x].getX()- (this.titanes[x].getAncho()/2) &&this.titanes[k].getX()-this.titanes[k].getAncho()/2<=this.titanes[x].getX()+(this.titanes[x].getAncho()/2) ) {
//							this.titanes[k].setY(this.titanes[k].getY()+2);
//						}
//						if (this.titanes[k].getX()+this.titanes[k].getAncho()/2 >=this.titanes[x].getX()-(this.titanes[x].getAncho()/2) && this.titanes[k].getX()+this.titanes[k].getAncho()/2 <=this.titanes[x].getX() &&  
//								this.titanes[k].getY()+this.titanes[k].getAlto()/2>=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && this.titanes[k].getY()-this.titanes[k].getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2) ) {
//							this.titanes[k].setX(this.titanes[k].getX()-2);
//						}
//						if (this.titanes[k].getX()-this.titanes[k].getAncho()/2 <=this.titanes[x].getX()+(this.titanes[x].getAncho()/2) && this.titanes[k].getX()-this.titanes[k].getAncho()/2 >=this.titanes[x].getX() && 
//								this.titanes[k].getY()+this.titanes[k].getAlto()/2>=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && this.titanes[k].getY()-this.titanes[k].getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2) ) {
//							this.titanes[k].setX(this.titanes[k].getX()+2);
//						}
//					}
//		        }
//			    
//			    // colision kyojin obstaculo
//			    for (int k=0;k<=this.titanes.length-1;k++) { // recorremos cada titan
//					for (int x=0;x<=this.objetos.length-1;x++) { //preguntamos si cada titan toca algun objeto si es asi el titan no pasara
//						//techo superior de los objetos
//						if (this.titanes[k].getY()+this.titanes[k].getAlto()/2 ==this.objetos[x].getY()-(this.objetos[x].getAlto()/2) && this.titanes[k].getX()+this.titanes[k].getAncho()/2 >= this.objetos[x].getX()- (this.objetos[x].getAncho()/2) && 
//								this.titanes[k].getX()-this.titanes[k].getAncho()/2<=this.objetos[x].getX()+(this.objetos[x].getAncho()/2) ) {
//							this.titanes[k].setY(this.titanes[k].getY()-1);								
//						}
//						if (this.titanes[k].getY()-this.titanes[k].getAlto()/2==this.objetos[x].getY()+(this.objetos[x].getAlto()/2) && this.titanes[k].getX()+this.titanes[k].getAncho()/2 >= this.objetos[x].getX()- (this.objetos[x].getAncho()/2) &&
//								this.titanes[k].getX()-this.titanes[k].getAncho()/2<=this.objetos[x].getX()+(this.objetos[x].getAncho()/2) ) {
//							this.titanes[k].setY(this.titanes[k].getY()+1);
//						}
//						if (this.titanes[k].getX()+this.titanes[k].getAncho()/2 ==this.objetos[x].getX()-(this.objetos[x].getAncho()/2) && this.titanes[k].getY()+this.titanes[k].getAlto()/2>=this.objetos[x].getY()-(this.objetos[x].getAlto()/2) && 
//								this.titanes[k].getY()-this.titanes[k].getAlto()/2<=this.objetos[x].getY()+(this.objetos[x].getAlto()/2) ) {
//							this.titanes[k].setX(this.titanes[k].getX()-1);
//						}
//						if (this.titanes[k].getX()-this.titanes[k].getAncho()/2 ==this.objetos[x].getX()+(this.objetos[x].getAncho()/2) && this.titanes[k].getY()+this.titanes[k].getAlto()/2>=this.objetos[x].getY()-(this.objetos[x].getAlto()/2) && 
//								this.titanes[k].getY()-this.titanes[k].getAlto()/2<=this.objetos[x].getY()+(this.objetos[x].getAlto()/2) ) {
//							this.titanes[k].setX(this.titanes[k].getX()+1);
//						}
//					}
//		         }
				
////////////////obstaculos
				
				// iniciar
				for (int x=0;x<=this.objetos.length-1;x++) {
					 this.objetos[x].dibujarse(entorno);
				}
				
				// colision mikasa obstaculo
				for (int x=0;x<=this.objetos.length-1;x++) {
					//techo superior de los objetos
					if (this.mikasa.getY()+this.mikasa.getAlto()/2 ==this.objetos[x].getY()-(this.objetos[x].getAlto()/2) && this.mikasa.getX()+this.mikasa.getAncho()/2 >= this.objetos[x].getX()- (this.objetos[x].getAncho()/2) && 
							this.mikasa.getX()-this.mikasa.getAncho()/2<=this.objetos[x].getX()+(this.objetos[x].getAncho()/2) ) {
						mikasa.moverArriba();
					}
					if (this.mikasa.getY()-this.mikasa.getAlto()/2==this.objetos[x].getY()+(this.objetos[x].getAlto()/2) && this.mikasa.getX()+this.mikasa.getAncho()/2 >= this.objetos[x].getX()- (this.objetos[x].getAncho()/2) &&
							this.mikasa.getX()-this.mikasa.getAncho()/2<=this.objetos[x].getX()+(this.objetos[x].getAncho()/2) ) {
						mikasa.moverAbajo();
					}
					if (this.mikasa.getX()+this.mikasa.getAncho()/2 ==this.objetos[x].getX()-(this.objetos[x].getAncho()/2) && this.mikasa.getY()+this.mikasa.getAlto()/2>=this.objetos[x].getY()-(this.objetos[x].getAlto()/2) && 
							this.mikasa.getY()-this.mikasa.getAlto()/2<=this.objetos[x].getY()+(this.objetos[x].getAlto()/2)) {
						mikasa.moverIzquierda();
					}
					if (this.mikasa.getX()-this.mikasa.getAncho()/2 ==this.objetos[x].getX()+(this.objetos[x].getAncho()/2) && this.mikasa.getY()+this.mikasa.getAlto()/2>=this.objetos[x].getY()-(this.objetos[x].getAlto()/2) && 
							this.mikasa.getY()-this.mikasa.getAlto()/2<=this.objetos[x].getY()+(this.objetos[x].getAlto()/2)  ) {
						mikasa.moverDerecha();
					}
				}
				
				//colision proyectil con obstaculo
				if (this.mikasa.verdisparo()) {
					for (int x=0;x<=this.objetos.length-1;x++) {
						//techo superior de los objetos
						if (this.proyectil.getY()+this.proyectil.getAlto()/2 >=this.objetos[x].getY()-(this.objetos[x].getAlto()/2) && this.proyectil.getY()+this.proyectil.getAlto()/2 <=this.objetos[x].getY() &&  
								this.proyectil.getX()+this.proyectil.getAncho()/2 >= this.objetos[x].getX()- (this.objetos[x].getAncho()/2) && this.proyectil.getX()-this.proyectil.getAncho()/2<=this.objetos[x].getX()+(this.objetos[x].getAncho()/2) ) {
							this.mikasa.mikadispara(false);
						}
						if (this.proyectil.getY()-this.proyectil.getAlto()/2<=this.objetos[x].getY()+(this.objetos[x].getAlto()/2) && this.proyectil.getY()-this.proyectil.getAlto()/2>=this.objetos[x].getY() && 
								this.proyectil.getX()+this.proyectil.getAncho()/2 >= this.objetos[x].getX()- (this.objetos[x].getAncho()/2) &&this.proyectil.getX()-this.proyectil.getAncho()/2<=this.objetos[x].getX()+(this.objetos[x].getAncho()/2) ) {
							this.mikasa.mikadispara(false);
						}
						if (this.proyectil.getX()+this.proyectil.getAncho()/2 >=this.objetos[x].getX()-(this.objetos[x].getAncho()/2) && this.proyectil.getX()+this.proyectil.getAncho()/2 <=this.objetos[x].getX() && 
								this.proyectil.getY()+this.proyectil.getAlto()/2>=this.objetos[x].getY()-(this.objetos[x].getAlto()/2) && this.proyectil.getY()-this.proyectil.getAlto()/2<=this.objetos[x].getY()+(this.objetos[x].getAlto()/2) ) {
							this.mikasa.mikadispara(false);
						}
						if (this.proyectil.getX()-this.proyectil.getAncho()/2 <=this.objetos[x].getX()+(this.objetos[x].getAncho()/2) && this.proyectil.getX()-this.proyectil.getAncho()/2 >=this.objetos[x].getX() &&  
								this.proyectil.getY()+this.proyectil.getAlto()/2>=this.objetos[x].getY()-(this.objetos[x].getAlto()/2) && this.proyectil.getY()-this.proyectil.getAlto()/2<=this.objetos[x].getY()+(this.objetos[x].getAlto()/2) ) {
							this.mikasa.mikadispara(false);
						}
					}
				}								
				    
/////////////////suero
				    
				//random suero
				 while (estasuero==false) { //mientras el suero no aparesca dentro un un objeto podremos dibujarlo en pantalla
				    	Random Rango = new Random();
				    	int randomx = Rango.nextInt(800) + 1;
				    	int randomy = Rango.nextInt(600) + 1;
				    	 for (int x=0;x<=this.objetos.length-1;x++) {
					    	 if (randomy +11 >=this.objetos[x].getY()-(this.objetos[x].getAlto()) && randomy -11  <=this.objetos[x].getY()+(this.objetos[x].getAlto()) &&
					    	    randomx + 11  >= this.objetos[x].getX()- (this.objetos[x].getAncho()) && randomx - 11 <= this.objetos[x].getX()+(this.objetos[x].getAncho()) ) {
					    	 }else {
					    		//inicializar suero
					    		    this.vacuna=new Suero(randomx,randomy); 
					    		    this.estasuero=true;
					    	 }
						}
				 }
				   
				if (estasuero) { // si mikasa aun no obtiene el suero se dibujara
					this.vacuna.dibujarse(entorno);  
				}
					
				// mikasa toca suero
				if (!mikasa.Verpoder()) { // si mikasa aun no es un titan puede obtener el suero
						
						if (this.mikasa.getY()+this.mikasa.getAlto()/2 >=this.vacuna.getY()-(this.vacuna.getAlto()/2) && this.mikasa.getY()+this.mikasa.getAlto()/2 <=this.vacuna.getY() && 
								this.mikasa.getX()+this.mikasa.getAncho()/2 >= this.vacuna.getX()- (this.vacuna.getAncho()/2) && this.mikasa.getX()-this.mikasa.getAncho()/2<=this.vacuna.getX()+(this.vacuna.getAncho()/2) ) {
							this.mikasa.Convertir(true);
							this.vacuna.setX(-20);
							this.vacuna.setY(-20);
						}
						if (this.mikasa.getY()-this.mikasa.getAlto()/2<=this.vacuna.getY()+(this.vacuna.getAlto()/2) && this.mikasa.getY()-this.mikasa.getAlto()/2>=this.vacuna.getY() && 
								this.mikasa.getX()+this.mikasa.getAncho()/2 >= this.vacuna.getX()- (this.vacuna.getAncho()/2) &&this.mikasa.getX()-this.mikasa.getAncho()/2<=this.vacuna.getX()+(this.vacuna.getAncho()/2) ) {
							this.mikasa.Convertir(true);
							this.vacuna.setX(-20);
							this.vacuna.setY(-20);
						}
						if (this.mikasa.getX()+this.mikasa.getAncho()/2 >=this.vacuna.getX()-(this.vacuna.getAncho()/2) && this.mikasa.getX()+this.mikasa.getAncho()/2 <=this.vacuna.getX() && 
								this.mikasa.getY()+this.mikasa.getAlto()/2>=this.vacuna.getY()-(this.vacuna.getAlto()/2) && this.mikasa.getY()-this.mikasa.getAlto()/2<=this.vacuna.getY()+(this.vacuna.getAlto()/2)) {
							this.mikasa.Convertir(true);
							this.vacuna.setX(-20);
							this.vacuna.setY(-20);
						}
						if (this.mikasa.getX()-this.mikasa.getAncho()/2 <=this.vacuna.getX()+(this.vacuna.getAncho()/2) && this.mikasa.getX()-this.mikasa.getAncho()/2 >=this.vacuna.getX() && 
								this.mikasa.getY()+this.mikasa.getAlto()/2>=this.vacuna.getY()-(this.vacuna.getAlto()/2) && this.mikasa.getY()-this.mikasa.getAlto()/2<=this.vacuna.getY()+(this.vacuna.getAlto()/2)  ) {
							this.mikasa.Convertir(true);
							this.vacuna.setX(-20);
							this.vacuna.setY(-20);
						}
				}
	
				//mikasa titan toca algun kyojin, el kyojin se muere
				for (int x=0;x<=this.titanes.length-1;x++) {
						if (this.mikasa.getY()+this.mikasa.getAlto()/2 >=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && this.mikasa.getY()+this.mikasa.getAlto()/2 <=this.titanes[x].getY() && 
								this.mikasa.getX()+this.mikasa.getAncho()/2 >= this.titanes[x].getX()- (this.titanes[x].getAncho()/2) && this.mikasa.getX()-this.mikasa.getAncho()/2<=this.titanes[x].getX()+(this.titanes[x].getAncho()/2) && 
								    this.mikasa.Verpoder() ) {
							this.titanes[x].Cambiarvivo(false);
							this.titanes[x].setX(0);
							this.titanes[x].setY(0);
							mikasa.Convertir(false);
							this.muertes+=1;		
							this.contadorvivos-=1;
						}
						if (this.mikasa.getY()-this.mikasa.getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2) && this.mikasa.getY()-this.mikasa.getAlto()/2>=this.titanes[x].getY() && 
								this.mikasa.getX()+this.mikasa.getAncho()/2 >= this.titanes[x].getX()- (this.titanes[x].getAncho()/2) &&this.mikasa.getX()-this.mikasa.getAncho()/2<=this.titanes[x].getX()+(this.titanes[x].getAncho()/2) &&  
								    this.mikasa.Verpoder()  ) {
							this.titanes[x].Cambiarvivo(false);
							this.titanes[x].setX(0);
							this.titanes[x].setY(0);
							mikasa.Convertir(false);
							this.muertes+=1;		
							this.contadorvivos-=1;
						}
						if (this.mikasa.getX()+this.mikasa.getAncho()/2 >=this.titanes[x].getX()-(this.titanes[x].getAncho()/2) && this.mikasa.getX()+this.mikasa.getAncho()/2 <=this.titanes[x].getX() && 
								this.mikasa.getY()+this.mikasa.getAlto()/2>=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && this.mikasa.getY()-this.mikasa.getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2)  &&  this.mikasa.Verpoder() ) {
							this.titanes[x].Cambiarvivo(false);
							this.titanes[x].setX(0);
							this.titanes[x].setY(0);
							mikasa.Convertir(false);
							this.muertes+=1;		
							this.contadorvivos-=1;
						}
						if (this.mikasa.getX()-this.mikasa.getAncho()/2 <=this.titanes[x].getX()+(this.titanes[x].getAncho()/2) && this.mikasa.getX()-this.mikasa.getAncho()/2 >=this.titanes[x].getX() &&  
								this.mikasa.getY()+this.mikasa.getAlto()/2>=this.titanes[x].getY()-(this.titanes[x].getAlto()/2) && this.mikasa.getY()-this.mikasa.getAlto()/2<=this.titanes[x].getY()+(this.titanes[x].getAlto()/2)  &&  this.mikasa.Verpoder() ) {
							this.titanes[x].Cambiarvivo(false);
							this.titanes[x].setX(0);
							this.titanes[x].setY(0);
							mikasa.Convertir(false);
							this.muertes+=1;		
							this.contadorvivos-=1;
						}
				}			    
////////////////////////////////////////////////////////////////////////////////////////////////////////
				    entorno.cambiarFont(null,20, Color.black );			
				    entorno.escribirTexto("Kyojin destruidos: "+ this.muertes , 20, 580 );
			
		   }else { 
			   if (star&&!cargando) {
				    this.contador+=1;
					this.inicio.Cargando(entorno);
					entorno.cambiarFont(null,20, Color.black );			
				    entorno.escribirTexto("CARGANDO "+ this.contador/10 , 20, 580 );
				    if(contador==1000) {
				    	cargando=true;
				    }
			   }
		   }

		}
	  }
	}
	public void Choque() {
		
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}


