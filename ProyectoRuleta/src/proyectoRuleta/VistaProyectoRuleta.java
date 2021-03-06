package proyectoRuleta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
	private JButton startButton = new JButton("comenzar");

	private Socket conexion; // conexion con el servidor
	private Scanner entrada; // entrada del servidor
	private Formatter salida; // salida al servidor
	private String HOST;
	private ConexionServer hiloSocket;

	public VistaProyectoRuleta(String host){
		HOST = host;
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
		//tablero.setLayout(null);
		panelTableros.add(tablero);

		contenedorPrincipal.add(panelTableros, BorderLayout.CENTER);

		MouseEvents evento = new MouseEvents();
		startButton.addMouseListener(evento);
		contenedorPrincipal.add(startButton, BorderLayout.NORTH);

		//mensajes para el jugador
		display = new JLabel();
		Font texto = new Font(Font.SERIF, Font.BOLD, 32);
		display.setFont(texto);
		display.setBackground(Color.BLUE);
		contenedorPrincipal.add(display,BorderLayout.SOUTH);

		this.add(contenedorPrincipal);

		setTitle("TABLERO DE APUESTAS");

		display.setText("Comienzan apuestas");
	}

	public void execute(){
		hiloSocket = new ConexionServer();
		hiloSocket.run();
	}


	public void setTime(int time) {
		this.time = time;
	}


	private class ConexionServer implements Runnable{

		@Override
		public void run() {
			// se conecta al servidor, obtiene los flujos e inicia subproceso de salida

			try {
				System.out.println("[Cliente] intentar conectarse al servidor...");
				conexion = new Socket(InetAddress.getByName( HOST ), 12345 );

				entrada = new Scanner( conexion.getInputStream() );
				salida = new Formatter( conexion.getOutputStream() );
			}
			catch ( IOException excepcionES ){
				excepcionES.printStackTrace();
				System.out.println("[Cliente] Error al conectarse al servidor");
			}
			System.out.println("[Cliente] socket address: "+conexion.getLocalSocketAddress());
			System.out.println("[Cliente] socket puerto: "+conexion.getPort());
			System.out.println("[Cliente] socket status: "+conexion.isConnected() );

			while ( true ) {
				if ( entrada.hasNextLine() )
			  	handlerMesageServer(entrada.nextLine());
			} // fin de while

		}

	}

 	private void handlerMesageServer(String msn){
		display.setText( "[server] "+msn );
		if(!msn.equals("Conectado al servidor")){
				ruleta.girarRuleta( Integer.parseInt(msn) );
		}
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
					display.setText("ruleta comienza en: "+time);
					System.out.println("estoy en el hilo de la vista");
					estadoJuego=true;
				}
				catch(InterruptedException e) {
					return;
				}
			}

			control.controlGirarRuleta(ruleta, tablero);
			display.setText("mientas gira la ruleta obtenemos las apuestas");
			display.setText("cuando termine la ruleta, obtenemos el valor de la ruleta");
			display.setText("calculamos los ganadores y perdedores, se realiza los pagos");

		}

		/**
		 * Start.
		 */
		public void start() {
			Thread timerThread = new Thread(this);
			timerThread.start();
		}
	}

	private class MouseEvents implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			JButton clikeado = (JButton) e.getSource();
			if(clikeado == startButton){

				System.out.println("Comenzar juego");
				setTime(10);
				RondaNueva ronda = new RondaNueva();
				ronda.start();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
