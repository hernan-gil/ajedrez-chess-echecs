import java.util.LinkedList;
import java.util.List;


public class Peon extends Pieza {

	protected Peon(Color color, boolean sentido, boolean promovido, boolean enPassant, boolean saltoDos) {
		super("P", color, 1, sentido, promovido, enPassant, saltoDos);
	}

	@Override
	public List<Posicion> getMovimientos(Posicion from, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();
		String colorPiezaActual = t[from.getX()][from.getY()].getColor().toString().equals("BLACK")?"N":"B";
		System.out.println("Iniciando movimientos de peon (" + from.getX()+","+from.getY() + ") de color: " + colorPiezaActual);
		int avance = isBaja()? 1 : -1;
		Posicion movimiento = new Posicion(from.getX() + avance, from.getY());
		if(null == t[movimiento.getX()][movimiento.getY()]) {
			System.err.println("Agregando movimiento de Peon: (" + movimiento.getX() +","+ movimiento.getY() +")");
			movimientos.add(movimiento);
		} else {
			String colorPiezaTemp = t[movimiento.getX()][movimiento.getY()].getColor().toString().equals("BLACK")?"N":"B";
			if (colorPiezaActual.equals(colorPiezaTemp)) {
			} else {
				System.err.println("Agregando movimiento de Peon: (" + movimiento.getX() +","+ movimiento.getY() +")");
				movimientos.add(movimiento);
			}
		}
		
//		System.err.println("1 Agregando movimiento: (" + (from.getX() + avance) +","+ from.getY() +")");
		if (isPosicionInicial()) {
			movimiento = new Posicion(from.getX()+ 2 * avance, from.getY());
//			System.err.println("2 Agregando movimiento: (" + (from.getX() + 2 * avance) +","+ from.getY() +")");
			if(null == t[movimiento.getX()][movimiento.getY()]) {
				System.err.println("Agregando movimiento de Peon: (" + movimiento.getX() +","+ movimiento.getY() +")");
				movimientos.add(movimiento);
			} else {
				String colorPiezaTemp = t[movimiento.getX()][movimiento.getY()].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
				} else {
					System.err.println("Agregando movimiento de Peon: (" + movimiento.getX() +","+ movimiento.getY() +")");
					movimientos.add(movimiento);
				}
			}
		} else {
			movimiento = new Posicion(from.getX() + avance, from.getY() + avance); //Posible captura a la derecha de su posicion
//			System.err.println("3(Captura)Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() + avance) +")");
			if(null == t[movimiento.getX()][movimiento.getY()]) {
				System.err.println("Agregando movimiento de Peon: (" + movimiento.getX() +","+ movimiento.getY() +")");
				movimientos.add(movimiento);
			} else {
				String colorPiezaTemp = t[movimiento.getX()][movimiento.getY()].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
				} else {
					System.err.println("Agregando movimiento de Peon: (" + movimiento.getX() +","+ movimiento.getY() +")");
					movimientos.add(movimiento);
				}
			}
			movimiento = new Posicion(from.getX() + avance, from.getY() - avance); //Posible captura a la izquierda de su posicion
//			System.err.println("4(Captura)Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() - avance) +")");
			if(null == t[movimiento.getX()][movimiento.getY()]) {
				System.err.println("Agregando movimiento de Peon: (" + movimiento.getX() +","+ movimiento.getY() +")");
				movimientos.add(movimiento);
			} else {
				String colorPiezaTemp = t[movimiento.getX()][movimiento.getY()].getColor().toString().equals("BLACK")?"N":"B";
				if (colorPiezaActual.equals(colorPiezaTemp)) {
				} else {
					System.err.println("Agregando movimiento de Peon: (" + movimiento.getX() +","+ movimiento.getY() +")");
					movimientos.add(movimiento);
				}
			}
		}
		if (enPassant) {
			enPassant = false;
		}
		System.out.println("Finalizando movimientos de peon de color: " + colorPiezaActual);
		return movimientos;
	}
	
	public List<Posicion> getMovimientosConDireccion(Posicion from, Posicion dest, Pieza[][] t) {
		List<Posicion> movimientos = new LinkedList<Posicion>();
		int avance = isBaja()? 1 : -1;
		Posicion movimiento = new Posicion(from.getX() + avance, from.getY());
		if(null == t[movimiento.getX()][movimiento.getY()])
			movimientos.add(movimiento);
//		System.err.println("1 Agregando movimiento: (" + (from.getX() + avance) +","+ from.getY() +")");
		if (isPosicionInicial()) {
			movimiento = new Posicion(from.getX()+ 2 * avance, from.getY());
//			System.err.println("2 Agregando movimiento: (" + (from.getX() + 2 * avance) +","+ from.getY() +")");
			if(null == t[movimiento.getX()][movimiento.getY()] && movimiento.getX() < ChessRules.SIZE || movimiento.getY() < ChessRules.SIZE)
				movimientos.add(movimiento);
			if( movimientos.contains(dest))
				return movimientos;
		} else {
			movimiento = new Posicion(from.getX() + avance, from.getY() + avance); //Posible captura a la derecha de su posicion
//			System.err.println("3(Captura)Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() + avance) +")");
			if(null == t[movimiento.getX()][movimiento.getY()])
				movimientos.add(movimiento);
			movimiento = new Posicion(from.getX() + avance, from.getY() - avance); //Posible captura a la izquierda de su posicion
//			System.err.println("4(Captura)Agregando movimiento: (" + (from.getX() + avance) +","+ (from.getY() - avance) +")");
			if(null == t[movimiento.getX()][movimiento.getY()])
				movimientos.add(movimiento);
			if( movimientos.contains(dest))
				return movimientos;
			movimientos.clear();
		}
		if (enPassant) {
			enPassant = false;
		}
		return movimientos;
	}
//	public boolean esValido(int x, int y) {
//		return (x == getPosicionX() && Math.abs(y - getPosicionY()) <= 2);
//	}

	//validarEnPassant
}
