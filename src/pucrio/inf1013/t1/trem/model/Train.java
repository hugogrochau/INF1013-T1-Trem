package pucrio.inf1013.t1.trem.model;

import java.awt.Color;

public class Train {
	
	private Direction direction;
	private int speed;
	
	public Train(Direction d) {
		this(d, 50);
	}
	
	public Train(Direction d, int speed) {
		this.direction = d;
		this.speed = speed;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public Direction getDirection() {
		return this.direction;
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
		return String.format("Speed %d, Direction %s", this.getSpeed(), this.direction.toString());
	}
	
	public enum Direction {
		LEFT_TO_RIGHT,
		RIGHT_TO_LEFT;
	}

}

