package pucrio.inf1013.t1.trem;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Timer;

import pucrio.inf1013.t1.trem.Train.Direction;
import pucrio.inf1013.t1.trem.state.RailCrossingState;
import pucrio.inf1013.t1.trem.state.StopLightState;
import pucrio.inf1013.t1.trem.views.RailControlSystemFrame;

public class RailControlSystem implements Observer {
	
	public final static Point LEFT_EXIT_COORDINATES = new Point(0, 335);
	public final static Point LEFT_ENTRY_COORDINATES = new Point(0, 460);
	public final static Point RIGHT_EXIT_COORDINATES = new Point(1544, 339);
	public final static Point RIGHT_ENTRY_COORDINATES = new Point(1540, 460);
	public final static Point BRIDGE_LEFT_COORDINATES = new Point(471, 385);
	public final static Point BRIDGE_RIGHT_COORDINATES = new Point(1123, 385);

	public final static int LEFT_ENTRY_POSITION = 0;
	public final static int RIGHT_ENTRY_POSITION = 1544;
	public final static int LEFT_ANGLED_TRACK_START = 180;
	public final static int LEFT_ANGLED_TRACK_END = 420;
	public final static int RIGHT_ANGLED_TRACK_START = 1343;
	public final static int RIGHT_ANGLED_TRACK_END = 1152;
	public final static int TRAIN_CIRCLE_RADIUS = 16;
	public final static int TRAIN_DISTANCE = 32;
	
	private int leftWaitingTrains = 0;
	private int rightWaitingTrains = 0;

	private static RailControlSystem instance;
	private RailControlSystemFrame rcsf;
	private RailCrossingState railCrossingState;
	
	private StopLightState leftStopLightState = StopLightState.OPEN;
	private StopLightState rightStopLightState = StopLightState.OPEN;
	
	private List<Train> leftToRightTrains = new LinkedList<Train>();
	private List<Train> rightToLeftTrains = new LinkedList<Train>();
	
	private Timer timer;

	private RailControlSystem() {
		this.rcsf = new RailControlSystemFrame();
		this.timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				RailControlSystem.getInstance().tick();
			}
		});
		this.timer.setRepeats(true);
	}

	public List<Train> getLeftToRightTrains() {
		return this.leftToRightTrains;
	}

	public List<Train> getRightToLeftTrains() {
		return this.rightToLeftTrains;
	}

	public void addTrain(Train t) {
		if (t.getDirection().equals(Direction.LEFT_TO_RIGHT)) {
			this.leftToRightTrains.add(t);
			this.railCrossingState = this.railCrossingState.trainTouchedLeftEntrySensor();
		} else if (t.getDirection().equals(Direction.RIGHT_TO_LEFT)) {
			this.rightToLeftTrains.add(t);
			this.railCrossingState = this.railCrossingState.trainTouchedRightEntrySensor();
		}
		t.addObserver(this);
		new Thread(t).start();
	}

	public static RailControlSystem getInstance() {
		if (RailControlSystem.instance == null) {
			RailControlSystem.instance = new RailControlSystem();
		}
		return RailControlSystem.instance;
	}

	public void moveTrain(Train t, Direction d) {
		if (this.canMove(t, d)) {
			t.tick();
		}
	}

	public boolean canMove(Train t, Direction d) {
		boolean canMove = true;
		if (d.equals(Direction.LEFT_TO_RIGHT)) {
			for (Train otherTrain : this.leftToRightTrains) {
				int maxDistance = Math.max(t.getMinimalDistance(), otherTrain.getMinimalDistance());
				if (!otherTrain.equals(t) && otherTrain.getPosition() <= t.getPosition() + maxDistance
						&& otherTrain.getPosition() >= t.getPosition()) {
					canMove = false;
				}
			}
			if (this.getLeftStopLightState().equals(StopLightState.CLOSED) && t.getPosition() >= RailControlSystem.LEFT_ANGLED_TRACK_START && t.getPosition() <= RailControlSystem.RIGHT_ANGLED_TRACK_END) {
				canMove = false;
			}
		} else if (d.equals(Direction.RIGHT_TO_LEFT)) {
			for (Train otherTrain : this.rightToLeftTrains) {
				int maxDistance = Math.max(t.getMinimalDistance(), otherTrain.getMinimalDistance());
				if (!otherTrain.equals(t) && otherTrain.getPosition() >= t.getPosition() - maxDistance
						&& otherTrain.getPosition() <= t.getPosition()) {
					canMove = false;
				}
			}
			if (this.getRightStopLightState().equals(StopLightState.CLOSED) && t.getPosition() <= RailControlSystem.RIGHT_ANGLED_TRACK_START && t.getPosition() >= RailControlSystem.LEFT_ANGLED_TRACK_END) {
				canMove = false;
			}
		}
		return canMove;
	}

	public void tick() {
		this.rcsf.getContentPane().paintAll(rcsf.getContentPane().getGraphics());
		System.out.println("tick");
		System.out.println(this.leftWaitingTrains);
		System.out.println(this.rightWaitingTrains);

	}

	public void start() {
		this.timer.start();
		this.railCrossingState = RailCrossingState.getInitialState();
	}

	public StopLightState getLeftStopLightState() {
		return leftStopLightState;
	}

	public void setLeftStopLightState(StopLightState leftStopLightState) {
		this.leftStopLightState = leftStopLightState;
	}

	public StopLightState getRightStopLightState() {
		return rightStopLightState;
	}

	public void setRightStopLightState(StopLightState rightStopLightState) {
		this.rightStopLightState = rightStopLightState;
	}

	public void removeTrain(Train train) {
		if (train.getDirection().equals(Direction.LEFT_TO_RIGHT)) {
			this.leftToRightTrains.remove(train);
		} else if (train.getDirection().equals(Direction.RIGHT_TO_LEFT)) {
			this.rightToLeftTrains.remove(train);
		}
		
	}

	@Override
	public void update(Observable obs, Object obj) {
		Train t = (Train) obs;
		if (t.getDirection().equals(Direction.LEFT_TO_RIGHT)) {
			this.railCrossingState = this.railCrossingState.trainTouchedRightExitSensor();
		} else if (t.getDirection().equals(Direction.RIGHT_TO_LEFT)) {
			this.railCrossingState = this.railCrossingState.trainTouchedLeftExitSensor();
		}
	}

	public int getLeftWaitingTrains() {
		return leftWaitingTrains;
	}

	public void addLeftWaitingTrain() {
		this.leftWaitingTrains++;
	}
	
	public void removeLeftWaitingTrain() {
		this.leftWaitingTrains--;
	}

	public int getRightWaitingTrains() {
		return rightWaitingTrains;
	}

	public void addRightWaitingTrain() {
		this.rightWaitingTrains++;
	}
	
	public void removeRightWaitingTrain() {
		this.rightWaitingTrains--;
	}

}
