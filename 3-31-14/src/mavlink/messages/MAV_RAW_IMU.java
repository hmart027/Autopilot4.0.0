package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#27
 * 	The RAW IMU readings for the usual 9DOF sensor setup. 
 * 	This message should always contain the true raw values without any scaling to allow data capture and system debugging.
 */
public class MAV_RAW_IMU extends MAVLinkMessage {
	
	public final static char MSG_ID = 27;

/*	time_usec	uint64_t	Timestamp (microseconds since UNIX epoch or microseconds since system boot)
	xacc	int16_t	X acceleration (raw)
	yacc	int16_t	Y acceleration (raw)
	zacc	int16_t	Z acceleration (raw)
	xgyro	int16_t	Angular speed around X axis (raw)
	ygyro	int16_t	Angular speed around Y axis (raw)
	zgyro	int16_t	Angular speed around Z axis (raw)
	xmag	int16_t	X Magnetic field (raw)
	ymag	int16_t	Y Magnetic field (raw)
	zmag	int16_t	Z Magnetic field (raw)*/
	
	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_RAW_IMU(){
		this.mID 	= MAV_MESSAGE_TYPE.get(MSG_ID);
		this.len  	= (byte) MAVLinkEnum.MAVLINK_MESSAGE_LENGTHS[mID.value];
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
