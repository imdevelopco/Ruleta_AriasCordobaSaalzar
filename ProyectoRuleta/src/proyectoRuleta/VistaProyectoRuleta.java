
package proyectoRuleta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VistaProyectoRuleta extends JFrame{

	private PanelTablero tablero;
	private PanelRuleta ruleta;
	private JPanel contenedorPrincipal, panelMensajes, panelTableros;
	private Jugador player;
	private JLabel display;
	private ControlProyectoRuleta control = new ControlProyectoRuleta();
	/** Tiempo para realizar las apuestas*/
	private int time = 10,timeRuleta = 400;
	private boolean estadoJuego = false;
	/**Hilo para manejar el tiempo de las apuestas */
	private Thread timerThread;


	public VistaProyectoRuleta(){

		initGUI();
		pack();

		this.setSize(1200,700);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);

	}

	public void initGUI(){
		contenedorPrincipal = new JPanel(new BorderLayout());
		
		//Paneles: ruleta y tablero de apuestas
		panelTableros = new JPanel(new GridLayout(1,2));
		
		ruleta = new PanelRuleta();
		panelTableros.add(ruleta);
		
		tablero = new PanelTablero();
		panelTableros.add(tablero);
		
		panelTableros.setBackground(Color.GREEN.darker().darker().darker());
		contenedorPrincipal.add(panelTableros, BorderLayout.CENTER);
		
		//mensajes para el jugador
		display = new JLabel();
		contenedorPrincipal.add(display,BorderLayout.NORTH);
		
		this.add(contenedorPrincipal);
		
		setTitle("TABLERO DE APUESTAS");
		
		display.setText("Comienzan apuestas");
		RondaNueva ronda = new RondaNueva();
		ronda.start();
		
		
	}
	
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
					Thread.sleep(200);
					display.setText("ruleta comienza en: "+time);
					estadoJuego=true;
				}
				catch(InterruptedException e) {
					return;
				}
			}
			
			display.setText("comienza a girar la ruleta");
			display.setText("mientas gira la ruleta obtenemos las apuestas");
			display.setText("cuando termine la ruleta, obtenemos el valor de la ruleta");
			display.setText("calculamos los ganadores y perdedores, se realiza los pagos");
			try{
				while(timeRuleta>0){
					timeRuleta-=1;
					ruleta.repaint();
					Thread.sleep(10);
					ruleta.setRuletaGirando(false);
				}
				System.out.println(ruleta.getPosicionPausa()+"++");
				System.out.println(ruleta.getCasillaGanadora());
				System.out.println(ruleta.getPosicionPausa()+"++");
			}catch(InterruptedException e) {
				return;
			}
		

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
