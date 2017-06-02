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
 * Nombre del método: main Descripción: <i>Método para </i>
 * 
 * @author hernan.gil
 * @param args
 * 
 *            MODIFICACIONES:
 * 
 *            Fecha: 11/02/2016 Autor - Empresa: hernan.gil Descripción:
 *            Creación del método
 */

public class AjedrezCopy {

	// Clase singleton
	private static AjedrezCopy INSTANCE = new AjedrezCopy();
	private List<String> jugadas = new LinkedList<String>();
	private List<Pieza> piezasEnPassant = new ArrayList<Pieza>();

	private List<Posicion> casillasAtacadas = new ArrayList<Posicion>();
	private List<Posicion> casillasAtacadasXBlanco = new ArrayList<Posicion>();
	private List<Posicion> casillasAtacadasXNegro = new ArrayList<Posicion>();

	private Pieza.Color turnoColor = Pieza.Color.WHITE;
	private String quienArriba = "";

	private AjedrezCopy() {
	};

	private boolean enJaque = false;
	private boolean enMate = false;
	private boolean gameOver = false;

	private String piezas[] = { "T", "C", "A", "D", "R", "P" };
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

	public static AjedrezCopy getInstance() {
		return INSTANCE;
	}

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
	// Solo para ver graficamente y orientarme
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
	public void posicionInicialPiezasBlancas(boolean arriba) {
		if (arriba) {
			// Pintar
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|TB|CB|AB|DB|RB|AB|CB|TB|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|PB|PB|PB|PB|PB|PB|PB|PB|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|  |XX|  |XX|  |XX|  |XX|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|XX|  |XX|  |XX|  |XX|  |");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|  |XX|  |XX|  |XX|  |XX|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|XX|  |XX|  |XX|  |XX|  |");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|PN|PN|PN|PN|PN|PN|PN|PN|");
			System.err.println(Thread.currentThread().getStackTrace()[2] + "|TN|CN|AN|DN|RN|AN|CN|TN|");
		} else {
			// Pintar
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
			for (int i = 0; i < 8; i++) {
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
			for (int i = 0; i < 8; i++) {
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
			t1[0][4] = new Rey(colorArriba, false, true);
			t1[0][5] = new Alfil(colorArriba);
			t1[0][6] = new Caballo(colorArriba);
			t1[0][7] = new Torre(colorArriba);
			for (int i = 0; i < 8; i++) {
				t1[1][i] = new Peon(colorArriba, true, false, false, false);
				t1[6][i] = new Peon(colorAbajo, false, false, false, false);
			}
			t1[7][0] = new Torre(colorAbajo);
			t1[7][1] = new Caballo(colorAbajo);
			t1[7][2] = new Alfil(colorAbajo);
			t1[7][3] = new Dama(colorAbajo);
			t1[7][4] = new Rey(colorAbajo, false, true);
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
			t1[7][4] = new Rey(colorArriba, false, true);
			t1[7][5] = new Alfil(colorArriba);
			t1[7][6] = new Caballo(colorArriba);
			t1[7][7] = new Torre(colorArriba);
			for (int i = 0; i < 8; i++) {
				t1[1][i] = new Peon(colorAbajo, true, false, false, false);
				t1[6][i] = new Peon(colorArriba, false, false, false, false);
			}
			t1[0][0] = new Torre(colorAbajo);
			t1[0][1] = new Caballo(colorAbajo);
			t1[0][2] = new Alfil(colorAbajo);
			t1[0][3] = new Dama(colorAbajo);
			t1[0][4] = new Rey(colorAbajo, false, true);
			t1[0][5] = new Alfil(colorAbajo);
			t1[0][6] = new Caballo(colorAbajo);
			t1[0][7] = new Torre(colorAbajo);
		}
	}

	public String getPieza(Pieza p) {
		String piecita = p.getClass().getSimpleName();

		if ("Torre".equals(p.getClass().getSimpleName()))
			piecita = "T";
		if ("Alfil".equals(p.getClass().getSimpleName()))
			piecita = "A";
		if ("Caballo".equals(p.getClass().getSimpleName()))
			piecita = "C";
		if ("Dama".equals(p.getClass().getSimpleName()))
			piecita = "D";
		if ("Rey".equals(p.getClass().getSimpleName()))
			piecita = "R";
		if ("Peon".equals(p.getClass().getSimpleName()))
			piecita = "P";

		// System.err.println(Thread.currentThread().getStackTrace()[2] +
		// "Color: " + p.getColor().toString().equals("BLACK"));
		return piecita + (p.getColor().toString().equals("BLACK") ? "B" : "N");
	}

	public String getTipoPieza(int x, int y, Pieza pieza) {
		String p = getPieza(pieza);
		// System.err.println(Thread.currentThread().getStackTrace()[2] +
		// "getTipoPieza / Pieza: " + p);
		return p;
	}

	public Pieza getPieza(int x, int y) {
		return t1[x][y];
	}

	public void setCasilla(int x, int y, Pieza pieza) {
		t[x][y] = getTipoPieza(x, y, pieza);
	}

	public void borrarCasilla(int x, int y) {
		t[x][y] = ""; // o null - grafica
		t1[x][y] = null; // juego - logica
	}

	public String casillaOcupada(int x, int y) {
		System.err.println(Thread.currentThread().getStackTrace()[2] + "Evaluando x: " + x + " y: " + y);
		if (null == getPieza(x, y))
			return "";
		else
			return getPieza(x, y).toString();
	}

	/**
	 * observable : pieza data : puede ser un dato relevante -> definir despues
	 */
	// @Override
	// public void update(Observable observable, Object data) {
	// System.err.println(Thread.currentThread().getStackTrace()[2] +
	// "El Tablero esta siendo notificado...");
	// }

	public static void main(String[] args) {
		// String nombre = "Hernan ";
		// String nombres[] = new String[2];
		// nombres[0] = nombre.split(" ")[0];
		// nombres[1] = nombre.split(" ")[1];
		// if(null == nombre.split(" ")[1])
		// nombres[1] = "";
		// System.err.println(Thread.currentThread().getStackTrace()[2] +
		// "Nombre 1: " + nombres[0] + " / nombre 2: " + nombres[1]);
		AjedrezCopy ta = new AjedrezCopy();
		boolean esArribaBlancas = false;
		ta.quienArriba = esArribaBlancas ? "B" : "N"; // variable para que
														// funcione el metodo
														// validarSentido
		ta.posicionarPiezas(esArribaBlancas);// Logico
		ta.posicionarPiezasGraficas(esArribaBlancas);// Gráfico
		System.out.print(ta.toString());

		// int i = 3;
		while (!ta.gameOver /* || i > 0 */) {
			ta.llenarCasillasAtacadas();
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			System.out.println("Digite su jugada: ");
			try {
				String jugada = br.readLine();
				// Validar movimiento: Si el movimiento no termina en mate->
				// continue jugando
				if (jugada.equals("quit")) {
					ta.gameOver = true;
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Se terminó el juego.");
					break;
				}
				ta.moverPieza(jugada);
				System.out.print(ta.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void evaluarJuego() {
		int valorBlanco = 0, valorNegro = 0;
		for (int i = 0; i < ChessRules.SIZE; i++) {
			for (int j = 0; j < ChessRules.SIZE; j++) {
				if (t[i][j].contains("B")) {
					if (t[i][j].contains("D"))
						valorBlanco += valorPiezas.get("D");
					if (t[i][j].contains("T"))
						valorBlanco += valorPiezas.get("T");
					if (t[i][j].contains("A"))
						valorBlanco += valorPiezas.get("A");
					if (t[i][j].contains("C"))
						valorBlanco += valorPiezas.get("C");
					if (t[i][j].contains("P"))
						valorBlanco += valorPiezas.get("P");
				} else if (t[i][j].contains("N")) {
					if (t[i][j].contains("D"))
						valorNegro += valorPiezas.get("D");
					if (t[i][j].contains("T"))
						valorNegro += valorPiezas.get("T");
					if (t[i][j].contains("A"))
						valorNegro += valorPiezas.get("A");
					if (t[i][j].contains("C"))
						valorNegro += valorPiezas.get("C");
					if (t[i][j].contains("P"))
						valorNegro += valorPiezas.get("P");
				}
			}
		}
	}

	private Pieza.Color nextColor(Pieza.Color color) {
		int indice = color.ordinal();
		if ((indice + 1) == Pieza.Color.values().length) {
			return Pieza.Color.values()[indice - 1];
		} else {
			return Pieza.Color.values()[indice + 1];
		}
	}

	public boolean validarCasillaOcupadaMismoColor(Posicion ini, Posicion fin) {
		boolean result = false;
		if (null == getPieza(fin.getX(), fin.getY())) {
			result = true; // Puede
		} else {
			String tmpIniCol = getPieza(ini.getX(), ini.getY()).toString().contains("N") ? "N" : "B";
			String tmpFinCol = getPieza(fin.getX(), fin.getY()).toString().contains("N") ? "N" : "B";
			if (tmpIniCol.equals(tmpFinCol)) {
				result = false; // No puede
			}
		}
		return result;
	}

	public boolean validarMovimientoPeon(Posicion ini, Posicion fin, Pieza p) {
		System.err.println(Thread.currentThread().getStackTrace()[2] + " validarMovimientoPeon / p.enPassant: " + p.enPassant);
		boolean result = false;
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			if (posicionActual.getX() < ChessRules.SIZE
					&& posicionActual.getY() < ChessRules.SIZE) {
				if (ini.getY() == fin.getY()
						&& null == getPieza(fin.getX(), fin.getY())) {
					if (p.baja) {
						if (ini.getX() < fin.getX()) {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de peon en avance válido. Con peon que baja.");
							result = true;
						} else {
							result = false;
						}
					} else { // peon no baja
						if (ini.getX() > fin.getX()) {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de peon en avance válido. Con peon que no baja.");
							result = true;
						} else {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de peon no válido.");
							result = false;
						}
					}
					// System.err.println(Thread.currentThread().getStackTrace()[2]
					// + "Movimiento de peon en avance válido.");
					// result = true;
				} else if (ini.getY() == fin.getY()
						&& null != getPieza(fin.getX(), fin.getY())) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento no válido con el peon ya que no puede capturar de frente.");
					return false;
					// break;
				} else if (null != getPieza(fin.getX(), fin.getY())/*
																	 * null !=
																	 * getPieza(
																	 * posicionActual
																	 *.getX() +
																	 * 1, * posicionActual
																	 *.getY() +
																	 * 1) ||
																	 * null !=
																	 * piezasEnPassant
																	 * &&
																	 * piezasEnPassant
																	 *.
																	 * contains(
																	 * p)
																	 */) {
					// Movimiento de captura con peon
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de captura con el peon derecha.");
					result = true;
				} else if (null != getPieza(posicionActual.getX() + 1, posicionActual.getY() - 1) /*
													 * || null !=
													 * piezasEnPassant &&
													 * piezasEnPassant
													 *.contains(p)
													 */) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento de captura con el peon izquierda.");
					result = true;
				}
			}
		}
		if (result && (Math.abs((ini.getX() - fin.getX())) == 2)) {
			System.err.println(Thread.currentThread().getStackTrace()[2] + " validarMovimientoPeon informa que salto dos.");
			p.saltoDos = true;
		}
		return result;
	}

	public boolean validarMovimientoCaballo(Posicion ini, Posicion fin, Pieza p) {
		boolean result = false;
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			if (posicionActual.getX() < ChessRules.SIZE
					&& posicionActual.getY() < ChessRules.SIZE) {
				if (null != getPieza(fin.getX(), fin.getY())) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento no válido con el Caballo. No puede saltar sobre una pieza del mismo color.");
					result = false;
					break;
				} else {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento con el Caballo.");
					result = true;
				}
			}
		}
		return result;
	}

	public boolean validarMovimientoAlfil(Posicion ini, Posicion fin, Pieza p) {
		boolean result = false;
		String tmpArriba = p.getColor().toString().equals("WHITE") ? "B" : "N";
		String sentido = validarSentido(ini, fin, tmpArriba.equals(quienArriba));
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			String sentidoPosActual = validarSentido(ini, posicionActual, tmpArriba.equals(quienArriba));
			if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE
						&& posicionActual.getY() < ChessRules.SIZE) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " ->Pos fin x: " + fin.getX() + " pos fin Y: " + fin.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual + " pieza actual: " + getPieza(posicionActual.getX(), posicionActual.getY()));
					if (null != getPieza(posicionActual.getX(), posicionActual.getY())
							&& posicionActual.getX() != fin.getX()
							&& posicionActual.getY() != fin.getY()) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " El Alfil no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						if (null == getPieza(posicionActual.getX(), posicionActual.getY())) {
							if (posicionActual.getX() == fin.getX()
									&& posicionActual.getY() == fin.getY()) {
								result = true;
								break;
							} else {
								result = false;
							}
						} else {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " Sigo buscando movimientos validos con el Alfil.");
							result = true;
						}
					}
				}
			} else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE
						&& posicionActual.getY() < ChessRules.SIZE) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " ->Pos ini x: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY())
							&& posicionActual.getX() != fin.getX()
							&& posicionActual.getY() != fin.getY()) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " El Alfil no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Sigo buscando movimientos validos con el Alfil.");
						result = true;
					}
				}
			} else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE
						&& posicionActual.getY() < ChessRules.SIZE) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " ->Pos ini x: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY())
							&& posicionActual.getX() != fin.getX()
							&& posicionActual.getY() != fin.getY()) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " El Alfil no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Sigo buscando movimientos validos con el Alfil.");
						result = true;
					}
				}
			} else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE
						&& posicionActual.getY() < ChessRules.SIZE) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " ->Pos ini x: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY())
							&& posicionActual.getX() != fin.getX()
							&& posicionActual.getY() != fin.getY()) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " El Alfil no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Sigo buscando movimientos validos con el Alfil.");
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
		if (sentido) {
			if (deltaX >= 0 && deltaY >= 0)
				result = "1IA";
			else if (deltaX <= 0 && deltaY >= 0)
				result = "2IB";
			else if (deltaX >= 0 && deltaY <= 0)
				result = "3DA";
			else if (deltaX <= 0 && deltaY <= 0)
				result = "4IB";
		} else {
			if (deltaX >= 0 && deltaY >= 0)
				result = "5DB";
			else if (deltaX <= 0 && deltaY >= 0)
				result = "6DA";
			else if (deltaX >= 0 && deltaY <= 0)
				result = "7IB";
			else if (deltaX <= 0 && deltaY <= 0)
				result = "8IA";
		}
		// System.err.println(Thread.currentThread().getStackTrace()[2] +
		// "510:Sentido: " + result + " deltaX: " + deltaX + " / deltaY: " +
		// deltaY + " / sentido: " + sentido);
		return result;
	}

	public boolean validarMovimientoTorre(Posicion ini, Posicion fin, Pieza p) {
		boolean result = false;
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			if (posicionActual.getX() < ChessRules.SIZE
					&& posicionActual.getY() < ChessRules.SIZE) {
				if (null != getPieza(posicionActual.getX(), posicionActual.getY())
						&& posicionActual.getX() != fin.getX()
						&& posicionActual.getY() != fin.getY()) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " La Torre no puede pasar sobre una pieza.");
					result = false;
					break;
				} else {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Sigo buscando movimientos validos con la Torre.");
					result = true;
				}
			}
		}
		return result;
	}

	public boolean validarMovimientoDama(Posicion ini, Posicion fin, Pieza p) {
		boolean result = false;
		String tmpArriba = p.getColor().toString().equals("WHITE") ? "B" : "N";
		String sentido = validarSentido(ini, fin, tmpArriba.equals(quienArriba));
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			String sentidoPosActual = validarSentido(ini, posicionActual, tmpArriba.equals(quienArriba));
			if (sentido.equals(sentidoPosActual)) {
				if (null != getPieza(posicionActual.getX(), posicionActual.getY())
						&& /*
							 * posicionActual.getX() != fin.getX() &&
							 * posicionActual.getY() != fin.getY() &&
							 */posicionActual.getX() != ini.getX()
						&& posicionActual.getY() != ini.getY()) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " La dama no puede pasar sobre una pieza.");
					result = false;
					break;
				} else {
					if (null == getPieza(posicionActual.getX(), posicionActual.getY())) {
						if (posicionActual.getX() == fin.getX()
								&& posicionActual.getY() == fin.getY()) {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " La dama ha llegado a su posicion destino.");
							result = true;
							break;
						} else {
							result = false;
						}
					} else {
						// System.err.println(Thread.currentThread().getStackTrace()[2]
						// +
						// "567:Sigo buscando movimientos validos con la dama.");
						result = true;
					}
				}
				// }
				// }
			} else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE
						&& posicionActual.getY() < ChessRules.SIZE) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " ->Pos ini X: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY())
							&& posicionActual.getX() != fin.getX()
							&& posicionActual.getY() != fin.getY()) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " La dama no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Sigo buscando movimientos validos con la dama.");
						result = true;
					}
				}
			} else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE
						&& posicionActual.getY() < ChessRules.SIZE) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " ->Pos ini x: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY())
							&& posicionActual.getX() != fin.getX()
							&& posicionActual.getY() != fin.getY()) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " La dama no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Sigo buscando movimientos validos con la dama.");
						result = true;
					}
				}
			} else if (sentido.equals(sentidoPosActual)) {
				if (posicionActual.getX() < ChessRules.SIZE
						&& posicionActual.getY() < ChessRules.SIZE) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " ->Pos ini x: " + ini.getX() + " pos ini Y: " + ini.getY() + " / posActual X: " + posicionActual.getX() + " posActual Y: " + posicionActual.getY() + " / sentidoactual: " + sentidoPosActual);
					if (null != getPieza(posicionActual.getX(), posicionActual.getY())
							&& posicionActual.getX() != fin.getX()
							&& posicionActual.getY() != fin.getY()) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " La dama no puede pasar sobre una pieza.");
						result = false;
						break;
					} else {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Sigo buscando movimientos validos con la dama.");
						result = true;
					}
				}
			}
		}
		return result;
	}

	public boolean validarMovimientoRey(Posicion ini, Posicion fin, Pieza p) {
		boolean result = false;
		List<Posicion> listaDeMovimientosPieza = p.getMovimientos(ini,t1);
		for (Iterator iterator = listaDeMovimientosPieza.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			if (posicionActual.getX() < ChessRules.SIZE
					&& posicionActual.getY() < ChessRules.SIZE) {
				if (null != getPieza(fin.getX(), fin.getY())) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento no válido con el Rey. No puede saltar sobre una pieza del mismo color.");
					result = false;
					break;
				} else {
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimiento con el Rey.");
					result = true;
				}
			}
		}
		result = validarCasillaOcupadaMismoColor(ini, fin);
		if (result && (Math.abs((ini.getY() - fin.getY())) == 2)) {
			System.err.println(Thread.currentThread().getStackTrace()[2] + " El rey saltó dos casillas. Validar enroque");
			if (p.isPosicionInicial()) {
				System.err.println(Thread.currentThread().getStackTrace()[2] + " El rey está en su posición inicial y puede hacer enroque.");
				result = true;
			} else {
				System.err.println(Thread.currentThread().getStackTrace()[2] + " **********Ya había movido el rey y no puede hacer enroque.");
				result = false;
			}
		}
		if(result)
			p.posicionInicial = false;
		return result;
	}

	public boolean moverPieza(String movimiento) {
		Pieza pieza1 = null;
		Pieza pieza2 = null;
		boolean permite = true;
		boolean permiteMovimientoPeon = false;
		boolean promocionPeon = false;
		Coordenada co1 = new Coordenada();
		Coordenada co2 = new Coordenada();
		co1 = devolverCoordenada(movimiento.substring(0, 2));
		pieza1 = getPieza(co1.getX(), co1.getY());
		co2 = devolverCoordenada(movimiento.substring(2, 4));
		pieza2 = getPieza(co2.getX(), co2.getY());
		Posicion tmpPos = new Posicion(co2.getX(), co2.getY()); // Posicion para
																// comparar
		if (null != pieza1 && null != t1[co1.getX()][co1.getY()]) {
			if (Pattern.matches("([a-h][1-8]){2}", movimiento)) {
				System.err.println(Thread.currentThread().getStackTrace()[2] + " Moviendo: " + movimiento.substring(0, 2) + " a " + movimiento.substring(2, 4));

				System.err.println(Thread.currentThread().getStackTrace()[2] + "color:_ " + ((pieza1.getColor() == turnoColor) ? "true" : "false"));
				if (null != pieza1 && pieza1.getColor() == turnoColor /* && null != listaDeMovimientosPieza*/) {
					if (pieza1.getNombre().equals("P")) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Validando movimiento de Peon");
						permite = validarMovimientoPeon(new Posicion(co1.getX(), co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
						if (permite)
							permiteMovimientoPeon = true;
						else
							permiteMovimientoPeon = false;
					} else if (pieza1.getNombre().equals("C")) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Validando movimiento de Caballo");
						permite = validarMovimientoCaballo(
								new Posicion(co1.getX(), co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
					} else if (pieza1.getNombre().equals("A")) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Validando movimiento de Alfil");
						permite = validarMovimientoAlfil(
								new Posicion(co1.getX(), co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
					} else if (pieza1.getNombre().equals("T")) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Validando movimiento de Torre");
						permite = validarMovimientoTorre(
								new Posicion(co1.getX(), co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
					} else if (pieza1.getNombre().equals("D")) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Validando movimiento de Dama");
						permite = validarMovimientoDama(new Posicion(
								co1.getX(), co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
					} else if (pieza1.getNombre().equals("R")) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Validando movimiento de Rey");
						permite = validarMovimientoRey(new Posicion(co1.getX(), co1.getY()), new Posicion(co2.getX(), co2.getY()), pieza1);
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
					System.err.println(Thread.currentThread().getStackTrace()[2] + " El turno es para el jugador: " + turnoColor);
				}
				// Post Validaciones
				System.err.println(Thread.currentThread().getStackTrace()[2] + " Permite: " + permite + " / enJaque: " + enJaque + " / permiteMovimientoPeon: " + permiteMovimientoPeon);
				if (permite /* && !enJaque && !permiteMovimientoPeon */) {
					if (enJaque) {
						if (permiteMovimientoPeon) {
							if (evaluarJaque(pieza1.getMovimientos(tmpPos,t1), pieza1)) {// Posicion final del movimiento
								System.err.println(Thread.currentThread().getStackTrace()[2] + " El sistema detectó JAQUE en su rey por favor muévalo o cubra al Rey.");
								// System.err.println(Thread.currentThread().getStackTrace()[2]
								// + "799:Pieza1 que da jaque: " +
								// pieza1.getNombre());
								// piezasJaqueadoras.add(pieza1);
								enJaque = true;
							} else {
								enJaque = false;
							}
						}
						if (enJaque) {
							// if(evaluarMate(pieza1.getMovimientos(tmpPos,t1), // pieza1) && enJaque) {//Posicion final del
							// movimiento
							if (esJaqueMate(pieza1.getMovimientos(tmpPos,t1), pieza1, co2)) {// Posicion final del
													// movimiento
								System.err.println(Thread.currentThread().getStackTrace()[2] + " El sistema detectó MATE-.");
								enMate = true;
								gameOver = true;
							} else {
								System.err.println(Thread.currentThread().getStackTrace()[2] + " El sistema no detectó mate.");
								enMate = false;
							}
						}
					}
					turnoColor = nextColor(turnoColor);
					System.err.println(Thread.currentThread().getStackTrace()[2] + " El turno es para el jugador: " + turnoColor);
					jugadas.add(movimiento + " ");
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Se agrega el movimiento a la lista de jugadas de la partida.");
					System.err.println(Thread.currentThread().getStackTrace()[2] + " Pieza en casilla origen : " + t1[co1.getX()][co1.getY()] + " en (" + co1.getX() + ", " + co1.getY() + ") / pieza en casilla destino: " + t1[co2.getX()][co2.getY()]);
					if (pieza1.getNombre().contains("R")) {
						if (Math.abs((co1.getY() - co2.getY())) == 2) { // Intento
																		// Enroque
							switch (co1.getX()) {
							case 0:
								if (co1.getY() == 2) {
									setCasilla(co2.getX(), co2.getY(), pieza1);// Gráfico
									t1[co2.getX()][co2.getY()] = pieza1;
									t1[co1.getX()][co1.getY()] = null; // Lógico
									t1[0][3] = getPieza(0, 0); // Lógico
									setCasilla(0, 3, getPieza(0, 0));// Gráfico
									t1[0][0] = null; // Lógico
								} else {
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
									setCasilla(co2.getX(), co2.getY(), pieza1);// Gráfico
									t1[co2.getX()][co2.getY()] = pieza1;
									t1[co1.getX()][co1.getY()] = null; // Lógico
									t1[7][3] = getPieza(7, 0); // Lógico
									setCasilla(7, 3, getPieza(7, 0));// Gráfico
									t1[7][0] = null; // Lógico
								} else {
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
							setCasilla(co2.getX(), co2.getY(), pieza1);// Gráfico
							borrarCasilla(co1.getX(), co1.getY());// Logico
							t1[co2.getX()][co2.getY()] = pieza1;
							t1[co1.getX()][co1.getY()] = null;
						}
					} else {
						setCasilla(co2.getX(), co2.getY(), pieza1);// Gráfico
						borrarCasilla(co1.getX(), co1.getY());// Logico
						t1[co2.getX()][co2.getY()] = pieza1;
						t1[co1.getX()][co1.getY()] = null;
					}
					if (permiteMovimientoPeon) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " piezasEnPassant.size(): " + piezasEnPassant.size());
						if (null != piezasEnPassant
								&& piezasEnPassant.size() > 0) {
							if (pieza1.baja) {
								System.err.println(Thread.currentThread().getStackTrace()[2] + " borrar casilla izq 1 if");
								borrarCasilla(co2.getX() - 1, co2.getY());
							} else {
								System.err.println(Thread.currentThread().getStackTrace()[2] + " borrar casilla der 2 else");
								borrarCasilla(co2.getX() + 1, co2.getY());
							}
							piezasEnPassant.clear();
						} else {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " Llama a poner en passant");
							ponerEnPassant(new Posicion(co2.getX(), co2.getY()));
						}
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
										System.err.println(Thread.currentThread().getStackTrace()[2] + " Eligio D");
										t1[co2.getX()][co2.getY()] = new Dama(nextColor(turnoColor));
										t[co2.getX()][co2.getY()] = "D" + (nextColor(turnoColor).toString().contains("N") ? "N": "B");
									}
									if (piezaElegida.equals("T")) {
										System.err.println(Thread.currentThread().getStackTrace()[2] + " Eligio T");
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
										System.err.println(Thread.currentThread().getStackTrace()[2] + " Eligio C");
										t1[co2.getX()][co2.getY()] = new Caballo(
												nextColor(turnoColor));
										t[co2.getX()][co2.getY()] = "C" + (nextColor(turnoColor).toString().contains("N") ? "N": "B");
										setCasilla(co2.getX(), co2.getY(), t1[co2.getX()][co2.getY()]);// Gráfico
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					if (promocionPeon) {
						Posicion posFin = new Posicion(co2.getX(), co2.getY());
						if (evaluarJaque(getPieza(co2.getX(), co2.getY()).getMovimientos(posFin,t1), getPieza(co2.getX(), co2.getY()))) {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " El sistema detectó JAQUE en su rey por favor muévalo o cubra al Rey.");
							enJaque = true;
						} else {
							enJaque = false;
						}
					}
					// if(evaluarJaque(pieza1.getMovimientos(tmpPos,t1), pieza1) &&
					// !promocionPeon) {//Posicion final del movimiento
					// System.err.println(Thread.currentThread().getStackTrace()[2]
					// +
					// " El sistema detectó JAQUE en su rey por favor muévalo o cubra al Rey.");
					// enJaque = true;
					// } else {
					// enJaque = false;
					// }
					if (pieza1.toString().contains("P")|| pieza1.toString().contains("R"))
						pieza1.setPosicionInicial(false);// Peon o Rey ya no
															// esta en posicion
															// Inicial
					promocionPeon = false;
					if (enJaque) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " evaluar Jaque Mate ya que esta en Jaque.");
						// if(evaluarMate(pieza1.getMovimientos(tmpPos,t1), pieza1)
						// && enJaque) {//Posicion final del movimiento
						if (esJaqueMate(pieza1.getMovimientos(tmpPos,t1), pieza1, co2)) {// Posicion final del movimiento
							// System.err.println(Thread.currentThread().getStackTrace()[2]
							// + "807:El sistema detectó MATE.");
							enMate = true;
							gameOver = true;
						} else {
							enMate = false;
						}
						// System.err.println(Thread.currentThread().getStackTrace()[2]
						// + "858: EsMate: " + enMate);
					}
					// } else {
					// //enJaque = false;
					// //
					// System.err.println(Thread.currentThread().getStackTrace()[2]
					// + "816:El sistema...");
					// //poner el caso de tapar la jugada, mover rey y quitar la
					// pieza jaqueadora
					// System.err.println(Thread.currentThread().getStackTrace()[2]
					// + "818:El sistema detectó Jaque.");
					// //evaluarJaque(piezasJaqueadoras.get(0).getMovimientos(encontrarPieza(piezasJaqueadoras.get(0))), // pieza1);
					// if(!enJaque)
					// piezasJaqueadoras.clear();
				} else {
					if ("P".equals(pieza1.getNombre()) && permiteMovimientoPeon) {
						// if (pieza1.baja) {
						System.err.println(Thread.currentThread().getStackTrace()[2] + " borrar casilla izq 1 if");
						// setCasilla(co2.getX(), co2.getY(), pieza1);//Gráfico
						// borrarCasilla(co1.getX(), co1.getY());//Logico
						// t1[co2.getX()][co2.getY()] = pieza1;
						// t1[co1.getX()][co1.getY()] = null;
						// } else {
						// System.err.println(Thread.currentThread().getStackTrace()[2]
						// + "849: borrar casilla der 2 else");
						// setCasilla(co2.getX(), co2.getY(), pieza1);//Gráfico
						// borrarCasilla(co1.getX(), co1.getY());//Logico
						// t1[co2.getX()][co2.getY()] = pieza1;
						// t1[co1.getX()][co1.getY()] = null;
						// }
					} else {
						// else de if(permite && !enJaque){
						if (esJaqueMate(pieza1.getMovimientos(tmpPos,t1), pieza1, co2)) {// Posicion final del movimiento
							System.err.println(Thread.currentThread().getStackTrace()[2] + " El sistema detectó MATE.");
							enMate = true;
							gameOver = true;
						} else {
							System.err.println(Thread.currentThread().getStackTrace()[2] + " El sistema no detectó JAQUE.");
							enJaque = false;
							enMate = false;
						}
					}
				}
			} else {
				System.err.println(Thread.currentThread().getStackTrace()[2] + " En la casilla de origen no hay pieza a mover");
			}
		} else {
			System.err.println(Thread.currentThread().getStackTrace()[2] + " Debe seleccionar una pieza para mover.");
		}
		return true;
	}

	private boolean validarEnroque(Coordenada co1, Coordenada co2) {
		boolean result = false;
		if (co2.getY() > co1.getY()) { // Corto
			if (null == getPieza(co1.getX(), co1.getY() + 1)) {
				if (null == getPieza(co1.getX(), co1.getY() + 2)) {
					result = true;
				} else {
					result = false;
				}
			} else {
				result = false;
			}
		} else { // Largo
			if (null == getPieza(co1.getX(), co1.getY() - 1)) {
				if (null == getPieza(co1.getX(), co1.getY() - 2)) {
					if (null == getPieza(co1.getX(), co1.getY() - 3)) {
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
	 * Lista de posiciones Pieza que hace el jaque
	 */
	private boolean evaluarJaque(List<Posicion> posiciones, Pieza p) {
		boolean result = false;
		Posicion posRey = encontrarRey(p.getColor());// buscar rey contrario
		System.err.println(Thread.currentThread().getStackTrace()[2] + " evaluarJaque / Rey buscado de color: " + nextColor(p.getColor()));
		// System.err.println(Thread.currentThread().getStackTrace()[2] +
		// "940:Posicion del rey contrario-> x:" + posRey.getX() + " / pos y: "
		// + posRey.getY());
		for (Iterator iterator = posiciones.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			// System.err.println(Thread.currentThread().getStackTrace()[2] +
			// "494:Posicion que esta siendo evaluada: (" +
			// posicionActual.getX() + ", " + posicionActual.getY() + ")");
			if (posRey.compareTo(posicionActual) == 0) {
				llenarPiezasJaqueadoras(p);
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
	 * Lista de posiciones Pieza que hace jaque
	 */

	private boolean evaluarMate(List<Posicion> posiciones, Pieza p) {
		boolean result = false;
		Pieza tmpPieza = null;
		String tmpArriba = p.getColor().toString().equals("WHITE") ? "B" : "N";
		Posicion posRey = new Posicion(0, 0);
		// posRey = encontrarRey(p.getColor());
		posRey = encontrarPieza(p);
		List<Posicion> movimientoReyActual = new ArrayList<Posicion>(); // p.getMovimientos(posRey);
		List<Posicion> movimientosRey = new ArrayList<Posicion>();
		movimientosRey.addAll(p.getMovimientos(posRey,t1));
		for (Iterator iterator = movimientosRey.iterator(); iterator.hasNext();) {
			Posicion posicionActual = (Posicion) iterator.next();
			System.err.println(Thread.currentThread().getStackTrace()[2] + " Pos actual Rey: " + posicionActual.toString());
			movimientoReyActual.add(posicionActual);
			if (evaluarJaque(movimientoReyActual, p)) {
				// movimientosRey.remove();
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}

	/*
	 * 
	 * String strNombrePieza = getPieza(piezasJaqueadoras.get(0));
	 * if(strNombrePieza.contains("P")) tmpPieza = new
	 * Peon(nextColor(turnoColor), (strNombrePieza.contains("B") &&
	 * tmpArriba.contains("B")), false, false, false); else
	 * if(strNombrePieza.contains("A")) tmpPieza = new
	 * Alfil(nextColor(turnoColor)); else if(strNombrePieza.contains("C"))
	 * tmpPieza = new Caballo(nextColor(turnoColor)); else
	 * if(strNombrePieza.contains("T")) tmpPieza = new
	 * Torre(nextColor(turnoColor)); else if(strNombrePieza.contains("D"))
	 * tmpPieza = new Dama(nextColor(turnoColor)); //Evaluar primero que el rey
	 * tiene movimientos disponibles //La pieza desde la posicion actual tiene
	 * disponible el movimiento hasta la posicion actual del rey if( ) {
	 * 
	 * } else {
	 * 
	 * }
	 */
	private Posicion encontrarRey(Pieza.Color color) {
		Posicion resultPos = null;
		Rey rey = new Rey(nextColor(color), false, true);
		System.err.println(Thread.currentThread().getStackTrace()[2] + " El rey: " + rey.toString() + " es el buscado...");
		for (int i = 0; i < ChessRules.SIZE; i++) {
			if (null == resultPos) {
				for (int j = 0; j < ChessRules.SIZE; j++) {
					if (null != getPieza(i, j)) {
						if (getPieza(i, j).toString().equals(rey.toString())) {
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
			if (null == resultPos) {
				for (int j = 0; j < ChessRules.SIZE; j++) {
					if (null != getPieza(i, j)) {
						// System.err.println(Thread.currentThread().getStackTrace()[2]
						// + "531:La pieza en la posicion actual es: " +
						// getPieza(i, j) + " La pieza buscada es: " +
						// (p.getNombre()+
						// (p.getColor().equals("WHITE")?"B":"N")));
						if (getPieza(i, j).toString().equals((p.getNombre() + (p.getColor().equals(
										"WHITE") ? "B" : "N")))) {
							resultPos = new Posicion(i, j);
							// System.err.println(Thread.currentThread().getStackTrace()[2]
							// + "968:La pieza : " + getPieza(i, j) +
							// " se encuentra en (i,j): (" + i + "," + j + ")");
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

	public Coordenada devolverCoordenada(String casilla) {
		Coordenada c = new Coordenada();
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (tEquivalenteNatural[i][j].equals(casilla)) {
					c.setX(i);
					c.setY(j);
					break;
				}
			}
		}
		System.err.println(Thread.currentThread().getStackTrace()[2] + " La casilla devuelta es : (" + c.getX() + " , " + c.getY() + ")");
		return c;
	}

	public class Coordenada {
		int x, y;

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getX() {
			return this.x;
		}

		public int getY() {
			return this.y;
		}
	}

	public String getNombre(Pieza pieza) {
		if (pieza == null) {
			return "";
		}
		return pieza.toString();
	}

	public void ponerEnPassant(Posicion posPeon) {
		String turnoContrario = nextColor(turnoColor).toString().contains("N") ? "N"
				: "B";
		int fila = getPieza(posPeon.getX(), posPeon.getY()).baja ? 3 : 4;
		System.err.println(Thread.currentThread().getStackTrace()[2] + "***** Entrando a poner enPassant. Fila: " + fila + " / turno: " + turnoContrario + " / pos peon: (" + posPeon.getX() + ", " + posPeon.getY() + ")");
		if (null != getPieza(posPeon.getX(), posPeon.getY())
				&& getPieza(posPeon.getX(), posPeon.getY()).saltoDos) {
			System.err.println(Thread.currentThread().getStackTrace()[2] + "***** ponerEnPassant / peon SaltoDos: "); // +
																	// getPieza(posPeon.getX(), // posPeon.getY()).saltoDos);
			if (null != getPieza(fila, posPeon.getY() + 1)
					&& getPieza(fila, posPeon.getY() + 1).toString().contains(
							"P") /*
								 * && getPieza(fila, posPeon.getY() + 1
								 * ).toString().contains(turnoContrario)
								 */) {
				if (null != getPieza(posPeon.getX(), posPeon.getY() + 1)) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + "586:Poniendo el peon (" + posPeon.getX() + "," + (posPeon.getY() + 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() + 1] + ")");
					System.err.println(Thread.currentThread().getStackTrace()[2] + "P: " + getPieza(fila, posPeon.getY() + 1).toString());
				}
				System.err.println(Thread.currentThread().getStackTrace()[2] + "**** Poniendo el peon (" + posPeon.getX() + "," + (posPeon.getY() + 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() + 1] + ")");
				// getPieza(fila, (posPeon.getY() + 1) ).enPassant = true;
				piezasEnPassant.add(getPieza(fila, posPeon.getY() + 1));
				System.err.println(Thread.currentThread().getStackTrace()[2] + "**** piezasEnPassant: " + piezasEnPassant.get(0) + " / size: " + piezasEnPassant.size());
			} else if (null != getPieza(fila, posPeon.getY() - 1)
					&& getPieza(fila, posPeon.getY() - 1).toString().contains(
							"P") /*
								 * && getPieza(fila, posPeon.getY() - 1
								 * ).toString().contains(turnoContrario)
								 */) {
				// getPieza(fila, (posPeon.getY() - 1) ).enPassant = true;
				if (null != getPieza(posPeon.getX(), posPeon.getY() - 1)) {
					System.err.println(Thread.currentThread().getStackTrace()[2] + "587:Poniendo el peon (" + posPeon.getX() + "," + (posPeon.getY() - 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() - 1] + ")");
					System.err.println(Thread.currentThread().getStackTrace()[2] + "P: " + getPieza(fila, posPeon.getY() - 1).toString());
				}
				System.err.println(Thread.currentThread().getStackTrace()[2] + "**** Poniendo el peon (" + fila + "," + (posPeon.getY() - 1) + ") (" + tEquivalenteNatural[posPeon.getX()][posPeon.getY() - 1] + ")" + " / pieza: " + getPieza(fila, posPeon.getY() - 1));
				piezasEnPassant.add(getPieza(fila, posPeon.getY() - 1));
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

	public boolean esJaqueMate(List<Posicion> posiciones, Pieza p, Coordenada co) { // player, // opp
		boolean result = false;
		// Posicion del Rey
		Posicion posRey = encontrarRey(p.getColor());
		/* Traer todos los movimientos posibles del Rey */
		// List<Posicion> movimientoReyActual = new ArrayList<Posicion>();

		List<Posicion> movimientosRey = new ArrayList<Posicion>();
		List<Posicion> movimientosReyAux = new ArrayList<Posicion>();

		movimientosRey.addAll(t1[posRey.getX()][posRey.getY()].getMovimientos(posRey,t1));

		piezasJaqueadoras.clear();
		// Encontrar todas las piezas que atacan al Rey
		// Lista de piezas que dan Jaque
		llenarPiezasJaqueadoras(p);
		for (Pieza pieza : piezasJaqueadoras) {
			Posicion pos = encontrarPieza(pieza);
			if (movimientosRey.contains(pos)) {
				int ubicacion = movimientosRey.indexOf(pos);
				movimientosRey.remove(ubicacion);
			}
		}
		boolean isReySolo = false;
		int[] posicionesABorrar = new int[movimientosRey.size()];
		int k = 0;
		if (movimientosRey.size() > 0) {
			movimientosReyAux = movimientosRey;
			for (Iterator iterator = movimientosReyAux.iterator(); iterator.hasNext();) {
				Posicion posicion = (Posicion) iterator.next();
				System.err.println(Thread.currentThread().getStackTrace()[2] + " Pos x: " + posicion.getX() + " / pos Y: " + posicion.getY());
				if (null != t1[posicion.getX()][posicion.getY()]
						&& t1[posRey.getX()][posRey.getY()].getColor() != p.getColor()) {
					int ubicacion = movimientosReyAux.indexOf(posicion);
					if (ubicacion != -1) {
						posicionesABorrar[k] = ubicacion;
						System.err.println(Thread.currentThread().getStackTrace()[2] + " Posicion a borrar en: " + ubicacion + " / movRey: " + movimientosRey.get(ubicacion));
						k++;
					}
				}
			}
			for (int i = 0; i < k; i++) {
				System.err.println(Thread.currentThread().getStackTrace()[2] + " Posiciones a borrar: " + posicionesABorrar[i]);
			}
			System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimientos rey size (antes): " + movimientosRey.size());
			for (int i = k - 1; i >= 0; i--) {
				movimientosRey.remove(posicionesABorrar[i]);
			}
			System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimientos rey size (despues): " + movimientosRey.size());
			for (Iterator iterator = movimientosRey.iterator(); iterator.hasNext();) {
				Posicion posIcion = (Posicion) iterator.next();
				System.err.println(Thread.currentThread().getStackTrace()[2] + " Posiciones restantes para el rey: " + posIcion.toString());
			}
			posicionesABorrar = new int[movimientosRey.size()];
			k = 0;
			if (movimientosRey.size() > 0) {
				// volver a chequear si esos movimientos que quedan no estan
				// tambien en jaque
				if (piezasJaqueadoras.size() > 0) {
					for (Pieza pieza : piezasJaqueadoras) {
						for (Iterator iterator2 = movimientosRey.iterator(); iterator2.hasNext();) {
							Posicion posicionRey = (Posicion) iterator2.next();
							System.err.println(Thread.currentThread().getStackTrace()[2] + " Pos REY: " + posicionRey.toString());
							for (Iterator iterator = pieza.getMovimientos(encontrarPieza(pieza),t1).iterator(); iterator.hasNext();) {
								Posicion posPieza = (Posicion) iterator.next();
								if (posPieza.compareTo(posicionRey) == 0) {
									int ubicacion = movimientosRey.indexOf(posicionRey);
									posicionesABorrar[k] = ubicacion;
									k++;
									System.err.println(Thread.currentThread().getStackTrace()[2] + " Encontre una posicion para borrar: " + ubicacion + " / posRey: " + posicionRey.toString() + " / K: " + k);
									break;
								}
							}
						}
					}
				}
			}
			System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimientos rey size (antes): " + movimientosRey.size());
			for (int i = k - 1; i >= 0; i--) {
				movimientosRey.remove(posicionesABorrar[i]);
			}
			System.err.println(Thread.currentThread().getStackTrace()[2] + " Movimientos rey size (despues): " + movimientosRey.size());

			for (Iterator iterator = movimientosRey.iterator(); iterator.hasNext();) {
				Posicion pos = (Posicion) iterator.next();
				System.err.println(Thread.currentThread().getStackTrace()[2] + " Posiciones finales del Rey: " + pos.toString());
			}
			if (movimientosRey.size() > 0) {
				// El rey puede salir solo del jaque
				isReySolo = true;
			} else {
				isReySolo = false;
			}
		}

		System.err.println(Thread.currentThread().getStackTrace()[2] + " Rey solo: " + isReySolo);
		if (isReySolo)
			return false;

		boolean isPiezaCubre = false;

		casillasAtacadas = p.getMovimientosConDireccion(encontrarPieza(p), posRey,t1);
		Posicion posVaPieza = new Posicion(co.getX(), co.getY());
		if (null != casillasAtacadas && casillasAtacadas.contains(posVaPieza)) {
			isPiezaCubre = true;
			enJaque = false;// revisar este caso
		} else
			isPiezaCubre = false;

		if (isReySolo || isPiezaCubre)
			result = false;
		else
			result = true;

		return result;
	}

	/*
	 * 
	 * Recibe la pieza Rey como parametro. Encontrar todas las piezas que atacan
	 * al Rey.
	 */

	public void llenarPiezasJaqueadoras(Pieza p) {
		if (!piezasJaqueadoras.contains(p))
			piezasJaqueadoras.add(p);
		System.err.println(Thread.currentThread().getStackTrace()[2] + " llenarPiezasJaqueadoras / Piezas Jaquedoras tamaño: " + piezasJaqueadoras.size());
	}

	public String colorALetraInicial(Pieza.Color color) {
		return color.toString().equals("BLACK") ? "N" : "B";
	}

	// p1: Posicion del rey
	// p2: Posicion de la pieza que da jaque
	public String mostrarCaminoABloquear(Posicion destino, Posicion origen) {
		String result = "";
		int xDest = destino.getX();
		int yDest = destino.getY();
		int xOrig = origen.getX();
		int yOrig = origen.getY();

		result = (xDest - xOrig > 0) ? "1" : (xDest - xOrig == 0) ? "2"
				: (xDest - xOrig < 0) ? "3" : "";
		result += (yDest - yOrig > 0) ? "4" : (yDest - yOrig == 0) ? "5"
				: (yDest - yOrig < 0) ? "6" : "";

		return result;
	}

	/*
	 * retorna: 1 si la pieza que esta mas arriba es la 1 retorna: 2 si la pieza
	 * que esta mas arriba es la 2
	 */
	public String piezaUbicacionRespectoAOtra(Posicion destino, Posicion origen) {
		return (destino.getX() - origen.getX()) < 0 ? "1" : "2";
	}

	public void llenarCasillasAtacadas() {
		for (int i = 0; i < ChessRules.SIZE; i++) {
			for (int j = 0; j < ChessRules.SIZE; j++) {
				Posicion posActual = new Posicion(i, j);
				if (null != t1[i][j]) {
					if (t1[i][j].getColor().toString().equals("WHITE")) {
						casillasAtacadasXBlanco.addAll(t1[i][j].getMovimientos(posActual,t1));
					} else {
						casillasAtacadasXNegro.addAll(t1[i][j].getMovimientos(posActual,t1));
					}
				}
			}
		}
	}
}
