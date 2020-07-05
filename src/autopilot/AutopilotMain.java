package autopilot;

import java.io.IOException;
import java.io.InputStream;

import mavlink.MAVLink;
import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;
import mavlink.messages.MAV_HEARTBEAT;
import mavlink.messages.MAVLinkMessage;

@SuppressWarnings("unused")
public class AutopilotMain {
	
	public static void main(String args[]) {
	//	new MainWindow("Autopilot");
		
		byte[] myArray = {1,0,0,0,0,0,0,2,3,(byte) 128,4,3};
		
		Comm port = new Comm();
		port.getComm("COM15", 57600);
	//	port.getComm("COM8", 9600);
		
		InputStream input = port.getInputStream();
		
//		MAVLinkMessage msg = MAVLinkMessage.parseData(myArray);
//		System.out.println(msg.getMessage());
//		MAV_HEARTBEAT msg1 = (MAV_HEARTBEAT) msg;
//		System.out.println(msg1.type);
//		System.out.println(msg1.autopilot);
//		System.out.println(msg1.modeFlag);
//		System.out.println(msg1.status);
		
		MAVLink mav = new MAVLink();
		
		while(true)
			try {
				while(input.available()>0){
					int val = input.read();
					System.out.println((char)val);
					//mav.parseChar((byte)val);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}