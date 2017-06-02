import java.util.List;

	/**
	 * Nombre del método:       main
	 * Descripción:             <i>Método para </i>
	 * @author                  hernan.gil
	 * @param args
	 *
	 *  MODIFICACIONES:
	 * 
	 *  Fecha:                  11/02/2016
	 *  Autor - Empresa:        hernan.gil - E.T.N. S.A.S.
	 *  Descripción:            Creación del método
	 */

public abstract class Pieza {
	private String nombre;
	private Color color;
	private int valor;
	boolean posicionInicial = true;
	boolean baja = true; 
	boolean enJaque = false; 
	boolean promovido = false;
	boolean enPassant = false;
	boolean saltoDos = false;
	
	protected Pieza(String tipo, Color color, int valor, boolean enJaque, boolean posicionInicial) {
		super();
		this.nombre = tipo;
		this.color = color;
		this.valor = valor;
		this.enJaque = enJaque;
		this.posicionInicial = posicionInicial;
	}
	
	protected Pieza(String tipo, Color color, int valor, boolean baja, boolean promovido, boolean enPassant, boolean saltoDos) {
		super();
		this.nombre = tipo;
		this.color = color;
		this.valor = valor;
		this.baja = baja;
		this.promovido = promovido;
		this.enPassant = enPassant;
		this.saltoDos = saltoDos;
	}

	protected Pieza(String tipo, Color color, int valor) {
		super();
		this.nombre = tipo;
		this.color = color;
		this.valor = valor;
	}

	public abstract List<Posicion> getMovimientos(Posicion from, Pieza[][] tablero);

	public abstract List<Posicion> getMovimientosConDireccion(Posicion from, Posicion dest, Pieza[][] tablero);
	
	public enum Color {
		BLACK, WHITE
	}

	public Color getColor() {
		return color;
	}

	public int getValor() {
		return valor;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isPosicionInicial() {
		return posicionInicial;
	}

	public void setPosicionInicial(boolean posicionInicial) {
		this.posicionInicial = posicionInicial;
	}

	public boolean isBaja() {
		return baja;
	}

	public boolean isEnJaque() {
		return enJaque;
	}

	public boolean isPromovido() {
		return promovido;
	}

	public boolean isEnPassant() {
		return enPassant;
	}

	public boolean isSaltoDos() {
		return saltoDos;
	}
	
	@Override
	public String toString() {
		return getNombre() + (getColor() == Pieza.Color.WHITE ? "B" : "N");
	}
	
	public String colorToString() {
		return getNombre() + (getColor() == Pieza.Color.WHITE ? "WHITE" : "BLACK");
	}
}