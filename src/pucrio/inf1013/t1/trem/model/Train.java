package pucrio.inf1013.t1.trem.model;

import java.awt.Color;
import java.awt.Point;

public class Train {
	
	private Direction direction;
	private int speed;
	private Point position;
	
	public Train(Direction d, Point pos) {
		this(d, 50, pos);
	}
	
	public Train(Direction d, int speed, Point pos) {
		this.direction = d;
		this.speed = speed;
		this.position = (Point) pos.clone();
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public Point getPosition() {
		return this.position;
	}
	
	public void setPosition(Point pos) {
		this.position = (Point) pos.clone();
	}
	
	public void translatePosition(int x, int y) {
		this.position.translate(x, y);
	}
	
	public Color getColor() {
		switch(this.direction) {
		case LEFT_TO_RIGHT:
			return Color.BLACK;
		case RIGHT_TO_LEFT:
			return Color.RED;
		default:
			return Color.BLACK;
		}
	}
	
	@Override
	public String toString() {
		return String.format("Speed %d, Direction %s, Position (%d,%d)", this.getSpeed(), this.direction.toString(), this.position.x, this.position.y);
	}
	
	public enum Direction {
		LEFT_TO_RIGHT,
		RIGHT_TO_LEFT;
	}

}

