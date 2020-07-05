package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#35
 *	The RAW values of the RC channels received. The standard PPM modulation is as follows: 
 *	1000 microseconds: 0%, 
 *	2000 microseconds: 100%. 
 *	Individual receivers/transmitters might violate this specification.
 */
public class MAV_RC_CHANNELS_RAW extends MAVLinkMessage {
	
	public final static char MSG_ID = 35;

	//	time_boot_ms	uint32_t	Timestamp (milliseconds since system boot)
	//	port			uint8_t		Servo output port (set of 8 outputs = 1 port). Most MAVs will just use one, but this allows for more than 8 servos.
	//	chan1_raw		uint16_t	RC channel 1 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan2_raw		uint16_t	RC channel 2 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan3_raw		uint16_t	RC channel 3 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan4_raw		uint16_t	RC channel 4 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan5_raw		uint16_t	RC channel 5 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan6_raw		uint16_t	RC channel 6 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan7_raw		uint16_t	RC channel 7 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	chan8_raw		uint16_t	RC channel 8 value, in microseconds. A value of UINT16_MAX implies the channel is unused.
	//	rssi			uint8_t		Receive signal strength indicator, 0: 0%, 100: 100%, 255: invalid/unknown.
	
	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_RC_CHANNELS_RAW(){
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
