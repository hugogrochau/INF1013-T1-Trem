package pucrio.inf1013.t1.trem;

import pucrio.inf1013.t1.trem.model.Train;
import pucrio.inf1013.t1.trem.model.Train.Direction;

public class Main {

	public static void main(String args[]) {
		System.out.println("PUC-RIO INF1013 T1 - Trem. By Hugo Grochau");
		RailControlSystem rcs = RailControlSystem.getInstance();
		rcs.start();
	}
}
