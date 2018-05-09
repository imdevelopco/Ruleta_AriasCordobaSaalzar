package proyectoRuleta;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class ControlProyectoRuleta.
 */
public class ControlProyectoRuleta {

	/** Array con los jugadores de la ruleta*/
	private ArrayList<Jugador> jugadores = new ArrayList();

	/** Array con las apuestas del tablero (en esta primera entrega solo es un jugador, entonces las apuestas se guardara aca.
	 * cuando se manejen más jugadores, cada uno guardara sus propias apuestas realizadas) */
	private ArrayList<Integer> apuesta1X1 = new ArrayList();
	private ArrayList<Integer> apuesta2X1 = new ArrayList();
	private ArrayList<Integer> apuesta05X1 = new ArrayList();
	private ArrayList<Integer> apuesta5X1 = new ArrayList();
	private ArrayList<Integer> apuesta8X1 = new ArrayList();
	private ArrayList<Integer> apuesta11X1 = new ArrayList();
	private ArrayList<Integer> apuesta17X1 = new ArrayList();
	private ArrayList<Integer> apuesta35X1 = new ArrayList();

	/**Total ganado en la ronda */
	private int totalRonda = 0;

	/** Tiempo para realizar las apuestas*/
	private int time = 10;

	/**Hilo para manejar el tiempo de las apuestas */
	private Thread timerThread;

	/**
	 * Instantiates a new control proyecto ruleta.
	 */
	public ControlProyectoRuleta() {
		System.out.println("Comienzan apuestas");
		RondaNueva ronda = new RondaNueva();
		ronda.start();
	}

	/**
		* Verifica cuales apuestas resultaron ganadoras y realiza los pagos
		*/
	private void calcularGanancia(int resultadoRuleta){
		int ganancia = 0; //acumulador de ganacias
		//verificamos si le aposto al número que arrojo la ruleta
		for(int i = 0; i < apuesta1X1.size(); i++){
      if(resultadoRuleta == apuesta1X1.get(i) ){
				ganancia+=(50+50);
			}
		}

		for(int i = 0; i < apuesta2X1.size(); i++){
      if(resultadoRuleta == apuesta2X1.get(i) ){
				ganancia+=(50+100);
			}
		}

		for(int i = 0; i < apuesta05X1.size(); i++){
      if(resultadoRuleta == apuesta05X1.get(i) ){
				ganancia+=(50+25);
			}
		}

		for(int i = 0; i < apuesta5X1.size(); i++){
      if(resultadoRuleta == apuesta5X1.get(i) ){
				ganancia+= (50+(5*50));
			}
		}

		for(int i = 0; i < apuesta8X1.size(); i++){
      if(resultadoRuleta == apuesta8X1.get(i) ){
				ganancia+= (50+(8*50));
			}
		}

		for(int i = 0; i < apuesta11X1.size(); i++){
      if(resultadoRuleta == apuesta11X1.get(i) ){
				ganancia+= (50+(11*50));
			}
		}

		for(int i = 0; i < apuesta17X1.size(); i++){
      if(resultadoRuleta == apuesta17X1.get(i) ){
				ganancia+= (50+(17*50));
			}
		}

		for(int i = 0; i < apuesta35X1.size(); i++){
      if(resultadoRuleta == apuesta35X1.get(i) ){
				ganancia+= (50+(35*50));
			}
		}

		this.totalRonda = ganancia;
	}

	/**
		* Retorna el total ganado en la ronda
		* @return Total ganado en la ronda
		*/
	public int getTotalRonda(){
		return totalRonda;
	}

	/**
	 * Gets the resultado ruleta.
	 *
	 * @param tb the tb
	 * @return the resultado ruleta
	 
	public int getResultadoRuleta(PanelTablero tb) {
		return tb.getPocisionFinal();
	}
	*/

	/**
	 * Establece el tiempo para realizar las apuestas.
	 *
	 * @param time entero, cantidad de segundos para realizar las apuestas
	 */
	public void setTime(int time) {
		this.time = time;
	}


	/**
	 * The Class RondaNueva.
	 */
	private class RondaNueva implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(time > 0) {
				time-=1;
				setTime(time);
				try {
					Thread.sleep(1000);
					System.out.println("ruleta comienza en: "+time);
				}
				catch(InterruptedException e) {
					return;
				}
			}
			System.out.println("comienza a girar la ruleta");
			System.out.println("mientas gira la ruleta obtenemos las apuestas");
			System.out.println("cuando termine la ruleta, obtenemos el valor de la ruleta");
			System.out.println("calculamos los ganadores y poerdedores, se realiza los pagos");
			PanelRuleta r = new PanelRuleta();
			r.setRuletaGirando(true);
		}

		/**
		 * Start.
		 */
		public void start() {
			timerThread = new Thread(this);
			timerThread.start();
		}

	}


}
