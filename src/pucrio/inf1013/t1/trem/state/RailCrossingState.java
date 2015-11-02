package pucrio.inf1013.t1.trem.state;

import pucrio.inf1013.t1.trem.RailControlSystem;

abstract public class RailCrossingState {

	protected static RailControlSystem rcs = RailControlSystem.getInstance();

	public abstract RailCrossingState trainTouchedLeftEntrySensor();

	public abstract RailCrossingState trainTouchedLeftExitSensor();

	public abstract RailCrossingState trainTouchedRightEntrySensor();

	public abstract RailCrossingState trainTouchedRightExitSensor();

	public static RailCrossingState getInitialState() {
		return new BothOpen();
	}
}
