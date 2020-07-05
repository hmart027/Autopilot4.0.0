/*	Java implementation of the MAVLink
 * communication protocols
 *
 *
 */

package mavlink;

import java.util.ArrayList;

import mavlink.MAVLinkTypes.mavlink_message_t;
import mavlink.MAVLinkTypes.mavlink_parse_state_t;
import mavlink.MAVLinkTypes.mavlink_status_t;
import mavlink.messages.MAVLinkMessage;
import drones.Drone;

public class MAVLink {
	private ArrayList<Drone> drones = new ArrayList<>();

	private static byte HEADER = (byte) 0xFE;
	private static boolean CRC_EXTRA = true;
	
	// Parsing states
	public static final byte MAVLINK_PARSE_STATE_UNINIT		 = 0;
	public static final byte MAVLINK_PARSE_STATE_IDLE		 = 1;
	public static final byte MAVLINK_PARSE_STATE_GOT_STX	 = 2;
	public static final byte MAVLINK_PARSE_STATE_GOT_SEQ	 = 3;
	public static final byte MAVLINK_PARSE_STATE_GOT_LENGTH	 = 4;
	public static final byte MAVLINK_PARSE_STATE_GOT_SYSID	 = 5;
	public static final byte MAVLINK_PARSE_STATE_GOT_COMPID	 = 6;
	public static final byte MAVLINK_PARSE_STATE_GOT_MSGID	 = 7;
	public static final byte MAVLINK_PARSE_STATE_GOT_PAYLOAD = 8;
	public static final byte MAVLINK_PARSE_STATE_GOT_CRC1	 = 9;

	// Input data info
	private int dataIndex = 0;
	private byte[] dataString;

	private int cntAuto = -1;
	private int outSerial = 0;
	private int inSerial = 0;
	@SuppressWarnings("unused")
	private int pckCount = 0;
	
	private mavlink_message_t currentMsg;
	private mavlink_status_t status = new mavlink_status_t();

 	public int getLastDrone() {
		return cntAuto;
	}

	public int getDrone(int sID, int cID) {
		Drone current;
		for (int i = 0; i < drones.size(); i++) {
			current = drones.get(i);
			if ((current.getSystemID() == sID)
					& (current.getComponentID() == cID))
				return i;
		}
		return -1;
	}

	public ArrayList<Drone> getDroneList() {
		return drones;
	}

	public float getDropRate() {
		float dropped	= status.packet_rx_drop_count;
		float total 	= status.packet_rx_drop_count;
		return dropped/total * 100;
				//(float) (pckCount / (inSerial + 1) * 100) - 100;
	}

	public byte[] sendCommand(int autopilotNum, int cmdID, byte[] parameters) {
		Drone drone = drones.get(autopilotNum);
		byte[] data = drone.sendCommand(cmdID, parameters);
		byte sID = (byte) drone.getSystemID();
		byte cID = 0;
		byte length = (byte) MAVLinkEnum.MAVLINK_MESSAGE_LENGTHS[cmdID];
		byte[] transmition = new byte[length + MAVLinkEnum.MAVLINK_MIN_MSG_LENGTH];
		transmition[0] = HEADER;
		transmition[1] = length;
		transmition[2] = (byte) (outSerial++);
		transmition[3] = sID;
		transmition[4] = cID;
		transmition[5] = (byte)cmdID;
		for (int i = 6; i < (data.length+6); i++)
			transmition[i] = data[i - 6];
		char crc = CheckSum.crc_init();
		for(int i=0;i < length + 5;i++)
			crc = CheckSum.crc_accumulate(transmition[i+1], crc);
		if (CRC_EXTRA)
			crc = CheckSum.crc_accumulate((byte) MAVLinkEnum.MAVLINK_MESSAGE_CRCS[cmdID], crc);
		transmition[transmition.length - 2] = (byte) (crc);
		transmition[transmition.length - 1] = (byte) (crc>>8);
		return transmition;
	}
	
	//test
	public boolean parseChar(byte data) {
		
		switch (status.parse_state){
		case UNINIT:
		case IDLE:
			if ((HEADER == data)) {
				currentMsg = new mavlink_message_t();
				status.packet_idx++;
				status.parse_state = mavlink_parse_state_t.GOT_STX;
			}
			return false;
			
		case GOT_STX:
			currentMsg.len = data;
			dataString = new byte[(currentMsg.len & 0x0FF) + 7];
			dataString[status.packet_idx - 1] = data;
			status.packet_idx++;
			status.parse_state = mavlink_parse_state_t.GOT_LENGTH;
			return false;
			
		case GOT_LENGTH:
			currentMsg.seq = data;
			status.packet_idx++;
			status.parse_state = mavlink_parse_state_t.GOT_SEQ;
			return false;
			
		case GOT_SEQ:
			currentMsg.sysid = data;
			status.packet_idx++;
			status.parse_state = mavlink_parse_state_t.GOT_SYSID;
			return false;
		
		case GOT_SYSID:
			currentMsg.compid = data;
			status.packet_idx++;
			status.parse_state = mavlink_parse_state_t.GOT_COMPID;
			return false;
			
		case GOT_COMPID:
			currentMsg.msgid = data;
			status.packet_idx++;
			status.parse_state = mavlink_parse_state_t.GOT_MSGID;
			return false;
			
		case GOT_MSGID:
			dataString[dataIndex - 1] = data;
			status.packet_idx++;
			if (dataIndex == (currentMsg.len + 7))
				status.parse_state = mavlink_parse_state_t.GOT_PAYLOAD;
			return false;
			
		case GOT_PAYLOAD:
			currentMsg.checksum = (char) (data);
			status.packet_idx++;
			status.parse_state = mavlink_parse_state_t.GOT_CRC1;
			return false;
			
		case GOT_CRC1:
			currentMsg.checksum = (char) (currentMsg.checksum | (data<<8));
			char crc = CheckSum.crc_init();
			for(int i = 0; i<dataString.length-2; i++)
				crc = CheckSum.crc_accumulate(dataString[i], crc);
			if (CRC_EXTRA)
				crc = CheckSum.crc_accumulate((byte) MAVLinkEnum.MAVLINK_MESSAGE_CRCS[currentMsg.msgid & 0x0FF], crc);
			if (crc != currentMsg.checksum)
				return false;
			status.parse_state = mavlink_parse_state_t.IDLE;
		}
		
		status.current_rx_seq = currentMsg.seq;
		status.packet_rx_success_count++;
		status.msg_received = currentMsg.msgid;
		
		byte[] dataPck = new byte[currentMsg.len+3];
		for (int i = 0; i < dataPck.length; i++) {
			dataPck[i] = dataString[i + 2];
		}

		@SuppressWarnings("unused")
		MAVLinkMessage msg = MAVLinkMessage.parseData(dataPck);
		
		dataIndex = 0;
		currentMsg.len = 0;
		dataString = null;
		return true;
	}
	
	// end test
	
	public boolean mavlink_parse_char(byte data, mavlink_message_t msg) {

		if (dataIndex == 0) {
			if ((HEADER == data)) {
				dataIndex++;
				currentMsg = new mavlink_message_t();
				return false;
			}
			return false;
		}
		if (dataIndex == 1) {
			currentMsg.len = data;
			dataString = new byte[(currentMsg.len & 0x0FF) + 7];
			dataString[dataIndex - 1] = data;
			dataIndex++;
			return false;
		}
		if ((dataIndex > 1)) {
			if (dataIndex == 5) {
				if (MAVLinkEnum.MAVLINK_MESSAGE_LENGTHS[data&0x00FF] != currentMsg.len) {
					dataIndex = 0;
					dataString = null;
					return false;
				}
			}
			dataString[dataIndex - 1] = data;
			dataIndex++;
		}	
		// If false the whole package has been received
		if (dataIndex < (currentMsg.len + 8))
			return false;
		
		currentMsg.sysid = dataString[2];
		if (currentMsg.sysid == 0) {
			dataIndex = 0;
			dataString = null;
			return false;
		}
		currentMsg.compid = dataString[3];
		currentMsg.msgid = dataString[4];
	
		// This is where the Checksum is calculated and checked
		char crc = CheckSum.crc_init();
		for(int i = 0; i<dataString.length-2; i++){
			crc = CheckSum.crc_accumulate(dataString[i], crc);
		}
		if (CRC_EXTRA)
			crc = CheckSum.crc_accumulate((byte) MAVLinkEnum.MAVLINK_MESSAGE_CRCS[currentMsg.msgid & 0x0FF], crc);
		char crc1 = (char) (dataString[dataString.length-1] & 0x0FF);
		char crc2 = (char) (dataString[dataString.length-2] & 0x0FF);
		if ((crc^((crc1<<8) | crc2)) != 0) {
			dataIndex = 0;
			dataString = null;
			return false;
		}	
		cntAuto = getDrone(currentMsg.sysid & 0x0FF, currentMsg.compid & 0x0FF);
		if (cntAuto == -1) {
			drones.add(new Drone(currentMsg.sysid & 0x0FF, currentMsg.compid & 0x0FF));
			cntAuto = drones.size() - 1;
		}
		inSerial = dataString[1] & 0x00FF;
		if (inSerial == 0)
			pckCount = 1;
		pckCount++;
		byte[] dataPck = new byte[currentMsg.len];
		for (int i = 0; i < dataPck.length; i++) {
			dataPck[i] = dataString[i + 5];
		}
		drones.get(cntAuto).receivedCommand(currentMsg.msgid & 0x0FF, dataPck);
		if(msg != null)
			msg.clone(currentMsg);
		dataIndex = 0;
		currentMsg.len = 0;
		dataString = null;
		return true;
	}

}