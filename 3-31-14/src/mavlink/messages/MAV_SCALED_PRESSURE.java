package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#29
 * 	The pressure readings for the typical setup of one absolute and differential pressure sensor. 
 * 	The units are as specified in each field.
 */
public class MAV_SCALED_PRESSURE extends MAVLinkMessage {

	public final static char MSG_ID = 0;
	
	//	time_boot_ms	uint32_t	Timestamp (milliseconds since system boot)
	//	press_abs		float		Absolute pressure (hectopascal)
	//	press_diff		float		Differential pressure 1 (hectopascal)
	//	temperature		int16_t		Temperature measurement (0.01 degrees celsius)
	
	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_SCALED_PRESSURE(){
		this.mID 	= MAV_MESSAGE_TYPE.get(MSG_ID);
		this.len 	= (byte) MAVLinkEnum.MAVLINK_MESSAGE_LENGTHS[mID.value];
	}
	
	@Override
	public byte[] pack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MAVLinkMessage decode(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

}
