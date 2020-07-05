package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#42
 *	Message that announces the sequence number of the current active mission item. The MAV will fly towards this mission item.
 */
public class MAV_MISSION_CURRENT extends MAVLinkMessage {

	public final static char MSG_ID = 0;
	
	//	seq	uint16_t	Sequence

	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_MISSION_CURRENT(){
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
