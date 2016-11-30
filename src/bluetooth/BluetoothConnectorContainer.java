package bluetooth;

/**
 * Container for the bluetooth connector
 * In order to prevent NullPointer
 */

public class BluetoothConnectorContainer {

	private BluetoothConnector controller;
	private boolean isMaster;
	
	/**
	 * Constructor
	 * @param master		Indicates if the robot is the master
	 * @param masterName	The name of the master
	 */
	public BluetoothConnectorContainer(boolean master, String masterName){
		this.isMaster = master;
		if(isMaster){
			controller = new BluetoothConnector(masterName);
		}else{
			controller = new BluetoothConnector();
		}
	}
	
	/**
	 * Get the BluetoothConnector instance
	 * @return BluetoothConnector instance
	 */
	public BluetoothConnector getInstance(){
		return controller;
	}
	
}
