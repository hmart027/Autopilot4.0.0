package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#74
 * 	Metrics typically displayed on a HUD for fixed wing aircraft
 */
public class MAV_VFR_HUD extends MAVLinkMessage {
	
	public final static char MSG_ID = 74;
	
	float airspeed;		//	Current airspeed in m/s. 										Float
	float groundspeed;	//	Current ground speed in m/s. 									Float
	float alt;			//	Current altitude (MSL), in meters. 								Float
	float climb;		//	Current climb rate in meters/second. 							Float
	int heading;		//	Current heading in degrees, in compass units (0..360, 0=north). 16bits integer.
	int throttle;		//	Current throttle setting in integer percent, 0 to 100. 			16bits unsigned integer

	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_VFR_HUD(){
		this.mID 	= MAV_MESSAGE_TYPE.get(MSG_ID);
		this.len	 = (byte) MAVLinkEnum.MAVLINK_MESSAGE_LENGTHS[mID.value];
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
