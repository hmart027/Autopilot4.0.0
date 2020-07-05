package mavlink;

public class MAVLinkTypes {
	
	public static final int MAVLINK_MAX_PAYLOAD_LEN = 255; ///< Maximum payload length
	public static final int MAVLINK_CORE_HEADER_LEN = 5; ///< Length of core header (of the comm. layer): message length (1 byte) + message sequence (1 byte) + message system id (1 byte) + message component id (1 byte) + message type id (1 byte)
	public static final int MAVLINK_NUM_HEADER_BYTES = (MAVLINK_CORE_HEADER_LEN + 1); ///< Length of all header bytes, including core and checksum
	public static final int MAVLINK_NUM_CHECKSUM_BYTES = 2;
	public static final int MAVLINK_NUM_NON_PAYLOAD_BYTES = (MAVLINK_NUM_HEADER_BYTES + MAVLINK_NUM_CHECKSUM_BYTES);
	public static final int MAVLINK_MAX_PACKET_LEN = (MAVLINK_MAX_PAYLOAD_LEN + MAVLINK_NUM_NON_PAYLOAD_BYTES); ///< Maximum packet length
	public static final int MAVLINK_MSG_ID_EXTENDED_MESSAGE = 255;
	public static final int MAVLINK_EXTENDED_HEADER_LEN = 14;
	public static final int MAVLINK_MAX_EXTENDED_PACKET_LEN = 65507;
	public static final int MAVLINK_MAX_EXTENDED_PAYLOAD_LEN = (MAVLINK_MAX_EXTENDED_PACKET_LEN - MAVLINK_EXTENDED_HEADER_LEN - MAVLINK_NUM_NON_PAYLOAD_BYTES);
	public static final int MAVLINK_MAX_FIELDS = 64;
	
	public static enum mavlink_message_type_t{
		MAVLINK_TYPE_CHAR     ,
		MAVLINK_TYPE_UINT8_T  ,
		MAVLINK_TYPE_INT8_T   ,
		MAVLINK_TYPE_UINT16_T ,
		MAVLINK_TYPE_INT16_T  ,
		MAVLINK_TYPE_UINT32_T ,
		MAVLINK_TYPE_INT32_T  ,
		MAVLINK_TYPE_UINT64_T ,
		MAVLINK_TYPE_INT64_T  ,
		MAVLINK_TYPE_FLOAT    ,
		MAVLINK_TYPE_DOUBLE   
	};
	
	public static enum mavlink_channel_t{
	    MAVLINK_COMM_0,
	    MAVLINK_COMM_1,
	    MAVLINK_COMM_2,
	    MAVLINK_COMM_3
	};
	
	public static enum mavlink_parse_state_t{
	    UNINIT,
	    IDLE,
	    GOT_STX,
	    GOT_SEQ,
	    GOT_LENGTH,
	    GOT_SYSID,
	    GOT_COMPID,
	    GOT_MSGID,
	    GOT_PAYLOAD,
	    GOT_CRC1;
	}; 
	
	public static class mavlink_system_t{
		byte sysid;   ///< Used by the MAVLink message_xx_send() convenience function
		byte compid;  ///< Used by the MAVLink message_xx_send() convenience function
		byte type;    ///< Unused, can be used by user to store the system's type
		byte state;   ///< Unused, can be used by user to store the system's state
		byte mode;    ///< Unused, can be used by user to store the system's mode
		byte nav_mode;    ///< Unused, can be used by user to store the system's navigation mode
	}
	
	public static class mavlink_message_t{
		public char checksum; 	// sent at end of packet
		public byte magic;   	// Message Headder
		public byte len;     	// Length of payload
		public byte seq;     	// Sequence of packet
		public byte sysid;   	// ID of message sender system/aircraft
		public byte compid;  	// ID of the message sender component
		public byte msgid;   	// ID of message in payload
		public byte[] payload = new byte[MAVLINK_MAX_PAYLOAD_LEN];	// Message Payload
		
		/**
		 * Copies the parameters of the msg into this one;
		 * @param msg-message to be copied
		 */
	    public void clone(mavlink_message_t msg){
	    	checksum 	= msg.checksum;
	    	magic		= msg.magic;
	    	len			= msg.len;
	    	seq			= msg.seq;
	    	sysid		= msg.sysid;
	    	compid		= msg.compid;
	    	msgid		= msg.msgid;
	 //   	for(int i = 0; i<payload64.length; i++)
	 //  		payload64[i]=msg.payload64[i];
	    }
	}

	public static class mavlink_extended_message_t {
	       mavlink_message_t base_msg;
	       int extended_payload_len;   ///< Length of extended payload if any
	       byte[] extended_payload = new byte[MAVLINK_MAX_EXTENDED_PAYLOAD_LEN];
	}
	
	public static class mavlink_field_info_t {
        String name;      		          // name of this field
        String print_format;      		  // printing format hint, or NULL
        mavlink_message_type_t type;      // type of this field
        int array_length;        		  // if non-zero, field is an array
        int wire_offset;         		  // offset of each field in the payload
        int structure_offset;    		  // offset in a C structure
	}

	// note that in this structure the order of fields is the order
	// in the XML file, not necessary the wire order
	public static class mavlink_message_info_t {
		String name;                                      									// name of the message
		int num_fields;                                   									// how many fields in this message
		mavlink_field_info_t[] fields = new mavlink_field_info_t[MAVLINK_MAX_FIELDS];       // field information
	}

	public static class mavlink_status_t {
	    public byte msg_received;               	///< Number of received messages
	    public byte buffer_overrun;             	///< Number of buffer overruns
	    public byte parse_error;                	///< Number of parse errors
	    public mavlink_parse_state_t parse_state;  	///< Parsing state machine
	    public byte packet_idx;                 	///< Index in current packet
	    public byte current_rx_seq;             	///< Sequence number of last packet received
	    public byte current_tx_seq;             	///< Sequence number of last packet sent
	    public char packet_rx_success_count;   		///< Received packets
	    public char packet_rx_drop_count;      		///< Number of packet drops
	    
	    /**
		 * Copies the parameters of the msg into this one;
		 * @param msg-message to be copied
		 */
	    public void clone(mavlink_status_t sts){
	    	msg_received 			= sts.msg_received;
	    	buffer_overrun			= sts.buffer_overrun;
	    	parse_error				= sts.parse_error;
	    	parse_state				= sts.parse_state;
	    	packet_idx				= sts.packet_idx;
	    	current_rx_seq			= sts.current_rx_seq;
	    	current_tx_seq			= sts.current_tx_seq;
	    	packet_rx_success_count	= sts.packet_rx_success_count;
	    	packet_rx_drop_count	= sts.packet_rx_drop_count;
	    }
	}
	
}
