package bluetooth;

public class BluetoothConnectorContainer {

	private BluetoothConnector controller;
	private boolean isMaster;
	
	public BluetoothConnectorContainer(boolean master){
		this.isMaster = master;
	}
	
	public BluetoothConnector getInstance(){
		if (controller == null){
			if(isMaster){
				//FIXME: Pass the Rover with which you would like to connect to, currently hardcoded. 
				controller = new BluetoothConnector(4);
			}else{
				controller = new BluetoothConnector();
			}
		}else{
			return controller;
		}
		return null;
	}
	
}
