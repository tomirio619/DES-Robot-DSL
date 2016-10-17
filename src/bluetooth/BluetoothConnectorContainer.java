package bluetooth;

public class BluetoothConnectorContainer {

	private BluetoothConnector controller;
	private boolean isMaster;
	
	public BluetoothConnectorContainer(boolean master){
		this.isMaster = master;
		if(isMaster){
			//FIXME: Pass the Rover with which you would like to connect to, currently hardcoded. 
			controller = new BluetoothConnector(2);
		}else{
			controller = new BluetoothConnector();
		}
	}
	
	public BluetoothConnector getInstance(){
		return controller;
	}
	
}
