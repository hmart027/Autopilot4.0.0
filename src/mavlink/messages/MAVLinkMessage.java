package mavlink.messages;

import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;
import mavlink.MAVLinkTypes.mavlink_message_t;

public abstract class MAVLinkMessage {
	
	public static final int MAVLINK_MAX_PAYLOAD_LEN = 255; ///< Maximum payload length
	public static final int MAVLINK_CORE_HEADER_LEN = 5; ///< Length of core header (of the comm. layer): message length (1 byte) + message sequence (1 byte) + message system id (1 byte) + message component id (1 byte) + message type id (1 byte)
	public static final int MAVLINK_NUM_HEADER_BYTES = (MAVLINK_CORE_HEADER_LEN + 1 ); ///< Length of all header bytes, including core and headder
	public static final int MAVLINK_NUM_CHECKSUM_BYTES = 2;
	public static final int MAVLINK_NUM_NON_PAYLOAD_BYTES = (MAVLINK_NUM_HEADER_BYTES + MAVLINK_NUM_CHECKSUM_BYTES);
	public static final int MAVLINK_MAX_PACKET_LEN = (MAVLINK_MAX_PAYLOAD_LEN + MAVLINK_NUM_NON_PAYLOAD_BYTES); ///< Maximum packet length
	public static final int MAVLINK_MSG_ID_EXTENDED_MESSAGE = 255;
	public static final int MAVLINK_EXTENDED_HEADER_LEN = 14;
	public static final int MAVLINK_MAX_EXTENDED_PACKET_LEN = 65507;
	public static final int MAVLINK_MAX_EXTENDED_PAYLOAD_LEN = (MAVLINK_MAX_EXTENDED_PACKET_LEN - MAVLINK_EXTENDED_HEADER_LEN - MAVLINK_NUM_NON_PAYLOAD_BYTES);
	public static final int MAVLINK_MAX_FIELDS = 64;
	
	public static final int MAVLINK_HEADER = 0xFE; 
	public static final int MAVLINK_VERSION = 3;

	protected byte len;     										// Length of payload
	protected byte seq;     										// Sequence of packet
	protected byte sID;												// System ID
	protected byte cID; 											// Componnent ID
	protected byte mID;				 								// Message ID
	protected byte[] payload = new byte[MAVLINK_MAX_PAYLOAD_LEN];	// Message Payload	
	protected byte checksumLow; 									// last-1 byte of packet	
	protected byte checksumHigh;									// last byte of packet
			
	/**
	 * Gets the Enumeration that matches the message ID.
	 * @return	{@link mavlink.MAVLinkEnumerations.MAV_MESSAGE}
	 */
	public MAV_MESSAGE_TYPE getMessage(){
		return MAV_MESSAGE_TYPE.get(this.mID & 0x0FF);
	}

	/**
	 * Parses the incoming data array and extracts its data. After parsing it returns the corresponding MAVLinkMessage.
	 * The resulting MAVLinkMessage should be casted to its corresponding message class by using {@link MAVLinkMessage.getMessage()}
	 * @param data -the byte array containing the data
	 * @return corresponding MAVLinkMessage
	 */
	public static MAVLinkMessage parseData(byte[] data){
		
		if(data == null)
			return null;
		if(data.length<3)
			return null;
		
		MAVLinkMessage msg;
		
		MAV_MESSAGE_TYPE msgID = MAV_MESSAGE_TYPE.get(data[2] & 0x0FF);
		
		if(msgID == null)
			return null;
		
		switch (msgID){
		case HEARTBEAT:
			msg = new MAV_HEARTBEAT(); break;
		case SYS_STATUS:
			msg = new MAV_SYS_STATUS(); break;
		case SYSTEM_TIME:
			msg = new MAV_SYSTEM_TIME(); break;
		case GPS_RAW_INT:
			msg = new MAV_GPS_RAW_INT(); break;
		case RAW_IMU:
			msg = new MAV_RAW_IMU(); break;
		case SCALED_PRESSURE:
			msg = new MAV_SCALED_PRESSURE(); break;
		case ATTITUDE:
			msg = new MAV_ATTITUDE(); break;
		case GLOBAL_POSITION_INT:
			msg = new MAV_GLOBAL_POSITION_INT(); break;
		case RC_CHANNELS_RAW:
			msg = new MAV_RC_CHANNELS_RAW(); break;
		case SERVO_OUTPUT_RAW:
			msg = new MAV_SERVO_OUTPUT_RAW(); break;
		case MISSION_CURRENT:
			msg = new MAV_MISSION_CURRENT(); break;
		case NAV_CONTROLLER_OUTPUT:
			msg = new MAV_NAV_CONTROLLER_OUTPUT(); break;
		case VFR_HUD:
			msg = new MAV_VFR_HUD(); break;
		default:
			return null;
		}
		
		System.out.println(MAV_MESSAGE_TYPE.get(data[2] & 0x0FF));
		
		return msg.decode(data);
	}
	
	/**
	 * Parses the incoming message and extracts its data. After parsing it, returns the corresponding MAVLinkMessage.
	 * The resulting MAVLinkMessage should be casted to its corresponding message class by using {@link MAVLinkMessage.getMessage()}
	 * @param a the {@link mavlink_message_t} containing the message info.
	 * @return corresponding MAVLinkMessage
	 */
	public MAVLinkMessage parseData(){
		
		MAVLinkMessage msg;
		
		switch (MAV_MESSAGE_TYPE.get(this.mID & 0x0FF)){
		case HEARTBEAT:
			msg = new MAV_HEARTBEAT(); break;
		case SYS_STATUS:
			msg = new MAV_SYS_STATUS(); break;
		case SYSTEM_TIME:
			msg = new MAV_SYSTEM_TIME(); break;
		case GPS_RAW_INT:
			msg = new MAV_GPS_RAW_INT(); break;
		case RAW_IMU:
			msg = new MAV_RAW_IMU(); break;
		case SCALED_PRESSURE:
			msg = new MAV_SCALED_PRESSURE(); break;
		case ATTITUDE:
			msg = new MAV_ATTITUDE(); break;
		case GLOBAL_POSITION_INT:
			msg = new MAV_GLOBAL_POSITION_INT(); break;
		case RC_CHANNELS_RAW:
			msg = new MAV_RC_CHANNELS_RAW(); break;
		case SERVO_OUTPUT_RAW:
			msg = new MAV_SERVO_OUTPUT_RAW(); break;
		case MISSION_CURRENT:
			msg = new MAV_MISSION_CURRENT(); break;
		case NAV_CONTROLLER_OUTPUT:
			msg = new MAV_NAV_CONTROLLER_OUTPUT(); break;
		case VFR_HUD:
			msg = new MAV_VFR_HUD(); break;
		default:
			return null;
		}
		
		System.out.println(mID);
		
		msg.clone(this);
		return msg.decode(payload);
	}
	
	/**
	 * Packs the {@link MAVLinkMessage} into a byte array to be transmitted.
	 * This method must return a byte array containing the following structure:<br>
	 * { systemID(1 byte), componentID(1 byte), messageID(1 byte), payload(n bytes) }<br>
	 * @return an array of size lebgth only containing systemID, componentID, and messageID..
	 */
	public byte[] pack(){
		int msgLength = MAVLINK_NUM_NON_PAYLOAD_BYTES + this.len;
		byte[] msg = new byte[msgLength];
		msg[0] 				= (byte) MAVLINK_HEADER;
		msg[1] 				= this.len;
		msg[2] 				= this.seq;
		msg[3] 				= this.sID;
		msg[4] 				= this.cID;
		msg[5] 				= this.mID;
		msg[msgLength-2] 	= this.checksumLow;
		msg[msgLength-1] 	= this.checksumHigh;
		for(int i = 0; i<this.len; i++)
			msg[i+6] = this.payload[i];	
		return msg;
	}
	
	/**
	 * This method is only used by {@link MAVLinkMessage} children. It returns a MAVLink message containing the appropiate message.
	 * @param data	- byte array of the message payload
	 * @return	appropiate {@link MAVLinkMessage}
	 */
	protected MAVLinkMessage decode(byte[] data){return null;}

	/**
	 * Copies the parameters of the msg into this one;
	 * @param msg-message to be copied
	 */
    private void clone(MAVLinkMessage msg){
    	this.len		= msg.len;
    	this.seq		= msg.seq;
    	this.sID		= msg.sID;
    	this.cID		= msg.cID;
    	this.mID		= msg.mID;
    	for(int i = 0; i<payload.length; i++)
    		this.payload[i]=msg.payload[i];
    	this.checksumLow 	= msg.checksumLow;
    	this.checksumHigh 	= msg.checksumHigh;
    }
}
