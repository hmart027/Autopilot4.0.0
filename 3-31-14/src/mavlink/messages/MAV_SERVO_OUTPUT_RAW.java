package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#36
 *	The RAW values of the servo outputs (for RC input from the remote, use the RC_CHANNELS messages). 
 *	The standard PPM modulation is as follows: 1000 microseconds: 0%, 2000 microseconds: 100%.
 */
public class MAV_SERVO_OUTPUT_RAW extends MAVLinkMessage {

	public final static char MSG_ID = 36;
	
	//	time_usec	uint32_t	Timestamp (microseconds since system boot)
	//	port		uint8_t		Servo output port (set of 8 outputs = 1 port). Most MAVs will just use one, but this allows to encode more than 8 servos.
	//	servo1_raw	uint16_t	Servo output 1 value, in microseconds
	//	servo2_raw	uint16_t	Servo output 2 value, in microseconds
	//	servo3_raw	uint16_t	Servo output 3 value, in microseconds
	//	servo4_raw	uint16_t	Servo output 4 value, in microseconds
	//	servo5_raw	uint16_t	Servo output 5 value, in microseconds
	//	servo6_raw	uint16_t	Servo output 6 value, in microseconds
	//	servo7_raw	uint16_t	Servo output 7 value, in microseconds
	//	servo8_raw	uint16_t	Servo output 8 value, in microseconds

	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_SERVO_OUTPUT_RAW(){
		this.mID 	= MAV_MESSAGE_TYPE.get(MSG_ID);
		this.len	= (byte) MAVLinkEnum.MAVLINK_MESSAGE_LENGTHS[mID.value];
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
