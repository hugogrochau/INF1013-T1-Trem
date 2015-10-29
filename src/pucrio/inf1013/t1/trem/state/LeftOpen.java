package pucrio.inf1013.t1.trem.state;

public class LeftOpen extends RailCrossingState {

	public LeftOpen() {
		rcs.setLeftStopLightState(StopLightState.OPEN);
		rcs.setRightStopLightState(StopLightState.CLOSED);
	}
	
	@Override
	public RailCrossingState trainTouchedLeftEntrySensor() {
		rcs.addLeftWaitingTrain();
		return this;		
	}

	@Override
	public RailCrossingState trainTouchedLeftExitSensor() {
		rcs.removeRightWaitingTrain();
		return this;
	}

	@Override
	public RailCrossingState trainTouchedRightEntrySensor() {
		rcs.addRightWaitingTrain();
		return this;
	}

	@Override
	public RailCrossingState trainTouchedRightExitSensor() {
		rcs.removeLeftWaitingTrain();
		
		if (rcs.getLeftWaitingTrains() <= 0) {
			if (rcs.getRightWaitingTrains() > 0) {
				return new RightOpen();
			} else {
				return new BothOpen();
			}
		}
		
		return this;
	}



}
