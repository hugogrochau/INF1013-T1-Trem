package pucrio.inf1013.t1.trem;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Timer;

import pucrio.inf1013.t1.trem.model.Train;
import pucrio.inf1013.t1.trem.model.Train.Direction;
import pucrio.inf1013.t1.trem.view.RailControlSystemFrame;
import pucrio.inf1013.t1.trem.view.StopLight;

public class RailControlSystem {

	public final static Point LEFT_EXIT_COORDINATES = new Point(0, 335);
	public final static Point LEFT_ENTRY_COORDINATES = new Point(0, 460);
	public final static Point RIGHT_EXIT_COORDINATES = new Point(1544, 339);
	public final static Point RIGHT_ENTRY_COORDINATES = new Point(1540, 460);
	public final static Point BRIDGE_LEFT_COORDINATES = new Point(471, 385);
	public final static Point BRIDGE_RIGHT_COORDINATES = new Point(1123, 385);
	
	public final static int LEFT_ANGLED_TRACK_START = 180;
	public final static int LEFT_ANGLED_TRACK_END = 420;
	public final static int RIGHT_ANGLED_TRACK_START = 1152;
	public final static int RIGHT_ANGLED_TRACK_END = 1343;
	public final static int TRAIN_CIRCLE_RADIUS = 16;
	public final static int TRAIN_DISTANCE = 32;

	private static RailControlSystem instance;
	private RailControlSystemFrame rcsf;

	private Queue<Train> leftToRightWaitingQueue = new LinkedList<Train>();
	private Queue<Train> rightToLeftWaitingQueue = new LinkedList<Train>();
	private Queue<Train> bridgeQueue = new LinkedList<Train>();
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
	
	public Queue<Train> getLeftToRightWaitingQueue() {
		return this.leftToRightWaitingQueue;
	}

	public Queue<Train> getRightToLeftWaitingQueue() {
		return this.rightToLeftWaitingQueue;
	}

	public Queue<Train> getBridgeQueue() {
		return this.bridgeQueue;
	}

	public void addTrain(Train t) {
		if (!this.canMove(t, t.getDirection())) {
			return;
		}
		if (t.getDirection().equals(Direction.LEFT_TO_RIGHT)) {
			this.leftToRightWaitingQueue.add(t);
		} else if (t.getDirection().equals(Direction.RIGHT_TO_LEFT)) {
			this.rightToLeftWaitingQueue.add(t);
		}
	}

	public boolean moveTrainToBridge(Direction d) {
		Queue<Train> queue = d.equals(Direction.LEFT_TO_RIGHT) ? leftToRightWaitingQueue : rightToLeftWaitingQueue;
		Train t = queue.poll();
		if (t == null) {
			return false;
		}
		this.bridgeQueue.add(t);
		return true;
	}

	public static RailControlSystem getInstance() {
		if (RailControlSystem.instance == null) {
			RailControlSystem.instance = new RailControlSystem();
		}
		return RailControlSystem.instance;
	}

	public void moveTrain(Train t, Direction d) {
		if (!this.canMove(t, d)) {
			return;
		}
		if (d.equals(Direction.LEFT_TO_RIGHT)) {
			/* left angled track */
			if (t.getPosition().x >= RailControlSystem.LEFT_ANGLED_TRACK_START
					&& t.getPosition().x <= RailControlSystem.LEFT_ANGLED_TRACK_END) {
				t.translatePosition(RailControlSystem.TRAIN_DISTANCE, -10);
			/* right angled track */
			} else if (t.getPosition().x >= RailControlSystem.RIGHT_ANGLED_TRACK_START
					&& t.getPosition().x <= RailControlSystem.RIGHT_ANGLED_TRACK_END) {
				t.translatePosition(RailControlSystem.TRAIN_DISTANCE, -7);
			/* straight track */
			} else {
				t.translatePosition(RailControlSystem.TRAIN_DISTANCE, 0);
			}
		} else if (d.equals(Direction.RIGHT_TO_LEFT)) {
			/* left angled track */
			if (t.getPosition().x >= RailControlSystem.LEFT_ANGLED_TRACK_START + 30
					&& t.getPosition().x <= RailControlSystem.LEFT_ANGLED_TRACK_END + 50) {
				t.translatePosition(-RailControlSystem.TRAIN_DISTANCE, 10);
			/* right angled track */
			} else if (t.getPosition().x >= RailControlSystem.RIGHT_ANGLED_TRACK_START + 10
					&& t.getPosition().x <= RailControlSystem.RIGHT_ANGLED_TRACK_END + 10) {
				t.translatePosition(-RailControlSystem.TRAIN_DISTANCE, -13);
			/* straight track */
			} else {
				t.translatePosition(-RailControlSystem.TRAIN_DISTANCE, 0);
			}
		}
	}
	
	public boolean canMove(Train t, Direction d) {
		boolean canMove = true;
		Queue<Train> q = d.equals(Direction.LEFT_TO_RIGHT) ? this.leftToRightWaitingQueue : this.rightToLeftWaitingQueue;
 		for (Train qt : q) {
			if (!t.equals(qt)) {
				int maxDistance = Math.max(t.getMinimalDistance(), qt.getMinimalDistance());	
				if (qt.getPosition().x <= t.getPosition().x && qt.getPosition().x >= t.getPosition().x - maxDistance && d.equals(Direction.RIGHT_TO_LEFT)) {
					canMove = false;
				}
				if (qt.getPosition().x <= t.getPosition().x + maxDistance && qt.getPosition().x >= t.getPosition().x && d.equals(Direction.LEFT_TO_RIGHT)) {
					canMove = false;
				}
			}
		}
		return canMove;
	}

	public void updateTrainPositions() {
		for (Train t : this.leftToRightWaitingQueue) {
			this.moveTrain(t, Direction.LEFT_TO_RIGHT);
		
		}
		for (Train t : this.rightToLeftWaitingQueue) {
			if (t.getPosition().x >= RIGHT_ANGLED_TRACK_END + 10) {
				this.moveTrain(t, Direction.RIGHT_TO_LEFT);
			}
		}
	}

	public void tick() {
		this.updateTrainPositions();
		this.rcsf.getContentPane().paintAll(rcsf.getContentPane().getGraphics());
	}

	public void start() {
		this.timer.start();
	}

}
