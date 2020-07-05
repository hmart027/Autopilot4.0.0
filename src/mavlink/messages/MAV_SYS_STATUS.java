package mavlink.messages;

import mavlink.MAVLinkEnum;
import mavlink.MAVLinkEnum.MAV_MESSAGE_TYPE;

/** ID#1
 * The general system state. If the system is following the MAVLink standard, the system state is mainly defined by three 
 * orthogonal states/modes: The system mode, which is either 
 * LOCKED (motors shut down and locked), 
 * MANUAL (system under RC control), 
 * GUIDED (system with autonomous position control, position setpoint controlled manually) or 
 * AUTO (system guided by path/waypoint planner). 
 * 
 * The NAV_MODE defined the current flight state: 
 * LIFTOFF (often an open-loop maneuver), 
 * LANDING, 
 * WAYPOINTS or 
 * VECTOR. 
 * 
 * This represents the internal navigation state machine. The system status shows wether the system is currently active 
 * or not and if an emergency occured. During the CRITICAL and EMERGENCY states the MAV is still considered to be active, 
 * but should start emergency procedures autonomously. After a failure occured it should first move from active to critical 
 * to allow manual intervention and then move to emergency after a certain timeout.
 */
public class MAV_SYS_STATUS extends MAVLinkMessage {
	
	public final static char MSG_ID = 1;

	//	onboard_control_sensors_present		uint32_t	Bitmask showing which onboard controllers and sensors are present. Value of 0: not present. Value of 1: present. Indices defined by ENUM MAV_SYS_STATUS_SENSOR
	//	onboard_control_sensors_enabled		uint32_t	Bitmask showing which onboard controllers and sensors are enabled: Value of 0: not enabled. Value of 1: enabled. Indices defined by ENUM MAV_SYS_STATUS_SENSOR
	//	onboard_control_sensors_health		uint32_t	Bitmask showing which onboard controllers and sensors are operational or have an error: Value of 0: not enabled. Value of 1: enabled. Indices defined by ENUM MAV_SYS_STATUS_SENSOR
	//	load								uint16_t	Maximum usage in percent of the mainloop time, (0%: 0, 100%: 1000) should be always below 1000
	//	voltage_battery						uint16_t	Battery voltage, in millivolts (1 = 1 millivolt)
	//	current_battery						int16_t		Battery current, in 10*milliamperes (1 = 10 milliampere), -1: autopilot does not measure the current
	//	battery_remaining					int8_t		Remaining battery energy: (0%: 0, 100%: 100), -1: autopilot estimate the remaining battery
	//	drop_rate_comm						uint16_t	Communication drops in percent, (0%: 0, 100%: 10'000), (UART, I2C, SPI, CAN), dropped packets on all links (packets that were corrupted on reception on the MAV)
	//	errors_comm							uint16_t	Communication errors (UART, I2C, SPI, CAN), dropped packets on all links (packets that were corrupted on reception on the MAV)
	//	errors_count1						uint16_t	Autopilot-specific errors
	//	errors_count2						uint16_t	Autopilot-specific errors
	//	errors_count3						uint16_t	Autopilot-specific errors
	//	errors_count4						uint16_t	Autopilot-specific errors
	
	/**
	 * This Default Constructor should only be called by {@link MAVLinkMessage} when parsing an incoming message.
	 */
	protected MAV_SYS_STATUS(){
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
