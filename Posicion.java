public class Posicion implements Comparable<Posicion>{
	int x, y;

	public Posicion(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int compareTo(Posicion p) {
//		System.err.println("Compare -> p x: " + p.getX() + " / p y: " + p.getY() + " / this.x: " + this.x + " / this.y: " + this.y);
		if(this.x == p.getX() && this.y == p.getY()) return 0;
		else if(this.x - p.getX() < 0) return -1;
		else if(this.x - p.getX() > 0) return 1;
		else return -1;
	}

	@Override
	public String toString() {
		return "Posicion [x=" + x + ", y=" + y + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o == this) return true;
		if (o instanceof Posicion) {
			Posicion other = (Posicion) o;
			return other.x == this.x && other.y == this.y; 
		}
		return false;
	}

}
