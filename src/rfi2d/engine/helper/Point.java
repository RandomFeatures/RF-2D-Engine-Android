package rfi2d.engine.helper;

public class Point {
	
	public float x;
	public float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public Point setX(float x) {
		this.x = x;
		return this;
	}

	public float getY() {
		return y;
	}

	public Point setY(float y) {
		this.y = y;
		return this;
	}

}
