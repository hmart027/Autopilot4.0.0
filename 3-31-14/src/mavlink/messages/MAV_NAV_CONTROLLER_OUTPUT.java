package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#62
 * 	Outputs of the APM navigation controller. The primary use of this message is to check the response and signs of the 
 *	controller before actual flight and to assist with tuning controller parameters.
 */
public class MAV_NAV_CONTROLLER_OUTPUT extends MAVLinkMessage {

	public final static char MSG_ID = 0;

	//	nav_roll		float		Current desired roll in degrees
	//	nav_pitch		float		Current desired pitch in degrees
	//	nav_bearing		int16_t		Current desired heading in degrees
	//	target_bearing	int16_t		Bearing to current MISSION/target in degrees
	//	wp_dist			uint16_t	Distance to active MISSION in meters
	//	alt_error		float		Current altitude error in meters
	//	aspd_error		float		Current airspeed error in meters/second
	//	xtrack_error	float		Current crosstrack error on x-y plane in meters

	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_NAV_CONTROLLER_OUTPUT(){
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
