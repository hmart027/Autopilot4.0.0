package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#2
 * 	The system time is the time of the master clock, typically the computer clock of the main onboard computer.
 */
public class MAV_SYSTEM_TIME extends MAVLinkMessage {
	
	public final static char MSG_ID = 2;
	
	//	time_unix_usec	uint64_t	Timestamp of the master clock in microseconds since UNIX epoch.
	//	time_boot_ms	uint32_t	Timestamp of the component clock since boot time in milliseconds.

	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_SYSTEM_TIME(){
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
