import java.util.LinkedList;
import java.util.List;


public class Dama extends Pieza{

	protected Dama(Color color) {
		super("D", color, 10);
	}

	@Override
	public List<Posicion> getMovimientos(Posicion from, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();

		String colorPiezaActual = t[from.getX()][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
		System.err.println("Iniciando movimientos de Dama de color: " + colorPiezaActual);
		//Diagonal hacia la izquierda arriba
		for (int x = from.getX(), y = from.getY(); x < ChessRules.SIZE && y >= 0; x++, y--) {
			if (x == from.getX() && y == from.getY()) continue;
			if(null != t[x][y]) {
				String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Dama Diagonal hacia la izquierda arriba) Agrego la posicion: (" + x + "," + y +")");
					movimientos.add(new Posicion(x, y));
					break;
				}
			} else {
				System.err.println("(Dama Diagonal hacia la izquierda arriba) Agrego la posicion: (" + x + "," + y +")");
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
					System.err.println("(Dama Diagonal hacia la izquierda abajo) Agrego la posicion: (" + x + "," + y +")");
					movimientos.add(new Posicion(x, y));
					break;
				}
			} else {
				System.err.println("(Dama Diagonal hacia la izquierda abajo) Agrego la posicion: (" + x + "," + y +")");
				movimientos.add(new Posicion(x, y));
			}
		}
		//Diagonal hacia la derecha abajo
		for (int x = from.getX(), y = from.getY(); x < ChessRules.SIZE && y < ChessRules.SIZE; x++, y++) {
			if (x == from.getX() && y == from.getY()) continue;
			if(null != t[x][y]) {
				String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Dama Diagonal hacia la diagonal derecha abajo) Agrego la posicion: (" + x + "," + y +")");
					movimientos.add(new Posicion(x, y));
					break;
				}
			} else {
				System.err.println("(Dama Diagonal hacia la diagonal derecha abajo) Agrego la posicion: (" + x + "," + y +")");
				movimientos.add(new Posicion(x, y));
			}
		}		
		//Diagonal hacia la derecha arriba
		for (int x = from.getX(), y = from.getY(); x >= 0 && y < ChessRules.SIZE; x--, y++) {
			if (x == from.getX() && y == from.getY()) continue;
			if(null != t[x][y]) {
				String colorPiezaTemp = t[x][y].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Dama Diagonal hacia la diagonal derecha arriba) Agrego la posicion: (" + x + "," + y +")");
					movimientos.add(new Posicion(x, y));
					break;
				}
			} else {
				System.err.println("(Dama Diagonal hacia la diagonal derecha arriba) Agrego la posicion: (" + x + "," + y +")");
				movimientos.add(new Posicion(x, y));
			}
		}
		//Vertical hacia arriba
		for (int x = from.getX(); x >= 0; x--) {
			if (x == from.getX()) continue;
			if(null != t[x][from.getY()]) {
				String colorPiezaTemp = t[x][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
					break;
				} else {
					System.err.println("(Dama Vertical hacia arriba) Agrego la posicion: (" + x + "," + from.getY() +")");
					movimientos.add(new Posicion(x, from.getY()));
					break;
				}
			} else {
				System.err.println("(Dama Vertical hacia arriba) Agrego la posicion: (" + x + "," + from.getY() +")");
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
					System.err.println("(Dama Vertical hacia abajo) Agrego la posicion: (" + x + "," + from.getY() +")");
					movimientos.add(new Posicion(x, from.getY()));
					break;
				}
			} else {
				System.err.println("(Dama Vertical hacia abajo) Agrego la posicion: (" + x + "," + from.getY() +")");
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
					System.err.println("(Dama Horizontal izquierda) Agrego la posicion: (" + from.getX() + "," + y +")");
					movimientos.add(new Posicion(from.getX(), y));
					break;
				}
			} else {
				System.err.println("(Dama Horizontal izquierda) Agrego la posicion: (" + from.getX() + "," + y +")");
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
					System.err.println("(Dama Horizontal izquierda) Agrego la posicion: (" + from.getX() + "," + y +")");
					movimientos.add(new Posicion(from.getX(), y));
					break;
				}
			} else {
				System.err.println("(Dama Horizontal izquierda) Agrego la posicion: (" + from.getX() + "," + y +")");
				movimientos.add(new Posicion(from.getX(), y));
			}
		}
		System.err.println("Finalizando movimientos de Dama de color: " + colorPiezaActual);
		return movimientos;
	}

//	@Override
	public List<Posicion> getMovimientos1(Posicion from, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();

		String colorPiezaActual = t[from.getX()][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
		// Movimiento horizontal
		for (int x = 0; x < ChessRules.SIZE; x++) {
			if(null == t[x][from.getY()]) {
				System.err.println("Agregando movimiento de Dama: (" + x +","+ from.getY() +")");
				movimientos.add(new Posicion(x, from.getY()));
			} else {
				String colorPiezaTemp = t[x][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp) && from.getX() == x) {
					break;
				} else {
					System.err.println("Agregando movimiento de Dama: (" + x +","+ from.getY() +")");
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
//			System.err.println(Thread.currentThread().getStackTrace()[2] + " PosActual: (" + from.getX() + ", " + y + ")" );
			if(null == t[from.getX()][y]) {
				System.err.println("Agregando movimiento de Dama: (" + from.getX() +","+ y +")");
				movimientos.add(new Posicion(from.getX(), y));
			} else {
				String colorPiezaTemp = t[from.getX()][y].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)/* && from.getY() == y*/) {
					break;
				} else {
					System.err.println("Agregando movimiento de Dama: (" + from.getX() +","+ y +")");
					movimientos.add(new Posicion(from.getX(), y));
					break;
				}
			}
		}

		// De izquierda a derecha
		int y = from.getX() - from.getY();
		for (int x = 0; x < ChessRules.SIZE; x++) {
			if (x < ChessRules.SIZE && y > 0) {
//				System.err.println(Thread.currentThread().getStackTrace()[2] + " PosActual: (" + x + ", " + y + ")" );
				if(null == t[y][x]) {
					System.err.println("Agregando movimiento de Dama: (" + y +","+ x +")");
					movimientos.add(new Posicion(y, x));
				} else {
					String colorPiezaTemp = t[y][x].getColor().toString().equals("BLACK")?"N":"B";
					if (colorPiezaActual.equals(colorPiezaTemp)) {
						break;
					} else {
						System.err.println("Agregando movimiento de Dama: (" + y +","+ x +")");
						movimientos.add(new Posicion(y, x));
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
					System.err.println("Agregando movimiento de Dama: (" + x +","+ yy +")");
					movimientos.add(new Posicion(x, yy));
				} else {
					String colorPiezaTemp = t[x][yy].getColor().toString().equals("BLACK")?"N":"B";
					if (colorPiezaActual.equals(colorPiezaTemp)) {
						break;
					} else {
						System.err.println("Agregando movimiento de Dama: (" + x +","+ yy +")");
						movimientos.add(new Posicion(x, yy));
						break;
					}
				}
			}
			x++;
		}
		
		return movimientos;
	}

	public List<Posicion> getMovimientosConDireccion(Posicion from, Posicion dest, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();

		// Movimiento horizontal
		for (int x = 0; x < ChessRules.SIZE; x++) {
			if (x == from.getX()) {
				continue;
			}
//			System.out.println("20:Agregando-> x:" + x + " / y: " + from.getY());
			movimientos.add(new Posicion(x, from.getY()));
		}
		if(movimientos.contains(dest))
			return movimientos;
		
		movimientos.clear();
		// Movimiento vertical
		for (int y = 0; y < ChessRules.SIZE; y++) {
			if (y == from.getY()) {
				continue;
			}
			movimientos.add(new Posicion(from.getX(), y));
//			System.out.println("29:Agregando-> x:" + from.getX() + " / y: " + y);
		}
		if(movimientos.contains(dest))
			return movimientos;
		
		movimientos.clear();
		// Movimientos en diagonal
		for (int avance = 0; (from.getY() + avance) < ChessRules.SIZE ; avance++) {//&& (from.getY() + avance) < ChessRules.SIZE
//			System.out.println("34:Agregando-> x:" + (from.getX()+avance) + " / y: " + (from.getY() + avance));
			movimientos.add(new Posicion(from.getX() + avance, from.getY() + avance));
		}
		if(movimientos.contains(dest))
			return movimientos;
		
		movimientos.clear();
		for (int avance = 0; (from.getX() + avance) >= 0 && (from.getY() + avance) >= 0; avance--) {
//			System.out.println("38:Agregando-> x:" + (from.getX()+avance) + " / y: " + (from.getY() + avance));
			movimientos.add(new Posicion(from.getX() + avance, from.getY() + avance));
		}
		
		return movimientos;
	}
//	public boolean esValido(int x, int y) {
//		return x == getPosicionX() || y == getPosicionY() || Math.abs(x - getPosicionX()) == Math.abs(y - getPosicionY());
//	}
}
