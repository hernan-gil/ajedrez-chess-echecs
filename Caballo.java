import java.util.LinkedList;
import java.util.List;


public class Caballo extends Pieza {

	protected Caballo(Color color) {
		super("C", color, 3);
	}

	@Override
	public List<Posicion> getMovimientos(Posicion from, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();
		String colorPiezaActual = t[from.getX()][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
		System.out.println("Iniciando movimientos de Caballo de color: " + colorPiezaActual);
		int x, y;
		x = from.getX() + 2;
		if (x < ChessRules.SIZE && x >= 0) {
			y = from.getY() + 1;
			if (y < ChessRules.SIZE && y >= 0) {
				if(null == t[x][y]) {
					movimientos.add(new Posicion(x, y));
					System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
				} else {
					String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
					if (!colorPiezaActual.equals(colorPiezaTemp)) {
						System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
						movimientos.add(new Posicion(x, y));
					}
				}
			}
			y = from.getY() - 1;
			if (y >= 0) {
				if(null == t[x][y]) {
					movimientos.add(new Posicion(x, y));
					System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
				} else {
					String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
					if (!colorPiezaActual.equals(colorPiezaTemp)) {
						System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
						movimientos.add(new Posicion(x, y));
					}
				}
			}
		}
		
		x = from.getX() - 2;
		if (x >= 0) {
			y = from.getY() + 1;
			if (y < ChessRules.SIZE && y >= 0) {
				if(null == t[x][y]) {
					System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
					movimientos.add(new Posicion(x, y));
				} else {
					String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
					if (!colorPiezaActual.equals(colorPiezaTemp)) {
						System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
						movimientos.add(new Posicion(x, y));
					}
				}
			}
			y = from.getY() - 1;
			if (y >= 0) {
				if(null == t[x][y]) {
					System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
					movimientos.add(new Posicion(x, y));
				} else {
					String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
					if (!colorPiezaActual.equals(colorPiezaTemp)) {
						System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
						movimientos.add(new Posicion(x, y));
					}
				}
			}
		}

		y = from.getY() + 2;
		if (y < ChessRules.SIZE && y >= 0) {
			x = from.getX() + 1;
			if (x < ChessRules.SIZE && x >= 0) {
				if(null == t[x][y]) {
					System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
					movimientos.add(new Posicion(x, y));
				} else {
					String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
					if (!colorPiezaActual.equals(colorPiezaTemp)) {
						System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
						movimientos.add(new Posicion(x, y));
					}
				}
			}
			x = from.getX() - 1;
			if (x >= 0) {
				if(null == t[x][y]) {
					System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
					movimientos.add(new Posicion(x, y));
				} else {
					String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
					if (!colorPiezaActual.equals(colorPiezaTemp)) {
						System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
						movimientos.add(new Posicion(x, y));
					}
				}
			}
		}

		y = from.getY() - 2;
		if (y >= 0) {
			x = from.getX() + 1;
			if (x < ChessRules.SIZE && x >= 0) {
				if(null == t[x][y]) {
					System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
					movimientos.add(new Posicion(x, y));
				} else {
					String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
					if (!colorPiezaActual.equals(colorPiezaTemp)) {
						System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
						movimientos.add(new Posicion(x, y));
					}
				}
			}
			x = from.getX() - 1;
			if (x >= 0) {
				if(null == t[x][y]) {
					System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
					movimientos.add(new Posicion(x, y));
				} else {
					String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
					if (!colorPiezaActual.equals(colorPiezaTemp)) {
						System.err.println("Agregando movimiento de Caballo: (" + x +","+ y +")");
						movimientos.add(new Posicion(x, y));
					}
				}
			}
		}
		System.out.println("Finalizando movimientos de Caballo de color: " + colorPiezaActual);
		return movimientos;
	}

	@Override
	public List<Posicion> getMovimientosConDireccion(Posicion from, Posicion dest, Pieza[][] t) {
		return null;
	}

	
//	public boolean esValido(int x, int y) {
//		int deltaX = Math.abs(x - getPosicionX());
//		int deltaY = Math.abs(y - getPosicionY());
//		return deltaX == deltaY * 2 || 2 * deltaX == deltaY; 
//	}

}
