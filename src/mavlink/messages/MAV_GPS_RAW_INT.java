package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#24
 * 	The global position, as returned by the Global Positioning System (GPS). 
 *  This is NOT the global position estimate of the sytem, but rather a RAW sensor value. 
 *  See message GLOBAL_POSITION for the global position estimate. Coordinate frame is right-handed, Z-axis up (GPS frame).
 */
public class MAV_GPS_RAW_INT extends MAVLinkMessage {
	
	public final static char MSG_ID = 24;
	
	//	time_usec			uint64_t	Timestamp (microseconds since UNIX epoch or microseconds since system boot)
	//	fix_type			uint8_t		0-1: no fix, 2: 2D fix, 3: 3D fix. Some applications will not use the value of this field unless it is at least two, so always correctly fill in the fix.
	//	lat					int32_t		Latitude (WGS84), in degrees * 1E7
	//	lon					int32_t		Longitude (WGS84), in degrees * 1E7
	//	alt					int32_t		Altitude (WGS84), in meters * 1000 (positive for up)
	//	eph					uint16_t	GPS HDOP horizontal dilution of position in cm (m*100). If unknown, set to: UINT16_MAX
	//	epv					uint16_t	GPS VDOP vertical dilution of position in cm (m*100). If unknown, set to: UINT16_MAX
	//	vel					uint16_t	GPS ground speed (m/s * 100). If unknown, set to: UINT16_MAX
	//	cog					uint16_t	Course over ground (NOT heading, but direction of movement) in degrees * 100, 0.0..359.99 degrees. If unknown, set to: UINT16_MAX
	//	satellites_visible	uint8_t	Number of satellites visible. If unknown, set to 255
	
	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_GPS_RAW_INT(){
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
