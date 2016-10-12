package bluetooth;

public class SlaveBluetoothConnector extends BluetoothConnector{

	private BluetoothConnector connector;
	
	public SlaveBluetoothConnector(){
		connector = new BluetoothConnector();
	}
	
	public BluetoothConnector getConnector(){
		return connector;
	}
	
}
