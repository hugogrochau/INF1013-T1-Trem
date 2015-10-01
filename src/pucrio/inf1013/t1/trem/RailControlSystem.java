package pucrio.inf1013.t1.trem;

import pucrio.inf1013.t1.trem.view.RailControlSystemFrame;

public class RailControlSystem {

	private static RailControlSystem instance;
	private RailControlSystemFrame rcsf; 
	
	private RailControlSystem() {
		this.rcsf = new RailControlSystemFrame();
	}
	
	public static RailControlSystem getInstance() {
		if (RailControlSystem.instance == null) {
			RailControlSystem.instance = new RailControlSystem();
		}
		return RailControlSystem.instance;
	}
	
}
