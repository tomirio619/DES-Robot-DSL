package main;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class SlaveRobot extends Robot {

	private final EV3TouchSensor touchLeftSensor;
	private final EV3TouchSensor touchRightSensor;

	private final EV3UltrasonicSensor frontUltraSensor;

	private final EV3GyroSensor gyroSensor;
	
	private final SampleProvider touchLeft, touchRight, distance;

	public SlaveRobot() {
		touchLeftSensor = new EV3TouchSensor(LocalEV3.get().getPort("S1"));
		touchRightSensor = new EV3TouchSensor(LocalEV3.get().getPort("S2"));

		frontUltraSensor = new EV3UltrasonicSensor(LocalEV3.get().getPort("S3"));

		gyroSensor = new EV3GyroSensor(LocalEV3.get().getPort("S4"));
		
		//Sample providers
		touchLeft = touchLeftSensor.getTouchMode();
		touchRight = touchRightSensor.getTouchMode();
		
		distance = frontUltraSensor.getDistanceMode();
	}

	public float getTouchValue(Direction dir) {
		switch (dir) {
			case LEFT: {
				float[] sampleSize = new float[touchLeftSensor.sampleSize()];
				touchLeftSensor.fetchSample(sampleSize, 0);
				return sampleSize[0];
			}
			default: {
				float[] sampleSize = new float[touchRightSensor.sampleSize()];
				touchRightSensor.fetchSample(sampleSize, 0);
				return sampleSize[0];
			}
		}
	}

}
