package pucrio.inf1013.t1.trem;

import java.util.LinkedList;
import java.util.Queue;

import pucrio.inf1013.t1.trem.model.Train;
import pucrio.inf1013.t1.trem.model.Train.Direction;
import pucrio.inf1013.t1.trem.view.RailControlSystemFrame;

public class RailControlSystem {

	private static RailControlSystem instance;
	private RailControlSystemFrame rcsf;
	private Queue<Train> leftToRightWaitingQueue = new LinkedList<Train>();
	private Queue<Train> rightToLeftWaitingQueue = new LinkedList<Train>();
	private Queue<Train> bridgeQueue = new LinkedList<Train>();

	private RailControlSystem() {
		this.rcsf = new RailControlSystemFrame();
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

	public void tick() {
		this.rcsf.getContentPane().paintAll(rcsf.getContentPane().getGraphics());
	}

}
