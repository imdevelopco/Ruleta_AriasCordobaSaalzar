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
		System.out.println("[debug] Comienzan apuestas");
	}

	public void controlGirarRuleta(PanelRuleta ruleta, PanelTablero tablero){
		System.out.println("[debug] Control: entre a controlGirarRuleta");
		ruleta.setRuletaGirando(true);
		ruleta.girarRuleta(); //empezar a girar la ruleta
		apuesta1X1 = tablero.getApuesta1X1(); //obtener las apuestas 1x1 que se hizo
		apuesta2X1 = tablero.getApuesta2X1(); //obtener las apuestas 2X1 que hay en el ytablero
		apuesta05X1 = tablero.getApuesta05X1(); //obteber apuestas 05X1
		apuesta5X1 = tablero.getApuesta5X1(); //obtener las apuestas 5X1
		apuesta8X1 = tablero.getApuesta8X1(); //obtener las apuestas 8X1
		apuesta11X1 = tablero.getApuesta11X1(); //obtener apuestas 11X1
		apuesta17X1 = tablero.getApuesta17X1(); //obtener apuestas 17X1
		apuesta35X1 = tablero.getApuesta35X1(); //obtener apuesta 35X1;
		System.out.println("[debug] Control: total apuestas 1X1 "+apuesta1X1.size());
		System.out.println("[debug] Control: total apuestas 2X1 "+apuesta2X1.size());

		//esperar que la ruleta pare
		Thread delay = new Thread(){
            public synchronized void run(){
              try {
                	sleep(5000);
									System.out.println("[debug] Control: numero ganador= "+ruleta.getNumeroGanador());
									int ganancia = calcularGanancia(ruleta.getNumeroGanador());
									tablero.resetColors(); //limpiar las casillas en las que se apostaron
									tablero.setTotal(ganancia);
            	}catch (InterruptedException ex){
            		ex.printStackTrace();
            	}
            }
		};
		delay.start();
	}

	/**
		* Verifica cuales apuestas resultaron ganadoras y realiza los pagos
		*/
	private int calcularGanancia(int resultadoRuleta){
		int ganancia = 0; //acumulador de ganacias
		//verificamos si le aposto al número que arrojo la ruleta
		for(int i = 0; i < apuesta1X1.size(); i++){
      if(resultadoRuleta == apuesta1X1.get(i) ){
				ganancia+=(50+50);
				System.out.println("[debug] Control: Apuesta 1x1 resultado = "+resultadoRuleta+" Ganaste!");
			}
		}

		for(int i = 0; i < apuesta2X1.size(); i++){
      if(resultadoRuleta == apuesta2X1.get(i) ){
				ganancia+=(50+100);
				System.out.println("[debug] Control: Apuesta 2x1 resultado = "+resultadoRuleta+" Ganaste!");
			}
		}

		for(int i = 0; i < apuesta05X1.size(); i++){
      if(resultadoRuleta == apuesta05X1.get(i) ){
				ganancia+=(50+25);
				System.out.println("[debug] Control: Apuesta 05x1 resultado = "+resultadoRuleta+" Ganaste!");
			}
		}

		for(int i = 0; i < apuesta5X1.size(); i++){
      if(resultadoRuleta == apuesta5X1.get(i) ){
				ganancia+= (50+(5*50));
				System.out.println("[debug] Control: Apuesta 5x1 resultado = "+resultadoRuleta+" Ganaste!");
			}
		}

		for(int i = 0; i < apuesta8X1.size(); i++){
      if(resultadoRuleta == apuesta8X1.get(i) ){
				ganancia+= (50+(8*50));
				System.out.println("[debug] Control: Apuesta 8x1 resultado = "+resultadoRuleta+" Ganaste!");
			}
		}

		for(int i = 0; i < apuesta11X1.size(); i++){
      if(resultadoRuleta == apuesta11X1.get(i) ){
				System.out.println("[debug] Control: Apuesta 11x1 resultado = "+resultadoRuleta+" Ganaste!");
				ganancia+= (50+(11*50));
			}
		}

		for(int i = 0; i < apuesta17X1.size(); i++){
      if(resultadoRuleta == apuesta17X1.get(i) ){
				System.out.println("[debug] Control: Apuesta 17x1 resultado = "+resultadoRuleta+" Ganaste!");
				ganancia+= (50+(17*50));
			}
		}

		for(int i = 0; i < apuesta35X1.size(); i++){
      if(resultadoRuleta == apuesta35X1.get(i) ){
				System.out.println("[debug] Control: Apuesta 35x1 resultado = "+resultadoRuleta+" Ganaste!");
				ganancia+= (50+(35*50));
			}
		}

		System.out.println("[debug] Control: Ganancia = "+ganancia);
		this.totalRonda = ganancia;
		return ganancia;
	}

	/**
		* Retorna el total ganado en la ronda
		* @return Total ganado en la ronda
		*/
	public int getTotalRonda(){
		return totalRonda;
	}

}