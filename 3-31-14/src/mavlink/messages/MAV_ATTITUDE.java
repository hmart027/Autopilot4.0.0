package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#30
 *	The attitude in the aeronautical frame (right-handed, Z-down, X-front, Y-right).
 */
public class MAV_ATTITUDE extends MAVLinkMessage {
	
	public final static char MSG_ID = 30;
	
	//time_boot_ms	uint32_t	Timestamp (milliseconds since system boot)
	//roll	float	Roll angle (rad, -pi..+pi)
	//pitch	float	Pitch angle (rad, -pi..+pi)
	//yaw	float	Yaw angle (rad, -pi..+pi)
	//rollspeed	float	Roll angular speed (rad/s)
	//pitchspeed	float	Pitch angular speed (rad/s)
	//yawspeed	float	Yaw angular speed (rad/s)
	
	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_ATTITUDE(){
		this.mID 	= MAV_MESSAGE_TYPE.get(MSG_ID);
		this.len = (byte) MAVLinkEnum.MAVLINK_MESSAGE_LENGTHS[mID.value];
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
