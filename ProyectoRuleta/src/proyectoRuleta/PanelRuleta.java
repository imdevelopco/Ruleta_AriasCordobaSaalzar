package proyectoRuleta;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelRuleta extends JPanel {
	// Arreglo de posiciones de las 37 casillas (angulos en los que se debe girar la imagen)
	int[] arr = {0,9,20,29,39,49,59,69,79,88,98,107,117,127,147,
			156,166,176,185,195,205,215,223,233,243,253,263,
			273,283,293,303,313,322,332,341,351};
	/*
	 * posiciones de los angulos
	 * i=0 casilla 0
	 * i=9 casilla 26
	 * i=20 casilla 3
	 * i=29 casilla 35
	 * i=39 casilla 12
	 * i=49 casilla 28
	 * i=59 casilla 7
	 * i=69 casilla 29
	 * i=79 casilla 18
	 * i= 88 casilla 22
	 * i = 98 casilla 9
	 * i =  107 casilla 31
	 * i =  117 casilla 14
	 * i =  127 casilla 20
	 * i =  147 casilla 33
	 * i=   156 casilla  16
	 * i =  166 casilla 24
	 * i =  176 casilla 5
	 * i =  185 casilla 10
	 * i =  195 casilla 23
	 * i =  205 casilla 8
	 * i =  215 casilla 30
	 * i =  223 casilla 11
	 * i =  233 casilla 36
	 * i =  243 casilla 13
	 * i =  253 casilla 27
	 * i =  263 casilla 6
	 * i =  273 casilla 34
	 * i =  283 casilla 17
	 * i =  293 casilla 25
	 * i =  303 casilla 2
	 * i =  313 casilla 21
	 * i =  322 casilla 4
	 * i =  332 casilla 19
	 * i =  341 casilla 15
	 * i =  351 casilla 32
	 */
	/** Posicion de casillas (en el mismo orden del arreglo arr)*/
	
	int [] casillas = {	0,26,3,35,12,28,7,29,18,22,9,3,14,20,33,16,
			24,5,10,23,8,30,11,36,13,27,6,34,17,25,2,21,4,19,15,32};
	/** Atributo que se toma como el estado de la ruleta*/
	private boolean ruletaGirando;


	/** variable que aloja el angulo que esta en el arreglo arr*/
	private int posicionFinal=0;
	/** variable que aloja el la posicion en el arreglo casillas*/
	private int posPausa=0;
	/** Variable que aloja el numero ganador internamente*/
	private int casillaGanadora=0;

	/**Numero ganador, este es el que se le entrega al control*/
	private int numeroGanador= 0;

	/** iterador que ira aumentando para facilitar el repaint de la img.*/
	private int iteradorImagen=0;
	/** Atributo que se toma como temporizzador*/
	private int timeRuleta = 400;
	/** Hilo encargado de ejecutar la ruleta*/
	private Thread timerThread;

	/** instancia tipo Runnable, inicia el hilo de la ruleta*/

	private RondaNueva ronda = new RondaNueva();

	/**
	 * Metodo que  recorre el arreglo arr(el que contiene los angulos
	 * en los que se debe posicionar la imagen para que la
	 * flecha quede en el centro de la casilla)
	 * */
	public int posiciones(int iterador) {

			posicionFinal=arr[iterador%arr.length];

			return posicionFinal;
		}
	/**
	 * asigna el valor del arreglo casillas a la variable casillaGanadora
	 * y asi se puede saber en que casilla se detuvo la ruleta
	 * 
	 * */

	public void setCasillaGanadora(int n) {
			casillaGanadora = casillas[n];
		
	}

/**
 * Se utiliza uba excepcion para cargar la imagen, el metodo retorna el biufer
 * que se setea dentro de este (se carga la imagen)
 * 
 */
	BufferedImage cargarImagen(String ruta) {
		BufferedImage img =null;
		try {
			img = ImageIO.read(new File(ruta));
		}catch(IOException e) {
			System.out.println("Error al Cargar la imagen");
		}
		return img;
	}
	/** 
	 * devuelve la posicion en la que se paro la ruleta
	 * */
	public int getPosicionPausa() {
		return posPausa;
		}
	public void setPosicionPausa(int n) {
		posPausa=n;
	}

	/**
	 * Retorna la casilla ganadora interna
	 */
	public int getCasillaGanadora() {
		return casillaGanadora;
	}

	/**
	  *Retorna el numero ganador, este es el numero que aparece en la vista de la ruleta
	  */
	public int getNumeroGanador(){
		return numeroGanador;
	}
	
	/** cambia el estado de la ruleta (si esta girando o no)*/
	public void setRuletaGirando(boolean booleano) {
		ruletaGirando=booleano;
	}
	
	/** Cambia el valor del atributo, el que se toma como temporizador*/
	public void setTimeRuleta(int time){
		this.timeRuleta = time;
	}

	/**
	 * Pinta los componentes en el JPanel
	 * 
	 * */
	public void paintComponent(Graphics g) {

		// se cargan las imagenes
		BufferedImage ruleta = cargarImagen("src/imagenes/Ruleta.png");
		BufferedImage flecha = cargarImagen("src/imagenes/Flecha.png");

		//posicion en la que se ubicara la imagen a rotar
		AffineTransform at = AffineTransform.getTranslateInstance(50, 50);

		//el metodo posiciones devuelve el entero (angulo) en la posicion iteradorimagen ,

		at.rotate(Math.toRadians(posiciones(iteradorImagen++)),ruleta.getWidth()/2,ruleta.getHeight()/2);
		Graphics2D ruleta2D=(Graphics2D) g;

		ruleta2D.setColor(Color.GREEN.darker().darker().darker());

		ruleta2D.fillRect(0, 0, this.getWidth(), this.getHeight());

		ruleta2D.drawImage(ruleta, at, null);
		ruleta2D.drawImage(flecha, 275, 85,50,50,this);
		if(ruletaGirando==true) {

		}
	}
	
	/** 
	 * metodo que empieza el hilo (Jugabilidad de la ruleta)
	 * */
	public void girarRuleta(){
		System.out.println("Entre a girarRuleta");
		ronda.start();
	}

	private class RondaNueva implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				System.out.println("comienza a girar la ruleta en PanelRuleta");
				while(timeRuleta>0){
					timeRuleta-=1;
					repaint();
					Thread.sleep(10);
					setRuletaGirando(false);
				}
				posPausa=iteradorImagen%arr.length;
				casillaGanadora= casillas[posPausa];
				System.out.println(getPosicionPausa()+"++");
				System.out.println(getCasillaGanadora());
				numeroGanador = getCasillaGanadora();
				System.out.println(getPosicionPausa()+"++");
			}catch(InterruptedException e) {
				return;
			}
		}

		public void start() {
			setTimeRuleta(400);
			timerThread = new Thread(this);
			timerThread.start();
		}
	}
}