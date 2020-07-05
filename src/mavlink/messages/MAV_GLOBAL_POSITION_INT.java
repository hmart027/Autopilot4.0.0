package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#33
 *	The filtered global position (e.g. fused GPS and accelerometers). The position is in GPS-frame (right-handed, Z-up). 
 *	It is designed as scaled integer message since the resolution of float is not sufficient.
 */
public class MAV_GLOBAL_POSITION_INT extends MAVLinkMessage {
	
	public final static char MSG_ID = 33;
	
//	time_boot_ms	uint32_t	Timestamp (milliseconds since system boot)
//	lat				int32_t		Latitude, expressed as * 1E7
//	lon				int32_t		Longitude, expressed as * 1E7
//	alt				int32_t		Altitude in meters, expressed as * 1000 (millimeters), above MSL
//	relative_alt	int32_t		Altitude above ground in meters, expressed as * 1000 (millimeters)
//	vx				int16_t		Ground X Speed (Latitude), expressed as m/s * 100
//	vy				int16_t		Ground Y Speed (Longitude), expressed as m/s * 100
//	vz				int16_t		Ground Z Speed (Altitude), expressed as m/s * 100
//	hdg				uint16_t	Compass heading in degrees * 100, 0.0..359.99 degrees. If unknown, set to: UINT16_MAX

	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_GLOBAL_POSITION_INT(){
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
