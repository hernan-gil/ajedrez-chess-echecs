import java.util.LinkedList;
import java.util.List;

public class Torre extends Pieza {

	protected Torre(Color color) {
		super("T", color, 5);
	}
	
	@Override
	public List<Posicion> getMovimientos(Posicion from, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();
		String colorPiezaActual = t[from.getX()][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
		//Vertical hacia arriba
		for (int x = from.getX(); x >= 0; x--) {
			if (x == from.getX()) continue;
			if(null != t[x][from.getY()]) {
				String colorPiezaTemp = t[x][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Torre Vertical hacia arriba) Agrego la posicion: (" + x + "," + from.getY() +")");
					movimientos.add(new Posicion(x, from.getY()));
					break;
				}
			} else {
				System.err.println("(Torre Vertical hacia arriba) Agrego la posicion: (" + x + "," + from.getY() +")");
				movimientos.add(new Posicion(x, from.getY()));
			}
		}
		//Vertical hacia abajo
		for (int x = from.getX(); x < ChessRules.SIZE; x++) {
			if (x == from.getX()) continue;
			if(null != t[x][from.getY()]) {
				String colorPiezaTemp = t[x][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Torre Vertical hacia abajo) Agrego la posicion: (" + x + "," + from.getY() +")");
					movimientos.add(new Posicion(x, from.getY()));
					break;
				}
			} else {
				System.err.println("(Torre Vertical hacia abajo) Agrego la posicion: (" + x + "," + from.getY() +")");
				movimientos.add(new Posicion(x, from.getY()));
			}
		}
		//Horizontal izquierda
		for (int y = from.getY(); y >= 0; y--) {
			if (y == from.getY()) continue;
			if(null != t[from.getX()][y]) {
				String colorPiezaTemp = t[from.getX()][y].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Torre Horizontal izquierda) Agrego la posicion: (" + from.getX() + "," + y +")");
					movimientos.add(new Posicion(from.getX(), y));
					break;
				}
			} else {
				System.err.println("(Torre Horizontal izquierda) Agrego la posicion: (" + from.getX() + "," + y +")");
				movimientos.add(new Posicion(from.getX(), y));
			}
		}
		//Horizontal derecha
		for (int y = from.getY(); y < ChessRules.SIZE; y++) {
			if (y == from.getY()) continue;
			if(null != t[from.getX()][y]) {
				String colorPiezaTemp = t[from.getX()][y].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Torre Horizontal izquierda) Agrego la posicion: (" + from.getX() + "," + y +")");
					movimientos.add(new Posicion(from.getX(), y));
					break;
				}
			} else {
				System.err.println("(Torre Horizontal izquierda) Agrego la posicion: (" + from.getX() + "," + y +")");
				movimientos.add(new Posicion(from.getX(), y));
			}
		}
		System.out.println("Finalizando movimientos de Torre de color: " + colorPiezaActual);
		return movimientos;
	}
	
	public List<Posicion> getMovimientos1(Posicion from, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();
		String colorPiezaActual = t[from.getX()][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
		System.out.println("Iniciando movimientos de Torre de color: " + colorPiezaActual);
//		System.err.println("Torre / Posicion : (" + from.getX() +", " + from.getY() + ")");
		// Movimiento horizontal
		for (int x = 0; x < ChessRules.SIZE; x++) {
//			if (x == from.getX()) {
//				continue;
//			}
//			System.err.println("1 Agregando movimiento: (" + x +","+ from.getY() +")");
			if(null == t[x][from.getY()]) {
				movimientos.add(new Posicion(x, from.getY()));
			} else {
				String colorPiezaTemp = t[x][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					movimientos.add(new Posicion(x, from.getY()));
					break;
				}
			}
		}
		// Movimiento vertical
		for (int y = 0; y < ChessRules.SIZE; y++) {
//			if (y == from.getY()) {
//				continue;
//			}
//			System.err.println("2 Agregando movimiento: (" + from.getX() +","+ y +")");
			if(null == t[from.getX()][y]) {
				movimientos.add(new Posicion(from.getX(), y));
			} else {
				String colorPiezaTemp = t[from.getX()][y].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					movimientos.add(new Posicion(from.getX(), y));
					break;
				}
			}
		}
		System.out.println("Finalizando movimientos de Torre de color: " + colorPiezaActual);
		return movimientos;
	}

	public List<Posicion> getMovimientosConDireccion(Posicion from, Posicion dest, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();
//		System.err.println("Torre / Posicion : (" + from.getX() +", " + from.getY() + ")");
		// Movimiento horizontal
		for (int x = 0; x < ChessRules.SIZE; x++) {
			if (x == from.getX()) {
				continue;
			}
//			System.err.println("1 Agregando movimiento: (" + x +","+ from.getY() +")");
			if(null == t[x][from.getY()])
				movimientos.add(new Posicion(x, from.getY()));
			else
				break;
		}
		
		if (movimientos.contains(dest))
			return movimientos;
		movimientos.clear();
		
		// Movimiento vertical
		for (int y = 0; y < ChessRules.SIZE; y++) {
			if (y == from.getY()) {
				continue;
			}
//			System.err.println("2 Agregando movimiento: (" + from.getX() +","+ y +")");
			if(null == t[from.getX()][y])
				movimientos.add(new Posicion(from.getX(), y));
			else
				break;
		}
		return movimientos;
	}

//	public boolean esValido(int x, int y) {
//		return x == getPosicionX() || y == getPosicionY(); 
//	}

}
