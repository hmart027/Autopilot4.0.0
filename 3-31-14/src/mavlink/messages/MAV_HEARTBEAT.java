package mavlink.messages;

import mavlink.MAVLinkEnum.*;
import mavlink.MAVLinkEnum;
import mavlink.MAVLinkTools;

/** ID#0
 * 	The heartbeat message shows that a system is present and responding. The type of the MAV and Autopilot hardware 
 * 	allow the receiving system to treat further messages from this system appropriate (e.g. by laying out the user 
 * 	interface based on the autopilot).
 */
public class MAV_HEARTBEAT extends MAVLinkMessage{
	
	public final static char MSG_ID = 0;
	
	public MAV_AUTOPILOT 	autopilot;
	public MAV_TYPE 		type;
	public MAV_MODE_FLAG	modeFlag;
	public MAV_STATE 		status;
	public int 				customMode;
	
	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_HEARTBEAT(){
		this.mID 	= MAV_MESSAGE_TYPE.get(MSG_ID);
		this.len = (byte) MAVLinkEnum.MAVLINK_MESSAGE_LENGTHS[mID.value];
	}
	
	/**
	 * 	type			uint8_t					Type of the MAV (quadrotor, helicopter, etc., up to 15 types, defined in MAV_TYPE ENUM)
	 *	autopilot		uint8_t					Autopilot type / class. defined in MAV_AUTOPILOT ENUM
	 *	base_mode		uint8_t					System mode bitfield, see MAV_MODE_FLAGS ENUM in mavlink/include/mavlink_types.h
	 *	custom_mode		uint32_t				A bitfield for use for autopilot-specific flags.
	 *	system_status	uint8_t					System status flag, see MAV_STATE ENUM
	 *	mavlink_version	uint8_t_mavlink_version	MAVLink version, not writable by user, gets added by protocol because of magic data type: uint8_t_mavlink_version
	 */
	public MAV_HEARTBEAT( byte systemID, byte componentID, MAV_TYPE type, MAV_AUTOPILOT autopilot, MAV_MODE_FLAG baseMode, int customMode, MAV_STATE systemStatus){
		this.sID 		= systemID;
		this.cID 		= componentID;
		this.mID 		= MAV_MESSAGE_TYPE.get(MSG_ID);
		this.len	 	= (byte) MAVLinkEnum.MAVLINK_MESSAGE_LENGTHS[mID.value];
		this.type 		= type;
		this.autopilot 	= autopilot;
		this.modeFlag	= baseMode;
		this.customMode = customMode;
		this.status		= systemStatus;
	}
	
	@Override
	public byte[] pack() {
		byte[] newArray = new byte[len+3];
		newArray[0]		= sID;
		newArray[1]		= cID;
		newArray[2]		= (byte) mID.value;
		newArray[3]		= (byte)(customMode>>24);
		newArray[4]		= (byte)(customMode>>16);
		newArray[5]		= (byte)(customMode>>8);
		newArray[6]		= (byte)(customMode);
		newArray[7]		= (byte)this.type.value;
		newArray[8]		= (byte)this.autopilot.value;
		newArray[9]		= (byte)this.modeFlag.value;
		newArray[10]	= (byte)this.status.value;
		newArray[11]	= this.version;
		return newArray;
	}

	@Override
	protected MAVLinkMessage decode(byte[] data) {
		if(data[2] != MSG_ID)
			return null;
		this.customMode = MAVLinkTools.toInteger(data,3, 7, true);
		this.type		= MAV_TYPE.get(data[7]&0x00FF);
		this.autopilot	= MAV_AUTOPILOT.get(data[8]&0x00FF);
		this.modeFlag	= MAV_MODE_FLAG.get(data[9]&0x00FF);
		this.status		= MAV_STATE.get(data[10]&0x00FF);
		return this;
	}

}
