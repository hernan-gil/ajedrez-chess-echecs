import java.util.LinkedList;
import java.util.List;


public class Alfil extends Pieza{

	protected Alfil(Color color) {
		super("A", color, 4);
	}

	@Override
	public List<Posicion> getMovimientos(Posicion from, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();
		String colorPiezaActual = t[from.getX()][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
		System.out.println("Iniciando movimientos de Alfil de color: " + colorPiezaActual);
		//Diagonal hacia la izquierda arriba
		for (int x = from.getX(), y = from.getY(); x < ChessRules.SIZE && y >= 0; x++, y--) {
			if (x == from.getX() && y == from.getY()) continue;
			if(null != t[x][y]) {
				String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Alfil Diagonal hacia la izquierda arriba) Agrego la posicion: (" + x + "," + y +")");
					movimientos.add(new Posicion(x, y));
					break;
				}
			} else {
				System.err.println("(Alfil Diagonal hacia la izquierda arriba) Agrego la posicion: (" + x + "," + y +")");
				movimientos.add(new Posicion(x, y));
			}
		}
		//Diagonal hacia la izquierda abajo
		for (int x = from.getX(), y = from.getY(); x >= 0 && y >= 0; x--, y--) {
			if (x == from.getX() && y == from.getY()) continue;
			if(null != t[x][y]) {
				String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Alfil Diagonal hacia la izquierda abajo) Agrego la posicion: (" + x + "," + y +")");
					movimientos.add(new Posicion(x, y));
					break;
				}
			} else {
				System.err.println("(Alfil Diagonal hacia la izquierda abajo) Agrego la posicion: (" + x + "," + y +")");
				movimientos.add(new Posicion(x, y));
			}
		}
		//Diagonal hacia la diagonal derecha abajo
		for (int x = from.getX(), y = from.getY(); x < ChessRules.SIZE && y < ChessRules.SIZE; x++, y++) {
			if (x == from.getX() && y == from.getY()) continue;
			if(null != t[x][y]) {
				String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Alfil Diagonal hacia la diagonal derecha abajo) Agrego la posicion: (" + x + "," + y +")");
					movimientos.add(new Posicion(x, y));
					break;
				}
			} else {
				System.err.println("(Alfil Diagonal hacia la diagonal derecha abajo) Agrego la posicion: (" + x + "," + y +")");
				movimientos.add(new Posicion(x, y));
			}
		}		
		//Diagonal hacia la diagonal derecha arriba
		for (int x = from.getX(), y = from.getY(); x >= 0 && y < ChessRules.SIZE; x--, y++) {
			if (x == from.getX() && y == from.getY()) continue;
			if(null != t[x][y]) {
				String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Alfil Diagonal hacia la diagonal derecha arriba) Agrego la posicion: (" + x + "," + y +")");
					movimientos.add(new Posicion(x, y));
					break;
				}
			} else {
				System.err.println("(Alfil Diagonal hacia la diagonal derecha arriba) Agrego la posicion: (" + x + "," + y +")");
				movimientos.add(new Posicion(x, y));
			}
		}
		System.out.println("Finalizando movimientos de Alfil de color: " + colorPiezaActual);
		return movimientos;
	}
	
	public List<Posicion> getMovimientos1(Posicion from, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();
		String colorPiezaActual = t[from.getX()][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
		System.out.println("Iniciando movimientos de Alfil de color: " + colorPiezaActual);
		// De izquierda a derecha
		int y = from.getX() - from.getY();
		for (int x = 0; x < ChessRules.SIZE; x++) {
			if (x < ChessRules.SIZE && y > 0) {
				if(null == t[y][x]) {
					System.err.println("Agregando movimiento de Alfil: (" + y +","+ x +")");
					movimientos.add(new Posicion(y, x));
				} else {
					String colorPiezaTemp = t[y][x].getColor().toString().equals("BLACK")?"N":"B";
					if (colorPiezaActual.equals(colorPiezaTemp)) {
						break;
					} else {
						movimientos.add(new Posicion(y, x));
						System.err.println("Agregando movimiento de Alfil: (" + y +","+ x +")");
						break;
					}
				}
			}
			y++;
		}
		
		// De derecha a izquierda
		int x = 0;//from.getY()-from.getY();
		for (int yy = from.getY(); yy >= 0 ; yy--) {
			if ( x >= 0 || yy < ChessRules.SIZE) {
//				System.err.println(Thread.currentThread().getStackTrace()[2] + " PosActual: (" + x + ", " + yy + ")" );
				if(null == t[x][yy]) {
					movimientos.add(new Posicion(x, yy));
					System.err.println("Agregando movimiento de Alfil: (" + x +","+ yy +")");
				} else {
					String colorPiezaTemp = t[x][yy].getColor().toString().equals("BLACK")?"N":"B";
					if (colorPiezaActual.equals(colorPiezaTemp)) {
						break;
					} else {
						System.err.println("Agregando movimiento de Alfil: (" + x +","+ yy +")");
						movimientos.add(new Posicion(x, yy));
						break;
					}
				}
			}
			x++;
		}
		
		// Movimientos en diagonal
		// + +
//		for (int avance = 1; (from.getX() + avance) < ChessRules.SIZE && (from.getY() + avance) < ChessRules.SIZE; avance++) {
//			movimientos.add(new Posicion(from.getX() + avance, from.getY() + avance));
////			System.err.println("1Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() + avance) +")");
//		}
//		// - -
//		for (int avance = -1; (from.getX() + avance) >= 0 && (from.getY() + avance) >= 0; avance--) {
//			movimientos.add(new Posicion(from.getX() + avance, from.getY() + avance));
////			System.err.println("2Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() + avance) +")");
//		}
//		// + -
//		for (int avance = 1; (from.getX() + avance) < ChessRules.SIZE && (from.getY() - avance) > 0; avance++) {
//			movimientos.add(new Posicion(from.getX() + avance, from.getY() - avance));
////			System.err.println("3Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() - avance) +")");
//		}
//		// - +
//		for (int avance = -1; (from.getX() + avance) > 0 && (from.getY() - avance) > 0; avance--) {
//			movimientos.add(new Posicion(from.getX() + avance, from.getY() - avance));
////			System.err.println("4Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() - avance) +")");
//		}
		System.out.println("Finalizando movimientos de Alfil de color: " + colorPiezaActual);
		return movimientos;
	}

	public List<Posicion> getMovimientosConDireccion(Posicion from, Posicion dest, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();
//		System.err.println("Alfil->getMovimientos de la posicion x: " + from.getX() + " / y: " + from.getY());
		// Movimientos en diagonal
		// + +
		for (int avance = 1; (from.getX() + avance) < ChessRules.SIZE && (from.getY() + avance) < ChessRules.SIZE; avance++) {
			movimientos.add(new Posicion(from.getX() + avance, from.getY() + avance));
//			System.err.println("1Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() + avance) +")");
		}
		
		if( movimientos.contains(dest))
			return movimientos;
		movimientos.clear();
		
		// - -
		for (int avance = -1; (from.getX() + avance) >= 0 && (from.getY() + avance) >= 0; avance--) {
			movimientos.add(new Posicion(from.getX() + avance, from.getY() + avance));
//			System.err.println("2Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() + avance) +")");
		}
		
		if( movimientos.contains(dest))
			return movimientos;
		movimientos.clear();

		// + -
		for (int avance = 1; (from.getX() + avance) < ChessRules.SIZE && (from.getY() - avance) > 0; avance++) {
			movimientos.add(new Posicion(from.getX() + avance, from.getY() - avance));
//			System.err.println("3Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() - avance) +")");
		}
		
		if( movimientos.contains(dest))
			return movimientos;
		movimientos.clear();
		
		// - +
		for (int avance = -1; (from.getX() + avance) > 0 && (from.getY() - avance) > 0; avance--) {
			movimientos.add(new Posicion(from.getX() + avance, from.getY() - avance));
//			System.err.println("4Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() - avance) +")");
		}
		return movimientos;
	}
	
//	public boolean esValido(int x, int y) {
//		return Math.abs(x - getPosicionX()) == Math.abs(y - getPosicionY()); 
//	}
}
