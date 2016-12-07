package helpers;

import lejos.robotics.Color;

public final class RGBColorWrapper {
	
	
	// All of the measurements done using Rover7
	/**
	 * Red RGB color measurements. Color ID: 0
	 */
	static float[] red = { 0.19f, 0.048f, 0.030f };
	/**
	 * Green RGB color measurements. Color ID: 1
	 */
	static float[] green = { 0.10f, 0.22f, 0.09f };
	/**
	 * Blue RGB color measurements. Color ID: 2
	 */
	static float[] blue = { 0.05f, 0.165f, 0.138f };
	/**
	 * White RGB color measurements. Color ID 6
	 */
	static float[] white = { 0.26f, 0.29f, 0.19f };
	/**
	 * Black RGB color measurements. Color ID 7
	 */
	static float[] black = { 0.023f, 0.028f, 0.016f };
	/**
	 * Deviation for the color measurements
	 */
	static float measurementDeviation = 0.03f;
	
	/**
	 * Determine the color from the RGB color measurements
	 * @param colorMeasurement float[] containing 3 RGB color values.
	 * @return	Integer indicating the color (according to lejos.robotics.color)
	 */
	public static int determineColor(float[] colorMeasurement){
		if (equalMeasurements(red, colorMeasurement, measurementDeviation)){
			return Color.RED; // Color ID: 0
		}
		else if (equalMeasurements(green, colorMeasurement, measurementDeviation)){
			return Color.GREEN; // Color ID: 1
		}
		else if (equalMeasurements(blue, colorMeasurement, measurementDeviation)){
			return Color.BLUE; // Color ID: 2
		}
		else if (equalMeasurements(white, colorMeasurement, measurementDeviation)){
			return Color.WHITE; // Color ID 6
		}
		else if (equalMeasurements(black, colorMeasurement, measurementDeviation)){
			return Color.BLACK; // Color ID 7
		}
		else{
			return Color.NONE;
		}
	}
	
	/**
	 * Check if two RGB measurements are "equal" given a measurement deviation.
	 * @param measurement1	The first RGB measurement
	 * @param measurement2	The second RGB measurement
	 * @param measurementDeviation the measurement deviation
	 * @return <code>True</code> if the absolute difference for the pairwise values of the RGB measurements are smaller 
	 * than the specified measurement deviation.
	 */
	static boolean equalMeasurements(float[] measurement1, float[] measurement2, float measurementDeviation){
		for (int i = 0; i < 3; i++){
			if (Math.abs(measurement1[i] - measurement2[i]) > measurementDeviation){
				return false;
			}
		}
		return true;
	}

}
