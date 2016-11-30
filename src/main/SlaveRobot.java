package main;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class SlaveRobot extends Robot {

	/**
	 * The left touch sensor
	 */
	private final EV3TouchSensor touchLeftSensor;
	/**
	 * The right touch sensor
	 */
	private final EV3TouchSensor touchRightSensor;
	/**
	 * The front ultra sensor
	 */
	private final EV3UltrasonicSensor frontUltraSensor;
	/**
	 * The gyro sensor
	 */
	private final EV3GyroSensor gyroSensor;
	/**
	 * The sample providers for the sensors
	 */
	private final SampleProvider touchLeft, touchRight, ultraBack, gyro;

	public SlaveRobot() {
		touchLeftSensor = new EV3TouchSensor(LocalEV3.get().getPort("S1"));
		touchRightSensor = new EV3TouchSensor(LocalEV3.get().getPort("S2"));

		frontUltraSensor = new EV3UltrasonicSensor(LocalEV3.get().getPort("S3"));

		gyroSensor = new EV3GyroSensor(LocalEV3.get().getPort("S4"));
		
		//Sample providers
		touchLeft = touchLeftSensor.getTouchMode();
		touchRight = touchRightSensor.getTouchMode();
		ultraBack = frontUltraSensor.getDistanceMode();
		gyro = gyroSensor.getAngleMode();
	}

	/**
	 * Get the touch value of the touch sensor for a given direction
	 * @param dir	Specifies from which touch sensor you need obtain the sample
	 * @return		The touch sample of the touch sensor for the specified dir.
	 */
	public int getTouchValue(Direction dir) {
		switch (dir) {
			case LEFT: {
				float[] sampleSize = new float[touchLeftSensor.sampleSize()];
				touchLeft.fetchSample(sampleSize, 0);
				return (int) sampleSize[0];
			}
			default: {
				// RIGHT
				float[] sampleSize = new float[touchRightSensor.sampleSize()];
				touchRight.fetchSample(sampleSize, 0);
				return (int) sampleSize[0];
			}
		}
	}
	
	/**
	 * Get the current gyro value modulo 360
	 * @return The gyro value mod 360
	 */
	public int getGyroValue(){
		float[] sampleSize = new float[gyroSensor.sampleSize()];
		gyro.fetchSample(sampleSize, 0);
		//FIXME: currently working modulo 360, but this should not be the default behavior.
		return (int) (sampleSize[0] % 360);
	}
	
	/**
	 * Get the value of the ultrasonic sensor on the back.
	 * @return float of the current sample of the ultrasonic sensor on the back.
	 */
	public float getFrontUltraValue(){
		float[] sampleSize = new float[frontUltraSensor.sampleSize()];
		ultraBack.fetchSample(sampleSize, 0);
		return sampleSize[0];
	}

}
