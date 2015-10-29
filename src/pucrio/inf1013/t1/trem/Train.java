package pucrio.inf1013.t1.trem;

import java.awt.Color;
import java.util.Observable;

public class Train extends Observable implements Runnable  {
	
	private Direction direction;
	private int speed;
	private int position;
	private int minimalDistance = 70;
	private boolean running = true;
	private boolean exitedTunnel = false;
	
	public Train(Direction d, int pos) {
		this(d, 50, pos);
	}
	
	public Train(Direction d, int speed, int pos) {
		this.direction = d;
		this.speed = speed;
		this.position = pos;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public int calculateY() {
		if (this.position > 472 && this.position < 1153) {
			return 380;
		}
		return 380;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public int getPosition() {
		return this.position;
	}

	public void kill() {
		if (this.running) {
			RailControlSystem.getInstance().removeTrain(this);
			this.running = false;
		}
	}
	
	public void setPosition(int pos) {
		this.position = pos;
	}
	
	public void tick() {
		
		int sign = this.direction.equals(Direction.LEFT_TO_RIGHT) ? 1 : -1;
		this.position += this.speed * sign;
		
		if (this.getDirection().equals(Direction.LEFT_TO_RIGHT)) {
			if (this.getPosition() >= RailControlSystem.RIGHT_ANGLED_TRACK_START && !this.exitedTunnel) {
				this.exitedTunnel = true;
				this.setChanged();
				this.notifyObservers();
			} else if (this.getPosition() >= RailControlSystem.RIGHT_ENTRY_POSITION) {
				this.kill();
			}			
		} else if (this.getDirection().equals(Direction.RIGHT_TO_LEFT)) {
			if (this.getPosition() <= RailControlSystem.LEFT_ANGLED_TRACK_START && !this.exitedTunnel) {
				this.exitedTunnel = true;
				this.setChanged();
				this.notifyObservers();
			} else if (this.getPosition() <= RailControlSystem.LEFT_ENTRY_POSITION) {
				this.kill();
			}
		}
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
	
	public int getMinimalDistance() {
		return minimalDistance;
	}
	
	@Override
	public String toString() {
		return String.format("Speed %d, Direction %s, Minimal Distance %d, Position %d", this.getSpeed(), this.direction.toString(), this.minimalDistance, this.position);
	}

	public enum Direction {
		LEFT_TO_RIGHT,
		RIGHT_TO_LEFT;
	}

	@Override
	public void run() {
		while (this.running) {
			if (RailControlSystem.getInstance().canMove(this, this.direction)) {
				this.tick();
			}
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) { }
		}
	}

}

