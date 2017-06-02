import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

public class Tablero {
	
	//Clase singleton
	private static Tablero INSTANCE = new Tablero();
	private List<String> jugadas = new LinkedList<String>();
	private List<Pieza> piezasEnPassant = new ArrayList<Pieza>();
	
	private List<Posicion> casillasAtacadas = new ArrayList<Posicion>();
	private List<Posicion> casillasAtacadasXBlanco = new ArrayList<Posicion>();
	private List<Posicion> casillasAtacadasXNegro = new ArrayList<Posicion>();
	
	private Pieza.Color turnoColor = Pieza.Color.WHITE;
	private String quienArriba = "";
	
	private Tablero(){};
	
	private boolean enJaque = false;
	private boolean enMate = false;
	private boolean gameOver = false;
	
	private String piezas[] = {"T","C","A","D","R","P"};
	private static final Map<String, Integer> valorPiezas;
	static {
		valorPiezas = new HashMap<String, Integer>();
		valorPiezas.put("T", 5);
		valorPiezas.put("C", 3);
		valorPiezas.put("A", 4);
		valorPiezas.put("D", 10);
		valorPiezas.put("R", 100);
		valorPiezas.put("P", 1);
	}
	
	public static Tablero getInstance() {return INSTANCE;}
	
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
			System.err.println("|TB|CB|AB|DB|RB|AB|CB|TB|");
			System.err.println("|PB|PB|PB|PB|PB|PB|PB|PB|");
			System.err.println("|  |XX|  |XX|  |XX|  |XX|");
			System.err.println("|XX|  |XX|  |XX|  |XX|  |");
			System.err.println("|  |XX|  |XX|  |XX|  |XX|");
			System.err.println("|XX|  |XX|  |XX|  |XX|  |");
			System.err.println("|PN|PN|PN|PN|PN|PN|PN|PN|");
			System.err.println("|TN|CN|AN|DN|RN|AN|CN|TN|");
		} else {
			//Pintar
			System.err.println("|TN|CN|AN|DN|RN|AN|CN|TN|");
			System.err.println("|PN|PN|PN|PN|PN|PN|PN|PN|");
			System.err.println("|  |XX|  |XX|  |XX|  |XX|");
			System.err.println("|XX|  |XX|  |XX|  |XX|  |");
			System.err.println("|  |XX|  |XX|  |XX|  |XX|");
			System.err.println("|XX|  |XX|  |XX|  |XX|  |");
			System.err.println("|PB|PB|PB|PB|PB|PB|PB|PB|");
			System.err.println("|TB|CB|AB|DB|RB|AB|CB|TB|");
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

//		System.err.println("Color: " + p.getColor().toString().equals("BLACK"));
		return piecita +(p.getColor().toString().equals("BLACK")?"B":"N");
	}
	
	public String getTipoPieza(int x, int y, Pieza pieza){
		String p = getPieza(pieza);
//		System.err.println("getTipoPieza / Pieza: " + p);
		return p;
	}
	
	public Pieza getPieza(int x, int y){
		return t1[x][y];
	}
	
	public void setCasilla(int x, int y, Pieza pieza){
		t[x][y] = getTipoPieza(x, y, pieza); 
	}
	
	public void borrarCasilla(int x, int y){
		t[x][y] = ""; //o null - grafica
		t1[x][y] = null; // juego - logica
	}
	
	public String casillaOcupada(int x, int y){
		System.err.println("Evaluando x: " + x + " y: " + y);
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
//		System.err.println("El Tablero esta siendo notificado...");
//	}
	
	public static void main(String[] args) {
//		String nombre = "Hernan ";
//		String nombres[] = new String[2];
//		nombres[0] = nombre.split(" ")[0];
		//nombres[1] = nombre.split(" ")[1];
//		if(null == nombre.split(" ")[1])
//			nombres[1] = "";
//		System.err.println("Nombre 1: " + nombres[0] + " / nombre 2: " + nombres[1]);
		Tablero ta = new Tablero();
		boolean esArribaBlancas = true;
		ta.quienArriba = esArribaBlancas?"B":"N"; //variable para que funcione el metodo validarSentido 
		ta.posicionarPiezas(esArribaBlancas);//Logico
		ta.posicionarPiezasGraficas(esArribaBlancas);//Gráfico
		System.out.print(ta.toString());
		
//		int i = 3;
		while(!ta.gameOver /*|| i > 0*/){
			ta.llenarCasillasAtacadas();
//			System.err.println("Atacadas por blanco: " +  ta.casillasAtacadasXBlanco.size());
//			System.err.println("Atacadas por negro : " +  ta.casillasAtacadasXNegro.size());
//			Leer movimiento tipo e4e6 o Pe4
//			Mover m = new Mover();
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			System.out.println("Digite su jugada: " );
			try {
				String jugada = br.readLine();
//				Validar movimiento: Si el movimiento no termina en mate-> continue jugando
				if (jugada.equals("quit")) {
					ta.gameOver = true;
					System.err.println("Se terminó el juego.");
					break;
				}
				ta.moverPieza(jugada);
				System.out.print(ta.toString());
				//ta.evaluarJuego();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			gameOver = true;
		}
	}

	private void evaluarJuego() {
		int valorBlanco = 0, valorNegro = 0;
//		System.out.println("Evaluando...");
//		System.out.println("**********************************");
//		System.out.println("valorPiezas D: " + valorPiezas.get("D"));
//		System.out.println("valorPiezas T: " + valorPiezas.get("T"));
//		System.out.println("valorPiezas A: " + valorPiezas.get("A"));
//		System.out.println("valorPiezas C: " + valorPiezas.get("C"));
//		System.out.println("valorPiezas P: " + valorPiezas.get("P"));
//		System.out.println("**********************************");
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
		//System.err.println("Blanco: " + valorBlanco + " / Negro: " + valorNegro + " - Diferencia: " + (valorBlanco - valorNegro));
	}

	private Pieza.Color nextColor(Pieza.Color color) {
		int indice = color.ordinal();
//		System.err.println("Indice: " + indice + " / longitud: " + Pieza.Color.values().length);
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
			}
		}
		return result;
	}
	
	public boolean validarMovimientoPeon(Posicion ini, Posicion fin, Pieza p){
		System.err.println("265:validarMovimientoPeon / p.enPassant: " + p.enPassant);
		boolean result = false;
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
				if( ini.getY() == fin.getY() && null == getPieza(fin.getX(), fin.getY())) {
					if (p.baja) {
						if(ini.getX() < fin.getX() ){
							System.err.println("384:Movimiento de peon en avance válido. Con peon que baja.");
							result = true;
						} else {
							result = false;
						} 
					} else { // peon no baja
						if(ini.getX() > fin.getX() ){
							System.err.println("391:Movimiento de peon en avance válido. Con peon que no baja.");
							result = true;
						} else {
							System.err.println("394:Movimiento de peon no válido.");
							result = false;
						} 
					}
//					System.err.println("Movimiento de peon en avance válido.");
//					result = true;
				} else if(ini.getY() == fin.getY() && null != getPieza(fin.getX(), fin.getY())) {
					System.err.println("401:Movimiento no válido con el peon ya que no puede capturar de frente.");
					return false;
//					break;
				} else if( null != getPieza(posicionActual.getX() + 1, posicionActual.getY() + 1) || piezasEnPassant.contains(p)) {
					//Movimiento de captura con peon
					System.err.println("406:Movimiento de captura con el peon derecha.");
					result = true;
				} else if( null != getPieza(posicionActual.getX() + 1, posicionActual.getY() - 1) || piezasEnPassant.contains(p)) {
					System.err.println("409:Movimiento de captura con el peon izquierda.");
					result = true;
				}
			}
		}
		if (result && (Math.abs((ini.getX() - fin.getX())) == 2)){
			System.err.println("305:validarMovimientoPeon informa que salto dos.");
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
					System.err.println("318:Movimiento no válido con el Caballo. No puede saltar sobre una pieza del mismo color.");
					result = false;
					break;
				} else {
					System.err.println("322:Movimiento con el Caballo.");
					result = true;
				}
			}
		}	
		return result;
	}

	public boolean validarMovimientoAlfil(Posicion ini, Posicion fin, Pieza p){
		boolean result = false;
		String tmpArriba = p.getColor().toString().equals("WHITE")?"B":"N";
		String sentido = validarSentido(ini, fin, tmpArriba.equals(quienArriba));
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			String sentidoPosActual = validarSentido(ini, posicionActual, tmpArriba.equals(quienArriba));
			if (sentido.equals(sentidoPosActual) ) {
				if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
					System.err.println("435->Pos fin x: " + fin.getX() + " pos fin Y: " + fin.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual + " pieza actual: " + getPieza(posicionActual.getX(), posicionActual.getY()));
					if (null != getPieza(posicionActual.getX(), posicionActual.getY()) && posicionActual.getX() != fin.getX() && posicionActual.getY() != fin.getY()){
						System.err.println("437:El Alfil no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						if (null == getPieza(posicionActual.getX(), posicionActual.getY())) {
							if(posicionActual.getX() == fin.getX() && posicionActual.getY() == fin.getY()) {
								result = true;
								break;
							} else {
								result = false;
							}
						} else {
							System.err.println("449:Sigo buscando movimientos validos con el Alfil.");
							result = true;
						}
					}
				}
			} else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
					System.err.println("456->Pos ini x: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY()) && posicionActual.getX() != fin.getX() && posicionActual.getY() != fin.getY()){
						System.err.println("458:El Alfil no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println("462:Sigo buscando movimientos validos con el Alfil.");
						result = true;
					}
				}
			} else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
					System.err.println("468->Pos ini x: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY()) && posicionActual.getX() != fin.getX() && posicionActual.getY() != fin.getY()){
						System.err.println("470:El Alfil no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println("474:Sigo buscando movimientos validos con el Alfil.");
						result = true;
					}
				}
			} else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
					System.err.println("480->Pos ini x: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY()) && posicionActual.getX() != fin.getX() && posicionActual.getY() != fin.getY()){
						System.err.println("482:El Alfil no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println("487:Sigo buscando movimientos validos con el Alfil.");
						result = true;
					}
				}
			}
		}	
		return result;
	}

	private String validarSentido(Posicion ini, Posicion fin, boolean sentido) {
		String result = "";
		int deltaX = fin.getX() - ini.getX();
		int deltaY = fin.getY() - ini.getY();
		if(sentido) {
			if (deltaX >= 0 && deltaY >= 0) result = "1IA";
			else if (deltaX <= 0 && deltaY >= 0) result = "2IB";
			else if (deltaX >= 0 && deltaY <= 0) result = "3DA";
			else if (deltaX <= 0 && deltaY <= 0) result = "4IB";
		} else {
			if (deltaX >= 0 && deltaY >= 0) result = "5DB";
			else if (deltaX <= 0 && deltaY >= 0) result = "6DA";
			else if (deltaX >= 0 && deltaY <= 0) result = "7IB";
			else if (deltaX <= 0 && deltaY <= 0) result = "8IA";
		}
		//System.err.println("510:Sentido: " + result + " deltaX: " + deltaX + " / deltaY: " + deltaY + " / sentido: " + sentido);
		return result;
	}

	public boolean validarMovimientoTorre(Posicion ini, Posicion fin, Pieza p){
		boolean result = false;
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
				if (null != getPieza(posicionActual.getX(), posicionActual.getY()) && posicionActual.getX() != fin.getX() && posicionActual.getY() != fin.getY()){
					System.err.println("356:La Torre no puede pasar sobre una pieza.");
					result = false;
					break;
				} else {
					System.err.println("360:Sigo buscando movimientos validos con la Torre.");
					result = true;
				}
			}
		}	
		return result;
	}

	public boolean validarMovimientoDama(Posicion ini, Posicion fin, Pieza p){
		boolean result = false;
		String tmpArriba = p.getColor().toString().equals("WHITE")?"B":"N";
		String sentido = validarSentido(ini, fin, tmpArriba.equals(quienArriba));
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			String sentidoPosActual = validarSentido(ini, posicionActual, tmpArriba.equals(quienArriba));
			if (sentido.equals(sentidoPosActual) ) {
//				if (posicionActual.getX() <= ChessRules.SIZE && posicionActual.getY() <= ChessRules.SIZE) {
//					System.err.println("551: posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / Pos fin X: " + fin.getX() + " pos fin Y: " + fin.getY() +  " / sentidoactual: " + sentidoPosActual + " / result actual: " + result);
//					if(posicionActual.getX() != ini.getX() && posicionActual.getY() != ini.getY()) {
						if (null != getPieza(posicionActual.getX(), posicionActual.getY()) && /*posicionActual.getX() != fin.getX() && posicionActual.getY() != fin.getY() &&*/ posicionActual.getX() != ini.getX() && posicionActual.getY() != ini.getY()){
							System.err.println("554:La dama no puede pasar sobre una pieza.");
							result = false;
							break;
						} else {
							if (null == getPieza(posicionActual.getX(), posicionActual.getY())) {
								if(posicionActual.getX() == fin.getX() && posicionActual.getY() == fin.getY()) {
									System.err.println("560:La dama ha llegado a su posicion destino.");
									result = true;
									break;
								} else {
									result = false;
								}
							} else {
//								System.err.println("567:Sigo buscando movimientos validos con la dama.");
								result = true;
							}
						}
//					}
//				}
			} 
			else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
					System.err.println("564->Pos ini X: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY()) && posicionActual.getX() != fin.getX() && posicionActual.getY() != fin.getY()){
						System.err.println("566:La dama no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println("570:Sigo buscando movimientos validos con la dama.");
						result = true;
					}
				}
			} else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
					System.err.println("576->Pos ini x: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY()) && posicionActual.getX() != fin.getX() && posicionActual.getY() != fin.getY()){
						System.err.println("578:La dama no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println("582:Sigo buscando movimientos validos con la dama.");
						result = true;
					}
				}
			} else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE && posicionActual.getY() < ChessRules.SIZE) {
					System.err.println("588->Pos ini x: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY()) && posicionActual.getX() != fin.getX() && posicionActual.getY() != fin.getY()){
						System.err.println("590:La dama no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println("594:Sigo buscando movimientos validos con la dama.");
						result = true;
					}
				}
			}
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
					System.err.println("394:Movimiento no válido con el Rey. No puede saltar sobre una pieza del mismo color.");
					result = false;
					break;
				} else {
					System.err.println("398:Movimiento con el Rey.");
					result = true;
				}
			}
		}
		result =  validarCasillaOcupadaMismoColor(ini, fin);
		if (result && (Math.abs((ini.getY() - fin.getY())) == 2)){
			System.err.println("412:El rey saltó dos casillas. Validar enroque");
			if (p.isPosicionInicial()) {
				System.err.println("414:El rey está en su posición inicial y puede hacer enroque.");
				result = true;
			} else {
				System.err.println("************Ya había movido el rey y no puede hacer enroque.");
				result = false;
			}
		}
		return result;
	}

	public boolean moverPieza(String movimiento){
		Pieza pieza1 = null;
		Pieza pieza2 = null;
		boolean permite = true;
		boolean permiteMovimientoPeon = false;
		boolean promocionPeon = false;
		if(Pattern.matches("([a-h][1-8]){2}", movimiento)) {
			System.err.println("652:Moviendo: " + movimiento.substring(0, 2) + " a " + movimiento.substring(2, 4));
			Coordenada co1 = new Coordenada();
			Coordenada co2 = new Coordenada();
			co1 = devolverCoordenada(movimiento.substring(0, 2));
			pieza1 = getPieza(co1.getX(), co1.getY());
			co2 = devolverCoordenada(movimiento.substring(2, 4));
			pieza2 = getPieza(co2.getX(), co2.getY());
			Posicion tmpPos = new Posicion(co2.getX(), co2.getY()); //Posicion para comparar
//			List<Posicion> listaDeMovimientosPieza = pieza1.getMovimientos(new Posicion(co1.getX(), co1.getY()));
			System.err.println("color:_ " + ((pieza1.getColor() == turnoColor)?"true":"false"));
			if (null != pieza1 && pieza1.getColor() == turnoColor /*&& null != listaDeMovimientosPieza*/) {
//				for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
//					Posicion posicionActual = (Posicion) iterator.next();
					if(pieza1.getNombre().equals("P")) {
						System.err.println("665:Validando movimiento de Peon");
						permite = validarMovimientoPeon(new Posicion(co1.getX(),co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
						permiteMovimientoPeon = true;
					} else if(pieza1.getNombre().equals("C")){
						System.err.println("669:Validando movimiento de Caballo");
						permite = validarMovimientoCaballo(new Posicion(co1.getX(),co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
					} else if(pieza1.getNombre().equals("A")){
						System.err.println("672:Validando movimiento de Alfil");
						permite = validarMovimientoAlfil(new Posicion(co1.getX(),co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
					} else if(pieza1.getNombre().equals("T")){
						System.err.println("675:Validando movimiento de Torre");
						permite = validarMovimientoTorre(new Posicion(co1.getX(),co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
					} else if(pieza1.getNombre().equals("D")){
						System.err.println("678:Validando movimiento de Dama");
						permite = validarMovimientoDama(new Posicion(co1.getX(),co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
					} else if(pieza1.getNombre().equals("R")){
						System.err.println("681:Validando movimiento de Rey");
						permite = validarMovimientoRey(new Posicion(co1.getX(),co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
						if (permite && Math.abs((co1.getY() - co2.getY())) == 2) {
							permite = validarEnroque(co1, co2);
						}
						if (permite) {
							pieza1.enJaque = false;
							enJaque = false;
						}
					}
				} else {
					permite = false;
					System.err.println("701:El turno es para el jugador: " + turnoColor);
				}
					//Post Validaciones
					System.err.println("704:Permite: " + permite + " / enJaque: " + enJaque + " / permiteMovimientoPeon: " + permiteMovimientoPeon);
					if(permite /*&& !enJaque && !permiteMovimientoPeon*/){
						if (enJaque) {
							if(permiteMovimientoPeon) {
								/*do something*/
							}
						} 
						turnoColor = nextColor(turnoColor);
						System.err.println("707:El turno es para el jugador: " + turnoColor);
						jugadas.add(movimiento + " ");
						System.err.println("709:Se agrega el movimiento a la lista de jugadas de la partida.");
						System.err.println("710:Pieza en casilla origen : " + t1[co1.getX()][co1.getY()]+ " en (" + co1.getX()+", "+ co1.getY() + ") / pieza en casilla destino: " + t1[co2.getX()][co2.getY()]);
						if (pieza1.getNombre().contains("R")) {
							if (Math.abs((co1.getY() - co2.getY())) == 2) { //Intento Enroque
								switch (co1.getX()) {
								case 0:
									if(co1.getY() == 2) {
										setCasilla(co2.getX(), co2.getY(), pieza1);//Gráfico
										t1[co2.getX()][co2.getY()] = pieza1;
										t1[co1.getX()][co1.getY()] = null; //Lógico
										t1[0][3] = getPieza(0, 0); //Lógico
										setCasilla( 0, 3, getPieza(0, 0));//Gráfico
										t1[0][0] = null; //Lógico
									} else {
										setCasilla(co2.getX(), co2.getY(), pieza1);//Gráfico
										t1[co2.getX()][co2.getY()] = pieza1;
										t1[co1.getX()][co1.getY()] = null; //Lógico
										t1[0][5] = getPieza(0, 7); //Lógico
										setCasilla( 0, 5, getPieza(0, 7));//Gráfico
										t1[0][7] = null; //Lógico
									}
									break;
								case 7:
									if(co1.getY() == 2) {
										setCasilla(co2.getX(), co2.getY(), pieza1);//Gráfico
										t1[co2.getX()][co2.getY()] = pieza1;
										t1[co1.getX()][co1.getY()] = null; //Lógico
										t1[7][3] = getPieza(7, 0); //Lógico
										setCasilla( 7, 3, getPieza(7, 0));//Gráfico
										t1[7][0] = null; //Lógico
									} else {
										setCasilla(co2.getX(), co2.getY(), pieza1);//Gráfico
										t1[co2.getX()][co2.getY()] = pieza1;
										t1[co1.getX()][co1.getY()] = null; //Lógico
										t1[7][5] = getPieza(7, 7); //Lógico
										setCasilla( 7, 5, getPieza(7, 7));//Gráfico
										t1[7][7] = null; //Lógico
									}
									break;

								default:
									break;
								}
							} else {
								setCasilla(co2.getX(), co2.getY(), pieza1);//Gráfico
								borrarCasilla(co1.getX(), co1.getY());//Logico
								t1[co2.getX()][co2.getY()] = pieza1; 
								t1[co1.getX()][co1.getY()] = null;
							}
						} else {
							setCasilla(co2.getX(), co2.getY(), pieza1);//Gráfico
							borrarCasilla(co1.getX(), co1.getY());//Logico
							t1[co2.getX()][co2.getY()] = pieza1; 
							t1[co1.getX()][co1.getY()] = null;
						}
						if (permiteMovimientoPeon) {
							System.err.println("770:piezasEnPassant.size(): " + piezasEnPassant.size());
							if (null != piezasEnPassant && piezasEnPassant.size() > 0) {
								System.err.println("772:mueve peon y esta en passant ... mire a ver que hace mijo");
								if (pieza1.baja) {
									System.err.println("773: borrar casilla izq 1 if");
									borrarCasilla(co2.getX() - 1, co2.getY());
								} else {
									System.err.println("777: borrar casilla der 2 else");
									borrarCasilla(co2.getX() + 1, co2.getY());
								}
								piezasEnPassant.clear();
							} else {
								System.err.println("782: Llama a poner en passant");
								ponerEnPassant(new Posicion(co2.getX(), co2.getY()));
							}
							if(pieza1.baja && co2.getX() == ChessRules.SIZE - 1){ //Promovido
								promocionPeon = true;
							} else if (!pieza1.baja && co2.getX() == 0) { //Promovido
								promocionPeon = true;
							}
							if (promocionPeon) {
								InputStreamReader isr = new InputStreamReader(System.in);
								BufferedReader br = new BufferedReader(isr);
								System.out.println("Seleccione una de las siguientes piezas: Dama [D] Torre [T] Alfil [A] Caballo [C]" );
								try {
									String piezaElegida = br.readLine();
									if(Pattern.matches("([D][T][A][C])", piezaElegida)) {
										if (piezaElegida.equals("D")) {
											t1[co2.getX()][co2.getY()] = new Dama(turnoColor);
											t[co2.getX()][co2.getY()] = "D" + (turnoColor.toString().contains("N")?"N":"B");
										} if (piezaElegida.equals("T")) {
											t1[co2.getX()][co2.getY()] = new Torre(turnoColor);
											t[co2.getX()][co2.getY()] = "T" + (turnoColor.toString().contains("N")?"N":"B");
										} if (piezaElegida.equals("A")) {
											t1[co2.getX()][co2.getY()] = new Alfil(turnoColor);
											t[co2.getX()][co2.getY()] = "A" + (turnoColor.toString().contains("N")?"N":"B");
										} if (piezaElegida.equals("C")) {
											t1[co2.getX()][co2.getY()] = new Caballo(turnoColor);
											t[co2.getX()][co2.getY()] = "C" + (turnoColor.toString().contains("N")?"N":"B");
										}
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
								promocionPeon = false;
							}
						}
						permiteMovimientoPeon = false;
						if(pieza1.toString().contains("P") || pieza1.toString().contains("R")) pieza1.setPosicionInicial(false);//Peon o Rey ya no esta en posicion Inicial
						if(evaluarJaque(pieza1.getMovimientos(tmpPos,t1), pieza1)) {//Posicion final del movimiento
							System.err.println("820:El sistema detectó JAQUE en su rey por favor muévalo o cubra al Rey.");
//							System.err.println("799:Pieza1 que da jaque: " + pieza1.getNombre());
//							piezasJaqueadoras.add(pieza1);
							enJaque = true;
						} else {
							enJaque = false;
						}
						if(enJaque) {
							//if(evaluarMate(pieza1.getMovimientos(tmpPos), pieza1) && enJaque) {//Posicion final del movimiento
							if(isCheckMate(pieza1.getMovimientos(tmpPos,t1), pieza1)) {//Posicion final del movimiento
//								System.err.println("807:El sistema detectó MATE.");
								enMate = true;
								gameOver = true;
							} else {
								enMate = false;
							}
						}
//					} else {
//						//enJaque = false;
////						System.err.println("816:El sistema ...");
//						//poner el caso de tapar la jugada, mover rey y quitar la pieza jaqueadora
//						System.err.println("818:El sistema detectó Jaque.");
//						//evaluarJaque(piezasJaqueadoras.get(0).getMovimientos(encontrarPieza(piezasJaqueadoras.get(0))), pieza1);
//						if(!enJaque)
//							piezasJaqueadoras.clear();
					} else {
						if("P".equals(pieza1.getNombre()) && permiteMovimientoPeon) {
//							if (pieza1.baja) {
								System.err.println("843: borrar casilla izq 1 if");
//								setCasilla(co2.getX(), co2.getY(), pieza1);//Gráfico
//								borrarCasilla(co1.getX(), co1.getY());//Logico
//								t1[co2.getX()][co2.getY()] = pieza1; 
//								t1[co1.getX()][co1.getY()] = null;
//							} else {
//								System.err.println("849: borrar casilla der 2 else");
//								setCasilla(co2.getX(), co2.getY(), pieza1);//Gráfico
//								borrarCasilla(co1.getX(), co1.getY());//Logico
//								t1[co2.getX()][co2.getY()] = pieza1; 
//								t1[co1.getX()][co1.getY()] = null;
//							}
						} else {
							//else de if(permite && !enJaque){
							if(isCheckMate(pieza1.getMovimientos(tmpPos,t1), pieza1)) {//Posicion final del movimiento
								System.err.println("859:El sistema detectó MATE.");
								enMate = true;
								gameOver = true;
							} else {
								System.err.println("840:El sistema detectó JAQUE.");
								enMate = false;
							}
						}
					}
				} else {
					System.err.println("824:En la casilla de origen no hay pieza a mover");
				}
//		}
		return true;
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
		System.err.println("586: validarEnroque: " + result);
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
		System.err.println("evaluarJaque / Rey buscado de color: " + nextColor(p.getColor()));
		System.err.println("870:Posicion del rey contrario-> x:" + posRey.getX() + " / pos y: " + posRey.getY());
		for (Iterator iterator = posiciones.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
//			System.err.println("494:Posicion que esta siendo evaluada: (" + posicionActual.getX() + ", " + posicionActual.getY() + ")");
			if(posRey.compareTo(posicionActual) == 0){
				llenarPiezasJaqueadoras(p);
				System.err.println("875:El rey está en peligro!");
				result = true;
				getPieza(posRey.getX(), posRey.getY()).enJaque = true;
				break;
			} else {
				result = false;
			}
		}
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
			System.err.println("905:Pos actual Rey: " + posicionActual.toString());
			movimientoReyActual.add(posicionActual);
			if(evaluarJaque(movimientoReyActual, p)){
				//movimientosRey.remove();
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}
/*
 * 
 * 			String strNombrePieza = getPieza(piezasJaqueadoras.get(0));
			if(strNombrePieza.contains("P"))
				tmpPieza = new Peon(nextColor(turnoColor), (strNombrePieza.contains("B") && tmpArriba.contains("B")), false, false, false);
			else if(strNombrePieza.contains("A"))
				tmpPieza = new Alfil(nextColor(turnoColor));
			else if(strNombrePieza.contains("C"))
				tmpPieza = new Caballo(nextColor(turnoColor));
			else if(strNombrePieza.contains("T"))
				tmpPieza = new Torre(nextColor(turnoColor));
			else if(strNombrePieza.contains("D"))
				tmpPieza = new Dama(nextColor(turnoColor));
			//Evaluar primero que el rey tiene movimientos disponibles
			//La pieza desde la posicion actual tiene disponible el movimiento hasta la posicion actual del rey
			if(   ) {
				
			} else {
				
			}
 */
	private Posicion encontrarRey(Pieza.Color color) {
		Posicion resultPos = null; 
		Rey rey = new Rey(nextColor(color),false,true);
		System.err.println("941:El rey: " + rey.toString() + " es el buscado...");
		for (int i = 0; i < ChessRules.SIZE; i++) {
			if(null == resultPos) {
				for (int j = 0; j < ChessRules.SIZE; j++) {
					if(null != getPieza(i, j)){
						if( getPieza(i, j).toString().equals(rey.toString())){
							resultPos = new Posicion(i, j);
							System.err.println("948:La pieza : " + getPieza(i, j) + " se encuentra en (i,j): (" + i + "," + j + ")");
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
						//System.err.println("531:La pieza en la posicion actual es: " + getPieza(i, j) + " La pieza buscada es: " + (p.getNombre()+ (p.getColor().equals("WHITE")?"B":"N")));
						if( getPieza(i, j).toString().equals((p.getNombre()+ (p.getColor().equals("WHITE")?"B":"N")))){
							resultPos = new Posicion(i, j);
//							System.err.println("968:La pieza : " + getPieza(i, j) + " se encuentra en (i,j): (" + i + "," + j + ")");
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
		System.err.println("983:La casilla devuelta es : (" + c.getX() + " , " + c.getY() + ")");
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
		System.err.println("*****1004:Entrando a poner enPassant. Fila: " + fila + " / turno: " + turnoContrario + " / pos peon: (" + posPeon.getX() + ", " + posPeon.getY() + ")");
		if(null != getPieza(posPeon.getX(), posPeon.getY()) && getPieza(posPeon.getX(), posPeon.getY()).saltoDos ) {
			System.err.println("*****1006:ponerEnPassant / peon SaltoDos: "); //+ getPieza(posPeon.getX(), posPeon.getY()).saltoDos);
			if(null != getPieza(fila, posPeon.getY() + 1) && getPieza(fila, posPeon.getY() + 1).toString().contains("P") /*&& getPieza(fila, posPeon.getY() + 1 ).toString().contains(turnoContrario)*/){
				if(null != getPieza(posPeon.getX(), posPeon.getY() + 1 )){ System.err.println("586:Poniendo el peon ("+ posPeon.getX()  + "," + (posPeon.getY() + 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() + 1] + ")"); System.err.println("P: " + getPieza(fila, posPeon.getY() + 1).toString());}
				System.err.println("****1009:Poniendo el peon ("+ posPeon.getX()  + "," + (posPeon.getY() + 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() + 1] + ")");
//				getPieza(fila, (posPeon.getY() + 1) ).enPassant = true;
				piezasEnPassant.add(getPieza(fila, posPeon.getY() + 1 ));
				System.err.println("****1012:piezasEnPassant: " + piezasEnPassant.get(0) + " / size: " + piezasEnPassant.size());
			} else if (null != getPieza(fila, posPeon.getY() - 1 ) && getPieza(fila, posPeon.getY() - 1 ).toString().contains("P") /*&& getPieza(fila, posPeon.getY() - 1 ).toString().contains(turnoContrario)*/) {
//				getPieza(fila, (posPeon.getY() - 1) ).enPassant = true;
				if(null != getPieza(posPeon.getX(), posPeon.getY() - 1 )){ System.err.println("587:Poniendo el peon ("+ posPeon.getX()  + "," + (posPeon.getY() - 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() - 1] + ")"); System.err.println("P: " + getPieza(fila, posPeon.getY() - 1).toString());}
				System.err.println("****1016:Poniendo el peon ("+ fila  + "," + (posPeon.getY() - 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() - 1] + ")" + " / pieza: " + getPieza(fila, posPeon.getY() - 1 ));
				piezasEnPassant.add(getPieza(fila, posPeon.getY() - 1 ));
				System.err.println("***1018:piezasEnPassant: " + piezasEnPassant.get(0) + " / size: " + piezasEnPassant.size());
			}
			getPieza(posPeon.getX(), posPeon.getY()).saltoDos = false;
		}
		if (piezasEnPassant.size() > 0)
			System.err.println("**********En la lista de passant hay: " + piezasEnPassant.size() + " / pos 1: " + piezasEnPassant.get(0));
		else 
			System.err.println("**********En la lista de passant hay: 0");
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

	public boolean isCheckMate( List<Posicion> posiciones, Pieza p ) { //player, opp
		boolean result = false;
		//Posicion del Rey
		Posicion posRey = encontrarRey(p.getColor());
		/* Traer todos los movimientos posibles del Rey */
//		List<Posicion> movimientoReyActual = new ArrayList<Posicion>();
		
		List<Posicion> movimientosRey = new ArrayList<Posicion>();
		List<Posicion> movimientosReyAux = new ArrayList<Posicion>();
		
		movimientosRey.addAll(t1[posRey.getX()][posRey.getY()].getMovimientos(posRey,t1));
		
		//Encontrar todas las piezas que atacan al Rey
		//Lista de piezas que dan Jaque 
		llenarPiezasJaqueadoras(p);
		for (Pieza pieza: piezasJaqueadoras) {
			Posicion pos = encontrarPieza(pieza);
			if(movimientosRey.contains(pos)){
				int ubicacion = movimientosRey.indexOf(pos);
				movimientosRey.remove(ubicacion);
			}
		}
		boolean isReySolo = false;
		int []posicionesABorrar = new int[movimientosRey.size()];
		int k = 0;
		if(movimientosRey.size() > 0) {
			movimientosReyAux = movimientosRey;
			for (Iterator iterator = movimientosReyAux.iterator(); iterator.hasNext();) {
				Posicion posicion = (Posicion) iterator.next();
				System.err.println("Pos x: " + posicion.getX() + " / pos Y: " + posicion.getY());
				if (null != t1[posicion.getX()][posicion.getY()] && t1[posRey.getX()][posRey.getY()].getColor() != p.getColor() ) {
					int ubicacion = movimientosReyAux.indexOf(posicion);
					if (ubicacion != -1) {
						posicionesABorrar[k] = ubicacion;
						System.err.println("Posicion a borrar en: " + ubicacion + " / movRey: " + movimientosRey.get(ubicacion));
						k++;
					}
				}
			}
			for (int i = 0; i < k; i++) {
				System.err.println("Posiciones a borrar: " + posicionesABorrar[i]);
			}
			System.err.println("Movimientos rey size (antes): " + movimientosRey.size());
			for (int i = k - 1; i >= 0; i--) {
				movimientosRey.remove(posicionesABorrar[i]);
			}
			System.err.println("Movimientos rey size (despues): " + movimientosRey.size());
			for (Iterator iterator = movimientosRey.iterator(); iterator.hasNext();) {
				Posicion posIcion = (Posicion) iterator.next();
				System.err.println("Posiciones restantes para el rey: " + posIcion.toString());
			}
			posicionesABorrar = new int[movimientosRey.size()];
			k = 0;
			if(movimientosRey.size() > 0) {
				//volver a chequear si esos movimientos que quedan no estan tambien en jaque
				if (piezasJaqueadoras.size() > 0 ) {
					for (Pieza pieza : piezasJaqueadoras) {
						for (Iterator iterator2 = movimientosRey.iterator(); iterator2.hasNext();) {
							Posicion posicionRey = (Posicion) iterator2.next();
							System.err.println("Pos REY: " + posicionRey.toString());
							for (Iterator iterator = pieza.getMovimientos(encontrarPieza(pieza),t1).iterator(); iterator.hasNext();) {
								Posicion posPieza = (Posicion) iterator.next();
//								System.err.println("Posicion actual comparada de la pieza que da jaque: " + posPieza.toString());
								if( posPieza.compareTo(posicionRey) == 0 ){
									int ubicacion = movimientosRey.indexOf(posicionRey);
									posicionesABorrar[k] = ubicacion;
									k++;
									System.err.println("Encontre una posicion para borrar: " + ubicacion + " / posRey: " + posicionRey.toString() + " / K: " + k);
									break;
								}
							}
						}
					}
				}
			}
			System.err.println("Movimientos rey size (antes): " + movimientosRey.size());
			for (int i = k - 1; i >= 0; i--) {
				movimientosRey.remove(posicionesABorrar[i]);
			}
			System.err.println("Movimientos rey size (despues): " + movimientosRey.size());
			
			for (Iterator iterator = movimientosRey.iterator(); iterator.hasNext();) {
				Posicion pos = (Posicion) iterator.next();
				System.err.println("Posiciones finales del Rey: " + pos.toString());
			}
			if(movimientosRey.size() > 0) {
				//El rey puede salir solo del jaque
				isReySolo = true;
			} else {
				isReySolo = false;
			}
		} else if(piezasJaqueadoras.size() > 1) {
			isReySolo = false;
		}
		
		System.err.println("Rey solo: " + isReySolo);
//		System.err.println("Piezas jaqueadoras: " + piezasJaqueadoras.get(0).toString());
		boolean isPiezaCubre = false;
//		casillasAtacadas
		//llenar una lista con las piezas que pueden cubrir al rey en el jaque
		//Luego, evaluar si hay piezas que puedan tapar el jaque o declarar
		//mate siempre que la variable isReySolo = false
		
		String camino = mostrarCaminoABloquear(posRey, encontrarPieza(p));
		String cualArriba = piezaUbicacionRespectoAOtra(posRey, encontrarPieza(p));
		System.err.println("Camino elegido y que se debe bloquear : " + camino);
		int xRey = posRey.getX();
		int yRey = posRey.getY();
		int xTemp = 0;
		int yTemp = 0;
//		for (Iterator iterator = p.getMovimientos(encontrarPieza(p)).iterator(); iterator.hasNext();) {
//			Posicion posicionActual = (Posicion) iterator.next();
//			xTemp = xRey - posicionActual.getX();
//			yTemp = yRey - posicionActual.getY();
//			if(!"".equals(camino)){
//				if("14".equals(camino) && xTemp > 0 && yTemp > 0){//x > 0 ; y > 0
//					System.err.println("14");
//					casillasAtacadas.add(posicionActual);
//				} else if("24".equals(camino) && xTemp == 0 && yTemp > 0){//x = 0 ; y > 0
//					System.err.println("24");
//					casillasAtacadas.add(posicionActual);
//				} else if("34".equals(camino) && xTemp < 0 && yTemp > 0){//x < 0 ; y > 0
//					System.err.println("34");
//					casillasAtacadas.add(posicionActual);
//				} else if("15".equals(camino) && xTemp > 0 && yTemp == 0){//x > 0 ; y = 0
//					System.err.println("15");
//					casillasAtacadas.add(posicionActual);
//				} else if("25".equals(camino)){
//					//Nada aqui
//				} else if("35".equals(camino) && xTemp < 0 && yTemp == 0){//x < 0 ; y = 0
//					System.err.println("35");
//					casillasAtacadas.add(posicionActual);
//				} else if("16".equals(camino) && xTemp > 0 && yTemp < 0){//x > 0 ; y < 0
//					System.err.println("16");
//					casillasAtacadas.add(posicionActual);
//				} else if("26".equals(camino) && xTemp == 0 && yTemp < 0){//x = 0 ; y < 0
//					System.err.println("26");
//					casillasAtacadas.add(posicionActual);
//				} else if("36".equals(camino) && xTemp < 0 && yTemp < 0){//x < 0 ; y < 0
//					System.err.println("36");
//					if("1".equals(cualArriba)) {//Rey mas arriba que la pieza que ataca
//						String posActualVsPiezaQueAtaca = piezaUbicacionRespectoAOtra(encontrarPieza(p),posicionActual);
//						if("2".equals(posActualVsPiezaQueAtaca)) {
//							if ( Math.abs(xTemp) == Math.abs(yTemp) )
//								casillasAtacadas.add(posicionActual);
//						}
//					}
//				}
//			}
//		}
		casillasAtacadas = p.getMovimientosConDireccion(encontrarPieza(p), posRey, t1);
		for (Iterator iterator = casillasAtacadas.iterator(); iterator.hasNext();) {
			Posicion casillaActual = (Posicion) iterator.next();
			System.err.println("Casilla atacada: " + casillaActual.toString());
		}
		
		if (isReySolo && isPiezaCubre)
			result = false;
		else
			result = true;
		
		result = false;
		return result;
	}
	/*
	 * 
	 * Recibe la pieza Rey como parametro.
	 * Encontrar todas las piezas que atacan al Rey.
	 * 
	 */
	
	public void llenarPiezasJaqueadoras(Pieza p){
//		Posicion posRey = encontrarRey(nextColor(p.getColor()));
//		String colorPieza = colorALetraInicial(p.getColor());
//		List<Posicion> movimientosRey = new ArrayList<Posicion>();
//		movimientosRey.addAll(p.getMovimientos(posRey));
//		System.err.println("Pieza jaqueadora: " + p.getNombre() + " / color: " + colorPieza.toString());
		if(!piezasJaqueadoras.contains(p))
			piezasJaqueadoras.add(p);
		System.err.println("llenarPiezasJaqueadoras / Piezas Jaquedoras tamaño: " + piezasJaqueadoras.size());
//		for (int i = 0; i < ChessRules.SIZE; i++) {
//			for (int j = 0; j < ChessRules.SIZE; j++) {
//				if ( t[i][j].contains(colorPieza)){//Las piezas del color
//					Posicion posActual = new Posicion(i, j);
//					List<Posicion> movimientosPiezaActual = new ArrayList<Posicion>();
//					movimientosPiezaActual.addAll(t1[i][j].getMovimientos(posActual));
//					for (Posicion posicion : movimientosPiezaActual) {
//						if (movimientosRey.contains(posicion)) {
//							piezasJaqueadoras.add(t1[i][j]);
//							System.err.println("Piezas Jaqueadoras: " + t1[i][j].getNombre());
//						}
//					}
//				}
//			}
//		}
	}

	public String colorALetraInicial(Pieza.Color color) {
		return color.toString().equals("BLACK")?"N":"B";
	}

	//p1: Posicion del rey
	//p2: Posicion de la pieza que da jaque
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
	
	public void llenarCasillasAtacadas(){
		for (int i = 0; i < ChessRules.SIZE; i++) {
			for (int j = 0; j < ChessRules.SIZE; j++) {
				Posicion posActual = new Posicion(i, j);
				if (null != t1[i][j]) {
					if (t1[i][j].getColor().toString().equals("WHITE") ){
						casillasAtacadasXBlanco.addAll(t1[i][j].getMovimientos(posActual,t1));
					} else {
						casillasAtacadasXNegro.addAll(t1[i][j].getMovimientos(posActual,t1));
					} 
				}
			}
		}
	}
}
