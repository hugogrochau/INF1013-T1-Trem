package pucrio.inf1013.t1.trem.state;

public class RightOpen extends RailCrossingState {

	public RightOpen() {
		rcs.setLeftStopLightState(StopLightState.CLOSED);
		rcs.setRightStopLightState(StopLightState.OPEN);
	}

	@Override
	public RailCrossingState trainTouchedLeftEntrySensor() {
		rcs.addLeftWaitingTrain();
		return this;
	}

	@Override
	public RailCrossingState trainTouchedLeftExitSensor() {
		rcs.removeRightWaitingTrain();

		if (rcs.getRightWaitingTrains() <= 0) {
			if (rcs.getLeftWaitingTrains() > 0) {
				return new LeftOpen();
			} else {
				return new BothOpen();
			}
		}

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
		return this;
	}

}
