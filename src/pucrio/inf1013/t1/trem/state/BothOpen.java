package pucrio.inf1013.t1.trem.state;

import pucrio.inf1013.t1.trem.RailControlSystem;

public class BothOpen extends RailCrossingState {

	private static RailControlSystem rcs = RailControlSystem.getInstance();
	
	public BothOpen() {
		rcs.setLeftStopLightState(StopLightState.OPEN);
		rcs.setRightStopLightState(StopLightState.OPEN);
	}

	@Override
	public RailCrossingState trainTouchedLeftEntrySensor() {
		rcs.addLeftWaitingTrain();
		return new LeftOpen();
	}

	@Override
	public RailCrossingState trainTouchedLeftExitSensor() {
		return this;
	}

	@Override
	public RailCrossingState trainTouchedRightEntrySensor() {
		rcs.addRightWaitingTrain();
		return new RightOpen();
	}

	@Override
	public RailCrossingState trainTouchedRightExitSensor() {
		return this;
	}


}
