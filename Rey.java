import java.util.LinkedList;
import java.util.List;


public class Rey extends Pieza{

	protected Rey(Color color, boolean enJaque, boolean posicionInicial) {
		super("R", color, 100, enJaque, posicionInicial);
	}
	@Override
	public List<Posicion> getMovimientos(Posicion from, Pieza[][] t) {
		String colorPiezaActual = t[from.getX()][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
		System.out.println("Iniciando movimientos de Rey " + colorPiezaActual);
		List<Posicion> movimientos = new LinkedList<Posicion>();
		//izquierda arriba +-
		if (((from.getX()+1)) < ChessRules.SIZE && (from.getY()-1) >= 0) {
			if(null != t[(from.getX()+1)][from.getY()-1]) {
				String colorPiezaTemp = t[(from.getX()+1)][from.getY()-1].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
				} else {
					System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + (from.getX()+1) + "," + (from.getY()-1) +")");
					movimientos.add(new Posicion((from.getX()+1), from.getY()-1));
				}
			} else {
				System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + (from.getX()+1) + "," + (from.getY()-1) +")");
				movimientos.add(new Posicion((from.getX()+1), from.getY()-1));
			}
		}
		//Diagonal hacia la izquierda abajo --
		if (((from.getX()-1)) >= 0 && (from.getY()-1) >= 0) {
			if(null != t[(from.getX()-1)][from.getY()-1]) {
				String colorPiezaTemp = t[(from.getX()-1)][from.getY()-1].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
				} else {
					System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + ((from.getX()-1)) + "," + (from.getY()-1) +")");
					movimientos.add(new Posicion((from.getX()-1), from.getY()-1));
				}
			} else {
				System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + ((from.getX()-1)) + "," + (from.getY()-1) +")");
				movimientos.add(new Posicion((from.getX()-1), from.getY()-1));
			}
		}
		//Diagonal hacia la derecha abajo ++
		if (((from.getX()+1)) < ChessRules.SIZE && ((from.getY()+1)) < ChessRules.SIZE) {
			if(null != t[(from.getX()+1)][(from.getY()+1)]) {
				String colorPiezaTemp = t[(from.getX()+1)][(from.getY()+1)].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
				} else {
					System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + (from.getX()+1) + "," + ((from.getY()+1)) +")");
					movimientos.add(new Posicion((from.getX()+1), (from.getY()+1)));
				}
			} else {
				System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + (from.getX()+1) + "," + ((from.getY()+1)) +")");
				movimientos.add(new Posicion((from.getX()+1), (from.getY()+1)));
			}
		}
		//Diagonal hacia la derecha arriba -+
		if (((from.getX()-1)) >= 0 && ((from.getY()+1)) < ChessRules.SIZE) {
			if(null != t[(from.getX()-1)][(from.getY()+1)]) {
				String colorPiezaTemp = t[(from.getX()-1)][(from.getY()+1)].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
				} else {
					System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + ((from.getX()-1)) + "," + ((from.getY()+1)) +")");
					movimientos.add(new Posicion((from.getX()-1), (from.getY()+1)));
				}
			} else {
				System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + ((from.getX()-1)) + "," + ((from.getY()+1)) +")");
				movimientos.add(new Posicion((from.getX()-1), (from.getY()+1)));
			}
		}
		//Vertical hacia arriba  x--, y fija
		if (((from.getX()-1)) > 0) {
			if(null != t[(from.getX()-1)][from.getY()]) {
				String colorPiezaTemp = t[(from.getX()-1)][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
				} else {
					System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + ((from.getX()-1)) + "," + from.getY() +")");
					movimientos.add(new Posicion((from.getX()-1), from.getY()));
				}
			} else {
				System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + ((from.getX()-1)) + "," + from.getY() +")");
				movimientos.add(new Posicion((from.getX()-1), from.getY()));
			}
		}		
		//Vertical hacia abajo x++, y fija
		if (((from.getX()+1)) < ChessRules.SIZE) {
			if(null != t[(from.getX()+1)][from.getY()]) {
				String colorPiezaTemp = t[(from.getX()+1)][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
				} else {
					System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + (from.getX()+1) + "," + from.getY() +")");
					movimientos.add(new Posicion((from.getX()+1), from.getY()));
				}
			} else {
				System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + (from.getX()+1) + "," + from.getY() +")");
				movimientos.add(new Posicion((from.getX()+1), from.getY()));
			}
		}
		//Horizontal izquierda x fija, y--
		if ((from.getY()-1) >= 0) {
			if(null != t[from.getX()][from.getY()-1]) {
				String colorPiezaTemp = t[from.getX()][from.getY()-1].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
				} else {
					System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + from.getX() + "," + (from.getY()-1) +")");
					movimientos.add(new Posicion(from.getX(), from.getY()-1));
				}
			} else {
				System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + from.getX() + "," + (from.getY()-1) +")");
				movimientos.add(new Posicion(from.getX(), from.getY()-1));
			}
		}
		//Horizontal derecha x fija, y++
		if (((from.getY()+1)) < ChessRules.SIZE) {
			if(null != t[from.getX()][(from.getY()+1)]) {
				String colorPiezaTemp = t[from.getX()][(from.getY()+1)].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
				} else {
					System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + from.getX() + "," + (from.getY()+1) +")");
					movimientos.add(new Posicion(from.getX(), (from.getY()+1)));
				}
			} else {
				System.err.println("(Rey Diagonal hacia la izquierda arriba) Agrego la posicion: (" + from.getX() + "," + (from.getY()+1) +")");
				movimientos.add(new Posicion(from.getX(), (from.getY()+1)));
			}
		}
		System.out.println("Fin movimientos de Rey " + colorPiezaActual);
		return movimientos;
	}
	
	public List<Posicion> getMovimientos1(Posicion from, Pieza[][] t) {
		String colorPiezaActual = t[from.getX()][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
		System.out.println("Iniciando movimientos de Rey " + colorPiezaActual);
		List<Posicion> movimientos = new LinkedList<Posicion>();
		int x, y;
		for (int xDelta = -1; xDelta <= 1; xDelta++) {
			x = from.getX() + xDelta;
			for (int yDelta = -1; yDelta <= 1; yDelta++) {
				y = from.getY() + yDelta;
				if (x >= 0 && y < ChessRules.SIZE && y >= 0 && y < ChessRules.SIZE) {
					if (x == from.getX() && y == from.getY()) {
						continue;
					}
					if ( x < ChessRules.SIZE && y < ChessRules.SIZE) {
						Posicion movimiento = new Posicion(x,y);
							if(null == t[movimiento.getX()][movimiento.getY()]){
								System.err.println("Agregando movimiento de Rey: (" + x +","+ y +")");
								movimientos.add(new Posicion(x, y));
							}
					}
				}
			}
		}
		System.out.println("Fin movimientos de Rey " + colorPiezaActual);
		return movimientos;
	}

	@Override
	public List<Posicion> getMovimientosConDireccion(Posicion from,	Posicion dest, Pieza[][] t) {
		return null;
	}


//	public boolean esValido(int x, int y) {
//		return (Math.abs(x - getPosicionX()) <= 1 && Math.abs(y - getPosicionY()) <= 1);
//	}

}
