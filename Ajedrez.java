import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


	/**
	 * Nombre del método:       main
	 * Descripción:             <i>Método para </i>
	 * @author                  hernan.gil
	 * @param args
	 *
	 *  MODIFICACIONES:
	 * 
	 *  Fecha:                  11/02/2016
	 *  Autor - Empresa:        hernan.gil
	 *  Descripción:            Creación del método
	 */

public class Ajedrez {

	//Clase singleton
	private static Ajedrez INSTANCE = new Ajedrez();
	private List<String> jugadas = new LinkedList<String>();
	private List<Pieza> piezasEnPassant = new ArrayList<Pieza>();
	
	private List<Posicion> casillasAtacadas = new ArrayList<Posicion>();
	private List<Posicion> listaCasillasAtacadasXBlanco = new ArrayList<Posicion>();
	private List<Posicion> listaCasillasAtacadasXNegro = new ArrayList<Posicion>();
	
	private Pieza.Color turnoColor = Pieza.Color.WHITE;
	private String quienArriba = "";
	
	private Ajedrez(){};
	
	private boolean enJaque = false;
	private boolean enMate = false;
	private boolean gameOver = false;
	private boolean tablas = false;
	
	private String piezas[] = {"T","C","A","D","R","P"};
	private static final Map<String, Integer> valorPiezas;
	static {
		valorPiezas = new HashMap<String, Integer>();
		valorPiezas.put("T", 5);
		valorPiezas.put("C", 3);
		valorPiezas.put("A", 4);
		valorPiezas.put("D", 10);
		valorPiezas.put("R", 20);
		valorPiezas.put("P", 1);
	}
	
	public static Ajedrez getInstance() {return INSTANCE;}
	
	private List<Pieza> piezasJaqueadoras = new ArrayList<Pieza>();
	
	private String t[][] = {
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""}
	};

	private Pieza t1[][] = new Pieza[8][8];
	//Solo para ver graficamente y orientarme
	private String tCoordenadas[][] = {
			{"00","01","02","03","04","05","06","07"},
			{"10","11","12","13","14","15","16","17"},
			{"20","21","22","23","24","25","26","27"},
			{"30","31","32","33","34","35","36","37"},
			{"40","41","42","43","44","45","46","47"},
			{"50","51","52","53","54","55","56","57"},
			{"60","61","62","63","64","65","66","67"},
			{"70","71","72","73","74","75","76","77"}
		};
	//Para procesar las jugadas
	private final String tEquivalenteNatural[][] = {
			{"a8","b8","c8","d8","e8","f8","g8","h8"},
			{"a7","b7","c7","d7","e7","f7","g7","h7"},
			{"a6","b6","c6","d6","e6","f6","g6","h6"},
			{"a5","b5","c5","d5","e5","f5","g5","h5"},
			{"a4","b4","c4","d4","e4","f4","g4","h4"},
			{"a3","b3","c3","d3","e3","f3","g3","h3"},
			{"a2","b2","c2","d2","e2","f2","g2","h2"},
			{"a1","b1","c1","d1","e1","f1","g1","h1"}
		};
	
	public void posicionInicialPiezasBlancas(boolean arriba){
		if(arriba){
			//Pintar
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|TB|CB|AB|DB|RB|AB|CB|TB|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|PB|PB|PB|PB|PB|PB|PB|PB|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|  |XX|  |XX|  |XX|  |XX|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|XX|  |XX|  |XX|  |XX|  |");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|  |XX|  |XX|  |XX|  |XX|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|XX|  |XX|  |XX|  |XX|  |");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|PN|PN|PN|PN|PN|PN|PN|PN|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|TN|CN|AN|DN|RN|AN|CN|TN|");
		} else {
			//Pintar
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|TN|CN|AN|DN|RN|AN|CN|TN|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|PN|PN|PN|PN|PN|PN|PN|PN|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|  |XX|  |XX|  |XX|  |XX|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|XX|  |XX|  |XX|  |XX|  |");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|  |XX|  |XX|  |XX|  |XX|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|XX|  |XX|  |XX|  |XX|  |");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|PB|PB|PB|PB|PB|PB|PB|PB|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|TB|CB|AB|DB|RB|AB|CB|TB|");
		}
	}

	private void posicionarPiezasGraficas(boolean arriba) {
		String colorArriba, colorAbajo;
		if (arriba) {
			colorArriba = "B";
			colorAbajo = "N";
			// Registrar posicion
			t[0][0] = "T" + colorArriba;
			t[0][1] = "C" + colorArriba;
			t[0][2] = "A" + colorArriba;
			t[0][3] = "D" + colorArriba;
			t[0][4] = "R" + colorArriba;
			t[0][5] = "A" + colorArriba;
			t[0][6] = "C" + colorArriba;
			t[0][7] = "T" + colorArriba;
			for (int i= 0; i <8; i++) {
				t[1][i] = "P" + colorArriba;
				t[6][i] = "P" + colorAbajo;
			}
			t[7][0] = "T" + colorAbajo;
			t[7][1] = "C" + colorAbajo;
			t[7][2] = "A" + colorAbajo;
			t[7][3] = "D" + colorAbajo;
			t[7][4] = "R" + colorAbajo;
			t[7][5] = "A" + colorAbajo;
			t[7][6] = "C" + colorAbajo;
			t[7][7] = "T" + colorAbajo;
		} else {
			colorArriba = "N";
			colorAbajo = "B";
			// Registrar posicion
			t[7][0] = "T" + colorArriba;
			t[7][1] = "C" + colorArriba;
			t[7][2] = "A" + colorArriba;
			t[7][3] = "D" + colorArriba;
			t[7][4] = "R" + colorArriba;
			t[7][5] = "A" + colorArriba;
			t[7][6] = "C" + colorArriba;
			t[7][7] = "T" + colorArriba;
			for (int i= 0; i <8; i++) {
				t[1][i] = "P" + colorAbajo;
				t[6][i] = "P" + colorArriba;
			}
			t[0][0] = "T" + colorAbajo;
			t[0][1] = "C" + colorAbajo;
			t[0][2] = "A" + colorAbajo;
			t[0][3] = "D" + colorAbajo;
			t[0][4] = "R" + colorAbajo;
			t[0][5] = "A" + colorAbajo;
			t[0][6] = "C" + colorAbajo;
			t[0][7] = "T" + colorAbajo;
		}
	}

	private void posicionarPiezas(boolean arriba) {
		Pieza.Color colorArriba, colorAbajo;
		if (arriba) {
			colorArriba = Pieza.Color.WHITE;
			colorAbajo = Pieza.Color.BLACK;
			// Registrar posicion
			t1[0][0] = new Torre(colorArriba);
			t1[0][1] = new Caballo(colorArriba);
			t1[0][2] = new Alfil(colorArriba);
			t1[0][3] = new Dama(colorArriba);
			t1[0][4] = new Rey(colorArriba,false,true);
			t1[0][5] = new Alfil(colorArriba);
			t1[0][6] = new Caballo(colorArriba);
			t1[0][7] = new Torre(colorArriba);
			for (int i= 0; i <8; i++) {
				t1[1][i] = new Peon(colorArriba,true,false,false,false);
				t1[6][i] = new Peon(colorAbajo,false,false,false,false);
			}
			t1[7][0] = new Torre(colorAbajo);
			t1[7][1] = new Caballo(colorAbajo);
			t1[7][2] = new Alfil(colorAbajo);
			t1[7][3] = new Dama(colorAbajo);
			t1[7][4] = new Rey(colorAbajo,false,true);
			t1[7][5] = new Alfil(colorAbajo);
			t1[7][6] = new Caballo(colorAbajo);
			t1[7][7] = new Torre(colorAbajo);
		} else {
			colorArriba = Pieza.Color.BLACK;
			colorAbajo = Pieza.Color.WHITE;
			// Registrar posicion
			t1[7][0] = new Torre(colorArriba);
			t1[7][1] = new Caballo(colorArriba);
			t1[7][2] = new Alfil(colorArriba);
			t1[7][3] = new Dama(colorArriba);
			t1[7][4] = new Rey(colorArriba,false,true);
			t1[7][5] = new Alfil(colorArriba);
			t1[7][6] = new Caballo(colorArriba);
			t1[7][7] = new Torre(colorArriba);
			for (int i= 0; i <8; i++) {
				t1[1][i] = new Peon(colorAbajo,true,false,false,false);
				t1[6][i] = new Peon(colorArriba,false,false,false,false);
			}
			t1[0][0] = new Torre(colorAbajo);
			t1[0][1] = new Caballo(colorAbajo);
			t1[0][2] = new Alfil(colorAbajo);
			t1[0][3] = new Dama(colorAbajo);
			t1[0][4] = new Rey(colorAbajo,false,true);
			t1[0][5] = new Alfil(colorAbajo);
			t1[0][6] = new Caballo(colorAbajo);
			t1[0][7] = new Torre(colorAbajo);
		}
	}

	public String getPieza(Pieza p){
		String piecita = p.getClass().getSimpleName();
		
		if("Torre".equals(p.getClass().getSimpleName())) piecita = "T";
		if("Alfil".equals(p.getClass().getSimpleName())) piecita = "A";
		if("Caballo".equals(p.getClass().getSimpleName())) piecita = "C";
		if("Dama".equals(p.getClass().getSimpleName())) piecita = "D";
		if("Rey".equals(p.getClass().getSimpleName())) piecita = "R";
		if("Peon".equals(p.getClass().getSimpleName())) piecita = "P";

//		System.err.println(Thread.currentThread().getStackTrace()[2] + "Color: " + p.getColor().toString().equals("BLACK"));
		return piecita +(p.getColor().toString().equals("BLACK")?"B":"N");
	}
	
	public String getTipoPieza(int x, int y, Pieza pieza){
		String p = getPieza(pieza);
//		System.err.println(Thread.currentThread().getStackTrace()[2] + "getTipoPieza / Pieza: " + p);
		return p;
	}
	
	public Pieza getPieza(int x, int y){
		if(x < 0) return null;
		if(y < 0) return null;
		return t1[x][y];
	}
	
	public void setCasilla(int x, int y, Pieza pieza){
		t[x][y] = pieza.toString();//Gráfico
	}
	
	public void borrarCasilla(int x, int y){
		t[x][y] = ""; //o null - grafica
		t1[x][y] = null; // juego - logica
	}
	
	public String casillaOcupada(int x, int y){
		System.err.println(Thread.currentThread().getStackTrace()[2] + "Evaluando x: " + x + " y: " + y);
		if(null == getPieza(x, y))
			return "";
		else 
			return getPieza(x, y).toString();
	}
	
	/**
	 * observable :  pieza
	 * data : puede ser un dato relevante -> definir despues
	 */
//	@Override
//	public void update(Observable observable, Object data) {
//		System.err.println(Thread.currentThread().getStackTrace()[2] + "El Tablero esta siendo notificado...");
//	}
	
	public static void main(String[] args) {
//		String nombre = "Hernan ";
//		String nombres[] = new String[2];
//		nombres[0] = nombre.split(" ")[0];
		//nombres[1] = nombre.split(" ")[1];
//		if(null == nombre.split(" ")[1])
//			nombres[1] = "";
//		System.err.println(Thread.currentThread().getStackTrace()[2] + "Nombre 1: " + nombres[0] + " / nombre 2: " + nombres[1]);
		Ajedrez ta = new Ajedrez();
		boolean esArribaBlancas = false;
		ta.quienArriba = esArribaBlancas?"B":"N"; //variable para que funcione el metodo validarSentido 
		ta.posicionarPiezas(esArribaBlancas);//Logico
		ta.posicionarPiezasGraficas(esArribaBlancas);//Gráfico
		System.out.print(ta.toString());
		int numJugada = 1;
//		int i = 3;
		while(!ta.gameOver /*|| i > 0*/){
//			ta.llenarCasillasAtacadas();//Este codigo genera movimientos iniciales para dama ???
//			System.err.println(Thread.currentThread().getStackTrace()[2] + "Atacadas por blanco: " +  ta.casillasAtacadasXBlanco.size());
//			System.err.println(Thread.currentThread().getStackTrace()[2] + "Atacadas por negro : " +  ta.casillasAtacadasXNegro.size());
//			Leer movimiento tipo e4e6 o Pe4
//			Mover m = new Mover();
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			System.out.print("Digite su jugada: " );
			try {
				String jugada = br.readLine();
//				Validar movimiento: Si el movimiento no termina en mate-> continue jugando
				if (null != jugada && jugada.equals("quit")) {
					ta.gameOver = true;
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Se terminó el juego.");
					break;
				}
				if( ta.moverPieza(jugada)) {
					System.err.println("Jugada válida.");
					if(ta.turnoColor.equals("BLACK"))
						numJugada++;
				} else {
					System.err.println("Jugada no válida.");
				}
				System.out.print(ta.toString());
				//ta.evaluarJuego();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			gameOver = true;
			System.out.println("");
		}
	}

	private void evaluarJuego() {
		int valorBlanco = 0, valorNegro = 0;
		for (int i = 0; i < ChessRules.SIZE; i++) {
			for (int j = 0; j < ChessRules.SIZE; j++) {
				if ( t[i][j].contains("B") ){
					if (t[i][j].contains("D")) valorBlanco += valorPiezas.get("D");
					if (t[i][j].contains("T")) valorBlanco += valorPiezas.get("T");
					if (t[i][j].contains("A")) valorBlanco += valorPiezas.get("A");
					if (t[i][j].contains("C")) valorBlanco += valorPiezas.get("C");
					if (t[i][j].contains("P")) valorBlanco += valorPiezas.get("P");
				} else if ( t[i][j].contains("N") ){
					if (t[i][j].contains("D")) valorNegro += valorPiezas.get("D");
					if (t[i][j].contains("T")) valorNegro += valorPiezas.get("T");
					if (t[i][j].contains("A")) valorNegro += valorPiezas.get("A");
					if (t[i][j].contains("C")) valorNegro += valorPiezas.get("C");
					if (t[i][j].contains("P")) valorNegro += valorPiezas.get("P");
				} 
			}
		}
		//System.err.println(Thread.currentThread().getStackTrace()[2] + "Blanco: " + valorBlanco + " / Negro: " + valorNegro + " - Diferencia: " + (valorBlanco - valorNegro));
	}

	private Pieza.Color nextColor(Pieza.Color color) {
		int indice = color.ordinal();
//		System.err.println(Thread.currentThread().getStackTrace()[2] + "Indice: " + indice + " / longitud: " + Pieza.Color.values().length);
		if ((indice + 1) == Pieza.Color.values().length) {
			return Pieza.Color.values()[indice-1];
		} else {
			return Pieza.Color.values()[indice+1];
		}
	}
	
	public boolean validarCasillaOcupadaMismoColor (Posicion ini, Posicion fin) {
		boolean result = false;
		if ( null == getPieza(fin.getX(), fin.getY()) ) {
			result = true; //Puede
		} else {
			String tmpIniCol =  getPieza(ini.getX(), ini.getY()).toString().contains("N")?"N":"B";
			String tmpFinCol =  getPieza(fin.getX(), fin.getY()).toString().contains("N")?"N":"B";
			if ( tmpIniCol.equals(tmpFinCol) ) {
				result = false; //No puede
			} else {
				result = true;
			}
		}
		return result;
	}
	
	public boolean validarMovimientoPeon(Posicion ini, Posicion fin, Pieza p){
		System.err.println(Thread.currentThread().getStackTrace()[2] + " validarMovimientoPeon / p.enPassant: " + p.enPassant);
		boolean result = true;
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
				System.err.println(Thread.currentThread().getStackTrace()[2] + " PosActual: (" + posicionActual.getX() + ", " + posicionActual.getY() + ")" );
//				if( getPieza()){
//					return result;
//				}
				if( ini.getY() == fin.getY() && null == getPieza(posicionActual.getX(), posicionActual.getY())) {
					if (p.baja) {
						if(ini.getX() < fin.getX()){
							System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de peon en avance válido. Con peon que baja.");
							if(ini.getX() < posicionActual.getX() && null != getPieza(posicionActual.getX(), posicionActual.getY()))
								result = false;
							else
								result = true;
						} else {
							System.err.println(Thread.currentThread().getStackTrace()[2]);
							result = false;
						} 
					} else { // peon no baja
						if(ini.getX() > fin.getX()){
							System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de peon en avance válido. Con peon que no baja.");
							if(ini.getX() < posicionActual.getX() && null != getPieza(posicionActual.getX(), posicionActual.getY()))
								result = false;
							else
								result = true;
						} else {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de peon no válido.");
							result = false;
						} 
					}
				} 
				if( null != getPieza(fin.getX(), fin.getY())/*null != getPieza(posicionActual.getX() + 1, posicionActual.getY() + 1) || null != piezasEnPassant && piezasEnPassant.contains(p)*/) {
					//Movimiento de captura con peon
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de captura con el peon derecha.");
					result = true;
				} else if( null != getPieza(posicionActual.getX() + 1, posicionActual.getY() - 1)/* && (posicionActual.getY() - 1) >= 0 && (posicionActual.getX() + 1) < ChessRules.SIZE*//*|| null != piezasEnPassant && piezasEnPassant.contains(p)*/) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de captura con el peon izquierda.");
					result = true;
				}
			}
		}
		if(result){
			result = validarCasillaOcupadaMismoColor(ini, fin);
		}
		if (result && (Math.abs((ini.getX() - fin.getX())) == 2)){
			System.err.println(Thread.currentThread().getStackTrace()[2] + " validarMovimientoPeon informa que salto dos.");
			p.saltoDos = true; 
		}
		return result;
	}

	public boolean validarMovimientoCaballo(Posicion ini, Posicion fin, Pieza p){
		boolean result = false;
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
				if(null != getPieza(fin.getX(), fin.getY()) ){
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento no válido con el Caballo. No puede saltar sobre una pieza del mismo color.");
					result = false;
					break;
				} else {
//					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento con el Caballo.");
					result = true;
				}
			}
		}
		if(result){
			result = validarCasillaOcupadaMismoColor(ini, fin);
		}
		return result;
	}

	public boolean validarMovimientoAlfil(Posicion ini, Posicion fin, Pieza p){
		boolean result = true;
		String direccionMovimiento = ejeDesplazamientoDama(ini, fin);
		System.err.println(Thread.currentThread().getStackTrace()[2] + "direccionMovimiento: " + direccionMovimiento);
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			String direccionMovimientoActual = ejeDesplazamientoDama(ini, posicionActual);
			System.err.println(Thread.currentThread().getStackTrace()[2] + "direccionMovimientoActual: " + direccionMovimientoActual + " posActual->" + "x:"+posicionActual.getX()+",y:"+posicionActual.getY());
			if (direccionMovimiento.equals(direccionMovimientoActual)) {
				if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
					System.err.println(Thread.currentThread().getStackTrace()[2]);
					if ( null != getPieza(posicionActual.getX(), posicionActual.getY())){
						System.err.println(Thread.currentThread().getStackTrace()[2] + " El Alfil no puede pasar sobre una pieza." + "x:"+posicionActual.getX()+",y:"+posicionActual.getY());
						result = false;
						break;
					} else {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Sigo buscando movimientos validos con el Alfil.");
						if (fin.getX() == posicionActual.getX() && fin.getY() == posicionActual.getY())
							return true;
						else 
							result = true;
					}
				}
			} 
		}
		result = listaDeMovimientosPieza.contains(fin);
		if(result){
			result = validarCasillaOcupadaMismoColor(ini, fin);
		}
		return result;
	}

	private String validarSentido(Posicion ini, Posicion fin, boolean sentido) {
		String result = "";
//		int deltaX = fin.getX() - ini.getX();
//		int deltaY = fin.getY() - ini.getY();
//		if(sentido) {
//			if (deltaX >= 0 && deltaY >= 0) result = "1IA";
//			else if (deltaX <= 0 && deltaY >= 0) result = "2IB";
//			else if (deltaX >= 0 && deltaY <= 0) result = "3DA";
//			else if (deltaX <= 0 && deltaY <= 0) result = "4IB";
//		} else {
//			if (deltaX >= 0 && deltaY >= 0) result = "5DB";
//			else if (deltaX <= 0 && deltaY >= 0) result = "6DA";
//			else if (deltaX >= 0 && deltaY <= 0) result = "7IB";
//			else if (deltaX <= 0 && deltaY <= 0) result = "8IA";
//		}
		//System.err.println(Thread.currentThread().getStackTrace()[2] + "510:Sentido: " + result + " deltaX: " + deltaX + " / deltaY: " + deltaY + " / sentido: " + sentido);
		return result;
	}
	/*
	 * Regresa con true si son la misma posicion.
	 */
	public boolean validarMismaPosicion(Posicion ini, Posicion fin){
		boolean result = false;
		result = ini.getX() == fin.getX() ? true : false;
		if (result)
			result = ini.getY() == fin.getY() ? false : true;
		return result;
	}
	
	/*
	 * Sirve para determinar en que desplazamiento ira la TORRE.
	 * Devuelve x o y según el caso en que sea el mismo valor.
	 */
	public String ejeDesplazamientoTorre(Posicion ini, Posicion fin){
		if(ini.getX() == fin.getX()) {
			System.err.println(Thread.currentThread().getStackTrace()[2] + " hacia la x");
			return "x";
		} else {
			System.err.println(Thread.currentThread().getStackTrace()[2] + " hacia la y");
			return "y";
		}
	}
	
	public boolean validarMovimientoTorre(Posicion ini, Posicion fin, Pieza p){
		boolean result = true;
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		String desplazamientoA = ejeDesplazamientoTorre(ini, fin);
		System.err.println(Thread.currentThread().getStackTrace()[2] + " El desplazamiento debe ser hacia " + desplazamientoA);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			if(!validarMismaPosicion(ini, posicionActual)) {
				System.err.println(Thread.currentThread().getStackTrace()[2]);
				if (desplazamientoA.equals(ejeDesplazamientoTorre(ini, posicionActual))) {
					System.err.println(Thread.currentThread().getStackTrace()[2]);
					if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
						System.err.println(Thread.currentThread().getStackTrace()[2]);
						if ( null != getPieza(posicionActual.getX(), posicionActual.getY())){
							System.err.println(Thread.currentThread().getStackTrace()[2] + " La Torre no puede pasar sobre una pieza." + "x:"+posicionActual.getX()+",y:"+posicionActual.getY());
							result = false;
							break;
						} else {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " Sigo buscando movimientos validos con la Torre.");
							result = true;
						}
					}
				}
			}
		}
		if(result){
			result = validarCasillaOcupadaMismoColor(ini, fin);
		}
		return result;
	}

	/*
	 * El primer simbolo pertenece al eje X y el segundo al eje Y
	 * Hace un calculo y devuelve:
	 * ++: cuando va de la pos ini hacia el cuadrante 1 de un plano cartesiano
	 * +-: cuando va de la pos ini hacia el cuadrante 2 de un plano cartesiano
	 * --: cuando va de la pos ini hacia el cuadrante 3 de un plano cartesiano
	 * -+: cuando va de la pos ini hacia el cuadrante 4 de un plano cartesiano
	 */
	public String ejeDesplazamientoDama(Posicion ini, Posicion fin) {
		String result = "";
		int xOrig = ini.getX();
		int yOrig = ini.getY();
		int xDest = fin.getX();
		int yDest = fin.getY();
		result  = xOrig < xDest ? "-" : "+";
		result += yOrig < yDest ? "+" : "-";
//		result += "" + Math.abs(xOrig-xDest);
//		result += "" + Math.abs(yOrig-yDest);
		return result;
	}
	
	public boolean validarMovimientoDama(Posicion ini, Posicion fin, Pieza p){
		boolean result = true;
		String direccionMovimiento = ejeDesplazamientoDama(ini, fin);
		System.err.println(Thread.currentThread().getStackTrace()[2] + "direccionMovimiento: " + direccionMovimiento);
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		result = movimientoValidoPieza(fin, listaDeMovimientosPieza);
		String colorPiezaActual = getPieza(p).toString().equals("BLACK")?"N":"B";
		System.err.println("validarMovimientoDama / Color de la pieza actual: " + colorPiezaActual);
		if (result) {
			for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
				Posicion posicionActual = (Posicion) iterator.next();
				String direccionMovimientoActual = ejeDesplazamientoDama(ini, posicionActual);
				System.err.println(Thread.currentThread().getStackTrace()[2] + "direccionMovimientoActual: " + direccionMovimientoActual + " posActual->" + "x:"+posicionActual.getX()+",y:"+posicionActual.getY());
				if (direccionMovimiento.equals(direccionMovimientoActual)) {
					if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
						System.err.println(Thread.currentThread().getStackTrace()[2]);
						if ( null != getPieza(posicionActual.getX(), posicionActual.getY())){
							String colorPiezaTemp = t[posicionActual.getX()][posicionActual.getY()].toString().contains("N")?"N":"B"; //TODO: Corregir este codigo donde lo encuentre
							System.err.println("validarMovimientoDama / Color de la pieza temporal: " + colorPiezaTemp);
							if (colorPiezaActual.equals(colorPiezaTemp)) {
								System.err.println(Thread.currentThread().getStackTrace()[2] + " La Dama no puede pasar sobre una pieza." + "x:"+posicionActual.getX()+",y:"+posicionActual.getY());
								result = false;
								break;
							} else {
								System.err.println(Thread.currentThread().getStackTrace()[2] + " La Dama captura una pieza.");
								result = true;
								break;
							}
						} else {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " Sigo buscando movimientos validos con la Dama.");
							if (fin.getX() == posicionActual.getX() && fin.getY() == posicionActual.getY())
								return true;
							else
								result = true;
						}
					}
				} 
			}
		}
		if(result){
			result = validarCasillaOcupadaMismoColor(ini, fin);
		}
		return result;
	}

	public boolean validarMovimientoRey(Posicion ini, Posicion fin, Pieza p){
		boolean result = false;
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
				if(null != getPieza(fin.getX(), fin.getY()) ){
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento no válido con el Rey. No puede saltar sobre una pieza del mismo color.");
					result = false;
					break;
				} else {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento con el Rey.");
					result = true;
				}
			}
		}
		result =  validarCasillaOcupadaMismoColor(ini, fin);
		if (result && (Math.abs((ini.getY() - fin.getY())) == 2)){
			System.err.println(Thread.currentThread().getStackTrace()[2] + " El rey saltó dos casillas. Validar enroque");
			if (p.isPosicionInicial()) {
				System.err.println(Thread.currentThread().getStackTrace()[2] + " El rey está en su posición inicial y puede hacer enroque.");
				result = true;
			} else {
				System.err.println(Thread.currentThread().getStackTrace()[2] + " **********Ya había movido el rey y no puede hacer enroque.");
				result = false;
			}
		}
		if(result){
			result = validarCasillaOcupadaMismoColor(ini, fin);
		}
		if((Math.abs((ini.getX() - fin.getX())) == 2))
			result = false;
		
		result = validarCercaOtroRey(fin, p);
		System.err.println("Resultado del movimiento de Rey, desde la validacion de movimientos del rey: " + result);
		return result;
	}

	public boolean validarCercaOtroRey(Posicion fin, Pieza p){ 
		Posicion reyQuieto = encontrarRey(p.getColor());
		System.err.println("pos rey Mueve: (" + fin.getX() + "," + fin.getY() +")");
		System.err.println("pos rey No Mueve: (" + reyQuieto.getX() + "," + reyQuieto.getY() +")");
		if ( Math.abs(fin.getX() - reyQuieto.getX()) > 1 || Math.abs(fin.getY() - reyQuieto.getY()) > 1) {
			System.err.println("Movimiento de rey es valido, no hay otro rey cerca.");
			return true;
		} 
		System.err.println("Jugada de rey no permitida, hay otro rey cerca.");
		return false;
	}
	
	public boolean moverPieza(String movimiento){
		Pieza pieza1 = null;
		Pieza pieza2 = null;
		Coordenada co1 = new Coordenada();
		Coordenada co2 = new Coordenada();
		boolean permite = false;
		boolean promocionPeon = false;
//		Posicion tmpPos = null;
		if (!"".equals(movimiento)) {
			System.err.println(Thread.currentThread().getStackTrace()[2] + movimiento.substring(0, 2));
			co1 = devolverCoordenada(movimiento.substring(0, 2));
			System.err.println(Thread.currentThread().getStackTrace()[2] + movimiento.substring(2, 4));
			if (null != getPieza(co1.getX(), co1.getY()))
				pieza1 = getPieza(co1.getX(), co1.getY());
			else
				return false;
			co2 = devolverCoordenada(movimiento.substring(2, 4));
			pieza2 = getPieza(co2.getX(), co2.getY());
//			tmpPos = new Posicion(co2.getX(), co2.getY()); //Posicion para comparar
			if (null != pieza1 || null != t1[co1.getX()][co1.getY()] && pieza1.getColor() == turnoColor ) {
				if(Pattern.matches("([a-h][1-8]){2}", movimiento)) {
					if (pieza1.getNombre().equals("P")) {
						permite = validarMovimientoPeon(new Posicion(co1.getX(), co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Eligio P " + " / permite: " + permite);
						if (permite) {
//						System.err.println(Thread.currentThread().getStackTrace()[2]+ " piezasEnPassant.size(): " + piezasEnPassant.size());
//						if (null != piezasEnPassant && piezasEnPassant.size() > 0) {
//							if (pieza1.baja) {
//								System.err.println(Thread.currentThread().getStackTrace()[2]+ " borrar casilla izq 1 if");
//								borrarCasilla(co2.getX() - 1, co2.getY());
//							} else {
//								System.err.println(Thread.currentThread().getStackTrace()[2]+ " borrar casilla der 2 else");
//								borrarCasilla(co2.getX() + 1, co2.getY());
//							}
//							piezasEnPassant.clear();
//						} else {
//							System.err.println(Thread.currentThread().getStackTrace()[2] + " Llama a poner en passant"); //TODO: validar esto que no esta bien.
//							ponerEnPassant(new Posicion(co2.getX(), co2.getY()));
//						}
						if (pieza1.baja && co2.getX() == ChessRules.SIZE - 1) { // Promovido
							promocionPeon = true;
						} else if (!pieza1.baja && co2.getX() == 0) { // Promovido
							promocionPeon = true;
						}
						if (promocionPeon) {
							InputStreamReader isr = new InputStreamReader(System.in);
							BufferedReader br = new BufferedReader(isr);
							System.out.println("Seleccione una de las siguientes piezas: Dama [D] Torre [T] Alfil [A] Caballo [C]");
							try {
								String piezaElegida = br.readLine();
								if (Pattern.matches("([DTAC])", piezaElegida)) {
									if (piezaElegida.equals("D")) {
										System.err.println(Thread.currentThread().getStackTrace()[2]+ " Eligio D");
										t1[co2.getX()][co2.getY()] = new Dama(nextColor(turnoColor));
										t[co2.getX()][co2.getY()] = "D" + (nextColor(turnoColor).toString().contains("N") ? "N": "B");
									}
									if (piezaElegida.equals("T")) {
										System.err.println(Thread.currentThread().getStackTrace()[2]+ " Eligio T");
										t1[co2.getX()][co2.getY()] = new Torre(nextColor(turnoColor));
										t[co2.getX()][co2.getY()] = "T" + (nextColor(turnoColor).toString().contains("N") ? "N": "B");
										setCasilla(co2.getX(), co2.getY(), t1[co2.getX()][co2.getY()]);// Gráfico
									}
									if (piezaElegida.equals("A")) {
										System.err.println(Thread.currentThread().getStackTrace()[2] + " Eligio A");
										t1[co2.getX()][co2.getY()] = new Alfil(nextColor(turnoColor));
										t[co2.getX()][co2.getY()] = "A" + (nextColor(turnoColor).toString().contains("N") ? "N": "B");
										setCasilla(co2.getX(), co2.getY(), t1[co2.getX()][co2.getY()]);// Gráfico
									}
									if (piezaElegida.equals("C")) {
										System.err.println(Thread.currentThread().getStackTrace()[2]+ " Eligio C");
										t1[co2.getX()][co2.getY()] = new Caballo(nextColor(turnoColor));
										t[co2.getX()][co2.getY()] = "C" + (nextColor(turnoColor).toString().contains("N") ? "N": "B");
										setCasilla(co2.getX(), co2.getY(),t1[co2.getX()][co2.getY()]);// Gráfico
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					} else if (pieza1.getNombre().equals("C")) {
						permite = validarMovimientoCaballo(new Posicion(co1.getX(), co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Eligio C " + " / permite: " + permite);
					} else if (pieza1.getNombre().equals("A")) {
						permite = validarMovimientoAlfil(new Posicion(co1.getX(), co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Eligio A " + " / permite: " + permite);
					} else if (pieza1.getNombre().equals("T")) {
						permite = validarMovimientoTorre(new Posicion(co1.getX(), co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Eligio T " + " / permite: " + permite);
					} else if (pieza1.getNombre().equals("D")) {
						permite = validarMovimientoDama(new Posicion(co1.getX(), co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Eligio D " + " / permite: " + permite);
					} else if (pieza1.getNombre().equals("R")) {
						permite = validarMovimientoRey(new Posicion(co1.getX(),co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Eligio R " + " / permite: " + permite);
						if (permite && Math.abs((co1.getY() - co2.getY())) == 2) {
							permite = validarEnroque(co1, co2);
						}
						if (pieza1.isPosicionInicial()) {
							if (Math.abs((co1.getY() - co2.getY())) == 2) { // Intento
								// Enroque
								switch (co1.getX()) {
								case 0:
									if (co1.getY() == 2) {
										System.err.println(Thread.currentThread().getStackTrace()[2] + " Enroque largo arriba");
										setCasilla(co2.getX(), co2.getY(), pieza1);// Gráfico 
										t1[co2.getX()][co2.getY()] = pieza1;
										t1[co1.getX()][co1.getY()] = null; // Lógico
										t1[0][5] = getPieza(0, 7); // Lógico
										setCasilla(0, 5, getPieza(0, 7));// Gráfico
										t1[0][7] = null; // Lógico
									} else {
										System.err.println(Thread.currentThread().getStackTrace()[2] + " Enroque corto arriba");
										setCasilla(co2.getX(), co2.getY(), pieza1);// Gráfico
										t1[co2.getX()][co2.getY()] = pieza1;
										t1[co1.getX()][co1.getY()] = null; // Lógico
										t1[0][5] = getPieza(0, 7); // Lógico
										setCasilla(0, 5, getPieza(0, 7));// Gráfico
										t1[0][7] = null; // Lógico
									}
									break;
								case 7:
									if (co1.getY() == 2) {
										System.err.println(Thread.currentThread().getStackTrace()[2] + " Enroque largo abajo");
										setCasilla(co2.getX(), co2.getY(), pieza1);// Gráfico
										t1[co2.getX()][co2.getY()] = pieza1;
										t1[co1.getX()][co1.getY()] = null; // Lógico
										t1[7][5] = getPieza(7, 7); // Lógico
										setCasilla(7, 5, getPieza(7, 7));// Gráfico
										t1[7][7] = null; // Lógico
									} else {
										System.err.println(Thread.currentThread().getStackTrace()[2] + "Enroque corto abajo");
										setCasilla(co2.getX(), co2.getY(), pieza1);// Gráfico
										t1[co2.getX()][co2.getY()] = pieza1;
										t1[co1.getX()][co1.getY()] = null; // Lógico
										t1[7][5] = getPieza(7, 7); // Lógico
										setCasilla(7, 5, getPieza(7, 7));// Gráfico
										t1[7][7] = null; // Lógico
									}
									break;

								default:
									break;
								}
							} else {
//								permite = false;
//								System.err.println(Thread.currentThread().getStackTrace()[2] + " El rey no puede saltar dos casillas.");
//								setCasilla(co2.getX(), co2.getY(), pieza1);// Gráfico
//								borrarCasilla(co1.getX(), co1.getY());// Logico
//								t1[co2.getX()][co2.getY()] = pieza1;
//								t1[co1.getX()][co1.getY()] = null;
							}
						}
						if(permite){
							pieza1.posicionInicial = false;//Rey ya no está en posición inicial
						}
					}
				} else {
					System.err.println("Jugada invalida desde la entrada : " + movimiento);
					return false;
				}
			} else {
				System.err.println("Pieza null o turno erroneo.");
				return false;
			}
			/******************** Validar Jaque ********************/
			if (permite) {
				System.err.println(Thread.currentThread().getStackTrace()[2] + " Entro a validar jaque.");
				if (evaluarJaqueV2(encontrarRey(Pieza.Color.BLACK), encontrarRey(Pieza.Color.WHITE))) {//TODO: voltear esto luego
					System.err.println(Thread.currentThread().getStackTrace()[2] + " El sistema detectó JAQUE en su rey por favor muévalo o cubra al Rey.");
					enJaque = true;
					permite = false;
				} else {
					enJaque = false;
				}
			}
			System.err.println(Thread.currentThread().getStackTrace()[2] + " En jaque? " + enJaque);
			/******************** Fin Validar Jaque ********************/
			if (permite && !enJaque){
				System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de pieza deliberado? (por defecto.) de (" + co1.getX() +","+ co1.getY()+") a (" + co2.getX() +","+ co2.getY()+")");
				t1[co2.getX()][co2.getY()] = pieza1; //Logico
				t1[co1.getX()][co1.getY()] = null; // Lógico
				setCasilla(co2.getX(), co2.getY(), pieza1);// Gráfico
//				t[co2.getX()][co2.getY()] = pieza1.toString();
				t[co1.getX()][co1.getY()] = "";// Gráfico
				//llenar las casillas atacadas justo en este instante
				llenarCasillasAtacadas();
				if (evaluarJaqueV2(encontrarRey(Pieza.Color.BLACK), encontrarRey(Pieza.Color.WHITE))) {//TODO: voltear esto luego
					System.err.println(Thread.currentThread().getStackTrace()[2] + " El sistema detectó JAQUE en su rey por favor muévalo o cubra al Rey.");
					enJaque = true;
				} else {
					enJaque = false;
				}
			} else {
				//Devolver jugada
				setCasilla(co1.getX(), co1.getY(), pieza1);// Gráfico
				t[co2.getX()][co2.getY()] = "";// Gráfico
				t1[co1.getX()][co1.getY()] = pieza1; //Logico
				t1[co2.getX()][co2.getY()] = null; // Lógico
			}
			if(permite && enJaque){
				System.err.println("permite y enJaque!");
				permite = true;
			} else {
				System.err.println("no permite y no esta enJaque!");
				//turnoColor = nextColor(turnoColor);
				//permite = true;
				//permitir movimiento
				if(enJaque) {
					Posicion posMov = new Posicion(co2.getX(), co2.getY()); 
					String tempJugada = jugadas.get(jugadas.size()-1).replace("+", "");
					System.err.println("Jugadas: " + tempJugada);
					Coordenada coord = devolverCoordenada(tempJugada.substring(2, 4));
					Posicion posPiezaJaq = new Posicion(coord.getX(), coord.getY());
					if(t1[coord.getX()][coord.getY()].getMovimientos(posPiezaJaq, t1).contains(posMov)/*Agregar: y el movimiento esta en la misma direccion*/) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de pieza (cubre jaque.) (" + co1.getX() +","+ co1.getY()+") a (" + co2.getX() +","+ co2.getY()+")");
						t1[co2.getX()][co2.getY()] = pieza1; //Logico
						t1[co1.getX()][co1.getY()] = null; // Lógico
						setCasilla(co2.getX(), co2.getY(), pieza1);// Gráfico
//				t[co2.getX()][co2.getY()] = pieza1.toString();
						t[co1.getX()][co1.getY()] = "";// Gráfico
						System.err.println(Thread.currentThread().getStackTrace()[2] + " turnoColor: " + turnoColor);
						if ( turnoColor.equals(Pieza.Color.BLACK)) {//Negro
							System.err.println(Thread.currentThread().getStackTrace()[2] + " entro al if");
							if (listaCasillasAtacadasXBlanco.contains(encontrarRey(Pieza.Color.WHITE))) {
								enJaque = true;
							} else {
								enJaque = false;
							}
						} else {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " entro al else");
							if (listaCasillasAtacadasXNegro.contains(encontrarRey(Pieza.Color.BLACK))) {
								enJaque = true;
							} else {
								enJaque = false;
							}
						}
						if(enJaque){
							System.err.println("Devolver jugada ya que no cubre el jaque.");
							//Devolver jugada
							setCasilla(co1.getX(), co1.getY(), pieza1);// Gráfico
							t[co2.getX()][co2.getY()] = "";// Gráfico
							t1[co1.getX()][co1.getY()] = pieza1; //Logico
							t1[co2.getX()][co2.getY()] = null; // Lógico
							System.err.println(Thread.currentThread().getStackTrace()[2] + " 1En jaque? " + enJaque + " 1permite: " + permite);
							permite = false;
						}
					} else {
						System.err.println("Devolver jugada ya que no cubre el jaque.");
						//Devolver jugada
						setCasilla(co1.getX(), co1.getY(), pieza1);// Gráfico
						t[co2.getX()][co2.getY()] = "";// Gráfico
						t1[co1.getX()][co1.getY()] = pieza1; //Logico
						t1[co2.getX()][co2.getY()] = null; // Lógico
						System.err.println(Thread.currentThread().getStackTrace()[2] + " 2En jaque? " + enJaque + " 2permite: " + permite);
						permite = false;
					}
				}
			}
		}
		//Agregar movimiento a la lista de jugadas
		if (permite) {
			if (turnoColor.equals("WHITE"))
				jugadas.add( " " + movimiento + (enJaque==true?"+":enMate==true?"+":""));
			else
				jugadas.add(movimiento + (enJaque==true?"+":enMate==true?"+":""));
		}
		System.err.println("Lista de jugadas: " + Arrays.asList(jugadas));
		//TODO: falta poner lo de devolver una captura y para eso es pieza2
		return permite;
	}
	
	private boolean validarEnroque(Coordenada co1, Coordenada co2){
		boolean result = false;
		if (co2.getY() > co1.getY()) { //Corto
			if (null == getPieza(co1.getX(), co1.getY()+1)) {
				if (null == getPieza(co1.getX(), co1.getY()+2)) {
					result = true;
				} else {
					result = false;
				}
			} else {
				result = false;
			}
		} else { //Largo
			if (null == getPieza(co1.getX(), co1.getY()-1)) {
				if (null == getPieza(co1.getX(), co1.getY()-2)) {
					if (null == getPieza(co1.getX(), co1.getY()-3)) {
						result = true;
					} else {
						result = false;
					}
				} else {
					result = false;
				}
			} else {
				result = false;
			}
		}
		System.err.println(Thread.currentThread().getStackTrace()[2] + " validarEnroque: " + result);
		return result;
	}
	/*
	 * Lista de posiciones
	 * Pieza que hace el jaque
	 * 
	 */
	private boolean evaluarJaque(List<Posicion> posiciones, Pieza p) {
		boolean result = false;
		Posicion posRey = encontrarRey(p.getColor());//buscar rey contrario
		System.err.println(Thread.currentThread().getStackTrace()[2] + " evaluarJaque / Rey buscado de color: " + nextColor(p.getColor()) + " / Rey en: (" + posRey.getX() + ", " + posRey.getY() + ")");
//		System.err.println(Thread.currentThread().getStackTrace()[2] + "940:Posicion del rey contrario-> x:" + posRey.getX() + " / pos y: " + posRey.getY());
		for (Iterator iterator = posiciones.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			System.err.println(Thread.currentThread().getStackTrace()[2] + "Posicion que esta siendo evaluada: (" + posicionActual.getX() + ", " + posicionActual.getY() + ")");
			if(posRey.compareTo(posicionActual) == 0){
//				llenarPiezasJaqueadoras(p);
				System.err.println(Thread.currentThread().getStackTrace()[2] + " El rey está en peligro!");
				result = true;
				getPieza(posRey.getX(), posRey.getY()).enJaque = true;
				break;
			} else {
				result = false;
			}
		}
		System.err.println(Thread.currentThread().getStackTrace()[2] + " evaluarJaque: " + result);
		return result;
	}
	/*
	 * Lista de posiciones 
	 * Pieza que hace jaque
	 */
	
	private boolean evaluarMate(List<Posicion> posiciones, Pieza p) {
		boolean result = false;
		Pieza tmpPieza = null;
		String tmpArriba = p.getColor().toString().equals("WHITE")?"B":"N";
		Posicion posRey = new Posicion(0, 0);
//		posRey = encontrarRey(p.getColor());
		posRey = encontrarPieza(p);
		List<Posicion> movimientoReyActual = new ArrayList<Posicion>(); //p.getMovimientos(posRey);
		List<Posicion> movimientosRey = new ArrayList<Posicion>();
		movimientosRey.addAll(p.getMovimientos(posRey,t1));
		for (Iterator iterator = movimientosRey.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			System.err.println(Thread.currentThread().getStackTrace()[2] + " Pos actual Rey: " + posicionActual.toString());
			movimientoReyActual.add(posicionActual);
			//if(evaluarJaque(movimientoReyActual, p)){
			if(evaluarJaqueV2(encontrarRey(Pieza.Color.WHITE), encontrarRey(Pieza.Color.BLACK))) {
				//movimientosRey.remove();
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}

	private Posicion encontrarRey(Pieza.Color color) {
		Posicion resultPos = null; 
		Rey rey = new Rey(nextColor(color),false,true);
		System.err.println(Thread.currentThread().getStackTrace()[2] + " El rey: " + rey.toString() + " es el buscado...");
		for (int i = 0; i < ChessRules.SIZE; i++) {
			if(null == resultPos) {
				for (int j = 0; j < ChessRules.SIZE; j++) {
					if(null != getPieza(i, j)){
						if( getPieza(i, j).toString().equals(rey.toString())){
							resultPos = new Posicion(i, j);
							System.err.println(Thread.currentThread().getStackTrace()[2] + " La pieza : " + getPieza(i, j) + " se encuentra en (i,j): (" + i + "," + j + ")");
							break;
						}
					}
				}
			} else {
				break;
			}
		}
		return resultPos;
	}

	private Posicion encontrarPieza(Pieza p) {
		Posicion resultPos = null; 
		for (int i = 0; i < ChessRules.SIZE; i++) {
			if(null == resultPos) {
				for (int j = 0; j < ChessRules.SIZE; j++) {
					if(null != getPieza(i, j)){
						//System.err.println(Thread.currentThread().getStackTrace()[2] + "531:La pieza en la posicion actual es: " + getPieza(i, j) + " La pieza buscada es: " + (p.getNombre()+ (p.getColor().equals("WHITE")?"B":"N")));
						if( getPieza(i, j).toString().equals((p.getNombre()+ (p.getColor().equals("WHITE")?"B":"N")))){
							resultPos = new Posicion(i, j);
//							System.err.println(Thread.currentThread().getStackTrace()[2] + "968:La pieza : " + getPieza(i, j) + " se encuentra en (i,j): (" + i + "," + j + ")");
							break;
						}
					}
				}
			} else {
				break;
			}
		}
		return resultPos;
	}
	
	public Coordenada devolverCoordenada(String casilla){
		Coordenada c = new Coordenada();
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if(tEquivalenteNatural[i][j].equals(casilla)){
					c.setX(i);
					c.setY(j);
					break;
				}
			}
		}
		return c;
	}
	public class Coordenada{
		int x, y;
		public void setX(int x){this.x=x;}
		public void setY(int y){this.y=y;}
		public int getX(){return this.x;}
		public int getY(){return this.y;}
	}

	public String getNombre(Pieza pieza) {
		if (pieza == null) {
			return "";
		}
		return pieza.toString();
	}
	
	public void ponerEnPassant(Posicion posPeon){
		String turnoContrario = nextColor(turnoColor).toString().contains("N")?"N":"B";
		int fila = getPieza(posPeon.getX(), posPeon.getY()).baja ? 3 : 4;
		System.err.println(Thread.currentThread().getStackTrace()[2] + "***** Entrando a poner enPassant. Fila: " + fila + " / turno: " + turnoContrario + " / pos peon: (" + posPeon.getX() + ", " + posPeon.getY() + ")");
		if(null != getPieza(posPeon.getX(), posPeon.getY()) && getPieza(posPeon.getX(), posPeon.getY()).saltoDos ) {
			System.err.println(Thread.currentThread().getStackTrace()[2] + "***** ponerEnPassant / peon SaltoDos: "); //+ getPieza(posPeon.getX(), posPeon.getY()).saltoDos);
			if(null != getPieza(fila, posPeon.getY() + 1) && getPieza(fila, posPeon.getY() + 1).toString().contains("P") /*&& getPieza(fila, posPeon.getY() + 1 ).toString().contains(turnoContrario)*/){
				if(null != getPieza(posPeon.getX(), posPeon.getY() + 1 )){ System.err.println(Thread.currentThread().getStackTrace()[2] + "586:Poniendo el peon ("+ posPeon.getX()  + "," + (posPeon.getY() + 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() + 1] + ")"); System.err.println(Thread.currentThread().getStackTrace()[2] + "P: " + getPieza(fila, posPeon.getY() + 1).toString());}
				System.err.println(Thread.currentThread().getStackTrace()[2] + "**** Poniendo el peon ("+ posPeon.getX()  + "," + (posPeon.getY() + 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() + 1] + ")");
//				getPieza(fila, (posPeon.getY() + 1) ).enPassant = true;
				piezasEnPassant.add(getPieza(fila, posPeon.getY() + 1 ));
				System.err.println(Thread.currentThread().getStackTrace()[2] + "**** piezasEnPassant: " + piezasEnPassant.get(0) + " / size: " + piezasEnPassant.size());
			} else if (null != getPieza(fila, posPeon.getY() - 1 ) && getPieza(fila, posPeon.getY() - 1 ).toString().contains("P") /*&& getPieza(fila, posPeon.getY() - 1 ).toString().contains(turnoContrario)*/) {
//				getPieza(fila, (posPeon.getY() - 1) ).enPassant = true;
				if(null != getPieza(posPeon.getX(), posPeon.getY() - 1 )){ System.err.println(Thread.currentThread().getStackTrace()[2] + "587:Poniendo el peon ("+ posPeon.getX()  + "," + (posPeon.getY() - 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() - 1] + ")"); System.err.println(Thread.currentThread().getStackTrace()[2] + "P: " + getPieza(fila, posPeon.getY() - 1).toString());}
				System.err.println(Thread.currentThread().getStackTrace()[2] + "**** Poniendo el peon ("+ fila  + "," + (posPeon.getY() - 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() - 1] + ")" + " / pieza: " + getPieza(fila, posPeon.getY() - 1 ));
				piezasEnPassant.add(getPieza(fila, posPeon.getY() - 1 ));
				System.err.println(Thread.currentThread().getStackTrace()[2] + "*** piezasEnPassant: " + piezasEnPassant.get(0) + " / size: " + piezasEnPassant.size());
			}
			getPieza(posPeon.getX(), posPeon.getY()).saltoDos = false;
		}
		if (piezasEnPassant.size() > 0)
			System.err.println(Thread.currentThread().getStackTrace()[2] + "******** En la lista de passant hay: " + piezasEnPassant.size() + " / pos 1: " + piezasEnPassant.get(0));
		else 
			System.err.println(Thread.currentThread().getStackTrace()[2] + "******** En la lista de passant hay: 0");
	}
	
	@Override
	public String toString() {
		System.out.println("* EnHeroChess *");
		System.out.println("\r\n");
		StringBuilder str = new StringBuilder();
		for (int i = 0, k = 8; i < 8; i++, k--) {
			str.append(k + " |");
			for (int j = 0; j < 8; j++) {
				Pieza pieza = t1[i][j];
				if (pieza == null) {
					str.append((i + j) % 2 == 0 ? "  " : "==");
				} else {
					str.append(pieza.toString());
				}
				str.append("|");
			}
			str.append("\r\n");
		}
		str.append("   a  b  c  d  e  f  g  h");
		str.append("\r\n");
		return str.toString();
	}
	
	/**************************************************************
	 * check whether player's king is check mate
	 **************************************************************/

	public boolean esJaqueMate( List<Posicion> posiciones, Pieza p, Coordenada co ) {
		boolean result = false;
		return result;
	}
	/*
	 * 
	 * Recibe la pieza Rey como parametro.
	 * Encontrar todas las piezas que atacan al Rey.
	 * 
	 */
	
//	public void llenarPiezasJaqueadoras(Pieza p){
//		if(!piezasJaqueadoras.contains(p))
//			piezasJaqueadoras.add(p);
//		System.err.println(Thread.currentThread().getStackTrace()[2] + " llenarPiezasJaqueadoras / Piezas Jaquedoras tamaño: " + piezasJaqueadoras.size());
//	}

	public String colorALetraInicial(Pieza.Color color) {
		return color.toString().equals("BLACK")?"N":"B";
	}

	//p1: Posicion del rey
	//p2: Posicion de la pieza que da jaque
	/*
	 * Devuelve la dirección en forma de direccion hacia la que va la pieza o el ataque
	 * 1:
	 * 2:
	 * 3:
	 * 4:
	 * 5:
	 * 6:
	 */
	public String mostrarCaminoABloquear(Posicion destino, Posicion origen) {
		String result = "";
		int xDest = destino.getX();
		int yDest = destino.getY();
		int xOrig = origen.getX();
		int yOrig = origen.getY();
		
		result = (xDest - xOrig > 0)?"1":(xDest - xOrig == 0)?"2":(xDest - xOrig < 0)?"3":"";
		result += (yDest - yOrig > 0)?"4":(yDest - yOrig == 0)?"5":(yDest - yOrig < 0)?"6":"";
		
		return result;
	}
	/*
	 * retorna: 1 si la pieza que esta mas arriba es la 1
	 * retorna: 2 si la pieza que esta mas arriba es la 2
	 */
	public String piezaUbicacionRespectoAOtra(Posicion destino, Posicion origen) {
		return (destino.getX() - origen.getX()) < 0 ? "1":"2";
	}

	private void addNoDuplicates(List<Posicion> target, List<Posicion> source) {
		for (Posicion posicion : source) {
			if(posicion.x < ChessRules.SIZE && posicion.y < ChessRules.SIZE) {
				if (!target.contains(posicion)) {
					System.err.println("Agregando la posicion : (" + posicion.getX() +","+posicion.getY()+")");
					target.add(posicion);
				}
			}
		}
	}
	
	public void llenarCasillasAtacadas(){
		for (int i = 0; i < ChessRules.SIZE; i++) {
			for (int j = 0; j < ChessRules.SIZE; j++) {
				Posicion posActual = new Posicion(i, j);
				if (null != t1[i][j]) {
//					System.err.println(Thread.currentThread().getStackTrace()[2] + " Tablero-> posActual: (" + posActual.getX() + ", " + posActual.getY() + ")");
					if (null != getPieza(i, j) && t1[i][j].getColor().toString().equals("WHITE") ){
//						System.err.println("Agregando posicion de dominio blanco...");
						addNoDuplicates(listaCasillasAtacadasXBlanco, t1[i][j].getMovimientos(posActual,t1));
//						listaCasillasAtacadasXBlanco.addAll(t1[i][j].getMovimientos(posActual,t1));
					} 
					if (null != getPieza(i, j) && t1[i][j].getColor().toString().equals("BLACK") ){
//						System.err.println("Agregando posicion de dominio negro...");
						addNoDuplicates(listaCasillasAtacadasXNegro, t1[i][j].getMovimientos(posActual,t1));
//						listaCasillasAtacadasXNegro.addAll(t1[i][j].getMovimientos(posActual,t1));
					}
				}
			}
		}
	}

	/*
	 * Valida si el movimiento se encuentra dentro de la lista de movimeintos de la pieza.
	 */
	public boolean movimientoValidoPieza(Posicion pos, List<Posicion> movimientos) {
		boolean result = false;
		for (Iterator iterator = movimientos.iterator(); iterator.hasNext();) {
			Posicion posicion = (Posicion) iterator.next();
			System.err.println(Thread.currentThread().getStackTrace()[2] + " pos: (" + pos.getX() + ", " + pos.getY() + ") y la pos actual es: " + posicion.getX() + ", " + posicion.getY() + ")");
			if (pos.getX() == posicion.getX() && pos.getY() == posicion.getY()){
				result = true;
				return result;
			}
		}
		return result;
	}

	public boolean evaluarJaqueV2(Posicion reyBlanco, Posicion reyNegro){
//		llenarCasillasAtacadas();
		boolean result = false;
		System.err.println("Rey Blanco esta en: (" + reyBlanco.getX()+","+reyBlanco.getY()+")");
		System.err.println("Rey Negro esta en : (" + reyNegro.getX()+ ","+reyNegro.getY()+")");
		for (Iterator iterator = listaCasillasAtacadasXBlanco.iterator(); iterator.hasNext();) {
			Posicion posActual = (Posicion) iterator.next();
//			System.err.println(Thread.currentThread().getStackTrace()[2] + " Blanco-> posActual: (" + posActual.getX() + ", " + posActual.getY() + ") posicion Rey Negro : (" + reyNegro.getX()+ ","+reyNegro.getY()+")");
			if (posActual.getX() == reyNegro.getX() && posActual.getY() == reyNegro.getY()){
				result = true;
				return result;
			}
		}
		for (Iterator iterator = listaCasillasAtacadasXNegro.iterator(); iterator.hasNext();) {
			Posicion posActual = (Posicion) iterator.next();
//			System.err.println(Thread.currentThread().getStackTrace()[2] + " Negro-> posActual: (" + posActual.getX() + ", " + posActual.getY() + ") Posicion Rey Blanco : (" + reyBlanco.getX()+","+reyBlanco.getY()+")");
			if (posActual.getX() == reyBlanco.getX() && posActual.getY() == reyBlanco.getY()){
				result = true;
				return result;
			}
		} 
		return result;
	}
	
	public boolean evaluarMateV2(Posicion reyBlanco, Posicion reyNegro){
		boolean result = false;
		boolean isReySolo = false;
		boolean isReyBlanco = false;
		boolean isReyNegro = false;
		Posicion posRey = null;
		boolean isPiezaCubre = false;
		List<Posicion> movimientosRey = new ArrayList<Posicion>();
		List<Posicion> movimientosReyAux = new ArrayList<Posicion>();
		//Averiguar cual de los dos rey está en jaque
		for (Iterator iterator = listaCasillasAtacadasXBlanco.iterator(); iterator.hasNext();) {
			Posicion posActual = (Posicion) iterator.next();
//			System.err.println(Thread.currentThread().getStackTrace()[2] + " Blanco-> posActual: (" + posActual.getX() + ", " + posActual.getY() + ") posicion Rey Negro : (" + reyNegro.getX()+ ","+reyNegro.getY()+")");
			if (posActual.getX() == reyNegro.getX() && posActual.getY() == reyNegro.getY()){
				isReyNegro = true;
			}
		}
		if (!isReyNegro) {
			for (Iterator iterator = listaCasillasAtacadasXNegro.iterator(); iterator.hasNext();) {
				Posicion posActual = (Posicion) iterator.next();
//			System.err.println(Thread.currentThread().getStackTrace()[2] + " Negro-> posActual: (" + posActual.getX() + ", " + posActual.getY() + ") Posicion Rey Blanco : (" + reyBlanco.getX()+","+reyBlanco.getY()+")");
				if (posActual.getX() == reyBlanco.getX() && posActual.getY() == reyBlanco.getY()){
					isReyBlanco = true;
				}
			} 
		}
		// Obtener con la Posicion del Rey que está en jaque y sus movimientos
		List<Posicion> listaCasillasAtacadas = new ArrayList<Posicion>();
		if(isReyNegro){
			movimientosRey.addAll(t1[reyNegro.getX()][reyNegro.getY()].getMovimientos(reyNegro,t1));
			posRey = reyNegro;
			listaCasillasAtacadas = listaCasillasAtacadasXBlanco;
		} else {
			movimientosRey.addAll(t1[reyBlanco.getX()][reyBlanco.getY()].getMovimientos(reyBlanco,t1));
			posRey = reyBlanco;
			listaCasillasAtacadas = listaCasillasAtacadasXNegro;
		}
		//Validar los movimientos del rey y saber si el solo puede salir del jaque
		int[] posicionesABorrar = new int[movimientosRey.size()];
		int k = 0;
		if (movimientosRey.size() > 0) {
			movimientosReyAux = movimientosRey;
			for (Iterator iterator = movimientosReyAux.iterator(); iterator.hasNext();) {
				Posicion posicion = (Posicion) iterator.next();
				System.err.println(Thread.currentThread().getStackTrace()[2]+ " Pos x: " + posicion.getX() + " / pos Y: "+ posicion.getY());
				if (listaCasillasAtacadas.contains(posicion)) {
					int ubicacion = movimientosReyAux.indexOf(posicion);
					if (ubicacion != -1) {
						posicionesABorrar[k] = ubicacion;
						System.err.println(Thread.currentThread().getStackTrace()[2]+ " Posicion a borrar en: "+ ubicacion+ " / movRey: "+ movimientosRey.get(ubicacion));
						k++;
					}
				}
			}
			for (int i = 0; i < k; i++) {
				System.err.println(Thread.currentThread().getStackTrace()[2]+ " Posiciones a borrar: " + posicionesABorrar[i]);
			}
			System.err.println(Thread.currentThread().getStackTrace()[2]+ " Movimientos rey size (antes): "+ movimientosRey.size());
			for (int i = k - 1; i >= 0; i--) {
				movimientosRey.remove(posicionesABorrar[i]);
			}
			System.err.println(Thread.currentThread().getStackTrace()[2]+ " Movimientos rey size (despues): "+ movimientosRey.size());
			for (Iterator iterator = movimientosRey.iterator(); iterator.hasNext();) {
				Posicion posIcion = (Posicion) iterator.next();
				System.err.println(Thread.currentThread().getStackTrace()[2]+ " Posiciones restantes para el rey: "+ posIcion.toString());
			}
			if (movimientosRey.size() > 0) {
				// El rey puede salir solo del jaque
				isReySolo = true;
			} else {
				isReySolo = false;
			}
		}
		if (isReySolo)
			return false;
		//Validar que hay mate
		//Encontrar las piezas que hacen jaque
		if(isReyNegro){
			for (int i = 0; i < ChessRules.SIZE; i++) {
				for (int j = 0; j < ChessRules.SIZE; j++) {
					Posicion posActual = new Posicion(i, j);
					if (null != t1[i][j]) {
						if (null != getPieza(i,j) && t1[i][j].getColor().toString().equals("WHITE") ){
							List<Posicion> casillasAtacadasTemp = new ArrayList<Posicion>();
							casillasAtacadasTemp.addAll(t1[i][j].getMovimientosConDireccion(posActual, reyNegro, t1));
							if (casillasAtacadasTemp.size() > 0 ) { //La pieza da jaque al rey
								piezasJaqueadoras.add(t1[i][j]);
							}
						}
					}
				}
			}
		} else {
			for (int i = 0; i < ChessRules.SIZE; i++) {
				for (int j = 0; j < ChessRules.SIZE; j++) {
					Posicion posActual = new Posicion(i, j);
					if (null != t1[i][j]) {
						if (null != getPieza(i,j) && t1[i][j].getColor().toString().equals("BLACK") ){
							List<Posicion> casillasAtacadasTemp = new ArrayList<Posicion>();
							casillasAtacadasTemp.addAll(t1[i][j].getMovimientosConDireccion(posActual, reyBlanco, t1));
							if (casillasAtacadasTemp.size() > 0 ) { //La pieza da jaque al rey
								piezasJaqueadoras.add(t1[i][j]);
							}
						}
					}
				}
			}
		}
		// Validar piezasJaqueadoras y movimientos con direccion para saber si hay posibilidad de bloquear el jaque
		List<Posicion> posicionesAtacadasTemp = new ArrayList<Posicion>();
		for (Iterator iterator = piezasJaqueadoras.iterator(); iterator.hasNext();) {
			Pieza piezaActual = (Pieza) iterator.next();
			if (isReyNegro)
				posicionesAtacadasTemp = piezaActual.getMovimientosConDireccion(encontrarPieza(piezaActual), reyBlanco, t1);
			else
				posicionesAtacadasTemp = piezaActual.getMovimientosConDireccion(encontrarPieza(piezaActual), reyNegro, t1);
			if(null != posicionesAtacadasTemp && posicionesAtacadasTemp.size() > 0){
				for (Iterator iterator2 = posicionesAtacadasTemp.iterator(); iterator2.hasNext();) {
					Posicion posicion2 = (Posicion) iterator2.next();
						//TODO: Volver a evaluar jaque con el movimiento realizado sino, devolver que no es valido el movimiento.
				}
			}
		}
		for (Iterator iterator = posicionesAtacadasTemp.iterator(); iterator.hasNext();) {
			Posicion posicion = (Posicion) iterator.next();
			System.err.println("Posicion que esta atacada por la pieza que da jaque: " + posicion.toString());
		}
		
		if (piezasJaqueadoras.size() > 0) {
			for (Pieza pieza : piezasJaqueadoras) {
				casillasAtacadas = pieza.getMovimientosConDireccion(encontrarPieza(pieza), posRey,t1);
				Posicion posVaPieza = new Posicion(encontrarPieza(pieza).getX(), encontrarPieza(pieza).getY());
				if (null != casillasAtacadas && casillasAtacadas.contains(posVaPieza)) {
					isPiezaCubre = true;
					enJaque = false;// revisar este caso
				} else
					isPiezaCubre = false;
			}
		}
//		casillasAtacadas = p.getMovimientosConDireccion(encontrarPieza(p), posRey);
//		Posicion posVaPieza = new Posicion(co.getX(), co.getY());
//		if (null != casillasAtacadas && casillasAtacadas.contains(posVaPieza)) {
//			isPiezaCubre = true;
//			enJaque = false;// revisar este caso
//		} else
//			isPiezaCubre = false;

		if (isReySolo || isPiezaCubre)
			result = false;
		else
			result = true;
		return result;
	}
}
