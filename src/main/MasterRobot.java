package main;

import java.math.BigDecimal;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.SampleProvider;

public class MasterRobot extends Robot{

	private final EV3LargeRegulatedMotor leftMotor;
	private final EV3LargeRegulatedMotor rightMotor;
	
	private final NXTLightSensor leftLightSensor;
	private final NXTLightSensor rightLightSensor;
	
	private final EV3UltrasonicSensor backUltraSensor;
	
	private final EV3ColorSensor colorSensor;
	
	private final SampleProvider leftLight, rightLight, distance, color;
	
	public MasterRobot() {
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		
		leftLightSensor = new NXTLightSensor(LocalEV3.get().getPort("S1"));
		rightLightSensor = new NXTLightSensor(LocalEV3.get().getPort("S2"));
		
		backUltraSensor = new EV3UltrasonicSensor(LocalEV3.get().getPort("S3"));
		
		colorSensor = new EV3ColorSensor(LocalEV3.get().getPort("S4"));
		
		//Sample Providers
		leftLight = leftLightSensor.getRedMode();
		rightLight = rightLightSensor.getRedMode();
		color = colorSensor.getRGBMode();
		distance = backUltraSensor.getDistanceMode();

	}
	
	public void rotateLeftMotorBackward(){
		this.leftMotor.backward();
	}
	
	public void rotateLeftMotorForward(){
		this.leftMotor.forward();
	}
	
	public void rotateRightMotorBackward(){
		this.rightMotor.backward();
	}
	
	public void rotateRightMotorForward(){
		this.rightMotor.forward();
	}
	
	public void stopRightMotor(){
		this.rightMotor.stop();
	}
	
	public void stopLeftMotor(){
		this.leftMotor.stop();
	}
	
	public EV3LargeRegulatedMotor getLeftMotor(){
		return this.leftMotor;
	}
	
	public EV3LargeRegulatedMotor getRightMotor(){
		return this.rightMotor;
	}
	
	/**
	 * Returns the color ID of the surface.
	 * The sensor can identify 8 unique colors (NONE, BLACK, BLUE, GREEN, YELLOW, RED, WHITE, BROWN)
	 * with ID of 0-7 respectively.
	 * @return The color ID of the surface.
	 */
	public float[] getFloorColor(){
		/**
		 * colorsensor.sampleSize() has the value 1
		 * If you want to use RGB measurements, you should change it to 3.
		 */
		float [] sampleSize = new float [3];
		color.fetchSample(sampleSize, 0);
		return sampleSize;
	}
		
	/**
	 * Get the value of the ultrasonic sensor on the back.
	 * Note that this sensor points down, so it can be used to prevent from falling off
	 * the platform when driving backwards.
	 * @return float value of the current sample of the ultrasonic sensor.
	 */
	public float getBackDistanceValue(){
		float[] sampleSize = new float[backUltraSensor.sampleSize()];
		distance.fetchSample(sampleSize, 0);
		return sampleSize[0];
	}	
}
